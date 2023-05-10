package misc;

import core.Constants;

public class Refraction {
	
	public final Vector3 direction;
	public final double reflexionCoefficient;
	public final double refractionCoefficient;
	
	public Refraction(RaycastHit hit) {
		double cosPhi1 = hit.ray.direction.opposite().dotProduct(hit.normal);
		
		double n1, n2;
		Vector3 normal;
		if(cosPhi1 > 0) {
			n1 = Constants.AIR_REFRACTION_INDEX;
			n2 = hit.gameObject.material.refractionIndex;
			normal = hit.normal;
		} else {
			n1 = hit.gameObject.material.refractionIndex;
			n2 = Constants.AIR_REFRACTION_INDEX;
			normal = hit.normal.opposite();
			cosPhi1 = -cosPhi1;
		}
		
		Vector3 c = hit.ray.direction.crossProduct(normal);
		
		double sinPhi1 = c.magnitude();
		double sinPhi2 = n1 * sinPhi1 / n2;
		
		double cosPhi2 = Math.sqrt(1 - sinPhi2*sinPhi2);
		double tanPhi2 = sinPhi2 / cosPhi2;
		
		Vector3 r = normal.crossProduct(c.normalize()).multiplyBy(tanPhi2);
		this.direction = normal.opposite().add(r).normalize();
		
		double Rs = (n1 * cosPhi1 - n2 * cosPhi2) / (n1 * cosPhi1 + n2 * cosPhi2);
		Rs *= Rs;
		double Rp = (n1 * cosPhi2 - n2 * cosPhi1) / (n1 * cosPhi2 + n2 * cosPhi1);
		Rp *= Rp;
		double R = 0.5*(Rs + Rp);

		this.reflexionCoefficient = Math.max(0, Math.min(1, R + hit.gameObject.material.opacity));
		this.refractionCoefficient = 1 - reflexionCoefficient;
	}
}
