import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JFrame;

import components.Material;
import game_objects.GameObject;
import game_objects.Sphere;
import misc.Ray;
import misc.RaycastHit;
import misc.Vector3;

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
	
	private final static double NEAR_PLANE_DISTANCE = 1;
	private final static double CAMERA_FOV_X = 12;
	private final static double CAMERA_FOV_Y = 6.75;
	
	private final static Vector3 LIGHT_DIRECTION = new Vector3(0, -1, 0);
	private final static Color LIGHT_COLOR = Color.WHITE;
	

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
		
		for(int x = 0; x < WIDTH; x++) {
			for(int y = 0; y < HEIGHT; y++) {
				Vector3 pixelPosition = topLeft.add(new Vector3(x*xDiff, y*yDiff, 0));
				Ray ray = new Ray(pixelPosition, CAMERA_DIRECTION);
				RaycastHit hit = sphere.rayIntersect(ray);
				if(hit != null) {
					image.setRGB(x, y, hit.gameObject.material.color.getRGB());
					
				}
			}
		}
	}
	
	public void paint(Graphics g) {
		super.paint(g);
		g.drawImage(image, 0, 0, Color.BLACK, null);
	}

}
