<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<div class="row">
	<div class="col-md-3"></div>
	<div class="col-md-6">
		<h2 class="text-active">
			<c:choose>
				<c:when test="${operacion == 'nuevo'}">Inserción de nuevo editorial</c:when>
				<c:when test="${operacion == 'editar'}">Modificación de editorial</c:when>
				<c:when test="${operacion == 'borrar'}">Borrado de editorial</c:when>
			</c:choose>
		</h2>
		<div class="card border-info">
			<div class="card-body">
				<form class="form" action="gestionEditorial?operacion=${operacion}" method="post">
					<input type="hidden" name="id" value="${editorial.id}" />
					<input type="hidden" name="operacion" value="${operacion}" />
					<div class="form-group">
						<label for="nombre" class="col-sm-10 control-label">Nombre:</label>
						<div class="col-sm-10">
							<input type=text class="form-control" id="nombre" name="nombre"
								placeholder="Nombre" required value="${editorial.nombre}"
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
							<a class="btn btn-danger" href="menu?accion=editoriales">Cancelar</a>
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>
	<div class="col-md-3"></div>
</div>