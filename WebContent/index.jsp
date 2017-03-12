<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Login</title>
	<link rel="stylesheet" type="text/css" href="css/bootstrap.min.css">
	<style>
		.container {
			margin: 250px auto;
		}
	</style>
</head>
<body>
	
	<div class="container">
		<div class="col-xs-3 col-xs-offset-4">
		<h3 class="page-header">ENTRAR NO SISTEMA</h3>
			${msg }
			<form action="logar.html" method="post">
				<div class="form-group">
					<input type="text" name="login" placeholder="Login" class="form-control">
				</div>
				<div class="form-group"> 
					<input type="password" name="senha" placeholder="Senha" class="form-control">
				</div>
				<div class="row">
					<div class="col-xs-3 col-xs-offset-3">
						<input type="submit" value="LOGAR" class="btn btn-info">
					</div>
				</div>
			</form>
		</div>
	</div>
	
	
</body>
</html>