package Graphics;

public class Graphics {
	
	private Shader shader;
	
	public Graphics() {
		shader = new Shader(".\\shaders\\shader"); //Crea el vertex and fragment shader https://www.khronos.org/opengl/wiki/Rendering_Pipeline_Overview - https://stackoverflow.com/questions/4421261/vertex-shader-vs-fragment-shader
	}	
	
	public Shader getShader() {
		return shader;
	}
}
