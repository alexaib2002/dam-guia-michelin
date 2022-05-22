package org.uem.dam.test;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.uem.dam.GuiaMichelin.utils.SQLQueryBuilder;

class InsertQueryTest {

	@Test
	void insertRestauranteCreationTest() {
		final String expectedVal = "INSERT INTO RESTAURANTES ('NOMBRE', 'REGION', 'CIUDAD', 'DISTINCION', 'DIRECCION', 'PRECIO_MIN', 'PRECIO_MAX', 'COCINA', 'TELEFONO', 'WEB') VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
		assertEquals(expectedVal, SQLQueryBuilder.buildInsertQuery("RESTAURANTES", new String[] { "NOMBRE", "REGION",
				"CIUDAD", "DISTINCION", "DIRECCION", "PRECIO_MIN", "PRECIO_MAX", "COCINA", "TELEFONO", "WEB" }));
	}

	@Test
	void standardQueryCreationTest() {
		final String expectedVal = "INSERT INTO TESTING ('HELLO', 'WORLD') VALUES (?, ?);";
		String query = SQLQueryBuilder.buildInsertQuery("TESTING", new String[] { "HELLO", "WORLD" });
		assertEquals(expectedVal, query);
	}

	@Test
	void oneColumnQueryCreationTest() {
		final String expectedVal = "INSERT INTO ONE_COL_TABLE ('ONE') VALUES (?);";
		String query = SQLQueryBuilder.buildInsertQuery("ONE_COL_TABLE", new String[] { "ONE" });
		assertEquals(expectedVal, query);
	}

	@Test
	void emptyTableCreationTest() {
		assertThrows(IllegalArgumentException.class, () -> {
			SQLQueryBuilder.buildInsertQuery("", new String[] { "NAME", "AGE" });
		});
	}

	@Test
	void emptyColumnCreationTest() {
		assertThrows(IllegalArgumentException.class, () -> {
			SQLQueryBuilder.buildInsertQuery("EMPTY_TABLE", new String[] {});
		});
	}

	@Test
	void bothArgumentsEmptiedCreationTest() {
		assertThrows(IllegalArgumentException.class, () -> {
			SQLQueryBuilder.buildInsertQuery("", new String[] {});
		});
	}

	@Test
	void nulledArgumentsCreationTest() {
		assertThrows(NullPointerException.class, () -> {
			SQLQueryBuilder.buildInsertQuery(null, null);
		});
	}

}