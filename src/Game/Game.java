package Game;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;
import static org.lwjgl.glfw.GLFW.glfwGetTime;
import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwSetWindowShouldClose;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import Graphics.Graphics;
import Graphics.Texture;
import Graphics.Window;
import Models.Spider;
import World.TileRenderer;

public class Game {

	public static final double FRAMES_PER_SECOND = 1.0f / 60.0f;

	private Camera camera;
	private Graphics graphics;
	private Spider spider;
	private Window wnd;
	private Texture texture;

	private double deltaTime = 0.0f; // Tiempo entre frames
	private double lastTime = glfwGetTime(); // Tiempo antes de frame
	private long timer = System.currentTimeMillis();
	private int frames = 0; // Frames trascurridos1
	private Matrix4f scale = new Matrix4f().scale(16);

	public Game(Window wnd) {
		this.wnd = wnd;
		this.camera = new Camera(wnd.getWidth(), wnd.getHeight()); // Camara proyecta el mundo se mueve al rededor de la
																	// camara
																	// https://www.youtube.com/watch?v=zHlxQoJYUhw
		camera.setPosition(new Vector3f(0, 0, 0)); // Posiciona la camara
		this.spider = new Spider(); // Modelo de ara�a
		this.graphics = new Graphics();
		texture = new Texture(".\\res\\img\\spider_idle_left\\0.png"); // Crea textura de la ara�a
		TileRenderer tiles = new TileRenderer();
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
		wnd.update();
		--deltaTime;
	}

	private void renderGame() {
		glClear(GL_COLOR_BUFFER_BIT);
		// Shader bind
//		this.graphics.getShader().bind();
//		this.graphics.getShader().setUniform("sampler", 0);
		this.graphics.getShader().setUniform("projection", camera.getProjection().mul(scale));
		// texture.bind(0);
		// CreateCapabilitiesRender model
		spider.draw();
		wnd.swapBuffers();
	}
}
