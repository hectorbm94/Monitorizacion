<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@page isELIgnored="false"%>
<%@ page import="com.google.appengine.api.blobstore.BlobstoreServiceFactory" %>
<%@ page import="com.google.appengine.api.blobstore.BlobstoreService" %>

<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<!-- Meta, title, CSS, favicons, etc. -->
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<title>Monitorizacion</title>
	<!-- Bootstrap core CSS -->
	<link href="../css/bootstrap.min.css" rel="stylesheet">
	<link href="../fonts/css/font-awesome.min.css" rel="stylesheet">
	<link href="../css/animate.min.css" rel="stylesheet">
	<!-- Custom CSS -->
	<link href="../css/custom.css" rel="stylesheet">
</head>
<body class="nav-md">
	<div class="container body">
		<div class="main_container">
			<%@ include file="menu.jsp"%>
			<!-- /top navigation -->
			<div class="right_col" role="main">
				<div class="">
					<div class="page-title">
						<div class="title_left">
							<h3>Análisis de dispositivos</h3>
						</div>
					</div>
					<div class="clearfix"></div>
					<!-- /.row -->
					<div class="row">
						<div class="col-lg-12">
							<div class="panel panel-default">
								<!-- /.panel-heading -->
								<div class="panel-body">
									<canvas id="myChart"></canvas>
									<script src="../js/Chart.js"></script>
									<script>
										var ctx = document.getElementsByTagName('canvas')[0];
										ctx.height = 70;
										
										var axisX = [];
										<c:forEach items="${dias}" var="x">
											axisX.push("<c:out value='${x}'/>");
										</c:forEach>
										
										var axisY = [];
										<c:forEach items="${total}" var="y">
											axisY.push(parseInt("<c:out value='${y}'/>"));
										</c:forEach>
										
										var data = {
										    labels  : axisX,
										    datasets: [
												{
													label: "Dispositivos/Día",
													fill: false,
													lineTension: 0.1,
													backgroundColor: "rgba(75,192,192,0.4)",
													borderColor: "rgba(75,192,192,1)",
													borderCapStyle: 'butt',
													borderDash: [],
													borderDashOffset: 0.0,
													borderJoinStyle: 'miter',
													pointBorderColor: "rgba(75,192,192,1)",
													pointBackgroundColor: "#fff",
													pointBorderWidth: 1,
													pointHoverRadius: 5,
													pointHoverBackgroundColor: "rgba(75,192,192,1)",
													pointHoverBorderColor: "rgba(220,220,220,1)",
													pointHoverBorderWidth: 2,
													pointRadius: 1,
													pointHitRadius: 10,
													data: axisY,
												}
										    ]
										};
											
										var options = {
											scales: {
									            yAxes: [{
									                ticks: {
									                    beginAtZero:true
									                }
									            }]
									        }
										};
										
										var myChart = new Chart(ctx, {
										    type   : 'line',
										    data   : data,
										    options: options
										});
									</script>
								</div>
								<!-- /.panel-body -->
							</div>
							<!-- /.panel -->
						</div>
						<!-- /.col-lg-12 -->
					</div>
				</div>
				<!-- /page content -->
				<div id="custom_notifications" class="custom-notifications dsp_none">
					<ul class="list-unstyled notifications clearfix"
						data-tabbed_notifications="notif-group"></ul>
					<div class="clearfix"></div>
					<div id="notif-group" class="tabbed_notifications"></div>
				</div>
			</div>
			<%@ include file="footer.jsp"%>
		</div>
		<%@ include file="scripts.jsp"%>
	</div>
</body>
</html>