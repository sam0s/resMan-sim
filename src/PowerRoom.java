import java.lang.reflect.InvocationTargetException;

import org.newdawn.slick.Input;

public class PowerRoom extends ProductionRoom {
	public PowerRoom(float x, float y, StateGame s) throws NoSuchMethodException, SecurityException {
		super(x, y, "power_room", s);
		this.prod_rate = 1; // MW/hr
		this.level = 0;
		
		s.resources.power_prod += prod_rate;
	}
	
	public void update(Input i, int mx, int my, int delta) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		super.update(i, mx, my, delta);
		
		/* power has to be handled differently */
		
	}
}
