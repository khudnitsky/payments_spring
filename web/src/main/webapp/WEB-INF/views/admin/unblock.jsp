<%@ page language="java"
		 contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Разблокировка счета</title>
	<link href="../../../assets/css/page_style.css" rel="stylesheet" type="text/css">
	<link href="../../../assets/css/sidebar_style.css" rel="stylesheet" type="text/css">
	<link href="../../../assets/css/button_style.css" rel="stylesheet" type="text/css">
</head>

<body>
<div class="container">
	<div class="header">
		<%@include file="../elements/header_pages.jsp" %>
		<!-- end .header -->
	</div>

	<div class="sidebar">
		<%@include file="../elements/admin/mini_sidebar.jsp" %>
		<!-- end .sidebar1 -->
	</div>

	<div class="content">
		<form name="unblockForm" method="POST" action="controller">
			<input type="hidden" name="command" value="unblock" />
			Выберите счет для разблокировки
			<table border="1">
				<tr bgcolor="#CCCCCC">
					<td align="center"><strong>№ Счета</strong></td>
					<td align="center"><strong>Сумма</strong></td>
					<td align="center"><strong>Валюта</strong></td>
				</tr>
				<c:forEach var="account" items="${accountsList}">
					<tr>
						<td><c:out value="${ account.id }" /></td>
						<td><c:out value="${ account.deposit }" /></td>
						<td><c:out value="${ account.currency.currencyType }" /></td>
						<td><input type="radio" name="toUnblock" value="${ account.id }"/></td>
					</tr>
				</c:forEach>
			</table>
			${errorEmptyChoice}
			${errorEmptyList} <br/>
			<input type="submit" value="Разблокировать"/>  <br/>
		</form>
		<a href="controller?command=backadmin">Вернуться обратно</a>
		<a href="controller?command=logout">Выйти из системы</a>
		<!-- end .content -->
	</div>
	<!-- end .container -->
</div>
</body>
</html>

