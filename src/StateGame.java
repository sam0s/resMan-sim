import java.awt.FontFormatException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
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

public class StateGame extends BasicGameState implements Serializable {
	StateBasedGame psbg;
	public static String[] namesM = { "Glenn", "Jerry", "Joe", "Jack", "Paul", "Nick", "Trevor", "Mathew", "Todd", "Linus", "Harry", "Walter", "Ryan", "Bob", "Henry", "Brian", "Dennis" };
	public static String[] namesF = { "Stephanie", "Susan", "Patricia", "Kim", "Rachel", "Rebecca", "Alice", "Jackie", "Judy", "Heidi", "Skylar", "Anna", "Paige" };
	public static String[] namesL = { "Rollins", "Howard", "Zalman", "Bell", "Newell", "Caiafa", "Finnegan", "Hall", "Howell", "Kernighan", "Wilson", "Ritchie" };
	boolean dragging = false;
	static Image head, bg, bg2, power_room_image, water_room_image, elevator_room_image, food_room_image, eyes;
	static SpriteSheet legs;
	static SpriteSheet faces, hairs;
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

	Resources resources;

	EntityWindow ewin;
	BuildingSelectWindow rwin;
	ControlWindow cwin;
	Window focused_win;
	Room focused_room;
	MenuBar menu;
	InfoDisp info;
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
		Human e = new Human((int) r.x, (int) r.y);
		r.add_entity(e);
		resources.add_staff(e);
	}

	public void load() throws SlickException, NoSuchMethodException, SecurityException {
		String filename = "file.bas";
		FileInputStream file;
		Vector<Room> e = null;
		try {
			file = new FileInputStream(filename);
			ObjectInputStream in = new ObjectInputStream(file);

			// Method for deserialization of object
			e = (Vector<Room>) in.readObject();
			for (Room ar : e) {
				ar.onLoad(this);
			}
			in.close();
			file.close();

		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		rooms = e;
	}

	public void kill_all() throws NoSuchMethodException, SecurityException {
		resources.remove_all_staff();
		// ewin.deselect_entity();
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

	public void gotothaglobe() {
		psbg.enterState(1);
	}

	public void save() {
		String filename = "file.bas";

		// Serialization
		try {
			// Saving of object in a file
			FileOutputStream file = new FileOutputStream(filename);
			ObjectOutputStream out = new ObjectOutputStream(file);

			// Method for serialization of object
			out.writeObject(rooms);

			out.close();
			file.close();

			System.out.println("Object has been serialized");

		}

		catch (IOException ex) {
			ex.printStackTrace();
			// System.out.println("IOException is caught");
		}

	}

	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {

		mode = "general";
		head = new Image("gfx//charAttributes//head.png");
		head.setFilter(Image.FILTER_NEAREST);
		eyes = new Image("gfx//charAttributes//eyes.png"); // 15,6
		eyes.setFilter(Image.FILTER_NEAREST);
		faces = new SpriteSheet(new Image("gfx//charAttributes//faces.png"), 17, 14); // 12,2
		faces.setFilter(Image.FILTER_NEAREST);
		legs = new SpriteSheet(new Image("gfx//charAttributes//leg_strip.png"), 32, 64);
		legs.setFilter(Image.FILTER_NEAREST);
		hairs = new SpriteSheet(new Image("gfx//charAttributes//hairs.png"), 32, 32); // 0,0
		hairs.setFilter(Image.FILTER_NEAREST);
		// parent state based game
		psbg = sbg;

		input = gc.getInput();
		input.addMouseListener(mc);
		input.addKeyListener(kc);

		bg = new Image("gfx//over_grass.png");
		bg.setFilter(Image.FILTER_NEAREST);
		bg2 = new Image("gfx//under1.png");
		bg2.setFilter(Image.FILTER_NEAREST);
		power_room_image = new Image("gfx//room_power.png");
		power_room_image.setFilter(Image.FILTER_NEAREST);
		elevator_room_image = new Image("gfx//room_elevator.png");
		elevator_room_image.setFilter(Image.FILTER_NEAREST);
		water_room_image = new Image("gfx//room_water.png");
		water_room_image.setFilter(Image.FILTER_NEAREST);
		food_room_image = new Image("gfx//room_food.png");
		food_room_image.setFilter(Image.FILTER_NEAREST);
		init_fonts();

		resources = new Resources(this);

		/* init containers */

		try {
			rooms.addElement(new PowerRoom(320, Game.HEIGHT - 210, this));
			add_person(rooms.firstElement());
			menu = new MenuBar();
			ui.addElement(menu);
			info = new InfoDisp(resources, this);
			ui.addElement(info);
			cwin = new ControlWindow(400, 100, 0, 0, 4, 4, 2, f_24, this);
			ui.addElement(cwin);
			ewin = new EntityWindow(this);
			rwin = new BuildingSelectWindow(this);
			focused_win = cwin;
			ui.addElement(ewin);
			ui.addElement(rwin);

			menu = new MenuBar();
			menu.add_icon(new ImageButton(64, 64, 0, 0, new Image("gfx//globeicon.png"), fgetMethod("gotothaglobe"), this), "left");
			menu.add_icon(new ImageButton(64, 64, 0, 0, new Image("gfx//buildicon.png"), fgetMethod("show_build_menu"), this), "left");
			menu.add_icon(new ImageButton(64, 64, 0, 0, new Image("gfx//debug.png"), fgetMethod("show_ctrl_menu"), this), "left");
			menu.add_icon(new ImageButton(64, 64, 0, 0, new Image("gfx//icon_camera.png"), fgetMethod("reset_viewport"), this), "right");

			ui.addElement(menu);
		} catch (NoSuchMethodException | SecurityException e) {
			e.printStackTrace();

		}

		set_window_focus(ewin);
	}

	public void show_ctrl_menu() {
		cwin.hidden = false;
	}

	public void show_build_menu() {
		rwin.hidden = false;
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

	public boolean overlaps(float a_tl_x, float a_tl_y, float a_br_x, float a_br_y, float b_tl_x, float b_tl_y, float b_br_x, float b_br_y) {
		System.out.printf("%s\n", a_br_x <= b_tl_x || a_tl_x >= b_br_x ? "l/r" : "");
		System.out.printf("%s\n", a_br_y <= b_tl_y || a_tl_y >= b_br_y ? "u/d" : "");
		return !(a_br_x <= b_tl_x || a_tl_x >= b_br_x) && !(a_br_y <= b_tl_y || a_tl_y >= b_br_y);
	}

	public Room room_overlap(float b_tl_x, float b_tl_y, float b_br_x, float b_br_y) {
		for (Room a : rooms) {
			if (overlaps(a.x, a.y, a.x + a.sizex, a.y + a.sizey, b_tl_x, b_tl_y, b_br_x, b_br_y)) {
				return a;
			}
		}
		return null;
	}

	public void enter_placement_mode(String type) throws NoSuchMethodException, SecurityException, SlickException {
		System.out.println(type);
		switch (type) {
		case "power":
			new_room = new PowerRoom(0, 0, this);
			break;
		case "water":
			new_room = new WaterRoom(0, 0, this);
			break;
		case "elevator":
			new_room = new Elevator(0, 0, this);
			break;
		case "food":
			new_room = new FoodRoom(0, 0, this);
			break;
		default:
			new_room = new PowerRoom(0, 0, this);
			break;
		}
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

	public void update_containers(Vector<Container> elements, int delta, Boolean mousepress, int mx, int my) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		for (Iterator<Container> iter = elements.iterator(); iter.hasNext();) {
			Container cont = iter.next();
			if (focused_win != null) {
				focused_win.update(input, mx, my, delta);
			}

			if (!cont.is_focused) {
				cont.update(input, mx, my, delta);
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
	public void mouseDragged(int oldx, int oldy, int newx, int newy) {
		if (input.isMouseButtonDown(1)) {
			this.vp_x += newx - oldx;
			this.vp_y += newy - oldy;

			// i need some jdedmondt magic on this
			if (vp_x < -1280) {
				vp_x = -1280;
			}

			if (vp_y < -720) {
				vp_y = -720;
			}

			if (vp_x > 1280) {
				vp_x = 1280;
			}

			if (vp_y > 0) {
				vp_y = 0;
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
					r.update(input, mousex_rel, mousey_rel, delta);
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
			update_containers(ui, delta, mousepress, input.getMouseX(), input.getMouseY());
			update_containers(misc_renders, delta, mousepress, input.getMouseX(), input.getMouseY());
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e1) {
			e1.printStackTrace();
		}
		resources.update(delta);
		for (Iterator<Room> iter = rooms.iterator(); iter.hasNext();) {
			Room r = iter.next();
			try {
				r.update(input, mousex_rel, mousey_rel, delta);
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e1) {
				e1.printStackTrace();
			}
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

		// <drawing bg> ------------------------------------- (is it legal to mark code blocks this way?)
		if (vp_x < 0) {
			bg.draw(Game.WIDTH, 0);
			bg2.draw(Game.WIDTH, bg.getHeight() - 1);
		}

		if (vp_x > 0) {
			bg.draw(-Game.WIDTH, 0);
			bg2.draw(-Game.WIDTH, bg.getHeight() - 1);
		}

		bg.draw(0, 0);
		bg2.draw(0, bg.getHeight() - 1);
		// </drawing bg> -----------------------------------

		for (Room r : rooms) {
			r.draw(g);
		}

		if (ewin.selected_ent != null) {
			g.setColor(Color.red);
			g.setLineWidth(2);
			g.drawRect(ewin.selected_ent.x, ewin.selected_ent.y, ewin.selected_ent.hitbox.getWidth(), ewin.selected_ent.hitbox.getHeight());
		}

		g.translate(-vp_x, -vp_y);
		g.scale(1 / vp_zoom_scale, 1 / vp_zoom_scale);

		if (debug_info) {
			f_32.drawString(32, menu.sizey, String.format("(%d, %d), vp: (%d %d) %dx%d [%f], mr: (%d, %d)", input.getMouseX(), input.getMouseY(), vp_x, vp_y, vp_w, vp_h, vp_zoom_scale, mousex_rel, mousey_rel), Color.red);
			f_32.drawString(32, menu.sizey + 32, String.format("pop: %d, rms: %d", resources.n_staff, rooms.size()), Color.red);
			f_32.drawString(32, menu.sizey + 64, String.format("power_use %.3f, power_prod %.3f, pow_net %.4f, pow_store %.3f", resources.power_use, resources.power_prod, resources.net_power(), resources.power_store), Color.red);
			f_32.drawString(32, menu.sizey + 96, String.format("water_store %.3f, food_store %.3f", resources.water_store, resources.food_store), Color.red);
		}

		for (Container cont : ui) {
			if (!cont.is_focused) {
				cont.draw(g);
			}

		}
		for (Container cont : misc_renders) {
			cont.draw(g);
		}

		if (focused_win != null) {
			focused_win.draw(g);
		}

	}
}