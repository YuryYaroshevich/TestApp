package com.epam.ta.presentation.action;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.Globals;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.DispatchAction;

import com.epam.ta.database.dao.INewsDAO;
import com.epam.ta.exception.TATechnicalException;
import com.epam.ta.model.News;
import com.epam.ta.presentation.action.requestwrapper.RequestWrapper;
import com.epam.ta.presentation.form.NewsForm;

public class NewsAction extends DispatchAction {
	private INewsDAO newsDAO;

	private static final String FORWARD_NEWS_LIST = "newsList";
	private static final String FORWARD_VIEW_NEWS = "viewNews";
	private static final String FORWARD_ADD_NEWS = "addNews";
	private static final String FORWARD_EDIT_NEWS = "editNews";

	private static final String ATTR_PATH_WRAPPER = "pathWrapper";
	private static final String ATTR_LANGUAGE = "language";
	private static final String ATTR_PREVIOUS_PATH = "previousPath";

	public void setNewsDAO(INewsDAO newsDAO) {
		this.newsDAO = newsDAO;
	}

	public ActionForward newsList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws TATechnicalException {
		NewsForm newsForm = (NewsForm) form;
		newsForm.setNewsList(newsDAO.getNewsList());
		saveToken(request);
		HttpSession session = request.getSession(true);
		RequestWrapper requestWrapper = prepareRequestWrapper(session);
		ActionForward whereWeGo = mapping.findForward(FORWARD_NEWS_LIST);
		requestWrapper.appendPath(whereWeGo.getPath());
		session.setAttribute(ATTR_PATH_WRAPPER, requestWrapper);
		session.setAttribute(ATTR_PREVIOUS_PATH, whereWeGo.getPath());
		return whereWeGo;
	}

	// gets form for creating news
	public ActionForward addNewsPage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		NewsForm newsForm = (NewsForm) form;
		newsForm.resetNewsMessage();
		saveToken(request);
		HttpSession session = request.getSession(true);
		RequestWrapper requestWrapper = prepareRequestWrapper(session);
		ActionForward whereWeGo = mapping.findForward(FORWARD_ADD_NEWS);
		requestWrapper.appendPath(mapping.findForward(FORWARD_NEWS_LIST)
				.getPath());
		session.setAttribute(ATTR_PATH_WRAPPER, requestWrapper);
		session.setAttribute(ATTR_PREVIOUS_PATH, whereWeGo.getPath());
		return whereWeGo;
	}

	// saves created news in database
	public ActionForward saveNews(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws TATechnicalException {
		if (isTokenValid(request)) {
			NewsForm newsForm = (NewsForm) form;
			ActionMessages errors = newsForm.validate(mapping, request);
			if (!errors.isEmpty()) {
				saveErrors(request, errors);
				return mapping.findForward(FORWARD_ADD_NEWS);
			}
			resetToken(request);
			News newsMessage = newsForm.getNewsMessage();
			long newsId = newsDAO.addNews(newsMessage);
			newsMessage.setNewsId(newsId);
			// updates news list for newsList page
			newsForm.addToNewsList(newsMessage);
			return viewNews(mapping, newsForm, request, response);
		}
		return mapping.findForward(FORWARD_VIEW_NEWS);
	}

	public ActionForward viewNews(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws TATechnicalException {
		NewsForm newsForm = (NewsForm) form;
		News newsMessage = newsDAO.fetchNewsById(newsForm.getNewsId());
		newsForm.setNewsMessage(newsMessage);
		// creates token in user's session before submitting form
		saveToken(request);
		HttpSession session = request.getSession(true);
		RequestWrapper requestWrapper = prepareRequestWrapper(session);
		ActionForward whereWeGo = mapping.findForward(FORWARD_VIEW_NEWS);
		requestWrapper.appendPathAndNewsId(whereWeGo.getPath(),
				newsMessage.getNewsId());
		session.setAttribute(ATTR_PATH_WRAPPER, requestWrapper);
		session.setAttribute(ATTR_PREVIOUS_PATH, whereWeGo.getPath());
		return whereWeGo;
	}

	// gets form for editing with fields contains current news data
	public ActionForward editNewsPage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws TATechnicalException {
		NewsForm newsForm = (NewsForm) form;
		News newsMessage = newsDAO.fetchNewsById(newsForm.getNewsId());
		newsForm.setNewsMessage(newsMessage);
		// creates token in user's session before submitting form
		saveToken(request);
		HttpSession session = request.getSession(true);
		ActionForward whereWeGo = mapping.findForward(FORWARD_EDIT_NEWS);
		session.setAttribute(ATTR_PREVIOUS_PATH, whereWeGo.getPath());
		return whereWeGo;
	}

	// saves edited news in database
	public ActionForward updateNews(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws TATechnicalException {
		if (isTokenValid(request)) {
			NewsForm newsForm = (NewsForm) form;
			ActionMessages errors = newsForm.validate(mapping, request);
			if (!errors.isEmpty()) {
				saveErrors(request, errors);
				return mapping.findForward(FORWARD_EDIT_NEWS);
			}
			resetToken(request);
			newsDAO.updateNews(newsForm.getNewsMessage());
			return viewNews(mapping, newsForm, request, response);
		}
		return mapping.findForward(FORWARD_VIEW_NEWS);
	}

	// on view news page
	public ActionForward deleteNews(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws TATechnicalException {
		ActionForward whereWeGo = mapping.findForward(FORWARD_NEWS_LIST);
		if (isTokenValid(request, true)) {
			NewsForm newsForm = (NewsForm) form;
			newsDAO.deleteNews(newsForm.getNewsId());
			newsForm.deleteFromNewsList(newsForm.getNewsMessage());
			HttpSession session = request.getSession(true);
			session.setAttribute(ATTR_PREVIOUS_PATH, whereWeGo.getPath());
		}
		return whereWeGo;
	}

	// on list news page
	public ActionForward deleteNewsGroup(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws TATechnicalException {
		ActionForward whereWeGo = mapping.findForward(FORWARD_NEWS_LIST);
		if (isTokenValid(request, true)) {
			NewsForm newsForm = (NewsForm) form;
			String[] selectedNews = newsForm.getSelectedNews();
			if (selectedNews != null) {
				newsDAO.deleteNewsGroup(selectedNews);
				newsForm.deleteGroupFromList();
			}
			HttpSession session = request.getSession(true);
			session.setAttribute(ATTR_PREVIOUS_PATH, whereWeGo.getPath());
		}
		return whereWeGo;
	}

	public ActionForward changeLocale(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession(true);
		String language = (String) request.getParameter(ATTR_LANGUAGE);
		session.setAttribute(Globals.LOCALE_KEY, new Locale(language));
		String previousPath = (String) session
				.getAttribute(ATTR_PREVIOUS_PATH);
		return new ActionForward(previousPath);
	}

	public ActionForward cancel(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession(true);
		RequestWrapper requestWrapper = (RequestWrapper) session
				.getAttribute(ATTR_PATH_WRAPPER);
		String previousPath = requestWrapper.getRequest();
		return new ActionForward(previousPath);
	}

	private static RequestWrapper prepareRequestWrapper(HttpSession session) {
		RequestWrapper requestWrapper = (RequestWrapper) session
				.getAttribute(ATTR_PATH_WRAPPER);
		if (requestWrapper == null) {
			return new RequestWrapper();
		} else {
			requestWrapper.resetWrapper();
			return requestWrapper;
		}
	}
}
