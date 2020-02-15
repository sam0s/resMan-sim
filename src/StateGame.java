import java.awt.FontFormatException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.newdawn.slick.*;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class StateGame extends BasicGameState
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
	long frameTime = 0;
	public static final int ID = 0;

	public int[] getMouse()
	{
		return new int[]
		{ input.getMouseX(), input.getMouseY() };
	}
	
	@Override
	public int getID() {
		return StateGame.ID;
	}

	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		i = new Image("hogBoss.jpg");
		Color outer = new Color(255,0,0);
		Color inner = new Color(255,255,255,50);
		theta = 0;
		input = gc.getInput();
		menu = new Container(500, 64, 200, 500,inner,outer,2.5);
		blocks = new Container[4];
		for (int i = 0; i < 4; i++) {
			blocks[i] = new Container(20, 20, 320, 240,inner,outer,1);
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
	
	public float[] travel_to_point(float curx, float cury, float destx, float desty, float speed, int delta) {
		/* delta is in miliseconds, so divide by 1000 to get seconds. multiply by speed (pixels/s) to get 
		 * number of pixels we need to move. finally, multiply by curpos - destpos to make
		 * movement proportional to distance from target.
		 */
		
		curx -= speed * (delta/1000f) * (curx - destx);
		cury -= speed * (delta/1000f) * (cury - desty);
		
		return new float[] { curx, cury };
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException
	{

		//gc.getGraphics().setFont(f);
		frameTime+=delta;
		// BECAUSE DELTA TIME = TIME BETWEEN FRAMES
		// THIS FUNCTION SHOULD BE INCREMENTING FRAMETIME BY A LARGER NUMBER WHEN THE FRAMERATE IS HIGH
		// AND BY A SMALLER NUMBER WHEN THE FRAMERATE IS LOW??? I THINK BUT THIS ACTUALLY EVENS THE SPEED OUT SOMEHOW TOO
		//if (frameTime>20){
		//System.out.println(delta);
		int mouse_pos[] = getMouse();
		mouse_pos[0] -= menu.sizex/2;
		mouse_pos[1] -= menu.sizey/2;
		
		
		float new_point[] = travel_to_point(menu.x, menu.y, mouse_pos[0], mouse_pos[1], 4, delta);
		float diff_avg = ((menu.x - mouse_pos[0])+(menu.y - mouse_pos[1]))/4;
		
		menu.set_size(500, 64);

		if (mouse_pos[1] > 400) {
			menu.x = new_point[0];
			menu.y = new_point[1];
			menu.sizex += diff_avg;
			menu.sizey += diff_avg;
		} else {
			new_point = travel_to_point(menu.x, menu.y, mouse_pos[0], 450, 3, delta);
			menu.x = new_point[0];
			menu.y = new_point[1];
		}
		
		blocks[0].x = (int) ((float)Math.cos(theta)*100 + 320);
		blocks[0].y = (int) ((float)Math.sin(theta)*100 + 240);
		
		blocks[2].x = (float)Math.cos(-theta)*100 + 320;
		blocks[2].y = (float)Math.sin(-theta)*100 + 240;
		
		theta += 1 * Math.PI * (delta/1000f);
		
		for (int j = 1; j <= 3; j++) {
			if (j == 2) { continue; }
			new_point = travel_to_point(blocks[j].x, blocks[j].y, blocks[j-1].x, blocks[j-1].y, 4, delta);
			blocks[j].x = new_point[0];
			blocks[j].y = new_point[1];
		}
		//frameTime=0;
		//}
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		i.draw(210, 140, 200,200);
		f_32.drawString(32, 32, String.format("Coomer Shelter (%d, %d)", getMouse()[0], getMouse()[1]), Color.orange);
		menu.draw(g);
		for (int i = 0; i < 4; i++) {
		blocks[i].draw(g);
		}
		f_24.drawString(menu.x+4, menu.y+4, "Menu",Color.orange);
	}
}