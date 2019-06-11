package Databse;

import java.sql.*;  
public class OracleConn{
	
	public static Connection connection;
	public static Statement stmt; 
	
	public static void connectOracle() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			connection=DriverManager.getConnection(  
					   "jdbc:oracle:thin:@localhost:1521:xe","kratov","Jmjm12jmjm12");
			stmt=connection.createStatement();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static ResultSet executeQuery(String sql) {
		try {
			return stmt.executeQuery(sql);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static void CloseConnection() {
		try {
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}