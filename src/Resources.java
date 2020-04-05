import java.util.Vector;

public class Resources {
	float power_store; /* MWe */
	float food_store; /* tons */
	float water_store; /* liters */
	float money; /* TBD */

	/* might not end up implementing this system: the following will be an array keeping track
	 * of different types of raw resources like steel, rubber, etc.
	 */
	int raw_res[]; 
	
	float power_io[]; /* power production and usage rates */
	float food_io[]; /* food production and usage rates */
	float water_io[]; /* water production and usage rates */
	
	int staff;
	
	Vector<Human> staff_list;
	
	StateGame s;
	
	public Resources(StateGame s) {
		this.power_store = 0;
		this.food_store = 0;
		this.water_store = 0;
		this.power_io = new float[2];
		this.food_io = new float[2];
		this.water_io = new float[2];
		this.staff = 0;
		this.money = 0;
		
		this.raw_res = new int[10];
		
		this.staff_list = new Vector<Human>();
		
		this.s = s;
	}
	
	public void update(int delta) {
		power_store += power_io[0]-power_io[1];
		food_store += food_io[0]-food_io[1];
		water_store += water_io[0]-water_io[1];
	}
	
	public void calc_rates() {
		// loop through all rooms, add power gen/use rates of each.
		// loop through all rooms, add water gen/use rates of each.
		// add water usage by water usage per person * staff number.
		// loop through all rooms, add food usage/use rates of each.
		// add food usage by food usage per person * staff number.
		
		// for now
		power_io[0] = power_io[1] = 1; // MWe
		food_io[0] = food_io[1] = 0.25f; // tons
		water_io[0] = water_io[1] = 10; // liters 
	}
}
