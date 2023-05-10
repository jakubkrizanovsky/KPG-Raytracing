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
import misc.Refraction;
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
			double specularIntensity = directionalLight.direction.opposite().dotProduct(ray.direction);
			
			
			if(specularIntensity > Constants.SPECULAR_CONSTANT) {
				return ColorBlender.multiplyColor(directionalLight.getColor(), (specularIntensity - Constants.SPECULAR_CONSTANT) / (1 - Constants.SPECULAR_CONSTANT));
			} else {
				return Color.BLACK;
			}
			
			
		} else {
			
			
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
			
			
			//Reflected and refracted light
			Color reflectedAndRefractedLight = Color.BLACK;
			Refraction refraction = new Refraction(hit);
			
			//Refracted light
			if(refraction.refractionCoefficient > 0) {
				Ray refractionRay = new Ray(hit.position, refraction.direction);
				Color refractedLight = reflectionRay(refractionRay, recursive-1, !isRefraction);
				refractedLight = ColorBlender.multiplyColor(refractedLight, refraction.refractionCoefficient);
				reflectedAndRefractedLight = ColorBlender.addColors(reflectedAndRefractedLight, refractedLight);
			}
			
			
			//Reflected light
			if(refraction.reflexionCoefficient > 0) {
				Ray reflectionRay = new Ray(hit.position, ray.direction.bounce(hit.normal));
				Color reflectedLight = reflectionRay(reflectionRay, recursive-1, isRefraction);
				reflectedLight = ColorBlender.multiplyColor(reflectedLight, refraction.reflexionCoefficient);
				reflectedAndRefractedLight = ColorBlender.addColors(reflectedAndRefractedLight, reflectedLight);	
			}
			
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
}
