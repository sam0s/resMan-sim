import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.Font;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

import org.newdawn.slick.Color;

public class Window extends Container {

    public Container titlebar;
    public int titlebar_height;
    public String title;
    public Font f;
    boolean moving;
    Color title_color;
    public boolean hidden = false;
    Button hidebutton;

    float moving_cursor_x_offset;
    float moving_cursor_y_offset;

    public void toggle_hide() {
	hidden = !hidden;
    }


    public Window(int sizex, int sizey, int x, int y, Color inner, Color outer, double weight, Font f, String title) throws NoSuchMethodException, SecurityException {
	super(sizex, sizey, x, y, inner, outer, weight);
	this.moving_cursor_x_offset = 0;
	this.moving_cursor_y_offset = 0;
	titlebar_height = 20;
	titlebar = new Container(this.sizex, titlebar_height, this.x, this.y, outer, inner, 2.5);
	hidebutton = new Button(30, titlebar_height - 2, 0, 0, Color.white, Color.red, 2.5, "X", f, getMethod("toggle_hide"), this);
	moving = false;
	this.title = title;
	this.f = f;
	this.title_color = new Color(inner.getRed(), inner.getGreen(), inner.getBlue());
	add_button(hidebutton, sizex - 32, -titlebar_height);
    }

    public void add_button(Button new_button, int relx, int rely) {
	super.add_button(new_button,relx,rely);
	new_button.y = new_button.y + this.titlebar_height;
    }

    public void update(Input i, int delta) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
	if (!hidden) {
	    int mx = i.getMouseX();
	    int my = i.getMouseY();

	    if (!i.isMouseButtonDown(0)) {
		moving = false;
	    }

	    for (Button b : buttons) {
		b.update(i);
	    }

	    if (moving) {
		float new_point[] = travel_to_point(this.x, this.y, mx - this.moving_cursor_x_offset, my - this.moving_cursor_y_offset, 20, delta);
		float xoffset = new_point[0] - this.x;
		float yoffset = new_point[1] - this.y;
		this.x += xoffset;
		this.y += yoffset;

		titlebar.x += xoffset;
		titlebar.y += yoffset;

		for (Button b: buttons) {
		    b.x += xoffset;
		    b.y += yoffset;
		}
	    }

	    if (mx > x && mx < x + this.sizex) {
		if (my > y && my < y + this.titlebar_height) {
		    if (!moving) {
			moving = i.isMouseButtonDown(0);
			if (moving) {
			    moving_cursor_x_offset = mx - this.x;
			    moving_cursor_y_offset = my - this.y;
			}
		    }
		}
	    }
	}
    }

    public void draw(Graphics surface) {
	if (!hidden) {
	    surface.setLineWidth(weight);
	    surface.setColor(inner);
	    surface.fillRect(x, y, sizex, sizey);
	    surface.setColor(outer);
	    surface.drawRect(x, y, sizex, sizey);
	    titlebar.draw(surface);

	    for (Button b:buttons) {
		b.draw(surface);
	    }

	    surface.setFont(f);
	    surface.setColor(title_color);
	    surface.drawString(title, x + 2, y + 2);
	}
    }
}
