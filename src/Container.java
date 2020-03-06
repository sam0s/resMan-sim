import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

import org.newdawn.slick.Input;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Color;
import org.newdawn.slick.SlickException;

public class Container {
	int sizex;
	int sizey;
	float x;
	float y;
	int padx;
	int pady;
	Color inner;
	Color outer;
	Color inner_f;
	Color outer_f;
	float weight;
	public Container containers[];
	public boolean hidden = false;
	float relx;
	float rely;
	Boolean destroy;
	Boolean is_focused = false;

	// Use to get method reference to a method of this class!
	public Method fgetMethod(String methodName, Class... args) throws NoSuchMethodException, SecurityException {
		return this.getClass().getMethod(methodName, args);
	}

	public void hide() {
		hidden = true;
	}

	public void show() {
		hidden = false;
	}

	public void set_pos(float x, float y) {
		this.x = x + relx;
		this.y = y + rely;

		for (Container c : containers) {
			c.set_pos(x, y);
		}

	}

	public void move(float xoffset, float yoffset) {
		x += xoffset;
		y += yoffset;

		for (Container c : containers) {
			c.move(xoffset, yoffset);
		}
	}

	// When making a container, relx, rely are set to original x,y, in the case
	// that this is a sub-container.
	public Container(int sizex, int sizey, float x, float y, int padx, int pady, double weight) {
		this.sizex = sizex;
		this.sizey = sizey;
		this.x = x;
		this.y = y;
		this.padx = padx;
		this.pady = pady;
		this.inner = Game.win_inner;
		this.outer = Game.win_outer;
		this.inner_f = Game.win_inner_f;
		this.outer_f = Game.win_outer_f;
		this.weight = (float) weight;
		this.containers = new Container[] {};
		this.relx = x;
		this.rely = y;
		this.destroy = false;
	}

	public void setTheme(Color in_f, Color out_f, Color in, Color out) {
		inner_f = in_f;
		outer_f = out_f;
		inner = in;
		outer = out;
	}

	public void clear_containers() {
		containers = new Container[] {};
	}

	public void add_container(Container... containerz) {
		for (Container new_container : containerz) {
			containers = Arrays.copyOf(containers, containers.length + 1);
			containers[containers.length - 1] = new_container;
			new_container.x = x + new_container.relx + padx;
			new_container.y = y + new_container.rely + pady;
		}
	}

	public void update(Input i, int mx, int my, int delta) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		for (Container c : containers) {
			c.is_focused = is_focused;
			c.update(i, mx, my, delta);
		}
	}

	public void set_size(int sizex, int sizey) {
		this.sizex = sizex;
		this.sizey = sizey;
	}

	public void draw(Graphics surface) throws SlickException {
		surface.setLineWidth(weight);
		surface.setColor(is_focused ? inner_f : inner);
		surface.fillRect(x, y, sizex, sizey);
		surface.setColor(is_focused ? outer_f : outer);
		surface.drawRect(x, y, sizex, sizey);

		for (Container c : containers) {
			c.draw(surface);
		}
	}

}