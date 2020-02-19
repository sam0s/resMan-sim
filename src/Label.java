import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.Graphics;

public class Label extends Container {

	public int padding;
	public boolean borders;
	public String text;
	public Font f;

	public Label(float x, float y, int padding, boolean borders, Color inner, Color outer, double weight, String text, Font f) {
		super(f.getWidth(text) + padding * 2, f.getHeight(text) + padding * 2, x, y, inner, outer, weight);

		this.text = text;
		this.padding = padding;
		this.borders = borders;
	}
	
	

	public void set_text(String text) {
		this.text = text;
	}

	public void update() {
	}

	public void draw(Graphics surface) {
		surface.setLineWidth(weight);
		surface.setColor(inner);
		surface.fillRect(x, y, sizex, sizey);
		surface.setColor(outer);
		if (this.borders) {
			surface.drawRect(x, y, sizex, sizey);
		}
		surface.drawString(text, this.x + padding, this.y + padding);
	}

}
