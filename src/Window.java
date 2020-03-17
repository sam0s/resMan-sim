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

	Container titlebar;
	String title;
	
	Font win_font;
	
	boolean moving;
	Color title_color;
	Button hidebutton;
	StateGame s;

	float moving_cursor_x_offset;
	float moving_cursor_y_offset;
	
	

	@Override
	public void add_container(Container... containerz) {
		for (Container new_container : containerz) {
			containers.addElement(new_container);
			inset_cont(new_container, 
					new_container.relx + padx, new_container.relx + titlebar.sizey + pady);
		}
	}

	@Override
	public void hide() {
		hidden = true;
		moving = false;
	}

	public Window(int sizex, int sizey, int x, int y, int padx, int pady, double weight, String title, StateGame s) throws NoSuchMethodException, SecurityException {
		super(sizex, sizey, x, y, padx, pady, weight);
		this.moving_cursor_x_offset = 0;
		this.moving_cursor_y_offset = 0;
		
		win_font = s.f_24;
		
		titlebar = new Container(this.sizex, 
				win_font.getHeight(title), 
				this.x, 
				this.y, 
				2, 2, 
				weight);
		
		hidebutton = new Button(30, titlebar.sizey, titlebar.sizex-30-(int)weight, (int)-weight, weight, "H", win_font, fgetMethod("hide"), this);
		titlebar.add_container(hidebutton);
		moving = false;
		
		this.title = title;
		this.title_color = outer;
		this.is_focused = false;
		this.s = s;
	}

	public void update(Input i, int mx, int my, int delta) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		super.update(i, mx, my, delta);
		if (!hidden) {
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
				} else if (Math.abs(Game.MENU_BAR_HEIGHT - (y + yoffset)) < 8) {
					yoffset = Game.MENU_BAR_HEIGHT - y;
				}
				move(xoffset, yoffset);
				titlebar.move(xoffset, yoffset);

			}

			if (mx > x && mx < x + sizex) {
				if (my > y && my < y + titlebar.sizey) {
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
				c.update(i, mx, my, delta);
			}
			titlebar.is_focused = is_focused;
			titlebar.update(i, mx, my, delta);

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

	public void set_title(String text) {
		int size_lim = sizex - (int) weight * 2 - hidebutton.sizex - (int) hidebutton.weight * 2;
		if (win_font.getWidth(text) > size_lim) {
			int i = 0;
			for (i = text.length() - 1; i >= 0; i--) {
				if (win_font.getWidth(text.substring(0, i)) < size_lim - win_font.getWidth("...")) {
					title = text.substring(0, i).concat("...");
					return;
				}
			}
		} else {
			title = text;
		}
	}

	public void draw(Graphics surface) throws SlickException {
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
			surface.setFont(win_font);
			surface.setColor(outer);
			surface.drawString(title, x + padx, y + pady/2);
		}
	}
}
