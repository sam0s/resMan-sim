public class FoodRoom extends ProductionRoom {
	public FoodRoom(float x, float y, StateGame s) throws NoSuchMethodException, SecurityException {
		super(x, y, "food_room", s);
		this.prod_rate = 0.01f; // tons/hr
		this.level = 0;
	}
}
