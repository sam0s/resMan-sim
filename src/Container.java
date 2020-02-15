import org.newdawn.slick.Graphics;
import org.newdawn.slick.Color;

public class Container {
	int sizex;
	int sizey;
	float x;
	float y;
	Color inner;
	Color outer;
	float weight;
	
	public Container(int sizex,int sizey,int x,int y,Color c1,Color c2,double weight){
		this.sizex=sizex;
		this.sizey=sizey;
		this.x=x;
		this.y=y;
		this.inner = c1;
		this.outer = c2;
		this.weight = (float) weight;
	}
	
	public void set_size(int sizex, int sizey) {
		this.sizex = sizex;
		this.sizey = sizey;
	}
	
	public void draw(Graphics surface){
		surface.setLineWidth(weight);
		surface.setColor(inner);
		surface.fillRect(x, y, sizex, sizey);
		surface.setColor(outer);
		surface.drawRect(x, y, sizex, sizey);
		
	}

}