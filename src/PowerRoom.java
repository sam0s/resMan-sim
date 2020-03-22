
public class PowerRoom extends Room {

	public PowerRoom(float x, float y, StateGame s) throws NoSuchMethodException, SecurityException {
		super(x, y, 400, 110, "Power", StateGame.power_room_image, s);
		this.type = "power_room";
	}
}
