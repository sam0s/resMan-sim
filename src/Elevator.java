import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Rectangle;

public class Elevator extends Room {
	Image sprite;

	public Elevator(float x, float y, StateGame s) throws NoSuchMethodException, SecurityException {
		super(x, y, 80, 110, "Elevator", StateGame.elevator_room_image, s);
	}

	@Override
	public void drawFreeAdjacents(Graphics surface) {
		surface.setColor(Color.white);
		if (s.new_room.name.equals("Elevator")) {
			System.out.println("yep thats an eleavtor");
			if (up == null && !s.room_overlap(x, y - sizey, x + s.new_room.sizex, y - sizey + s.new_room.sizey)) {
				draw_top_button(surface);
			} else {
				build_u.pause = true;
			}

			if (down == null && !s.room_overlap(x, y + sizey, x + s.new_room.sizex, y + sizey + s.new_room.sizey)) {
				draw_bottom_button(surface);
			} else {
				build_d.pause = true;
			}
			return;
		} else {
			super.drawFreeAdjacents(surface);
		}

	}

}