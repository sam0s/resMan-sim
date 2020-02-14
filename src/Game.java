import java.awt.FontFormatException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.newdawn.slick.*;

public class Game extends BasicGame
{
	Image i;
	Container menu;
	Input input;
	Font f;

	public Game(String gamename)
	{
		super(gamename);
	}

	public int[] getMouse()
	{
		return new int[]
		{ input.getMouseX(), input.getMouseY() };
	}

	@Override
	public void init(GameContainer gc) throws SlickException
	{
		i = new Image("hogBoss.jpg");
		input = gc.getInput();
		menu = new Container(500, 64, 200, 500);
		java.awt.Font fontRaw = null;
		try
		{
			fontRaw = java.awt.Font.createFont(java.awt.Font.TRUETYPE_FONT, new java.io.File("FreeMonoBold.ttf"));
			fontRaw = fontRaw.deriveFont(32f);
		} catch (FontFormatException e)
		{
			e.printStackTrace();

		} catch (IOException e)
		{
			e.printStackTrace();
		}
		if (fontRaw == null)
		{
			fontRaw = new java.awt.Font("Default", 0, 28);
		}
		f = new TrueTypeFont(fontRaw, true);
	}

	@Override
	public void update(GameContainer gc, int i) throws SlickException
	{

		gc.getGraphics().setFont(f);
		System.out.println(i);
		// just a test
		if (menu.y > 300)
		{
			menu.y -= 0.07 * (menu.y - 300);
		}

	}

	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException
	{
		i.draw(32, 32, 200,200);
		f.drawString(32, 32, String.format("Coomer Shelter (%d, %d)", getMouse()[0], getMouse()[1]), Color.orange);
		menu.draw(g);
	}

	public void loop()
	{

	}

	public static void main(String[] args)
	{
		try
		{
			AppGameContainer appgc;
			appgc = new AppGameContainer(new Game("COOMER SHELTER"));
			appgc.setDisplayMode(640, 480, false);
			appgc.start();

		} catch (SlickException ex)
		{
			Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
}
