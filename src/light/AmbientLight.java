package light;

import java.awt.Color;

import misc.Vector3;

public class AmbientLight extends Light {

	public AmbientLight(Color color, double intensity) {
		super(color, intensity);
	}

	@Override
	public Vector3 directionToLight(Vector3 position) {
		throw new IllegalStateException("Cannot determine direction to ambient light");
	}

}
