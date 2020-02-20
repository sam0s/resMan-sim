import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.Graphics;

public class Label extends Container {

	public boolean borders;
	public String text;
	public Font f;

	public Label(float x, float y, int padx, int pady, Color inner, Color outer, double weight, String text, Font f) {
		super(f.getWidth(text) + padx* 2, f.getHeight(text) + pady*2, x, y, padx, pady, inner, outer, weight);

		this.text = text;
		this.borders = borders;
		this.f = f;
	}
	
	

	public void set_text(String text) {
		this.text = text;
	}
	
	public void update() {
		set_size(f.getWidth(text) + padx*2, f.getHeight(text) + pady*2);	
	}

	public void draw(Graphics surface) {
		surface.setLineWidth(weight);
		surface.setColor(inner);
		surface.fillRect(x, y, sizex, sizey);
		surface.setColor(outer);
		surface.drawRect(x, y, sizex, sizey);
		f.drawString(x + padx, y + pady, text, outer);
	}

}
