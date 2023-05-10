package core;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import camera.AntiAliasedPerspectiveCamera;
import camera.Camera;
import camera.OrthogonalCamera;
import components.Material;
import components.Texture;
import components.Transform;
import light.DirectionalLight;
import light.PointLight;
import misc.ImageLoader;
import misc.Util;
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
	
	private static final Vector3 CAMERA_ORIGIN = new Vector3(0, 2, -10);
	private static final Vector3 ORTHOGONAL_CAMERA_ORIGIN = new Vector3(0, 4, -20);
	private static final Vector3 CAMERA_DIRECTION = new Vector3(0, -0.1, 1);

	public static Camera cam;

	private long lastTime;
	
	

	/**
	 * @param args
	 */
	public static void main(String[] args) {		
		Scene scene = new Scene();
		createRandomScene(scene);
		
		cam = new AntiAliasedPerspectiveCamera(scene, new Transform(CAMERA_ORIGIN, 1, CAMERA_DIRECTION));
		//cam = new OrthogonalCamera(scene, new Transform(ORTHOGONAL_CAMERA_ORIGIN, 10, CAMERA_DIRECTION));
		exportImage();

		
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
		
		while(true) {
			this.repaint();
		}
	}
	
	private static void createScene(Scene scene) {
		DirectionalLight directionalLight = new DirectionalLight(Color.WHITE, 0.7, new Vector3(1, -1, -1));
		PointLight pointLight = new PointLight(Color.WHITE, 0.4, new Vector3(3, 4, -10));
		
		scene.lights.add(directionalLight);
		scene.lights.add(pointLight);
		

		Sphere sphere = new Sphere(new Transform(new Vector3(-2, 0.5, 2), 1), new Material(Color.GREEN, 1.02, 1, 0, 0));
		Sphere sphere2 = new Sphere(new Transform(new Vector3(0, 0.5, 1), 1), new Material(Color.ORANGE, 50, 1, 1, 0.5));
		Sphere sphere3 = new Sphere(new Transform(new Vector3(2, 1, 1), 2), new Material(Color.WHITE, 1.02, 0.1));
		Sphere sphere4 = new Sphere(new Transform(new Vector3(2, 0.1, -1), 0.2), new Material(Color.BLUE, 1.02));
		Sphere sphere5 = new Sphere(new Transform(new Vector3(0, 0.3, 0), 0.6), new Material(Color.RED, 1.02, 0.3));
		Sphere sphere6 = new Sphere(new Transform(new Vector3(-3, 1.5, -2), 3), new Material(Color.CYAN, 1.02, 0.1));
		
		Plane plane = new Plane(new Transform(Vector3.ZERO, 10, Vector3.FORWARD), new Material(Color.WHITE, 1));
		plane.material.texture = new Texture(ImageLoader.loadImage("checkered.jpg"));
		
		Plane mirror = new Plane(new Transform(new Vector3(0, 0, 15), 20, new Vector3(0, 10, 0)), new Material(Color.WHITE));
		
		scene.objects.add(sphere);
		scene.objects.add(sphere2);
		scene.objects.add(sphere3);
		scene.objects.add(sphere4);
		scene.objects.add(sphere5);
		scene.objects.add(sphere6);
		scene.objects.add(plane);
		scene.objects.add(mirror);
	}
	
	private static void createScene2(Scene scene) {
		DirectionalLight redLight = new DirectionalLight(Color.CYAN, 0.8, new Vector3(1, -1, 1));
		DirectionalLight greenLight = new DirectionalLight(Color.MAGENTA, 0.8, new Vector3(1, -1, -1));
		DirectionalLight blueLight = new DirectionalLight(Color.YELLOW, 0.8, new Vector3(-1, -1, 0));
		
		scene.lights.add(redLight);
		scene.lights.add(greenLight);
		scene.lights.add(blueLight);
		
		Plane plane = new Plane(new Transform(Vector3.ZERO, 1000, Vector3.FORWARD), new Material(Color.WHITE, 1));
		Sphere sphere = new Sphere(new Transform(Vector3.UP, 2), new Material(Color.RED, 1.02, 1));
		Sphere sphere2 = new Sphere(new Transform(new Vector3(3, 0.5, 0), 1), new Material(Color.WHITE, 1.02, 0.1));
		Sphere sphere3 = new Sphere(new Transform(new Vector3(-3, 0.5, 0), 1), new Material(Color.WHITE, 1.02, 0.1));
		Sphere sphere4 = new Sphere(new Transform(new Vector3(0, 0.5, 3), 1), new Material(Color.WHITE, 1.02, 0.1));
		Sphere sphere5 = new Sphere(new Transform(new Vector3(0, 0.5, -3), 1), new Material(Color.WHITE, 1.02, 0.1));
		
		scene.objects.add(plane);
		scene.objects.add(sphere);
		scene.objects.add(sphere2);
		scene.objects.add(sphere3);
		scene.objects.add(sphere4);
		scene.objects.add(sphere5);
	}
	
	private static void createScene3(Scene scene) {
		Plane plane = new Plane(new Transform(Vector3.ZERO, 10, Vector3.FORWARD), new Material(Color.WHITE, 1));
		plane.material.texture = new Texture(ImageLoader.loadImage("checkered.jpg"));
		//Sphere sphere = new Sphere(new Transform(new Vector3(0, 0.5, -1), 1), new Material(Color.WHITE, 1.52, 0.5));
		
		scene.objects.add(plane);
		//scene.objects.add(sphere);
	}
	
	private static void createRandomScene(Scene scene) {
		PointLight pointLight = new PointLight(Color.WHITE, 0.8, new Vector3(0, 3, 0));
		scene.lights.add(pointLight);
		
		Plane plane = new Plane(new Transform(Vector3.ZERO, 1000, Vector3.FORWARD), new Material(Color.WHITE, 1));
		scene.objects.add(plane);
		
		Random r = new Random();
		
		for(int i = 0; i < 25; i++) {
			double scale = r.nextDouble();
			Transform t = new Transform(new Vector3(r.nextDouble(), 0, r.nextDouble()).subtract(Vector3.ONE.subtract(Vector3.UP).multiplyBy(0.5)).multiplyBy(10).add(Vector3.UP.multiplyBy(0.5*scale)), scale);
			Material material = new Material(Util.randomColor(r), 1 + r.nextDouble(), r.nextDouble(), r.nextDouble(), r.nextDouble());
			GameObject sphere = new Sphere(t, material);
			
			scene.objects.add(sphere);
		}
	}
	
	private static void update(double deltaTime) {
		//Move camera slightly to the right
		Vector3 newPos = cam.transform.position.add(cam.transform.right.multiplyBy(deltaTime));
		cam.transform = new Transform(newPos, cam.transform.scale, newPos.multiplyBy(-1));
		
		//Make camera look at [0, 0, 0]
		cam.transform = new Transform(cam.transform.position, cam.transform.scale, cam.transform.position.multiplyBy(-1));
	}

	
	public void paint(Graphics g) {
		image = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_3BYTE_BGR);
		
		double timeDelta = (System.nanoTime() - lastTime)*1e-9;
		lastTime = System.nanoTime();
		System.out.println(timeDelta + " s");
		
		update(timeDelta);
		
		cam.generateScene(image);
		
		g.drawImage(image, 0, 0, Color.BLACK, null);
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
