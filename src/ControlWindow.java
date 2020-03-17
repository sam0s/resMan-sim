import java.lang.reflect.InvocationTargetException;
import java.util.Random;

import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

public class ControlWindow extends Window {
	StateGame s;
	Button newGuy;
	Button placeRoom;
	Button placeElevator;
	Button reset_vp;
	
	Label dad_name;
	Label mom_name;

	Human mom;
	Human dad;
	Human sel_person;
	
	Random r;
	
	public ControlWindow(int sizex, int sizey, int x, int y, int padx, int pady, double weight, Font f, StateGame s) throws NoSuchMethodException, SecurityException {
		super(300, 400, 420, 200, padx, pady, weight, "Important window", s);
		
		this.mom = null;
		this.dad = null;
		this.sel_person = null;
		this.s = s;
		
		r = new Random();
		
		Button set_dad = new Button(100, 32, 0, 150, 2, "Set dad", f, fgetMethod("set_dad"), this);
		add_container(set_dad);
		dad_name = new Label(0, set_dad.rely + set_dad.sizey + pady, 2, 2, 0, "Dad: NULL", s.f_18);
		add_container(dad_name);
		Button set_mom = new Button(100, 32, 0, dad_name.rely + dad_name.sizey + pady, 2, "Set mom", f, fgetMethod("set_mom"), this);
		add_container(set_mom);
		mom_name = new Label(set_mom.relx, set_mom.rely + set_dad.sizey + pady, 2, 2, 0, "Mom: NULL", s.f_18);
		add_container(mom_name);
		
		add_container(new Button(100, 32, 0, mom_name.rely + mom_name.sizey + pady, 2, "BREED", f, fgetMethod("breed"), this));
			
		add_container(new Label(0, 0, 4, 4, 0, "sniff snoff", StateGame.f_18));
		newGuy = new Button(100, 32, 0, 32, 2, "add 1", f, s.fgetMethod("add_person", Room.class), s);
		newGuy.set_args(s.rooms.elementAt(r.nextInt(s.rooms.size())));
		placeRoom = new Button(100, 32, 132, 32, 2, "add room", f, s.fgetMethod("enter_placement_mode", String.class), s);
		placeRoom.set_args("default");
		placeElevator = new Button(160, 32, 150, 120, 2, "add elevator", f, s.fgetMethod("enter_placement_mode", String.class), s);
		placeElevator.set_args("elevator");
		
		add_container(new Button(120, 32, 150,placeElevator.rely + placeElevator.sizey + pady, 2,
				"sub health", s.f_24, fgetMethod("sub_health"), this));

		
		
		reset_vp = new Button(f.getWidth("reset_viewport") + 10, 32, 0, 68, 2, "reset viewport", f, s.fgetMethod("reset_viewport"), s);
		reset_vp.set_args((Object[]) null);
		add_container(newGuy, placeRoom, placeElevator, reset_vp);
		
		add_container(new Button(140, 32, 0, reset_vp.rely + reset_vp.sizey + pady, 2, "Exterminate", f, s.fgetMethod("kill_all"), s));

	}

	public void update(Input i, int mx, int my, int delta) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		super.update(i, mx, my, delta);
		newGuy.set_args(s.rooms.elementAt(r.nextInt(s.rooms.size())));
	}
	
	public void set_sel_person(Human h) {
		this.sel_person = h;
	}
	
	public void clear_parents() {
		mom = null;
		mom_name.set_text("Mom: NULL");
		dad = null;
		dad_name.set_text("Dad: NULL");
	}
	
	public void set_dad() throws NoSuchMethodException, SecurityException {
		if (sel_person == null) {
			s.add_dialog("no ent selected");
		} else if (sel_person.gender == Game.MALE) {
			this.dad = sel_person;
			dad_name.set_text(String.format("Dad: %s", sel_person.name));
		} else {
			s.add_dialog("set DAD not set MOM");
		}
	}
	
	public void set_mom() throws NoSuchMethodException, SecurityException {
		if (sel_person == null) {
			s.add_dialog("no ent selected");
		} else if (sel_person.gender == Game.FEMALE) {
			this.mom = sel_person;
			mom_name.set_text(String.format("Mom: %s", sel_person.name));
		} else {
			s.add_dialog("set MOM not set DAD");
		}
	}
	
	public void breed() throws NoSuchMethodException, SecurityException, SlickException {
		if (mom != null && dad != null) {
			Human child = mom.create_offspring(dad);
			mom.curRoom.add_entity(child);
			s.guys.addElement(child);
		} else {
			s.add_dialog("mom and dad must be selected");
		}
	}
	
	public void sub_health() {
		if (sel_person == null) {
			return;
		} else {
			sel_person.hp -= 10;
		}
	}

}
