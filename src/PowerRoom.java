
public class PowerRoom extends Room {
	
	float prod_rate;
	int level;

	public PowerRoom(float x, float y, StateGame s) throws NoSuchMethodException, SecurityException {
		super(x, y, 400, 110, "power_room", StateGame.power_room_image, s);
		this.prod_rate = 1; // MWe/hr
		this.level = 0;
	}
}
