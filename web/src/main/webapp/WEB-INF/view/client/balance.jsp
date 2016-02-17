<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
	<head>
		<title>Баланс</title>
	</head>
	<body>
		<h3>${userName}</h3>
		<table border="1">
			<tr>
				<td>У вас на счету:</td>
				<td><fmt:formatNumber value="${balance}" type="currency" currencySymbol=""/></td>
			</tr>
			<tr>
				<td>Валюта:</td>
				<td>${currency} </td>
			</tr>
		</table>
		<a href="controller?command=backclient">Вернуться обратно</a>
		<a href="controller?command=logout">Выйти из системы</a>
	</body>
</html>
