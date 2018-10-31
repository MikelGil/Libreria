package com.ipartek.formacion.libreria.controladores;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ipartek.formacion.libreria.accessodatos.AlumnoMySqlDAO;
import com.ipartek.formacion.libreria.accessodatos.EditorialMySqlDAO;
import com.ipartek.formacion.libreria.accessodatos.LibroMySqlDAO;
import com.ipartek.formacion.libreria.accessodatos.UsuarioMySqlDAO;
import com.ipartek.formacion.libreria.modelos.Alumno;
import com.ipartek.formacion.libreria.modelos.Editorial;
import com.ipartek.formacion.libreria.modelos.Libro;
import com.ipartek.formacion.libreria.modelos.Usuario;

@WebServlet("/menu")
public class MenuController extends HttpServlet {
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
			String accion = request.getParameter("accion");

			if (accion == null) {
				mensaje = "Error inesperado";
				mensajetipo = "danger";
				direccion = "/";
				
			}else {
				switch (accion) {
				case "alumnos":
					cargarAlumnos(request);
					direccion = "/WEB-INF/vistas/listadoAlumnos.jsp";
					break;
				case "editoriales":
					cargarEditoriales(request);
					direccion = "/WEB-INF/vistas/listadoEditoriales.jsp";
					break;
				case "libros":
				default:
					cargarLibros(request);
					direccion = "/WEB-INF/vistas/listadoLibros.jsp";
					break;
				}
			}

		} catch (Exception e) {
			mensaje = "Error al redirigir en el menu";
			mensajetipo = "danger";
			direccion = "/";
			new ControladorException("Error al redirigir en el menu: " + e.getMessage(), e).printStackTrace();
		}finally {
			request.setAttribute("mensaje", mensaje);
			request.setAttribute("mensajetipo", mensajetipo);
			request.getRequestDispatcher(direccion).forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try{
			String accion = request.getParameter("accion");
			if ("login".equals(accion)) {
				comprobarLogin(request, response);
			}
			else {
				doGet(request, response);
			}
		}catch (Exception e) {
			request.setAttribute("mensaje", "Error al redirigir en el menu");
			request.setAttribute("mensajetipo", "danger");
			request.getRequestDispatcher("/").forward(request, response);
			new ControladorException("Error al redirigir en el menu: " + e.getMessage(), e).printStackTrace();
		}
	}

	private void comprobarLogin(HttpServletRequest request, HttpServletResponse response) {
		try {
			String nombre = request.getParameter("nombre");
			String password = request.getParameter("password");

			Usuario usuario = UsuarioMySqlDAO.getInstance().getByName(nombre, password);
			
			String mensaje = null;
			String mensajeTipo = null;
			String direccion = null;

			if (usuario != null) {
				cargarLibros(request);
				request.getSession().setAttribute("usuario", usuario);
				direccion = "/WEB-INF/vistas/listadoLibros.jsp";
			} else {
				mensaje = "El usuario o la contraseña son incorrectos";
				mensajeTipo = "warning";
				direccion = "/";
				
			}
			
			request.setAttribute("alertatipo", mensajeTipo);
			request.setAttribute("alertatexto", mensaje);
			request.getRequestDispatcher(direccion).forward(request, response);
		} catch (Exception e) {
			throw new ControladorException("Error en el login " + e.getMessage(), e);
		}
	}
	
	private void cargarLibros(HttpServletRequest request) {
		try {
			List<Libro> libros = LibroMySqlDAO.getInstance().getAllAndPrestamos();
		
			request.setAttribute("libros", libros);
		}catch(Exception e) {
			throw new ControladorException("Error al cargar la lista de libros" + e.getMessage(), e);
		}
	}
	
	private void cargarEditoriales(HttpServletRequest request) {
		try {
			List<Editorial> editoriales = EditorialMySqlDAO.getInstance().getAll();
			
			request.setAttribute("editoriales", editoriales);
		}catch(Exception e) {
			throw new ControladorException("Error al cargar la lista de editoriales" + e.getMessage(), e);
		}
	}
	
	private void cargarAlumnos(HttpServletRequest request) {
		try {
			List<Alumno> alumnos = AlumnoMySqlDAO.getInstance().getAll();
			
			request.setAttribute("alumnos", alumnos);
		}catch(Exception e) {
			throw new ControladorException("Error al cargar la lista de alumnos" + e.getMessage(), e);
		}
	}
}
