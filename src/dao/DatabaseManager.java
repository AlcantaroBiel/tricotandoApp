package tricotando;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class databaseManager {
	private static String url = "API_URL";
	private static String user = "DB_USERNAME";
	private static String password = "DB_PASSWORD";
	
	public static Connection connect() throws SQLException{
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch(ClassNotFoundException e){
			e.printStackTrace();
			throw new SQLException("Driver JDBC not found");
		}
		return DriverManager.getConnection(url, user, password);
	}
	

}
