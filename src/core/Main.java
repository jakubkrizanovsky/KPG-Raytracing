package core;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JFrame;

import camera.Camera;
import camera.OrthogonalCamera;
import camera.PerspectiveCamera;
import color_blending.ColorBlender;
import components.Material;
import misc.Ray;
import misc.RaycastHit;
import misc.Vector3;
import objects.GameObject;
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
	
	private static final Vector3 CAMERA_ORIGIN = new Vector3(0, 1, -10);
	private static final Vector3 CAMERA_DIRECTION = new Vector3(0, 0, 1);


	public final static int MAX_BOUNCES = 10;
	
	

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_3BYTE_BGR);
		
		Scene scene = new Scene();
		createScene(scene);
		
		Camera cam = new PerspectiveCamera(scene, CAMERA_ORIGIN, CAMERA_DIRECTION);
		cam.generateScene(image);
		
		Main window = new Main();
		window.initialize();
	}
	
	private void initialize() {
		this.setTitle("Raytracing scene");
		this.setSize(WIDTH, HEIGHT);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);	
		this.setLocationRelativeTo(null);	
		this.setVisible(true);
	}
	
	private static void createScene(Scene scene) {
		Sphere sphere = new Sphere(new Vector3(-2, 0.5, 2), 1, new Material(Color.GREEN));
		Sphere sphere2 = new Sphere(new Vector3(0, 0.5, 1), 1, new Material(Color.ORANGE));
		Sphere sphere3 = new Sphere(new Vector3(2, 1, 1), 2, new Material(Color.WHITE));
		Sphere sphere4 = new Sphere(new Vector3(2, 0.1, -1), 0.2, new Material(Color.BLUE));
		Plane plane = new Plane(Vector3.ZERO, Vector3.UP, 10, new Material(Color.GRAY));
		Plane plane2 = new Plane(new Vector3(0, 0, 5), Vector3.BACK, 10, new Material(Color.WHITE));
		
		scene.objects.add(sphere);
		scene.objects.add(sphere2);
		scene.objects.add(sphere3);
		scene.objects.add(sphere4);
		scene.objects.add(plane);
		scene.objects.add(plane2);
	}
	

	
	public void paint(Graphics g) {
		super.paint(g);
		g.drawImage(image, 0, 0, Color.BLACK, null);
	}

}
