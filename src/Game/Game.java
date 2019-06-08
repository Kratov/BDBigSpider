package Game;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;
import static org.lwjgl.glfw.GLFW.glfwGetKey;
import static org.lwjgl.glfw.GLFW.glfwGetTime;
import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwSetWindowShouldClose;
import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_TRUE;
import static org.lwjgl.opengl.GL11.glClear;

import org.joml.Matrix4f;
import org.joml.Vector3f;

import Graphics.Shader;
import Graphics.Texture;
import Graphics.Window;
import Models.Model;

public class Game {
	
	private Camera camera;
	
	public Game(Window wnd) {
		this.camera = new Camera(wnd.getWidth(), wnd.getHeight()); // Camara proyecta el mundo se mueve al rededor de la camara https://www.youtube.com/watch?v=zHlxQoJYUhw
		float[] spiderVertices = new float[] { 	//Vertices de la araña https://open.gl/drawing
				-0.5f,0.5f,0, //TOP LEFT   0
				0.5f,0.5f,0,  //TOP RIGHT   1
				0.5f,-0.5f,0,  //BOTTOM RIGHT	2
				-0.5f, -0.5f,0, //BOTTOMLEFT  3
				
		};
			
		float[] texture = new float[] { //Coordenadas de la textura https://learnopengl.com/Getting-started/Textures
				0,0, //Lower left of image
				1,0, //Lower Right
				1,1, //Upper right
				0,1, //Upper left 
		
		};
		
		//Indexes for join vertex
		int[] indexes = new int[] {  //Union de los vertices  http://openglbook.com/chapter-3-index-buffer-objects-and-primitive-types.html
				0,1,2,  // 
				2,3,0	
		};
		
		Model spiderModel = new Model(spiderVertices, texture, indexes); //Modelo de araña
		
		Texture tex = new Texture(".\\res\\img\\spider_idle_left\\0.png"); // Crea textura de la araña
		
		Shader shader = new Shader(".\\shaders\\shader"); //Crea el vertex and fragment shader https://www.khronos.org/opengl/wiki/Rendering_Pipeline_Overview - https://stackoverflow.com/questions/4421261/vertex-shader-vs-fragment-shader
		 
		Matrix4f scale = new Matrix4f().scale(45); //Escala de proyeccion de la camara
		
		camera.setPosition(new Vector3f(0,0,0)); //Posiciona la camara
		
		double deltaTime = 0.0f; //Tiempo entre frames
		int frames = 0; //Frames trascurridos
		double lastTime = glfwGetTime(); //Tiempo antes de frame
		
		long timer = System.currentTimeMillis();
	}
	
	public void go() {
		double currentTime = glfwGetTime();
		deltaTime += (currentTime - lastTime) / FRAMES_PER_SECOND;
		lastTime = currentTime;
		
		while (deltaTime >= 1.0f) {
			glfwPollEvents();
			if(glfwGetKey(window.getHandler(), GLFW_KEY_ESCAPE) == GL_TRUE) {
				glfwSetWindowShouldClose(window.getHandler(), true);
			}
			--deltaTime;
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
			glfwSwapBuffers(window.getHandler());
			frames++; 
		}
		
		if (System.currentTimeMillis() - timer > 1000) {
            timer += 1000;
            System.out.println(frames + " fps");
            frames = 0;
         }
	}
	
	
}
