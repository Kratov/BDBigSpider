package Entity;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

import org.joml.Matrix3dc;
import org.joml.Matrix3fc;
import org.joml.Matrix3x2fc;
import org.joml.Matrix4dc;
import org.joml.Matrix4f;
import org.joml.Matrix4fc;
import org.joml.Matrix4x3fc;
import org.joml.Quaternionf;
import org.joml.Quaternionfc;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector3fc;
import org.lwjgl.glfw.GLFW;

import Collision.AABB;
import Collision.Collision;
import Game.Animation;
import Game.Camera;
import Graphics.Shader;
import Graphics.Window;
import Models.Model;
import World.World;

public abstract class Entity {
	private static Model model;
	private Animation[] animations;
	private int use_animation;
	protected Transform transform;
	private AABB bounding_box;
	
	public Entity(int max_animations, Transform transform) {
		this.animations = new Animation[max_animations];
		this.transform = transform;
		
		this.use_animation = 0;
		
		bounding_box = new AABB(new Vector2f(transform.pos.x,transform.pos.y), new Vector2f(transform.scale.x,transform.scale.y));
	}
	
	protected void setAnimation(int index, Animation animation) {
		animations[index] = animation;
	}
	
	public void useAnimation(int index) {
		this.use_animation = index;
	}
	
	public void move(Vector2f direction) {
		transform.pos.add(new Vector3f(direction,0));
		bounding_box.getCenter().set(transform.pos.x, transform.pos.y);
	}
	
	public abstract void update(float delta, Window wnd, Camera camera, World world);
	
	public void collideWithTiles(World world) {
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
	}
	
	public void render(Shader shader, Camera camera, World world) {
		Matrix4f target = camera.getProjection();
		target.mul(world.getWorld());
		shader.bind();
		shader.setUniform("sampler", 0);
		shader.setUniform("projection", transform.getProjection(target));
		animations[use_animation].bind(0);
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

	public void collideWithEntity(Entity entity) {
		Collision collision = bounding_box.getCollision(entity.bounding_box);
		if (collision.isIntersecting) {
			collision.distance.x /= 2;
			collision.distance.y /= 2;
			
			bounding_box.correctPosition(entity.bounding_box, collision);
			transform.pos.set(bounding_box.getCenter().x, bounding_box.getCenter().y,0);
			entity.bounding_box.correctPosition(bounding_box, collision);
			entity.transform.pos.set(entity.bounding_box.getCenter().x, entity.bounding_box.getCenter().y,0);
		}
	}
}
