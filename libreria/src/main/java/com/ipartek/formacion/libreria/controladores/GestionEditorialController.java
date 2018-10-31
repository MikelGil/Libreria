package com.ipartek.formacion.libreria.controladores;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ipartek.formacion.libreria.accessodatos.EditorialMySqlDAO;
import com.ipartek.formacion.libreria.biblioteca.Utils;
import com.ipartek.formacion.libreria.modelos.Editorial;

@WebServlet("/gestionEditorial")
public class GestionEditorialController extends HttpServlet {
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
			
			EditorialMySqlDAO dao = EditorialMySqlDAO.getInstance();
			
			Long idL;
			Editorial editorial = null;
			
			switch (operacion) {
			case "nuevo":
				break;
			case "editar":
			case "borrar":
				idL = Utils.extraerId(id);
				editorial = dao.getById(idL);
				request.setAttribute("editorial", editorial);
				break;
			default:
				throw new ControladorException("No se admite una peticion que no sea insertar, editar o borrar");
			}
			
			request.setAttribute("operacion", operacion);
			direccion = "/WEB-INF/vistas/gestionarEditorial.jsp";
			
		}catch (Exception e) {
			mensaje =  "Error al redirigir al menu de gestionar editorial";
			mensajetipo = "danger";
			direccion = "menu?accion=editoriales";
			new ControladorException("Error al redirigir al menu de gestionar editorial: " + e.getMessage(), e).printStackTrace();
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
			
			EditorialMySqlDAO dao = EditorialMySqlDAO.getInstance();
			
			String nombre = request.getParameter("nombre");

			Long idL;
			
			Editorial editorial;
			
			switch (operacion) {
			case "nuevo":
				editorial = new Editorial(null, nombre);
				dao.insert(editorial);
				
				mensaje = "Inserción correcta del editorial " + editorial.getNombre();
				mensajetipo = "info";
				break;
			case "editar":
				idL = Utils.extraerId(id);
				
				editorial = new Editorial(idL, nombre);
				
				dao.update(editorial);
				
				mensaje = "Actualización correcta del editorial " + editorial.getNombre();
				mensajetipo = "info";
				break;
			case "borrar":
				idL = Utils.extraerId(id);
				
				dao.delete(idL);

				mensaje = "Borrado correcto del editorial con id " + idL;
				mensajetipo = "info";
				break;
			default:
				throw new ControladorException("No se admite una peticion que no sea insertar, editar o borrar");
			}
			
			direccion = "menu?accion=editoriales";
		}catch(Exception e) {
			mensaje = "Error al procesar el formulario de editoriales";
			mensajetipo = "danger";
			direccion = "menu?accion=editoriales";
			new ControladorException("Error al procesar el formulario de editorialesl: " + e.getMessage(), e).printStackTrace();
		}finally {
			request.setAttribute("mensaje", mensaje);
			request.setAttribute("mensajetipo", mensajetipo);
			request.getRequestDispatcher(direccion).forward(request, response);
		}
	}
}
