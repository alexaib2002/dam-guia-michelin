package org.uem.dam.GuiaMichelin.persist;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnection {

	private String driver;
	private String url;

	public DBConnection() {
		FileInputStream fileInputStream;
		try {
			fileInputStream = new FileInputStream("dbconfig.properties");
			Properties dbProperties = new Properties();
			dbProperties.load(fileInputStream);
			driver = dbProperties.getProperty("driver");
			url = dbProperties.getProperty("url");
		} catch (FileNotFoundException e) {
			System.err.println("Database connection file not found");
			System.exit(1);
		} catch (IOException e) {
			System.err.println("Error while reading the file");
			System.exit(2);
		}
	}

	public Connection getConnection() throws SQLException, ClassNotFoundException {
		Class.forName(driver);
		return DriverManager.getConnection(url);
	}

}
