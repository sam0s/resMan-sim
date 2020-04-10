import org.newdawn.slick.Image;

public class Limb {
	public Image sprite;
	public float[] rotation;
	public int rot_frame = 0;
	public float angle = 0;
	public float speed;

	public Limb(Image s) {
		sprite = s;
		speed = 1;
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

	public void update() {
		System.out.println(rot_frame + " " + angle + " " + rotation[rot_frame]);
		if (rotation.length > 0) {
			if (angle < rotation[rot_frame]) {
				angle += speed;
				sprite.rotate(speed);
				if (angle >= rotation[rot_frame]) {
					advance_rot_frame();
				}
			} else {

				if (angle > rotation[rot_frame]) {
					angle -= speed;
					sprite.rotate(-speed);
					if (angle <= rotation[rot_frame]) {
						advance_rot_frame();
					}
				}
			}
		}

	}

	public void draw(float x, float y) {
		update();
		sprite.draw(x, y);

	}
}
