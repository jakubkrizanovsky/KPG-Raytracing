package color_blending;

import java.awt.Color;

public class ColorBlender {
	
	public static Color blendColors(Color color1, Color color2, BlendMode blendMode) {
		//TODO alpha
		
		int r = saturate(color1.getRed()*blendMode.src + color2.getRed()*blendMode.dst);
		int g = saturate(color1.getGreen()*blendMode.src + color2.getGreen()*blendMode.dst);
		int b = saturate(color1.getBlue()*blendMode.src + color2.getBlue()*blendMode.dst);
		
		return new Color(r, g, b);
	}
	
	public static Color addColors(Color color1, Color color2) {
		//TODO alpha
		
		int r = saturate(color1.getRed() + color2.getRed());
		int g = saturate(color1.getGreen() + color2.getGreen());
		int b = saturate(color1.getBlue() + color2.getBlue());
		
		return new Color(r, g, b);
	}
	
	public static Color multiplyColors(Color color1, Color color2) {
		//TODO alpha
		
		int r = saturate(color1.getRed() * color2.getRed() / 255.0);
		int g = saturate(color1.getGreen() * color2.getGreen() / 255.0);
		int b = saturate(color1.getBlue() * color2.getBlue() / 255.0);
		
		return new Color(r, g, b);
	}
	
	public static Color multiplyColor(Color color1, double k) {
		//TODO alpha
		
		int r = saturate(color1.getRed() * k);
		int g = saturate(color1.getGreen() * k);
		int b = saturate(color1.getBlue() * k);
		
		return new Color(r, g, b);
	}
	
	private static int saturate(double d) {
		return (int)Math.min(255, Math.max(0, d));
	}
	
}
