import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public class Human extends Entity{
	//sheet1.setFilter(Image.FILTER_NEAREST);
	
	public Human(String name, int x, int y, boolean gender) throws SlickException, NoSuchMethodException, SecurityException {
		super(name, x, y, gender);
		Image sprite;
		if(gender==Game.MALE){
			sprite = new Image("gfx//testChar.png");
			sprite.setFilter(Image.FILTER_NEAREST);
			setSpriteLoad(new SpriteSheet(sprite, 32, 64));
		}
		else{
			sprite = new Image("gfx//testCharFem.png");
			sprite.setFilter(Image.FILTER_NEAREST);
			setSpriteLoad(new SpriteSheet(sprite, 32, 64));
		}
	}

}
