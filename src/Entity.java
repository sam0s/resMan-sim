import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.Vector;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

public class Entity implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7002488053710004924L;
	public String name;
	// /public Image sprite;
	public transient Image sprite;
	public int level;
	public int id;
	public float sizex = 1;
	public float sizey = 1;
	public float x;
	public float y;
	int direction = 1;
	public transient Rectangle hitbox;
	public transient Room curRoom;
	public boolean moving = false;
	public int roamDir = 1;
	public int[] origin = { 16, 32 };
	transient Vector<Room> visited = new Vector<Room>();
	String cur_path = "";
	int hp;
	int hp_max;
	int speed = 100;
	boolean dead;
	float target_x;
	boolean destroy;

	public Method fgetMethod(String methodName, Class... args) throws NoSuchMethodException, SecurityException {
		return this.getClass().getMethod(methodName, args);
	}

	public void anim_walk() {
	}

	public void anim_idle() {
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
		if (r == start) {
			return;
		}
		String pathL = "";
		String pathR = "";
		String y = "";
		visited = new Vector<Room>();
		visited.add(start);

		if (start.right != null) {
			pathR = findpath(start.right, r, "r ");
		}

		visited = new Vector<Room>();
		visited.add(start);

		if (start.left != null) {
			pathL = findpath(start.left, r, "l ");
		}

		// System.out.println("L dist " + pathL.length());
		// System.out.println("R dist " + pathR.length());
		y = (pathR.equals("")) ? pathL : pathR;
		if (pathL.equals("") == false && pathR.equals("") == false) {
			if (pathR.length() == pathL.length()) {
				// prefer to move the RIGHT way
				y = pathR;
			}
			// choose the road most traveled
			y = (pathL.length() < pathR.length()) ? pathL : pathR;
		}
		y = y.replaceAll("\\s", "");
		cur_path = "" + y;
		System.out.println("PATH: " + y);
		if (cur_path.charAt(0) == 'l') {
			direction = -1;
		} else if (cur_path.charAt(0) == 'r') {
			direction = 1;
		}
		anim_walk();

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

	public void setRoom(Room r) {
		curRoom = r;
		x = r.x;
		y = r.y + r.sizey - sprite.getHeight() * sizey;
	}

	public void moveToRoom(Room r, int left) {
		Room lastRoom = curRoom;
		curRoom = r;
		curRoom.add_entity(this);
		lastRoom.clearOldEnts();
		x = r.x;
		if (left == 1) {
			System.out.println("leftit");
			x = r.x + r.sizex - this.sprite.getWidth();
		}
		y = r.y + r.sizey - sprite.getHeight() * sizey;

	}

	public void move_to_point(float xx) {
		if (xx > curRoom.x + curRoom.sizex - sprite.getWidth()) {

			System.out.println("o: " + xx + sprite);
			xx = curRoom.x + sprite.getWidth();
		}
		if (xx < curRoom.x) {
			System.out.println("g: " + xx);
			xx = curRoom.x;
		}
		System.out.println("ASDSD: " + xx);
		target_x = xx;

	}

	public void move(int delta) {
		switch (cur_path.charAt(0)) {
		case 'r':
			direction = 1;
			x += speed * (delta / 1000f);
			// System.out.printf("moving right.. ");
			if (x >= curRoom.x + curRoom.sizex) {
				cur_path = cur_path.substring(1);
				moveToRoom(curRoom.right, 0);
			}
			break;
		case 'l':
			direction = -1;
			x -= speed * (delta / 1000f);
			// System.out.printf("moving right.. ");
			if (x + sprite.getWidth() <= curRoom.x) {
				cur_path = cur_path.substring(1);
				moveToRoom(curRoom.left, 1);
			}
			break;
		case 'u':
			cur_path = cur_path.substring(1);
			moveToRoom(curRoom.up, 0);
			break;
		case 'd':
			cur_path = cur_path.substring(1);
			moveToRoom(curRoom.down, 0);
			break;

		}
	}

	public void roam(int delta) {

		if (target_x != x) {
			// System.out.println(target_x + " " + x);
			if (x > target_x) {
				x -= speed / 2 * (delta / 1000f);
				if (x < target_x) {
					anim_idle();
					x = target_x;
				}
			} else {
				if (x < target_x) {
					x += speed / 2 * (delta / 1000f);
					if (x > target_x) {
						anim_idle();
						x = target_x;
					}
				}
			}
		} 
	}

	public void setSpriteLoad(String spr_name) throws SlickException {
		// later make this a list accessible by the class;
		System.out.println("loading imags");
		// this.sprite = head.sprite;
		this.hitbox = new Rectangle(x, y, sprite.getWidth(), sprite.getHeight());
	}

	public void update(int delta) {
		if (cur_path.equals("") == false) {
			move(delta);
		} else {
			roam(delta);
		}
		if (hp <= 0) {
			dead = true;
			destroy = true;
		}

	}

	public void draw(Graphics surface) {

	}

}