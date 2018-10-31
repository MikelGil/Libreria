<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
<div class="row text-center">
	<h2 class="text-warning">Gestión alumnos</h2>
</div>
<div class="row">
	<div class="col-md-12">
		<div class="table-responsive">
			<table id="tablaAlumnos" name="tablaAlumnos" class="table table-sm table-striped table-light table-hover">
				<thead>
					<tr class="table-warning">
						<th scope="col">Nombre</th>
						<th scope="col">Apellido</th>
						<th scope="col">DNI</th>
						<th scope="col"><a href="gestionAlumno?operacion=nuevo" class="btn btn-primary"><i class="fas fa-plus"></i>&nbsp;Nuevo alumno</a></th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${alumnos}" var="alumno">
						<tr>
						<th>${alumno.nombre}</th>
						<td>${alumno.apellido}</td>
						<td>${alumno.dni}</td>
						<td>
							<a href="gestionAlumno?operacion=editar&id=${alumno.id}"><i class="far fa-edit"></i></a>
							<a href="gestionAlumno?operacion=borrar&id=${alumno.id}"><i class="far fa-trash-alt"></i></a> 
						</td>
					</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
</div>