public class MenuBar extends Container {
	
	public MenuBar() {
		super(Game.WIDTH+4, 68, -2, -2, 2, 2, 2);
	}
	
	public void add_icon(ImageButton i) {
		i.relx = containers.length*i.sizex + (containers.length+1)*padx;
		add_container(i);
	}
}
