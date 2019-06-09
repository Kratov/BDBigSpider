package Graphics;
import static org.lwjgl.glfw.GLFW.GLFW_FALSE;
import static org.lwjgl.glfw.GLFW.GLFW_VISIBLE;
import static org.lwjgl.glfw.GLFW.glfwCreateWindow;
import static org.lwjgl.glfw.GLFW.glfwGetPrimaryMonitor;
import static org.lwjgl.glfw.GLFW.glfwGetVideoMode;
import static org.lwjgl.glfw.GLFW.glfwMakeContextCurrent;
import static org.lwjgl.glfw.GLFW.glfwSetWindowPos;
import static org.lwjgl.glfw.GLFW.glfwShowWindow;
import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.glfw.GLFW.glfwWindowHint;

import org.lwjgl.glfw.GLFWVidMode;
public class Window {
	private int height, width;
	private long handler;
	private String title;
	private boolean isFullScreen;
	public Window(int width, int height, String title) {
		this.height = height;
		this.width = width;
		this.title = title;
		setFullScreen(false);
	}
	
	public void createWindow() {
		glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);  // Visibilidad de ventana falso
		handler = glfwCreateWindow(this.width, this.height, this.title, isFullScreen ? glfwGetPrimaryMonitor() : 0, 0); // Crea ventana retorna manejador
		if (handler == 0) 
			throw new IllegalStateException("Failed to create window");
		if (!isFullScreen) {
			GLFWVidMode videoMode = glfwGetVideoMode(glfwGetPrimaryMonitor()); // Retorna atributos del monitor
			glfwSetWindowPos(handler, (videoMode.width() - this.width) / 2, (videoMode.height() - this.height) / 2); //Centra la ventana
		}
		glfwMakeContextCurrent(handler); // Asigna Contexto de ventana como principal en Hilo https://www.khronos.org/opengl/wiki/OpenGL_Context
	}
	
	public void setSize(int width, int height) {
		this.width = width;
		this.height = height;
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
	public void swapBuffers() {
		//Swap draw buffer
		glfwSwapBuffers(this.handler);
	}
	public void setFullScreen(boolean isFullScreen) {
		this.isFullScreen = isFullScreen;
	}
}
