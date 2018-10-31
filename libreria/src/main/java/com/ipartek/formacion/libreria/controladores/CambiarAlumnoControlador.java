package com.ipartek.formacion.libreria.controladores;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ipartek.formacion.libreria.accessodatos.AlumnoMySqlDAO;
import com.ipartek.formacion.libreria.biblioteca.Utils;
import com.ipartek.formacion.libreria.modelos.Alumno;


@WebServlet("/cambiarAlumno")
public class CambiarAlumnoControlador extends HttpServlet {
	private static final long serialVersionUID = 1L;
   
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			response.setContentType("text/plain");
			response.setCharacterEncoding("UTF-8");
			
			PrintWriter out = response.getWriter();
			
			String alumnoId = request.getParameter("alumno");
			if (!"0".equals(alumnoId)) {
				AlumnoMySqlDAO dao = AlumnoMySqlDAO.getInstance();
				
				Alumno alumno = dao.getById(Utils.extraerId(alumnoId));
				
				out.println("<div class='form-group'>");
				out.println("<label for='nombre' class='col-sm-10 control-label'>Nombre:</label>");
				out.println("<div class='col-sm-10'>");
				out.println("<input type='text' class='form-control' id='nombre' name='nombre' placeholder='Nombre' value='"+ alumno.getNombre() +"' readonly>");
				out.println("</div></div>");
				out.println("<div class='form-group'>");
				out.println("<label for='apellido' class='col-sm-10 control-label'>Apellido:</label>");
				out.println("<div class='col-sm-10'>");
				out.println("<input type='text' class='form-control' id='apellido' name='apellido' placeholder='Apellido' value='"+alumno.getApellido()+"' readonly>");
				out.println("</div></div>");
				out.println("<div class='form-group'>");
				out.println("<label for='dni' class='col-sm-10 control-label'>DNI:</label>");
				out.println("<div class='col-sm-10'>");
				out.println("<input type='text' class='form-control' id='dni' name='dni' placeholder='DNI' value='"+alumno.getDni()+"' readonly>");
				out.println("</div></div>");
			}else {
				out.println("<div class='form-group'>");
				out.println("<label for='nombre' class='col-sm-10 control-label'>Nombre:</label>");
				out.println("<div class='col-sm-10'>");
				out.println("<input type='text' class='form-control' id='nombre' name='nombre' placeholder='Nombre' value='' readonly>");
				out.println("</div></div>");
				out.println("<div class='form-group'>");
				out.println("<label for='apellido' class='col-sm-10 control-label'>Apellido:</label>");
				out.println("<div class='col-sm-10'>");
				out.println("<input type='text' class='form-control' id='apellido' name='apellido' placeholder='Apellido' value='' readonly>");
				out.println("</div></div>");
				out.println("<div class='form-group'>");
				out.println("<label for='dni' class='col-sm-10 control-label'>DNI:</label>");
				out.println("<div class='col-sm-10'>");
				out.println("<input type='text' class='form-control' id='dni' name='dni' placeholder='DNI' value='' readonly>");
				out.println("</div></div>");
			}
		} catch (Exception e) {
			request.setAttribute("mensaje", "Error al cambiar los datos del alumno");
			request.setAttribute("mensajetipo", "danger");
			request.getRequestDispatcher("menu?accion=libros").forward(request, response);
			new ControladorException("Error al cambiar los datos del alumno: " + e.getMessage(), e).printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
