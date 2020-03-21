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
	String name;
	int id = 0;

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

	public Room(float x, float y, int sizex, int sizey, String name, Image sprite, StateGame s) throws NoSuchMethodException, SecurityException {
		this.x = x;
		this.y = y;
		this.s = s;
		this.sprite = sprite;
		// case "elevator":
		// sizex = 80;
		// sizey = 110;
		// break;
		// default:
		this.sizex = sizex;
		this.sizey = sizey;
		// break;
		// }
		this.name = name;
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

	public void update(int delta) {
		for (Entity e : ents) {
			e.update(delta);
		}
	}

	public void update(Input i, int mx, int my, int delta) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
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
			break;
		case "down":
			down = r;
			r.up = this;
			r.x = x;
			r.y = this.y + sizey;
			break;
		case "left":
			left = r;
			r.right = this;
			r.x = this.x - r.sizex;
			r.y = this.y;
			break;
		case "right":
			right = r;
			r.left = this;
			r.x = this.x + this.sizex;
			r.y = this.y;
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

		if (left == null && !s.room_overlap(x - s.new_room.sizex, y, x, y + s.new_room.sizey)) {
			draw_left_button(surface);
		} else {
			build_l.pause = true;
		}

		if (right == null && !s.room_overlap(x + sizex, y, x + sizex + s.new_room.sizex, y + s.new_room.sizey)) {
			draw_right_button(surface);
		} else {
			build_r.pause = true;
		}

	}

	public void draw(Graphics surface) {

		sprite.draw(x,y);
		
		surface.setColor(Color.red);
		surface.setLineWidth(2);
		surface.drawRect(x, y, hitbox.getWidth(), hitbox.getHeight());
		
		for (Entity e : ents) {
			e.draw(surface);
		}
		
		

	}

}
