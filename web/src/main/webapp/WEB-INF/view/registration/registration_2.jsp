<%@ page language="java" 
	contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" errorPage="/assets/pages/error/error.jsp"%>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<html>
	<head>
		<title>Регистрация</title>
        <link href="/payments/assets/css/page_style.css" rel="stylesheet" type="text/css">
        <link href="/payments/assets/css/logo_style.css" rel="stylesheet" type="text/css">
        <link href="/payments/assets/css/button_style.css" rel="stylesheet" type="text/css">
        <link href="/payments/assets/css/input_style.css" rel="stylesheet" type="text/css">
        <link href="/payments/assets/css/sidebar_style.css" rel="stylesheet" type="text/css">
	</head>
	<body>
    	<script type="text/javascript" 
				src="/payments/assets/js/registration_form_validation.js">
		</script>
    	<div class="container">
        
        	<div class="header">
                <%@include file="../elements/header.jsp" %>
            </div> <!-- end of header -->
            
            <div class="sidebar">
                <ul class="nav">
                      <li><a href="controller?command=back">Вернуться обратно</a></li>
                      <li><a href="http://select.by/kurs/" target="_blank">Курсы валют</a></li>
                      <li><a href="#">Информация о банке</a></li>
                      <li><a href="#">Новости</a></li>
                </ul>
            <!-- end .sidebar -->
            </div>
            
      <div class="content">
     	<form name="registrationForm" method="POST" action="controller" onSubmit="return validateForm()">
			<input type="hidden" name="command" value="registration" />
			<p align="center"><b>Введите ваши данные: </b></p>
            <br>
			<table align="center" cellpadding="5" cellspacing="5">
				<tr>
					<td>Имя:</td>
					<td><input type="text" id="firstName" name="firstname" value="" class="inputStyle"/></td>
				</tr>
                <tr>
                	<td></td>
                	<td width="20" align="center">
                    	<span id="emptyFirstName"></span>
                    </td>
                </tr>
				<tr>
					<td>Фамилия:</td>
					<td><input type="text" id="lastName" name="lastname" value="" class="inputStyle" /></td>
				</tr>
                <tr>
                	<td></td>
                	<td width="20" align="center">
                    	<span id="emptyLastName"></span>
                    </td>
                </tr>
				<tr>
					<td>Логин:</td>
					<td><input type="text" id="login" name="login" value="" class="inputStyle"/></td>
				</tr>
                <tr>
                	<td></td>
                	<td width="20" align="center">
                    	<span id="emptyLogin"></span>
                    </td>
                </tr>
				<tr>
					<td>Пароль 1:</td>
					<td><input type="password" id="password_1" name="password" value="" class="inputStyle" /></td>
				</tr>
                <tr>
                	<td></td>
                	<td width="20" align="center">
                    	<span id="emptyPassword_1"></span>
                    </td>
                </tr>
                <tr>
					<td>Пароль 2:</td>
					<td><input type="password" id="password_2" value="" class="inputStyle" /></td>
				</tr>
                <tr>
                	<td></td>
                	<td width="20" align="center">
                    	<span id="emptyPassword_2"></span>
                    </td>
                </tr>
				<tr>
					<td>№ Счета:</td>
					<td><input type="text" id="accountId" name="accountid" value="" class="inputStyle"/></td>
				</tr>
                <tr>
                	<td></td>
                	<td width="20" align="center">
                    	<span id="emptyAccountId"></span>
                    </td>
                </tr>
				<tr>
					<td>Валюта Счета:</td>
					<td>
						<input type="radio" name="currency" value="BYR" checked="checked"/>BYR
						<input type="radio" name="currency" value="USD"/>USD
						<input type="radio" name="currency" value="EUR"/>EUR<br/>
					</td>
				</tr>
                <tr>
                    <td colspan="2" align="center">
                        	${operationMessage}
                            ${errorUserExists}
                    </td>
                </tr>
			</table>			 
			 <br>
            
            <p align="center">
                        <input type="submit" value="Зарегистрировать"/>
                    </p>
                    <br>
		</form>

            <!-- end .container -->
		</div>
	</body>
</html>