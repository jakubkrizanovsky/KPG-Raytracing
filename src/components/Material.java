package components;
import java.awt.Color;

public class Material {

	public final Color color;
	public final double metallicity;
	
	public static final Material DEFAULT = new Material(Color.WHITE);
	
	public Material(Color color) {
		this(color, 1);
	}
	
	public Material(Color color, double metallicity) {
		this.color = color;
		this.metallicity = metallicity;
	}
}
