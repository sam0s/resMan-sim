
public class PowerRoom extends ProductionRoom {
	public PowerRoom(float x, float y, StateGame s) throws NoSuchMethodException, SecurityException {
		super(x, y, "power_room", s);
		this.prod_rate = 1; // MWe/hr
		this.level = 0;
	}
}
