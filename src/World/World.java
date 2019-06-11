package World;

import static org.lwjgl.glfw.GLFW.glfwSetWindowShouldClose;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;

import Collision.AABB;
import Collision.ITouchable;
import Game.Camera;
import Graphics.Shader;
import Graphics.Window;
import Entity.Door;
import Entity.Entity;
import Entity.Player;
import Entity.Transform;

public class World {
	private final int view = 24;
	private byte[] tiles;
	private AABB[] bounding_boxes;
	private int width;
	private int height;
	private int scale;
	private Player player;
	private Door door;
	private List<Entity> entities;
	
	private Matrix4f world;
	
	public World(String world, Camera camera) {
		try {
			BufferedImage tile_sheet = ImageIO.read(new File(".\\"+world+"\\tiles.png"));
			BufferedImage entity_sheet = ImageIO.read(new File(".\\"+world+"\\entities.png"));
			width = tile_sheet.getWidth();
			height = tile_sheet.getHeight();
			scale = 16;
			this.world = new Matrix4f().setTranslation(new Vector3f(0));
			this.world.scale(scale);
			int[] colorTileSheet = tile_sheet.getRGB(0, 0, width, height, null, 0, width);
			int[] colorEntitySheet = entity_sheet.getRGB(0, 0, width, height, null, 0, width);
			
			tiles = new byte[width * height];
			bounding_boxes = new AABB[width*height];
			entities = new ArrayList<Entity>();
			
			Transform transform;
			
			for (int y = 0; y < height; y++) {
				for (int x = 0; x < width; x++) {
					int red = (colorTileSheet[x+y*width]>>16) & 0xFF;
					int entity_index = (colorEntitySheet[x+y*width] >> 16) & 0xFF;
					int entity_alpha = (colorEntitySheet[x+y*width] >> 24) & 0xFF;
					Tile t; 
					try {
						t =  Tile.tiles[red];
					} catch (ArrayIndexOutOfBoundsException e) {
						t = null;
					}
					if (t != null) {
						setTile(t, x, y);
					}
					if (entity_alpha > 0) {
						transform = new Transform();
						transform.pos.x = x*2;
						transform.pos.y = -y*2;
						switch (entity_index) {
						case 1:
							player = new Player(transform);
							entities.add(player);
							camera.getPosition().set(transform.pos.mul(-scale, new Vector3f()));
							break;
						case 2:
							door = new Door(transform);
							entities.add(door);
							break;
						default:
							break;
						}
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
		
		for (Entity entity : entities) {
			entity.render(shader, cam, this);
		}
		
	}
	
	public void update(float delta, Window wnd, Camera camera) {
		for (Entity entity : entities) {
			entity.update(delta, wnd, camera, this);
		}
		
		for (int i = 0; i < entities.size(); i++) {
			entities.get(i).collideWithTiles(this);
			for (int j = i + 1; j < entities.size(); j++) {
				entities.get(i).collideWithEntity(entities.get(j), new ITouchable() {
					@Override
					public boolean touching() {
						glfwSetWindowShouldClose(wnd.getHandler(), true);
						return true;
					}
				});
			}
			entities.get(i).collideWithTiles(this);
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
		
		
