import java.lang.reflect.InvocationTargetException;

import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.Input;

public class ControlWindow extends Window {
	StateGame s;
	Button newGuy;
	Button placeRoom;
	Button reset_vp;

	public ControlWindow(int sizex, int sizey, int x, int y, int padx, int pady, double weight, Font f, StateGame s) throws NoSuchMethodException, SecurityException {
		super(300, 200, 420, 200, padx, pady, weight, f, "Important window", s);
		add_container(new Label(0, 0, 4, 4, 0, "sniff snoff", StateGame.f_18));
		newGuy = new Button(100, 32, 0, 32, 2, "add 1", f, s.fgetMethod("add_person", Room.class), s);
		newGuy.set_args(s.rooms.firstElement());
		placeRoom = new Button(100, 32, 132, 32, 2, "add room", f, s.fgetMethod("set_mode", String.class), s);
		placeRoom.set_args("room_place");
		reset_vp = new Button(f.getWidth("reset_viewport") + 10, 32, 0, 68, 2, "reset viewport", f, s.fgetMethod("reset_viewport"), s);
		reset_vp.set_args((Object[]) null);
		add_container(newGuy, placeRoom, reset_vp);

	}

	public void update(Input i, int mx, int my, int delta) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		super.update(i, mx, my, delta);
	}

}
