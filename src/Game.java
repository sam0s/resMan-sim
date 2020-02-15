import java.awt.FontFormatException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.newdawn.slick.*;

public class Game extends BasicGame
{
	double theta;
	Image i;
	Container menu;
	Container blocks[];
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
		theta = 0;
		input = gc.getInput();
		menu = new Container(500, 64, 200, 500);
		blocks = new Container[4];
		for (int i = 0; i < 4; i++) {
			blocks[i] = new Container(20, 20, 100, 100);
		}
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
				
		int mouse_pos[] = getMouse();
		mouse_pos[0] -= menu.sizex/2;
		mouse_pos[1] -= menu.sizey/2;
		
		menu.x -= 0.10*(menu.x-mouse_pos[0]);
		menu.y -= 0.10*(menu.y-mouse_pos[1]);
		
		
		blocks[0].x = (int) ((float)Math.cos(theta)*100 + 150);
		blocks[0].y = (int) ((float)Math.sin(theta)*100 + 150);
		
		theta += 0.01 * Math.PI;
		
		//blocks[0].x -= 0.10*(blocks[0].x - menu.x - menu.sizex/2);
		//blocks[0].y -= 0.10*(blocks[0].y - menu.y - menu.sizey/2);
		
		blocks[1].x -= 0.10*(blocks[1].x - blocks[0].x);
		blocks[1].y -= 0.10*(blocks[1].y - blocks[0].y);

		blocks[2].x = (int) ((float)Math.cos(-theta)*100 + 150);
		blocks[2].y = (int) ((float)Math.sin(-theta)*100 + 150);
		
		//blocks[2].x -= 0.10*(blocks[2].x - blocks[1].x);
		//blocks[2].y -= 0.10*(blocks[2].y - blocks[1].y);
		
		blocks[3].x -= 0.10*(blocks[3].x - blocks[2].x);
		blocks[3].y -= 0.10*(blocks[3].y - blocks[2].y);


	}

	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException
	{
		i.draw(210, 140, 200,200);
		f_32.drawString(32, 32, String.format("Coomer Shelter (%d, %d)", getMouse()[0], getMouse()[1]), Color.orange);
		menu.draw(g);
		for (int i = 0; i < 4; i++) {
		blocks[i].draw(g);
		}
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
			appgc.setTargetFrameRate(60);
			appgc.start();
			

		} catch (SlickException ex)
		{
			Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
}
