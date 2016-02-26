﻿<%@ page contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" errorPage="WEB-INF/views/error/error.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>

<html>
<head>
    <title>Авторизация</title>
    <link href="assets/css/index/page_style.css" rel="stylesheet" type="text/css">
    <link href="assets/css/logo_style.css" rel="stylesheet" type="text/css">
    <link href="assets/css/button_style.css" rel="stylesheet" type="text/css">
    <link href="assets/css/input_style.css" rel="stylesheet" type="text/css">
    <link href="assets/css/index/sidebar_style.css" rel="stylesheet" type="text/css">

</head>

<body>
<script type="text/javascript"
        src="assets/js/login_form_validation.js">
</script>
<script type="text/javascript"
        src="assets/js/clear_result.js">
</script>
<div class="container">

    <div class="header">
        <%@include file="WEB-INF/views/elements/index/header.jsp" %>
    </div> <!-- end of header -->

    <div class="sidebar">
        <%@include file="WEB-INF/views/elements/index/sidebar.jsp"%>
        <!-- end .sidebar -->
    </div>

    <div class="content">
        <form name="loginForm" method="post" action="/login_user" onSubmit="return validateForm()">
            <br>
            <p align="center"><b>Введите ваш логин и пароль:</b></p>
            <table align="center" cellpadding="5" cellspacing="5">
                <tr>
                    <td>
                        <input type="text" id="login" name="login" class="inputStyle" placeholder="Логин"/>
                    </td>
                </tr>
                <tr>
                    <td width="20" align="center">
                        <span id="emptyLogin"></span>
                    </td>
                </tr>
                <tr>
                    <td>
                        <input type="password" id="password" name="password" class="inputStyle" placeholder="Пароль"/>
                    </td>
                </tr>
                <tr>
                    <td width="20" align="center">
                        <span id="emptyPassword"></span>
                    </td>
                </tr>
                <tr>
                    <td width="20">
                        <label id="label">
                            ${errorLoginOrPassword}
                        </label>
                    </td>
                </tr>
            </table>
            <p align="center">
                <input type="submit" value="Войти" onClick="clearResult()"/>
            </p>
        </form>
        <br>
        <!-- end .content -->
    </div>

    <div class="footer">
        <%@include file="WEB-INF/views/elements/index/footer.jsp" %>
        <!-- end .footer -->
    </div>
    <!-- end .container -->
</div>
</body>
</html>