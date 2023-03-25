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
	
	private final double PHONG_CONSTANT = 1;
	
	public final List<GameObject> objects = new LinkedList<GameObject>();
	
	public final List<Light> lights = new LinkedList<Light>();
	
	private final AmbientLight ambientLight = new AmbientLight(Color.WHITE, 0);
	
	private final DirectionalLight directionalLight = new DirectionalLight(Color.WHITE, 1, new Vector3(1, -1, 1));
	
	private final double AIR_REFTRACTION_INDEX = 1;

	public Scene() {
		
	}

	public Color reflectionRay(Ray ray, int recursive) {
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
			
			if(phongIntensity > PHONG_CONSTANT) {
				return ColorBlender.multiplyColor(directionalLight.getColor(), (phongIntensity - PHONG_CONSTANT) / (1 - PHONG_CONSTANT));
			} else {
				return Color.BLACK;
			}
			
		} else {
			Ray bounce = new Ray(hit.position, ray.direction.bounce(hit.normal));
			
			
			//Reflected light
			double intensity = Math.max(0, bounce.direction.dotProduct(hit.normal));
			Color reflectedLight = ColorBlender.multiplyColor(reflectionRay(bounce, recursive-1), intensity);
			
			reflectedLight = ColorBlender.multiplyColors(reflectedLight, ColorBlender.multiplyColor(hit.gameObject.material.color, hit.gameObject.material.metallicity));

			//Lambert + Ambient light
			Color light = ColorBlender.addColors(ambientLight.getColor(), shadowRay(hit));
			light = ColorBlender.multiplyColors(light, hit.gameObject.material.color);
			
			return ColorBlender.addColors(reflectedLight, light);
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
	
	private Color refractionRay(RaycastHit hit, boolean in) {
		
		return Color.BLACK;
	}
	
	private Vector3 refractionVector(RaycastHit hit) {
		Vector3 c = hit.ray.direction.normalize().crossProduct(hit.normal);
		
		double sinPhi1 = c.magnitude();
		double sinPhi2 = AIR_REFTRACTION_INDEX * sinPhi1 / hit.gameObject.material.refractionIndex;
		double tanPhi2 = sinPhi2 / Math.sqrt(1 - sinPhi2*sinPhi2);
		
		Vector3 r = hit.normal.crossProduct(c.normalize()).multiplyBy(tanPhi2);
		return r.subtract(hit.position);
	}

}
