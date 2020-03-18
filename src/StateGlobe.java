import org.newdawn.slick.*;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class StateGlobe extends BasicGameState {
	public static final int ID = 1;
	int rot = 0;
	Image globeImg;

	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		globeImg = new Image(String.format("gfx//globe_frames//globf00%02d.png", rot));
	}

	@Override
	public void mouseDragged(int oldx, int oldy, int newx, int newy) {
		rot += (newx - oldx);

		if (rot > 71) {
			rot = 0;
		}
		if (rot < 0) {
			rot = 71;
		}
		try {
			globeImg = new Image(String.format("gfx//globe_frames//globf00%02d.png", rot));
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {

	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		globeImg.draw(0, 0);
	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return ID;
	}
}