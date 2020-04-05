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
	
	int n_staff;
	
	Vector<Human> staff_list;
	
	StateGame s;
	
	public Resources(StateGame s) {
		this.power_store = 0;
		this.food_store = 0;
		this.water_store = 0;
		this.n_staff = 0;
		this.money = 0;
		
		this.raw_res = new int[10];
		
		this.staff_list = new Vector<Human>();
		
		this.s = s;
	}
	
	public void update(int delta) {
		
	}
	
	public void add_staff(Human staff) {
		staff_list.addElement(staff);
		n_staff++;
	}
	
	public void remove_staff(Human staff) {
		staff_list.remove(staff);
		n_staff--;
	}
}
