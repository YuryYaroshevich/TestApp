package com.epam.ta.database.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.epam.ta.database.dao.exception.NewsDAOException;
import com.epam.ta.database.dao.property.SQLQueryReader;
import com.epam.ta.database.pool.ConnectionPool;
import com.epam.ta.exception.TATechnicalException;
import com.epam.ta.model.News;

public final class NewsDAO extends AbstractDAO implements INewsDAO {
	private static final INewsDAO dao = new NewsDAO();

	private static ConnectionPool pool;

	private static final int NEWS_ID_COLUMN_INDEX = 1;
	private static final int TITLE_COLUMN_INDEX = 2;
	private static final int BRIEF_COLUMN_INDEX = 3;
	private static final int CONTENT_COLUMN_INDEX = 4;
	private static final int DATE_OF_PUBLISHING_COLUMN_INDEX = 5;

	private static final String FETCH_BY_ID_QUERY = "FETCH_BY_ID_QUERY";
	private static final String GET_LIST_QUERY = "GET_LIST_QUERY";
	private static final String DELETE_NEWS_QUERY = "DELETE_NEWS_QUERY";
	private static final String UPDATE_NEWS_QUERY = "UPDATE_NEWS_QUERY";
	private static final String ADD_NEWS_QUERY = "ADD_NEWS_QUERY";
	private static final String GET_ID_OF_NEW_NEWS_QUERY = "GET_ID_OF_NEW"
			+ "_NEWS_QUERY";
	private static final String DELETE_NEWS_GROUP_QUERY = "DELETE_NEWS_"
			+ "GROUP_QUERY";

	private static final Logger logger = Logger.getLogger(NewsDAO.class);

	private NewsDAO() {
	}

	public static INewsDAO getInstance() {
		return dao;
	}

	public void setConnectionPool(ConnectionPool connectionPool) {
		pool = connectionPool;
	}

	public ConnectionPool getConnectionPool() {
		return pool;
	}

	public News fetchNewsById(long newsId) throws TATechnicalException {
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Connection connection = null;
		try {
			connection = pool.getConnection();
			preparedStatement = connection.prepareStatement(SQLQueryReader
					.getSQlQuery(FETCH_BY_ID_QUERY));
			preparedStatement.setLong(1, newsId);
			resultSet = preparedStatement.executeQuery();
			resultSet.next();
			return buildNews(resultSet);
		} catch (SQLException e) {
			logger.error(e);
			throw new NewsDAOException(e);
		} finally {
			closeStatement(preparedStatement);
			closeResultSet(resultSet);
			pool.makeConnectionFree(connection);
		}
	}

	public List<News> getNewsList() throws TATechnicalException {
		Statement statement = null;
		ResultSet resultSet = null;
		Connection connection = null;
		try {
			connection = pool.getConnection();
			statement = connection.createStatement();
			resultSet = statement.executeQuery(SQLQueryReader
					.getSQlQuery(GET_LIST_QUERY));
			return buildNewsList(resultSet);
		} catch (SQLException e) {
			logger.error(e);
			throw new NewsDAOException(e);
		} finally {
			closeStatement(statement);
			closeResultSet(resultSet);
			pool.makeConnectionFree(connection);
		}
	}

	public void deleteNews(long newsId) throws TATechnicalException {
		PreparedStatement statement = null;
		Connection connection = null;
		try {
			connection = pool.getConnection();
			statement = connection.prepareStatement(SQLQueryReader
					.getSQlQuery(DELETE_NEWS_QUERY));
			statement.setLong(1, newsId);
			statement.executeUpdate();
		} catch (SQLException e) {
			logger.error(e);
			throw new NewsDAOException(e);
		} finally {
			closeStatement(statement);
			pool.makeConnectionFree(connection);
		}
	}

