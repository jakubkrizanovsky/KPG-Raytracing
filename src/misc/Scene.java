package misc;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JFrame;

import color_blending.BlendMode;
import color_blending.ColorBlender;
import components.Material;
import game_objects.GameObject;
import game_objects.Sphere;

/**
 * 
 */

/**
 * @author jakubkrizanovsky
 *
 */
public class Scene extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	private static List<GameObject> objects = new LinkedList<GameObject>();
	
	private static BufferedImage image;
	
	private final static int WIDTH = 1200;
	private final static int HEIGHT = 675;
	
	private static final Vector3 CAMERA_ORIGIN = new Vector3(0, 0, -10);
	private static final Vector3 CAMERA_DIRECTION = new Vector3(0, 0, 1);

	private final static double CAMERA_FOV_X = 12;
	private final static double CAMERA_FOV_Y = 6.75;
	
	private final static Vector3 LIGHT_DIRECTION = new Vector3(0, 1, 0);
	private final static Color DIRECTIONAL_LIGHT_COLOR = Color.WHITE;
	
	private final static int AMBIENT_LIGHT_INTENSITY = 30;
	private final static Color AMBIENT_LIGHT_COLOR = new Color(AMBIENT_LIGHT_INTENSITY, AMBIENT_LIGHT_INTENSITY, AMBIENT_LIGHT_INTENSITY);
	
	private final static int MAX_BOUNCES = 10;
	
	public final static double MIN_HIT_DISTANCE = 0.01;
	

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_3BYTE_BGR);
		createScene();
		Scene scene = new Scene();
		scene.initialize();
	}
	
	private void initialize() {
		this.setTitle("Raytracing scene");
		this.setSize(WIDTH, HEIGHT);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);	
		this.setLocationRelativeTo(null);	
		this.setVisible(true);
	}
	
	private static void createScene() {
		Sphere sphere = new Sphere(Vector3.ZERO, 0.5, new Material(Color.GREEN));
		Sphere sphere2 = new Sphere(new Vector3(0, 1.5, 1), 0.5, new Material(Color.ORANGE));
		Sphere sphere3 = new Sphere(new Vector3(2, 1, 1), 1, new Material(Color.WHITE));
		Sphere sphere4 = new Sphere(new Vector3(2, -1, -0.1), 0.2, new Material(Color.BLUE));
		objects.add(sphere);
		objects.add(sphere2);
		objects.add(sphere3);
		objects.add(sphere4);
		//Vector3 nearPlaneCenter = CAMERA_ORIGIN.addTo(CAMERA_DIRECTION.multiplyBy(NEAR_PLANE_DISTANCE));
		Vector3 topLeft = CAMERA_ORIGIN.add(new Vector3(-CAMERA_FOV_X/2, 0, 0)).add(new Vector3(0, -CAMERA_FOV_Y/2, 0));
//		double xDiff = CAMERA_FOV_X/WIDTH;
//		double yDiff = CAMERA_FOV_Y/HEIGHT;
		double xDiff = CAMERA_FOV_X/WIDTH;
		double yDiff = CAMERA_FOV_Y/HEIGHT;
		
//		Ray testRay	= new Ray(new Vector3(0, 0, -5), CAMERA_DIRECTION);
//		if(sphere.rayIntersect(testRay) > 0) {
//			System.out.println("Hit");
//		} else {
//			System.out.println("Not Hit");
//		}
		
//		for(int x = 0; x < WIDTH; x++) {
//			for(int y = 0; y < HEIGHT; y++) {
//				Vector3 pixelPosition = nearPlaneTopLeft.addTo(new Vector3(x*xDiff, y*yDiff, 0));
//				Ray ray = new Ray(CAMERA_ORIGIN, pixelPosition.subtract(CAMERA_ORIGIN));
//				System.out.println("Ray dir: " + ray.direction);
//				if(sphere.rayIntersect(ray) > 0) {
//					//image.setRGB(x, y, 1);
//					System.out.println("Hit");
//				} else {
//					//System.out.println("Not Hit");
//				}
//			}
//		}
		
//		for(int x = 0; x < WIDTH; x++) {
//			for(int y = 0; y < HEIGHT; y++) {
//				Vector3 pixelPosition = topLeft.add(new Vector3(x*xDiff, y*yDiff, 0));
//				Ray ray = new Ray(pixelPosition, CAMERA_DIRECTION);
//				
//				RaycastHit hit = sphere.rayIntersect(ray);
//				if(hit != null) {
//					//Ambient light
//					Color light = AMBIENT_LIGHT_COLOR; 
//					
//					//Lambert
//					Vector3 normal = hit.normal;
//					double intensity = LIGHT_DIRECTION.dotProduct(normal);
//					Color lambert = ColorBlender.blendColors(DIRECTIONAL_LIGHT_COLOR, Color.BLACK, new BlendMode(intensity, 1-intensity));
//					
//					light = ColorBlender.addColors(light, lambert);
//					
//					Color color = ColorBlender.multiplyColors(hit.gameObject.material.color, light);
//					
//					image.setRGB(x, y, color.getRGB());
//				}
//			}
//		}
		
		for(int x = 0; x < WIDTH; x++) {
			for(int y = 0; y < HEIGHT; y++) {
				Vector3 pixelPosition = topLeft.add(new Vector3(x*xDiff, y*yDiff, 0));
				Ray ray = new Ray(pixelPosition, CAMERA_DIRECTION);
				Color color = reflectionRay(ray, MAX_BOUNCES);
				
				image.setRGB(x, y, color.getRGB());
			}
		}
	}
	
	private static Color reflectionRay(Ray ray, int recursive) {
		if(recursive == 0) {
			return Color.BLACK;
		}
		
		RaycastHit hit = null;
		for(GameObject object : objects) {
			RaycastHit currentHit = object.rayIntersect(ray);
			if(currentHit != null && currentHit.distnace > MIN_HIT_DISTANCE) {
				if(hit == null || hit.distnace > currentHit.distnace) {
					hit = currentHit;
				}
			}
		}
		
		if(hit == null) {
			return Color.BLACK;
		} else {
			Ray bounce = new Ray(hit.position, ray.direction.bounce(hit.normal));
			
			double intensity =  Math.max(0, bounce.direction.dotProduct(hit.normal));
			Color lrc = ColorBlender.multiplyColor(reflectionRay(bounce, recursive-1), intensity);
			Color light = ColorBlender.addColors(AMBIENT_LIGHT_COLOR, lrc);

			
			//Color light = ColorBlender.addColors(AMBIENT_LIGHT_COLOR, lightRay(bounce, recursive-1));
			
			light = ColorBlender.addColors(light, shadowRay(hit));
			return ColorBlender.multiplyColors(light, hit.gameObject.material.color);
		}
	}
	
	private static Color shadowRay(RaycastHit hit) {
		Ray shadowRay = new Ray(hit.position, LIGHT_DIRECTION.multiplyBy(-1));
		
		for(GameObject object : objects) {
			RaycastHit currentHit = object.rayIntersect(shadowRay);
			if(currentHit != null) {
//				System.out.println("k");
//				System.out.println(hit.gameObject);
//				System.out.println(currentHit.gameObject);
//				System.out.println(currentHit.distnace);
				return Color.BLACK;
			}
		}

		
		double intensity = Math.max(0, shadowRay.direction.dotProduct(hit.normal));
		return ColorBlender.multiplyColor(DIRECTIONAL_LIGHT_COLOR, intensity);
	}
	
	public void paint(Graphics g) {
		super.paint(g);
		g.drawImage(image, 0, 0, Color.BLACK, null);
	}

}
