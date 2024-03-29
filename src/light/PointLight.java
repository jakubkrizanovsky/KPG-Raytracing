package light;

import java.awt.Color;

import misc.Vector3;

public class PointLight extends Light {
	
	public final Vector3 position;

	public PointLight(Color color, double intensity, Vector3 position) {
		super(color, intensity);
		this.position = position;
	}

	@Override
	public Vector3 directionToLight(Vector3 position) {
		return this.position.subtract(position).normalize();
	}

}
