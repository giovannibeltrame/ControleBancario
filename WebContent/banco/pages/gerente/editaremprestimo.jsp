<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Novo Plano de Empréstimo</title>
	<link rel="stylesheet" type="text/css" href="../../../css/bootstrap.min.css">
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
	<script>
		function validateForm() {
			var descricao = document.getElementById("descricao").value;
			var valortotal = document.getElementById("valortotal").value;
			var juros = document.getElementById("juros").value;
			if (descricao.trim() == "" || valortotal.trim() == "" || juros.trim() == "") {
				alert("Todos os campos devem ser preenchidos!");
				return false;
			}
		}
	</script>
</head>
<body>
	<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

	<div class="container">
		<div class="col-xs-6">
			<h3 class="page-header">Editar Plano de Empréstimo</h3>
			<div class="row">
				<div class="col-xs-8">
					<form action="editaremprestimo2.html" method="post">
						<div class="row">
							<div class="form-group col-xs-12">
								<div class="col-xs-4"><label for="descricao">Descrição</label></div>
								<div class="col-xs-8">
									<input type="text" name="descricao" id="descricao" placeholder="Descrição" class="form-control" value="${emp.descricao }">
								</div>
							</div>
						</div>
						<div class="row">
							<div class="form-group col-xs-12">
								<div class="col-xs-4"><label for="parcelas">Parcelas</label></div>
								<div class="col-xs-4">
								<select name="parcelas" id="parcelas" class="form-control">
									<option value="1" <c:if test="${emp.parcelas == 1 }">selected</c:if>>1x</option>
									<option value="2" <c:if test="${emp.parcelas == 2 }">selected</c:if>>2x</option>
									<option value="3" <c:if test="${emp.parcelas == 3 }">selected</c:if>>3x</option>
									<option value="4" <c:if test="${emp.parcelas == 4 }">selected</c:if>>4x</option>
									<option value="5" <c:if test="${emp.parcelas == 5 }">selected</c:if>>5x</option>
									<option value="5" <c:if test="${emp.parcelas == 6 }">selected</c:if>>6x</option>
								</select></div>
							</div>
						</div>
						<div class="row">
							<div class="form-group col-xs-12">
								<div class="col-xs-4"><label for="valortotal">Valor Total</label></div>
								<div class="col-xs-8">
									<input type="text" name="valortotal" id="valortotal" placeholder="Valor Total" class="form-control" value="${emp.valortotal }">
								</div>
							</div>
						</div>
						<div class="row">
							<div class="form-group col-xs-12">
								<div class="col-xs-4"><label for="juros">Juros (%)</label></div>
								<div class="col-xs-8">
									<input type="text" name="juros" id="juros" placeholder="Juros (%)" class="form-control" value="${emp.juros }">
								</div>
							</div>
						</div>
						<input type="hidden" name="idemprestimo" value="${emp.idemprestimo }">
						<input type="submit" value="SALVAR" class="btn btn-primary" onClick="return validateForm()">
					</form>
					${msg }
				</div>
			</div>
		</div>
	</div>
</body>
</html>