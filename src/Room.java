import java.util.Arrays;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;

//Maybe this should extend container?? >:)

public class Room {
	int sizex;
	int sizey;
	float x;
	float y;
	String name;
	int id = 0;
	public Rectangle hitbox;
	Entity ents[];
	Room left;
	Room right;
	Room up;
	Room down;

	public Room(float x, float y) {
		this.x = x;
		this.y = y;
		sizex = 300;
		sizey = 100;
		name = "TestRoom";
		hitbox = new Rectangle(x, y, sizex, sizey);
		hitbox.setBounds(hitbox);
		this.ents = new Entity[] {};
	}

	public void set_adjacent(String dir, Room e) {
		dir = dir.toLowerCase();
		switch (dir) {
		case "left":
			left = e;
			break;
		case "right":
			right = e;
			break;
		case "up:":
			up = e;
			break;
		case "down":
			down = e;
			break;
		}
	}

	public void add_entity(Entity e) {
		e.setRoom(this);
		ents = Arrays.copyOf(ents, ents.length + 1);
		ents[ents.length - 1] = e;
	}

	public void update(int delta) {
		for (Entity e : ents) {
			e.update(delta);
		}
	}

	public void drawFreeAdjacents(Graphics surface) {
		surface.setColor(Color.white);
		
		//select one of these to add a room to. checks room size after this.
		if (left == null) {
			surface.drawRect(x-6, y, -100, 100);
		}
		if (right == null) {
			surface.drawRect(x+sizex+6, y, 100, 100);
		}
		if (up == null) {
			surface.drawRect(x, y-6, sizex, -100);
		}
		if (down == null) {
			surface.drawRect(x, y+sizey+6, sizex, 100);
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
