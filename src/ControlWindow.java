import java.lang.reflect.InvocationTargetException;

import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.Input;

public class ControlWindow extends Window {
	StateGame s; 
	Button newGuy;
	
	public ControlWindow(int sizex, int sizey, int x, int y, int padx, int pady, double weight, Font f, StateGame s) throws NoSuchMethodException, SecurityException {
		super(sizex, sizey, x, y, padx, pady, weight, f, "Important window",s);
		add_container(new Label(0, 0, 4, 4, 0, "sniff snoff", StateGame.f_18));
		newGuy = new Button(100,32,0,32,2,"add 1",f,s.fgetMethod("add_person",Room.class),s);
		newGuy.set_args(s.rooms[0]);
		add_container(newGuy);

	}
	
	public void update(Input i, int delta) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		super.update(i, delta);
	}

}
