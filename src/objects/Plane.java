package objects;

import components.Material;
import components.Transform;
import core.Constants;
import misc.Ray;
import misc.RaycastHit;
import misc.Vector2;
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
		
		if(t < Constants.MIN_HIT_DISTANCE)
			return null;
		
		Vector3 hitPosition = ray.pointOnRay(t);
		
		//TODO
		if(hitPosition.subtract(transform.position).magnitude() > transform.scale/2)
			return null;
		
		Vector3 normal = normal(hitPosition);
		double cos = ray.direction.opposite().dotProduct(normal);
		if(cos < 0) {
			normal = normal.opposite();
		}
		
		 
		Vector2 uv = new Vector2(
				(hitPosition.x - transform.position.x) / transform.scale,
				(hitPosition.z - transform.position.z) / transform.scale
			).add(Vector2.ONE.multiplyBy(0.5));
		
		return new RaycastHit(ray, hitPosition, normal, cos, t, this, uv);
	}

	@Override
	public Vector3 normal(Vector3 position) {
		return transform.up;
	}
	
	public Plane(Transform transform, Material material) {
		super(transform, material);
	}

}
