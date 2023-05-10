package color_blending;

import java.awt.Color;

import misc.Util;
import misc.Vector3;

public class ColorBlender {
	
	public static Color blendColors(Color color1, Color color2, BlendMode blendMode) {
		
		int r = saturate(color1.getRed()*blendMode.src + color2.getRed()*blendMode.dst);
		int g = saturate(color1.getGreen()*blendMode.src + color2.getGreen()*blendMode.dst);
		int b = saturate(color1.getBlue()*blendMode.src + color2.getBlue()*blendMode.dst);
		
		return new Color(r, g, b);
	}
	
	public static Color addColors(Color color1, Color color2) {
		
		int r = saturate(color1.getRed() + color2.getRed());
		int g = saturate(color1.getGreen() + color2.getGreen());
		int b = saturate(color1.getBlue() + color2.getBlue());
		
		return new Color(r, g, b);
	}
	
	public static Color multiplyColors(Color color1, Color color2) {
		
		int r = saturate(color1.getRed() * color2.getRed() / 255.0);
		int g = saturate(color1.getGreen() * color2.getGreen() / 255.0);
		int b = saturate(color1.getBlue() * color2.getBlue() / 255.0);
		
		return new Color(r, g, b);
	}
	
	public static Color multiplyColor(Color color1, double k) {
		
		int r = saturate(color1.getRed() * k);
		int g = saturate(color1.getGreen() * k);
		int b = saturate(color1.getBlue() * k);
		
		return new Color(r, g, b);
	}
	
	public static Color averageColors(Color ... colors) {
		int r = 0;
		int g = 0;
		int b = 0;
		
		for(Color c : colors) {
			r += c.getRed();
			g += c.getGreen();
			b += c.getBlue();
		}
		
		r /= colors.length;
		g /= colors.length;
		b /= colors.length;
		
		return new Color(r, g, b);
	}
	
	public static Color lerp(Color a, Color b, double v) {
		return new Color(
				(int)Util.lerp(a.getRed(), b.getRed(), v),
				(int)Util.lerp(a.getGreen(), b.getGreen(), v),
				(int)Util.lerp(a.getBlue(), b.getBlue(), v)
			);
	}
	
	private static int saturate(double d) {
		return (int)Math.min(255, Math.max(0, d));
	}
	
	public static Color colorFromVector3(Vector3 vector) {
		Vector3 vectorNormalized = vector.normalize();
		int r = (int)(Math.max(0, 255 * vectorNormalized.x));
		int g = (int)(Math.max(0, 255 * vectorNormalized.y));
		int b = (int)(Math.max(0, 255 * vectorNormalized.z));
		
		return new Color(r, g, b);
	}
	
}
