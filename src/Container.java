import org.newdawn.slick.Graphics;
import org.newdawn.slick.Color;

public class Container {
	int sizex;
	int sizey;
	int color;
	public Container(int sizex,int sizey){
		this.sizex=sizex;
		this.sizey=sizey;
		//this.color=color;
	}
	public void draw(int x,int y,Graphics surface){
		//surface.fillRect(arg0, arg1, arg2, arg3, arg4, arg5, arg6);
		surface.setColor(Color.orange);
		surface.drawRect(x, y, sizex, sizey);
		
	}

}
