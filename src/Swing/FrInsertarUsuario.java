package Swing;

import java.awt.FlowLayout;
import java.awt.ScrollPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.ResultSet;
import java.sql.SQLException;
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
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListModel;

import Databse.OracleConn;

public class FrInsertarUsuario extends JFrame {

	/**
	 * 
	 */
	
	private JPanel panel;
	private JLabel label;
	
	private JLabel labNombreJugador; 
	private JTextField ediNombreJugador;
	
	private JLabel labCedulaJugador; 
	private JTextField ediCedulaJugador;
	
	private JLabel labTiempoJuego; 
	private JButton btnCerrar;
	private JButton btnIniciar;
	private JButton btnAgregarJugador;
	private JList lstJugadores;
	
	private double elapsedGameSeconds; 
	
	private static final long serialVersionUID = 1L;
	
	public FrInsertarUsuario(String title) {
		super( title );                      // invoke the JFrame constructor
	    setSize( 300, 500);
	    setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
	    
	    setLayout( new FlowLayout() );       // set the layout manager
	    label = new JLabel("Big Spider");  // construct a JLabel
	    labNombreJugador = new JLabel("Nombre jugador");
	    labTiempoJuego = new JLabel("0"+" s");
	    ediNombreJugador = new JTextField(20);
	    labCedulaJugador = new JLabel("Cedula jugador");
	    ediCedulaJugador = new JTextField(20);
	    btnCerrar = new JButton("Cerrar");
	    btnCerrar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				
			}
		});
	    btnIniciar = new JButton("Iniciar");
	    btnAgregarJugador = new JButton("Agregar Jugador");
	    btnAgregarJugador.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
//				OracleConn.stmt.executeUpdate();
			}
		});

	    Vector<String> temp = new Vector<String>();
	    ResultSet r = OracleConn.executeQuery("SELECT * FROM PLAYER");
	    
	    try {
			while (r.next()) {
				temp.add(r.getString("nombre"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	    
	    lstJugadores = new JList(temp); 
	    
//	    add( label );
	    
	    add(labCedulaJugador);
	    add(ediCedulaJugador);
	    
	    add(labNombreJugador);
	    add(ediNombreJugador);
	    
	    add(btnAgregarJugador);
	    
	    add(new JScrollPane(lstJugadores));
	    
	    add(btnIniciar);
	    add(btnCerrar);
	    
	    add(labTiempoJuego);
	}
	
	public JButton getBtnIniciar() {
		return btnIniciar;
	}
	
	public JButton getBtnAgregarJugador() {
		return btnAgregarJugador;
	}
	
	public double getElapsedGameSeconds() {
		return elapsedGameSeconds;
	}
	
	public void setElapsedGameSeconds(double elapsedGameSeconds) {
		this.elapsedGameSeconds = elapsedGameSeconds;
	}
	
	public JLabel getLabTiempoJuego() {
		return labTiempoJuego;
	}
}

