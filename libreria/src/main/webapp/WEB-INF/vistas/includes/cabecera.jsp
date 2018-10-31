<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html>
<html>
<head>
<base href="${pageContext.request.contextPath}/">

<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<meta name="description" content="">
<meta name="author" content="">

<title>Librería</title>

<!-- CSS -->
	<!-- Bootstrap core CSS -->
	<!-- <link href="css/bootstrap.min.css" rel="stylesheet"> -->
	<link href="css/bootstrap-litera-theme.min.css" rel="stylesheet">
	
	<!-- Icons -->
	<link rel="stylesheet"
		href="https://use.fontawesome.com/releases/v5.4.1/css/all.css"
		integrity="sha384-5sAR7xN1Nv6T6+dT2mhtzEpVJvfS3NScPQTrOxhwjIuvcA67KV2R5Jz6kr4abQsz"
		crossorigin="anonymous">
	
	<!-- Datatables  styles-->
	<link rel="stylesheet" href="https://cdn.datatables.net/1.10.19/css/dataTables.bootstrap4.min.css">
	
	<!-- Custom styles -->
	<link href="css/estilos.css" rel="stylesheet">
	
<!-- SCRIPTS -->
	<!-- Optional JavaScript -->
	<!-- JQuery first, then Popper.js, then Bootstrap JS -->
	<!-- <script src="js/jquery-3.3.1.slim.min.js"></script>-->
	<script src="js/jquery-3.3.1.min.js"></script>
	<script src="js/popper.min.js"></script>
	<script src="js/bootstrap.min.js"></script>
	
	<!-- Datatables -->
	<script	src="http://cdn.datatables.net/1.10.19/js/jquery.dataTables.min.js"></script>
	<script src="https://cdn.datatables.net/1.10.19/js/dataTables.bootstrap4.min.js"></script>
	
	<!-- Custom scripts -->
		<!-- Datatables -->
		<script src="js/customDataTables.js"></script>
		<!-- Carga alumnos en select -->
		<script src="js/cambiarAlumnoSelect.js"></script>

</head>
<body>
	<div class="container-fluid">