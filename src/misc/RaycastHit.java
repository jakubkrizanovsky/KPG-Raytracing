package misc;
import objects.GameObject;

public class RaycastHit {
	
	public final Ray ray;
	public final Vector3 position;
	public final Vector3 normal;
	public final double distnace;
	public final GameObject gameObject;
	public final Vector2 uv;
	
	public RaycastHit(Ray ray, Vector3 position, Vector3 normal, double distnace, GameObject gameObject, Vector2 uv) {
		this.ray = ray;
		this.position = position;
		this.normal = normal;
		this.distnace = distnace;
		this.gameObject = gameObject;
		this.uv = uv;
	}
}
