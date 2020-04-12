public class EyeColor {
	static int[][] eye_colors = {
			{95, 72, 40}, /* dark brown */
			{150, 123, 82}, /* light brown */
			{36, 111, 151}, /* dark blue */
			{78, 161, 205}, /* light blue */
			{39, 176, 54}, /* green */
			{79, 88, 80}, /* grey */
	};
	
	public static int[] get_eye_color(int ec) {
		return eye_colors[ec];
	}
}
