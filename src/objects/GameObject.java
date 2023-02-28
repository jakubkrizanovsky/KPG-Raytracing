package objects;
import components.Material;
import misc.Ray;
import misc.RaycastHit;
import misc.Vector3;

public abstract class GameObject {
	
	protected final static double MIN_HIT_DISTANCE = 0.01;
	
	public final Vector3 position;
	public final double scale;
	public final Material material;

	
	public GameObject(Vector3 position, double scale, Material material) {
		this.position = position;
		this.scale = scale;
		this.material = material;
	}
	
	public abstract RaycastHit rayIntersect(Ray ray);
	
	public abstract Vector3 normal(Vector3 position);
}
