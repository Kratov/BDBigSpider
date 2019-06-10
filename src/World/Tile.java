package World;

public class Tile {
	private byte id;
	private String texture;
	public static byte not = 0; // number of tiles
	public static Tile tiles[] = new Tile[16];
	public static final Tile test_tile = new Tile(".\\res\\texture\\worldbackground");
	public static final Tile test2 = new Tile(".\\res\\texture\\checker");
	public Tile(String texture) {
		this.id = not;
		not++;
		this.texture = texture;
		if (tiles[id] != null) 
			throw new IllegalStateException("Tiles at ["+id+"] is already beaign used!");
		tiles[id] = this;
	}
	public String getTexture() {
		return texture;
	}
	public byte getId() {
		return id;
	}
}
