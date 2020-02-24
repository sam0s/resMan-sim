import java.lang.reflect.InvocationTargetException;

import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

public class EntityWindow extends Window {

	Entity activeEnt;
	StateGame s;
	Button rename;
	Button sizeb;
	Label debug_ln1;

	Container person_containers[];
	Container generic_containers[];
	Image grab_icon;
	ImageButton grab;

	public EntityWindow(int sizex, int sizey, int x, int y, int padx, int pady, double weight, Font f, StateGame s) throws NoSuchMethodException, SecurityException, SlickException {
		super(sizex, sizey, x, y, padx, pady, weight, f, "NULL");

		this.activeEnt = null;
		this.s = s;
		Color clear = new Color(0, 0, 0, 0);

		Label age = new Label(0, 0, 2, 2, 0, "Age: NULL", StateGame.f_18);
		this.add_container(age);
		Label sex = new Label(0, age.rely + age.sizey, 2, 2, 0, "Sex: MALE", StateGame.f_18);
		this.add_container(sex);
		Label hp = new Label(age.relx + age.sizex + 30, 0, 2, 2, 0, "HP: 100", StateGame.f_18);
		this.add_container(hp);
		Label happiness = new Label(hp.relx, hp.rely + hp.sizey, 2, 2, 0, "Happiness: 100", StateGame.f_18);
		this.add_container(happiness);
		Container horizontal_rule = new Container(sizex, 0, -padx, sex.rely + sex.sizey + pady, 0, 0, 2);
		this.add_container(horizontal_rule);
		
		// rename button
		rename = new Button(StateGame.f_18.getWidth("Rename") + 16, StateGame.f_18.getHeight("Rename") + 8, 0, horizontal_rule.rely + (int) horizontal_rule.weight / 2 + pady, 2, "Rename", StateGame.f_18, fgetMethod("do_nothing"), this);
		rename.set_args((Object[]) null);
		
		//resize button (temporary)
		sizeb = new Button(StateGame.f_18.getWidth("Sizem") + 16, StateGame.f_18.getHeight("Sizem") + 2, rename.sizex+32, horizontal_rule.rely + (int) horizontal_rule.weight / 2 + pady, 2, "Sizem", StateGame.f_18, fgetMethod("do_nothing"), this);
		sizeb.set_args((Object[]) null);
		
		//grab button
		grab = new ImageButton(64,64,sizex-72,sizeb.y,new Image("gfx//tweezIcon.png"),fgetMethod("do_nothing"), this);
		grab.set_args((Object[]) null);
		
		this.add_container(rename);
		this.add_container(grab);
		this.add_container(sizeb);

		Label debug_label = new Label(0, sizey - 100, 2, 2, 0, "debug", StateGame.f_18);
		this.add_container(debug_label);
		debug_ln1 = new Label(0, sizey - 100 + debug_label.sizey, 2, 2, 0, "null", StateGame.f_16);
		add_container(debug_ln1);

		for (Container c : containers) {
			c.setTheme(clear, outer);
		}
	}

	public void do_nothing() throws NoSuchMethodException, SecurityException {
		s.add_dialog("No entity currently selected!");
		return;
	}

	public void setEntity(Entity e) {
		activeEnt = e;
		try {
			if (activeEnt != null) {
				rename.set_args("hog boss");
				rename.set_func(activeEnt.fgetMethod("set_name", String.class), activeEnt);
				sizeb.set_func(activeEnt.fgetMethod("up_size"), activeEnt);
				grab.set_func(s.fgetMethod("grab_entity", Entity.class), s);
				grab.set_args(activeEnt);

			} else {
				rename.set_args((Object[]) null);
				rename.set_func(fgetMethod("do_nothing"), this);
				sizeb.set_args((Object[]) null);
				sizeb.set_func(fgetMethod("do_nothing"), this);
				grab.set_args((Object[]) null);
				grab.set_func(fgetMethod("do_nothing"), this);
			}
		} catch (NoSuchMethodException | SecurityException e1) {
		}
	}

	public void update(Input i, int delta) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		super.update(i, delta);
		title = (activeEnt != null) ? activeEnt.name : "";
		if (activeEnt != null) {
			debug_ln1.set_text(String.format("(%.0f, %.0f) | Dir: %d", activeEnt.x, activeEnt.y, activeEnt.roamDir));
		} else {
			debug_ln1.set_text("null");
		}
	}

}
