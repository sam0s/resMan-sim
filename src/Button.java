import org.newdawn.slick.Graphics;
import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.Input;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Button extends Container
{
	Font f;
	String text;
	Color hi_color;
	boolean hi = false;
	boolean triggered;
	Method func;
	Object gc;
	
	
	public Button(int sizex, int sizey, int x, int y, Color inner, Color outer, double weight, String text, Font fnt,Method func,Object gc)
	{
		super(sizex, sizey, x, y, inner, outer, weight);
		this.f = fnt;
		this.text = text;
		this.hi_color = new Color(inner.getRed(),inner.getGreen(),inner.getBlue(),inner.getAlpha()+30);
		this.func = func;
		this.gc=gc;

	}

	public void update(Input i) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException
	{
		hi = false;
		int mx = i.getMouseX();
		int my = i.getMouseY();
		if (mx > x && mx < x + sizex)
		{
			if (my > y && my < y + sizey)
			{
				if (i.isMousePressed(0))
				{
					func.invoke(gc);
				}
				
				if (hi == false)
				{
					hi = true;
				}
			}
		}
	}

	public void draw(Graphics surface)
	{
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