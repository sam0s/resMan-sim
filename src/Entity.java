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
//	/public Image sprite;
	public Animation sprite;
	public int level;
	public float size = 1;
	public int x;
	public int y;
	public Rectangle hitbox;
	

	
	public Method fgetMethod(String methodName, Class... args) throws NoSuchMethodException, SecurityException {
		return this.getClass().getMethod(methodName, args);
	}
	
	public Entity(String name, int x, int y) {
		this.name = name;
		this.x = x;
		this.y = y;

	}
	
	public void set_name(String name) {
		this.name = name;
	}
	
	public void setSize(float size){
		this.size = size;
	}
	
	public void downSize(){
		setSize(size-1);
	}
	
	public void upSize(){
		setSize(size+1);
	}
	
	public boolean isClicked(Input i){
	    
	    return ( (i.getMouseX() > x && i.getMouseX() < x + hitbox.getWidth()) && (i.getMouseY() > y && i.getMouseY() < y + hitbox.getHeight()) );
	}
	
	public void setSpriteLoad(SpriteSheet spr) throws SlickException{
		// later make this a list accessible by the class;
		sprite = new Animation(spr,spr.getHorizontalCount());
		sprite.setSpeed(0.5f);
		hitbox = new Rectangle(x,y,sprite.getWidth(),sprite.getHeight());
		hitbox.setBounds(hitbox);
	}
	
	public void draw(Graphics surface){
		sprite.draw(x,y,sprite.getWidth()*size	,sprite.getHeight()*size);
		surface.setColor(Color.red);
		surface.drawRect(x, y, hitbox.getWidth(), hitbox.getHeight());
	}
	
}
