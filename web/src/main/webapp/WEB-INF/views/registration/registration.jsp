<%@ page language="java"
         contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" errorPage="../error/error.jsp" %>
<!DOCTYPE HTML>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>Регистрация</title>
    <link href="../../../assets/css/page_style.css" rel="stylesheet" type="text/css">
    <link href="../../../assets/css/button_style.css" rel="stylesheet" type="text/css">
    <link href="../../../assets/css/input_style.css" rel="stylesheet" type="text/css">
    <link href="../../../assets/css/sidebar_style.css" rel="stylesheet" type="text/css">
</head>

<body>
<script type="text/javascript"
        src="../../../assets/js/registration_form_validation.js">
</script>
<script type="text/javascript"
        src="../../../assets/js/clear_result.js">
</script>
<div class="container">
    <div class="header">
        <%@include file="../elements/header_pages.jsp" %>
        <!-- end .header -->
    </div>

    <div class="sidebar">
        <%@include file="../elements/sidebar.jsp" %>
        <!-- end .sidebar1 -->
    </div>

    <div class="content">
        <form name="registrationForm" method="POST" action="controller" onSubmit="return validateForm()">
            <input type="hidden" name="command" value="registration"/>
            <p align="center"><b>Введите ваши данные: </b></p>
            <br>
            <table align="left" cellpadding="5" cellspacing="5">
                <tr>
                    <td>Имя:</td>
                    <td><input type="text" id="firstName" name="firstname" value="" class="inputStyle"/></td>
                    <td>
                        <span id="emptyFirstName"></span>
                    </td>
                </tr>
                <tr>
                    <td>Фамилия:</td>
                    <td><input type="text" id="lastName" name="lastname" value="" class="inputStyle"/></td>
                    <td>
                        <span id="emptyLastName"></span>
                    </td>
                </tr>
                <tr>
                    <td>Логин:</td>
                    <td><input type="text" id="login" name="login" value="" class="inputStyle"/></td>
                    <td>
                        <span id="emptyLogin"></span>
                    </td>
                </tr>
                <tr>
                    <td>Пароль 1:</td>
                    <td><input type="password" id="password_1" name="password" value="" class="inputStyle"/></td>
                    <td>
                        <span id="emptyPassword_1"></span>
                    </td>
                </tr>
                <tr>
                    <td>Пароль 2:</td>
                    <td><input type="password" id="password_2" value="" class="inputStyle"/></td>
                    <td>
                        <span id="emptyPassword_2"></span>
                    </td>
                </tr>
                <tr>
                    <td>№ Счета:</td>
                    <td><input type="text" id="accountId" name="accountid" value="" class="inputStyle"/></td>
                    <td>
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
                        <span id="inputError"></span>
                        <label id="label">
                            ${operationMessage}
                            ${errorUserExists}
                        </label>
                    </td>
                </tr>
            </table>
            <br>

            <p align="center">
                <input type="submit" value="Зарегистрировать" onClick="clearResult()"/>
            </p>
            <br>
        </form>
        <!-- end .content -->
    </div>

    <!-- end .container -->
</div>
</body>
</html>