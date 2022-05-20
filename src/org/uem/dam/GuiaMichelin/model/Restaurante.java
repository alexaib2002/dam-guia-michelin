package org.uem.dam.GuiaMichelin.model;

public record Restaurante(
		int id,
		int distincion,
		float precioMin,
		float precioMax,
		String nombre,
		String region,
		String ciudad,
		String direccion,
		String cocina,
		String telefono,
		String web
		) {

}
