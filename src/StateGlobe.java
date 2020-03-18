import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.newdawn.slick.*;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class StateGlobe extends BasicGameState {
	public static final int ID = 1;
	double rot = 60;
	Image globeImg;
	ImageButton rot_l;
	ImageButton rot_r;
	Input input;
	int rotating;

	public Method fgetMethod(String methodName, Class... args) throws NoSuchMethodException, SecurityException {
		return this.getClass().getMethod(methodName, args);
	}

	public void rotate(Integer dir) {
		rot+=dir;
		if (rotating == 0) {
			rotating = dir;
		}
	}

	public void rotation_update(int delta) {
		rot += (rotating) * 0.03 * delta;
		System.out.println(rot);
		int grot = (int) rot;
		if (grot == 60 || grot == 43 || grot == 21) {
			rotating = 0;
			rot=grot;
		}

		if (rot > 71) {
			rot=0;
			grot = 0;
		}
		if (rot < 0) {
			rot=71;
			grot = 71;
		}
		try {
			//switch this to use an array of images so that loading can just be done one time. ( DO THIS EVENTUALLY)
			globeImg = new Image(String.format("gfx//globe_frames//globf00%02d.png", grot));
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		globeImg = new Image(String.format("gfx//globe_frames//globf00%02d.png", 60));
		input = gc.getInput();
		try {
			rot_r = new ImageButton(0, 0, 862, 311, new Image("gfx//globe_rotate.png"), fgetMethod("rotate", Integer.class), this);
			rot_l = new ImageButton(0, 0, 327, 311, new Image("gfx//globe_rotate_left.png"), fgetMethod("rotate", Integer.class), this);
			rot_r.set_args(-1);
			rot_l.set_args(1);

		} catch (NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {

		// update buttons
		try {
			rot_r.update(input, input.getMouseX(), input.getMouseY(), delta);
			rot_l.update(input, input.getMouseX(), input.getMouseY(), delta);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
		if (rotating != 0) {
			rotation_update(delta);
		}
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		globeImg.draw(0, 0);
		rot_r.draw(g);
		rot_l.draw(g);
	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return ID;
	}
}