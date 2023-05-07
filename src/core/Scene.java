package core;

import java.awt.Color;
import java.util.LinkedList;
import java.util.List;

import color_blending.ColorBlender;
import light.AmbientLight;
import light.DirectionalLight;
import light.Light;
import misc.Ray;
import misc.RaycastHit;
import misc.Vector3;
import objects.GameObject;

public class Scene {
	
	public final List<GameObject> objects = new LinkedList<GameObject>();
	
	public final List<Light> lights = new LinkedList<Light>();
	
	private final AmbientLight ambientLight = new AmbientLight(Color.WHITE, 0.1);
	
	private final DirectionalLight directionalLight = new DirectionalLight(Color.WHITE, 0.8, new Vector3(1, -1, -1));

	public Scene() {
		
	}
	
	public Color reflectionRay(Ray ray) {
		return reflectionRay(ray, Constants.MAX_BOUNCES, false);
	}

	public Color reflectionRay(Ray ray, int recursive, boolean isRefraction) {
		if(recursive == 0) {
			return Color.BLACK;
		}
		
		RaycastHit hit = null;
		for(GameObject object : objects) {
			RaycastHit currentHit = object.rayIntersect(ray);
			if(currentHit != null) {
				if(hit == null || hit.distnace > currentHit.distnace) {
					hit = currentHit;
				}
			}
		}
		
		if(hit == null) {
			double phongIntensity = directionalLight.direction.opposite().dotProduct(ray.direction);
			
			if(phongIntensity > Constants.PHONG_CONSTANT) {
				return ColorBlender.multiplyColor(directionalLight.getColor(), (phongIntensity - Constants.PHONG_CONSTANT) / (1 - Constants.PHONG_CONSTANT));
			} else {
				return Color.BLACK;
			}
			
		} else {
			
//			if(true) {
//				return ColorBlender.colorFromVector3(refractionVector2(hit, !isRefraction).opposite());
//			}
			
//			if(true) {
//				return ColorBlender.colorFromVector3(hit.normal);
//			}
			
			Color light = Color.BLACK;
				
			//Lambert + Ambient light
			light = ColorBlender.addColors(light, ambientLight.getColor());
			light = ColorBlender.addColors(light, shadowRay(hit));

			//Multiply light by material color
			light = ColorBlender.multiplyColors(light, hit.gameObject.material.color);
			light = ColorBlender.multiplyColor(light, hit.gameObject.material.opacity);
			
			
			Color reflectedAndRefractedLight = Color.BLACK;
			
			//Refracted light
			Ray refractionRay = new Ray(hit.position, refractionVector2(hit, !isRefraction));
			//double refractionIntensity = Math.max(0, refractionRay.direction.dotProduct(hit.normal.opposite()));
			double refractionIntensity = 1;
			Color refractedLight = reflectionRay(refractionRay, recursive-1, !isRefraction);
			refractedLight = ColorBlender.multiplyColor(refractedLight, refractionIntensity*(1-hit.gameObject.material.opacity));
			reflectedAndRefractedLight = ColorBlender.addColors(reflectedAndRefractedLight, refractedLight);
			
			//Reflected light
			Ray reflectionRay = new Ray(hit.position, ray.direction.bounce(hit.normal));
			double reflectionIntensity = Math.max(0, reflectionRay.direction.dotProduct(hit.normal));
			Color reflectedLight = reflectionRay(reflectionRay, recursive-1, isRefraction);
			reflectedLight = ColorBlender.multiplyColor(reflectedLight, reflectionIntensity*hit.gameObject.material.opacity);
			reflectedAndRefractedLight = ColorBlender.addColors(reflectedAndRefractedLight, reflectedLight);
		
			//Multiply by material color and metallicity
			reflectedAndRefractedLight = ColorBlender.multiplyColors(reflectedAndRefractedLight, ColorBlender.multiplyColor(hit.gameObject.material.color, hit.gameObject.material.metallicity));
			
			light = ColorBlender.addColors(light, reflectedAndRefractedLight);
			
			return light;
		}
	}
	
	private Color shadowRay(RaycastHit hit) {
		Ray shadowRay = new Ray(hit.position, directionalLight.direction.opposite());
		
		for(GameObject object : objects) {
			RaycastHit currentHit = object.rayIntersect(shadowRay);
			if(currentHit != null) {
				return Color.BLACK;
			}
		}

		
		double intensity = shadowRay.direction.dotProduct(hit.normal);
		return ColorBlender.multiplyColor(directionalLight.getColor(), intensity);
	}
	
	private Vector3 refractionVector(RaycastHit hit, boolean isExiting) {
		//System.out.println("IN: " + hit.ray.direction);
		double n1, n2;
		Vector3 normal;
		if(isExiting) {
			n1 = hit.gameObject.material.refractionIndex;
			n2 = Constants.AIR_REFRACTION_INDEX;
			normal = hit.normal.opposite();
		} else {
			n1 = Constants.AIR_REFRACTION_INDEX;
			n2 = hit.gameObject.material.refractionIndex;
			normal = hit.normal;
		}
		Vector3 c = hit.ray.direction.crossProduct(normal);
		
		double sinPhi1 = c.magnitude();
		double sinPhi2 = n1 * sinPhi1 / n2;
		double cosPhi2 = Math.sqrt(1 - sinPhi2*sinPhi2);
		double tanPhi2 = sinPhi2 / cosPhi2;
		
		Vector3 r = normal.crossProduct(c.normalize()).multiplyBy(tanPhi2);
		//System.out.println("OUT: " + normal.opposite().add(r));
		return normal.opposite().add(r).normalize();
	}
	
	private Vector3 refractionVector2(RaycastHit hit, boolean isExiting) {
		double n1, n2;
		Vector3 normal;
		if(isExiting) {
			n1 = hit.gameObject.material.refractionIndex;
			n2 = Constants.AIR_REFRACTION_INDEX;
			normal = hit.normal.opposite();
		} else {
			n1 = Constants.AIR_REFRACTION_INDEX;
			n2 = hit.gameObject.material.refractionIndex;
			normal = hit.normal;
		}
		
		double c = - normal.dotProduct(hit.ray.direction);
		double r = n1/n2;
		Vector3 s = normal.multiplyBy(r*c + Math.sqrt(1 - r*r*(1 - c*c)));

		return hit.ray.direction.multiplyBy(r).add(s);
	}

}
