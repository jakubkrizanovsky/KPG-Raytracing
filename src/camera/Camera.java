package camera;

import java.awt.image.BufferedImage;

import core.Scene;
import misc.Vector3;

public abstract class Camera {
	
	public final Vector3 position;
	public final Vector3 direction;
	public final double scale;
	
	public final Scene scene;
	protected final Vector3 up;
	protected final Vector3 right;
	
	public abstract void generateScene(BufferedImage image);

	public Camera(Scene scene, Vector3 position, Vector3 direction, double scale) {
		this.scene = scene;
		this.position = position;
		this.direction = direction.normalize();
		this.scale = scale;
		
		if(direction.subtract(Vector3.DOWN).magnitude() != 0) {
			up = direction.crossProduct(Vector3.UP).crossProduct(direction);
		} else {
			up = Vector3.FORWARD;
		}

		right = up.crossProduct(this.direction).normalize();
	}
}
