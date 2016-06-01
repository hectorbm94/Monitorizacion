<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@page isELIgnored="false"%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Monitorizacion</title>
<link
	href="http://fonts.googleapis.com/css?family=Open+Sans:300,400,600,700,800"
	rel="stylesheet" type="text/css">
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

			<div class="right_col" role="main">
				<div class="">
					<div class="page-title">
						<div class="title_left">
							<h3>Dispositivos</h3>
						</div>
					</div>
					<div class="clearfix"></div>
					<div class="row">
						<div class="col-md-12 col-sm-12 col-xs-12">
							<div class="clearfix"></div>
						</div>
						<div class="x_content">
							<div class="col-lg-12">
								<div class="panel panel-default">
									<!-- /.panel-heading -->
									<div class="panel-body">
										<table id="example"
											class="table table-striped responsive-utilities jambo_table">
											<thead>
												<tr class="headings">
													<th class="sorting">LAP</th>
													<th>Fecha</th>
													<th>Canal</th>
													<th>SNR</th>
												</tr>
											</thead>
											<tbody>
												<c:forEach items="${dispositivos}" var="dispositivo">
													<tr>
														<td><c:out value="${dispositivo.LAP}" /></td>
														<td><c:out value="${dispositivo.systime}" /></td>
														<td><c:out value="${dispositivo.canal}" /></td>
														<td><c:out value="${dispositivo.SNR}" /></td>
													</tr>
												</c:forEach>
											</tbody>
										</table>
									</div>
								</div>
								<c:if test="${alerta == 'Fichero subido con éxito'}">
									<div class="alert alert-success fade in"
										style="font-size: 14px;">
										<a class="close" href="#" data-dismiss="alert">x</a> <strong>Fichero correcto: 
										</strong>
										<c:out value="${alerta}" />
									</div>
								</c:if>
								<c:if
									test="${alerta != null && alerta != 'Fichero subido con éxito'}">
									<div class="alert alert-danger fade in"
										style="color: white; font-size: 14px;">
										<a class="close" href="#" data-dismiss="alert">x</a> <strong>Error:
										</strong>
										<c:out value="${alerta}" />
									</div>
								</c:if>
							</div>
						</div>
					</div>
				</div>
			</div>
			<%@ include file="footer.jsp"%>
		</div>
		<%@ include file="scripts.jsp"%>
	</div>
</body>
</html>