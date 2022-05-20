package org.uem.dam.GuiaMichelin.model;

import java.util.ArrayList;

public record Restaurante(int id, int distincion, float precioMin, float precioMax, String nombre, String region,
		String ciudad, String direccion, String cocina, String telefono, String web) {

	public enum PROPS {
		ID, NOMBRE, REGION, CIUDAD, DISTINCION, DIRECCION, PRECIO_MIN, PRECIO_MAX, COCINA, TELEFONO, WEB
	}

	public ArrayList<String> translateToStringArray() {
		ArrayList<String> values = new ArrayList<>();
		values.add(Integer.toString(id));
		values.add(nombre);
		values.add(region);
		values.add(ciudad);
		values.add(Integer.toString(distincion));
		values.add(direccion);
		values.add(Float.toString(precioMin));
		values.add(Float.toString(precioMax));
		values.add(cocina);
		values.add(telefono);
		values.add(web);
		return values;
	}

}
