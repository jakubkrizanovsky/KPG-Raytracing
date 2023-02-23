public abstract class GameObject {
	
	protected Vector3 position;
	protected Vector3 scale;
	protected Material material;
	
	public abstract double rayIntersect(Ray ray);
}
