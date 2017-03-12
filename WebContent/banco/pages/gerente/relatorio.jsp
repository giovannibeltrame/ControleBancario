<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Relatórios</title>
	<script src="../../../jquery-ui/external/jquery/jquery.js" type="text/javascript"></script>
	<script src="../../../js/bootstrap.min.js" type="text/javascript"></script>
	<script src="../../bower_components/raphael/raphael-min.js" type="text/javascript"></script>
	<script src="../../bower_components/morrisjs/morris.js" type="text/javascript"></script>
	<link href="../../bower_components/bootstrap/dist/css/bootstrap.min.css" rel="stylesheet">
	<link href="../../bower_components/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">
	<link href="../../bower_components/morrisjs/morris.css" rel="stylesheet">
	<style>
	#graph, #graph2 {
		width: 523px;
		height: 342px;
	</style>
</head>
<body>
	${msg }
	<div class="container">
		<div class="row">
			<div class="col-xs-12">
				<div class="row">
					<h3 class="page-header">Relatórios</h3>
				</div>
				<div class="row">
					<div class="col-xs-6">
						<div class="panel panel-default">
						<div class="panel-heading"><i class="fa fa-bar-chart-o fa-fw"></i> Empréstimos</div>
							<div class="panel-body">
								<div id="graph2"></div>
							</div>
						</div>
					</div>
					<div class="col-xs-6">
						<div class="panel panel-default">
							<div class="panel-heading"><i class="fa fa-bar-chart-o fa-fw"></i> Contas</div>
							<div class="panel-body">
								<div id="graph"></div>
							</div>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-xs-6">
						<div class="panel panel-default">
							<div class="panel-heading"><i class="fa fa-bar-chart-o fa-fw"></i> PDF</div>
							<div class="panel-body">
								<a href="relatoriocliente.html" target="_blank"><button type="button" class="btn btn-outline btn-primary"><span class="glyphicon glyphicon-download-alt"></span> Relatório de clientes</button></a>
								<a href="relatorioemprestimo.html" target="_blank"><button type="button" class="btn btn-outline btn-primary"><span class="glyphicon glyphicon-download-alt"></span> Relatório de empréstimos</button></a>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<script>
		Morris.Donut({
			element: 'graph',
			data: [
				{value: ${cc }, label: 'Conta Corrente'},
		    	{value: ${cp }, label: 'Conta Poupança'}
		  	],
		  	labelColor: '#000',
		  	colors: ['#CC3399','#669900']
		  	//formatter: function (x) { return x + "%"}
		});
		Morris.Bar({
			element: 'graph2',
			data: [
				{x: 'EMPRÉSTIMOS', y: ${tote }, z: ${totq }}
			],
			xkey: 'x',
			ykeys: ['y', 'z'],
			labels: ['VALOR EMPRESTADO', 'VALOR QUITADO']
		});
	</script>
</body>
</html>