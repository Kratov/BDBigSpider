package Entity;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;

import Collision.AABB;
import Collision.Collision;
import Game.Animation;
import Game.Camera;
import Graphics.Shader;
import Graphics.Window;
import Models.Model;
import World.World;

public class Entity {
	private static Model model;
	private Animation texture;
	private Transform transform;
	private AABB bounding_box;
	
	public Entity(Animation animation, Transform transform) {
		this.texture = animation;
		this.transform = transform;
		
		bounding_box = new AABB(new Vector2f(transform.pos.x,transform.pos.y), new Vector2f(transform.scale.x,transform.scale.y));
	}
	public void update(float delta, Window wnd, Camera camera, World world) {
		if(wnd.getInput().isKeyDown(GLFW.GLFW_KEY_A)) {
			transform.pos.add(new Vector3f(-10 * delta,0,0));
		}
		if(wnd.getInput().isKeyDown(GLFW.GLFW_KEY_D)) {
			transform.pos.add(new Vector3f(10 * delta,0,0));
		}
		if(wnd.getInput().isKeyDown(GLFW.GLFW_KEY_W)) {
			transform.pos.add(new Vector3f(0,10 * delta,0));
		}
		if(wnd.getInput().isKeyDown(GLFW.GLFW_KEY_S)) {
			transform.pos.add(new Vector3f(0,-10 * delta,0));
		}
		
		bounding_box.getCenter().set(transform.pos.x, transform.pos.y);
		
		AABB[] boxes = new AABB[25];
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				boxes[i+j*5] = world.getTileBoundingBox(
						(int)(((transform.pos.x / 2) + 0.5f) - (5/2))+i,
						(int)(((-transform.pos.y / 2) + 0.5f) - (5/2))+j
				);
			}
		}
		
		AABB box = null;
		for (int i = 0; i < boxes.length; i++) {
			if (boxes[i] != null) {
				if (box == null) {
					box = boxes[i];
				}
				Vector2f lenght1 = box.getCenter().sub(transform.pos.x, transform.pos.y, new Vector2f());
				Vector2f lenght2 = boxes[i].getCenter().sub(transform.pos.x, transform.pos.y, new Vector2f());
				
				if (lenght1.lengthSquared() > lenght2.lengthSquared()) {
					box = boxes[i];
				}
			}
		}
		
		if (box != null) {
			Collision data = bounding_box.getCollision(box);
			if (data.isIntersecting) {
				bounding_box.correctPosition(box, data);
				transform.pos.set(bounding_box.getCenter(),0);
			}
			
			
			for (int i = 0; i < boxes.length; i++) {
				if (boxes[i] != null) {
					if (box == null) {
						box = boxes[i];
					}
					Vector2f lenght1 = box.getCenter().sub(transform.pos.x, transform.pos.y, new Vector2f());
					Vector2f lenght2 = boxes[i].getCenter().sub(transform.pos.x, transform.pos.y, new Vector2f());
					
					if (lenght1.lengthSquared() > lenght2.lengthSquared()) {
						box = boxes[i];
					}
				}
			}
			
		
			data = bounding_box.getCollision(box);
			if (data.isIntersecting) {
				bounding_box.correctPosition(box, data);
				transform.pos.set(bounding_box.getCenter(),0);
			}
		}
		camera.getPosition().lerp(transform.pos.mul(-world.getScale(), new Vector3f()), 0.05f);
	}
	public void render(Shader shader, Camera camera, World world) {
		Matrix4f target = camera.getProjection();
		target.mul(world.getWorld());
		shader.bind();
		shader.setUniform("sampler", 0);
		shader.setUniform("projection", transform.getProjection(target));
		texture.bind(0);
		model.draw();
	}
	
	public static void initAsset() {
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
	}
	
	public static void deleteAsset() {
		model = null;
	}
}
