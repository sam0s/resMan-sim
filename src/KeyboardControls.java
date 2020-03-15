import org.newdawn.slick.Input;
import org.newdawn.slick.KeyListener;

import java.util.Iterator;
import java.util.Vector;


public class KeyboardControls implements KeyListener {

	StateGame s;
	
	int delta;
	Vector<Integer> keys;
	boolean shift;
	boolean ctrl;
	
	static final int LSHIFT = 54;
	static final int RSHIFT = 58;
	static final int LARROW = 203;
	static final int RARROW = 205;
	static final int UPARROW = 200;
	static final int DWNARROW = 208;
	static final int LCTRL = 29;
	static final int RCTRL = 157;
	
	static final int KEY_D = 32;
	
	public KeyboardControls(StateGame s) {
		this.s = s;
		this.delta = 0;
		this.keys = new Vector<Integer>();
		
		this.shift = false;
		this.ctrl = false;
	}
	
	public void set_delta(int delta) {
		this.delta = delta;
	}
	
	@Override
	public void inputEnded() {

	}

	@Override
	public void inputStarted() {
		// TODO Auto-generated method stub
		for (int k: keys) {
			switch(k) {
			case 1: /* escape */
				s.mode = "general"; 
				break;
			case LARROW:
			case 30:  		/* w 		*/
				s.vp_x += 10;
				break;
			case RARROW: 	
			case KEY_D:		/* d 		*/
				s.vp_x -= 10;
				break;
			case UPARROW:
			case 17:		/* w		*/
				s.vp_y += 10;
				break;
			case DWNARROW:
			case 31:		/* s		*/
				s.vp_y -= 10;
				break;
			}
		}
	}

	@Override
	public boolean isAcceptingInput() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void setInput(Input arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(int arg0, char arg1) {
		System.out.println(arg0);
		switch(arg0) {
		case LSHIFT:
		case RSHIFT:
			shift = true;
			break;
		case LCTRL:
		case RCTRL:
			ctrl = true;
			break;
		case KEY_D:
			if (ctrl) {
				s.debug_info ^= true;
				return;
			}
			break;
		}
		keys.addElement(arg0);
	}

	@Override
	public void keyReleased(int arg0, char arg1) {
		keys.removeIf(n -> ((Integer)n.intValue() == arg0));
		switch (arg0) {
		case LSHIFT:
		case RSHIFT:
			shift = false;
			break;
		case LCTRL:
		case RCTRL: 
			ctrl = false;
			break;
		}
	}

}
