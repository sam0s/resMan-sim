import org.newdawn.slick.Graphics;

public class HorzBarGraph extends Container {

	float percent;
	
	public HorzBarGraph(int sizex, int sizey, float x, float y, float weight) {
		super(sizex, sizey, x, y, 0, 0, weight);
		
		this.percent = 0;
	}
	
	public void set_percent(float percent) {
		if (percent > 1) {
			this.percent = 1;
			return;
		}
		this.percent = percent;
	}
	
	public void draw(Graphics surface) {
		surface.setLineWidth(weight);
		surface.setColor(outer);
		surface.fillRect(x,  y, (int) sizex * percent, sizey);
		surface.drawRect(x, y, sizex, sizey-(int)weight);
	}

}
