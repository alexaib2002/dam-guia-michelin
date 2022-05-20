package org.uem.dam.GuiaMichelin.utils;

public final class Utils {

	public static void parseDistinIntToString(Object[] restauranteProps, int distinIdx) {
		int distinVal = Integer.parseInt((String) restauranteProps[distinIdx]);
		restauranteProps[distinIdx] = "";
		for (int i = 0; i < distinVal; i++) {
			restauranteProps[distinIdx] += "*";
		}
	}

}
