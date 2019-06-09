package Graphics;
import java.awt.image.BufferedImage;
import static org.lwjgl.opengl.GL13.*;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import javax.imageio.ImageIO;
import org.lwjgl.BufferUtils;

public class Texture {
	private int id;
	private int width;
	private int height;
	public Texture(String fileName) {
		BufferedImage bi;
		try {
			//Open image
			bi = ImageIO.read(new File(fileName));
			width = bi.getWidth();
			height = bi.getHeight();
			
			//Store pixels 
			 int[] pixelsRaw = bi.getRGB(0, 0, width, height, null, 0, width);
			
			//Create pixels buffer R G B A per pixel
			ByteBuffer pixels = BufferUtils.createByteBuffer(width*height*4);
			
			//Read pixels RGBA
			for (int i = 0; i <  height; ++i) {
				for (int j = 0; j <  width; ++j) {
					int pixel = pixelsRaw[i*width+j];
					pixels.put((byte)((pixel>>16) & 0xFF)); //RED
					pixels.put((byte)((pixel>>8) & 0xFF)); //GREEN
					pixels.put((byte)((pixel) & 0xFF)); //BLUE
					pixels.put((byte)((pixel>>24) & 0xFF)); //ALPHA
				}
			}
			
			pixels.flip();
			
			//ID for texture
			id = glGenTextures();
			
			//Bind texture
			glBindTexture(GL_TEXTURE_2D, id);
			
			//Assign pixels to texture
			glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, pixels);
			
			//Texture parameters
			glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
			glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
			
			
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
	}

	public void bind(int sampler) {
		//Bind texture
		if (sampler >= 0 && sampler <=31) {
			glActiveTexture(GL_TEXTURE0 + sampler);
			glBindTexture(GL_TEXTURE_2D, id);
		}
	}
}
