package org.uem.dam.test;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.uem.dam.GuiaMichelin.contract.RestauranteContract;
import org.uem.dam.GuiaMichelin.contract.TableContract;
import org.uem.dam.GuiaMichelin.utils.SQLQueryBuilder;

class InsertQueryTestUnit extends SQLQueryBuilder {

	final String restauranteExpectedInsertQuery = "INSERT INTO RESTAURANTES ('NOMBRE', 'REGION', 'CIUDAD', 'DISTINCION', 'DIRECCION', 'PRECIO_MIN', 'PRECIO_MAX', 'COCINA', 'TELEFONO', 'WEB') VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";

	@Test
	void insertRestauranteCreationTest() {
		assertEquals(restauranteExpectedInsertQuery, buildInsertQuery("RESTAURANTES", new String[] { "NOMBRE", "REGION",
				"CIUDAD", "DISTINCION", "DIRECCION", "PRECIO_MIN", "PRECIO_MAX", "COCINA", "TELEFONO", "WEB" }));
	}

	@Test
	void insertRestauranteEnumTest() {
		assertEquals(restauranteExpectedInsertQuery,
				buildInsertQuery(TableContract.RESTAURANTES.toString(), RestauranteContract.getAllAttributes()));
	}

	@Test
	void standardQueryCreationTest() {
		final String expectedVal = "INSERT INTO TESTING ('HELLO', 'WORLD') VALUES (?, ?);";
		String query = buildInsertQuery("TESTING", new String[] { "HELLO", "WORLD" });
		assertEquals(expectedVal, query);
	}

	@Test
	void oneColumnQueryCreationTest() {
		final String expectedVal = "INSERT INTO ONE_COL_TABLE ('ONE') VALUES (?);";
		String query = buildInsertQuery("ONE_COL_TABLE", new String[] { "ONE" });
		assertEquals(expectedVal, query);
	}

	@Test
	void emptyTableCreationTest() {
		assertThrows(IllegalArgumentException.class, () -> {
			buildInsertQuery("", new String[] { "NAME", "AGE" });
		});
	}

	@Test
	void emptyColumnCreationTest() {
		assertThrows(IllegalArgumentException.class, () -> {
			buildInsertQuery("EMPTY_TABLE", new String[] {});
		});
	}

	@Test
	void bothArgumentsEmptiedCreationTest() {
		assertThrows(IllegalArgumentException.class, () -> {
			buildInsertQuery("", new String[] {});
		});
	}

	@Test
	void nulledArgumentsCreationTest() {
		assertThrows(NullPointerException.class, () -> {
			buildInsertQuery(null, null);
		});
	}

}
