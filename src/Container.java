import org.newdawn.slick.Graphics;
import org.newdawn.slick.Color;

public class Container {
	int sizex;
	int sizey;
	int x;
	int y;
	int color;
	public Container(int sizex,int sizey,int x,int y){
		this.sizex=sizex;
		this.sizey=sizey;
		this.x=x;
		this.y=y;
		//this.color=color;
	}
	public void draw(Graphics surface){
		//surface.fillRect(arg0, arg1, arg2, arg3, arg4, arg5, arg6);
		surface.setColor(Color.orange);
		surface.drawRect(x, y, sizex, sizey);
		
	}

}
