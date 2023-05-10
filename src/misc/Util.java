package misc;

import java.awt.Color;
import java.util.Random;

public class Util {
	
	public static double clamp(double v, double a, double b) {
		return Math.max(a, Math.min(b, v));
	}
	
	public static double lerp(double a, double b, double v) {
		return (1-v)*a + v*b;
	}

	public static double inverseLerp(double a, double b, double v) {
		return (v-a)/(b-a);
	}
	
	public static Color randomColor(Random r) {
		return new Color(
			r.nextInt(256),
			r.nextInt(256),
			r.nextInt(256));
	}
}
