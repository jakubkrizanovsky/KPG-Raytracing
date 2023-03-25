package components;
import java.awt.Color;

public class Material {

	public final Color color;
	public final double metallicity;
	public final double opacity;
	public final double refractionIndex;

	
	public static final Material DEFAULT = new Material(Color.WHITE);
	
	public Material(Color color) {
		this(color, 1, 1, 1);
	}
	
	public Material(Color color, double metallicity, double opacity, double refractionIndex) {
		this.color = color;
		this.metallicity = metallicity;
		this.opacity = opacity;
		this.refractionIndex = refractionIndex;
	}
}
