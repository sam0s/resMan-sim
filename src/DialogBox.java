import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.Graphics;

public class DialogBox extends Container {

	Font f;
	String text;
	boolean hidden;

	public DialogBox(String text, Font f, double weight) throws NoSuchMethodException, SecurityException {
		super(f.getWidth(text) + 80, f.getHeight(text) + 70, Game.WIDTH / 2 - (f.getWidth(text) + 20) / 2, Game.HEIGHT / 2, 0, 0,weight);

		this.text = text;
		this.f = f;
		this.hidden = false;

		Button exit = new Button(StateGame.f_24.getWidth("Ok") + 16, StateGame.f_24.getHeight("Ok") + 8, sizex / 2 - (StateGame.f_24.getWidth("Ok") + 16) / 2, 5 * sizey / 7 - (StateGame.f_24.getHeight("Ok") + 8) / 2, inner, outer, 2, "Ok", StateGame.f_24, fgetMethod("hide"), this);

		this.add_container(exit);

	}

	@Override
	public void hide() {
		hidden = true;
	}

	public void draw(Graphics surface) {
		if (!hidden) {
			surface.setLineWidth(weight);
			surface.setColor(inner);
			surface.fillRect(x, y, sizex, sizey);
			surface.setColor(outer);
			surface.drawRect(x, y, sizex, sizey);

			f.drawString(x + (sizex - f.getWidth(text)) / 2, y + (sizey - f.getHeight(text)) / 4, text, outer);

			for (Container c : containers) {
				c.draw(surface);
			}
		}
	}

}
