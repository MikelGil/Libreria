<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<div class="row text-center">
	<h2 class="text-success">Gestión libros y préstamos</h2>
</div>
<div class="row">
	<div class="col-md-12">
		<div class="table-responsive">
			<table id="tablaLibros" name="tablaLibros"
				class="table table-sm table-striped table-light table-hover">
				<thead>
					<tr class="table-success">
						<th scope="col">Libro</th>
						<th scope="col">Editorial</th>
						<th scope="col">ISBN</th>
						<th scope="col">Desde</th>
						<th scope="col">Dias</th>
						<th scope="col">Alumno</th>
						<th scope="col">Apellido</th>
						<th scope="col">Prestamo</th>
						<th scope="col">
							<a href="gestionLibro?operacion=nuevo" class="btn btn-primary">
								<i class="fas fa-plus"></i>&nbsp;Nuevo libro
							</a>
						</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${libros}" var="libro">
						<tr>
							<td>${libro.nombre}</td>
							<td>${libro.editorial.nombre}</td>
							<td>${libro.isbn}</td>
							<td>${libro.prestamo.fechaPrestamo}</td>
							<td><c:choose>
									<c:when test="${libro.prestamo.diasRestantes <= 0 && libro.prestamo.fechaPrestamo ne null}">
										<i class="fas fa-exclamation-triangle"></i>&nbsp;${libro.prestamo.diasRestantes}
									</c:when>
									<c:when test="${libro.prestamo.diasRestantes > 0}">
									${libro.prestamo.diasRestantes}
									</c:when>
								</c:choose></td>
							<td>${libro.prestamo.alumno.nombre}</td>
							<td>${libro.prestamo.alumno.apellido}</td>
							<td><c:choose>
									<c:when test="${libro.prestamo.fechaPrestamo eq null}">
										<a href="gestionPrestamo?operacion=prestamo&id=${libro.id}"
											class="btn btn-primary">
											<i class="fab fa-leanpub"></i>&nbsp;Prestar</a>
									</c:when>
									<c:otherwise>
										<a href="gestionPrestamo?operacion=devolver&id=${libro.id}"
											class="btn btn-danger"> <i class="fas fa-undo"></i>&nbsp;Devolver
										</a>
										<a href="gestionPrestamo?operacion=ampliar&id=${libro.id}"
											class="btn btn-success">+7</a>
									</c:otherwise>
								</c:choose></td>
							<td><a href="gestionLibro?operacion=editar&id=${libro.id}">
									<i class="far fa-edit fa-lg"></i>
							</a> <a href="gestionLibro?operacion=borrar&id=${libro.id}"> <i
									class="far fa-trash-alt fa-lg"></i>
							</a></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
</div>