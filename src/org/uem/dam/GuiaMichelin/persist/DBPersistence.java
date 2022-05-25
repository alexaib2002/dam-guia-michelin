package org.uem.dam.GuiaMichelin.persist;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.uem.dam.GuiaMichelin.contract.RestauranteContract;
import org.uem.dam.GuiaMichelin.contract.TableContract;
import org.uem.dam.GuiaMichelin.inter.UpdateExpression;
import org.uem.dam.GuiaMichelin.model.Restaurante;
import org.uem.dam.GuiaMichelin.utils.SQLQueryBuilder;

public class DBPersistence {

	private DBConnection dbConnection;

	public DBPersistence() {
		dbConnection = new DBConnection();
	}

	// TODO implement DataRetrivalExpression

	public ArrayList<Restaurante> getRestaurantes() {
		ResultSet rset = null;
		Connection con = null;
		Statement stmt = null;
		ArrayList<Restaurante> restaurantes = new ArrayList<Restaurante>();
		try {
			con = getConnection();
			stmt = con.createStatement();
			String query = SQLQueryBuilder.buildSelectQuery(TableContract.RESTAURANTES.toString(), new String[] { "*" },
					null, null, false);
			rset = stmt.executeQuery(query);

			while (rset.next()) {
				// parse values that can be nulled
				float precioMin = rset.getString(RestauranteContract.PRECIO_MIN.toString()) != null
						? Float.parseFloat(rset.getString(RestauranteContract.PRECIO_MIN.toString()))
						: 0f;
				float precioMax = rset.getString(RestauranteContract.PRECIO_MAX.toString()) != null
						? Float.parseFloat(rset.getString(RestauranteContract.PRECIO_MAX.toString()))
						: 0f;
				String direccion = rset.getString(RestauranteContract.DIRECCION.toString()) != null
						? RestauranteContract.DIRECCION.toString()
						: "";
				String cocina = RestauranteContract.COCINA.toString() != null ? RestauranteContract.COCINA.toString()
						: "";
				String telefono = RestauranteContract.TELEFONO.toString() != null
						? RestauranteContract.TELEFONO.toString()
						: "";
				String web = RestauranteContract.WEB.toString() != null ? RestauranteContract.WEB.toString() : "";

				restaurantes.add(new Restaurante( // FIXME
						rset.getInt(RestauranteContract.ID.toString()),
						rset.getInt(RestauranteContract.DISTINCION.toString()), precioMin, precioMax,
						RestauranteContract.NOMBRE.toString(), RestauranteContract.REGION.toString(),
						RestauranteContract.CIUDAD.toString(), direccion, cocina, telefono, web));
			}
		} catch (SQLException e) {
			System.out.println("Error en codigo SQL" + e);
		} catch (NullPointerException e) {
			System.err.println(e.getStackTrace());
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

	public int updateRestaurante(Restaurante restaurante, UpdateExpression expr) {
		int result = 0;
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = getConnection();
			pstmt = expr.executeUpdateSQL(con, pstmt);
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
			String query = SQLQueryBuilder.buildSelectQuery(TableContract.RESTAURANTES.toString(),
					new String[] { RestauranteContract.REGION.toString() }, null, null, true);
			rset = stmt.executeQuery(query);

			while (rset.next()) {
				regions.add(RestauranteContract.REGION.toString());
			}
		} catch (SQLException e) {
			System.out.println("Error en codigo SQL" + e);
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

	public boolean hasRestaurante(Restaurante restaurante) {
		boolean hasRestaurante = true;
		ResultSet result;
		Connection con = null;
		PreparedStatement pstmt = null;
		String query = SQLQueryBuilder.buildSelectQuery(TableContract.RESTAURANTES.toString(), new String[] { "ID" },
				new String[] { "NOMBRE = ?" }, null, false);
		try {
			con = getConnection();
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, restaurante.nombre());
			result = pstmt.executeQuery();
			hasRestaurante = result.next();
		} catch (SQLException e) {
			System.out.println("Error en codigo SQL" + e);
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
		return hasRestaurante;
	}

	public ArrayList<String> getAvailableColumns() {
		ResultSet rset = null;
		Connection con = null;
		Statement stmt = null;
		ArrayList<String> columns = new ArrayList<>();
		try { // FIXME code is repeated, perhaps implementation as lambda would help
			con = getConnection();
			stmt = con.createStatement();
			String query = SQLQueryBuilder.buildSelectQuery(
					String.format("pragma_table_info('%s')", TableContract.RESTAURANTES.toString()),
					new String[] { "name" }, null, "cid", false);
			rset = stmt.executeQuery(query);

			while (rset.next()) {
				columns.add(rset.getString("name"));
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
