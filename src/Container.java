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
    public TextBox textboxes[];
    public Button buttons[];
    public boolean hidden = false;
    float relx;
    float rely;

    //Use to get method reference to a method of this class!
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
    	
    	for (TextBox t: textboxes) {
    		t.set_pos(x, y);
    	}
    	for (Button b : buttons) {
    		b.set_pos(x, y);

    	}
    	for (Container c : containers) {
    		c.set_pos(x, y);
    	}
    	
    }
    
    public void move(float xoffset, float yoffset) {
    	this.x += xoffset;
    	this.y += yoffset;
    	
    	for (TextBox t: textboxes) {
    		t.move(xoffset, yoffset);
    	}
    	for (Button b : buttons) {
    		b.move(xoffset, yoffset);
    	}
    	for (Container c : containers) {
    		c.move(xoffset, yoffset);
    	}
    }
    
    //When making a container, relx, rely are set to original x,y, in the case that this is a sub-container.
    public Container(int sizex, int sizey, float x, float y, Color inner, Color outer, double weight) {
	this.sizex = sizex;
	this.sizey = sizey;
	this.x = x;
	this.y = y;
	this.inner = inner;
	this.outer = outer;
	this.weight = (float) weight;
	this.buttons = new Button[] {};
	this.textboxes = new TextBox[] {};
	this.containers = new Container[] {};
	this.relx=x;
	this.rely=y;
    }

    public void add_button(Button new_button) {
	this.buttons = Arrays.copyOf(this.buttons, buttons.length+1);
	this.buttons[buttons.length-1] = new_button;
	new_button.x = this.x + new_button.relx;
	new_button.y = this.y + new_button.rely;
    }
    
    public void add_conatiner(Container new_container) {
    this.containers = Arrays.copyOf(this.containers, this.containers.length+1);
    this.containers[containers.length-1] = new_container;
    new_container.x = this.x + new_container.relx;
    new_container.y = this.y + new_container.rely;
    }
    
    public void add_textbox(TextBox new_tb) {
    	this.textboxes = Arrays.copyOf(this.textboxes, this.textboxes.length+1);
    	this.textboxes[textboxes.length-1] = new_tb;
    	new_tb.x = this.x + new_tb.relx;
    	System.out.printf("this is %f\n", new_tb.x);
    	new_tb.y = this.y + new_tb.rely;
    }
    
    public void update(Input i) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException{
	for (Button b : buttons) {
	    b.update(i);
	    b.x = x + b.relx;
	    b.y = y + b.rely;
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
	
	for (TextBox t : textboxes) {
		t.draw(surface);
		System.out.printf("%f %f\n", t.x, t.y);
	}
	
	for (Container c : containers) {
		c.draw(surface);
	}
	
	for (Button b : buttons) {
		b.draw(surface);
	    }
    }

}