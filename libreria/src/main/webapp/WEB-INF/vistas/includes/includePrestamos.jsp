<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<div class="row">
	<div class="col-md-3"></div>
	<div class="col-md-6">
		<h2>Gestión de prestamo</h2>
		<div class="card">
			<div class="card-body">
				<form class="form" action="gestionPrestamo" method="post">
					<input type="hidden" name="id" value="${libro.id}" />
					<input type="hidden" name="editorialId" value="${libro.editorial.id}" />
					<div class="card">
						<h5 class="card-header">Libro</h5>
						<div class="card-body">
							<div class="form-group">
								<label for="nombre" class="col-sm-10 control-label">Título:</label>
								<div class="col-sm-10">
									<input type=text class="form-control" id="titulo" name="titulo"
										placeholder="Titulo" value="${libro.nombre}" readonly>
								</div>
							</div>
							<div class="form-group">
								<label for="editorial" class="col-sm-10 control-label">Editorial:</label>
								<div class="col-sm-10">
									<input type="text" class="form-control" id="editorial" name="editorial"
									placeholder="editorial" value="${libro.editorial.nombre}" readonly>
								</div>
							</div>
							<div class="form-group">
								<label for="isbn" class="col-sm-10 control-label">ISBN:</label>
								<div class="col-sm-10">
									<input type="text" class="form-control" id="isbn" name="isbn"
										placeholder="ISBN" pattern="[0-9]{13}" value="${libro.isbn}" readonly>
								</div>
							</div>
						</div>
					</div>

					<div class="card">
						<div class="card-header form-group row">
							<h5>Alumno:</h5>
							<select class="form-control offset-sm-1 col-sm-8" name='alumno' id="alumno" required>
								<option value="0">Alumno...</option>
							    <c:forEach items="${alumnos}" var="alumno">
							        <option value="${alumno.id}">${alumno.nombre} ${alumno.apellido}</option>
							    </c:forEach>
							</select>
						</div>
						<div id="alumno_card_body" class="card-body">
							<div class="form-group">
								<label for="nombre" class="col-sm-10 control-label">Nombre:</label>
								<div class="col-sm-10">
									<input type=text class="form-control" id="nombre" name="nombre"
										placeholder="Nombre" value="" readonly>
								</div>
							</div>
							<div class="form-group">
								<label for="apellido" class="col-sm-10 control-label">Apellido:</label>
								<div class="col-sm-10">
									<input type="text" class="form-control" id="apellido"
										name="apellido" placeholder="Apellido" value="" readonly>
								</div>
							</div>
							<div class="form-group">
								<label for="dni" class="col-sm-10 control-label">DNI:</label>
								<div class="col-sm-10">
									<input type="text" class="form-control" id="dni" name="dni"
										placeholder="DNI" value="" readonly>
								</div>
							</div>
						</div>
					</div>
					<div class="card">
						<div class="form-group">
							<div class="col-sm-12 text-center">
								<button type="submit" class="btn btn-success">Prestar</button>
								<a class="btn btn-danger" href="menu?accion=libros">Cancelar</a>
							</div>
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>
	<div class="col-md-3"></div>
</div>