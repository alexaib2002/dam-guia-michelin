package org.uem.dam.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.uem.dam.guia_michelin.contract.RestauranteContract;
import org.uem.dam.guia_michelin.contract.TableContract;
import org.uem.dam.guia_michelin.utils.SQLQueryBuilder;

class UpdateQueryTestUnit extends SQLQueryBuilder {

	@Test
	public void updateRestauranteQueryTest() {
		String expectedValue = "UPDATE RESTAURANTES SET NOMBRE = ? WHERE ID = ?;";
		assertEquals(expectedValue, buildUpdateQuery("RESTAURANTES", new String[] { "NOMBRE" }));
	}

	@Test
	public void updateRestauranteEnumQueryTest() {
		String expectedValue = "UPDATE RESTAURANTES SET NOMBRE = ?, DIRECCION = ? WHERE ID = ?;";
		assertEquals(expectedValue, buildUpdateQuery(TableContract.RESTAURANTES.toString(),
				new String[] { RestauranteContract.NOMBRE.toString(), RestauranteContract.DIRECCION.toString() }));
	}

}
