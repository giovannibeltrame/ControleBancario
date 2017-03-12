<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Relatórios</title>
	<script src="../../jquery-ui/external/jquery/jquery.js" type="text/javascript"></script>
	<script src="../../js/bootstrap.min.js" type="text/javascript"></script>
	<script src="../bower_components/raphael/raphael-min.js" type="text/javascript"></script>
	<script src="../bower_components/morrisjs/morris.js" type="text/javascript"></script>
	<link href="../bower_components/bootstrap/dist/css/bootstrap.min.css" rel="stylesheet">
	<link href="../bower_components/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">
	<link href="../bower_components/morrisjs/morris.css" rel="stylesheet">
	<script>
		//$("#fake").hide();
	</script>
	<style>
	#graph {
		width: 523px;
		height: 342px;
	</style>
</head>
<body>
	<div class="container">
	<div id="fake">
	</div>
		<div class="row">
			<div class="col-xs-12">
				<div class="row">
					<h3 class="page-header">Relatórios</h3>
				</div>
				<div class="row">
					<div class="col-xs-8">
						<div class="panel panel-default">
						<div class="panel-heading"><i class="fa fa-bar-chart-o fa-fw"></i> Saldo</div>
							<div class="panel-body">
								<div id="graph"></div>
							</div>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-xs-8">
						<div class="panel panel-default">
							<div class="panel-heading"><i class="fa fa-bar-chart-o fa-fw"></i> PDF</div>
							<div class="panel-body">
								<a href="relatorioextrato.html?tipo=C" target="_blank"><button type="button" class="btn btn-outline btn-primary"><span class="glyphicon glyphicon-download-alt"></span> Extrato últimos 90 dias - Conta Corrente</button></a>
								<a href="relatorioextrato.html?tipo=P" target="_blank"><button type="button" class="btn btn-outline btn-primary"><span class="glyphicon glyphicon-download-alt"></span> Extrato últimos 90 dias - Conta Poupança</button></a>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	 <script>
	 	function dateFormater() {
	 		
	 		var formatDateString = function (dateString) {
			    var date = new Date(dateString);
			    
			    //DAY
			    var day = date.getDate().toString();
			    if (day.length == 1){
					day = "0" + day;	
			    }
			    
			    //MONTH
			    var month = (date.getMonth()+1).toString();
			    if (month.length == 1){
			    	month = "0" + month;
			    }
			    
			    //YEAR
			    var year = date.getFullYear().toString();  
			    return day + "/" + month + "/" + year;	 			
	 		}
	 		
	 		return {
	 			formatDateString: formatDateString
	 		};
		}
	 	
	 	var formatter = new dateFormater();
	 	
		Morris.Area({
			element: 'graph',
		  	behaveLikeLine: true,
		  	data: [
					{ x: '2016-06-08', y: 0 }
					<c:forEach items="${cc }" var="c">
					,{ x: '${c.data }', y: ${c.saldo }}
					</c:forEach>
			],
		  	xkey: 'x',
		  	ykeys: ['y'],
		  	dateFormat: function (dateString) {
		 		return formatter.formatDateString(dateString);
		 	},
		  	labels: ['SALDO']
		});
	</script> 
</body>
</html>