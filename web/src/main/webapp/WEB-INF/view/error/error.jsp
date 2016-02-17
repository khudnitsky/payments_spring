<%@ page language="java" 
	contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" errorPage="/assets/pages/error/error.jsp"%>
<!DOCTYPE HTML>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title>Ошибка</title>
		<link href="/payments/assets/css/page_style.css" rel="stylesheet" type="text/css">
	</head>

	<body>
		<div class="container">
  			<div class="header">
                <%@include file="/assets/elements/header_pages.jsp" %>
    		<!-- end .header -->
            </div>
  				
  			<div class="sidebar">
            	<%@include file="/assets/elements/sidebar.jsp" %>
    		<!-- end .sidebar1 -->
            </div>
  
  			<div class="content">
            	<br>
    			<p> Извините, но в данный момент сервис не доступен: </p>
				<p>${errorDatabase} </p>
                <p>
                	<a href="controller?command=logout">Назад</a>
                </p>
    		<!-- end .content -->
            </div>
  		
  		<!-- end .container -->
        </div>
	</body>
</html>