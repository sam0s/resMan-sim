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
	Button rename;
	Button sizeb;
	Button deselect;
	Label debug_ln1;

	Container person_containers[];
	Container generic_containers[];
	Image grab_icon;
	ImageButton grab;
	InputBox entry_box;

	public EntityWindow(int padx, int pady, double weight, Font f, StateGame s) throws NoSuchMethodException, SecurityException, SlickException {
		super(300, 350, 100, 100, padx, pady, weight, f, "NULL");

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
		int width = StateGame.f_18.getWidth("Rename") + 16;
		int height = StateGame.f_18.getHeight("Rename") + 8;
		rename = new Button(width, height, 0, horizontal_rule.rely + (int) horizontal_rule.weight / 2 + pady, 2, "Rename", StateGame.f_18, fgetMethod("do_nothing"), this);
		rename.set_args((Object[]) null);

		// resize button (temporary)
		width = StateGame.f_18.getWidth("Sizem") + 16;
		height = StateGame.f_18.getHeight("Sizem") + 2;
		sizeb = new Button(StateGame.f_18.getWidth("Sizem") + 16, StateGame.f_18.getHeight("Sizem") + 2, rename.sizex + 32, horizontal_rule.rely + (int) horizontal_rule.weight / 2 + pady, 2, "Sizem", StateGame.f_18, fgetMethod("do_nothing"), this);
		sizeb.set_args((Object[]) null);

		// grab button
		grab = new ImageButton(64, 64, sizex - 72, sizeb.y, new Image("gfx//tweezicon.png"), fgetMethod("do_nothing"), this);
		grab.set_args((Object[]) null);
		
		// deselect button
		width = StateGame.f_18.getWidth("Deselect") + 16;
		height = StateGame.f_18.getHeight("Deselect") + 8;
		deselect = new Button(width, height, 0, sizey-height-pady-titlebar_height-(int)weight*2, 2, "Deselect", StateGame.f_18, fgetMethod("deselect_entity"), this);
		

		this.add_container(rename);
		this.add_container(grab);
		this.add_container(sizeb);
		this.add_container(deselect);

		Label debug_label = new Label(0, sizey - 120, 2, 2, 0, "debug", StateGame.f_18);
		this.add_container(debug_label);
		debug_ln1 = new Label(0, sizey - 120 + debug_label.sizey, 2, 2, 0, "null", StateGame.f_16);
		add_container(debug_ln1);
		
		for (Container c : containers) {
			c.setTheme(clear, outer);
		}
	}

	public void do_nothing() throws NoSuchMethodException, SecurityException {
		s.add_dialog("No entity currently selected!");
		return;
	}
	
	public void rename() throws NoSuchMethodException, SecurityException {
		if (entry_box == null) {
			entry_box = s.add_input_box();
		} 
	}
	
	public void deselect_entity() {
		activeEnt = null;
	}

	public void setEntity(Entity e) {
		activeEnt = e;
		try {
			if (activeEnt != null) {
				rename.set_func(fgetMethod("rename"), this);
				sizeb.set_func(activeEnt.fgetMethod("up_size"), activeEnt);
				grab.set_func(s.fgetMethod("grab_entity", Entity.class), s);
				grab.set_args(activeEnt);

			} else {
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
		if (entry_box != null && activeEnt != null) {
			int length = entry_box.text.length();
			if (length > 0 && entry_box.text.charAt(length-1) == '\n') {
				if (entry_box.text.charAt(0) == '\n') {
					try {
						s.add_dialog("You must enter at least one character");
					} catch (NoSuchMethodException | SecurityException e) {
						e.printStackTrace();
					}
				} else {
					activeEnt.set_name(entry_box.text.substring(0, length-1));
				}
				entry_box.destroy = true;
				entry_box = null;
			}
		}
	}

	@Override
	public void hide(){
		super.hide();
		activeEnt = null;
	}
	
	@Override
	public void draw(Graphics surface) throws SlickException {
		super.draw(surface);
		if (activeEnt!=null) {
			Image i = new Image(150, 150);
			surface.copyArea(i, (int) activeEnt.x - activeEnt.sprite.getWidth()*2, (int) activeEnt.y - activeEnt.sprite.getHeight()/2);
			i.draw(x+padx, y+150,100,100);
		}
	}

}
