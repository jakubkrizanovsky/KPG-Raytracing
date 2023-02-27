package objects;
import components.Material;
import core.Main;
import misc.Ray;
import misc.RaycastHit;
import misc.Vector3;

public class Sphere extends GameObject {
	
	private double radius;
	
	public Sphere(Vector3 position, double radius) {
		this(position, radius, Material.DEFAULT);
	}
	
	public Sphere(Vector3 position, double radius, Material material) {
		super(position, Vector3.ONE.multiplyBy(radius), material);
		this.radius = radius;
	}

	@Override
	public RaycastHit rayIntersect(Ray ray) {
		//P = O + t*ray.direction 
		//(S - P)(S - P) = R^2
		//(S - O - t*ray.dir)(S - O - t*ray.dir) = R^2
		//(O - S + t*D)(O - S + t*D) = R^2
		//D^2*t*t + 2*(O-S)*t*D + (O - S)(O - S) - R^2 = 0
		
		Vector3 diff = this.position.subtract(ray.origin);
		
		double a = ray.direction.dotProduct(ray.direction);
		double b = -2 * diff.dotProduct(ray.direction);
		double c = diff.dotProduct(diff) - radius*radius;

		double determinant = b*b - 4*a*c;
		
		
		double x1 = -1;
		double x2 = -1;
		
		if(determinant < 0) {
			return null;
		} else if(determinant == 0) {
			x1 = -b / (2*a);
		} else {
			x1 = (-b - Math.sqrt(determinant))/(2*a);
			x2 = (-b + Math.sqrt(determinant))/(2*a);
		}
		
		if(x1 > Main.MIN_HIT_DISTANCE) {
			Vector3 position = ray.pointOnRay(x1);
			return new RaycastHit(position, normal(position), x1, this);
		} else if(x2 > Main.MIN_HIT_DISTANCE) {
			Vector3 position = ray.pointOnRay(x2);
			return new RaycastHit(position, normal(position), x2, this);
		}
		
		
		return null;
	}

	@Override
	public Vector3 normal(Vector3 position) {
		return position.subtract(this.position).normalize();
	}


}
