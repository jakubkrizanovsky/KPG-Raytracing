package misc;

import core.Constants;

public class Refraction {
	
	public final Vector3 direction;
	public final double reflexionCoefficient;
	public final double refractionCoefficient;
	
	public Refraction(RaycastHit hit) {
		double cosPhi1 = hit.cosAngle;
		
		double n1, n2;
		if(cosPhi1 > 0) {
			n1 = Constants.AIR_REFRACTION_INDEX;
			n2 = hit.gameObject.material.refractionIndex;
		} else {
			n1 = hit.gameObject.material.refractionIndex;
			n2 = Constants.AIR_REFRACTION_INDEX;
			cosPhi1 = -cosPhi1;
		}
		
		Vector3 c = hit.ray.direction.crossProduct(hit.normal);
		
		double sinPhi1 = c.magnitude();
		double sinPhi2 = n1 * sinPhi1 / n2;
		
		double cosPhi2 = Math.sqrt(1 - sinPhi2*sinPhi2);
		double tanPhi2 = sinPhi2 / cosPhi2;
		
		Vector3 r = hit.normal.crossProduct(c.normalize()).multiplyBy(tanPhi2);
		this.direction = hit.normal.opposite().add(r).normalize();
		
		double Rs = (n1 * cosPhi1 - n2 * cosPhi2) / (n1 * cosPhi1 + n2 * cosPhi2);
		Rs *= Rs;
		double Rp = (n1 * cosPhi2 - n2 * cosPhi1) / (n1 * cosPhi2 + n2 * cosPhi1);
		Rp *= Rp;
		double R = 0.5*(Rs + Rp);

		this.reflexionCoefficient = Util.clamp(R + hit.gameObject.material.opacity, 0, 1);
		this.refractionCoefficient = 1 - reflexionCoefficient;
	}
}
