package org.uem.dam.test;

import org.junit.BeforeClass;
import org.junit.jupiter.api.Test;
import org.uem.dam.guia_michelin.persist.DBPersistence;

class PersistenceTest {

	private static DBPersistence database;

	@BeforeClass
	static void initDatabase() {
		database = new DBPersistence();
	}

	@Test
	void testDBQuery() {

	}

	@Test
	void testDBUsuario() {

	}

	@Test
	void testDBPassword() {

	}

	@Test
	void testDBRestaurante() {

	}

	@Test
	void testDBLocalidad() {

	}

	@Test
	void testDBConnection() {

	}

}
