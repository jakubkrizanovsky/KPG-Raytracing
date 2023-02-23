package misc;
public class Ray {
	
	public final Vector3 origin;
	public final Vector3 direction;
	
	public Ray(Vector3 origin, Vector3 direction) {
		this.origin = origin;
		this.direction = direction.normalize();
	}
	
	public Vector3 pointOnRay(double distance) {
		return origin.add(direction.multiplyBy(distance));
	}
}
