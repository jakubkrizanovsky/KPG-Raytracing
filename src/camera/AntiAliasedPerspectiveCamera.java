package camera;

import java.awt.Color;
import java.awt.image.BufferedImage;

import color_blending.ColorBlender;
import components.Transform;
import core.Scene;

public class AntiAliasedPerspectiveCamera extends PerspectiveCamera {

	public AntiAliasedPerspectiveCamera(Scene scene, Transform transform) {
		super(scene, transform);
	}
	
	@Override
	public void generateScene(BufferedImage image) {
		BufferedImage image2 = new BufferedImage(image.getWidth() + 1, image.getHeight() + 1, image.getType());
		super.generateScene(image2);
		
		for(int x = 0; x < image.getWidth(); x++) {
			for(int y = 0; y < image.getHeight(); y++) {				
				Color color = ColorBlender.averageColors(
						new Color(image2.getRGB(x, y)),
						new Color(image2.getRGB(x + 1, y)),
						new Color(image2.getRGB(x, y + 1)),
						new Color(image2.getRGB(x + 1, y + 1))
						);
				
				image.setRGB(x, y, color.getRGB());
			}
		}
	}

}
