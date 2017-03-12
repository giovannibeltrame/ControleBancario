<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Novo Cliente</title>
	<link rel="stylesheet" type="text/css" href="../../../css/bootstrap.min.css">
	<script src="../../../jquery-ui/external/jquery/jquery.js" type="text/javascript"></script>
	<script src="../../../js/bootstrap.min.js" type="text/javascript"></script>
	<script type="text/javascript" src="../../../jquery-ui/external/jquery.maskedinput-1.1.4.pack.js"/></script>
	<script type="text/javascript">
		$(function() {
			$("#cpf").mask("999.999.999-99");
		});
	</script>
	<script>
		function validateForm() {
			var nome = document.getElementById("nome").value;
			var cpf = document.getElementById("cpf").value;
			var telefone = document.getElementById("telefone").value;
			var login = document.getElementById("login").value;
			var senha = document.getElementById("senha").value;
			var confirmsenha = document.getElementById("confirmsenha").value;
			if (nome.trim() == "" || cpf.trim() == "" || telefone.trim() == "" || login.trim() == "" || senha.trim() == "" || confirmsenha.trim() == "" ) {
				alert("Todos os campos devem ser preenchidos!");
				return false;
			}
			if (senha.trim() != confirmsenha.trim()) {
				alert("Senhas diferentes!");
				return false;
			}
		}
	</script>
</head>
<body>
	<div class="container">
		<div class="col-xs-6">
			<h3 class="page-header">Novo Cliente</h3>
			${msg }
			<div class="row">
				<div class="col-xs-6">
					<form action="cadastrar.html" method="post">
						<div class="form-group">
							<input type="text" name="nome" id="nome" placeholder="Nome" class="form-control">
						</div>
						<div class="form-group">
							<input type="text" name="cpf" id="cpf" placeholder="CPF" class="form-control">
						</div>
						<div class="form-group">
							<input type="text" name="telefone" id="telefone" placeholder="Telefone" class="form-control">
						</div>
						<div class="form-group">
							<input type="text" name="login" id="login" placeholder="Login" class="form-control">
						</div>
						<div class="form-group">
							<input type="password" name="senha" id="senha" placeholder="Senha" class="form-control">
						</div>
						<div class="form-group">
							<input type="password" name="confirmsenha" id="confirmsenha" placeholder="Confirme a senha" class="form-control">
						</div>
						<div class="checkbox">
							<label><input type="checkbox" name="conta" value="C">Conta Corrente</label>
							<label><input type="checkbox" name="conta" value="P">Conta Poupança</label>
						</div>
						<input type="submit" value="CADASTRAR" class="btn btn-primary" onClick="return validateForm()">
					</form>
				</div>
			</div>
		</div>
	</div>
</body>
</html>