package com.epam.ta.database.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;

import com.epam.ta.database.dao.exception.NewsDAOException;

public abstract class AbstractDAO {
	private static final Logger logger = Logger.getLogger(AbstractDAO.class);
	
	protected static void closeStatement(Statement statement)
			throws NewsDAOException {
		try {
			if ((statement != null) && (!statement.isClosed())) {
				statement.close();
			}
		} catch (SQLException e) {
			logger.error(e);
			throw new NewsDAOException(e);
		}
	}

	protected static void closeResultSet(ResultSet resultSet)
			throws NewsDAOException {
		try {
			if ((resultSet != null) && (!resultSet.isClosed())) {
				resultSet.close();
			}
		} catch (SQLException e) {
			logger.error(e);
			throw new NewsDAOException(e);
		}
	}
}
