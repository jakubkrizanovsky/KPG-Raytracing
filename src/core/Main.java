package core;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import camera.AntiAliasedPerspectiveCamera;
import camera.Camera;
import components.Material;
import components.Transform;
import misc.Ray;
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
	
	private static final Vector3 CAMERA_ORIGIN = new Vector3(0, 2, -10);
	private static final Vector3 ORTHOGONAL_CAMERA_ORIGIN = new Vector3(0, 4, -20);
	private static final Vector3 CAMERA_DIRECTION = new Vector3(0, -0.1, 1);

	public static Camera cam;
	
	private static Plane plane;

	private long lastTime;
	
	

	/**
	 * @param args
	 */
	public static void main(String[] args) {		
		Scene scene = new Scene();
		createScene(scene);
		
		cam = new AntiAliasedPerspectiveCamera(scene, new Transform(CAMERA_ORIGIN, 1, CAMERA_DIRECTION));
		//cam = new PerspectiveCamera(scene, new Transform(CAMERA_ORIGIN, 1, CAMERA_DIRECTION));
		//cam = new OrthogonalCamera(scene, new Transform(ORTHOGONAL_CAMERA_ORIGIN, 10, CAMERA_DIRECTION));
		//exportImage();

		
		//System.out.println(scene.reflectionRay(new Ray(new Vector3(0, 0.75, -5), Vector3.FORWARD), 5, false));
		System.out.println(scene.reflectionRay(new Ray(new Vector3(2, 0.75, -5), Vector3.FORWARD)));
		Main window = new Main();
		window.initialize();
	}
	
	private void initialize() {
		lastTime = System.nanoTime();
		this.setTitle("Raytracing scene");
		this.setSize(WIDTH, HEIGHT);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);	
		this.setLocationRelativeTo(null);	
		this.setVisible(true);
	}
	
	private static void createScene(Scene scene) {
		Sphere sphere = new Sphere(new Transform(new Vector3(-2, 0.5, 2), 1), new Material(Color.GREEN, 1.02, 1));
		Sphere sphere2 = new Sphere(new Transform(new Vector3(0, 0.5, 1), 1), new Material(Color.ORANGE, 3));
		Sphere sphere3 = new Sphere(new Transform(new Vector3(2, 1, 1), 2), new Material(Color.WHITE, 1.02, 0.2));
		Sphere sphere4 = new Sphere(new Transform(new Vector3(2, 0.1, -1), 0.2), new Material(Color.BLUE, 1.02));
		Sphere sphere5 = new Sphere(new Transform(new Vector3(0, 0.3, 0), 0.6), new Material(Color.RED, 1.02, 0.3));
		plane = new Plane(new Transform(Vector3.ZERO, 1000, Vector3.FORWARD), new Material(Color.WHITE, 1));
		Plane plane2 = new Plane(new Transform(new Vector3(0, 0, 15), 20, new Vector3(0, 10, 0)), new Material(Color.WHITE));
		
		scene.objects.add(sphere);
		scene.objects.add(sphere2);
		scene.objects.add(sphere3);
		scene.objects.add(sphere4);
		scene.objects.add(sphere5);
		scene.objects.add(plane);
		//scene.objects.add(plane2);
	}
	
	private static void createScene2(Scene scene) {
		plane = new Plane(new Transform(Vector3.ZERO, 10, Vector3.FORWARD), new Material(Color.WHITE, 1));
		Sphere sphere = new Sphere(new Transform(new Vector3(0, 0.5, -1), 1), new Material(Color.WHITE, 1.52, 0.5));
		
		scene.objects.add(plane);
		scene.objects.add(sphere);
	}
	
	private static void update(double deltaTime) {
		//Move camera slightly to the right
		Vector3 newPos = cam.transform.position.add(cam.transform.right.multiplyBy(deltaTime));
		cam.transform = new Transform(newPos, cam.transform.scale ,plane.transform.position.subtract(newPos));
		
		//Make camera look at plane center
		cam.transform = new Transform(cam.transform.position, cam.transform.scale, plane.transform.position.subtract(cam.transform.position));
	}

	
	public void paint(Graphics g) {
		image = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_3BYTE_BGR);
		
		double timeDelta = (System.nanoTime() - lastTime)*1e-9;
		lastTime = System.nanoTime();
		System.out.println(timeDelta + " s");
		
		update(timeDelta);
		
		cam.generateScene(image);
		
		g.drawImage(image, 0, 0, Color.BLACK, null);
		
		this.repaint();
	}
	
	private static void exportImage() {
		image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_3BYTE_BGR);
		cam.generateScene(image);
		try {
			File outputfile = new File("output.png");
			ImageIO.write(image, "png", outputfile);
		} catch(IOException ex) {
			System.out.println("Could not create output image");
			System.out.println(ex);
		}
	}

}
