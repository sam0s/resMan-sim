import java.lang.reflect.Method;

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
	public int[] origin = {16,32};
	
	
	public Method fgetMethod(String methodName, Class... args) throws NoSuchMethodException, SecurityException {
		return this.getClass().getMethod(methodName, args);
	}

	public Entity(String name, int x, int y) {
		this.name = name;
		this.x = x;
		this.y = y;
		curRoom = new Room(x-5,y);
		

	}
	
	public void set_name(String name) {
		this.name = name;
	}
	
	public void setRoom(Room r){
		curRoom = r;
		x=r.x;
		y=r.y+r.sizey-sprite.getHeight()*sizey;
	}

	public void setSize(float size) {
		this.sizey = size;
	}

	public void downSize() {
		setSize(sizey - 1);
	}

	public void upSize() {
		setSize(sizey + 1);
	}

	public boolean isClicked(Input i) {

		return ((i.getMouseX() > x && i.getMouseX() < x + hitbox.getWidth()) && (i.getMouseY() > y && i.getMouseY() < y + hitbox.getHeight()));
	}

	public void roam(int delta) {
		if (roamDir == 1) {
			x += 40 * (delta / 1000f);
			if (x+sprite.getWidth()*sizex >= curRoom.x+curRoom.sizex) {
				roamDir = 0;
				sizex=-1;
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
	}

	public void draw(Graphics surface) {
		sprite.draw(x-sizex*origin[0]+origin[0], y-sizey*origin[1]+origin[1], sprite.getWidth() * sizex, sprite.getHeight() * sizey);
		surface.setColor(Color.red);
		surface.setLineWidth(2);
		surface.drawRect(x,y, hitbox.getWidth(), hitbox.getHeight());
	}

}
