<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<div class="row">
	<div class="col-md-3"></div>
	<div class="col-md-6">
		<h2 class="text-success">
			<c:choose>
				<c:when test="${operacion == 'nuevo'}">Inserción de nuevo libro</c:when>
				<c:when test="${operacion == 'editar'}">Modificación de libro</c:when>
				<c:when test="${operacion == 'borrar'}">Borrado de libro</c:when>
			</c:choose>
		</h2>
		<div class="card border-success">
			<div class="card-body">
				<form class="form" action="gestionLibro?operacion=${operacion}" method="post">
					<input type="hidden" name="id" value="${libro.id}" />
					<input type="hidden" name="operacion" value="${operacion}" />
					<input type="hidden" name="prestamo" value="${libro.prestamo.id}" />
					<div class="form-group">
						<c:if test="${libro.prestamo.id ne 0 && operacion == 'borrar'}">
							<span class="text-danger">*El libro que vas a borrar está prestado a ${libro.prestamo.alumno.nombre} ${libro.prestamo.alumno.apellido}</span>
							<br />
						</c:if>
						
						<label for="nombre" class="col-sm-10 control-label">Nombre:</label>
						<div class="col-sm-10">
							<input type=text class="form-control" id="nombre" name="nombre"
								placeholder="Nombre" required value="${libro.nombre}" 
								<c:if test="${operacion == 'borrar'}">disabled="disabled"</c:if>>
						</div>
					</div>
					<div class="form-group">
						<label for="editorial" class="col-sm-10 control-label">Editorial:</label>
						<div class="col-sm-10">
							<select class="form-control" name='editorial' required <c:if test="${operacion == 'borrar'}">disabled="disabled"</c:if>>
							    <c:forEach items="${editoriales}" var="editorial">
							        <option value="${editorial.id}" ${editorial.id == selectedEditorialId ? 'selected' : ''}>${editorial.nombre}</option>
							    </c:forEach>
							</select>
						</div>
					</div>
					<div class="form-group">
						<label for="ISBN" class="col-sm-10 control-label">ISBN:</label>
						<div class="col-sm-10">
							<input type=text class="form-control" id="isnb" name="isbn"
								placeholder="ISBN" pattern="[0-9]{13}" required value="${libro.isbn}"
								<c:if test="${operacion=='borrar'}">disabled="disabled"</c:if>>
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
							<a class="btn btn-danger" href="menu?accion=libros">Cancelar</a>
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>
	<div class="col-md-3"></div>
</div>