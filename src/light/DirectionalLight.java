package light;

import java.awt.Color;

import misc.Vector3;

public class DirectionalLight extends Light {
	
	public final Vector3 direction;

	public DirectionalLight(Color color, double intensity, Vector3 direction) {
		super(color, intensity);
		this.direction = direction.normalize();
	}

}
