
public class WaterRoom extends ProductionRoom {
	public WaterRoom(float x, float y, StateGame s) throws NoSuchMethodException, SecurityException {
		super(x, y, "water_room", s);
		this.prod_rate = 10; // liters/hr 
		this.level = 0;
	}
}
