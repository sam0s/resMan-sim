import org.newdawn.slick.Graphics;
import org.newdawn.slick.Color;

public class Container {
	int sizex;
	int sizey;
	float x;
	float y;
	int color;
	
	Color outer = new Color(255,0,0);
	Color inner = new Color(255,255,255,50);
	
	public Container(int sizex,int sizey,int x,int y){
		this.sizex=sizex;
		this.sizey=sizey;
		this.x=x;
		this.y=y;
		//this.color=color;
	}
	
	public void set_size(int sizex, int sizey) {
		this.sizex = sizex;
		this.sizey = sizey;
	}
	
	public void draw(Graphics surface){
		surface.setColor(inner);
		surface.fillRect(x, y, sizex, sizey);
		surface.setColor(outer);
		surface.drawRect(x, y, sizex, sizey);
		
	}

}