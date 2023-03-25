package objects;

import components.Material;
import components.Transform;
import misc.Ray;
import misc.RaycastHit;
import misc.Vector3;

public class Plane extends GameObject {

	@Override
	public RaycastHit rayIntersect(Ray ray) {
		
		double rayDirDotPlaneNormal = ray.direction.dotProduct(transform.up);
		
		//Check if ray is parallel to the plane
		if(rayDirDotPlaneNormal == 0) {
			return null;
		}
		
		double t = transform.position.subtract(ray.origin).dotProduct(transform.up) / rayDirDotPlaneNormal;
		
		if(t < MIN_HIT_DISTANCE)
			return null;
		
		Vector3 hitPosition = ray.pointOnRay(t);
		
		if(hitPosition.subtract(transform.position).magnitude() > transform.scale/2)
			return null;
		
		 
		
		
		return new RaycastHit(ray, hitPosition, transform.up, t, this, false);
	}

	@Override
	public Vector3 normal(Vector3 position) {
		return transform.up;
	}
	
	public Plane(Transform transform, Material material) {
		super(transform, material);
	}

}
