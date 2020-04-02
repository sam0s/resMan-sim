
public class WaterRoom extends Room {
	
	float prod_rate;
	int level;

	public WaterRoom(float x, float y, StateGame s) throws NoSuchMethodException, SecurityException {
		super(x, y, 400, 110, "Water", StateGame.water_room_image, s);
		this.prod_rate = 10; // liters/hr 
		this.level = 0;
	}
}
