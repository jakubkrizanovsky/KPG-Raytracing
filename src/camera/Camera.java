package camera;

import java.awt.image.BufferedImage;

import components.Transform;
import core.Scene;

public abstract class Camera {
	
	public final Scene scene;
	public Transform transform;
	
	public abstract void generateScene(BufferedImage image);

	public Camera(Scene scene, Transform transform) {
		this.scene = scene;
		this.transform = transform;
	}
}
