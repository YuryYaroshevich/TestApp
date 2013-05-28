package com.epam.ta.exception;

public abstract class TATechnicalException extends TAException {
	public TATechnicalException(Exception e) {
		super(e);
	}
}
