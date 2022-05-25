package org.uem.dam.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.uem.dam.GuiaMichelin.contract.TableContract;
import org.uem.dam.GuiaMichelin.utils.SQLQueryBuilder;

class SelectQueryTestUnit extends SQLQueryBuilder {

	String expectedQuery = "SELECT * FROM RESTAURANTES;";

	@Test
	void selectAllColumnsRestauranteNulledWhereArray() {
		assertEquals(expectedQuery,
				buildSelectQuery(TableContract.RESTAURANTES.toString(), new String[] { "*" }, null, null, false));
	}

	@Test
	void selectAllColumnsRestauranteWithWhereArray() {
		assertEquals(expectedQuery, buildSelectQuery(TableContract.RESTAURANTES.toString(), new String[] { "*" },
				new String[] { "" }, null, false));
	}

	@Test
	void selectColumnsTest() {
		assertEquals("SELECT NOMBRE, DISTINCION FROM RESTAURANTES;", buildSelectQuery(
				TableContract.RESTAURANTES.toString(), new String[] { "NOMBRE", "DISTINCION" }, null, null, false));
	}

	@Test
	void whereStatementTesting() {
		assertEquals("SELECT NOMBRE, DISTINCION FROM RESTAURANTES WHERE NOMBRE LIKE '%test%';",
				buildSelectQuery(TableContract.RESTAURANTES.toString(), new String[] { "NOMBRE", "DISTINCION" },
						new String[] { "NOMBRE LIKE '%test%'" }, null, false));
	}

	@Test
	void whereMultipleStatementTesting() {
		assertEquals("SELECT NOMBRE, DISTINCION FROM RESTAURANTES WHERE NOMBRE LIKE '%test%' AND DISTINCION = 3;",
				buildSelectQuery(TableContract.RESTAURANTES.toString(), new String[] { "NOMBRE", "DISTINCION" },
						new String[] { "NOMBRE LIKE '%test%'", "DISTINCION = 3" }, null, false));
	}

	@Test
	void orderByTest() {
		assertEquals("SELECT name FROM pragma_table_info('RESTAURANTES') ORDER BY cid;",
				SQLQueryBuilder.buildSelectQuery(
						String.format("pragma_table_info('%s')", TableContract.RESTAURANTES.toString()),
						new String[] { "name" }, null, "cid", false));
	}

}
