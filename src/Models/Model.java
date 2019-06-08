package Models;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;
public class Model {
	private int drawCount;
	private int vId;
	private int tId;
	private int iId;
	
	public Model(float[] vertices, float[] textureCoords, int[] indexes) {
		
		//Divides indices matrix into vertices relations
		drawCount = indexes.length;		
		//Create vetex buffer name
		vId =glGenBuffers();
		//Bind vertex buffer name to GPU buffers
		glBindBuffer(GL_ARRAY_BUFFER, vId);
		//Data to GPU Buffer
		glBufferData(GL_ARRAY_BUFFER, createFloatBuffer(vertices), GL_STATIC_DRAW);
		
		//Create texture buffer name
		tId = glGenBuffers();
		//Bind buffer namer to GPU Buffer
		glBindBuffer(GL_ARRAY_BUFFER, tId);
		glBufferData(GL_ARRAY_BUFFER, createFloatBuffer(textureCoords), GL_STATIC_DRAW);
		
		//Create texture buffer name
		iId = glGenBuffers();
		//Bind buffer namer to GPU Buffer
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, iId);
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, createIntBuffer(indexes), GL_STATIC_DRAW);
		
		glBindBuffer(GL_ARRAY_BUFFER, 0);
	}
	
	public void draw() {
		glEnableVertexAttribArray(0);
		glEnableVertexAttribArray(1);
		//specifies the location and data format of an array of vertex coordinates to use when rendering
		glBindBuffer(GL_ARRAY_BUFFER, vId);
		glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
		
		glBindBuffer(GL_ARRAY_BUFFER, tId);
		//Coordiante data format for rendering

		glVertexAttribPointer(1, 2, GL_FLOAT, false, 0, 0);
		
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, iId);
		
		//Draw primitives with indexes
		glDrawElements(GL_TRIANGLES, drawCount, GL_UNSIGNED_INT, 0);
		
		//Unbind buffers
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
		glBindBuffer(GL_ARRAY_BUFFER, 0);
		
		glDisableVertexAttribArray(0);
		glDisableVertexAttribArray(1);
	}
	
	private FloatBuffer createFloatBuffer(float[] data) {
		FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
		buffer.put(data);
		buffer.flip();
		return buffer;
	}
	
	private IntBuffer createIntBuffer(int[] data) {
		IntBuffer buffer = BufferUtils.createIntBuffer(data.length);
		buffer.put(data);
		buffer.flip();
		return buffer;
	}
}
