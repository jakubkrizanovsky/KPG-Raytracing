package misc;

public class Vector3 {
	
	public final double x;
	public final double y;
	public final double z;
	
	public static final Vector3 ZERO = new Vector3(0, 0, 0);
	public static final Vector3 ONE = new Vector3(1, 1, 1);
	
	public static final Vector3 UP = new Vector3(0, 1, 0);
	public static final Vector3 DOWN = new Vector3(0, -1, 0);
	public static final Vector3 FORWARD = new Vector3(0, 0, 1);
	public static final Vector3 BACK = new Vector3(0, 0, -1);
	
	public Vector3(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public Vector3 add(Vector3 other) {
		return new Vector3(x + other.x, y + other.y, z + other.z);
	}
	
	public Vector3 subtract(Vector3 other) {
		return new Vector3(x - other.x, y - other.y, z - other.z);
	}
	
	public Vector3 multiplyBy(double k) {
		return new Vector3(k * x, k * y, k * z);
	}

	public Vector3 normalize() {
		double magnitude = magnitude();
		return multiplyBy(1/magnitude);
	}
	
	public Vector3 bounce(Vector3 normal) {
		return this.subtract(normal.multiplyBy(2*this.dotProduct(normal)));
	}
	
	public Vector3 crossProduct(Vector3 other) {
		return new Vector3(
				this.y * other.z - this.z * other.y,
				this.z * other.x - this.x * other.z,
				this.x * other.y - this.y * other.x
				);
	}
	
	public Vector3 opposite() {
		return new Vector3(-x, -y, -z);
	}

	public double magnitude() {
		return Math.sqrt(x*x + y*y + z*z);
	}
	
	public double dotProduct(Vector3 other) {
		return x*other.x + y*other.y + z*other.z;
	}

	@Override
	public String toString() {
		return "[x=" + x + ", y=" + y + ", z=" + z + "]";
	}
	
	
}
