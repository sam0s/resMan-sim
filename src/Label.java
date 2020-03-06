import java.lang.reflect.InvocationTargetException;

import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;

public class Label extends Container {

	public boolean borders;
	public String text;
	public Font f;

	public Label(float x, float y, int padx, int pady, double weight, String text, Font f) {
		super(f.getWidth(text) + padx * 2, f.getHeight(text) + pady * 2, x, y, padx, pady, weight);

		this.text = text;
		this.f = f;
	}

	public void set_text(String text) {
		this.text = text;
	}

	public void update(Input i, int mx, int my, int delta) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		super.update(i, mx, my, delta);
		set_size(f.getWidth(text) + padx * 2, f.getHeight(text) + pady * 2);
		for (Container c : containers) {
			c.update(i, mx, my, delta);
		}
	}

	public void draw(Graphics surface) {
		surface.setLineWidth(weight);
		surface.setColor(is_focused ? inner_f : inner);
		surface.fillRect(x, y, sizex, sizey);
		surface.setColor(is_focused ? outer_f : outer);
		surface.drawRect(x, y, sizex, sizey);
		f.drawString(x + padx, y + pady, text, is_focused ? outer_f : outer);
	}

}
