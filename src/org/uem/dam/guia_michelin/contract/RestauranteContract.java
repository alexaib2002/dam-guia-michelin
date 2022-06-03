package org.uem.dam.guia_michelin.contract;

public enum RestauranteContract {
	ID, NOMBRE, REGION, CIUDAD, DISTINCION, DIRECCION, PRECIO_MIN, PRECIO_MAX, COCINA, TELEFONO, WEB;

	public static String[] getAllAttributes() { // return everything except id
		RestauranteContract[] contractValues = RestauranteContract.values();
		String[] attributes = new String[contractValues.length - 1]; // ignore id index

		for (int i = 1; i < contractValues.length; i++) {
			attributes[i - 1] = contractValues[i].toString();
		}

		return attributes;

	}
}