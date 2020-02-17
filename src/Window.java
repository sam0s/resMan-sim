import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;

import java.util.Arrays;

import org.newdawn.slick.Color;

public class Window extends Container {
	
	public Button buttons[];
	public int n_buttons;
	
	public Container titlebar;
	public int titlebar_height;
	
	boolean moving;
	
	public Window(int sizex, int sizey, int x, int y, Color inner, Color outer, double weight) {
		super(sizex, sizey, x, y, inner, outer, weight);
		
		this.buttons = new Button[] {};
		this.n_buttons = 0;
		titlebar_height = 20;
		titlebar = new Container(this.sizex, this.titlebar_height, this.x, this.y, inner, outer, 2.5);
		moving = false;
	}
	
	public void add_button(Button new_button, int relx, int rely) {
		this.buttons = Arrays.copyOf(this.buttons, n_buttons+1);
		this.buttons[n_buttons] = new_button;
		new_button.x = this.x + relx;
		new_button.y = this.y + rely + this.titlebar_height;
		n_buttons++;
	}
	
	public float[] travel_to_point(float curx, float cury, float destx, float desty, float speed, int delta)
	{
		/*
		 * delta is in miliseconds, so divide by 1000 to get seconds. multiply
		 * by speed (pixels/s) to get number of pixels we need to move. finally,
		 * multiply by curpos - destpos to make movement proportional to
		 * distance from target.
		 */

		curx -= speed * (delta / 1000f) * (curx - destx);
		cury -= speed * (delta / 1000f) * (cury - desty);

		return new float[]
		{ curx, cury };
	}
	
	public void update(Input i, int delta) {
		int mx = i.getMouseX();
		int my = i.getMouseY();
		
		if (!i.isMouseButtonDown(0)) {
			moving = false;
		}
		
		if (moving)
		{
			float new_point[] = travel_to_point(this.x, this.y, mx-(this.x - mx), my, 10, delta);
			float xoffset = new_point[0] - this.x;
			float yoffset = new_point[1] - this.y;
			this.x += xoffset;
			this.y += yoffset;
			
			titlebar.x += xoffset;
			titlebar.y += yoffset;
			
			for (int j = 0; j < n_buttons; j++) {
				buttons[j].x += xoffset;
				buttons[j].y += yoffset;
			}
		}
		
		if (mx > x && mx < x + this.sizex)
		{
			if (my > y && my < y + this.titlebar_height)
			{
				if (!moving) {
					moving = i.isMouseButtonDown(0);
				}
			}
		} 
	}
	
	public void draw(Graphics surface) {
		surface.setLineWidth(weight);
		surface.setColor(inner);
		surface.fillRect(x, y, sizex, sizey);
		surface.setColor(outer);
		surface.drawRect(x, y, sizex, sizey);
		
		titlebar.draw(surface);
		
		for (int i = 0; i < this.n_buttons; i++) {
			buttons[i].draw(surface);
		}
	}

}
