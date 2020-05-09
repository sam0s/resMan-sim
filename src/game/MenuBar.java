package game;
import java.util.Vector;

public class MenuBar extends Container {
	
	int max_icons;
	Vector<ImageButton> icons_right;
	int n_icons_right;
	Vector<ImageButton> icons_left;
	int n_icons_left;
	
	public MenuBar() {
		super(Game.WIDTH+4, 64 + 4, -2, -2, 2, 2, 2);
		this.max_icons = (int)Math.floor(this.sizex/64);
		this.icons_left = new Vector<ImageButton>();
		this.n_icons_right = 0;
		this.icons_right = new Vector<ImageButton>();
		this.n_icons_left = 0;
	}
	
	public void add_icon(ImageButton i, String just) {
		switch(just) {
		case "left":
			i.relx = n_icons_left*i.sizex + (n_icons_left+1)*padx;
			n_icons_left++;
			break;
		case "right":
			i.relx = n_icons_right*i.sizex + (n_icons_right+1)*padx;
			i.relx = this.sizex - i.relx - i.sizex - padx*2;
			n_icons_right++;
			break;
		}
		add_container(i);
	}
}
