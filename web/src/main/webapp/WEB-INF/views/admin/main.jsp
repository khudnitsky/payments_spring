<%@ page language="java"
		 contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8" %>
<!DOCTYPE HTML>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
		<title>Добро пожаловать</title>
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
				<%@include file="../elements/admin/main_sidebar.jsp" %>
				<!-- end .sidebar1 -->
			</div>

			<div class="content">
				<br>
				<h2 align="left">${user.firstName} ${user.lastName}</h2>
				<h3 align="left">Вы вошли в систему как администратор</h3>
				<!-- end .content -->
			</div>
			<!-- end .container -->
		</div>
	</body>
</html>