
public class FoodRoom extends Room {
	
	float prod_rate;
	int level;

	public FoodRoom(float x, float y, StateGame s) throws NoSuchMethodException, SecurityException {
		super(x, y, 400, 110, "food_room", StateGame.food_room_image, s);
		this.prod_rate = 0.01f; // tons/hr
		this.level = 0;
	}
}
