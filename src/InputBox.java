import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.KeyListener;
import org.newdawn.slick.SlickException;

public class InputBox extends Container implements KeyListener{
	Input i;
	String text = "";
	public InputBox(int sizex, int sizey, float x, float y,double weight,Input i) {
		super(sizex, sizey, x, y, 2, 2, weight);
		this.i=i;
		this.i.addKeyListener(this);
	}
	
	@Override
	public void draw(Graphics surface){
		try {
			super.draw(surface);
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		surface.drawString(text, x, y);
		
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
		return true;
	}

	@Override
	public void setInput(Input arg0) {
		this.i=arg0;
	}

	@Override
	public void keyPressed(int arg0, char arg1) {
		//backspace
		StringBuilder sb=new StringBuilder(text);
		if (text.length()>0 && arg0 == 14){
			sb.setLength(sb.length() - 1);
			text=sb.toString();
		}else{
		System.out.printf("pressed, %d %c\n", arg0,arg1);
		text+=arg1;
		}
	}

	@Override
	public void keyReleased(int arg0, char arg1) {
		// TODO Auto-generated method stub
		
	}

}
