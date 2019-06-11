// GLFW: is an Open Source, multi-platform library for OpenGL, 
		 //OpenGL ES and Vulkan development on the desktop. 
		 //It provides a simple API for creating windows, contexts and surfaces, 
		 //receiving input and events.
/**
 * @author Lina Maria Gonzalez Silva,
 * @author Christian Felipe Rodriguez Valencia,
 * @author Jaime Enrique Zamora Munar (Kratov)
 */

import static org.lwjgl.glfw.GLFW.*; //Import GLFW
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glEnable;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;

import static org.lwjgl.opengl.GL11.*;

import javax.swing.text.html.HTMLDocument.HTMLReader.IsindexAction;

import org.joml.Vector2f;
import org.lwjgl.opengl.GL;

import Collision.AABB;
import Databse.OracleConn;
import Entity.Entity;
import Game.Game;
import Graphics.Window;
import Helpers.Timer;
import Swing.FrInsertarUsuario;


/**
 * 
 * @author Kratov
 * Clase principal (Punto de entrada app)
 */
public class Main {
	
	
	//Public constants
	/**
	 * Constantes de app
	 * Tama�o de ventana y titulo de ventana
	 */
	public static final int WND_HEIGHT = 480;
	public static final int WND_WIDTH = 640;
	public static final String WND_TITLE = "BIG Spider";
	
	/**
	 * Metodo de entrada MAIN
	 */
	
	public Main() {
		
		OracleConn.connectOracle();
		
		try {
			if (OracleConn.connection.isClosed()) {
				throw new SQLException("Not connected");
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		FrInsertarUsuario frInsertarUsuario = new FrInsertarUsuario("Big Spider -  Registrar jugadores");
		frInsertarUsuario.getBtnIniciar().addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				double beginTime = Timer.getTime();
				Window.setCallbacks();
				if (!glfwInit())   // Inicializa  librerias GLFW
					throw new IllegalStateException("Failed to initialize GLFW");
				Window wnd = new Window(WND_WIDTH, WND_HEIGHT, WND_TITLE);
				wnd.createWindow();
				wnd.show();				
				GL.createCapabilities(); // Inits OPENGL
				glEnable(GL_BLEND);
				glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
				
				glEnable(GL_TEXTURE_2D); // Activa las texturas en 2D
				Game game = new Game(wnd);
				while (!glfwWindowShouldClose(wnd.getHandler())) {  // Ciclo de mensajes de Windows https://en.wikipedia.org/wiki/Message_loop_in_Microsoft_Windows
					game.go();
				}
				double endTime = Timer.getTime();
				frInsertarUsuario.setElapsedGameSeconds(endTime - beginTime);
				frInsertarUsuario.getLabTiempoJuego().setText(frInsertarUsuario.getElapsedGameSeconds()+" s");
				OracleConn.CloseConnection();
				Entity.deleteAsset();
				glfwTerminate(); //Cierra GLFW
			}
		});;
		frInsertarUsuario.setVisible(true);
	}
	
	public static void main(String[] args) {
		new Main();	
	}
}
