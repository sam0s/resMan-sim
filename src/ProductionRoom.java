import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class ProductionRoom extends Room {

	float prod_rate;
	int level;

	public ProductionRoom(float x, float y, String type, StateGame s) throws NoSuchMethodException, SecurityException, SlickException {
		super(x, y, 400, 110, type, s);
		prod_rate = 0;
		level = 0;
	}

	public void inc_level() {
		prod_rate *= (0.25 + level / 10); /*
										 * base increase of 25% plus 10% per
										 * level
										 */
		level++;
		s.resources.money -= 1500 * Math.pow(level, 2);
	}

}
