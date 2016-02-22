<%@ page language="java"
		 contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
		<title>Клиенты</title>
		<link href="../../../assets/css/page_style.css" rel="stylesheet" type="text/css">
		<link href="../../../assets/css/sidebar_style.css" rel="stylesheet" type="text/css">
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
				<table border="1" align="left">
					<tr bgcolor="#CCCCCC">
						<td align="center"><strong>Фамилия</strong></td>
						<td align="center"><strong>Имя</strong></td>
					</tr>
					<c:forEach var="client" items="${userList}">
						<tr>
							<td><c:out value="${client.lastName}" /></td>
							<td><c:out value="${client.firstName}" /></td>
						</tr>
					</c:forEach>
				</table>
				<!-- end .content -->
			</div>
			<!-- end .container -->
		</div>
	</body>
</html>