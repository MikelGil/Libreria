package com.ipartek.formacion.libreria.controladores;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ipartek.formacion.libreria.accessodatos.AlumnoMySqlDAO;
import com.ipartek.formacion.libreria.accessodatos.LibroMySqlDAO;
import com.ipartek.formacion.libreria.accessodatos.PrestamoMySqlDAO;
import com.ipartek.formacion.libreria.biblioteca.Utils;
import com.ipartek.formacion.libreria.modelos.Alumno;
import com.ipartek.formacion.libreria.modelos.Editorial;
import com.ipartek.formacion.libreria.modelos.Libro;
import com.ipartek.formacion.libreria.modelos.Prestamo;

@WebServlet("/gestionPrestamo")
public class GestionPrestamoController extends HttpServlet {
	private static final long serialVersionUID = 1L;
 
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String mensaje = null;
		String mensajetipo = null;
		String direccion = null;
		
		if (request.getAttribute("mensaje") != null) {
			mensaje =  (String) request.getAttribute("mensaje");
			mensajetipo = (String) request.getAttribute("mensajetipo");
		}
		
		try {
			request.setCharacterEncoding("UTF-8");
			
			String operacion = request.getParameter("operacion");
			String id = request.getParameter("id");
			
			if (operacion == null) {
				throw new ControladorException("No se admite una peticion que no tenga accion");
			}
			LibroMySqlDAO dao = LibroMySqlDAO.getInstance();
			PrestamoMySqlDAO daop = PrestamoMySqlDAO.getInstance();
			
			Long idL = Utils.extraerId(id);
			Libro libro = dao.getByIdAndPrestamo(idL);
			
			request.setAttribute("libro", libro);
			
			switch (operacion) {
			case "prestamo":
				List<Alumno> alumnos = AlumnoMySqlDAO.getInstance().getAll();
				//List<Alumno> alumnos = AlumnoMySqlDAO.getInstance().getAllWithoutPrestamo();
				
				request.setAttribute("alumnos", alumnos);
				direccion = "/WEB-INF/vistas/gestionarPrestamo.jsp";
				break;
			case "ampliar":
				long ltime = libro.getPrestamo().getFechaDevolucion().getTime() + 7*24*60*60*1000;
				Date modifiedDate = new Date(ltime);
				
				libro.getPrestamo().setFechaDevolucion(modifiedDate);

				Prestamo prestamo = new Prestamo(libro.getPrestamo().getId(), libro.getPrestamo().getFechaPrestamo(), libro.getPrestamo().getFechaDevolucion(), libro.getPrestamo().getAlumno(), libro);
				daop.update(prestamo);
				
				mensaje = "Ampliacion del prestamo del libro " + libro.getNombre() + " para la persona " + libro.getPrestamo().getAlumno().getNombre() + " ampliada correctamente";
				mensajetipo = "info";
				direccion = "menu?accion=libros";
				break;
			case "devolver":
				Prestamo prestamoDevolver = new Prestamo(libro.getPrestamo().getId(), libro.getPrestamo().getFechaPrestamo(), new Date(), libro.getPrestamo().getAlumno(), libro);
				daop.update(prestamoDevolver);
				
				libro.setPrestamo(null);
				dao.update(libro);
				
				mensaje = "Libro devuelto con exito: " + libro.getNombre();
				mensajetipo = "info";
				direccion = "menu?accion=libros";
				break;
			default:
				throw new ControladorException("No se admite una peticion que no sea prestamo, ampliar o devolver");
			}
		}catch (Exception e) {
			mensaje = "Error al redirigir al menu de gestionar libro";
			mensajetipo = "danger";
			direccion = "menu?accion=libros";
			new ControladorException("Error al redirigir al menu de gestionar libro: " + e.getMessage(), e).printStackTrace();
		}finally {
			request.setAttribute("mensaje", mensaje);
			request.setAttribute("mensajetipo", mensajetipo);
			request.getRequestDispatcher(direccion).forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String mensaje = null;
		String mensajetipo = null;
		String direccion = null;
		
		if (request.getAttribute("mensaje") != null) {
			mensaje =  (String) request.getAttribute("mensaje");
			mensajetipo = (String) request.getAttribute("mensajetipo");
		}
		
		try {
			request.setCharacterEncoding("UTF-8");
			
			String idLibro = request.getParameter("id");
			String idEditorial = request.getParameter("editorialId");
			
			String titulo = request.getParameter("titulo");
			String editorialS = request.getParameter("editorial");
			String isbn = request.getParameter("isbn");
			
			String alumnoId = request.getParameter("alumno");
			String nombre = request.getParameter("nombre");
			String apellido = request.getParameter("apellido");
			String dni = request.getParameter("dni");
			
			if (!"0".equals(alumnoId)) {
				Editorial editorial = new Editorial (Utils.extraerId(idEditorial), editorialS);
				Alumno alumno = new Alumno(Utils.extraerId(alumnoId), nombre, apellido, dni);
				Libro libro = new Libro(Utils.extraerId(idLibro), titulo, isbn, editorial, null);
				
				long ltime = new Date().getTime() + 14*24*60*60*1000;
				Date fechaDevolucion = new Date(ltime);
				
				Prestamo prestamo = new Prestamo(null,new Date(), fechaDevolucion, alumno, libro);

				LibroMySqlDAO dao = LibroMySqlDAO.getInstance();
				PrestamoMySqlDAO daop = PrestamoMySqlDAO.getInstance();
				
				daop.insert(prestamo);
				long idPrestamoGenerada = daop.getLastId();
				prestamo.setId(idPrestamoGenerada);
				libro.setPrestamo(prestamo);
				dao.update(libro);
				
				mensaje = "Libro " + libro.getNombre() + "prestado con exito a " + libro.getPrestamo().getAlumno().getNombre();
				mensajetipo = "info";
			}else {
				mensaje = "No se puede prestar un libro al aire";
				mensajetipo = "danger";
			}	
			
			direccion = "menu?accion=libros";
		}catch(Exception e) {
			mensaje = "Error al procesar el formulario de prestar libros";
			mensajetipo = "danger";
			direccion = "menu?accion=libros";
			new ControladorException("Error al procesar el formulario de prestar libros: " + e.getMessage(), e).printStackTrace();
		}finally {
			request.setAttribute("mensaje", mensaje);
			request.setAttribute("mensajetipo", mensajetipo);
			request.getRequestDispatcher(direccion).forward(request, response);
		}
	}
}
