import java.lang.reflect.InvocationTargetException;

import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.Input;

public class ControlWindow extends Window {
	StateGame s; 
	Button newGuy;
	Button placeRoom;
	
	public ControlWindow(int sizex, int sizey, int x, int y, int padx, int pady, double weight, Font f, StateGame s) throws NoSuchMethodException, SecurityException {
		super(300, 200, 420, 200, padx, pady, weight, f, "Important window",s);
		add_container(new Label(0, 0, 4, 4, 0, "sniff snoff", StateGame.f_18));
		newGuy = new Button(100,32,0,32,2,"add 1",f,s.fgetMethod("add_person",Room.class),s);
		newGuy.set_args(s.rooms[0]);
		placeRoom = new Button(100,32,132,32,2,"add room",f,s.fgetMethod("set_mode",String.class),s);
		placeRoom.set_args("room_place");
		add_container(newGuy,placeRoom);

	}
	
	public void update(Input i, int delta) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		super.update(i, delta);
	}

}
