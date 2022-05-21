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
			String query = String.format("SELECT * FROM %s;", "RESTAURANTES" // FIXME contract
			);
			rset = stmt.executeQuery(query);

			while (rset.next()) {
				// parse values that can be nulled
				float precioMin = rset.getString("PRECIO_MIN") != null ? Float.parseFloat(rset.getString("PRECIO_MIN"))
						: 0f;
				float precioMax = rset.getString("PRECIO_MAX") != null ? Float.parseFloat(rset.getString("PRECIO_MAX"))
						: 0f;
				String direccion = rset.getString("DIRECCION") != null ? rset.getString("DIRECCION") : "";
				String cocina = rset.getString("COCINA") != null ? rset.getString("COCINA") : "";
				String telefono = rset.getString("TELEFONO") != null ? rset.getString("TELEFONO") : "";
				String web = rset.getString("WEB") != null ? rset.getString("WEB") : "";

				restaurantes.add(new Restaurante( // FIXME
						Integer.parseInt(rset.getString("ID")), Integer.parseInt(rset.getString("DISTINCION")),
						precioMin, precioMax, rset.getString("NOMBRE"), rset.getString("REGION"),
						rset.getString("CIUDAD"), direccion, cocina, telefono, web));
			}
		} catch (SQLException e) {
			System.out.println("Error en codigo SQL");
		} catch (NullPointerException e) {
			System.out.println("bruh");
		} finally {
			try {
				if (rset != null)
					rset.close();
				if (stmt != null)
					stmt.close();
				if (con != null)
					con.close();
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
		String query = "INSERT INTO RESTAURANTES ('NOMBRE', 'REGION', 'CIUDAD', 'DISTINCION', 'DIRECCION', 'PRECIO_MIN', 'PRECIO_MAX', 'COCINA', 'TELEFONO', 'WEB') VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
		try {
			con = getConnection();
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, restaurante.nombre());
			pstmt.setString(2, restaurante.region());
			pstmt.setString(3, restaurante.ciudad());
			pstmt.setInt(4, restaurante.distincion());
			pstmt.setString(5, restaurante.direccion());
			pstmt.setFloat(6, restaurante.precioMin());
			pstmt.setFloat(7, restaurante.precioMax());
			pstmt.setString(8, restaurante.cocina());
			pstmt.setString(9, restaurante.telefono());
			pstmt.setString(10, restaurante.web());
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("Error en codigo SQL");
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (con != null)
					con.close();
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
			String query = String.format("SELECT DISTINCT REGION FROM RESTAURANTES;" // FIXME hardcode
			);
			rset = stmt.executeQuery(query);

			while (rset.next()) {
				regions.add(rset.getString("REGION")); // FIXME hardcode
			}
		} catch (SQLException e) {
			System.out.println("Error en codigo SQL");
		} finally {
			try {
				if (rset != null)
					rset.close();
				if (stmt != null)
					stmt.close();
				if (con != null)
					con.close();
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
			String query = String.format("SELECT name FROM pragma_table_info('%s') ORDER BY cid;", // FIXME hardcode
					"RESTAURANTES");
			rset = stmt.executeQuery(query);

			while (rset.next()) {
				columns.add(rset.getString("name")); // FIXME hardcode
			}
		} catch (SQLException e) {
			System.err.println(String.format("Error en codigo SQL: \n %s", e));
		} finally {
			try {
				if (rset != null)
					rset.close();
				if (stmt != null)
					stmt.close();
				if (con != null)
					con.close();
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
