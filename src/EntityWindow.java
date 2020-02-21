import java.lang.reflect.InvocationTargetException;

import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.Input;

public class EntityWindow extends Window {

	Entity activeEnt;

	Container person_containers[];
	Container generic_containers[];

	public EntityWindow(int sizex, int sizey, int x, int y, int padx, int pady, double weight, Font f) throws NoSuchMethodException, SecurityException {
		super(sizex, sizey, x, y, padx, pady, weight, f, "NULL");

		this.activeEnt = null;
		Color clear = new Color(0, 0, 0, 0);

		Label age = new Label(0, 0, 2, 2, 0, "Age: NULL", StateGame.f_18);
		age.setTheme(clear,  outer);
		this.add_container(age);
		Label sex = new Label(0, age.rely + age.sizey, 2, 2, 0, "Sex: MALE", StateGame.f_18);
		sex.setTheme(clear,  outer);
		this.add_container(sex);
		Label hp = new Label(age.relx + age.sizex + 30, 0, 2, 2, 0, "HP: 100", StateGame.f_18);
		hp.setTheme(clear,  outer);
		this.add_container(hp);
		Label happiness = new Label(hp.relx, hp.rely + hp.sizey, 2, 2, 0, "Happiness: 100", StateGame.f_18);
		happiness.setTheme(clear,  outer);
		this.add_container(happiness);
		Container horizontal_rule = new Container(sizex, 0, -padx, sex.rely + sex.sizey + pady, 0, 0, 2);
		horizontal_rule.setTheme(clear,  outer);
		this.add_container(horizontal_rule);

		Button rename = new Button(StateGame.f_18.getWidth("Rename") + 16, 
				StateGame.f_18.getHeight("Rename") + 8, 0, 
				horizontal_rule.rely + 4 + pady, inner, outer, 2, "Rename", 
				StateGame.f_18, fgetMethod("do_nothing"), this);
		rename.set_args("Mr. Hog Boss");
		this.add_container(rename);

	}
	
	public void do_nothing() {
		return;
	}

	public void setEntity(Entity e) {
		activeEnt = e;		
		if (activeEnt != null) {
			try {
				((Button)this.containers[6]).gc = activeEnt;
				((Button)this.containers[6]).set_func(activeEnt.fgetMethod("set_name", String.class));
			} catch (NoSuchMethodException | SecurityException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}		
	}

	public void update(Input i, int delta) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		super.update(i, delta);
		title = (activeEnt != null) ? activeEnt.name : "";
	}

}
