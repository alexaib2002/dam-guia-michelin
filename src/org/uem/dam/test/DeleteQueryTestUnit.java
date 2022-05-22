package org.uem.dam.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.uem.dam.GuiaMichelin.contract.RestauranteContract;
import org.uem.dam.GuiaMichelin.contract.TableContract;
import org.uem.dam.GuiaMichelin.utils.SQLQueryBuilder;

class DeleteQueryTestUnit {

	@Test
	public void deleteRestauranteQueryTest() {
		String expectedValue = "DELETE FROM RESTAURANTES WHERE ID = ?;";
		assertEquals(expectedValue, SQLQueryBuilder.buildDeleteQuery("RESTAURANTES", "ID"));
	}

	@Test
	public void deleteRestauranteEnumQueryTest() {
		String expectedValue = "DELETE FROM RESTAURANTES WHERE ID = ?;";
		assertEquals(expectedValue, SQLQueryBuilder.buildDeleteQuery(TableContract.RESTAURANTES.toString(),
				RestauranteContract.ID.toString()));
	}

	@Test
	public void nulledArgumentsBuildTest() {
		assertThrows(NullPointerException.class, () -> {
			SQLQueryBuilder.buildDeleteQuery(null, null);
		});
	}

	@Test
	public void emptyArgumentBuildTest() {
		assertThrows(IllegalArgumentException.class, () -> {
			SQLQueryBuilder.buildDeleteQuery("", "");
		});
	}

	@Test
	public void emptyTableBuildTest() {
		assertThrows(IllegalArgumentException.class, () -> {
			SQLQueryBuilder.buildDeleteQuery("", RestauranteContract.ID.toString());
		});
	}

	@Test
	public void emptyConditionBuildTest() {
		assertThrows(IllegalArgumentException.class, () -> {
			SQLQueryBuilder.buildDeleteQuery(TableContract.RESTAURANTES.toString(), "");
		});
	}

}
