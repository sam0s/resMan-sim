import java.lang.reflect.Method;
import java.util.Vector;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Rectangle;

public class Entity {
	public String name;
	// /public Image sprite;
	public Animation sprite;
	public int level;
	public int id;
	public float sizex = 1;
	public float sizey = 1;
	public float x;
	public float y;
	public Rectangle hitbox;
	public Room curRoom;
	public boolean moving = false;
	public int roamDir = 1;
	public int[] origin = { 16, 32 };
	Vector<Room> visited = new Vector<Room>();

	int hp;
	int hp_max;

	boolean dead;
	boolean destroy;

	public Method fgetMethod(String methodName, Class... args) throws NoSuchMethodException, SecurityException {
		return this.getClass().getMethod(methodName, args);
	}

	public Entity(String name, int x, int y, boolean gender) throws NoSuchMethodException, SecurityException {
		this.name = name;
		this.x = x;
		this.y = y;

		hp = 100;
		hp_max = 1000;
		dead = false;
		destroy = false;
	}

	public void set_name(String name) {
		this.name = name;
	}

	public void pathToRoom(Room start, Room r) {
		String y = find(start, r, "");
		System.out.println("PATH: " + y);
	}

	public String find(Room start, Room r, String path) {
		visited = new Vector<Room>();
		visited.add(start);

		if (start.right != null) {
			path = findpath(start.right, r, path + "r ");
		}

		visited = new Vector<Room>();
		visited.add(start);

		if (start.left != null && path.length() < 1) {
			path = findpath(start.left, r, path + "l ");
		}

		return path;

	}

	public String findpath(Room start, Room r, String path) {
		String t = "";
		visited.add(start);
		if (start == r) {
			t = path;
		} else {

			if (start.right != null && !visited.contains(start.right)) {
				t += findpath(start.right, r, path + "r ");
			}

			if (start.up != null && !visited.contains(start.up)) {
				t += findpath(start.up, r, path + "u ");
			}

			if (start.left != null && !visited.contains(start.left)) {
				t += findpath(start.left, r, path + "l ");
			}

			if (start.down != null && !visited.contains(start.down)) {
				t += findpath(start.down, r, path + "d ");
			}
		}

		return t;

	}

	public String pathHor(Room start, Room r) {
		String ret = "";
		System.out.println(start.name);
		System.out.println(r.name);
		Room testL = start;
		Room testR = start;
		Vector<String> q = new Vector<String>();

		int steps = 0;
		while (testL != r && testR != r) {
			// System.out.println("steps = " + steps);
			steps++;
			if (testL.left != null) {
				testL = testL.left;
			}

			if (testR.right != null) {
				testR = testR.right;
			}
			if (steps > 5000) {
				break;
			}
		}
		if (testR == r) {
			for (int i = 0; i < steps; i++) {
				ret += "r ";
				System.out.println("right");
			}
		} else {
			for (int i = 0; i < steps; i++) {
				ret += "l ";
				System.out.println("left");
			}
		}
		return ret;
	}

	public void setRoom(Room r) {
		curRoom = r;
		x = r.x;
		y = r.y + r.sizey - sprite.getHeight() * sizey;
	}

	public void setSize(float size) {
		this.sizey = size;
	}

	public void downSize() {
		setSize(sizey - 1);
	}

	public void up_size() {
		setSize(sizey + 1);
	}

	public boolean isClicked(int mx, int my) {

		return ((mx > x && mx < x + hitbox.getWidth()) && (my > y && my < y + hitbox.getHeight()));
	}

	public void roam(int delta) {
		if (roamDir == 1) {
			x += 40 * (delta / 1000f);
			if (x + sprite.getWidth() * sizex >= curRoom.x + curRoom.sizex) {
				roamDir = 0;
				sizex = -1;
				return;
			}
		} else {
			x -= 40 * (delta / 1000f);
			if (x <= curRoom.x) {
				sizex = 1;
				roamDir = 1;
				return;
			}
		}
	}

	public void setSpriteLoad(SpriteSheet spr) throws SlickException {
		// later make this a list accessible by the class;
		sprite = new Animation(spr, spr.getHorizontalCount());
		sprite.setSpeed(0.5f);
		hitbox = new Rectangle(x, y, sprite.getWidth(), sprite.getHeight());
		hitbox.setBounds(hitbox);
	}

	public void update(int delta) {
		roam(delta);
		if (hp <= 0) {
			dead = true;
			destroy = true;
		}
	}

	public void draw(Graphics surface) {
		sprite.draw(x - sizex * origin[0] + origin[0], y - sizey * origin[1] + origin[1], sprite.getWidth() * sizex, sprite.getHeight() * sizey);
		surface.setColor(Color.red);
		surface.setLineWidth(2);
		surface.drawRect(x, y, hitbox.getWidth(), hitbox.getHeight());
	}

}