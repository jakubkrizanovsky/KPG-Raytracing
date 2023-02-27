package camera;

import java.awt.Color;
import java.awt.image.BufferedImage;

import core.Main;
import core.Scene;
import misc.Ray;
import misc.Vector3;

public class OrthogonalCamera extends Camera {
	
	private final static double FOV_X = 1.2;
	private final static double FOV_Y = 0.675;

	public OrthogonalCamera(Scene scene, Vector3 position, Vector3 direction) {
		super(scene, position, direction);
	}

	@Override
	public void generateScene(BufferedImage image) {
		Vector3 topLeft = position.add(new Vector3(-FOV_X/2, 0, 0)).add(new Vector3(0, -FOV_Y/2, 0));
		double xDiff = FOV_X/image.getWidth();
		double yDiff = FOV_Y/image.getHeight();
		
		for(int x = 0; x < image.getWidth(); x++) {
			for(int y = 0; y < image.getHeight(); y++) {
				Vector3 pixelPosition = topLeft.add(new Vector3(x*xDiff, y*yDiff, 0));
				Ray ray = new Ray(pixelPosition, direction);
				Color color = scene.reflectionRay(ray, Main.MAX_BOUNCES);
				
				image.setRGB(x, y, color.getRGB());
			}
		}
	}

}
