import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Vector;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
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
	Room left;
	Room right;
	Room up;
	Room down;

	Button build_r;
	Button build_l;
	
	StateGame s;

	// Use to get method reference to a method of this class!
	public Method fgetMethod(String methodName, Class... args) throws NoSuchMethodException, SecurityException {
		return this.getClass().getMethod(methodName, args);
	}

	public void build(Room r, String direction) {
		switch (direction) {
		case "left":
			left = r;
			r.right = this;
			r.x = this.x - r.sizex;
			r.y = this.y;
			return;
		case "right":
			right = r;
			r.left = this;
			r.x = this.x + this.sizex;
			r.y = this.y;
			return;
		}
	}

	public Room(float x, float y,StateGame s) throws NoSuchMethodException, SecurityException {
		this.x = x;
		this.y = y;
		this.s = s;
		sizex = 300;
		sizey = 100;
		name = "TestRoom";
		hitbox = new Rectangle(x, y, sizex, sizey);
		hitbox.setBounds(hitbox);
		this.ents = new Vector<Entity>();

		build_r = new Button(75, 100, (int) x + sizex + 6, (int) y, 2, "", StateGame.f_18, fgetMethod("build", Room.class, String.class), this);
		build_r.set_args(null, "right");

		build_l = new Button(75, 100, -75 + x - 6, y, 2, "", StateGame.f_18, fgetMethod("build", Room.class, String.class), this);
		build_l.set_args(null, "left");


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
		build_r.update(i, mx, my, delta);
		//build_u.update(i, mx, my, delta);
		//build_d.update(i, mx, my, delta);
		build_l.update(i, mx, my, delta);
		build_r.set_args(s.incoming,"right");
		build_l.set_args(s.incoming,"left");
	}

	public void drawFreeAdjacents(Graphics surface) {
		surface.setColor(Color.white);

		// select one of these to add a room to. checks room size after this.
		if (left == null) {
			build_l.draw(surface);
		}

		if (right == null) {
			build_r.draw(surface);
		}

	}

	public void draw(Graphics surface) {

		for (Entity e : ents) {
			e.draw(surface);
		}
		surface.setColor(Color.red);
		surface.setLineWidth(2);
		surface.drawRect(x, y, hitbox.getWidth(), hitbox.getHeight());

	}

}
