package com.epam.ta.database.dao;

import java.util.List;

import com.epam.ta.exception.TATechnicalException;
import com.epam.ta.model.News;

public interface INewsDAO {
	List<News> getNewsList() throws TATechnicalException;
	
	News fetchNewsById(long newsId) throws TATechnicalException;
	
	void deleteNews(long newsId) throws TATechnicalException;
	
	void deleteNewsGroup(String[] selectedNews) throws TATechnicalException;
	
	long addNews(News news) throws TATechnicalException;
	
	void updateNews(News editedNews) throws TATechnicalException;
}
