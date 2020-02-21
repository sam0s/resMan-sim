import java.util.Arrays;

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
	
	public Room(float x, float y){
		this.x=x;
		this.y=y;
		sizex=300;
		sizey=100;
		name = "TestRoom";
		hitbox = new Rectangle(x, y, sizex, sizey);
		hitbox.setBounds(hitbox);
		this.ents = new Entity[] {};
	}
	
	public void add_entity(Entity e){
		e.setRoom(this);
		ents = Arrays.copyOf(ents, ents.length + 1);
		ents[ents.length - 1] = e;
	}
	
	public void update(int delta){
		for(Entity e:ents){
			e.update(delta);
		}
	}
	
	
	public void draw(Graphics surface){
		for(Entity e:ents){
			e.draw(surface);
		}
		surface.drawRect(x,y, hitbox.getWidth(), hitbox.getHeight());
		
	}
	

}
