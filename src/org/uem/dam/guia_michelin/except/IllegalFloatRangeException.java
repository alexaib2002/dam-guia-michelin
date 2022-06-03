package org.uem.dam.guia_michelin.except;

public class IllegalFloatRangeException extends Exception {

	private static final long serialVersionUID = 1L;
	final float minVal;
	final float maxVal;

	public IllegalFloatRangeException(float minVal, float maxVal) {
		this.minVal = minVal;
		this.maxVal = maxVal;
	}

	public float getMinVal() {
		return minVal;
	}

	public float getMaxVal() {
		return maxVal;
	}
}
