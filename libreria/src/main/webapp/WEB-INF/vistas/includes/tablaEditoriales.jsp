<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<div class="row text-center">
	<h2 class="text-active">Gestión editoriales</h2>
</div>

<div class="row">
	<div class="col-md-12">
		<div class="table-responsive">
			<table id="tablaEditoriales" name="tablaEditoriales" class="table table-sm table-striped table-light table-hover">
				<thead>
					<tr class="table-active">
						<th scope="col">Nombre</th>
						<th scope="col">
							<a href="gestionEditorial?operacion=nuevo" class="btn btn-primary"><i class="fas fa-plus"></i>&nbsp;Nuevo editorial</a>
						</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${editoriales}" var="editorial">
						<tr>
						<th>${editorial.nombre}</th>
						<td>
							<a href="gestionEditorial?operacion=editar&id=${editorial.id}"><i class="far fa-edit fa-lg"></i></a>
							<a href="gestionEditorial?operacion=borrar&id=${editorial.id}"><i class="far fa-trash-alt fa-lg"></i></a> 
						</td>
					</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
</div>