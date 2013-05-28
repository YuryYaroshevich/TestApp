<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/struts-html.tld" prefix="html"%>

<link rel="stylesheet" href="css/contentStylesheets/newsList.css"
	type="text/css" media="screen" />

<script type="text/javascript" src="js/confirmDeletingGroup.js"></script>
<script type="text/javascript">
	var areYouSureMsg = '<bean:message key="are.you.sure" />';
	var noCheckedNewsMsg = '<bean:message key="no.checked.news" />';
</script>

<div id="content-head">
	<span id="label"><bean:message key="head.content.news" /></span><span
		id="arrows"> &gt;&gt;</span>
	<bean:message key="head.content.list" />
</div>
<logic:notEmpty name="newsForm" property="newsList">
	<html:form action="news.do?method=deleteNewsGroup"
		onsubmit="return confirmDeletingGroup();">
		<logic:iterate scope="session" name="newsForm" property="newsList"
			id="news">
			<div class="news-in-list">
				<div class="title">
					<bean:message key="news.list.title" />
					<bean:write name="news" property="title" />
				</div>
				<div class="date">
					<bean:write name="news" property="dateOfPublishing" />
				</div>
				<div class="brief">
					<bean:write name="news" property="brief" />
				</div>
				<bean:define id="newsId" name="news" property="newsId" />
				<div class="action-links">
					<html:link action="news.do?method=viewNews" paramId="newsId"
						paramName="newsId" style="text-decoration:none">
						<bean:message key="news.list.view" />
					</html:link>
					<html:link action="news.do?method=editNewsPage" paramId="newsId"
						paramName="newsId" style="text-decoration:none">
						<bean:message key="news.list.edit" />
					</html:link>
					<html:multibox property="selectedNews" value="${news.newsId}" />
				</div>
			</div>
		</logic:iterate>
		</br>
		<html:submit styleId="delete-group">
			<bean:message key="news.list.delete" />
		</html:submit>
	</html:form>
</logic:notEmpty>
<logic:empty name="newsForm" property="newsList">
	<bean:message key="news.list.no.news" />
</logic:empty>


