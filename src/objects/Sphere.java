package objects;
import components.Material;
import components.Transform;
import core.Constants;
import misc.Ray;
import misc.RaycastHit;
import misc.Vector2;
import misc.Vector3;

public class Sphere extends GameObject {
	
	private double radius;
	
	public Sphere(Transform transform) {
		this(transform, Material.DEFAULT);
	}
	
	public Sphere(Transform transform, Material material) {
		super(transform, material);
		this.radius = transform.scale / 2;
	}

	@Override
	public RaycastHit rayIntersect(Ray ray) {
		
		Vector3 diff = transform.position.subtract(ray.origin);
		
		double a = ray.direction.dotProduct(ray.direction);
		double b = -2 * diff.dotProduct(ray.direction);
		double c = diff.dotProduct(diff) - radius*radius;

		double determinant = b*b - 4*a*c;
		
		
		double x1 = -1;
		double x2 = -1;
		
		if(determinant < 0) {
			return null;
//		} else if(determinant == 0) {
//			x1 = -b / (2*a);
		} else {
			x1 = (-b - Math.sqrt(determinant))/(2*a);
			x2 = (-b + Math.sqrt(determinant))/(2*a);
		}
		
		
		
		
		double distance = 0;
		if(x1 > Constants.MIN_HIT_DISTANCE) {
			distance = x1;
		} else if(x2 > Constants.MIN_HIT_DISTANCE) {
			distance = x2;
		} else {
			return null;
		}
		
		Vector3 position = ray.pointOnRay(distance);

		
		Vector3 normal = normal(position);
		double cos = ray.direction.opposite().dotProduct(normal);
		if(cos < 0) {
			normal = normal.opposite();
		}
		
		return new RaycastHit(ray, position, normal, cos, x1, this, Vector2.ZERO); //TODO

		
	}

	@Override
	public Vector3 normal(Vector3 position) {
		return position.subtract(transform.position).normalize();
	}


}
