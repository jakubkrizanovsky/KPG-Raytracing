package objects;
import components.Material;
import components.Transform;
import misc.Ray;
import misc.RaycastHit;
import misc.Vector3;

public abstract class GameObject {
	
	public final Transform transform;
	public final Material material;
	
	public GameObject(Transform transform, Material material) {
		this.transform = transform;
		this.material = material;
	}
	
	public abstract RaycastHit rayIntersect(Ray ray);
	
	public abstract Vector3 normal(Vector3 position);
}
