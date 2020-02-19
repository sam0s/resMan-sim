import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

import org.newdawn.slick.Input;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Color;

public class Container {
    int sizex;
    int sizey;
    float x;
    float y;
    Color inner;
    Color outer;
    float weight;
    public Container containers[];
    public boolean hidden = false;
    float relx;
    float rely;

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
	x = x + relx;
	y = y + rely;

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
    public Container(int sizex, int sizey, float x, float y, Color inner, Color outer, double weight) {
	this.sizex = sizex;
	this.sizey = sizey;
	this.x = x;
	this.y = y;
	this.inner = inner;
	this.outer = outer;
	this.weight = (float) weight;
	this.containers = new Container[] {};
	this.relx = x;
	this.rely = y;
    }

    public void add_container(Container... containerz) {
	for (Container new_container : containerz) {
	    containers = Arrays.copyOf(containers, containers.length + 1);
	    containers[containers.length - 1] = new_container;
	    new_container.x = x + new_container.relx;
	    new_container.y = y + new_container.rely;
	}
    }

    public void update(Input i) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
	for (Container c : containers) {
	    c.update(i);
	    c.x = x + c.relx;
	    c.y = y + c.rely;
	}
    }

    public void set_size(int sizex, int sizey) {
	this.sizex = sizex;
	this.sizey = sizey;
    }

    public float[] travel_to_point(float curx, float cury, float destx, float desty, float speed, int delta) {
	/*
	 * delta is in miliseconds, so divide by 1000 to get seconds. multiply
	 * by speed (pixels/s) to get number of pixels we need to move. finally,
	 * multiply by curpos - destpos to make movement proportional to
	 * distance from target.
	 */

	curx -= Math.ceil(speed * (delta / 1000f) * (curx - destx));
	cury -= Math.ceil(speed * (delta / 1000f) * (cury - desty));

	return new float[] { curx, cury };
    }

    public void draw(Graphics surface) {
	surface.setLineWidth(weight);
	surface.setColor(inner);
	surface.fillRect(x, y, sizex, sizey);
	surface.setColor(outer);
	surface.drawRect(x, y, sizex, sizey);

	for (Container c : containers) {
	    c.draw(surface);
	}
    }

}