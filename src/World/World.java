package World;

import org.joml.Matrix4f;
import org.joml.Vector3f;

import Game.Camera;
import Graphics.Shader;
import Graphics.Window;

public class World {
	private final int view = 24;
	private byte[] tiles;
	private int width;
	private int height;
	private int scale;
	
	private Matrix4f world;
	
	public World() {
		width = 64;
		height = 64;
		scale = 16;
		tiles = new byte[width * height];
		
		world = new Matrix4f().setTranslation(new Vector3f(0));
		world.scale(scale);
	}
	
	public void render(TileRenderer render, Shader shader, Camera cam, Window wnd) {
		int posX = ((int) cam.getPosition().x + (wnd.getWidth()/2)) / (scale * 2); //Center of world and offset
		int posY = ((int) cam.getPosition().y - (wnd.getHeight()/2)) / (scale * 2); //Center of world and offset
		
		for (int i = 0; i < view; i++) {
			for (int j = 0; j < view; j++) {
				Tile t = getTile(i-posX, j+posY);
				if (t != null) 
					render.renderTile(t, i-posX, -j-posY, shader, world, cam);
			}
		}
		
	}
	
	public void setTile(Tile tile, int x, int y){
		tiles[x + y * width] = tile.getId();
	}	
	public void correctCamera(Camera camera, Window wnd) {
		Vector3f pos = camera.getPosition();
		
		int w = -width * scale * 2;
		int h = height * scale * 2;
		
		if (pos.x > -(wnd.getWidth()/2)+scale) {
			pos.x = -(wnd.getWidth()/2)+scale;
		}
		if (pos.x < w + (wnd.getWidth()/2)+scale) {
			pos.x = w + (wnd.getWidth()/2)+scale;
		}
		if (pos.y < (wnd.getHeight()/2)-scale) {
			pos.y = (wnd.getHeight()/2)-scale;
		}
		if (pos.y > h - (wnd.getHeight()/2)-scale) {
			pos.y = h - (wnd.getHeight()/2)-scale;
		}
	}
	public Tile getTile(int x, int y) {
		try {
			return Tile.tiles[tiles[x + y * width]];
		} catch (ArrayIndexOutOfBoundsException e) {
			return null;
		}
	}
}
		
		
