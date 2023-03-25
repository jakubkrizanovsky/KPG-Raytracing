package core;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

import camera.AntiAliasedPerspectiveCamera;
import camera.Camera;
import components.Material;
import components.Transform;
import misc.Vector3;
import objects.Plane;
import objects.Sphere;

/**
 * 
 */

/**
 * @author jakubkrizanovsky
 *
 */
public class Main extends JFrame {
	
	private static final long serialVersionUID = 1L;

	
	private static BufferedImage image;
	
	private final static int WIDTH = 1200;
	private final static int HEIGHT = 675;
	
	private static final Vector3 CAMERA_ORIGIN = new Vector3(0, 1, -5);
	private static final Vector3 CAMERA_DIRECTION = new Vector3(0, -0.5, 1);



	public static Camera cam;
	
	private static Plane plane;
	
	

	/**
	 * @param args
	 */
	public static void main(String[] args) {		
		Scene scene = new Scene();
		createScene(scene);
		
		cam = new AntiAliasedPerspectiveCamera(scene, new Transform(CAMERA_ORIGIN, 1, CAMERA_DIRECTION));
		
		Main window = new Main();
		window.initialize();
		
//		Timer timer = new Timer();
//		timer.schedule(new TimerTask() {
//			
//			@Override
//			public void run() {
//				Main.fixedUpdate();
//			}
//		},
//				0,
//				(long)(1e3/Constants.FIXED_UPDATE_FREQUENCY));
	}
	
	private void initialize() {
		this.setTitle("Raytracing scene");
		this.setSize(WIDTH, HEIGHT);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);	
		this.setLocationRelativeTo(null);	
		this.setVisible(true);
	}
	
	private static void createScene(Scene scene) {
		Sphere sphere = new Sphere(new Transform(new Vector3(-2, 0.5, 2), 1), new Material(Color.GREEN));
		Sphere sphere2 = new Sphere(new Transform(new Vector3(0, 0.5, 1), 1), new Material(Color.ORANGE));
		Sphere sphere3 = new Sphere(new Transform(new Vector3(2, 1, 1), 2), new Material(Color.WHITE));
		Sphere sphere4 = new Sphere(new Transform(new Vector3(2, 0.1, -1), 0.2), new Material(Color.BLUE));
		plane = new Plane(new Transform(Vector3.ZERO, 10, Vector3.FORWARD), new Material(Color.WHITE));
		Plane plane2 = new Plane(new Transform(new Vector3(0, 5, 6), 10, new Vector3(0, 10, 0)), new Material(Color.WHITE));
		
		scene.objects.add(sphere);
		scene.objects.add(sphere2);
		scene.objects.add(sphere3);
		scene.objects.add(sphere4);
		scene.objects.add(plane);
		scene.objects.add(plane2);
	}
	
	public static void fixedUpdate() {
		//cam.transform.position = cam.transform.position.add(new Vector3(0, 0.004, -0.008));
	}
	
	private static void update() {
		Vector3 newPos = cam.transform.position.add(cam.transform.right.multiplyBy(0.2));
		cam.transform = new Transform(newPos, plane.transform.position.subtract(newPos));

	}

	
	public void paint(Graphics g) {
		
		
		image = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_3BYTE_BGR);
		
		update();
		
		long start = System.nanoTime();
		cam.generateScene(image);
		System.out.println((System.nanoTime() - start)*1e-9 + " s");
		
		g.drawImage(image, 0, 0, Color.BLACK, null);
		
		
		this.repaint();
	}

}
