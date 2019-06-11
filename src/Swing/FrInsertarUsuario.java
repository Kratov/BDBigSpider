package Swing;

import java.awt.FlowLayout;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.time.temporal.JulianFields;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.ListModel;

public class FrInsertarUsuario extends JFrame {

	/**
	 * 
	 */
	
	private JPanel panel;
	private JLabel label;
	
	private JLabel labNombreJugador; 
	private JTextField ediNombreJugador;
	
	private JButton btnCerrar;
	private JButton btnIniciar;
	private JButton btnAgregarJugador;
	private JList lstJugadores;
		
	private static final long serialVersionUID = 1L;
	
	public FrInsertarUsuario(String title) {
		super( title );                      // invoke the JFrame constructor
	    setSize( 300, 300);
	    setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
	    
	    setLayout( new FlowLayout() );       // set the layout manager
	    label = new JLabel("Big Spider");  // construct a JLabel
	    labNombreJugador = new JLabel("Nombre jugador");
	    ediNombreJugador = new JTextField(20);
	    btnCerrar = new JButton("Cerrar");
	    btnIniciar = new JButton("Iniciar");
	    btnAgregarJugador = new JButton("Agregar Jugador");
	    
	    Vector<String> temp = new Vector<String>();
	    
	    
	    lstJugadores = new JList<Map<Integer,String>>(); 
	    
	    add( label );
	    add(labNombreJugador);
	    add(ediNombreJugador);
	    
	    add(btnAgregarJugador);
	    
	    add(lstJugadores);
	    
	    add(btnIniciar);
	    add(btnCerrar);
	}
	
	public JButton getBtnIniciar() {
		return btnIniciar;
	}
}
