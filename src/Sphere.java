public class Sphere extends GameObject {
	
	private double radius;
	
	public Sphere(Vector3 position, double radius) {
		this(position, radius, Material.DEFAULT);
	}
	
	public Sphere(Vector3 position, double radius, Material material) {
		this.position = position;
		this.radius = radius;
		this.scale = Vector3.ONE.multiplyBy(radius);
		this.material = material;
	}

	@Override
	public double rayIntersect(Ray ray) {
		//P = O + t*ray.direction 
		//(S - P)(S - P) = R^2
		//(S - O - t*ray.dir)(S - O - t*ray.dir) = R^2
		//(O - S + t*D)(O - S + t*D) = R^2
		//D^2*t*t + 2*(O-S)*t*D + (O - S)(O - S) - R^2 = 0
		
		Vector3 diff = this.position.subtract(ray.origin);
		
		double a = ray.direction.dotProduct(ray.direction);
		double b = -2 * diff.dotProduct(ray.direction);
		double c = diff.dotProduct(diff) - radius*radius;
		
		//System.out.println("a: " + a);
		//System.out.println("b: " + b);
		//System.out.println("c: " + c);
		
		double determinant = b*b - 4*a*c;
		
		//System.out.println("Det: " + determinant);
		
		double x1 = -1;
		double x2 = -1;
		
		if(determinant < 0) {
			return -1;
		} else if(determinant == 0) {
			x1 = -b / (2*a);
		} else {
			x1 = (-b - Math.sqrt(determinant))/(2*a);
			x2 = (-b + Math.sqrt(determinant))/(2*a);
		}
		
		//System.out.println("x1: " + x1);
		//System.out.println("x2: " + x2);
		
		if(x1 > 0) {
			return x1;
		} else if(x2 > 0) {
			return x2;
		}
		
		
		return -1;
	}


}
