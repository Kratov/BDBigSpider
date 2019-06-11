package Game;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;
import static org.lwjgl.glfw.GLFW.glfwGetTime;
import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwSetWindowShouldClose;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;

import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;

import Entity.Entity;
import Entity.Player;
import Entity.Transform;
import Graphics.Graphics;
import Graphics.Texture;
import Graphics.Window;
import World.Tile;
import World.TileRenderer;
import World.World;

public class Game {

	public static final double FRAMES_PER_SECOND = 1.0f / 60.0f;

	private Camera camera;
	private Graphics graphics;
	private Window wnd;
	private Texture texture;
	private TileRenderer tiles;
	private World world;
	private Player player;

	private double deltaTime = 0.0f; // Tiempo entre frames
	private double lastTime = glfwGetTime(); // Tiempo antes de frame
	private long timer = System.currentTimeMillis();
	private int frames = 0; // Frames trascurridos1

	public Game(Window wnd) {
		this.wnd = wnd;
		this.camera = new Camera(wnd.getWidth(), wnd.getHeight()); // Camara proyecta el mundo se mueve al rededor de la
		Entity.initAsset();															// camara https://www.youtube.com/watch?v=zHlxQoJYUhw
		world = new World("levels",camera);
		camera.setPosition(new Vector3f(0, 0, 0)); // Posiciona la camara
		this.graphics = new Graphics();
		//texture = new Texture(".\\res\\img\\spider_idle_left\\0.png"); // Crea textura de la araña
		tiles = new TileRenderer();
	}

	public void go() {

		double currentTime = glfwGetTime();
		deltaTime += (currentTime - lastTime) / FRAMES_PER_SECOND; // FRAMES_PER_SECOND;
		lastTime = currentTime;
		while (deltaTime >= 1.0f) {
			update();
			renderGame();
			frames++;
		}

		if (System.currentTimeMillis() - timer > 1000) {
			timer += 1000;
			System.out.println(frames + " fps");
			frames = 0;
		}
	}

	private void update() {
		glfwPollEvents();
		if (wnd.getInput().isKeyReleased(GLFW_KEY_ESCAPE))
			glfwSetWindowShouldClose(this.wnd.getHandler(), true);
		world.update((float)FRAMES_PER_SECOND, wnd, camera);
		world.correctCamera(camera, wnd);
		wnd.update();
		--deltaTime;
	}
	
	

	private void renderGame() {
		glClear(GL_COLOR_BUFFER_BIT);
		// CreateCapabilitiesRender model
		world.render(tiles, graphics.getShader(), camera, wnd);
		wnd.swapBuffers();
	}
}
