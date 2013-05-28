<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="/struts-html.tld" prefix="html"%>
<%@ taglib uri="/struts-bean.tld" prefix="bean"%>




<h4>
	<bean:message key="news" />
</h4>

<ul>
	<li><a href="news.do?method=newsList"> <bean:message
				key="news.list" />
	</a></li>
	<li><a href="news.do?method=addNewsPage"> <bean:message
				key="add.news" />
	</a></li>
</ul>
