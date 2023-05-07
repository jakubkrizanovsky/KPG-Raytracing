package camera;

import java.awt.Color;
import java.awt.image.BufferedImage;

import components.Transform;
import core.Scene;
import misc.Constants;
import misc.Ray;
import misc.Vector3;

public class PerspectiveCamera extends Camera {
	
	public final static double focalLength = 1;

	public PerspectiveCamera(Scene scene, Transform transform) {
		super(scene, transform);
	}

	@Override
	public void generateScene(BufferedImage image) {
		
		double sizeX = image.getWidth() * transform.scale * Constants.PIXEL_SCALE;
		double sizeY = image.getHeight() * transform.scale * Constants.PIXEL_SCALE;
		
		Vector3 topLeft = transform.position
				.add(transform.forward.multiplyBy(focalLength))
				.add(transform.right.multiplyBy(-sizeX/2))
				.add(transform.up.multiplyBy(sizeY/2));
		
		double xDiff = sizeX/image.getWidth();
		double yDiff = sizeY/image.getHeight();
		
		for(int x = 0; x < image.getWidth(); x++) {
			for(int y = 0; y < image.getHeight(); y++) {
				
				Vector3 pixelPosition = topLeft
						.add(transform.right.multiplyBy(x*xDiff))
						.add(transform.up.multiplyBy(-y*yDiff));
				
				Ray ray = new Ray(transform.position, pixelPosition.subtract(transform.position));
				Color color = scene.reflectionRay(ray, Constants.MAX_BOUNCES, false);
				
				image.setRGB(x, y, color.getRGB());
			}
		}
		
	}

}
