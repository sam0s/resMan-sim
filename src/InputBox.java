import org.newdawn.slick.Font;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.KeyListener;
import org.newdawn.slick.SlickException;

public class InputBox extends Container implements KeyListener{
	Input i;
	String text = "";
	Font f;
	
	public static final int BACKSPACE = 14;
	public static final int RSHIFT = 56;
	public static final int LSHIFT = 42;
	public static final int ENTER = 28;
	
	public InputBox(int sizex, int sizey, float x, float y,double weight, Font f, Input i) {
		super(sizex, sizey, x, y, 2, 2, weight);
		this.i=i;
		this.i.addKeyListener(this);
		this.f = f;
	}
	
	public String insert_char(String text, char ch, int idx) {
		int len = text.length();
		char[] new_array = new char[len +1];
		text.getChars(0, idx, new_array, 0);
		new_array[idx] = ch;
		text.getChars(idx, len, new_array, idx+1);
		return new String(new_array);		
	}
	
	@Override
	public void draw(Graphics surface){
		try {
			super.draw(surface);
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		/* process string to see if it will extend beyond container */
		
		String print_str = text;
		
		int beg = 0;
		int i = 0;
		for (i = 0; i < print_str.length(); i++) {
			if (f.getWidth(print_str.substring(beg, i)) > sizex-8) {
				print_str = insert_char(print_str, '\n', i);
				beg = i;
			}
		}
		
		String split_text[] = print_str.split("\n");
		for (int j = 0; j < split_text.length && (j+1) * f.getHeight(split_text[j]) < sizey; j++) {
			f.drawString(x, y + (j * f.getHeight(split_text[j])), split_text[j]);
		}		
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
		System.out.printf("pressed, %d %c\n", arg0,arg1);

		StringBuilder sb=new StringBuilder(text);
		switch (arg0) {
		case BACKSPACE:
			if (text.length() > 0) {
				sb.setLength(sb.length() - 1);
				text=sb.toString();
			}
			break;
		case RSHIFT:
		case LSHIFT:
		case ENTER:
			text+="\n";
			break;
		default:
			text+=arg1;
		}
	}

	@Override
	public void keyReleased(int arg0, char arg1) {
		// TODO Auto-generated method stub
		
	}

}
