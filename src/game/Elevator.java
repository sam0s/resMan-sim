package game;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

public class Elevator extends Room {
	Image sprite;

	public Elevator(float x, float y, StateGame s) throws NoSuchMethodException, SecurityException, SlickException {
		super(x, y, 80, 110, "Elevator", s);
	}

	@Override
	public void drawFreeAdjacents(Graphics surface) {
		surface.setColor(Color.white);
		if (s.new_room.type.equals("Elevator")) {
			System.out.println("yep thats an eleavtor");
			if (up == null && s.room_overlap(x, y - sizey, x + s.new_room.sizex, y - sizey + s.new_room.sizey) == null) {
				draw_top_button(surface);
			} else {
				build_u.pause = true;
			}

			if (down == null && s.room_overlap(x, y + sizey, x + s.new_room.sizex, y + sizey + s.new_room.sizey) == null) {
				draw_bottom_button(surface);
			} else {
				build_d.pause = true;
			}
			return;
		} else {
			super.drawFreeAdjacents(surface);
		}

	}

	@Override
	public void check_adjacencies() {
		super.check_adjacencies();
		Room ovlp;
		if (up == null && (ovlp = s.room_overlap(x + 5, y - 10, x + sizex - 10, y)) != null) {
			ovlp.down = this;
			this.up = ovlp; 
		}
		
		if (down == null && (ovlp = s.room_overlap(x + 5, y + sizey, x + sizex - 10, y + sizey + 10)) != null) {
			ovlp.up = this;
			this.down = ovlp;
		}
	}

}