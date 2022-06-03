package org.uem.dam.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.uem.dam.guia_michelin.contract.RestauranteContract;
import org.uem.dam.guia_michelin.contract.TableContract;
import org.uem.dam.guia_michelin.utils.SQLQueryBuilder;

class DeleteQueryTestUnit extends SQLQueryBuilder {

	@Test
	public void deleteRestauranteQueryTest() {
		String expectedValue = "DELETE FROM RESTAURANTES WHERE ID = ?;";
		assertEquals(expectedValue, buildDeleteQuery("RESTAURANTES", "ID"));
	}

	@Test
	public void deleteRestauranteEnumQueryTest() {
		String expectedValue = "DELETE FROM RESTAURANTES WHERE ID = ?;";
		assertEquals(expectedValue,
				buildDeleteQuery(TableContract.RESTAURANTES.toString(), RestauranteContract.ID.toString()));
	}

	@Test
	public void nulledArgumentsBuildTest() {
		assertThrows(NullPointerException.class, () -> {
			buildDeleteQuery(null, null);
		});
	}

	@Test
	public void emptyArgumentBuildTest() {
		assertThrows(IllegalArgumentException.class, () -> {
			buildDeleteQuery("", "");
		});
	}

	@Test
	public void emptyTableBuildTest() {
		assertThrows(IllegalArgumentException.class, () -> {
			buildDeleteQuery("", RestauranteContract.ID.toString());
		});
	}

	@Test
	public void emptyConditionBuildTest() {
		assertThrows(IllegalArgumentException.class, () -> {
			buildDeleteQuery(TableContract.RESTAURANTES.toString(), "");
		});
	}

}
