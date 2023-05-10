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
	
	private final AmbientLight ambientLight = new AmbientLight(Color.WHITE, 0.01);
	
	public Scene() {
		
	}
	
	public Color reflectionRay(Ray ray) {
		return reflectionRay(ray, null, Constants.MAX_BOUNCES);
	}

	public Color reflectionRay(Ray ray, RaycastHit lastHit, int recursive) {
		if(recursive == 0) {
			return Color.BLACK;
		}
		
		RaycastHit hit = null;
		for(GameObject object : objects) {
			RaycastHit currentHit = object.rayIntersect(ray);
			if(currentHit != null) {
				if(hit == null || hit.distance > currentHit.distance) {
					hit = currentHit;
				}
			}
		}
		
		if(hit == null) {
			
			return specular(ray, lastHit);
			
		} else {
			
			
			if(Constants.VISUALIZE_NORMALS) {
				return ColorBlender.colorFromVector3(hit.normal);
			}
			
			Color light = Color.BLACK;
				
			//Lambert + Ambient light
			light = ColorBlender.addColors(light, ambientLight.getColor());
			
			for(Light l : lights) {
				light = ColorBlender.addColors(light, shadowRay(hit, l));
			}

			//Multiply light by material color
			light = ColorBlender.multiplyColors(light, hit.gameObject.material.getColor(hit.uv));
			light = ColorBlender.multiplyColor(light, hit.gameObject.material.opacity);
			
			
			//Reflected and refracted light
			Color reflectedAndRefractedLight = Color.BLACK;
			Refraction refraction = new Refraction(hit);
			
			//Refracted light
			if(refraction.refractionCoefficient > 0) {
				Ray refractionRay = new Ray(hit.position, refraction.direction);
				Color refractedLight = reflectionRay(refractionRay, hit, recursive-1);
				refractedLight = ColorBlender.multiplyColor(refractedLight, refraction.refractionCoefficient);
				reflectedAndRefractedLight = ColorBlender.addColors(reflectedAndRefractedLight, refractedLight);
			}
			
			
			//Reflected light
			if(refraction.reflexionCoefficient > 0) {
				Ray reflectionRay = new Ray(hit.position, ray.direction.bounce(hit.normal));
				Color reflectedLight = reflectionRay(reflectionRay, hit, recursive-1);
				reflectedLight = ColorBlender.multiplyColor(reflectedLight, refraction.reflexionCoefficient);
				reflectedAndRefractedLight = ColorBlender.addColors(reflectedAndRefractedLight, reflectedLight);	
			}
			
			//Multiply by material color and metallicity
			Color objectColor = hit.gameObject.material.getColor(hit.uv);
			objectColor = ColorBlender.lerp(Color.WHITE, objectColor, hit.gameObject.material.metallicity);
			reflectedAndRefractedLight = ColorBlender.multiplyColors(reflectedAndRefractedLight, objectColor);
			reflectedAndRefractedLight = ColorBlender.multiplyColor(reflectedAndRefractedLight, hit.gameObject.material.smoothness);
			
			light = ColorBlender.addColors(light, reflectedAndRefractedLight);
			
			return light;
		}
	}
	
	private Color shadowRay(RaycastHit hit, Light light) {
		Ray shadowRay = new Ray(hit.position, light.directionToLight(hit.position));
		
		for(GameObject object : objects) {
			if(object.rayIntersect(shadowRay) != null) {
				return Color.BLACK;
			}
		}
		
		double intensity = shadowRay.direction.dotProduct(hit.normal);
		return ColorBlender.multiplyColor(light.getColor(), intensity);
	}
	
	private Color specular(Ray ray, RaycastHit lastHit) {
		if(lastHit != null) {
			
			Color specularLight = Color.BLACK;
			
			for(Light light : lights) {
				double specular = Constants.SPECULAR_CONSTANT * lastHit.gameObject.material.smoothness;
				double cos = light.directionToLight(lastHit.position).dotProduct(ray.direction);
				if(cos > specular) {
					double intenstity = (cos - specular) / (1 - specular);
					specularLight = ColorBlender.addColors(specularLight, ColorBlender.multiplyColor(light.getColor(), intenstity));
				} 
			}
			
			return specularLight;
			
			
		} else {
			return Color.BLACK;
		}
	}
}
