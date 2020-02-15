import org.newdawn.slick.Graphics;
import org.newdawn.slick.Color;
import org.newdawn.slick.Font;

public class Button extends Container {
	Font f;
	String text;
	
	public Button(int sizex,int sizey,int x,int y,Color c1,Color c2,double weight,String text,Font fnt){
		super(sizex,sizey,x,y,c1,c2,weight);
		this.f=fnt;
		this.text=text;
	}
	
	public void set_size(int sizex, int sizey) {
		this.sizex = sizex;
		this.sizey = sizey;
	}
	
	public void draw(Graphics surface){
		super.draw(surface);
		surface.setFont(f);
		surface.setColor(outer);
		surface.drawString(text, (x+sizex/2)-f.getWidth(text)/2, (y+sizey/2)-f.getHeight(text)/2);
		
	}

}