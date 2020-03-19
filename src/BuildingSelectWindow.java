import java.lang.reflect.InvocationTargetException;

import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

public class BuildingSelectWindow extends Window {
	StateGame s;
	ImageButton power;
	ImageButton water;
	ImageButton food;

	/* misc elements */
	
	public void test(){
		System.out.println("yep");
	}
	public BuildingSelectWindow(StateGame s) throws NoSuchMethodException, SecurityException, SlickException {
		super(358, 350, 100, 100, Game.win_pad, Game.win_pad, 2, "NULL", s);
		power = new ImageButton(0, 0, 0, 0, new Image("gfx\\room_prev_power.png"), this.fgetMethod("test"), this);
		water = new ImageButton(0, 0, power.sprite.getWidth()+4, 0, new Image("gfx\\room_prev_water.png"), this.fgetMethod("test"), this);
		food = new ImageButton(0, 0, power.sprite.getWidth()*2+8, 0, new Image("gfx\\room_prev_food.png"), this.fgetMethod("test"), this);
		this.add_container(power, water, food);
		this.title = "Build";
		this.hidden=true;
	}

	public void update(Input i, int mx, int my, int delta) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		super.update(i, mx, my, delta);
	}
}
