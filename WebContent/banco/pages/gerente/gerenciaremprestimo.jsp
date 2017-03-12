<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Gerenciar Planos de Empréstimo</title>
	<!-- <link rel="stylesheet" type="text/css" href="../../../css/bootstrap.min.css">-->
	<link href="../../bower_components/bootstrap/dist/css/bootstrap.min.css" rel="stylesheet">
	<script src="../../../jquery-ui/external/jquery/jquery.js" type="text/javascript"></script>
	<script src="../../../js/bootstrap.min.js" type="text/javascript"></script>
	<script src="../../../jquery-ui/external/jquery.maskMoney.js" type="text/javascript"></script>
	<script type="text/javascript">
		$(function() {
			$("#valortotal").maskMoney({
				symbol: 'R$ ',
				showSymbol: true,
				thousands: '',
				decimal: ','
			});
			$("#juros").maskMoney({
				symbol: '',
				showSymbol: true,
				thousands: '',
				decimal: ','
			});
		});
	</script>
</head>
<body>
	<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
	<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
	<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

	<div class="container">
		<div class="col-xs-8">
			<div class="row">
				<h3 class="page-header">Gerenciar Planos de Empréstimo</h3>
			</div>
			<div class="row">
				<form action="buscaremprestimo.html" method="post">
					<div class="form-group col-xs-6">
						<input type="text" name="descricao" class="form-control">
					</div>
					<div class="form-group">
						<input type="submit" value="BUSCAR" class="btn btn-info">
					</div>
				</form>
			</div>
			<div class="row">
				<c:if test="${fn:length(lista) > 0 }">
					<table class="table table-bordered table-hover">
							<tr>
								<th>DESCRIÇÃO</th>
								<th>VALOR TOTAL</th>
								<th>JUROS (%)</th>
								<th>VALOR PARCELAS</th>
								<th>PARCELAS</th>
								<th></th>
							</tr>
							<c:forEach items="${lista }" var="e">
								<tr>
									<td>${e.descricao }</td>
									<td><fmt:formatNumber value="${e.valortotal }" type="currency" /></td>
									<td>${e.juros }</td>
									<td><fmt:formatNumber value="${e.valorparcela }" type="currency" /></td>
									<td>${e.parcelas }</td>
									<td>
										<a href="editaremprestimo.html?id=${e.idemprestimo }" class="btn btn-info" title="Editar"><span class="glyphicon glyphicon-edit"></span></a>
										<a href="debitarparcelas.html?id=${e.idemprestimo }" class="btn btn-danger" title="Debitar parcelas"><span class="glyphicon glyphicon-download-alt"></span></a>
									</td>
								</tr>
							</c:forEach>
						</table>
				</c:if>
			</div>
			${msg }	
		</div>
	</div>