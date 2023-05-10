package misc;

import core.Constants;

public class Refraction {
	
	public final Vector3 vector;
	public final double reflexionCoefficient;
	
	public Refraction(Vector3 vector, double cosIncidenceAngle, double cosRefractionAngle, double n1, double n2) {
		this.vector = vector;
		
		double Rs = (n1 * cosIncidenceAngle - n2 * cosRefractionAngle) / (n1 * cosIncidenceAngle + n2 * cosRefractionAngle);
		Rs *= Rs;
		double Rp = (n1 * cosRefractionAngle - n2 * cosIncidenceAngle) / (n1 * cosRefractionAngle + n2 * cosIncidenceAngle);
		Rp *= Rp;
		
		this.reflexionCoefficient = 0.5*(Rs + Rp);
	}
	
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
		this.vector = normal.opposite().add(r).normalize();
		
		double Rs = (n1 * cosPhi1 - n2 * cosPhi2) / (n1 * cosPhi1 + n2 * cosPhi2);
		this.reflexionCoefficient = Rs*Rs;
	}
}
