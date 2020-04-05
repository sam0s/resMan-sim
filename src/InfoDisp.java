import java.lang.reflect.InvocationTargetException;

import org.newdawn.slick.Input;

public class InfoDisp extends Container {

	Resources res;
	StateGame s;
	
	Label net;
	Label store_label;
	HorzBarGraph store_graph;
	
	public InfoDisp(Resources r, StateGame s) {
		super(400, 64, 0, Game.HEIGHT-66, 2, 2, 2);
		res = r;
		this.s = s;
		
		Label power_label = new Label(0, 0, 0, 0, 0, "Power: ", s.f_18);
		net = new Label(power_label.sizex, 0, 0, 0, 0, String.format("Net: %.1fMW ", res.net_power()), s.f_18);
		store_label = new Label(net.sizex + net.relx, 0, 0, 0, 0, "Store: ", s.f_18);
		store_graph = new HorzBarGraph(120, s.f_18.getHeight("MW/hr") + 4, store_label.relx + store_label.sizex, -2, 2, s.f_18);
		Container power = new Container(power_label.sizex + net.sizex + store_label.sizex + store_graph.sizex, store_graph.sizey, 0, 2, 0, 0, 0);
		power.add_container(power_label, net, store_label, store_graph);
		power.set_theme(Game.clear, Game.win_outer);
		
		add_container(power);
	}
	
	public void recalc_locations() {
		store_label.relx = net.sizex + net.relx;
		store_graph.relx = store_label.relx + store_label.sizex;
	}
	
	public void update(Input i, int mx, int my, int delta) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		super.update(i, mx, my, delta);
		net.set_text(String.format("Net: %.1fMW ", res.net_power()));
		store_graph.set_percent(res.power_store/res.power_store_max);
		store_graph.set_label(String.format("%.1f/%.1fMWh", res.power_store, res.power_store_max));
		store_graph.sizex = s.f_18.getWidth(store_graph.label) + 10;
		recalc_locations();
		
		System.out.printf("im being updated aaaaaaaaa\n");
	}
}
