package Entity;

import org.joml.Vector2f;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;

import Collision.AABB;
import Collision.Collision;
import Game.Animation;
import Game.Camera;
import Graphics.Shader;
import Graphics.Texture;
import Graphics.Window;
import Models.Model;
import World.World;

public class Player extends Entity {
	public static final int ANIM_IDLE = 0;
	public static final int ANIM_MOVING_LEFT = 1;
	public static final int ANIM_MOVING_RIGHT = 2;
	public static final int ANIM_SIZE = 3;
	public Player(Transform transform) {
		super(ANIM_SIZE, transform);
		setAnimation(ANIM_IDLE, new Animation(5, 15, "spider_idle_left"));
		setAnimation(ANIM_MOVING_LEFT, new Animation(5, 15, "spider_move_left"));
		setAnimation(ANIM_MOVING_RIGHT, new Animation(5, 15, "spider_move_right"));
	}
	
	@Override
	public void update(float delta, Window wnd, Camera camera, World world) {
		Vector2f movement = new Vector2f();
		
		if(wnd.getInput().isKeyDown(GLFW.GLFW_KEY_A)) {
			movement.add(-10 * delta,0);
			useAnimation(ANIM_MOVING_LEFT);
		}else
		if(wnd.getInput().isKeyDown(GLFW.GLFW_KEY_D)) {
			movement.add(10 * delta,0);
			useAnimation(ANIM_MOVING_RIGHT);
		}else
		if(wnd.getInput().isKeyDown(GLFW.GLFW_KEY_W)) {
			movement.add(0,10 * delta);
			useAnimation(ANIM_MOVING_RIGHT);
		}else
		if(wnd.getInput().isKeyDown(GLFW.GLFW_KEY_S)) {
			movement.add(0,-10 * delta);
			useAnimation(ANIM_MOVING_RIGHT);
		}else {
			useAnimation(ANIM_IDLE);
		}
		
		move(movement);
		
//		if (movement.x != 0 || movement.y != 0) {
//			useAnimation(ANIM_MOVING_LEFT);
//		}else {
//			useAnimation(ANIM_IDLE);
//		}
		
		camera.getPosition().lerp(transform.pos.mul(-world.getScale(), new Vector3f()), 0.05f);
	}
}



