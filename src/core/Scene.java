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
	
	private final DirectionalLight directionalLight = new DirectionalLight(Color.WHITE, 1, new Vector3(1, -1, 1));

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
			return Color.BLACK;
		} else {
			Ray bounce = new Ray(hit.position, ray.direction.bounce(hit.normal));
			
			
			//Reflected light
			double intensity = Math.max(0, bounce.direction.dotProduct(hit.normal));
			Color light = ColorBlender.multiplyColor(reflectionRay(bounce, recursive-1), intensity);
			
			//Ambient light
			light = ColorBlender.addColors(ambientLight.getColor(), light);
			
			//Lambert
			light = ColorBlender.addColors(light, shadowRay(hit));

			return ColorBlender.multiplyColors(light, hit.gameObject.material.color);
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
