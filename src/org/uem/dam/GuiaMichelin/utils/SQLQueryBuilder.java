package org.uem.dam.GuiaMichelin.utils;

public final class SQLQueryBuilder {

	public static String selectQuery(String[] select, String[] tables, String[] where) {
		String query = "";
		return query;
	}

	public static String buildInsertQuery(String table, String[] cols) {

		if (table.isEmpty() || table.isBlank() || cols.length <= 0) {
			throw new IllegalArgumentException();
		}

		// initial statements
		String query = String.format("INSERT INTO %s ", table);

		// append columns
		int c = 0; // counter
		for (String col : cols) {
			if (c == 0) {
				query += "(";
			}

			query += String.format("\'%s\'", col);

			if (c == cols.length - 1) {
				query += ")";
			} else {
				query += ", ";
			}

			c++;
		}

		query += " VALUES ";

		for (int i = 0; i < cols.length; i++) {
			if (i == 0) {
				query += "(";
			}

			query += "?";

			if (i == cols.length - 1) {
				query += ")";
			} else {
				query += ", ";
			}
		}

		query += ";";

		return query;
	}

}
