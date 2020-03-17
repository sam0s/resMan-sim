import java.lang.reflect.InvocationTargetException;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;

public class HorzBarGraph extends Container {

	float percent;
	
	public HorzBarGraph(int sizex, int sizey, float x, float y, float weight) {
		super(sizex, sizey, x, y, 0, 0, weight);
		
		this.percent = 0;
	}
	
	public void set_percent(float percent) {
		this.percent = percent;
	}
	
	public void update(Input i, int mx, int my, int delta) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		super.update(i, mx, my, delta);
	}
	
	public void draw(Graphics surface) {
		surface.setLineWidth(weight);
		surface.setColor(outer);
		surface.fillRect(x,  y, (int) sizex * percent, sizey);
		surface.drawRect(x, y, sizex, sizey-(int)weight);
	}

}
