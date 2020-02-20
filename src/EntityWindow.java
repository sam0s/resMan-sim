import org.newdawn.slick.Color;
import org.newdawn.slick.Font;


public class EntityWindow extends Window{

	Entity activeEnt;
	
	Container person_containers[];
	Container generic_containers[];
	
	public EntityWindow(int sizex, int sizey, int x, int y, int padx, int pady, Color inner, Color outer, double weight, Font f) throws NoSuchMethodException, SecurityException {
		super(sizex, sizey, x, y, padx, pady, inner, outer, weight, f, "NULL");
		
		this.activeEnt = null;
		
		Label name = new Label(0, 0, 2, 2, new Color(0, 0, 0, 0), outer, 1, "Name: NULL", StateGame.f_18);
		Label age = new Label(0, name.sizey, 2, 2, new Color(0, 0, 0, 0), outer, 1, "Age: NULL", StateGame.f_18);

		this.add_container(name, age);
	}
	
	public void setEntity(Entity e){
		title = e.name;
		activeEnt = e;
		
	}
	
	public void update() {
		if (activeEnt != null) {
			((Label)containers[0]).set_text("meme");
		}
	}
	
	

}
