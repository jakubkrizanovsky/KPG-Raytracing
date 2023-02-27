package objects;

import components.Material;
import misc.Ray;
import misc.RaycastHit;
import misc.Vector3;

public class Plane extends GameObject {
	
	private final Vector3 normal;
	private final double d;

	@Override
	public RaycastHit rayIntersect(Ray ray) {
		//ax + by + cz + d = 0
		//TODO
		
		return null;
	}

	@Override
	public Vector3 normal(Vector3 position) {
		return normal;
	}
	
	public Plane(Vector3 position, Vector3 normal, Material material) {
		super(position, Vector3.ONE, material);
		this.normal = normal;
		d = - position.dotProduct(normal);
	}

}
