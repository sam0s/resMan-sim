import java.awt.FontFormatException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.Random;
import java.util.Vector;

import org.newdawn.slick.*;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class StateGame extends BasicGameState {

	public static String[] namesM = { "Glenn", "Jerry", "Joe", "Jack", "Paul", "Nick", "Trevor", "Mathew", "Todd", "Linus", "Harry", "Walter", "Ryan", "Bob", "Henry", "Brian", "Dennis" };
	public static String[] namesF = { "Stephanie", "Susan", "Patricia", "Kim", "Rachel", "Rebecca", "Alice", "Jackie", "Judy", "Heidi", "Skylar", "Anna", "Paige" };
	public static String[] namesL = { "Rollins", "Howard", "Zalman", "Bell", "Newell", "Caiafa", "Finnegan", "Hall", "Howell", "Kernighan", "Wilson", "Ritchie" };
	
	Image bg, bg2;
	Input input;
	MouseControls mc = new MouseControls(this);
	KeyboardControls kc = new KeyboardControls(this);
	java.awt.Font fontRaw = null;
	public static Font f_32, f_18, f_24, f_16, f_14;
	Random b = new Random();
	
	Room new_room;
	
	Entity grabbed;
	Vector<Container> misc_renders = new Vector<Container>();
	Vector<Container> ui = new Vector<Container>();
	
	Vector<Room> rooms = new Vector<Room>();
	Vector<Entity> guys = new Vector<Entity>();
	
	EntityWindow ewin;
	ControlWindow cwin;
	Window focused_win;
	MenuBar menu;
	String mode;

	int vp_x = 0;
	int vp_y = 0;
	int vp_w = Game.WIDTH;
	int vp_h = Game.HEIGHT;
	float vp_zoom_scale = 1;

	int mousex_rel;
	int mousey_rel;
	
	boolean debug_info = true;
	boolean placed = false;

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

	// change form of (add_guy) to be more progressive!
	public void add_person(Room r) throws SlickException, NoSuchMethodException, SecurityException {
		Entity e = new Human((int) r.x, (int) r.y);
		r.add_entity(e);
		guys.addElement(e);
	}
	
	public void kill_all() throws NoSuchMethodException, SecurityException {
		for (Entity e: guys) {
			e.curRoom.ents.removeAllElements();
		}
		guys.removeAllElements();
		ewin.deselect_entity();
		cwin.sel_person = null;
		cwin.clear_parents();
	}
	
	public void init_fonts() {
		try {
			fontRaw = java.awt.Font.createFont(java.awt.Font.TRUETYPE_FONT, new java.io.File("TerminusTTF-Bold-4.47.0.ttf"));
		} catch (FontFormatException | IOException e) {
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
	}

	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		mode = "general";

		input = gc.getInput();
		input.addMouseListener(mc);
		input.addKeyListener(kc);

		bg = new Image("testBack.png");
		bg2 = new Image("under.png");
		
		init_fonts();
		
		/* init containers */

		try {
			rooms.addElement(new Room(320, Game.HEIGHT - 210, "default", this));
			add_person(rooms.firstElement());
			menu = new MenuBar();
			ui.addElement(menu);
			cwin = new ControlWindow(400, 100, 0, 0, 4, 4, 2, f_24, this);
			ui.addElement(cwin);
			ewin = new EntityWindow(Game.win_pad, Game.win_pad, 2, f_24, this);
			focused_win = cwin;
			ui.addElement(ewin);

			menu = new MenuBar();
			menu.add_icon(new ImageButton(64, 64, 0, 0, new Image("gfx//tweezicon.png"), fgetMethod("reset_viewport"), this), "left");
			menu.add_icon(new ImageButton(64, 64, 0, 0, new Image("gfx//tweezicon.png"), fgetMethod("reset_viewport"), this), "left");
			menu.add_icon(new ImageButton(64, 64, 0, 0, new Image("gfx//tweezicon.png"), fgetMethod("reset_viewport"), this), "left");
			menu.add_icon(new ImageButton(64, 64, 0, 0, new Image("gfx//tweezicon.png"), fgetMethod("reset_viewport"), this), "right");
			menu.add_icon(new ImageButton(64, 64, 0, 0, new Image("gfx//icon_camera.png"), fgetMethod("reset_viewport"), this), "right");

			ui.addElement(menu);
		} catch (NoSuchMethodException | SecurityException e) {
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
		/* TEMPORARY!!!! */
		for (Container c: ui) {
			c.hidden = false;
		}
	}
	
	public boolean overlaps(float a_tl_x, float a_tl_y, float a_br_x, float a_br_y, 
			float b_tl_x, float b_tl_y, float b_br_x, float b_br_y) {
			System.out.printf("%s\n", a_br_x <= b_tl_x || a_tl_x >= b_br_x ? "l/r" : "");
			System.out.printf("%s\n", a_br_y <= b_tl_y || a_tl_y >= b_br_y ? "u/d" : "");
		return !(a_br_x <= b_tl_x || a_tl_x >= b_br_x) && !(a_br_y <= b_tl_y || a_tl_y >= b_br_y);
	}
	
	public boolean room_overlap(float b_tl_x, float b_tl_y, float b_br_x, float b_br_y) {
		for (Room a: rooms) {
			if (overlaps(a.x, a.y, a.x + a.sizex, a.y + a.sizey, b_tl_x, b_tl_y, b_br_x, b_br_y)) {
				return true;
			}
		}
		return false;
	}
	
	public void enter_placement_mode(String type) throws NoSuchMethodException, SecurityException {
		new_room = new Room(0, 0, type, this);
		mode = "room_place";
		placed = false;
	}
	
	public void exit_placement_mode() {
		if (placed) {
			rooms.addElement(new_room);
		}
		new_room = null;
		mode = "general";
	}
	
	public void reset_mode() {
		switch (mode) {
		case "room_place":
			exit_placement_mode();
			break;
		case "general":
			break;
		}
	}


	public void update_containers(Vector<Container> elements, int delta, Boolean mousepress,int mx,int my) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		for (Iterator<Container> iter = elements.iterator(); iter.hasNext();) {
			Container cont = iter.next();
			if (focused_win != null) {
				focused_win.update(input, mx,my,delta);
			}

			if (!cont.is_focused) {
				cont.update(input, mx,my,delta);
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

		mousex_rel = (int) Math.floor(input.getMouseX() / vp_zoom_scale) - vp_x;
		mousey_rel = (int) Math.floor(input.getMouseY() / vp_zoom_scale) - vp_y;
		
		mc.set_delta(delta);
		kc.set_delta(delta);
		
		if (mode == "room_place") {
			for (Iterator<Room> iter = rooms.iterator(); iter.hasNext();) {
				Room r = iter.next();
				try {
					r.update(input,mousex_rel,mousey_rel, delta);
				} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e1) {
					e1.printStackTrace();
				}
			}
			if (placed) {
				reset_mode();
			} else {
				return; 
			}
		}
		
		try {
			update_containers(ui, delta, mousepress,input.getMouseX(),input.getMouseY());
			update_containers(misc_renders, delta, mousepress,input.getMouseX(),input.getMouseY());
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e1) {
			e1.printStackTrace();
		}

		for (Iterator<Room> iter = rooms.iterator(); iter.hasNext();) {
			Room r = iter.next();
			try {
				r.update(input,mousex_rel,mousey_rel, delta);
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e1) {
				e1.printStackTrace();
			}
		}

		for (Entity e : guys) {
			e.update(delta);
		}

		// drag guy if he is grabbed
		if (grabbed != null) {
			grabbed.x = mousex_rel;
			grabbed.y = mousey_rel;
		}
	}

	public void set_mode(String m) {
		mode = m;
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {

		g.scale(vp_zoom_scale, vp_zoom_scale);
		g.translate(vp_x, vp_y);
		
		if (mode == "room_place") {
			for (Iterator<Room> iter = rooms.iterator(); iter.hasNext();) {
				Room r = iter.next();
				r.draw(g);
				r.drawFreeAdjacents(g);
			}
			return;
		}

		bg.draw(0, 0);
		bg2.draw(0, bg.getHeight());

		for (Room r : rooms) {
			r.draw(g);
		}

		g.translate(-vp_x, -vp_y);
		g.scale(1 / vp_zoom_scale, 1 / vp_zoom_scale);

		if (debug_info) {
			f_32.drawString(32, menu.sizey, String.format("(%d, %d), vp: (%d %d) %dx%d [%f], mr: (%d, %d)", input.getMouseX(), input.getMouseY(), vp_x, vp_y, vp_w, vp_h, vp_zoom_scale, mousex_rel, mousey_rel), Color.red);
			f_32.drawString(32, menu.sizey + 32, String.format("pop: %d, rms: %d", guys.size(), rooms.size()), Color.red);
		}
		
		for (Container cont: ui) {
			if (!cont.is_focused) {
				cont.draw(g);
			}

		}
		for (Container cont: misc_renders) {
			cont.draw(g);
		}
		
		if (focused_win != null) {
			focused_win.draw(g);
		}
	}
}