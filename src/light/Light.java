package light;

import java.awt.Color;

import color_blending.ColorBlender;
import misc.Vector3;

public abstract class Light {
	
	private final Color color;
	private final double intensity;
	
	public Light(Color color, double intensity) {
		this.color = color;
		this.intensity = intensity;
	}
	
	public Color getColor() {
		return ColorBlender.multiplyColor(color, intensity);
	}
	
	public abstract Vector3 directionToLight(Vector3 position);

}
