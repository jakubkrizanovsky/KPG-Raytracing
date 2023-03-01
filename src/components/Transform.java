package components;

import misc.Vector3;

public class Transform {
	
	public final Vector3 position;
	public final double scale;
	
	public final Vector3 forward;
	public final Vector3 up;
	public final Vector3 right;
	

	public Transform(Vector3 position, double scale, Vector3 forward) {
		this.position = position;
		this.scale = scale;
		
		this.forward = forward.normalize();
		
		if(this.forward.dotProduct(Vector3.UP) != 1) {
			up = forward.crossProduct(Vector3.UP).crossProduct(this.forward);
		} else {
			up = Vector3.FORWARD;
		}

		right = up.crossProduct(this.forward).normalize();
	}
	
	public Transform(Vector3 position, Vector3 forward) {
		this(position, 1, Vector3.FORWARD);
	}
	
	public Transform(Vector3 position, double scale) {
		this(position, scale, Vector3.FORWARD);
	}
	
	public Transform(Vector3 position) {
		this(position, 1);
	}

}
