<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/struts-html.tld" prefix="html"%>

<link rel="stylesheet" href="css/contentStylesheets/addEditNews.css"
	type="text/css" media="screen" />
<link rel="stylesheet" href="css/contentStylesheets/newsView.css"
	type="text/css" media="screen" />

<script type="text/javascript">
	var areYouSureMsg = '<bean:message key="are.you.sure" />';
</script>
<script type="text/javascript" src="js/confirmDeleting.js"></script>

<div id="content-head">
	<span id="label"><bean:message key="head.content.news" /></span><span
		id="arrows"> &gt;&gt;</span>
	<bean:message key="head.content.view" />
</div>
<table>
	<tr>
		<td class="label"><bean:message key="view.news.title" /></td>
		<td class="text"><bean:write name="newsForm" property="title" /></td>
	</tr>
	<tr>
		<td class="label"><bean:message key="view.news.date" /></td>
		<td class="text"><bean:write name="newsForm"
				property="dateOfPublishing" /></td>
	</tr>
	<tr>
		<td class="label"><bean:message key="view.news.brief" /></td>
		<td class="text"><bean:write name="newsForm" property="brief" /></td>
	</tr>
	<tr>
		<td class="label"><bean:message key="view.news.content" /></td>
		<td class="text"><bean:write name="newsForm" property="content" /></td>
	</tr>
</table>
<div id="submit-buttons">
	<html:form action="news.do?method=editNewsPage">
		<html:submit>
			<bean:message key="view.news.edit" />
		</html:submit>
	</html:form>
	<html:form action="news.do?method=deleteNews" styleId="deletingForm">
		<html:submit>
			<bean:message key="view.news.delete" />
		</html:submit>
	</html:form>
</div>
