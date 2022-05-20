package org.uem.dam.GuiaMichelin.persist;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.uem.dam.GuiaMichelin.model.Restaurante;

public class DBPersistence {


	private DBConnection dbConnection;

	public DBPersistence() {
		dbConnection = new DBConnection();
	}

	public ArrayList<Restaurante> getRestaurantes() {
		ResultSet rset = null;
		Connection con = null;
		Statement stmt = null;
		ArrayList<Restaurante> restaurantes = new ArrayList<Restaurante>();
		try {
			con = getConnection();
			stmt = con.createStatement();
			String query = String.format(
					"SELECT * FROM %s;",
					"RESTAURANTES" // FIXME contract
					);
			rset = stmt.executeQuery(query);

			while (rset.next()) {
				restaurantes.add(new Restaurante( // FIXME
						Integer.parseInt(rset.getString("ID")),
						Integer.parseInt(rset.getString("DISTINCION")),
						Integer.parseInt(rset.getString("PRECIO_MIN")),
						Integer.parseInt(rset.getString("PRECIO_MAX")),
						rset.getString("NOMBRE"),
						rset.getString("REGION"),
						rset.getString("CIUDAD"),
						rset.getString("DIRECCION"),
						rset.getString("COCINA"),
						rset.getString("TELEFONO"),
						rset.getString("WEB")
						));
			}
		} catch (SQLException e) {
			System.out.println("Error en codigo SQL");
		} finally {
			try {
				if (rset != null) rset.close();
				if (stmt != null) stmt.close();
				if (con != null) con.close();
			} catch (SQLException e) {
				System.out.println("Excepcion en codigo SQL");
			}
		}
		return restaurantes;
	}

	public int addRestaurante(Restaurante restaurante) {
		int result = 0;
		Connection con = null;
		PreparedStatement pstmt = null;
		String query = String.format(
				"INSERT INTO %s VALUES ('%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s');",
				"RESTAURANTES",
				restaurante.id(),
				restaurante.nombre(),
				restaurante.region(),
				restaurante.ciudad(),
				restaurante.distincion(),
				restaurante.direccion(),
				restaurante.precioMin(),
				restaurante.precioMax(),
				restaurante.cocina(),
				restaurante.telefono(),
				restaurante.web()
				);
		try {
			con = getConnection();
			pstmt = con.prepareStatement(query);
			System.out.println(String.format("Ejecutando:\n %s", query));
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("Error en codigo SQL");
		} finally {
			try {
				if (pstmt != null) pstmt.close();
				if (con != null) con.close();
				System.out.println("Conexion a BBDD cerrada con exito");
			} catch (SQLException e) {
				System.out.println("Error durante cierre de conexion a BBDD");
			}
		}
		return result;
	}

	public ArrayList<String> getAvailableRegions() {
		ResultSet rset = null;
		Connection con = null;
		Statement stmt = null;
		ArrayList<String> regions = new ArrayList<>();
		try { // FIXME code is repeated, perhaps implementation as lambda would help
			con = getConnection();
			stmt = con.createStatement();
			String query = String.format(
					"SELECT DISTINCT REGION FROM RESTAURANTES;" // FIXME hardcode
					);
			rset = stmt.executeQuery(query);

			while (rset.next()) {
				regions.add(rset.getString("REGION")); // FIXME hardcode
			}
		} catch (SQLException e) {
			System.out.println("Error en codigo SQL");
		} finally {
			try {
				if (rset != null) rset.close();
				if (stmt != null) stmt.close();
				if (con != null) con.close();
			} catch (SQLException e) {
				System.out.println("Excepcion en codigo SQL");
			}
		}
		return regions;
	}

	public ArrayList<String> getAvailableColumns() {
		ResultSet rset = null;
		Connection con = null;
		Statement stmt = null;
		ArrayList<String> columns = new ArrayList<>();
		try { // FIXME code is repeated, perhaps implementation as lambda would help
			con = getConnection();
			stmt = con.createStatement();
			String query = String.format(
					"SELECT name FROM pragma_table_info('%s') ORDER BY cid;", // FIXME hardcode
					"RESTAURANTES"
					);
			rset = stmt.executeQuery(query);

			while (rset.next()) {
				columns.add(rset.getString("name")); // FIXME hardcode
			}
		} catch (SQLException e) {
			System.err.println(String.format(
					"Error en codigo SQL: \n %s",
					e));
		} finally {
			try {
				if (rset != null) rset.close();
				if (stmt != null) stmt.close();
				if (con != null) con.close();
			} catch (SQLException e) {
				System.out.println("Excepcion en codigo SQL");
			}
		}
		return columns;
	}

	private Connection getConnection() {
		Connection con = null;
		try {
			con = dbConnection.getConnection();
			System.out.println("Conexion establecida correctamente");
		} catch (SQLException e) {
			System.err.println("Excepcion SQL durante conexion");
		} catch (ClassNotFoundException e) {
			System.err.println("Driver no se ha cargado correctamente, la clase no fue encontrada");
		}
		return con;
	}

}
