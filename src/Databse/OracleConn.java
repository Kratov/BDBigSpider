package Databse;

import java.sql.*;  
class OracleCon{
	
	private Connection con;
	private Statement stmt; 
	
	public OracleCon() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con=DriverManager.getConnection(  
					   "jdbc:oracle:thin:@localhost:1521:xe","system","oracle");
			stmt=con.createStatement();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}
	
	public ResultSet executeQuery(String sql) {
		try {
			return stmt.executeQuery(sql);
		} catch (SQLException e) {
			return null;
		}
	}
 
	public Connection getCon() {
		return con;
	}
	
	public Statement getStmt() {
		return stmt;
	}
}