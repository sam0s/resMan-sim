package game;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Rectangle;

import java.util.Random;

public class Human extends Entity {
	private static final long serialVersionUID = 1L;

	public static String[] namesM = { "Glenn", "Jerry", "Joe", "Jack", "Paul", "Nick", "Trevor", "Mathew", "Todd", "Linus", "Harry", "Walter", "Ryan", "Bob", "Henry", "Brian", "Dennis" };
	public static String[] namesF = { "Stephanie", "Susan", "Patricia", "Kim", "Rachel", "Rebecca", "Alice", "Jackie", "Judy", "Heidi", "Skylar", "Anna", "Paige" };
	public static String[] namesL = { "Rollins", "Howard", "Zalman", "Bell", "Newell", "Caiafa", "Finnegan", "Hall", "Howell", "Kernighan", "Wilson", "Ritchie" };

	transient Random r;

	String first_name;
	String last_name;
	boolean gender;
	int age;
	int expression;
	int hair_color;
	int eye_color;
	int skin_color;
	int outfit = 0;
	int morale;
	int level;
	
	/* behavior stuff */
	float hunger;
	float energy;

	Human father;
	Human mother;
	transient Image eyes;
	transient Image face;
	transient Image hair;
	transient Image head;
	transient Animation legs;
	boolean dead;

	public void anim_walk() {
		legs.setSpeed((float) 0.01);
		legs.start();

	}

	public void anim_idle() {
		legs.stop();
		legs.setCurrentFrame(1);
	}

	public Human(int x, int y) throws SlickException, NoSuchMethodException, SecurityException {
		super("temp", x, y, true);
		r = new Random();

		gender = r.nextInt(2) == 1;
		first_name = gender ? namesM[r.nextInt(namesM.length)] : namesF[r.nextInt(namesF.length)];
		last_name = namesL[r.nextInt(namesL.length)];
		name = first_name + " " + last_name;

		age = (int) Math.round(r.nextGaussian() * 5 + 25) + 16;
		System.out.printf("age: %d\n", age);
		hair_color = r.nextInt(6);
		eye_color = r.nextInt(6);
		skin_color = r.nextInt(5);
		outfit = r.nextInt(4);
		expression = r.nextInt(3);

		level = r.nextInt(10) + 1;
		hp_max = 10 * level;
		hp = r.nextInt(hp_max);
		morale = r.nextInt(101);
		father = null;
		mother = null;

		dead = false;

		/* outfit */
		setSpriteLoad(gender ? "guy" : "girl");

		/* eyes */
		eyes = StateGame.eyes.copy();
		int ec[] = Traits.get_eye_color(eye_color);
		eyes.setImageColor(ec[0] / 255f, ec[1] / 255f, ec[2] / 255f);

		/* face */
		face = StateGame.faces.getSprite(expression, 0);

		/* hair */
		hair = StateGame.hairs.getSprite(r.nextInt(4), 0);
		int hc[] = Traits.get_hair_color(hair_color);
		// System.out.printf("%d %d %d\n", hc[0], hc[1], hc[2]);
		hair.setImageColor(hc[0] / 255f, hc[1] / 255f, hc[2] / 255f);
		
		hunger = 0.0f;
		energy = 100.0f;

	}

	public void onLoad(Room rm) throws SlickException {

		Random r = new Random();
		setSpriteLoad(gender ? "guy" : "girl");

		/* eyes */
		eyes = StateGame.eyes.copy();
		int ec[] = Traits.get_eye_color(this.eye_color);
		eyes.setImageColor(ec[0] / 255f, ec[1] / 255f, ec[2] / 255f);

		/* expression */
		face = StateGame.faces.getSprite(expression, 0);

		/* hair */
		hair = StateGame.hairs.getSprite(r.nextInt(4), 0);
		int hc[] = Traits.get_hair_color(this.hair_color);
		hair.setImageColor(hc[0] / 255f, hc[1] / 255f, hc[2] / 255f);

		setRoom(rm);
		System.out.println(name);
	}

	public void set_name(String first_name, String last_name) {
		this.first_name = first_name;
		this.last_name = last_name;
		this.name = first_name + " " + last_name;
	}

