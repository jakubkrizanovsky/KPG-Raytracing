package misc;

public class Vector2 {
	public final double x;
	public final double y;
	
	public static final Vector2 ZERO = new Vector2(0, 0);
	public static final Vector2 ONE = new Vector2(1, 1);
	
	public Vector2(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public Vector2 add(Vector2 other) {
		return new Vector2(x + other.x, y + other.y);
	}
	
	public Vector2 subtract(Vector2 other) {
		return new Vector2(x - other.x, y - other.y);
	}
	
	public Vector2 multiplyBy(double k) {
		return new Vector2(k * x, k * y);
	}

	public Vector2 normalize() {
		return multiplyBy(1/magnitude());
	}
	
	public Vector2 opposite() {
		return new Vector2(-x, -y);
	}

	public double magnitude() {
		return Math.sqrt(x*x + y*y);
	}
	
	@Override
	public String toString() {
		return "[x=" + x + ", y=" + y + "]";
	}
}
