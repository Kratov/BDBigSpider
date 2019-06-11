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
	public Player(Transform transform) {
		super(new Animation(5, 15, "spider_idle_left"), transform);
	}
	
}

