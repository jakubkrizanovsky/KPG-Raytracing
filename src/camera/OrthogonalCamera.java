package camera;

import java.awt.Color;
import java.awt.image.BufferedImage;

import core.Main;
import core.Scene;
import misc.Ray;
import misc.Vector3;

public class OrthogonalCamera extends Camera {

	public OrthogonalCamera(Scene scene, Vector3 position, Vector3 direction, double scale) {
		super(scene, position, direction, scale);
	}

	@Override
	public void generateScene(BufferedImage image) {
		
		double sizeX = image.getWidth() * scale;
		double sizeY = image.getHeight() * scale;
		
		Vector3 topLeft = position
				.add(right.multiplyBy(-sizeX/2))
				.add(up.multiplyBy(sizeY/2));
		
		double xDiff = sizeX/image.getWidth();
		double yDiff = sizeY/image.getHeight();
		
		for(int x = 0; x < image.getWidth(); x++) {
			for(int y = 0; y < image.getHeight(); y++) {
				
				Vector3 pixelPosition = topLeft
						.add(right.multiplyBy(x*xDiff))
						.add(up.multiplyBy(-y*yDiff));
				
				Ray ray = new Ray(pixelPosition, direction);
				Color color = scene.reflectionRay(ray, Main.MAX_BOUNCES);
				
				image.setRGB(x, y, color.getRGB());
			}
		}
	}

}
