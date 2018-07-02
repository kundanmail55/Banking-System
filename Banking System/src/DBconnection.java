import java.sql.*;

public class DBconnection {

	private static Connection con;
	static {

			try {
				Class.forName("com.mysql.jdbc.Driver");
				con=DriverManager.getConnection("jdbc:mysql://localhost:3306/login","root","root");
				}
			catch(Exception e) {
				e.printStackTrace();
			}
	}
			public static Connection getConnection() {
				return con;
			}

}

