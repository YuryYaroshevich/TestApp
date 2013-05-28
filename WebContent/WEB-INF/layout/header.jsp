<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="/struts-bean.tld" prefix="bean"%>


<h1>
	<bean:message key="news.management" />
</h1>

<div id="language-links">
	<a href="news.do?method=changeLocale&language=en"><bean:message
			key="english.language" /></a> <a
		href="news.do?method=changeLocale&language=ru"><bean:message
			key="russian.language" /></a>
</div>


