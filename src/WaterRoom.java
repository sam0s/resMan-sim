import java.lang.reflect.InvocationTargetException;

import org.newdawn.slick.Input;

public class WaterRoom extends ProductionRoom {
	public WaterRoom(float x, float y, StateGame s) throws NoSuchMethodException, SecurityException {
		super(x, y, "water_room", s);
		this.prod_rate = 10; // liters/hr 
		this.level = 0;
		
		s.resources.power_use += 0.10f;
	}
	
	public void update(Input i, int mx, int my, int delta) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		super.update(i, mx, my, delta);
		
		/* prod_rate is in liters/hr. divide by 360 to get l/s */
		s.resources.water_store += (prod_rate/360)*(delta/1000f);
	}
}
