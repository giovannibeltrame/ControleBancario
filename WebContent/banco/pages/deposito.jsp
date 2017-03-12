<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Depósito</title>
	<link rel="stylesheet" type="text/css" href="../../css/bootstrap.min.css">
	<script src="../../jquery-ui/external/jquery/jquery.js" type="text/javascript"></script>
	<script src="../../jquery-ui/external/jquery.maskMoney.js" type="text/javascript"></script>
	<script src="../../js/bootstrap.min.js" type="text/javascript"></script>
	<script type="text/javascript">
		$(function() {
			$("#valor").maskMoney({
				symbol: 'R$ ',
				showSymbol: true,
				thousands: '',
				decimal: ','
			});
		});
	</script>
</head>
<body>
	<div class="container">
		<div class="col-xs-6">
			<h3 class="page-header">Depósito</h3>
			<div class="col-xs-6">
				<form action="depositar.html" method="post">
					<div class="form-group">
						<select name="tipo" class="form-control">
							<option value="C">Conta Corrente</option>
							<option value="P">Conta Poupança</option>
						</select>
					</div>
					<div class="form-group">
						<input type="text" name="valor" id="valor" placeholder="Informe o valor" class="form-control">
					</div>
					<input type="submit" value="DEPOSITAR" class="btn btn-primary">
				</form>
				<br>
				${msg }
			</div>
		</div>
	</div>
</body>
</html>