package com.ipartek.formacion.libreria.accessodatos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.ipartek.formacion.libreria.biblioteca.Utils;
import com.ipartek.formacion.libreria.modelos.Alumno;
import com.ipartek.formacion.libreria.modelos.Editorial;
import com.ipartek.formacion.libreria.modelos.Libro;
import com.ipartek.formacion.libreria.modelos.Prestamo;
import com.mysql.cj.jdbc.CallableStatement;

public class LibroMySqlDAO implements CrudAble<Libro> {

	private String urlBD;
	private String usuarioBD;
	private String passwordBD;

	private static LibroMySqlDAO INSTANCE = null;

	public static synchronized LibroMySqlDAO getInstance() throws ClassNotFoundException {
		if (INSTANCE == null) {
			Class.forName("com.mysql.cj.jdbc.Driver");
			INSTANCE = new LibroMySqlDAO();
		}

		Properties prop = Utils.leerPropiedades("libreria.properties");

		INSTANCE.urlBD = prop.getProperty("url");
		INSTANCE.usuarioBD = prop.getProperty("usuario");
		INSTANCE.passwordBD = prop.getProperty("password");

		return INSTANCE;
	}

	
	@Override
	public boolean insert(Libro pojo) {
		try (Connection con = DriverManager.getConnection(urlBD, usuarioBD, passwordBD)) {
			String sql = "{call libros_insert(?, ?, ?)}";
			try (CallableStatement cst = (CallableStatement) con.prepareCall(sql)) {
				cst.setString(1, pojo.getNombre());
				cst.setString(2, pojo.getIsbn());
				cst.setLong(3, pojo.getEditorial().getId());

				int numFilas = cst.executeUpdate();

				if (numFilas != 1) {
					return false;
				}

			} catch (SQLException e) {
				throw new AccesoDatosException(e.getMessage(), e);
			}
		} catch (SQLException e) {
			throw new AccesoDatosException(e.getMessage(), e);
		}

		return true;
	}

	@Override
	public List<Libro> getAll() {
		Editorial editorial = null;
		ArrayList<Libro> libros = new ArrayList<>();

		try (Connection con = DriverManager.getConnection(urlBD, usuarioBD, passwordBD)) {
			String sql = "{call libros_getAll()}";
			try (CallableStatement cst = (CallableStatement) con.prepareCall(sql)) {

				try (ResultSet rs = cst.executeQuery()) {
					while (rs.next()) {
						editorial = new Editorial(rs.getLong("e.id"),
												rs.getString("e.editorial"));
						Libro libro = new Libro(rs.getLong("l.id"), 
								rs.getString("l.titulo"),
								rs.getString("l.ISBN"),
								editorial,
								null);
								
						libros.add(libro);
					}
				} catch (Exception e) {
					throw new AccesoDatosException(e.getMessage(), e);
				}
			} catch (SQLException e) {
				throw new AccesoDatosException(e.getMessage(), e);
			}

		} catch (SQLException e) {
			throw new AccesoDatosException(e.getMessage(), e);
		}

		return libros;
	}

	@Override
	public Libro getById(Long id) {
		Editorial editorial = null;
		Libro libro = null;

		try (Connection con = DriverManager.getConnection(urlBD, usuarioBD, passwordBD)) {
			String sql = "{call libros_getById(?)}";
			try (CallableStatement cst = (CallableStatement) con.prepareCall(sql)) {
				cst.setLong(1, id);
				try (ResultSet rs = cst.executeQuery()) {
					if (rs.next()) {
						editorial = new Editorial(rs.getLong("e.id"), rs.getString("e.editorial"));
						libro = new Libro(rs.getLong("l.id"), 
										rs.getString("l.titulo"),
										rs.getString("l.ISBN"),
										editorial,
										null);
					}else {
						return null;
					}
				} catch (SQLException e) {
					throw new AccesoDatosException(e.getMessage(), e);
				}
			} catch (Exception e) {
				throw new AccesoDatosException(e.getMessage(), e);
			}

		} catch (SQLException e) {
			throw new AccesoDatosException(e.getMessage(), e);
		}

		return libro;
	}

	@Override
	public boolean update(Libro pojo) {
		try (Connection con = DriverManager.getConnection(urlBD, usuarioBD, passwordBD)) {
			String sql = "{call libros_update(?, ?, ?, ?, ?)}";

			try (CallableStatement cst = (CallableStatement) con.prepareCall(sql)) {
				cst.setLong(1, pojo.getId());
				cst.setString(2, pojo.getNombre());
				cst.setString(3, pojo.getIsbn());
				cst.setLong(4, pojo.getEditorial().getId());
				if (pojo.getPrestamo() == null) {
					cst.setNull(5, java.sql.Types.INTEGER);
				}
				else {
					cst.setLong(5, pojo.getPrestamo().getId());
				}
				
				int numFilas = cst.executeUpdate();

				if (numFilas != 1) {
					return false;
				}

			} catch (SQLException e) {
				throw new AccesoDatosException(e.getMessage(), e);
			}
		} catch (SQLException e) {
			throw new AccesoDatosException(e.getMessage(), e);
		}

		return true;
	}

