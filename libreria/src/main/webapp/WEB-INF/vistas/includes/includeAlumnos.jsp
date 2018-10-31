<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<div class="row">
	<div class="col-md-3"></div>
	<div class="col-md-6">
		<h2 class="text-warning">
			<c:choose>
				<c:when test="${operacion == 'nuevo'}">Inserción de nuevo alumno</c:when>
				<c:when test="${operacion == 'editar'}">Modificación de alumno</c:when>
				<c:when test="${operacion == 'borrar'}">Borrado  de alumno</c:when>
			</c:choose>
		</h2>
		<div class="card border-warning">
			<div class="card-body">
				<form class="form" action="gestionAlumno?operacion=${operacion}" method="post">
					<input type="hidden" name="id" value="${alumno.id}">
					<input type="hidden" name="operacion" value="${operacion}"/>
					<div class="form-group">
						<label for="nombre" class="col-sm-10 control-label">Nombre:</label>
						<div class="col-sm-10">
							<input type=text class="form-control" id="nombre" name="nombre"
								placeholder="Nombre" required value="${alumno.nombre}"
								<c:if test="${operacion == 'borrar'}">disabled="disabled"</c:if>>
						</div>
					</div>
					<div class="form-group">
						<label for="apellido" class="col-sm-10 control-label">Apellido:</label>
						<div class="col-sm-10">
							<input type="text" class="form-control" id="apellido"
								name="apellido" placeholder="Apellido" required value="${alumno.apellido}"
								<c:if test="${operacion == 'borrar'}">disabled="disabled"</c:if>>
						</div>
					</div>
					<div class="form-group">
						<label for="dni" class="col-sm-10 control-label">DNI:</label>
						<div class="col-sm-10">
							<input type="text" class="form-control" id="dni" name="dni"
								placeholder="DNI" pattern="(([X-Z]{1})([-]?)(\d{7})([-]?)([A-Z]{1}))|((\d{8})([-]?)([A-Z]{1}))" required value="${alumno.dni}"
								<c:if test="${operacion == 'borrar'}">disabled="disabled"</c:if>>
						</div>
					</div>
					<div class="form-group">
						<div class="col-sm-12 text-center">
							<button type="submit" class="btn btn-success">
								<c:choose>
									<c:when test="${operacion == 'nuevo'}">Añadir</c:when>
									<c:when test="${operacion == 'editar'}">Modificar</c:when>
									<c:when test="${operacion == 'borrar'}">Borrar</c:when>
								</c:choose>
							</button>
							<a class="btn btn-danger" href="menu?accion=alumnos">Cancelar</a>
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>
	<div class="col-md-3"></div>
</div>