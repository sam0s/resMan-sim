import org.newdawn.slick.Image;

public class Limb {
	public Image sprite;
	public float[] rotation;
	public int rot_frame = 0;
	public float angle = 0;
	public float speed;
	public float origin_x;
	public float origin_y;
	public float rotTo;

	public Limb(Image s) {
		sprite = s;
		speed = 100;
		origin_x = s.getWidth() / 2;
		origin_y = s.getHeight() / 2;
	}

	public void advance_rot_frame() {
		rot_frame++;
		if (rot_frame > rotation.length - 1) {
			rot_frame = 0;
		}
	}

	public void set_rot(float[] rots) {
		rotation = rots;

	}

	public void rot_to(float r) {
		rotTo = r;
	}

	public void set_origin(float xx, float yy) {
		origin_x = xx;
		origin_y = yy;

	}

	public void update(int delta) {

		sprite.setCenterOfRotation(origin_x, origin_y);
		if (rotation != null && rotation.length > 0) {
			sprite.setRotation(angle);
			float spd = speed * (delta / 1000f);
			// System.out.println(rot_frame + " " + angle + " " +
			// rotation[rot_frame]);
			if (angle < rotation[rot_frame]) {
				angle += spd;
				if (angle >= rotation[rot_frame]) {
					advance_rot_frame();
				}
			} else {

				if (angle > rotation[rot_frame]) {
					angle -= spd;
					if (angle <= rotation[rot_frame]) {
						advance_rot_frame();
					}
				}
			}
		} else {
			if (angle != rotTo) {
				sprite.setRotation(angle);
				float spd = speed * (delta / 1000f);
				if (angle > rotTo) {
					angle -= spd;
					if (angle < rotTo) {
						angle = rotTo;
					}
				} else {
					if (angle < rotTo) {
						angle += spd;
						if (angle > rotTo) {
							angle = rotTo;
						}
					}
				}

			}
		}

	}

	public void draw(float x, float y) {
		sprite.draw(x, y);

	}

	public void set_speed(float spd) {
		speed = spd;

	}
}
