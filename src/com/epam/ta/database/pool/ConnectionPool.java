package com.epam.ta.database.pool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Locale;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Semaphore;

import org.apache.log4j.Logger;

import com.epam.ta.database.pool.exception.ConnectionPoolException;

public class ConnectionPool {
	private static final int NUMBER_OF_CONNECTIONS = 20;

	private static final String DATABASE_URL = "jdbc:oracle:thin:@localhost:1521:xe";

	private static final String DATABASE_USER = "yra";

	private static final String DATABASE_PASSWORD = "1234";

	private static final String DATABASE_DRIVER_NAME = "oracle.jdbc.driver.OracleDriver";

	// defined as volatile to make the write operation atomic
	private static volatile ConnectionPool pool;

	private BlockingQueue<Connection> busyConnections;

	private BlockingQueue<Connection> availableConnections;

	private Semaphore permissionGetter = new Semaphore(NUMBER_OF_CONNECTIONS,
			true);

	// used as lock in synchronized blocks
	private static final Object lock = new Object();

	private static final Logger logger = Logger.getLogger(ConnectionPool.class);

	private ConnectionPool() throws ConnectionPoolException {
		availableConnections = new ArrayBlockingQueue<Connection>(
				NUMBER_OF_CONNECTIONS);
		busyConnections = new ArrayBlockingQueue<Connection>(
				NUMBER_OF_CONNECTIONS);
		for (int i = 0; i < NUMBER_OF_CONNECTIONS; i++) {
			availableConnections.add(makeNewConnection());
		}
	}

	private Connection makeNewConnection() throws ConnectionPoolException {
		try {
			loadDatabaseDriver();
			Locale.setDefault(Locale.ENGLISH);
			return DriverManager.getConnection(DATABASE_URL, DATABASE_USER,
					DATABASE_PASSWORD);
		} catch (SQLException e) {
			logger.error(e);
			throw new ConnectionPoolException(e);
		}
	}

	private void loadDatabaseDriver() throws ConnectionPoolException {
		try {
			String driver = DATABASE_DRIVER_NAME;
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			logger.error(e);
			throw new ConnectionPoolException(e);
		}
	}

	public static ConnectionPool getInstance() throws ConnectionPoolException {
		if (pool == null) {
			synchronized (lock) {
				// This if is needed when 1st thread is interrupted after first
				// if. It would not allow to create one more pool.
				if (pool == null) {
					// The write operation wouldn't broke into 3 steps, because
					// pool is declared as volatile.
					pool = new ConnectionPool();
				}
			}
		}
		return pool;
	}

	public Connection getConnection() throws ConnectionPoolException {
		try {
			// I use semaphore here to avoid
			// NullPointerException
			permissionGetter.acquire();
			Connection connection = availableConnections.take();
			busyConnections.add(connection);
			return connection;
		} catch (InterruptedException e) {
			logger.error(e);
			throw new ConnectionPoolException(e);
		}
	}

	// synchronized, because if other thread interrupts work of
	// method after connection will be added to availableConnections, then there
	// would be wrong number of permissions.
	public void makeConnectionFree(Connection connection) {
		synchronized (lock) {
			busyConnections.remove(connection);
			availableConnections.add(connection);
			permissionGetter.release();
		}
	}

	public void closeAllConnections() throws ConnectionPoolException {
		closeConnections(busyConnections);
		busyConnections.clear();
		closeConnections(availableConnections);
		availableConnections.clear();
	}

	private void closeConnections(BlockingQueue<Connection> connections)
			throws ConnectionPoolException {
		try {
			Connection connection;
			for (int i = 0; i < connections.size(); i++) {
				connection = connections.poll();
				closeConnection(connection);
			}
		} catch (ConnectionPoolException e) {
			closeConnections(connections);
			throw e;
		}
	}

	private void closeConnection(Connection connection)
			throws ConnectionPoolException {
		try {
			if (!connection.isClosed()) {
				connection.close();
			}
		} catch (SQLException e) {
			logger.error(e);
			throw new ConnectionPoolException(e);
		}
	}
}
