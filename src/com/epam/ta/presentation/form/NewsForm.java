package com.epam.ta.presentation.form;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.ValidatorForm;

import com.epam.ta.model.News;
import com.epam.ta.model.comparison.NewsByDateComparator;

public class NewsForm extends ValidatorForm {
	private List<News> newsList;

	private News newsMessage;

	private String[] selectedNews;

	private static final Comparator<News> newsByDateComparator = 
			new NewsByDateComparator();

	public NewsForm() {
		newsList = new ArrayList<News>();
		newsMessage = new News();
	}

	public void reset(ActionMapping mapping, HttpServletRequest request) {
		super.reset(mapping, request);
		selectedNews = null;
	}

	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		return super.validate(mapping, request);
	}

	public List<News> getNewsList() {
		Collections.sort(newsList, newsByDateComparator);
		return newsList;
	}

	public List<News> getNewsList(Comparator<News> newsComparator) {
		Collections.sort(newsList, newsComparator);
		return newsList;
	}

	// nList is all news from table
	public void setNewsList(List<News> list) {
		this.newsList.clear(); // to avoid repetitive elements
		this.newsList.addAll(list);
	}

	public void addToNewsList(News news) {
		newsList.add(news);
	}

	public void deleteFromNewsList(News news) {
		newsList.remove(news);
	}

	public News getNewsMessage() {
		return newsMessage;
	}

	public void setNewsMessage(News newsMessage) {
		this.newsMessage = newsMessage;
	}

	public void resetNewsMessage() {
		setNewsMessage(new News());
	}

	public String[] getSelectedNews() {
		return selectedNews;
	}

	public void setSelectedNews(String[] selectedNews) {
		this.selectedNews = selectedNews;
	}

	public void deleteGroupFromList() {
		Iterator it = newsList.iterator();
		News news = null;
		long newsId;
		while (it.hasNext()) {
			news = (News) it.next();
			newsId = news.getNewsId();
			for (int i = 0; i < selectedNews.length; i++) {
				if (newsId == Long.valueOf(selectedNews[i])) {
					it.remove();
					break;
				}
			}
		}
	}

	// getters and setters for newsMessage
	public String getTitle() {
		return newsMessage.getTitle();
	}

	public String getBrief() {
		return newsMessage.getBrief();
	}

	public String getContent() {
		return newsMessage.getContent();
	}

	public String getDateOfPublishing() {
		return newsMessage.getDateOfPublishing();
	}

	public long getNewsId() {
		return newsMessage.getNewsId();
	}

	public void setNewsId(long newsId) {
		newsMessage.setNewsId(newsId);
	}

	public void setTitle(String title) {
		newsMessage.setTitle(title);
	}

	public void setBrief(String brief) {
		newsMessage.setBrief(brief);
	}

	public void setContent(String content) {
		newsMessage.setContent(content);
	}

	public void setDateOfPublishing(String dateOfPublishing) {
		newsMessage.setDateOfPublishing(dateOfPublishing);
	}
}
