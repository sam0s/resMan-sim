import java.lang.reflect.InvocationTargetException;

import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

public class EntityWindow extends Window {

	Entity activeEnt;
	StateGame s;
	
	Label age;
	Label sex;
	Label hp;
	Label morale;
	
	Label father;
	Label mother;
	
	Button rename;
	Button deselect;
	Label debug_ln1;
	Image prev;
	Image grab_icon;
	ImageButton grab;

	public EntityWindow(int padx, int pady, double weight, Font f, StateGame s) throws NoSuchMethodException, SecurityException, SlickException {
		super(300, 350, 100, 100, padx, pady, weight, f, "NULL", s);

		this.activeEnt = null;
		Color clear = new Color(0, 0, 0, 0);
		this.s = s;
		age = new Label(0, 0, 2, 2, 0, "Age: NULL", s.f_18);
		this.add_container(age);
		sex = new Label(age.relx + age.sizex + 20, 0, 2, 2, 0, "Sex: NULL", s.f_18);
		this.add_container(sex);
		hp = new Label(0, age.rely + age.sizey, 2, 2, 0, "HP: 100", s.f_18);
		this.add_container(hp);
		morale = new Label(sex.relx, hp.rely, 2, 2, 0, "Morale: NULL", s.f_18);
		this.add_container(morale);
		Container horizontal_rule = new Container(sizex, 0, -padx, hp.rely + hp.sizey + pady, 0, 0, 2);
		this.add_container(horizontal_rule);

		// rename button
		int width = StateGame.f_18.getWidth("Rename") + 16;
		int height = StateGame.f_18.getHeight("Rename") + 8;
		rename = new Button(width, height, 0, horizontal_rule.rely + (int) horizontal_rule.weight / 2 + pady, 2, "Rename", StateGame.f_18, fgetMethod("do_nothing"), this);
		rename.set_args((Object[]) null);
		
		father = new Label(0, rename.rely + rename.sizey + pady, 2, 2, 0, "Father: NULL", s.f_18);
		mother = new Label(0, father.rely + father.sizey + pady, 2, 2, 0, "Mother: NULL", s.f_18);
		add_container(father, mother);

		// grab button
		grab = new ImageButton(64, 64, sizex - 72, horizontal_rule.rely + pady, new Image("gfx//tweezicon.png"), fgetMethod("do_nothing"), this);
		grab.set_args((Object[]) null);

		// deselect button
		width = StateGame.f_18.getWidth("Deselect") + 16;
		height = StateGame.f_18.getHeight("Deselect") + 8;
		deselect = new Button(width, height, 0, sizey - height - pady - titlebar_height - (int) weight * 2, 2, "Deselect", StateGame.f_18, fgetMethod("deselect_entity"), this);

		this.add_container(rename, grab, deselect);

		debug_ln1 = new Label(0, sizey - 90, 2, 2, 0, "null", StateGame.f_16);
		add_container(debug_ln1);
		prev = new Image(155, 155);
		for (Container c : containers) {
			c.setTheme(clear, outer_f, clear, outer);
		}

	}

	public void do_nothing() throws NoSuchMethodException, SecurityException {
		s.add_dialog("No entity currently selected!");
		return;
	}

	public void rename() throws NoSuchMethodException, SecurityException {
		if (activeEnt != null) {
			InputBox ib = s.add_input_box(this.fgetMethod("set_ent_name", String.class), this);
		} else {
			s.add_dialog("No entity currently selected!");
		}
	}

	public void set_ent_name(String name) {
		activeEnt.set_name(name);
	}

	public void deselect_entity() throws NoSuchMethodException, SecurityException {
		activeEnt = null;
		father.set_text("Father: NULL");
		mother.set_text("Mother: NULL");
		disableButtons();
	}

	private void disableButtons() throws NoSuchMethodException, SecurityException {
		rename.set_func(fgetMethod("do_nothing"), this);
		grab.set_args((Object[]) null);
		grab.set_func(fgetMethod("do_nothing"), this);
	}

	public void setEntity(Entity e) throws NoSuchMethodException, SecurityException {
		activeEnt = e;
		rename.set_func(fgetMethod("rename"), this);
		grab.set_func(s.fgetMethod("grab_entity", Entity.class), s);
		grab.set_args(activeEnt);
		
		sex.set_text(String.format("Sex: %s", ((Human)activeEnt).gender ? "Male" : "Female"));
		age.set_text(String.format("Age: %d", ((Human)activeEnt).age));
		hp.set_text(String.format("HP: %d", ((Human)activeEnt).hp));
		morale.set_text(String.format("Morale: %d", ((Human)activeEnt).morale));
		
		father.set_text(((Human)activeEnt).father != null ? 
						((Human)activeEnt).father.name :
						"Father: NULL");
		mother.set_text(((Human)activeEnt).mother != null ? 
				((Human)activeEnt).mother.name :
				"Mother: NULL");
	}

	public void update(Input i, int mx, int my, int delta) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		super.update(i, mx, my, delta);
		set_title((activeEnt != null) ? activeEnt.name : "");
		if (activeEnt != null) {
			debug_ln1.set_text(String.format("(%.0f, %.0f) | Dir: %d", activeEnt.x, activeEnt.y, activeEnt.roamDir));
			age.set_text(String.format("Age: %d", ((Human)activeEnt).age));
		} else {
			debug_ln1.set_text("null");
		}
	}

	@Override
	public void hide() {
		super.hide();
		activeEnt = null;
	}

	@Override
	public void draw(Graphics surface) throws SlickException {
		super.draw(surface);
		if (activeEnt != null) {
			surface.copyArea(prev, (int) activeEnt.x - activeEnt.sprite.getWidth() * 2, (int) activeEnt.y - activeEnt.sprite.getHeight() / 2);
			prev.draw(x + padx, y + 190, 100, 100);
		}
	}

}
