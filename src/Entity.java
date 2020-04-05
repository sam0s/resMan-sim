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
	String cur_path = "";
	int hp;
	int hp_max;
	int speed = 100;
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
			x = r.x + r.sizex - this.sizex;
		}
		y = r.y + r.sizey - sprite.getHeight() * sizey;

	}

	public void move(int delta) {
		switch (cur_path.charAt(0)) {
		case 'r':
			x += speed * (delta / 1000f);
			// System.out.printf("moving right.. ");
			if (x >= curRoom.x + curRoom.sizex) {
				cur_path = cur_path.substring(1);
				moveToRoom(curRoom.right, 0);
			}
			break;
		case 'l':
			x -= speed * (delta / 1000f);
			// System.out.printf("moving right.. ");
			if (x <= curRoom.x) {
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
		if (roamDir == 1) {
			x += speed * (delta / 1000f);
			if (x + sprite.getWidth() * sizex >= curRoom.x + curRoom.sizex) {
				roamDir = 0;
				sizex = -1;
				return;
			}
		} else {
			x -= speed * (delta / 1000f);
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
		if (cur_path.equals("")) {
			roam(delta);
		} else {
			move(delta);
		}
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