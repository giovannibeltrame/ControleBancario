<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Minha Conta</title>
	<link rel="stylesheet" type="text/css" href="../../css/bootstrap.min.css">
	<script src="../../jquery-ui/external/jquery/jquery.js" type="text/javascript"></script>
	<script src="../../jquery-ui/external/jquery.maskMoney.js" type="text/javascript"></script>
	<script src="../../js/bootstrap.min.js" type="text/javascript"></script>
</head>
<body>
	<div class="container">
		<div class="row">
			<div class="col-xs-6">
				<div class="row">
					<h3 class="page-header">Minha Conta</h3>
				</div>
				<div class="row">
					<div class="well well-lg">
						  <p class="text-muted">Nome: ${u.nome }</p>
						  <p class="text-muted">CPF: ${u.cpf }</p>
						  <p class="text-muted">Telefone: ${u.telefone }</p>
					</div>
				</div>
				<div class="row">
					<c:if test="${c.idconta > 0 }">
					<div class="col-xs-6">
						<div class="alert alert-lg alert-warning">
							<b>CONTA CORRENTE</b><br>
							Nº Agência: ${c.agencia.idagencia }<br>
							Nº Conta: ${c.idconta }<br>
							Saldo disponível: <b><fmt:formatNumber value="${c.saldo }" type="currency"/></b>
						</div>
					</div>
					</c:if>
					<c:if test="${p.idconta > 0 }">
					<div class="col-xs-6">
						<div class="alert alert-lg alert-warning">
							<b>CONTA POUPANÇA</b><br>
							Nº Agência: ${p.agencia.idagencia }<br>
							Nº Conta: ${p.idconta }<br>
							Saldo disponível: <b><fmt:formatNumber value="${p.saldo }" type="currency"/></b>
						</div>
					</div>
					</c:if>
				</div>
			</div>
		</div>
	</div>
</body>
</html>