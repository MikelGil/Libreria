package com.ipartek.formacion.libreria.controladores;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ipartek.formacion.libreria.accessodatos.AlumnoMySqlDAO;
import com.ipartek.formacion.libreria.biblioteca.Utils;
import com.ipartek.formacion.libreria.modelos.Alumno;

@WebServlet("/gestionAlumno")
public class GestionAlumnosController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
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

			AlumnoMySqlDAO dao = AlumnoMySqlDAO.getInstance();

			Long idL;
			Alumno alumno = null;

			switch (operacion) {
			case "nuevo":
				break;
			case "editar":
			case "borrar":
				idL = Utils.extraerId(id);
				alumno = dao.getById(idL);
				request.setAttribute("alumno", alumno);
				break;
			default:
				throw new ControladorException("No se admite una peticion que no sea insertar, editar o borrar");
			}

			request.setAttribute("operacion", operacion);
			direccion = "/WEB-INF/vistas/gestionarAlumno.jsp";
		} catch (Exception e) {
			mensaje = "Error al redirigir al menu de gestionar alumno";
			mensajetipo = "danger";
			direccion = "menu?accion=alumnos";
			new ControladorException("Error al redirigir al menu de gestionar alumno: " + e.getMessage(), e)
					.printStackTrace();
		} finally {
			request.setAttribute("mensaje", mensaje);
			request.setAttribute("mensajetipo", mensajetipo);
			request.getRequestDispatcher(direccion).forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
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

			AlumnoMySqlDAO dao = AlumnoMySqlDAO.getInstance();

			String nombre = request.getParameter("nombre");
			String apellido = request.getParameter("apellido");
			String dni = request.getParameter("dni");
			Long idL;

			Alumno alumno;

			switch (operacion) {
			case "nuevo":
				alumno = new Alumno(null, nombre, apellido, dni);
				dao.insert(alumno);

				mensaje = "Inserción correcta del alumno " + alumno.getNombre();
				mensajetipo = "info";
				break;
			case "editar":
				idL = Utils.extraerId(id);

				alumno = new Alumno(idL, nombre, apellido, dni);

				dao.update(alumno);

				mensaje = "Actualización correcta del alumno " + alumno.getNombre();
				mensajetipo = "info";
				break;
			case "borrar":
				idL = Utils.extraerId(id);
				dao.obtenerPrestamos(idL);
				if (dao.obtenerPrestamos(idL) == 0) {
					dao.delete(idL);
					mensaje = "Borrado correcto del alumno con id " + idL;
					mensajetipo = "info";
				} else {
					mensaje = "No se puede borrar un alumno con libro prestado";
					mensajetipo = "danger";
				}

				break;
			default:
				throw new ControladorException("No se admite una peticion que no sea insertar, editar o borrar");
			}

			direccion = "menu?accion=alumnos";

		} catch (Exception e) {
			mensaje = "Error al procesar el formulario de alumnos";
			mensajetipo = "danger";
			direccion = "menu?accion=alumnos";
			new ControladorException("Error al procesar el formulario de alumnos: " + e.getMessage(), e)
					.printStackTrace();
		}finally {
			request.setAttribute("mensaje", mensaje);
			request.setAttribute("mensajetipo", mensajetipo);
			request.getRequestDispatcher(direccion).forward(request, response);
		}
	}
}
