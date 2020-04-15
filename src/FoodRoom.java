import java.lang.reflect.InvocationTargetException;

import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

public class FoodRoom extends ProductionRoom {
	public FoodRoom(float x, float y, StateGame s) throws NoSuchMethodException, SecurityException, SlickException {
		super(x, y, "food_room", s);
		this.prod_rate = 0.01f; // tons/hr
		this.level = 0;
		
		s.resources.power_use += 0.25f;
	}
	
	public void update(Input i, int mx, int my, int delta) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		super.update(i, mx, my, delta);
		
		/* prod_rate is in tons/hr. divide by 360 to get tons/s */
		s.resources.food_store += (prod_rate/360)*(delta/1000f);
	}
}
