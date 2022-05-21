package org.uem.dam.GuiaMichelin.except;

public class IllegalIntegerRangeException extends Exception {

	final int minVal;
	final int maxVal;

	public IllegalIntegerRangeException(int minVal, int maxVal) {
		this.minVal = minVal;
		this.maxVal = maxVal;
	}

	public int getMinVal() {
		return minVal;
	}

	public int getMaxVal() {
		return maxVal;
	}
}