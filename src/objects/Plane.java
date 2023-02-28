package objects;

import components.Material;
import misc.Ray;
import misc.RaycastHit;
import misc.Vector3;

public class Plane extends GameObject {
	
	private final Vector3 normal;

	@Override
	public RaycastHit rayIntersect(Ray ray) {
		
		double rayDirDotPlaneNormal = ray.direction.dotProduct(normal);
		
		//System.out.println(rayDirDotPlaneNormal);
		
		//Check if ray is parallel to the plane
		if(rayDirDotPlaneNormal == 0) {
			return null;
		}
		
		double t = position.subtract(ray.origin).dotProduct(normal) / rayDirDotPlaneNormal;
		
		if(t < MIN_HIT_DISTANCE)
			return null;
		
		Vector3 hitPosition = ray.pointOnRay(t);
		
		if(hitPosition.subtract(position).magnitude() > scale/2)
			return null;
		
		
		return new RaycastHit(hitPosition, normal, t, this);
	}

	@Override
	public Vector3 normal(Vector3 position) {
		return normal;
	}
	
	public Plane(Vector3 position, Vector3 normal, double scale, Material material) {
		super(position, scale, material);
		this.normal = normal.normalize();
	}

}
