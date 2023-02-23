package misc;
import game_objects.GameObject;

public class RaycastHit {
	
	public final Vector3 position;
	public final double distnace;
	public final GameObject gameObject;
	
	public RaycastHit(Vector3 position, double distnace, GameObject gameObject) {
		this.position = position;
		this.distnace = distnace;
		this.gameObject = gameObject;
	}
}
