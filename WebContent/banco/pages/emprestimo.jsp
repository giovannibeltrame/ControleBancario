<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Empréstimo</title>
	<!-- <link rel="stylesheet" type="text/css" href="../../../css/bootstrap.min.css">-->
	<link href="../bower_components/bootstrap/dist/css/bootstrap.min.css" rel="stylesheet">
	<script src="../../jquery-ui/external/jquery/jquery.js" type="text/javascript"></script>
	<script src="../../jquery-ui/external/jquery.maskMoney.js" type="text/javascript"></script>
	<script src="../../js/bootstrap.min.js" type="text/javascript"></script>
	<script type="text/javascript">
		$(function() {
			$("#final").hide();
			$("#fake").click(function() {
				$("#fake").hide();
				$("#final").fadeIn();
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
			<h3 class="page-header">Empréstimo</h3>
				<div class="row">
					<div class="col-xs-10">
						<div class="alert alert-info">Estão disponíveis alguns planos de empréstimo liberados pelo seu gerente!</div>
						<a href="#" class="btn btn-primary" id="fake">MOSTRAR</a>
						<div id="final">
							<div class="form-group col-xs-6">
								<input type="password" name="pass" id="pass" placeholder="Informe a senha" class="form-control">
							</div>
							<div class="form-group col-xs-6">
								<a href="mostrarplanos.html" class="btn btn-primary">MOSTRAR</a>
							</div>
						</div>
					</div>
				</div>
				<br>
				<div class="row">
					<c:if test="${fn:length(lista) > 0 }">
						<table class="table table-bordered table-hover">
							<tr>
								<th>DESCRIÇÃO</th>
								<th>VALOR TOTAL</th>
								<th>JUROS (%)</th>
								<th>VALOR PARCELAS</th>
								<th>PARCELAS</th>
								<th>SOLICITAR</th>
							</tr>
							<c:forEach items="${lista }" var="e">
								<tr>
									<td>${e.descricao }</td>
									<td><fmt:formatNumber value="${e.valortotal }" type="currency" /></td>
									<td>${e.juros }</td>
									<td><fmt:formatNumber value="${e.valorparcela }" type="currency" /></td>
									<td>${e.parcelas }</td>
									<td><a href="solicitaremprestimo.html?id=${e.idemprestimo }" class="btn btn-info"><span class="glyphicon glyphicon-plus"></span></a></td>
								</tr>
							</c:forEach>
						</table>
					</c:if>
				<div class="col-xs-10">
					${msg }
				</div>
				</div>
		</div>
	</div>
</body>
</html>