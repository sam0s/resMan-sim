import org.newdawn.slick.Image;

public class Limb {
	public Image sprite;
	public float[] rotation;
	public int rot_frame = 0;
	public float angle = 0;
	public float frame_rate = 15;
	public float frame = 0;
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
		sprite.setRotation(r);
	}

	public void set_origin(float xx, float yy) {
		origin_x = xx;
		origin_y = yy;
		sprite.setCenterOfRotation(xx, yy);

	}

	public void update(int delta) {
		if (rotation != null) {
			frame += speed * (delta / 1000f);
			System.out.println(rotation[0]);
			if (frame >= frame_rate) {
				frame = 0;
				advance_rot_frame();
				sprite.setRotation(rotation[rot_frame]);
				System.out.printf("OKTHEN");
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
