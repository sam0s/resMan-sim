import java.lang.reflect.Method;
import java.util.Arrays;

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
    public Button buttons[];

    //Use to get method reference to a method of this class!
    public Method getMethod(String methodName, Class... args) throws NoSuchMethodException, SecurityException {
	return this.getClass().getDeclaredMethod(methodName, args);
    }
    
    
    public Container(int sizex, int sizey, float x, float y, Color inner, Color outer, double weight) {
	this.sizex = sizex;
	this.sizey = sizey;
	this.x = x;
	this.y = y;
	this.inner = inner;
	this.outer = outer;
	this.weight = (float) weight;
	this.buttons = new Button[] {};
    }

    public void add_button(Button new_button, int relx, int rely) {
	this.buttons = Arrays.copyOf(this.buttons, buttons.length+1);
	this.buttons[buttons.length-1] = new_button;
	new_button.x = this.x + relx;
	new_button.y = this.y + rely;
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

	curx -= speed * (delta / 1000f) * (curx - destx);
	cury -= speed * (delta / 1000f) * (cury - desty);

	return new float[] { curx, cury };
    }

    public void draw(Graphics surface) {
	surface.setLineWidth(weight);
	surface.setColor(inner);
	surface.fillRect(x, y, sizex, sizey);
	surface.setColor(outer);
	surface.drawRect(x, y, sizex, sizey);
    }

}