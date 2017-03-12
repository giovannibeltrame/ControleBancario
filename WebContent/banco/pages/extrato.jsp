<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Extrato</title>
	<link rel="stylesheet" type="text/css" href="../../css/bootstrap.min.css">
	<script src="../../jquery-ui/external/jquery/jquery.js" type="text/javascript"></script>
	<script src="../../jquery-ui/external/jquery.maskMoney.js" type="text/javascript"></script>
	<script src="../../js/bootstrap.min.js" type="text/javascript"></script>
</head>
<body>
	<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
	<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
	<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

	<div class="container">
		<div class="col-xs-6">
			<h3 class="page-header">Extrato</h3>
			<div class="col-xs-6">
					<form action="extrato.html" method="post">
					<div class="form-group">
						<select name="tipo" class="form-control">
							<option value="C">Conta Corrente</option>
							<option value="P">Conta Poupança</option>
						</select>
					</div>
					<div class="form-group">
						<select name="dias" class="form-control">
							<option value="15">15 dias</option>
							<option value="30">30 dias</option>
						</select>
					</div>
					<input type="submit" value="BUSCAR" class="btn btn-primary">
				</form>
			</div>
			<div class="col-xs-12">
			<br>
				<c:if test="${fn:length(lista) > 0 }">
					<table class="table table-bordered table-hover">
						<tr>
							<th>DATA</th>
							<th>DESCRIÇÃO</th>
							<th>VALOR</th>
						</tr>
						<c:forEach items="${lista }" var="t">
							<tr class="<c:if test="${t.tipo == 'D' || t.tipo == 'E' }">info</c:if>
										<c:if test="${t.tipo == 'S' || t.descricao == 'Parcela Emprestimo' }">danger</c:if>">
								<td><fmt:formatDate value="${t.data }" pattern="dd/MM/yyyy" /></td>
								<td>${t.descricao }</td>
								<td><fmt:formatNumber value="${t.valor }" type="currency" /></td>
							</tr>
						</c:forEach>
					</table>
					<div class="alert alert-warning">Saldo atual disponível: <fmt:formatNumber value="${saldo }" type="currency"/></div>
				</c:if>
			</div>
		</div>
	</div>
</body>
</html>