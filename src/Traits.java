public class Traits {
	static int[][] colors = { { 95, 72, 40 }, /* dark brown */
	{ 150, 123, 82 }, /* light brown */
	{ 36, 111, 151 }, /* dark blue */
	{ 78, 161, 205 }, /* light blue */
	{ 39, 176, 54 }, /* green */
	{ 79, 88, 80 }, /* grey */
	};

	static int[][] colors_skin = { { 141, 85, 36 }, /* Russet */
		{ 116, 61, 43 }, /* Garnet */
	{ 198, 134, 66 },/* Peru */
	{ 255, 219, 172 }, /* Navajo White */
	{ 244, 222, 193}, /*Dutch white*/
	};

	public static int[] get_eye_color(int ec) {
		return colors[ec];
	}

	public static int[] get_hair_color(int hc) {
		return colors[hc];
	}

	public static int[] get_skin_color(int skin_color) {
		return colors_skin[skin_color];
	}
}