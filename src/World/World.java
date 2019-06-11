package World;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;

import Collision.AABB;
import Game.Camera;
import Graphics.Shader;
import Graphics.Window;

public class World {
	private final int view = 24;
	private byte[] tiles;
	private AABB[] bounding_boxes;
	private int width;
	private int height;
	private int scale;
	
	private Matrix4f world;
	
	public World(String world ) {
		try {
			BufferedImage tile_sheet = ImageIO.read(new File(".\\levels\\"+world+".png"));
			//BufferedImage entity_sheet = ImageIO.read(new File(".\\levels\\"+world+".png"));
			width = tile_sheet.getWidth();
			height = tile_sheet.getHeight();
			scale = 16;
			this.world = new Matrix4f().setTranslation(new Vector3f(0));
			this.world.scale(scale);
			int[] colorTileSheet = tile_sheet.getRGB(0, 0, width, height, null, 0, width);
			tiles = new byte[width * height];
			bounding_boxes = new AABB[width*height];
			
			for (int y = 0; y < height; y++) {
				for (int x = 0; x < width; x++) {
					int red = (colorTileSheet[x+y*width]>>16) & 0xFF;
					Tile t; 
					try {
						t =  Tile.tiles[red];
					} catch (ArrayIndexOutOfBoundsException e) {
						t = null;
					}
					if (t != null) {
						setTile(t, x, y);
					}
				}
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Matrix4f getWorld() {
		return world;
	}
	
	public World() {
		width = 64;
		height = 64;
		scale = 16;
		tiles = new byte[width * height];
		bounding_boxes = new AABB[width * height];
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
		if (tile.isSolid()) {
			bounding_boxes[x + y * width] = new AABB(new Vector2f(x*2,-y*2), new Vector2f(1,1));
		}else {
			bounding_boxes[x + y * width] = null;
		}
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
	public AABB getTileBoundingBox(int x, int y) {
		try {
			return bounding_boxes [x + y * width];
		} catch (ArrayIndexOutOfBoundsException e) {
			return null;
		}
	}
	public Tile getTile(int x, int y) {
		try {
			return Tile.tiles[tiles[x + y * width]];
		} catch (ArrayIndexOutOfBoundsException e) {
			return null;
		}
	}
	public int getScale() {
		return scale;
	}
}
		
		
