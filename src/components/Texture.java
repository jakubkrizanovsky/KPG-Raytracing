package components;

import java.awt.Color;
import java.awt.image.BufferedImage;

import misc.Util;
import misc.Vector2;

public class Texture {
	
	private final BufferedImage image;

	public Color ColorAt(Vector2 uv) {
		if(image == null)
			return Color.MAGENTA;
		
		
		
		double x = Util.lerp(0, image.getWidth()-1, uv.x);
		double y = Util.lerp(0, image.getHeight()-1, uv.y);
		
//		if(x >= image.getWidth() || x < 0 || y >= image.getHeight() || y < 0)
//			return Color.BLACK;
//		
//		x = Util.clamp(x, 0, image.getWidth()-1);
//		y = Util.clamp(y, 0, image.getHeight()-1);
		
		int rgb = image.getRGB((int)x, (int)y);
		
		return new Color(rgb);
	}
	
	public Texture(BufferedImage image) {
		this.image = image;
	}
}
