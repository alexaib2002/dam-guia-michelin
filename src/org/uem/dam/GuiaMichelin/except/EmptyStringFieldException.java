package org.uem.dam.GuiaMichelin.except;

public class EmptyStringFieldException extends Exception {

	private static final long serialVersionUID = 1L;
	private String emptyField;

	public EmptyStringFieldException(String emptyField) {
		this.emptyField = emptyField;
	}

	@Override
	public String getMessage() {
		return emptyField;
	}

}
