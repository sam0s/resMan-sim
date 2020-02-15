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
	java.awt.Font fontRaw;
	Font f_32;
	Font f_24;
	Font f_16;

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
		fontRaw = null;
		try
		{
			fontRaw = java.awt.Font.createFont(java.awt.Font.TRUETYPE_FONT, new java.io.File("FreeMonoBold.ttf"));
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
		f_32 = new TrueTypeFont(fontRaw.deriveFont(32f), true);
		f_24 = new TrueTypeFont(fontRaw.deriveFont(24f), true);
		f_16 = new TrueTypeFont(fontRaw.deriveFont(16f), true);
	}

	@Override
	public void update(GameContainer gc, int i) throws SlickException
	{

		//gc.getGraphics().setFont(f);
				
		int mouse_y = getMouse()[1] - menu.sizey/2;
		
		if (menu.y > mouse_y)
		{
			menu.y -= 0.10 * (menu.y - mouse_y);
		} else if (menu.y < mouse_y) {
			menu.y += 0.10 * (mouse_y - menu.y);
		}

	}

	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException
	{
		i.draw(32, 32, 200,200);
		f_32.drawString(32, 32, String.format("Coomer Shelter (%d, %d)", getMouse()[0], getMouse()[1]), Color.orange);
		menu.draw(g);
		f_24.drawString(menu.x+4, menu.y+4, "Menu",Color.orange);
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
