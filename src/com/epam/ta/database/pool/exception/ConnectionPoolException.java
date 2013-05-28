package com.epam.ta.database.pool.exception;

import com.epam.ta.exception.TATechnicalException;

public class ConnectionPoolException extends TATechnicalException {
	public ConnectionPoolException(Exception e) {
		super(e);
	}
}
