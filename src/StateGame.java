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
	public static String[] namesM = {"Glenn","Jerry","Joe","Jack","Paul","Nick","Trevor","Mathew","Todd","Linus","Harry","Walter","Ryan","Bob","Henry","Brian","Dennis"};
	public static String[] namesF = {"Stephanie","Susan","Patricia","Kim","Rachel","Rebecca","Alice","Jackie","Judy","Heidi","Skylar","Anna","Paige"};
	public static String[] namesL = {"Rollins","Howard","Zalman","Bell","Newell","Caiafa","Finnegan","Hall","Howell","Kernighan","Wilson","Ritchie"};
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
	Random b = new Random();
	Entity[] guys;
	Room rooms[]; 
	EntityWindow test_win;
	ControlWindow contrl;
	Image sheet1;
	Entity grabbed;
	Vector<Container> misc_renders = new Vector<Container>();
	InputBox e;
	// Sound soundbyte;

	public static final int ID = 0;

	@Override
	public int getID() {
		return StateGame.ID;
	}

	public Method fgetMethod(String methodName, Class... args) throws NoSuchMethodException, SecurityException {
		return this.getClass().getMethod(methodName, args);
	}
	
	public static String random_name(int gender){
		Random r = new Random();
		String name = "";
		name += (gender==0)?namesM[r.nextInt(namesM.length)]:namesF[r.nextInt(namesM.length)];
		name += " "+namesL[r.nextInt(namesL.length)];
		return name;
	}
	
	public void add_dialog(String text) throws NoSuchMethodException, SecurityException {
		DialogBox temp = new DialogBox(text,0,0, f_32, 2);
		int x = (32 * misc_renders.size()) % (Game.WIDTH-temp.sizex) + (misc_renders.size() % (Game.WIDTH/temp.sizex));
		int y = (32 * misc_renders.size()) % (Game.HEIGHT-temp.sizey) + (misc_renders.size() % (Game.HEIGHT/temp.sizey));
		temp.set_pos(x, y);
		misc_renders.addElement(temp);
	}
	
	public void grab_entity(Entity e){
		grabbed = e;
	}
	
	public void add_guy(Room r) throws SlickException{
		Entity e =new Entity(random_name(0),(int)r.x,(int)r.y);
		e.setSpriteLoad(new SpriteSheet(sheet1,32,64));
		rooms[0].add_entity(e);
		guys = Arrays.copyOf(guys, guys.length+1);
		guys[guys.length-1] = e;
	}

	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		guys=new Entity[]{};
		input = gc.getInput();
		sheet1 = new Image("gfx//testChar.png");
		sheet1.setFilter(Image.FILTER_NEAREST);
		rooms = new Room[]{new Room(320,Game.HEIGHT-200)};
		add_guy(rooms[0]);
		i = new Image("hogBoss.jpg");
		bg = new Image("testBack.png");
		// soundbyte = new Sound("cooom.ogg");
		theta = 0;
		int pad = 4;
		//menu = new Container(500, 64, 200, 500, pad, pad, inner, outer, 2.5);
		fontRaw = null;

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
			contrl = new ControlWindow(400, 100, 0, 0, 4, 4, 2, f_18,this);
			test_win = new EntityWindow(pad, pad, 2, f_24, this);

			//win.add_container(cont);
		} catch (NoSuchMethodException | SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		e = new InputBox(250, 64, 200, 32, 2, input);
		
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
		
		/* update windows */
		try {
			test_win.update(input, delta);
			contrl.update(input,delta);
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
		
		//drag guy if he is grabbed
		if(grabbed!=null){
			grabbed.x=input.getMouseX();
			grabbed.y=input.getMouseY();
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
		f_32.drawString(32, 32, String.format("(%d, %d)", input.getMouseX(), input.getMouseY()), Color.orange);
		e.draw(g);
		for(Room r:rooms){
			r.draw(g);
		}
		for (Iterator<Container> iter = misc_renders.iterator();iter.hasNext(); ) {
			Container cont = iter.next();
			cont.draw(g);
		}
		test_win.draw(g);
		contrl.draw(g);
	}
}