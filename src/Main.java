// GLFW: is an Open Source, multi-platform library for OpenGL, 
		 //OpenGL ES and Vulkan development on the desktop. 
		 //It provides a simple API for creating windows, contexts and surfaces, 
		 //receiving input and events.
/**
 * @author Lina Maria Gonzalez Silva,
 * @author Christian Felipe Rodriguez Valencia,
 * @author Jaime Enrique Zamora Munar
 */

import static org.lwjgl.glfw.GLFW.*; //Import GLFW
import static org.lwjgl.opengl.GL11.*;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;


public class Main {
	
	//Public constants
	public static final int WND_HEIGHT = 480;
	public static final int WND_WIDTH = 640;
	public static final String WND_TITLE = "BIG Spider";
	
	public Main() {
		// Inits GLFW
				if (!glfwInit())  
					throw new IllegalStateException("Failed to initialize GLFW"); 
				
				//Window Options Switch
				glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE); 
				
				// Create window
				long hWindow = glfwCreateWindow(WND_WIDTH, WND_HEIGHT, WND_TITLE, 0, 0);
				if (hWindow == 0) 
					throw new IllegalStateException("Failed to create window");
				
				// Retrive window specs
				GLFWVidMode videoMode = glfwGetVideoMode(glfwGetPrimaryMonitor());
				
				//Center window
				glfwSetWindowPos(hWindow, (videoMode.width() - WND_WIDTH) / 2, (videoMode.height() - WND_HEIGHT) / 2);
				
				//Show window
				glfwShowWindow(hWindow);
				
				//Create context
				glfwMakeContextCurrent(hWindow);
				GL.createCapabilities();
				
				Camera camera = new Camera(WND_WIDTH, WND_HEIGHT);
				
				//Enable textures
				glEnable(GL_TEXTURE_2D);
				
				//Vertices spider
				float[] spiderVertices = new float[] {
						-0.5f,0.5f,0, //TOP LEFT   0
						0.5f,0.5f,0,  //TOP RIGHT   1
						0.5f,-0.5f,0,  //BOTTOM RIGHT	2
						
						-0.5f, -0.5f,0, //BOTTOMLEFT  3
						
				};
				
				//Coords texture spider
				float[] texture = new float[] {
						0,0,
						1,0,
						1,1,
					
						0,1,
				
				};
				
				//Indexes for join vertex
				int[] indexes = new int[] {
						0,1,2,
						2,3,0	
				};
				
				//Spider model
				Model spiderModel = new Model(spiderVertices, texture, indexes);
				
				//GL Create texture
				Texture tex = new Texture(".\\res\\img\\spider_idle_left\\0.png");
				
				//Vertex and fragment shader
				Shader shader = new Shader(".\\shaders\\shader");
				
				Matrix4f scale = new Matrix4f().scale(64);
				
				camera.setPosition(new Vector3f(100,0,0));
		
				
				double frame_cap = 1.0/60.0; // 60 frames per 1.0 second
				
				double frame_time = 0;
				int frames = 0;
				
				double time = Timer.getTime();
				
				double unprocessed = 0;
				
				
				//Window loop receive messages
				while (!glfwWindowShouldClose(hWindow)) {
					
					boolean canRender = false;
					
					double time_2 = Timer.getTime();
					
					double passed = time_2 - time;
					
					unprocessed += passed;
					frame_time += passed;
					
					time = time_2;
					
					while (unprocessed >= frame_cap) {
						 unprocessed -= frame_cap;
						 canRender = true;
						 
						 if(glfwGetKey(hWindow, GLFW_KEY_ESCAPE) == GL_TRUE) {
								glfwSetWindowShouldClose(hWindow, true);
						 }
							
						glfwPollEvents();
						
						if (frame_time >= 1.0) {
							frame_time = 0;
							System.out.println("FPS: " + frames);
							frames = 0;
						}
					}
					
					if (canRender) {
						//Clear context (PIXELS BLACK)
						glClear(GL_COLOR_BUFFER_BIT);
						
						//Shader bind 
						shader.bind();
						shader.setUniform("sampler", 0);
						shader.setUniform("projection", camera.getProjection().mul(scale));
						tex.bind(0);
						//Render model
						spiderModel.render();

						//Swap draw buffer
						glfwSwapBuffers(hWindow);
						
						frames++;
					}

				}
				
				//End program
				glfwTerminate();
	}
	
	public static void main(String[] args) {
		new Main();	
	}
}
