package org.uem.dam.GuiaMichelin.except;

public class EmptyStringFiledException extends Exception {

	private String emptyField;

	public EmptyStringFiledException(String emptyField) {
		this.emptyField = emptyField;
	}

	@Override
	public String getMessage() {
		return emptyField;
	}

}
