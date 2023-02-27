package camera;

import java.awt.image.BufferedImage;

import core.Scene;
import misc.Vector3;

public abstract class Camera {
	
	public final Vector3 position;
	public final Vector3 direction;
	public final Scene scene;
	
	public abstract void generateScene(BufferedImage image);

	public Camera(Scene scene, Vector3 position, Vector3 direction) {
		this.scene = scene;
		this.position = position;
		this.direction = direction;
	}
}
