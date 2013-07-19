<!DOCTYPE HTML>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<html>
	<head>
		<title>Create Reminder.</title>
	</head>
	<body>
		<sf:form action="createReminder.post" method="post" commandName="reminder">
			<sf:textarea path="id"/><sf:errors path="id"/>
			<sf:textarea path="text"/><sf:errors path="text"/>
			<input type="submit" name="CreateReminder" value="Create Reminder" />
		</sf:form>
	</body>
</html>