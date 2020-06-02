<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="ual.dss.core.Noticia"%>
<!DOCTYPE html>
<html>
<head>
<title>Noticias</title>
<!-- <link rel="stylesheet" type="text/css" th:href="@{/css/style.css}" /> -->
<link
	href='http://fonts.googleapis.com/css?family=Roboto:400,100,100italic,300,300italic,4
00italic,500,500italic,700,700italic,900italic,900'
	rel='stylesheet' type='text/css' />
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!--===============================================================================================-->
<link rel="icon" type="image/png" href="/images/icons/favicon.png" />
<!--===============================================================================================-->
<link rel="stylesheet" type="text/css"
	href="vendor/bootstrap/css/bootstrap.min.css">
<!--===============================================================================================-->
<link rel="stylesheet" type="text/css"
	href="fonts/fontawesome-5.0.8/css/fontawesome-all.min.css">
<!--===============================================================================================-->
<link rel="stylesheet" type="text/css"
	href="fonts/iconic/css/material-design-iconic-font.min.css">
<!--===============================================================================================-->
<link rel="stylesheet" type="text/css" href="vendor/animate/animate.css">
<!--===============================================================================================-->
<link rel="stylesheet" type="text/css"
	href="vendor/css-hamburgers/hamburgers.min.css">
<!--===============================================================================================-->
<!-- <link rel="stylesheet" type="text/css"
	th:href="@{/vendor/animsition/css/animsition.min.css}"> -->
<!--===============================================================================================-->
<link rel="stylesheet" type="text/css" href="css/util.min.css">
<!--===============================================================================================-->
<link rel="stylesheet" type="text/css" href="css/main.css">
<!--===============================================================================================-->

<!--  RESULTS CSS -->
<link rel="stylesheet" type="text/css"
	href="css/results/style.css">
<link rel="stylesheet" href="css/results/bootstrap.min.css">
<link rel="stylesheet" href="css/results/owl.carousel.min.css">
<link rel="stylesheet" href="css/results/magnific-popup.css">
<link rel="stylesheet" href="css/results/font-awesome.min.css">
<link rel="stylesheet" href="css/results/themify-icons.css">
<link rel="stylesheet" href="css/results/nice-select.css">
<link rel="stylesheet" href="css/results/flaticon.css">
<link rel="stylesheet" href="css/results/gijgo.css">
<link rel="stylesheet" href="css/results/animate.min.css">
<link rel="stylesheet" href="css/results/slicknav.css">

<link href="https://fonts.googleapis.com/icon?family=Material+Icons"
	rel="stylesheet">
</head>
<body class="animsition">

	<!-- Header -->
	<header>
		<!-- Header desktop -->
		<div class="container-menu-desktop">

			<!--  -->
			<div class="wrap-logo container">
				<h1>Noticias</h1>
			</div>

			<!--  -->
			<div class="wrap-main-nav">
				<div class="main-nav">
					<!-- Menu desktop -->
					<nav class="menu-desktop">

						<ul class="main-menu">
							<li class="mega-menu-item"><a href="#">Noticias</a></li>

							<li class="mega-menu-item"><a href="http://localhost:8082">Películas
							</a></li>
						</ul>
					</nav>
				</div>
			</div>
		</div>
	</header>

	<section class="bg0 p-t-70">
		<div class="container">
			<div class="row justify-content-center">
				<div class="col-md-10 col-lg-8">
					<div class="p-b-20">
						<div class="tab01 p-b-20">
							<div
								class="tab01-head how2 how2-cl1 bocl12 flex-s-c m-r-10 m-r-0-sr991">
								<h3 class="f1-m-2 cl12 tab01-title">Noticias</h3>
							</div>

							<div class="tab-pane fade show active" id="tab1-1"
								role="tabpanel">
								<div class="row">

									<%
										ArrayList<Noticia> noticias = (ArrayList<Noticia>) request
																			.getAttribute("noticias");

																	if (noticias.isEmpty()) {
									%>
									<div>No hay noticias para mostrar :(</div>
									<%
										} else {

									%>
									<div style="width:100%">
										<%
											for (Noticia noticia : noticias) {
										%>

										<div class="col-lg-12 col-md-12" style="margin-top:4px">
											<div
												class="single_jobs white-bg d-flex justify-content-between">
												<div class="jobs_left d-flex align-items-center">

													<img
														style="height: 82px; width: 82px; display: block; margin-right: 10px;"
														src="images/news.png" alt="">

													<div class="jobs_conetent" >
													<div>
													<i class="material-icons" style='font-size: 24px; display:inline-block; vertical-align: middle;'>&#xe88f;</i>
															<h4 style="display: inline-block"><%=noticia.getShortDescription()%></h4>
														</div>
														<div>
														<i class="material-icons" style='font-size: 24px; display:inline-block; vertical-align: middle;'>&#xe873;</i>
														<p style="display:inline-block"><%=noticia.getLargeDescription()%></p>
														</div>
														<div style="vertical-align:middle;">
														<i class="material-icons" style='font-size: 24px; display:inline-block; vertical-align: middle;'>&#xe157;</i>
														<p style="display:inline-block"><%=noticia.getUrl()%></p>
														</div>
														<div class="links_locat d-flex align-items-center">
															<div class="location">
															<i class="material-icons" style='font-size: 24px; display:inline-block; vertical-align: middle;'>&#xe916;</i>
																<p style="display:inline-block">
																	<%=noticia.getDate()%>
																</p>
															</div>
														</div>
													</div>
												</div>
											</div>
										</div>
										<hr style="border: 1px solid black; width:100%">
										<%
										}
									%>
									</div>
									<%
										}
									%>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</section>

</body>
</html>