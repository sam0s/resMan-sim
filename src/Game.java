import java.awt.FontFormatException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.newdawn.slick.*;
import org.newdawn.slick.state.StateBasedGame;

public class Game extends StateBasedGame {

	public Game(String name) {
		super(name);
	}

	// Add more of these when there are more states
	public static final int GAME = 0;

	// Application Properties
	public static final int WIDTH = 1280;
	public static final int HEIGHT = 720;
	public static final int FPS = 120;
	public static final Color win_inner_f = new Color(40, 40, 40, 225);
	public static final Color win_outer_f = Color.orange;
	public static final Color win_inner = win_inner_f;
	public static final Color win_outer = win_outer_f;
	// major.minor(patch)
	public static final double VERSION = 1.35;

	public void initStatesList(GameContainer gc) throws SlickException {
		this.addState(new StateGame());
	}

	public static void main(String[] args) {
		try {
			AppGameContainer app = new AppGameContainer(new Game("coomout_shelter indeb v" + VERSION));
			app.setDisplayMode(WIDTH, HEIGHT, false);
			app.setTargetFrameRate(FPS);
			app.setShowFPS(true);
			app.start();
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
}
