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
									<h2>Dispositivos al día</h2>
									<canvas id="myChart"></canvas>
									<h2>Dispositivos por hora</h2>
									<canvas id="myChart2"></canvas>
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
									<script>
										var ctx = document.getElementsByTagName('canvas')[1];
										ctx.height = 70;
										
										var axisX = ["23pm", "22pm", "21pm", "20pm", "19pm", "18pm", "17pm", "16pm", "15pm", "14pm", "13pm", "12pm", "11am", "10am", "09am", "08am", "07am", "06am", "05am", "04am", "03am", "02am", "01am", "00am"];
										
										var axisY = [];
										<c:forEach items="${horas}" var="y">
											axisY.push(parseInt("<c:out value='${y}'/>"));
										</c:forEach>
										
										var data = {
										    labels  : axisX,
										    datasets: [
												{
													label: "Dispositivos/Hora",
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
									<div id="tablas" style="overflow:hidden; text-align: center; margin:auto;">
										<div class="tabla1" style="display:inline-table; width: 300px;	height: auto; margin-right: 10px;">
											<table id="arrivals" class="table table-striped responsive-utilities jambo_table">
												<thead>
													<tr class="headings">
														<th colspan="2" style="text-align: center;">Arrivals</th>
													</tr>
													<tr class="headings">
														<th style="text-align: center;">LAP</th>
														<th style="text-align: center;">Fecha Entrada</th>
													</tr>
												</thead>
												<tbody>
													<c:forEach items="${arrivals}" var="dispositivo">
													<tr>
														<td><c:out value="${dispositivo.LAP}" /></td>
														<td id="arrivals${dispositivo.id}">
															<span class="hours${dispositivo.id}"></span>
															<span class="minutes${dispositivo.id}"></span>
														</td>
													</tr>
													<script>
														
													function getTimeRemaining(endtime) {
														var t = parseInt(${milisAhora}) - endtime;
														var minutes = 0;
														var hours = 0;
														if (t > 0) {
															t = Date.parse(new Date()) - (endtime*1000);
															minutes = Math.floor((t / 1000 / 60) % 60);
															hours = Math.floor((t / (1000 * 60 * 60)) % 24);
														} else{
															aux = true;
														}
														return {
															'total': t,
															'hours': hours,
															'minutes': minutes
														};
													}
														
														var clock = document.getElementById('arrivals' + ${dispositivo.id});
														var hoursSpan = clock.querySelector('.hours' + ${dispositivo.id});
														var minutesSpan = clock.querySelector('.minutes' + ${dispositivo.id});
														var aux = false;
														var t = getTimeRemaining(${dispositivo.systimeIN});
														
														if (aux){
															minutesSpan.innerHTML = 'just now';
														} else{
															hoursSpan.innerHTML = t.hours + 'h ' + ':';
															minutesSpan.innerHTML = t.minutes + 'm ' + 'ago';
														}
														
														
													</script>
													</c:forEach>
												</tbody>
											</table>
										</div>
										<div class="tabla2" style="display:inline-table; width: 300px; height: auto;">
											<table id="departures" class="table table-striped responsive-utilities jambo_table">
												<thead>
													<tr class="headings">
														<th colspan="2" style="text-align: center;">Departures</th>
													</tr>	
													<tr class="headings">
														<th style="text-align: center;">LAP</th>
														<th style="text-align: center;">Fecha Salida</th>
													</tr>
												</thead>
												<tbody>
													<c:forEach items="${departures}" var="dispositivo">
													<tr>
														<td><c:out value="${dispositivo.LAP}" /></td>
														<td id="departures${dispositivo.id}">
															<span class="hours1${dispositivo.id}"></span>
															<span class="minutes1${dispositivo.id}"></span>
														</td>
													</tr>
													<script>
														
													function getTimeRemaining(endtime) {
														var t = parseInt(${milisAhora}) - endtime;
														var minutes = 0;
														var hours = 0;
														
														if (t > 0) {
															t = (Date.parse(new Date())) - (endtime*1000);
															minutes = Math.floor((t / 1000 / 60) % 60);
															hours = Math.floor((t / (1000 * 60 * 60)) % 24);
														} else{
															aux = true;
														}
														return {
															'total': t,
															'hours': hours,
															'minutes': minutes
														};
													}
														
														var clock = document.getElementById('departures' + ${dispositivo.id});
														var hoursSpan = clock.querySelector('.hours1' + ${dispositivo.id});
														var minutesSpan = clock.querySelector('.minutes1' + ${dispositivo.id});
														var aux = false;
														var t = getTimeRemaining(${dispositivo.systimeOUT});
														
														if (aux){
															minutesSpan.innerHTML = 'just now';
														} else{
															hoursSpan.innerHTML = t.hours + 'h ' + ':';
															minutesSpan.innerHTML = t.minutes + 'm ' + 'ago';
														}
														
														
													</script>
													</c:forEach>
												</tbody>
											</table>
										</div>
									</div>
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