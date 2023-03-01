package camera;

import java.awt.Color;
import java.awt.image.BufferedImage;

import java.util.Arrays;

import components.Transform;
import core.Scene;
import misc.Constants;
import misc.Ray;
import misc.Vector3;

public class ParallelPerspectiveCamera extends Camera {
	
//	private final static double FOV_X = 1.2;
//	private final static double FOV_Y = 0.675;
	private final static double focalLength = 1;
	

	public ParallelPerspectiveCamera(Scene scene, Transform transform) {
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
		
		Ray[][] rays = new Ray[image.getWidth()][image.getHeight()];
		
		for(int x = 0; x < image.getWidth(); x++) {
			for(int y = 0; y < image.getHeight(); y++) {
				
				Vector3 pixelPosition = topLeft
						.add(transform.right.multiplyBy(x*xDiff))
						.add(transform.up.multiplyBy(-y*yDiff));

				rays[x][y] = new Ray(transform.position, pixelPosition.subtract(transform.position));
				
			}
		}
		
		Arrays.stream(rays).parallel().forEach((col) -> {
			Arrays.stream(col).parallel().forEach((ray) -> {
				Color color = scene.reflectionRay(ray, Constants.MAX_BOUNCES);
				ray.col = color.getRGB();
			});
		});
		
		for(int x = 0; x < image.getWidth(); x++) {
			for(int y = 0; y < image.getHeight(); y++) {
				image.setRGB(x, y, rays[x][y].col);
			}
		}
	}

}
