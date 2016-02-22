<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<html>
	<head>
		<title>Совершить платеж</title>
	</head>
	<body>
		<form name="paymentForm" method="POST" action="controller">
			<input type="hidden" name="command" value="payment" />
			Введите сумму платежа:<br /> 
			<input type="text" name="payment" value="" /> 
			<input type="submit" value="Оплатить" /> <br />
			${operationMessage} <br />
		</form>
		<a href="controller?command=backclient">Вернуться обратно</a>
		<a href="controller?command=logout">Выйти из системы</a>
	</body>
</html>
