import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import java.util.Random;


public class Human extends Entity{	
	public static String[] namesM = { "Glenn", "Jerry", "Joe", "Jack", "Paul", "Nick", "Trevor", "Mathew", "Todd", "Linus", "Harry", "Walter", "Ryan", "Bob", "Henry", "Brian", "Dennis" };
	public static String[] namesF = { "Stephanie", "Susan", "Patricia", "Kim", "Rachel", "Rebecca", "Alice", "Jackie", "Judy", "Heidi", "Skylar", "Anna", "Paige" };
	public static String[] namesL = { "Rollins", "Howard", "Zalman", "Bell", "Newell", "Caiafa", "Finnegan", "Hall", "Howell", "Kernighan", "Wilson", "Ritchie" };
	
	Random r;
	
	String first_name;
	String last_name;
	boolean gender;
	int age;
	
	int hair_color;
	int eye_color;
	int skin_color;

	int morale;
	int level;
	
	Human father;
	Human mother;
	
	boolean dead;
	
	public Human(int x, int y) throws SlickException, NoSuchMethodException, SecurityException {
		super("temp", x, y, true);
		r = new Random();
		
		gender = r.nextInt(2) == 1;
		first_name = gender ? namesM[r.nextInt(namesM.length)] : namesF[r.nextInt(namesF.length)];
		last_name = namesL[r.nextInt(namesL.length)];
		name = first_name + " " + last_name;	
		
		do {
			age = (int) Math.round(r.nextGaussian()*5 + 25);
		} while (age <= 16);
		System.out.printf("age: %d\n", age);
		hair_color = r.nextInt(21);
		eye_color = r.nextInt(21);
		skin_color = r.nextInt(60);
		
		level = r.nextInt(10)+1;
		hp_max = 10 * level;
		hp = r.nextInt(hp_max);
		morale = r.nextInt(101);		
		father = null;
		mother = null;
		
		dead = false;
		
		Image sprite = new Image(gender ? "gfx//testChar.png" : "gfx//testCharFem.png");
		sprite.setFilter(Image.FILTER_NEAREST);
		setSpriteLoad(new SpriteSheet(sprite, 32, 64));
	}
	
	public void set_name(String first_name, String last_name) {
		this.first_name = first_name;
		this.last_name = last_name;
		this.name = first_name + " " + last_name;
	}
	
	public void set_gender(boolean gender) {
		this.gender = gender;
	}
	
	public void set_age(int age) {
		this.age = age;
	}
	
	public void set_traits(int hair_color, int eye_color, int skin_color) {
		this.hair_color = hair_color;
		this.eye_color = eye_color;
		this.skin_color = skin_color;
	}
	
	public Human create_offspring(Human father) throws NoSuchMethodException, SecurityException, SlickException {
		Human child = new Human((int)curRoom.x, (int)curRoom.y);
		int hc = (int) (this.hair_color + father.hair_color)/2;
		int ec = (int) (this.eye_color + father.eye_color)/2;
		int sc = (int) (this.skin_color + father.skin_color)/2;
		child.set_name(child.gender ? namesM[r.nextInt(namesM.length)] : namesF[r.nextInt(namesF.length)],
					   father.last_name);
		child.set_traits(hc + r.nextInt(7)-3, ec + r.nextInt(7)-3, sc + r.nextInt(7)-3);
		child.set_age(0);
		child.father = father;
		child.mother = this;
		return child;
	}

}
