import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import java.util.Random;


public class Human extends Entity{	
	public static String[] namesM = { "Glenn", "Jerry", "Joe", "Jack", "Paul", "Nick", "Trevor", "Mathew", "Todd", "Linus", "Harry", "Walter", "Ryan", "Bob", "Henry", "Brian", "Dennis" };
	public static String[] namesF = { "Stephanie", "Susan", "Patricia", "Kim", "Rachel", "Rebecca", "Alice", "Jackie", "Judy", "Heidi", "Skylar", "Anna", "Paige" };
	public static String[] namesL = { "Rollins", "Howard", "Zalman", "Bell", "Newell", "Caiafa", "Finnegan", "Hall", "Howell", "Kernighan", "Wilson", "Ritchie" };
	
	String first_name;
	String last_name;
	boolean gender;
	
	public Human(int x, int y) throws SlickException, NoSuchMethodException, SecurityException {
		super("temp", x, y, true);
		Random r = new Random();
		
		gender = r.nextInt(2) == 1;
		first_name = gender ? namesM[r.nextInt(namesM.length)] : namesF[r.nextInt(namesF.length)];
		last_name = namesL[r.nextInt(namesL.length)];
		
		name = first_name + " " + last_name;	
		
		Image sprite = new Image(gender ? "gfx//testChar.png" : "gfx//testCharFem.png");
		sprite.setFilter(Image.FILTER_NEAREST);
		setSpriteLoad(new SpriteSheet(sprite, 32, 64));
	}
	
	public void set_name(String first_name, String last_name) {
		this.first_name = first_name;
		this.last_name = last_name;
		this.name = first_name + " " + last_name;
	}
	
	public void set_gender(int gender) {
		
	}
	
	public void create_offspring(Human father) throws NoSuchMethodException, SecurityException, SlickException {
		Human child = new Human(0, 0);
		father.curRoom.add_entity(child);
	}

}
