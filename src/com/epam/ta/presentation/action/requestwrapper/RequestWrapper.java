package com.epam.ta.presentation.action.requestwrapper;

public class RequestWrapper {
	private static final String REQUEST_ROOT = "/news.do?method=";

	private static final String PARAM_NAME_NEWS_ID = "newsId";

	private StringBuilder request;

	private boolean isPathAppended;

	public RequestWrapper() {
		request = new StringBuilder(REQUEST_ROOT);
		isPathAppended = false;
	}

	public void appendPathAndNewsId(String path, long newsId) {
		if (!isPathAppended) {
			request.append(path);
			request.append("&" + PARAM_NAME_NEWS_ID + "=" + newsId);
			isPathAppended = true;
		}
	}

	public void appendPath(String path) {
		if (!isPathAppended) {
			request.append(path);
			isPathAppended = true;
		}
	}

	public String getRequest() {
		return request.toString();
	}

	public void resetWrapper() {
		if (REQUEST_ROOT.length() < request.length()) {
			request.replace(REQUEST_ROOT.length(), request.length(), "");
			isPathAppended = false;
		}
	}

	public String toString() {
		return "PathWrapper [request=" + request.toString() + ",isPathApended="
				+ isPathAppended + "]";
	}
}