	public void set_gender(boolean gender) {
		this.gender = gender;
	}

	public void setSpriteLoad(String spr_name) throws SlickException {
		// later make this a list accessible by the class;
		System.out.println("loading imags");
		head = StateGame.head.copy();
		legs = new Animation(StateGame.legs, 1);
		legs.setPingPong(true);
		anim_walk();
		if (outfit == 0) {
			sprite = new Image("gfx//charAttributes//default//default_" + spr_name + ".png");
		} else {
			// i have ideas for optimizing this, but for not this will do
			// it is possible to have all occurrences of the same outfit share the same reference image
			sprite = new Image("gfx//charAttributes//default//" + Traits.get_outfit(outfit));
		}
		sprite.setFilter(Image.FILTER_NEAREST);
		int sc[] = Traits.get_skin_color(skin_color);
		head.setImageColor(sc[0] / 255f, sc[1] / 255f, sc[2] / 255f);
		this.hitbox = new Rectangle(x, y, sprite.getWidth(), sprite.getHeight());
	}

	public void setRoom(Room r) {
		curRoom = r;
		x = r.x;
		y = r.y + r.sizey - sprite.getHeight() * sizey;
		this.r = new Random();
		int yx = (this.r.nextInt((int) r.sizex - sprite.getWidth()) + (int) r.x);
		System.out.println("ss " + yx);
		move_to_point(yx);
	}

	public void set_age(int age) {
		this.age = age;
	}

	public void set_traits(int hair_color, int eye_color, int skin_color) {
		this.hair_color = hair_color;
		this.eye_color = eye_color;
		this.skin_color = skin_color;
	}

	public void reset_colors() {
		int ec[] = Traits.get_eye_color(this.eye_color);
		int sc[] = Traits.get_skin_color(this.skin_color);
		eyes.setImageColor(ec[0] / 255f, ec[1] / 255f, ec[2] / 255f);
		// head.setImageColor(sc[0] / 255f, sc[1] / 255f, sc[2] / 255f);
		int hc[] = Traits.get_hair_color(hair_color);
		hair.setImageColor(hc[0] / 255f, hc[1] / 255f, hc[2] / 255f);
	}

	public Human create_offspring(Human father) throws NoSuchMethodException, SecurityException, SlickException {
		Human child = new Human((int) curRoom.x, (int) curRoom.y);
		int hc = (int) ((this.hair_color + father.hair_color) / 2f);
		int ec = (int) ((this.eye_color + father.eye_color) / 2f);
		int sc = (int) ((this.skin_color + father.skin_color) / 2f);
		child.set_name(child.gender ? namesM[r.nextInt(namesM.length)] : namesF[r.nextInt(namesF.length)], father.last_name);
		child.set_traits(hc, ec, sc);
		child.set_age(0);
		child.father = father;
		child.mother = this;
		child.reset_colors();
		return child;
	}
	
	float timespan;
	
	public void update(int delta) {
		super.update(delta);
		
		hunger += 0.1 * (delta/1000f);
		energy -= 0.05 * (delta/1000f);
		if (hunger > .5) {
			timespan += delta;
			if (timespan/1000f > 5) { /* lose 1 hp every 5 seconds if really hungry */
				hp -= 1;
				timespan = 0;
			}
		}
	}
	
	public void draw(Graphics surface) {
		head.draw(x, y);
		legs.draw(x, y);
		sprite.draw(x + ((direction == -1) ? sprite.getWidth() : 0), y, (direction == -1) ? -sprite.getWidth() : sprite.getWidth(), sprite.getHeight());
		hair.draw(x + ((direction == -1) ? hair.getWidth() : 0), y, (direction == -1) ? -hair.getWidth() : hair.getWidth(), hair.getHeight());
		face.draw(x + ((direction == -1) ? face.getWidth() + 4 : 12), y + 2, (direction == -1) ? -face.getWidth() : face.getWidth(), face.getHeight());
		eyes.draw(x + ((direction == -1) ? eyes.getWidth() + 6 : 17), y + 6, (direction == -1) ? -eyes.getWidth() : eyes.getWidth(), eyes.getHeight());

	}

}