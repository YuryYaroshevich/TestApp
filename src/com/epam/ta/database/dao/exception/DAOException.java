package com.epam.ta.database.dao.exception;

import com.epam.ta.exception.TATechnicalException;

public abstract class DAOException extends TATechnicalException {
	public DAOException(Exception e) {
		super(e);
	}
}
