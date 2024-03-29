package components;
import java.awt.Color;

import misc.Vector2;

public class Material {

	private final Color color;
	public Texture texture;
	public final double opacity;
	public final double smoothness;
	public final double metallicity;
	public final double refractionIndex;
	
	public static final Material DEFAULT = new Material(Color.WHITE);
	
	public Material(Color color) {
		this(color, 1);
	}
	
	public Material(Color color, double refractionIndex) {
		this(color, refractionIndex, 1);
	}
	
	public Material(Color color, double refractionIndex, double opacity) {
		this(color, refractionIndex, opacity, 1);
	}
	
	public Material(Color color, double refractionIndex, double opacity, double smoothness) {
		this(color, refractionIndex, opacity, smoothness, 1);
	}
	
	public Material(Color color, double refractionIndex, double opacity, double smoothness, double metallicity) {
		this.color = color;
		this.refractionIndex = refractionIndex;
		this.opacity = opacity;
		this.smoothness = smoothness;
		this.metallicity = metallicity;
	}
	
	public Color getColor(Vector2 uv) {
		if(texture != null) {
			return texture.ColorAt(uv);
		} else {
			return color;
		}
	}
}
