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
import java.util.Collections;

import org.newdawn.slick.*;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class StateGame extends BasicGameState {
	double theta;
	public static String[] namesM = { "Glenn", "Jerry", "Joe", "Jack", "Paul", "Nick", "Trevor", "Mathew", "Todd", "Linus", "Harry", "Walter", "Ryan", "Bob", "Henry", "Brian", "Dennis" };
	public static String[] namesF = { "Stephanie", "Susan", "Patricia", "Kim", "Rachel", "Rebecca", "Alice", "Jackie", "Judy", "Heidi", "Skylar", "Anna", "Paige" };
	public static String[] namesL = { "Rollins", "Howard", "Zalman", "Bell", "Newell", "Caiafa", "Finnegan", "Hall", "Howell", "Kernighan", "Wilson", "Ritchie" };
	Image bg,bg2;
	Input input;
	java.awt.Font fontRaw;
	public static Font f_32,f_18,f_24,f_16,f_14;
	long frameTime = 0;
	Random b = new Random();
	Entity[] guys;
	Room rooms[];
	
	Entity grabbed;
	Vector<Container> misc_renders = new Vector<Container>();
	Vector<Container> ui = new Vector<Container>();
	EntityWindow ewin;
	ControlWindow cwin;
	Boolean both_focused = false;
	Window focused_win;
	MenuBar menu;
	String mode;
	int camx;
	int camy;
	
	int vp_x = 0;
	int vp_y = 0;
	int vp_w = Game.WIDTH;
	int vp_h = Game.HEIGHT;
	float vp_zoom_scale = 1;
	
	int mousex_rel;
	int mousey_rel;
	
	// Sound soundbyte;

	public static final int ID = 0;

	@Override
	public int getID() {
		return StateGame.ID;
	}

	public Method fgetMethod(String methodName, Class... args) throws NoSuchMethodException, SecurityException {
		return this.getClass().getMethod(methodName, args);
	}

	public static String random_name(boolean gender) {
		Random r = new Random();
		String name = "";
		name += (gender) ? namesM[r.nextInt(namesM.length)] : namesF[r.nextInt(namesF.length)];
		name += " " + namesL[r.nextInt(namesL.length)];
		return name;
	}

	public void add_dialog(String text) throws NoSuchMethodException, SecurityException {
		DialogBox temp = new DialogBox(text, 0, 0, f_32, 2);
		int x = (32 * misc_renders.size()) % (Game.WIDTH - temp.sizex) + (misc_renders.size() % (Game.WIDTH / temp.sizex));
		int y = (32 * misc_renders.size()) % (Game.HEIGHT - temp.sizey) + (misc_renders.size() % (Game.HEIGHT / temp.sizey));
		temp.set_pos(x, y);
		misc_renders.addElement(temp);
	}

	public InputBox add_input_box(Method okb, Object target) throws NoSuchMethodException, SecurityException {
		InputBox temp = new InputBox("Entry box!", Game.WIDTH / 2 - 100, Game.HEIGHT / 2 - 50, f_24, 2, input, okb, target, this);
		misc_renders.addElement(temp);
		set_window_focus(temp);
		return temp;
	}

	public void grab_entity(Entity e) {
		grabbed = e;
	}

	//change form of (add_guy) to be more progressive!
	public void add_person(Room r) throws SlickException {
		boolean gender = new Random().nextBoolean();
		Entity e = new Human(random_name(gender), (int) r.x, (int) r.y,gender);
		rooms[0].add_entity(e);
		guys = Arrays.copyOf(guys, guys.length + 1);
		guys[guys.length - 1] = e;
	}

	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		mode = "general";
		
		guys = new Entity[] {};
		input = gc.getInput();
		input.addMouseListener(new MouseControls(this));
		input.addKeyListener(new KeyboardControls(this));
		rooms = new Room[] { new Room(320, Game.HEIGHT - 200) };
		add_person(rooms[0]);
		bg = new Image("testBack.png");
		bg2 = new Image("under.png");
		// soundbyte = new Sound("cooom.ogg");
		theta = 0;
		int pad = 4;
		// menu = new Container(500, 64, 200, 500, pad, pad, inner, outer, 2.5);
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
			menu = new MenuBar();
			ui.addElement(menu);
			cwin = new ControlWindow(400, 100, 0, 0, 4, 4, 2, f_24, this);
			ui.addElement(cwin);
			ewin = new EntityWindow(pad, pad, 2, f_24, this);
			focused_win = cwin;
			ui.addElement(ewin);
			
			menu = new MenuBar();
			ui.addElement(menu);

			// win.add_container(cont);
		} catch (NoSuchMethodException | SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}
		
		set_window_focus(ewin);
	}

	public void set_window_focus(Window f) {
		if (focused_win != null) {
			focused_win.is_focused = false;
		}
		f.is_focused = true;
		focused_win = f;
	}
	
	public void reset_viewport() {
		vp_x = vp_y = 0;
		vp_w = Game.WIDTH;
		vp_h = Game.HEIGHT;
		vp_zoom_scale = 1f;
	}

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

	public void update_containers(Vector<Container> elements, int delta, Boolean mousepress) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		for (Iterator<Container> iter = elements.iterator(); iter.hasNext();) {
			Container cont = iter.next();
			if (focused_win != null) {
				focused_win.update(input, delta);
			}
			
			if (!cont.is_focused) {
				cont.update(input, delta);
			}
			
			if (cont.destroy) {
				if (cont.is_focused) {
					focused_win = null;
				}
				iter.remove();
			}
		}
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		/* update windows */

		Boolean mousepress = input.isMouseButtonDown(0);
		
		mousex_rel = (int)Math.floor(input.getMouseX()/vp_zoom_scale) - vp_x;
		mousey_rel = (int)Math.floor(input.getMouseY()/vp_zoom_scale) - vp_y;

		try {
			update_containers(ui, delta, mousepress);
			update_containers(misc_renders, delta, mousepress);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e1) {
			e1.printStackTrace();
		}

		for (Entity e : guys) {
			e.update(delta);
		}

		// drag guy if he is grabbed
		if (grabbed != null) {
			grabbed.x = mousex_rel;
			grabbed.y = mousey_rel;
		}

		// eat mouse input
		if (input.isMousePressed(0)) {
			for (Entity e : guys) {
				if (e.isClicked(mousex_rel, mousey_rel)) {
					if (ewin.hidden) {
						ewin.show();
					}
					ewin.setEntity(e);
					break;
				}
				// test_win.setEntity(null);
			}
		}

	}
	public void set_mode(String m){
		mode = m;
	}
	
	
	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		
		g.scale(vp_zoom_scale,vp_zoom_scale);
		g.translate(vp_x,  vp_y);
		
		bg.draw(0-camx, 0-camy);
		bg2.draw(0-camx,bg.getHeight()-camy);
		
		if(mode=="room_place"){
			for (Room r : rooms) {
				r.draw(g);
			}
			return;
		}
		
		for (Room r : rooms) {
			r.draw(g);
		}
	
		g.translate(-vp_x,  -vp_y);
		g.scale(1/vp_zoom_scale, 1/vp_zoom_scale);
			
		
		f_32.drawString(32, 64, String.format("(%d, %d), vp: (%d %d) %dx%d [%f], mr: (%d, %d)", 
				input.getMouseX(), input.getMouseY(),
				vp_x, vp_y, vp_w, vp_h,vp_zoom_scale,
				mousex_rel, mousey_rel),
				Color.red);
		
		for (Iterator<Container> iter = ui.iterator(); iter.hasNext();) {
			Container cont = iter.next();
			if (!cont.is_focused) {
				cont.draw(g);
			}
			
		}
		for (Iterator<Container> iter = misc_renders.iterator(); iter.hasNext();) {
			Container cont = iter.next();
			cont.draw(g);
		}
		if (focused_win != null) {
			focused_win.draw(g);
		}
	}
}