	public void deleteNewsGroup(String[] selectedNews)
			throws TATechnicalException {
		Statement statement = null;
		Connection connection = null;
		try {
			connection = pool.getConnection();
			statement = connection.createStatement();
			statement.executeUpdate(deleteGroupQuery(selectedNews));
		} catch (SQLException e) {
			logger.error(e);
			throw new NewsDAOException(e);
		} finally {
			closeStatement(statement);
			pool.makeConnectionFree(connection);
		}
	}

	private static String deleteGroupQuery(String[] selectedNews) {
		StringBuilder query = new StringBuilder(
				SQLQueryReader.getSQlQuery(DELETE_NEWS_GROUP_QUERY));
		int len = selectedNews.length;
		for (int i = 0; i < len; i++) {
			query.append(selectedNews[i]);
			if (i < len - 1) {
				query.append(",");
			}
		}
		query.append(")");
		return query.toString();
	}

	public void updateNews(News editedNews) throws TATechnicalException {
		PreparedStatement statement = null;
		Connection connection = null;
		try {
			connection = pool.getConnection();
			statement = connection.prepareStatement(SQLQueryReader
					.getSQlQuery(UPDATE_NEWS_QUERY));
			statement.setString(1, editedNews.getTitle());
			statement.setString(2, editedNews.getBrief());
			statement.setString(3, editedNews.getContent());
			statement.setString(4, editedNews.getDateOfPublishing());
			statement.setLong(5, editedNews.getNewsId());
			statement.executeUpdate();
		} catch (SQLException e) {
			logger.error(e);
			throw new NewsDAOException(e);
		} finally {
			closeStatement(statement);
			pool.makeConnectionFree(connection);
		}
	}

	public long addNews(News news) throws TATechnicalException {
		PreparedStatement statement = null;
		Connection connection = null;
		try {
			connection = pool.getConnection();
			statement = connection.prepareStatement(SQLQueryReader
					.getSQlQuery(ADD_NEWS_QUERY));
			statement.setString(1, news.getTitle());
			statement.setString(2, news.getBrief());
			statement.setString(3, news.getContent());
			statement.setString(4, news.getDateOfPublishing());
			statement.executeUpdate();
			return getLastCreatedNewsId(connection);
		} catch (SQLException e) {
			logger.error(e);
			throw new NewsDAOException(e);
		} finally {
			closeStatement(statement);
			pool.makeConnectionFree(connection);
		}
	}

	private static long getLastCreatedNewsId(Connection connection)
			throws NewsDAOException {
		Statement statement = null;
		ResultSet resultSet = null;
		try {
			statement = connection.createStatement();
			resultSet = statement.executeQuery(SQLQueryReader
					.getSQlQuery(GET_ID_OF_NEW_NEWS_QUERY));
			resultSet.next();
			return resultSet.getLong(1);
		} catch (SQLException e) {
			logger.error(e);
			throw new NewsDAOException(e);
		} finally {
			closeResultSet(resultSet);
			closeStatement(statement);
		}
	}

	private static News buildNews(ResultSet newsResultSet)
			throws NewsDAOException {
		try {
			long newsId = newsResultSet.getLong(NEWS_ID_COLUMN_INDEX);
			String title = newsResultSet.getString(TITLE_COLUMN_INDEX);
			String brief = newsResultSet.getString(BRIEF_COLUMN_INDEX);
			String content = newsResultSet.getString(CONTENT_COLUMN_INDEX);
			String date = newsResultSet
					.getString(DATE_OF_PUBLISHING_COLUMN_INDEX);
			return new News(newsId, title, brief, content, date);
		} catch (SQLException e) {
			logger.error(e);
			throw new NewsDAOException(e);
		}
	}

	private static List<News> buildNewsList(ResultSet newsListResSet)
			throws NewsDAOException {
		try {
			List<News> newsList = new ArrayList<News>();
			while (newsListResSet.next()) {
				newsList.add(buildNews(newsListResSet));
			}
			return newsList;
		} catch (SQLException e) {
			logger.error(e);
			throw new NewsDAOException(e);
		}
	}
}
