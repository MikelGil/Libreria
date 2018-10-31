<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html>
<html>
<head>

<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<meta name="description" content="">
<meta name="author" content="">

<title>Librería</title>

<!-- Bootstrap core CSS -->
<link href="css/bootstrap.min.css" rel="stylesheet">

<!-- Icons -->
<link rel="stylesheet"
	href="https://use.fontawesome.com/releases/v5.4.1/css/all.css"
	integrity="sha384-5sAR7xN1Nv6T6+dT2mhtzEpVJvfS3NScPQTrOxhwjIuvcA67KV2R5Jz6kr4abQsz"
	crossorigin="anonymous">

<!-- Custom styles for this template -->
<link href="css/signin.css" rel="stylesheet">

<!-- Optional JavaScript -->
<!-- JQuery first, then Popper.js, then Bootstrap JS -->
<script src="js/jquery-3.3.1.slim.min.js"></script>
<script src="js/popper.min.js"></script>
<script src="js/bootstrap.min.js"></script>

</head>
<body class="text-center">
	<form class="form-signin" action="menu" method="post">
		<c:if test="${alertatexto != null}">
			<div class="alert alert-${alertatipo} alert-dismissible fade show"
				role="alert">
				${alertatexto}
				<button type="button" class="close" data-dismiss="alert"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
			</div>
		</c:if>
		<input type="hidden" name="accion" value="login"> <img
			class="mb-4" src="imgs/LOGO.png" alt="" width="72"
			height="72">
		<h1 class="h3 mb-3 font-weight-normal">Please sign in</h1>
		<label for="nombre" class="sr-only">Nombre</label> <input type="text"
			id="nombre" name="nombre" class="form-control" placeholder="Nombre"
			required autofocus> <label for="password" class="sr-only">Password</label>
		<input type="password" id="password" name="password"
			class="form-control" placeholder="Contraseña" required>
		<button class="btn btn-lg btn-success btn-block" type="submit">Sign
			in</button>
		<p class="mt-5 mb-3 text-muted">&copy; 2017-2018 Mikel Gil &amp;
			Mikel Kareaga</p>
	</form>
</body>
</html>

