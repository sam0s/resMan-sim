import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Vector;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Rectangle;

//Maybe this should extend container?? >:)

public class Room {
	int sizex;
	int sizey;
	float x;
	float y;
	String type;
	int id = 0;
	int level = 0;

	Rectangle hitbox;
	Vector<Entity> ents;
	Image sprite;
	Room left;
	Room right;
	Room up;
	Room down;

	Button build_u;
	Button build_d;
	Button build_r;
	Button build_l;

	StateGame s;

	// Use to get method reference to a method of this class!
	public Method fgetMethod(String methodName, Class... args) throws NoSuchMethodException, SecurityException {
		return this.getClass().getMethod(methodName, args);
	}

	public void clearOldEnts() {
		ents.removeIf(n -> (n.curRoom != this));
	}

	public Room(float x, float y, int sizex, int sizey, String type, Image sprite, StateGame s) throws NoSuchMethodException, SecurityException {
		this.x = x;
		this.y = y;
		this.s = s;
		this.sprite = sprite;
		this.sizex = sizex;
		this.sizey = sizey;

		this.type = type;
		hitbox = new Rectangle(x, y, sizex, sizey);
		hitbox.setBounds(hitbox);
		this.ents = new Vector<Entity>();

		build_u = new Button(sizex, sizey, (int) x, (int) y, 2, "", s.f_18, fgetMethod("add_connection", Room.class, String.class), this);
		build_u.set_args(null, "up");
		build_u.pause = true;

		build_d = new Button(sizex, sizey, (int) x, (int) y - sizey, 2, "", s.f_18, fgetMethod("add_connection", Room.class, String.class), this);
		build_d.set_args(null, "down");
		build_d.pause = true;

		build_r = new Button(75, sizey, (int) x + sizex + 6, (int) y, 2, "", s.f_18, fgetMethod("add_connection", Room.class, String.class), this);
		build_r.set_args(null, "right");
		build_r.pause = true;

		build_l = new Button(75, sizey, -75 + x - 6, y, 2, "", s.f_18, fgetMethod("add_connection", Room.class, String.class), this);
		build_l.set_args(null, "left");
		build_l.pause = true;

	}

	public void add_entity(Entity e) {
		e.setRoom(this);
		ents.addElement(e);
	}

	public void update(Input i, int mx, int my, int delta) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		if (mx >= x && mx <= x + sizex && my >= y && my <= y + sizey) {
			if (i.isMousePressed(0)) {
				s.focused_room = this;
				System.out.printf("roomba\n");
			}
		}
		if (s.mode == "room_place") {
			build_u.set_args(s.new_room, "up");
			build_d.set_args(s.new_room, "down");
			build_r.set_args(s.new_room, "right");
			build_l.set_args(s.new_room, "left");

			build_r.update(i, mx, my, delta);
			build_u.update(i, mx, my, delta);
			build_d.update(i, mx, my, delta);
			build_l.update(i, mx, my, delta);
		}
	}

	public void add_connection(Room r, String direction) {
		if (r == null) {
			return;
		}
		switch (direction) {
		case "up":
			up = r;
			r.down = this;
			r.x = x;
			r.y = this.y - sizey;
			r.level = this.level - 1;
			r.check_adjacencies();
			break;
		case "down":
			down = r;
			r.up = this;
			r.x = x;
			r.y = this.y + sizey;
			r.level = this.level + 1;
			r.check_adjacencies();
			break;
		case "left":
			left = r;
			r.right = this;
			r.x = this.x - r.sizex;
			r.y = this.y;
			r.level = this.level;
			r.check_adjacencies();
			break;
		case "right":
			right = r;
			r.left = this;
			r.x = this.x + this.sizex;
			r.y = this.y;
			r.level = this.level;
			r.check_adjacencies();
			break;
		}
		s.placed = true;
	}

	public void draw_left_button(Graphics surface) {
		build_l.x = x - build_l.sizex;
		build_l.y = y;
		build_l.draw(surface);
		build_l.pause = false;
	}

	public void draw_right_button(Graphics surface) {
		build_r.x = x + sizex;
		build_r.y = y;
		build_r.draw(surface);
		build_r.pause = false;
	}

	public void draw_top_button(Graphics surface) {
		build_u.x = x;
		build_u.y = y - build_u.sizey;
		build_u.draw(surface);
		build_u.pause = false;
	}

	public void draw_bottom_button(Graphics surface) {
		build_d.x = x;
		build_d.y = y + sizey;
		build_d.draw(surface);
		build_d.pause = false;
	}

	public void drawFreeAdjacents(Graphics surface) {
		surface.setColor(Color.white);

		if (left == null && s.room_overlap(x - s.new_room.sizex, y, x, y + s.new_room.sizey) == null) {
			draw_left_button(surface);
		} else {
			build_l.pause = true;
		}

		if (right == null && s.room_overlap(x + sizex, y, x + sizex + s.new_room.sizex, y + s.new_room.sizey) == null) {
			draw_right_button(surface);
		} else {
			build_r.pause = true;
		}

	}

	public void draw(Graphics surface) {

		sprite.draw(x, y);
		surface.setColor(Color.red);
		if (s.focused_room == this) {
			surface.setColor(Color.cyan);
		}
		surface.setLineWidth(2);
		surface.drawRect(x, y, hitbox.getWidth(), hitbox.getHeight());

		for (Entity e : ents) {
			e.draw(surface);
		}

		surface.setFont(StateGame.f_14);
		surface.setColor(Color.cyan);
		surface.drawString("" + level, x + 2, y + 2);

	}

	public void check_adjacencies() {
		Room ovlp;
		if (left == null && (ovlp = s.room_overlap(x - 10, y, x, y + sizey)) != null) {
			ovlp.right = this;
			left = ovlp;
		}

		if (right == null && (ovlp = s.room_overlap(x + sizex, y, x + sizex + 10, y + sizey)) != null) {
			ovlp.left = this;
			this.right = ovlp;
		}
	}

}
