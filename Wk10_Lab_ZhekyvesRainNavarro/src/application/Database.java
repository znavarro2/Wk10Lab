package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {

	private static final String DB_DRIVER = "com.mysql.cj.jdbc.Driver";
	private static final String DB_CONNECTION = "jdbc:mysql://localhost/wk10_lab";
	private static final String DB_USER = "root";
	private static final String DB_PASSWORD = "Passwordformysql!1";

	private Database() {
	}

	public static Connection getDBConnection() {
		Connection connection = null;

		try {
			Class.forName(DB_DRIVER);
		} catch (ClassNotFoundException exception) {
			exception.printStackTrace();
		}

		try {
			connection = DriverManager.getConnection(DB_CONNECTION, DB_USER, DB_PASSWORD);
			return connection;
		} catch (SQLException exception) {
			exception.printStackTrace();
		}

		return connection;
	}
}