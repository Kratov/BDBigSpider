package Models;

public class Spider extends Model {
	
	private static float[] vertices = new float[] { 	//Vertices de la araña https://open.gl/drawing
			-0.5f,0.5f,0, //TOP LEFT   0
			0.5f,0.5f,0,  //TOP RIGHT   1
			0.5f,-0.5f,0,  //BOTTOM RIGHT	2
			-0.5f, -0.5f,0, //BOTTOMLEFT  3
			
	};
	
	private static float[] textureCoords = new float[] { //Coordenadas de la textura https://learnopengl.com/Getting-started/Textures
			0,0, //Lower left of image
			1,0, //Lower Right
			1,1, //Upper right
			0,1, //Upper left 
	};
	
	//Indexes for join vertex
	private static int[] indexes = new int[] {  //Union de los vertices  http://openglbook.com/chapter-3-index-buffer-objects-and-primitive-types.html
			0,1,2,  // 
			2,3,0	
	};
	
	public Spider() {
		super(vertices, textureCoords, indexes);
	}
}
