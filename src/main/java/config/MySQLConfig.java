package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLConfig {

	public static Connection getConnection() {
		Connection connection = null;
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3307/crm_app", "root", "admin123");
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("Error!");
			e.printStackTrace();
		}
		
		return connection;
	}	
}
