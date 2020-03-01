import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.Font;
import org.newdawn.slick.SlickException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

import org.newdawn.slick.Color;
import org.newdawn.slick.geom.Rectangle;

public class Window extends Container {

	public Container titlebar;
	public int titlebar_height;
	public String title;
	public Font f;
	boolean moving;
	Color title_color;
	Button hidebutton;
	StateGame s;

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

	public Window(int sizex, int sizey, int x, int y, int padx, int pady, double weight, Font f, String title, StateGame s) throws NoSuchMethodException, SecurityException {
		super(sizex, sizey, x, y, padx, pady, weight);
		this.moving_cursor_x_offset = 0;
		this.moving_cursor_y_offset = 0;
		titlebar_height = f.getHeight(title) + 2;
		titlebar = new Container(this.sizex, titlebar_height, this.x, this.y, 2, 2, weight);
		hidebutton = new Button(30, titlebar_height, sizex - 30 - padx, -titlebar.sizey - pady, weight, "H", f, fgetMethod("hide"), this);
		moving = false;
		this.title = title;
		this.f = f;
		this.title_color = outer;
		this.add_container(hidebutton);
		this.is_focused = false;
		this.s = s;
	}

	public void update(Input i, int delta) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		super.update(i, delta);
		if (!hidden) {
			int mx = i.getMouseX();
			int my = i.getMouseY();

			if (!i.isMouseButtonDown(0)) {
				moving = false;
			}

			if (moving) {
				float xoffset = mx - moving_cursor_x_offset - x;
				float yoffset = my - moving_cursor_y_offset - y;
				if (Math.abs(Game.WIDTH - (x + sizex + xoffset)) < 8) {
					xoffset = Game.WIDTH - x - sizex - (int) weight + 1;
				} else if (Math.abs(x + xoffset) < 8) {
					xoffset = -x + (int) weight - 1;
				}
				if (Math.abs(Game.HEIGHT - (y + sizey + yoffset)) < 8) {
					yoffset = Game.HEIGHT - y - sizey - (int) weight;
				} else if (Math.abs(y + yoffset) < 8) {
					yoffset = -y;
				}
				move(xoffset, yoffset);
				titlebar.move(xoffset, yoffset);

			}
			
			if (mx > x && mx < x + sizex) {
				if (my > y && my < y + titlebar_height) {
					if (!moving && i.isMousePressed(0)) {
						moving = i.isMouseButtonDown(0);
						
						if (moving) {
							s.set_window_focus(this);
							moving_cursor_x_offset = mx - x;
							moving_cursor_y_offset = my - y;
						}
					}
				}
			}
			
			for (Container c : containers) {
				c.is_focused = is_focused;
				c.update(i, delta);
			}
			titlebar.is_focused = is_focused;
			titlebar.update(i, delta);

			if (mx > x && mx < x + sizex) {
				if (my > y && my < y + sizey) {
					if (is_focused) {
						if (i.isMousePressed(0)) {
							System.out.printf("yeah buddy, it's me, the focused window. You can stop clicking me now ya clutz.\n");
						}
					} else {
						if (i.isMousePressed(0)) {
							s.set_window_focus(this);
						}
					}
				}
			}

		}
	}

	public void draw(Graphics surface) throws SlickException {
		if (!hidden) {
			surface.setLineWidth(weight);
			surface.setColor(is_focused ? inner_f : inner);
			surface.fillRect(x, y, sizex, sizey);
			surface.setColor(is_focused ? outer_f : outer);
			surface.drawRect(x, y, sizex, sizey);
			titlebar.draw(surface);
			for (Container c : containers) {
				c.draw(surface);
			}
			surface.setFont(f);
			surface.setColor(is_focused ? outer_f : outer);
			surface.drawString(title, x + 2, y + 2);
		}
	}
}
