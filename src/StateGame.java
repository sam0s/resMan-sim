import java.awt.FontFormatException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.Random;
import java.util.Vector;
import java.util.Arrays;

import org.newdawn.slick.*;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class StateGame extends BasicGameState {
	double theta;
	Image i;
	Image bg;
	Input input;
	java.awt.Font fontRaw;
	public static Font f_32;
	public static Font f_18;
	public static Font f_24;
	public static Font f_16;
	public static Font f_14;
	long frameTime = 0;
	int[] mouse_pos;
	Random b = new Random();
	Entity[] guys;
	Room rooms[]; 
	EntityWindow test_win;
	Image sheet1;
	
	Vector<Container> misc_renders = new Vector<Container>();
	
	// Sound soundbyte;

	public static final int ID = 0;

	public int[] getMouse() {
		return new int[] { input.getMouseX(), input.getMouseY() };
	}

	@Override
	public int getID() {
		return StateGame.ID;
	}

	public Method fgetMethod(String methodName, Class... args) throws NoSuchMethodException, SecurityException {
		return this.getClass().getMethod(methodName, args);
	}
	
	public void add_dialog(String text) throws NoSuchMethodException, SecurityException {
		DialogBox temp = new DialogBox(text,0,0, f_32, 2);
		int x = (32 * misc_renders.size()) % (1280-temp.sizex) + (misc_renders.size() % (1280/temp.sizex));
		int y = (32 * misc_renders.size()) % (720-temp.sizey) + (misc_renders.size() % (720/temp.sizey));
		temp.set_pos(x, y);
		misc_renders.addElement(temp);
	}

	public void addGuy(Room r) throws SlickException{
		Entity e =new Entity("ea",(int)r.x,(int)r.y);
		e.setSpriteLoad(new SpriteSheet(sheet1,32,64));
		rooms[0].add_entity(e);
		guys = Arrays.copyOf(guys, guys.length+1);
		guys[guys.length-1] = e;
	}

	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		guys=new Entity[]{};
		sheet1 = new Image("gfx//testChar.png");
		sheet1.setFilter(Image.FILTER_NEAREST);
		rooms = new Room[]{new Room(320,Game.HEIGHT-200)};
		addGuy(rooms[0]);
		i = new Image("hogBoss.jpg");
		bg = new Image("testBack.png");
		// soundbyte = new Sound("cooom.ogg");
		theta = 0;
		int pad = 4;
		input = gc.getInput();
		//menu = new Container(500, 64, 200, 500, pad, pad, inner, outer, 2.5);
		fontRaw = null;
		mouse_pos = getMouse();

		try {
			fontRaw = java.awt.Font.createFont(java.awt.Font.TRUETYPE_FONT, new java.io.File("TerminusTTF-Bold-4.47.0.ttf"));
		} catch (FontFormatException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (fontRaw == null) {
			fontRaw = new java.awt.Font("Default", 0, 28);
		}
		f_32 = new TrueTypeFont(fontRaw.deriveFont(32f), false);
		f_24 = new TrueTypeFont(fontRaw.deriveFont(24f), false);
		f_18 = new TrueTypeFont(fontRaw.deriveFont(18f), false);
		f_16 = new TrueTypeFont(fontRaw.deriveFont(16f), false);
		f_14 = new TrueTypeFont(fontRaw.deriveFont(14f), false);
		
		/* init containers */
		Container cont = new Container(100, 100, 0, 0, pad, pad, 2);

		try {
			test_win = new EntityWindow(300, 200, 100, 100, pad, pad, 2, f_24, this);

			//win.add_container(cont);
		} catch (NoSuchMethodException | SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// public void coom() {
	// soundbyte.play();
	// }

	public float[] travel_to_point(float curx, float cury, float destx, float desty, float speed, int delta) {
		/*
		 * delta is in miliseconds, so divide by 1000 to get seconds. multiply
		 * by speed (pixels/s) to get number of pixels we need to move. finally,
		 * multiply by curpos - destpos to make movement proportional to
		 * distance from target.
		 */

		curx -= Math.ceil(speed * (delta / 1000f) * (curx - destx));
		cury -= Math.ceil(speed * (delta / 1000f) * (cury - desty));

		return new float[] { curx, cury };
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		mouse_pos = getMouse();

		// handle bottom menu
		/*
		float new_point[] = travel_to_point(menu.x, menu.y, mouse_pos[0], mouse_pos[1], 4, delta);
		float diff_avg = ((menu.x - mouse_pos[0]) + (menu.y - mouse_pos[1])) / 4;

		if (mouse_pos[1] > 600) {
			new_point = travel_to_point(menu.x, menu.y, 0, 620, 4, delta);
			menu.y = new_point[1];
		} else {
			new_point = travel_to_point(menu.x, menu.y, Game.WIDTH / 2 - menu.sizex / 2, Game.HEIGHT, 3, delta);
			menu.x = new_point[0];
			menu.y = new_point[1];
		}
		
		*/
		
		/* update windows */
		try {
			test_win.update(input, delta);
			for (Iterator<Container> iter = misc_renders.iterator();iter.hasNext(); ) {
				Container cont = iter.next();
				cont.update(input);
				if(cont.destroy) {
					iter.remove();
				}
			}
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for(Entity e:guys){
		    e.update(delta);
		}
		
		// eat mouse input
		if (input.isMousePressed(0)) {
		    for(Entity e:guys){
		    	if (e.isClicked(input)){
		    		if(test_win.hidden){
		    			test_win.show();
		    		}
		    		test_win.setEntity(e);
		    		break;
		    	}
		    	test_win.setEntity(null);
			}
		}

	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		bg.draw(0,0);
		i.draw(210, 140, 200, 200);
		f_32.drawString(32, 32, String.format("(%d, %d)", mouse_pos[0], mouse_pos[1]), Color.orange);
		
		for(Room r:rooms){
			r.draw(g);
		}
		for (Iterator<Container> iter = misc_renders.iterator();iter.hasNext(); ) {
			Container cont = iter.next();
			cont.draw(g);
		}
		test_win.draw(g);
	}
}