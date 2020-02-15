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
			blocks[i] = new Container(20, 20, 320, 240);
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
	
	public float[] travel_to_point(float curx, float cury, float destx, float desty) {
		curx -= 0.10 * (curx - destx);
		cury -= 0.10 * (cury - desty);
		
		return new float[] { curx, cury };
	}

	@Override
	public void update(GameContainer gc, int i) throws SlickException
	{

		//gc.getGraphics().setFont(f);
				
		int mouse_pos[] = getMouse();
		mouse_pos[0] -= menu.sizex/2;
		mouse_pos[1] -= menu.sizey/2;
		
		float new_point[] = travel_to_point(menu.x, menu.y, mouse_pos[0], mouse_pos[1]);
		float diff_avg = ((menu.x - mouse_pos[0])+(menu.y - mouse_pos[1]))/2;
		
		menu.x = new_point[0];
		menu.y = new_point[1];
		
		menu.set_size(500, 64);
		menu.sizex += diff_avg;
		menu.sizey += diff_avg;
		
		blocks[0].x = (int) ((float)Math.cos(theta)*100 + 320);
		blocks[0].y = (int) ((float)Math.sin(theta)*100 + 240);
		
		blocks[2].x = (float)Math.cos(-theta)*100 + 320;
		blocks[2].y = (float)Math.sin(-theta)*100 + 240;
		
		theta += 0.01 * Math.PI;
		
		for (int j = 1; j <= 3; j++) {
			if (j == 2) { continue; }
			new_point = travel_to_point(blocks[j].x, blocks[j].y, blocks[j-1].x, blocks[j-1].y);
			blocks[j].x = new_point[0];
			blocks[j].y = new_point[1];
		}
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
