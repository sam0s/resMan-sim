import java.lang.reflect.InvocationTargetException;

import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

public class BuildingSelectWindow extends Window {
	StateGame s;
	ImageButton power;
	ImageButton water;
	ImageButton food;
	Button placeElevator;

	public BuildingSelectWindow(StateGame s) throws NoSuchMethodException, SecurityException, SlickException {
		super(358, 350, 100, 100, Game.win_pad, Game.win_pad, 2, "NULL", s);
		power = new ImageButton(0, 0, 0, 0, new Image("gfx//room_prev_power.png"), s.fgetMethod("enter_placement_mode", String.class), s);
		water = new ImageButton(0, 0, power.sprite.getWidth() + 4, 0, new Image("gfx//room_prev_water.png"), s.fgetMethod("enter_placement_mode", String.class), s);
		food = new ImageButton(0, 0, power.sprite.getWidth() * 2 + 8, 0, new Image("gfx//room_prev_food.png"), s.fgetMethod("enter_placement_mode", String.class), s);
		placeElevator = new Button(160, 32, 150, 120, 2, "add elevator", StateGame.f_24, s.fgetMethod("enter_placement_mode", String.class), s);
		placeElevator.set_args("elevator");
		power.set_args("power");
		water.set_args("water");
		food.set_args("food");
		this.add_container(power, water, food, placeElevator);
		this.title = "Build";
		this.hidden = true;
	}

	public void update(Input i, int mx, int my, int delta) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		super.update(i, mx, my, delta);
	}
}
