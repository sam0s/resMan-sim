import org.newdawn.slick.Graphics;
import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.Input;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Button extends Container {
	Font f;
	String text;
	Color hi_color;
	boolean hi = false;
	boolean triggered;
	Method func;
	Object gc;
	Object args[];

	public Button(int sizex, int sizey, float x, float y, double weight, String text, Font fnt, Method func, Object gc) {
		super(sizex, sizey, x, y, 0,0,weight);
		this.f = fnt;
		this.text = text;
		this.hi_color = new Color(inner.getRed() + 40, inner.getGreen() + 40, inner.getBlue() + 40, inner.getAlpha() + 40);
		this.func = func;
		this.gc = gc;
		this.args = null;

	}
	
	public void set_func(Method func, Object obj) {
		this.func = func;
		this.gc = obj;
	}
	
	public void set_args(Object...args) {
		this.args = args;
	}

	public void update(Input i, int delta) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		super.update(i, delta);
		hi = false;
		if (i.getMouseX() >= x && i.getMouseX() <= x + sizex  && i.getMouseY() >= y && i.getMouseY() <= y + sizey) {
			if (i.isMousePressed(0)) {
				func.invoke(gc, this.args);
			}

			if (hi == false) {
				hi = true;
			}
		}
	}

	public void draw(Graphics surface) {
		surface.setLineWidth(weight);
		surface.setColor((hi) ? hi_color : inner);
		surface.fillRect(x, y, sizex, sizey);
		surface.setColor(outer);
		surface.drawRect(x, y, sizex, sizey);
		surface.setFont(f);
		surface.setColor(outer);
		surface.drawString(text, (x + sizex / 2) - f.getWidth(text) / 2, (y + sizey / 2) - f.getHeight(text) / 2);

	}

}