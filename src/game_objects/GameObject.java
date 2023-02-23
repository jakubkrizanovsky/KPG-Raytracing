package game_objects;
import components.Material;
import misc.Ray;
import misc.RaycastHit;
import misc.Vector3;

public abstract class GameObject {
	
	public Vector3 position;
	public Vector3 scale;
	public Material material;
	
	public abstract RaycastHit rayIntersect(Ray ray);
	
	public abstract Vector3 normal(Vector3 position);
}
