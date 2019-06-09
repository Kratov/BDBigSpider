package World;

public class Tile {
	private byte id;
	private String texture;
	public static Tile tiles[] = new Tile[16];
	public static final Tile test_tile = new Tile((byte) 0,"test");
	public Tile(byte id, String texture) {
		this.id = id;
		this.texture = texture;
		if (tiles[id] != null) 
			throw new IllegalStateException("Tiles at ["+id+"] is already beaign used!");
		tiles[id] = this;
	}
	public String getTexture() {
		return texture;
	}
}
