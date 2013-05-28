<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="/struts-html.tld" prefix="html"%>
<%@ taglib uri="/struts-bean.tld" prefix="bean"%>

<link rel="stylesheet" href="css/contentStylesheets/addEditNews.css"
	type="text/css" media="screen" />

<script type="text/javascript">
	var titleEmptyMsg = '<bean:message key="error.required.title" />';
	var titleTooLongMsg = '<bean:message key="error.maxlength.title" />';
	var briefEmptyMsg = '<bean:message key="error.required.brief" />';
	var briefTooLongMsg = '<bean:message key="error.maxlength.brief" />';
	var contentEmptyMsg = '<bean:message key="error.required.content" />';
	var contentTooLongMsg = '<bean:message key="error.maxlength.content" />';
	var dateEmptyMsg = '<bean:message key="error.required.date" />';
	var dateWrongFormatMsg = '<bean:message key="error.date.format" />';
</script>
<script type="text/javascript" src="js/cancel.js"></script>
<script type="text/javascript" src="js/validation.js"></script>
<script type="text/javascript" src="js/validationHelper.js"></script>

<div id="content-head">
	<span id="label"><bean:message key="head.content.news" /></span><span
		id="arrows"> &gt;&gt;</span>
	<bean:message key="head.content.edit" />
</div>
<html:errors />
<html:form action="news.do?method=updateNews">
	<table>
		<tr>
			<td class="label"><bean:message key="edit.news.title" /></td>
			<td><html:text name="newsForm" property="title" size="61" /></td>
		</tr>
		<tr>
			<td class="label"><bean:message key="edit.news.date" /></td>
			<td><html:text name="newsForm" property="dateOfPublishing"
					size="20" /></td>
		</tr>
		<tr>
			<td class="label"><bean:message key="edit.news.brief" /></td>
			<td><html:textarea name="newsForm" property="brief" cols="60"
					rows="10" /></td>
		</tr>
		<tr>
			<td class="label"><bean:message key="edit.news.content" /></td>
			<td><html:textarea name="newsForm" property="content" cols="60"
					rows="20" /></td>
		</tr>
		<tr>
			<td></td>
			<td class="buttons"><html:submit styleId="submit">
					<bean:message key="edit.news.save" />
				</html:submit> <html:submit styleId="cancel">
					<bean:message key="edit.news.cancel" />
				</html:submit></td>
		</tr>
	</table>
</html:form>

