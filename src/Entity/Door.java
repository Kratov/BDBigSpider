package Entity;

import Game.Animation;
import Game.Camera;
import Graphics.Window;
import World.World;

public class Door extends Entity{
	public static final int ANIM_IDLE = 0;
	public static final int ANIM_MOVING_LEFT = 1;
	public static final int ANIM_MOVING_RIGHT = 2;
	public static final int ANIM_SIZE = 1;
	public Door(Transform transform) {
		super(ANIM_SIZE, transform);
		setAnimation(ANIM_IDLE, new Animation(4, 15, "door_idle"));
	}
	@Override
	public void update(float delta, Window wnd, Camera camera, World world) {
		// TODO Auto-generated method stub
		
	}
}
