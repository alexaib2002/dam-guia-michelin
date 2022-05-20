package org.uem.dam.GuiaMichelin.persist;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

	private String driver;
	private String url;

	public DBConnection() {
		driver = "org.sqlite.JDBC";
		url = "jdbc:sqlite:db/UserDatabase.db";
	}

	public Connection getConnection() throws SQLException, ClassNotFoundException {
		Class.forName(driver);
		return DriverManager.getConnection(url);
	}

}
