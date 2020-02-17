import org.newdawn.slick.Graphics;
import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.Input;

public class Button extends Container
{
	Font f;
	String text;
	Color hi_color;
	boolean hi = false;

	public Button(int sizex, int sizey, int x, int y, Color c1, Color c2, double weight, String text, Font fnt)
	{
		super(sizex, sizey, x, y, c1, c2, weight);
		this.f = fnt;
		this.text = text;
		this.hi_color = new Color(inner.r, inner.g, inner.b, inner.a+5);

	}

	public void set_size(int sizex, int sizey)
	{
		this.sizex = sizex;
		this.sizey = sizey;
	}

	public void update(Graphics surface, int[] mouse)
	{
		hi = false;
		if (mouse[0] > x && mouse[0] < x + sizex)
		{
			if (mouse[1] > y && mouse[1] < y + sizey)
			{
				if (hi == false)
				{
					hi = true;
				}
			}
		}
		draw(surface);

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