import org.newdawn.slick.Color;
import org.newdawn.slick.Font;


public class EntityWindow extends Window{

	Entity activeEnt;
	
	public EntityWindow(int sizex, int sizey, int x, int y, int padx, int pady, Color inner, Color outer, double weight, Font f, String title) throws NoSuchMethodException, SecurityException {
		super(sizex, sizey, x, y, padx, pady, inner, outer, weight, f, title);
	}
	
	public void setEntity(Entity e){
		title = e.name;
		activeEnt = e;
		
	}

}
