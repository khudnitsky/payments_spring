<%@ page language="java" 
	contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html>
	<head>
		<title>Баланс</title>
	</head>
	<body>
		<h3>${userName}</h3>
		Ваш счет заблокирован <br/>
		Операции по снятию/пополнению средств невозможны <br/>
		Для разблокировки счета обратитесь к администратору <br/>
		<a href="controller?command=backclient">Вернуться обратно</a>
		<a href="controller?command=logout">Выйти из системы</a>
	</body>
</html>
