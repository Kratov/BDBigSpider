package Graphics;
import static org.lwjgl.glfw.GLFW.GLFW_FALSE;
import static org.lwjgl.glfw.GLFW.GLFW_VISIBLE;
import static org.lwjgl.glfw.GLFW.glfwCreateWindow;
import static org.lwjgl.glfw.GLFW.glfwGetPrimaryMonitor;
import static org.lwjgl.glfw.GLFW.glfwGetVideoMode;
import static org.lwjgl.glfw.GLFW.glfwInit;
import static org.lwjgl.glfw.GLFW.glfwMakeContextCurrent;
import static org.lwjgl.glfw.GLFW.glfwSetWindowPos;
import static org.lwjgl.glfw.GLFW.glfwShowWindow;
import static org.lwjgl.glfw.GLFW.glfwWindowHint;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glEnable;

import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
public class Window {
	private int height;
	private int width;
	private String title;
	private long handler;
	public Window(int width, int height, String title) {
		this.height = height;
		this.width = width;
		this.title = title;

		
		glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);  // Visibilidad de ventana falso
		handler = glfwCreateWindow(this.width, this.height, this.title, 0, 0); // Crea ventana retorna manejador
		if (handler == 0) 
			throw new IllegalStateException("Failed to create window");
		GLFWVidMode videoMode = glfwGetVideoMode(glfwGetPrimaryMonitor()); // Retorna atributos del monitor
		glfwSetWindowPos(handler, (videoMode.width() - this.width) / 2, (videoMode.height() - this.height) / 2); //Centra la ventana
		glfwMakeContextCurrent(handler); // Asigna Contexto de ventana como principal en Hilo https://www.khronos.org/opengl/wiki/OpenGL_Context
		GL.createCapabilities(); // Inits OPENGL
		glEnable(GL_TEXTURE_2D); // Activa las texturas en 2D
	}
	public long getHandler() {
		return handler;
	}
	public int getHeight() {
		return height;
	}
	public int getWidth() {
		return width;
	}
	public void show() {
		glfwShowWindow(handler); // Muestra la ventana
	}
}
