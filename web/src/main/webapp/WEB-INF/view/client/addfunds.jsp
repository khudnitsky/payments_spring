<%@ page language="java"
		 contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<html>
<head>
	<title>Пополнение счета</title>
</head>
<body>
<form name="addFundsForm" method="POST" action="controller">
	<input type="hidden" name="command" value="addFunds" />
	Введите сумму:<br />
	<input type="text" name="addFunds" value="" />
	<input type="submit" value="Пополнить" /> <br />
	${operationMessage}  <br />
</form>
<a href="controller?command=backclient">Вернуться обратно</a>
<a href="controller?command=logout">Выйти из системы</a>
</body>
</html>
