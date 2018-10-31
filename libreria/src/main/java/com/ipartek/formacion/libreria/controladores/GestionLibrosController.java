package com.ipartek.formacion.libreria.controladores;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ipartek.formacion.libreria.accessodatos.EditorialMySqlDAO;
import com.ipartek.formacion.libreria.accessodatos.LibroMySqlDAO;
import com.ipartek.formacion.libreria.accessodatos.PrestamoMySqlDAO;
import com.ipartek.formacion.libreria.biblioteca.Utils;
import com.ipartek.formacion.libreria.modelos.Editorial;
import com.ipartek.formacion.libreria.modelos.Libro;
import com.ipartek.formacion.libreria.modelos.Prestamo;

@WebServlet("/gestionLibro")
public class GestionLibrosController extends HttpServlet {
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
			
			List<Editorial> editoriales = EditorialMySqlDAO.getInstance().getAll();
		
			Long idL;
			Libro libro = null;
			
			switch (operacion) {
			case "nuevo":
				break;
			case "editar":
			case "borrar":
				idL = Utils.extraerId(id);
				libro = dao.getByIdAndPrestamo(idL);
				request.setAttribute("libro", libro);
				request.setAttribute("selectedEditorialId", libro.getEditorial().getId());
				break;
			default:
				throw new ControladorException("No se admite una peticion que no sea insertar, editar o borrar");
			}
			
			request.setAttribute("editoriales", editoriales);
			request.setAttribute("operacion", operacion);
			
			direccion = "/WEB-INF/vistas/gestionarLibro.jsp";
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
			String operacion = request.getParameter("operacion");
			String id = request.getParameter("id");
			
			if (operacion == null) {
				throw new ControladorException("No se admite una peticion que no tenga accion");
			}
			
			LibroMySqlDAO dao = LibroMySqlDAO.getInstance();
			
			String nombre = request.getParameter("nombre");
			String editorialS = request.getParameter("editorial");
			String isbn = request.getParameter("isbn");
			String prestamoS = request.getParameter("prestamo");
			
			Long idL;
			Long idLEditorial;
			Long idLPrestamo;
			
			Libro libro;
			Editorial editorial;
			Prestamo prestamo;
			
			switch (operacion) {
			case "nuevo":
				idLEditorial = Utils.extraerId(editorialS);
				
				editorial = new Editorial(idLEditorial, null);
				libro = new Libro(null, nombre, isbn, editorial, null);
				dao.insert(libro);
				
				mensaje = "Inserción correcta del libro " + libro.getNombre();
				mensajetipo = "info";
				break;
			case "editar":
				idL = Utils.extraerId(id);
				idLEditorial = Utils.extraerId(editorialS);
				
				try {
					idLPrestamo = Utils.extraerId(prestamoS);
					prestamo = PrestamoMySqlDAO.getInstance().getById(idLPrestamo);
				} catch (Exception e) {
					prestamo = null;
				}
					
				editorial = EditorialMySqlDAO.getInstance().getById(idLEditorial);
				
				libro = new Libro(idL, nombre, isbn, editorial, prestamo);
				
				dao.update(libro);
				
				mensaje = "Actualización correcta del libro " + libro.getNombre();
				mensajetipo = "info";
				break;
			case "borrar":
				idL = Utils.extraerId(id);
				
				dao.delete(idL);
				
				mensaje = "Borrado correcto del libro con id " + idL;
				mensajetipo = "info";
				break;
			default:
				throw new ControladorException("No se admite una peticion que no sea insertar, editar o borrar");
			}
			
			
			direccion = "menu?accion=libros";
		}catch(Exception e) {
			mensaje = "Error al procesar el formulario de libros";
			mensajetipo = "danger";
			direccion = "menu?accion=libros";
			new ControladorException("Error al procesar el formulario de libros: " + e.getMessage(), e).printStackTrace();
		}finally {
			request.setAttribute("mensaje", mensaje);
			request.setAttribute("mensajetipo", mensajetipo);
			request.getRequestDispatcher(direccion).forward(request, response);
		}
	}
}
