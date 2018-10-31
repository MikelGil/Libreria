<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!-- Navigation -->
<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
	<button class="navbar-toggler" type="button" data-toggle="collapse"
		data-target="#navbarTogglerDemo03" aria-controls="navbarTogglerDemo03"
		aria-expanded="false" aria-label="Toggle navigation">
		<span class="navbar-toggler-icon"></span>
	</button>
	<a class="navbar-brand" href="menu?accion=libros"> <i
		class="fas fa-home"></i>
	</a>

	<div class="collapse navbar-collapse" id="navbarTogglerDemo03">
		<ul class="navbar-nav mr-auto mt-2 mt-lg-0">
			<li class="nav-item active"><a class="nav-link"
				href="menu?accion=libros"><i class="fas fa-book"></i>&nbsp;Libros<span class="sr-only">(current)</span>
			</a></li>
			<li class="nav-item"><a class="nav-link"
				href="menu?accion=alumnos"><i class="fas fa-user"></i>&nbsp;Alumnos</a></li>
			<li class="nav-item"><a class="nav-link"
				href="menu?accion=editoriales"><i class="fas fa-pen-fancy"></i>&nbsp;Editoriales</a></li>
		</ul>
		<form action="logout" class="form-inline my-2 my-lg-0">
			<span class="navbar-text"> <i class="fas fa-user"></i>
				&nbsp;Bienvenido ${sessionScope.usuario.nombre}&nbsp;
			</span>
			<button class="btn btn-warning" type="submit">
				<i class="fas fa-sign-out-alt"></i>&nbsp;Logout
			</button>
		</form>
	</div>
</nav>

<c:if test="${mensaje != null}">
	<div class="alert alert-${mensajetipo} alert-dismissible fade show"
		role="alert">
		${mensaje}
		<button type="button" class="close" data-dismiss="alert"
			aria-label="Close">
			<span aria-hidden="true">&times;</span>
		</button>
	</div>
</c:if>