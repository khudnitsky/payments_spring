<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Операции</title>
	</head>
	<body>
		<table border="1">
			<tr bgcolor="#CCCCCC">
				<td align="center"><strong>Дата</strong></td>
				<td align="center"><strong>Описание</strong></td>
				<td align="center"><strong>Сумма</strong></td>
				<td align="center"><strong>Клиент</strong></td>
				<td align="center"><strong>Счет</strong></td>
			</tr>

			<c:forEach var="operation" items="${operationsList}">
				<tr>
					<td><c:out value="${ operation.operationDate }"/></td>
					<td><c:out value="${ operation.description }" /></td>
					<td align="right">
						<fmt:formatNumber value="${ operation.amount }" type="currency" minFractionDigits="2"  currencySymbol=""/>
					</td>
					<td align="center"><c:out value="${ operation.userLastName }" /></td>
					<td align="center"><c:out value="${ operation.accountNumber }" /></td>
				</tr>
			</c:forEach>
		</table>
		<br>

		<c:choose>
			<c:when test="${currentPage != 1}">
				<td><a href="controller?command=operations&currentPage=${currentPage - 1}">На предыдущую</a></td>
			</c:when>
			<c:otherwise>
				<td></td>
			</c:otherwise>
		</c:choose>


		<table border="1" cellpadding="5" cellspacing="5">
			<tr>
				<c:forEach begin="1" end="${numberOfPages}" var="i">
					<c:choose>
						<c:when test="${currentPage eq i}">
							<td>${i}</td>
						</c:when>
						<c:otherwise>
							<td><a href="controller?command=operations&currentPage=${i}">${i}</a></td>
						</c:otherwise>
					</c:choose>
				</c:forEach>
			</tr>
		</table>

		<c:choose>
			<c:when test="${currentPage lt numberOfPages}">
				<td><a href="controller?command=operations&currentPage=${currentPage + 1}">На следующую</a></td>
			</c:when>
			<c:otherwise>
				<td></td>
			</c:otherwise>
		</c:choose>

		<form name="operationsForm" method="POST" action="controller">
			<input type="hidden" name="command" value="operations" />
			<input type="text" name="currentPage" value="" size="3"/>
			<input type="submit" value="Перейти" />
		</form>

		<form name="recordsPerPageForm" method="POST" action="controller">
			<input type="hidden" name="command" value="operations"/>
			<select name="recordsPerPage">
				<option value="3">3</option>
				<option value="5">5</option>
				<option value="10">10</option>
			</select>
			<input type="submit" value="Обновить" />
		</form>

		<form name="orderingForm" method="POST" action="controller">
			<input type="hidden" name="command" value="operations"/>
			<select name="ordering">
				<option value="date">по дате</option>
				<option value="description">по описанию</option>
				<option value="amount">по сумме</option>
				<option value="client">по клиенту</option>
				<option value="account">по счету</option>
			</select>
			<input type="submit" value="Обновить" />
		</form>

		<br>
		<a href="controller?command=backadmin">Вернуться обратно</a>
		<a href="controller?command=logout">Выйти из системы</a>
	</body>
</html>