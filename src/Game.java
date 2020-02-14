import java.util.logging.Level;
import java.util.logging.Logger;
import org.newdawn.slick.*;

public class Game extends BasicGame
{
	Image i;
	Container menu;
	
	public Game(String gamename)
	{
		super(gamename);
	}

	@Override
	public void init(GameContainer gc) throws SlickException {
		i = new Image("img.png");
		menu = new Container(500,64);
	}

	@Override
	public void update(GameContainer gc, int i) throws SlickException {}

	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException
	{
		menu.draw(100,200,g);
		//i.draw(32,32,300,300);
	}

	public static void main(String[] args)
	{
		try
		{
			AppGameContainer appgc;
			appgc = new AppGameContainer(new Game("COOMER SHELTER"));
			appgc.setDisplayMode(640, 480, false);
			appgc.start();
		}
		catch (SlickException ex)
		{
			Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
}
