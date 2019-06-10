package World;

import java.util.HashMap;

import org.joml.Matrix4f;
import org.joml.Vector3f;

import Game.Camera;
import Graphics.Shader;
import Graphics.Texture;
import Models.Model;

public class TileRenderer {
	private HashMap<String, Texture> tile_textures;
	private Model model;
	
	public TileRenderer() {
		tile_textures = new HashMap<String, Texture>();
		float[] vertices = new float[] { 	//Vertices de la araña https://open.gl/drawing
				-1f,1f,0, //TOP LEFT   0
				 1f,1f,0,  //TOP RIGHT   1
				 1f,-1f,0,  //BOTTOM RIGHT	2
				-1f,-1,0, //BOTTOMLEFT  3
				
		};
		
		float[] textureCoords = new float[] { //Coordenadas de la textura https://learnopengl.com/Getting-started/Textures
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
		
		model = new Model(vertices, textureCoords, indexes);
		
		for (int i = 0; i < Tile.tiles.length; i++) {
			if(Tile.tiles[i] != null) {
				if (!tile_textures.containsKey(Tile.tiles[i].getTexture())) {
					String tex = Tile.tiles[i].getTexture();
					tile_textures.put(Tile.tiles[i].getTexture(), new Texture(tex+".png"));
				}			
			}
		}
	}
	
	public void renderTile(Tile tile, int x, int y, Shader shader, Matrix4f world, Camera cam) {
		shader.bind();
		if (tile_textures.containsKey(tile.getTexture())) 
			tile_textures.get(tile.getTexture()).bind(0);
		Matrix4f tile_pos = new Matrix4f().translate(new Vector3f(x*2, y*2,0));
		Matrix4f target = new Matrix4f();
		
		cam.getProjection().mul(world, target);
		target.mul(tile_pos);
		
		shader.setUniform("sampler", 0);
		shader.setUniform("projection", target);
		
		model.draw();
	}
	
}
