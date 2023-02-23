package components;
import java.awt.Color;

public class Material {

	public final Color color;
	
	public static final Material DEFAULT = new Material(Color.WHITE);
	
	public Material(Color color) {
		this.color = color;
	}
}
