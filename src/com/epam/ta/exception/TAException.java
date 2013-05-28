package com.epam.ta.exception;

public abstract class TAException extends Exception {
	protected Exception hidden;
	
	public TAException(String message, Exception e) {
		super(message, e);
		hidden = e;
	}

	public TAException(Exception e) {
		super(e);
		hidden = e;
	}
	
	public String toString() {
		return super.toString();
	}
}
