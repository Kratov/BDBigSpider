import static org.lwjgl.opengl.GL20.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.FloatBuffer;

import org.joml.Matrix4f;
import org.lwjgl.BufferUtils;
public class Shader {
	
	private int program; // Name oof program
	private int vs; //Process every vertex
	private int fs; //Process colors, blur, etc
	public Shader(String fileName) {
		
		//Create program name 
		program = glCreateProgram();
		//Create shader and binds code
		vs = glCreateShader(GL_VERTEX_SHADER);
		glShaderSource(vs, readFile(fileName+".vs"));
		//Compile shader program and validates 
		glCompileShader(vs);
		if (glGetShaderi(vs, GL_COMPILE_STATUS) != 1) {
			System.err.println(glGetShaderInfoLog(vs));
			System.exit(1);
		}
		
		//Creates fragment shader and binds code
		fs = glCreateShader(GL_FRAGMENT_SHADER);
		glShaderSource(fs, readFile(fileName+".fs"));
		//Compile and validate 
		glCompileShader(fs);
		if (glGetShaderi(fs, GL_COMPILE_STATUS) != 1) {
			System.err.println(glGetShaderInfoLog(fs));
			System.exit(1);
		}
		
		//Attaches shaders to program
		glAttachShader(program, vs);
		glAttachShader(program, fs);
		
		//Binds attributes with code
		glBindAttribLocation(program, 0, "vertices");
		//Binds attributes with code
		glBindAttribLocation(program, 1, "textures");
		
		//Links program (Creates executable build)
		glLinkProgram(program);
		if (glGetProgrami(program, GL_LINK_STATUS) != 1) {
			System.err.println(glGetProgramInfoLog(program));
			System.exit(1);
		}
		
		//Validate program
		glValidateProgram(program);
		if (glGetProgrami(program, GL_VALIDATE_STATUS) != 1) {
			System.err.println(glGetProgramInfoLog(program));
			System.exit(1);
		}
	}
	
	public void setUniform(String name, int value) {
		int location = glGetUniformLocation(program, name);
		if (location != -1) {
			glUniform1i(location, value);
		}
	}
	
	public void setUniform(String name, Matrix4f value) {
		int location = glGetUniformLocation(program, name);
		FloatBuffer buffer = BufferUtils.createFloatBuffer(16);
		value.get(buffer);
		if (location != -1) {
			glUniformMatrix4fv(location, false, buffer);
		}
	}
	
	public void bind() {
		//Uses executable
		glUseProgram(program);
	}
	
	
	//Reads the files and store it in string
	private String readFile(String fileName) {
		StringBuilder string = new StringBuilder();
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader(new File(fileName)));
			String line;
			while((line = br.readLine())!=null) {
				string.append(line);
				string.append('\n');
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return string.toString();
	}
}
