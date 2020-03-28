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
		String q = "";
		int vert = 0;
		if (start.level != r.level) {
			vert = r.level - start.level;

			Room tL = start;
			Room tR = start;
			System.out.println(tR.name);
			while (vert != 0) {
				while (tL.name.equals("Elevator") != true && tR.name.equals("Elevator") != true) {
					if (tL.left != null) {
						tL = tL.left;
					}

					if (tR.right != null) {      
						tR = tR.right;
					}
				}
				if (tR.name.equals("Elevator")) {
					if (vert < 0) {
						tR = tR.up;
					} else {
						tR = tR.down;
					}
					q += pathHor(start, tR);
					tL = tR;
				} else {
					if (vert < 0) {
						tL = tL.up;
					} else {
						tL = tL.down;
					}
					q += pathHor(start, tL);
					tR = tL;
				}

				if (vert > 0) {
					vert--;
					q += "d ";
				}
				if (vert < 0) {
					vert++;
					q += "u ";
				}
			}
			q += pathHor(tR, r);
		} else {
			q += pathHor(start, r);
		}
		System.out.println(q);
	}

	public String pathHor(Room start, Room r) {
		String ret = "";
		Room testL = start;
		Room testR = start;
		Vector<String> q = new Vector<String>();

		int steps = 0;
		while (testL != r && testR != r) {
			steps++;
			if (testL.left != null) {
				testL = testL.left;
			}

			if (testR.right != null) {
				testR = testR.right;
			}
		}
		if (testR == r) {
			for (int i = 0; i < steps; i++) {
				ret += "r ";
			}
		} else {
			for (int i = 0; i < steps; i++) {
				ret += "l ";
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
