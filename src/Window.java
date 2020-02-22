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
	Button hidebutton;

	float moving_cursor_x_offset;
	float moving_cursor_y_offset;

	@Override
	public void add_container(Container... containerz) {
		for (Container new_container : containerz) {
			containers = Arrays.copyOf(containers, containers.length + 1);
			containers[containers.length - 1] = new_container;
			new_container.x = x + new_container.relx + padx;
			new_container.y = y + new_container.rely + this.titlebar_height + pady;
		}
	}

	@Override
	public void hide() {
		hidden = true;
		moving = false;
	}

	public Window(int sizex, int sizey, int x, int y, int padx, int pady, double weight, Font f, String title) throws NoSuchMethodException, SecurityException {
		super(sizex, sizey, x, y, padx, pady, weight);
		this.moving_cursor_x_offset = 0;
		this.moving_cursor_y_offset = 0;
		titlebar_height = f.getHeight(title) + 2;
		titlebar = new Container(this.sizex, titlebar_height, this.x, this.y, 2, 2, weight);
		hidebutton = new Button(30, titlebar_height, sizex - 30-padx, -titlebar.sizey-pady, weight, "H", f, fgetMethod("hide"), this);
		moving = false;
		this.title = title;
		this.f = f;
		this.title_color = outer;
		this.add_container(hidebutton);
	}

	public void update(Input i, int delta) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		if (!hidden) {
			int mx = i.getMouseX();
			int my = i.getMouseY();
		
			if (!i.isMouseButtonDown(0)) {
				moving = false;
			}

			if (moving) {
				float xoffset = mx - moving_cursor_x_offset - x;
				float yoffset = my - moving_cursor_y_offset - y;
				if ( Math.abs(Game.WIDTH - (x + sizex + xoffset)) < 8) {
					xoffset = Game.WIDTH - x - sizex - (int)weight + 1;
				} else if (Math.abs(x + xoffset) < 8) {
					xoffset = -x + (int)weight -1;
				}
				if (Math.abs(Game.HEIGHT - (y + sizey + yoffset)) < 8) {
					yoffset = Game.HEIGHT - y - sizey - (int)weight;
				} else if (Math.abs(y + yoffset) < 8) {
					yoffset = -y;
				}
				move(xoffset, yoffset);
				titlebar.move(xoffset, yoffset);

			}
			
			
			for (Container c : containers) {
				c.update(i);
			}

			if (mx > x && mx < x + sizex) {
				if (my > y && my < y + titlebar_height) {
					if (!moving && i.isMousePressed(0)) {
						moving = i.isMouseButtonDown(0);
						if (moving) {
							moving_cursor_x_offset = mx - x;
							moving_cursor_y_offset = my - y;
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
			for (Container c : containers) {
				c.draw(surface);
			}
			surface.setFont(f);
			surface.setColor(title_color);
			surface.drawString(title, x + 2, y + 2);
		}
	}
}
