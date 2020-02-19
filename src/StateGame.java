import java.awt.FontFormatException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Random;

import org.newdawn.slick.*;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class StateGame extends BasicGameState {
    double theta;
    Image i;
    Container menu;
    Input input;
    java.awt.Font fontRaw;
    Font f_32;
    Font f_18;
    Font f_24;
    Font f_16;
    long frameTime = 0;
    Button menuBtn;
    Button btn2;
    Button btn3;
    Button btn_showattr;
    Window test_window;
    int[] mouse_pos;
    Random b = new Random();
    
    Sound soundbyte;

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

    @Override
    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
	i = new Image("hogBoss.jpg");
	soundbyte = new Sound("cooom.ogg");
	Color outer = new Color(255, 0, 0);
	Color inner = new Color(255, 255, 255, 50);
	theta = 0;
	input = gc.getInput();
	menu = new Container(500, 64, 200, 500, inner, outer, 2.5);
	fontRaw = null;
	mouse_pos = getMouse();

	try {
	    fontRaw = java.awt.Font.createFont(java.awt.Font.TRUETYPE_FONT, new java.io.File("FreeMonoBold.ttf"));
	} catch (FontFormatException e) {
	    e.printStackTrace();

	} catch (IOException e) {
	    e.printStackTrace();
	}
	if (fontRaw == null) {
	    fontRaw = new java.awt.Font("Default", 0, 28);
	}
	f_32 = new TrueTypeFont(fontRaw.deriveFont(32f), false);
	f_24 = new TrueTypeFont(fontRaw.deriveFont(24f), false);
	f_18 = new TrueTypeFont(fontRaw.deriveFont(18f), false);
	f_16 = new TrueTypeFont(fontRaw.deriveFont(16f), false);
	
	Color window_inner = new Color(40, 40, 40, 225);
	Color window_outer = Color.orange;

	try {
	    menuBtn = new Button(100, 64, 4, 4, window_inner, window_outer, 2, "New", f_24, fgetMethod("hello"), this);
	    btn2 = new Button(100, f_24.getHeight("buton")+4, 4,  menuBtn.rely+menuBtn.sizey+4, window_inner, window_outer, 2, "buton", f_24, fgetMethod("moveMe"), this);
	    btn3 = new Button(100, f_24.getHeight("COOM")+4, 4, btn2.rely + btn2.sizey+4, window_inner, window_outer, 2, "COOM", f_24, fgetMethod("coom"), this);
	    test_window = new Window(300, 600, 50, 50, window_inner, window_outer, 2, f_18, "Attributes");
	    btn_showattr = new Button(100,45, 4,20,Color.black, Color.red, 2, "Show Attr.",f_24,test_window.fgetMethod("show"),test_window);
	} catch (NoSuchMethodException | SecurityException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}

	test_window.add_button(menuBtn);
	test_window.add_button(btn2);
	test_window.add_button(btn3);
	menu.add_button(btn_showattr);
    }

    public void moveMe() {
	btn2.set_size(b.nextInt(Game.WIDTH), b.nextInt(Game.WIDTH));

    }
    
    public void coom() {
    	soundbyte.play();
    }

    public void hello() throws SlickException {
	i = new Image("img.png");
    }

    public float[] travel_to_point(float curx, float cury, float destx, float desty, float speed, int delta) {
	/*
	 * delta is in miliseconds, so divide by 1000 to get seconds. multiply
	 * by speed (pixels/s) to get number of pixels we need to move. finally,
	 * multiply by curpos - destpos to make movement proportional to
	 * distance from target.
	 */

	curx -= speed * (delta / 1000f) * (curx - destx);
	cury -= speed * (delta / 1000f) * (cury - desty);

	return new float[] { curx, cury };
    }

    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
	mouse_pos = getMouse();

	// mouse_pos[0] -= menu.sizex/2;
	// mouse_pos[1] -= menu.sizey/2;

	float new_point[] = travel_to_point(menu.x, menu.y, mouse_pos[0], mouse_pos[1], 4, delta);
	float diff_avg = ((menu.x - mouse_pos[0]) + (menu.y - mouse_pos[1])) / 4;

	menu.set_size(500, 64);

	if (mouse_pos[1] > 600) {
	    new_point = travel_to_point(menu.x, menu.y, 0, 620, 4, delta);
	    menu.y = new_point[1];
	    // menu.sizex += diff_avg;
	    // menu.sizey += diff_avg;
	} else {
	    new_point = travel_to_point(menu.x, menu.y, Game.WIDTH / 2 - menu.sizex / 2, Game.HEIGHT, 3, delta);
	    menu.x = new_point[0];
	    menu.y = new_point[1];
	}

	try {
	    test_window.update(input, delta);
	    menu.update(input);
	} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}

	// eat mouse input
	if (input.isMousePressed(0)) {
	    System.out.printf("nothing!");
	}

    }

    @Override
    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
	i.draw(210, 140, 200, 200);
	f_32.drawString(32, 32, String.format("Coomer Shelter (%d, %d)", getMouse()[0], getMouse()[1]), Color.orange);
	menu.draw(g);
	f_24.drawString(menu.x + 4, menu.y + 4, "Menu", Color.orange);
	test_window.draw(g);
    }
}