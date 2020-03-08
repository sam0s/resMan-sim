import org.newdawn.slick.Input;
import org.newdawn.slick.MouseListener;


public class MouseControls implements MouseListener{
	StateGame s;
	public MouseControls(StateGame s){
		this.s=s;
	}
	@Override
	public void inputEnded() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void inputStarted() {
		// TODO Auto-generated method stub
		
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
	public void mouseClicked(int arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseDragged(int arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(int arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(int arg0, int arg1, int arg2) {
		/* check if mouse is over entity */
		for (Entity e : s.guys) {
			if (e.isClicked(s.mousex_rel, s.mousey_rel)) {
				if (s.ewin.hidden) {
					s.ewin.show();
				}
				s.ewin.setEntity(e);
				break;
			}
		}
	}

	@Override
	public void mouseReleased(int arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseWheelMoved(int arg0) {
		// TODO Auto-generated method stub
		System.out.println(arg0);
		switch(arg0){
		case 120:
			s.vp_zoom_scale -= .05;
			break;
		case -120:
			s.vp_zoom_scale += .05;
			break;
		}
	}

}