	@Override
	public boolean delete(Long id) {
		try (Connection con = DriverManager.getConnection(urlBD, usuarioBD, passwordBD)) {
			
			String sql = "{call libros_DeleteById(?)}";
			try (CallableStatement cst = (CallableStatement) con.prepareCall(sql)) {
				cst.setLong(1, id);

				int numFilas = cst.executeUpdate();

				if (numFilas != 1) {
					return false;
				}

			} catch (SQLException e) {
				throw new AccesoDatosException(e.getMessage(), e);
			}
		} catch (SQLException e) {
			throw new AccesoDatosException(e.getMessage(), e);
		}

		return true;
	}
	
	/*
	 * Funcion que obtiene todos los libros con los editoriales + prestamos + alumnos
	 * 
	 * */
	public List<Libro> getAllAndPrestamos() {
		ArrayList<Libro> libros = new ArrayList<>();

		try (Connection con = DriverManager.getConnection(urlBD, usuarioBD, passwordBD)) {
			String sql = "{call libros_getAllAndPrestamos()}";
			try (CallableStatement cst = (CallableStatement) con.prepareCall(sql)) {

				try (ResultSet rs = cst.executeQuery()) {
					while (rs.next()) {
						
						Alumno alumno = new Alumno(rs.getLong("a.id"),
								rs.getString("a.nombre"),
								rs.getString("a.apellido"),
								rs.getString("a.dni"));
						
						Editorial editorial = new Editorial(rs.getLong("e.id"),
												rs.getString("e.editorial"));
						
						Libro libro = new Libro(rs.getLong("l.id"), 
								rs.getString("l.titulo"),
								rs.getString("l.ISBN"),
								editorial,
								null);
						
						Prestamo prestamo = new Prestamo(rs.getLong("p.id"),
														rs.getDate("p.fecha_prestamo"),
														rs.getDate("p.fecha_devolucion"),
														alumno, 
														libro);
						
						libro.setPrestamo(prestamo);
								
						libros.add(libro);
						
						if (libro.getPrestamo().getFechaDevolucion() != null) {
							// Obtener los dias restantes del prestamo comparando la fecha de hoy y la fecha de devolucion
							prestamo.setDiasRestantes(libro.getPrestamo().getFechaDevolucion());
						}else {
							prestamo.diasRestantes = (long) 0;
						}
					}
				} catch (Exception e) {
					throw new AccesoDatosException(e.getMessage(), e);
				}
			} catch (SQLException e) {
				throw new AccesoDatosException(e.getMessage(), e);
			}

		} catch (SQLException e) {
			throw new AccesoDatosException(e.getMessage(), e);
		}

		return libros;
	}
	
	public Libro getByIdAndPrestamo(Long id) {
		Libro libro = null;
		
		try (Connection con = DriverManager.getConnection(urlBD, usuarioBD, passwordBD)) {
			String sql = "{call libros_getByIdAndPrestamo(?)}";
			try (CallableStatement cst = (CallableStatement) con.prepareCall(sql)) {
				cst.setLong(1, id);
				try (ResultSet rs = cst.executeQuery()) {
					if (rs.next()) {
						Editorial editorial = new Editorial(rs.getLong("e.id"), rs.getString("e.editorial"));
						Alumno alumno = new Alumno(rs.getLong("a.id"),
								rs.getString("a.nombre"),
								rs.getString("a.apellido"),
								rs.getString("a.dni"));
						
						libro = new Libro(rs.getLong("l.id"), 
										rs.getString("l.titulo"),
										rs.getString("l.ISBN"),
										editorial,
										null);
						
						Prestamo prestamo = new Prestamo(rs.getLong("p.id"),
								rs.getDate("p.fecha_prestamo"),
								rs.getDate("p.fecha_devolucion"),
								alumno, 
								libro);
						
						libro.setPrestamo(prestamo);
					}else {
						return null;
					}
				} catch (SQLException e) {
					throw new AccesoDatosException(e.getMessage(), e);
				}
			} catch (Exception e) {
				throw new AccesoDatosException(e.getMessage(), e);
			}

		} catch (SQLException e) {
			throw new AccesoDatosException(e.getMessage(), e);
		}

		return libro;
	}
}
