import org.newdawn.slick.Image;

public class ProductionRoom extends Room {
	
	float prod_rate;
	int level;
	
	public ProductionRoom(float x, float y, String type, StateGame s) throws NoSuchMethodException, SecurityException {
		super(x, y, 400, 110, type, get_sprite(type, s), s);
		prod_rate = 0;
		level = 0;
	}
	
	static Image get_sprite(String type, StateGame s) {
		switch(type) {
		case "power_room":
			return s.power_room_image;
		case "water_room":
			return s.water_room_image;
		case "food_room":
			return s.food_room_image;
		}
		return null;
	}
	
	public void inc_level() {
		prod_rate *= (0.25 + level/10); /* base increase of 25% plus 10% per level */
		level++;
		s.resources.money -= 1500*Math.pow(level, 2);
	}

}
