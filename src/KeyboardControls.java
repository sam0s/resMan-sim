import org.newdawn.slick.Input;
import org.newdawn.slick.KeyListener;

import java.util.Iterator;
import java.util.Vector;


public class KeyboardControls implements KeyListener {

	StateGame s;
	
	Vector<Integer> keys = new Vector<Integer>();
	
	public KeyboardControls(StateGame s) {
		this.s = s;
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
			case 203: 	/* LARROW 	*/
			case 30:  	/* w 		*/
				s.vp_x += 1;
				break;
			case 205: 	/* RARROW 	*/
			case 32:	/* d 		*/
				s.vp_x -= 1;
				break;
			case 200: 	/* UPARROW 	*/
			case 17:	/* w		*/
				s.vp_y += 1;
				break;
			case 208: 	/* DWNARROW */
			case 31:	/* s		*/
				s.vp_y -= 1;
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
		keys.addElement(arg0);
	}

	@Override
	public void keyReleased(int arg0, char arg1) {
		keys.removeIf(n -> ((Integer)n.intValue() == arg0));
	}

}
