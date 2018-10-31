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

public class PrestamoMySqlDAO implements CrudAble<Prestamo> {

	private String urlBD;
	private String usuarioBD;
	private String passwordBD;

	private static PrestamoMySqlDAO INSTANCE = null;

	public static synchronized PrestamoMySqlDAO getInstance() throws ClassNotFoundException {
		if (INSTANCE == null) {
			Class.forName("com.mysql.cj.jdbc.Driver");
			INSTANCE = new PrestamoMySqlDAO();
		}

		Properties prop = Utils.leerPropiedades("libreria.properties");

		INSTANCE.urlBD = prop.getProperty("url");
		INSTANCE.usuarioBD = prop.getProperty("usuario");
		INSTANCE.passwordBD = prop.getProperty("password");

		return INSTANCE;
	}

	
	@Override
	public boolean insert(Prestamo pojo) {
		try (Connection con = DriverManager.getConnection(urlBD, usuarioBD, passwordBD)) {
			String sql = "{call prestamos_insert(?, ?, ?, ?)}";
			try (CallableStatement cst = (CallableStatement) con.prepareCall(sql)) {
				cst.setLong(1, pojo.getAlumno().getId());
				cst.setLong(2, pojo.getLibro().getId());
				cst.setTimestamp(3, new java.sql.Timestamp(pojo.getFechaPrestamo().getTime()));
				cst.setTimestamp(4, new java.sql.Timestamp(pojo.getFechaDevolucion().getTime()));

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
	public List<Prestamo> getAll() {
		ArrayList<Prestamo> prestamos = new ArrayList<>();

		try (Connection con = DriverManager.getConnection(urlBD, usuarioBD, passwordBD)) {
			String sql = "{call prestamos_getAll()}";
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
								
						prestamos.add(prestamo);
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

		return prestamos;
	}

	@Override
	public Prestamo getById(Long id) {
		Prestamo prestamo = null;

		try (Connection con = DriverManager.getConnection(urlBD, usuarioBD, passwordBD)) {
			String sql = "{call prestamos_getById(?)}";
			try (CallableStatement cst = (CallableStatement) con.prepareCall(sql)) {
				cst.setLong(1, id);
				try (ResultSet rs = cst.executeQuery()) {
					if (rs.next()) {
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
						
						prestamo = new Prestamo(rs.getLong("p.id"),
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

		return prestamo;
	}

	@Override
	public boolean update(Prestamo pojo) {
		try (Connection con = DriverManager.getConnection(urlBD, usuarioBD, passwordBD)) {
			String sql = "{call prestamos_update(?, ?, ?, ?, ?)}";

			try (CallableStatement cst = (CallableStatement) con.prepareCall(sql)) {
				cst.setLong(1, pojo.getId());
				cst.setLong(2, pojo.getAlumno().getId());
				cst.setLong(3, pojo.getLibro().getId());
				cst.setTimestamp(4, new java.sql.Timestamp(pojo.getFechaPrestamo().getTime()));
				cst.setTimestamp(5, new java.sql.Timestamp(pojo.getFechaDevolucion().getTime()));
				
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
			
			String sql = "{call prestamos_DeleteById(?)}";
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
	
	public Long getLastId() {
		long autoIncKeyFromApi = -1;
		try (Connection con = DriverManager.getConnection(urlBD, usuarioBD, passwordBD)) {
			String sql = "{call prestamos_last_id()}";
			try (CallableStatement cst = (CallableStatement) con.prepareCall(sql)) {
				try (ResultSet rs = cst.executeQuery()) {
					if (rs.next()) {
						autoIncKeyFromApi = rs.getLong("MAX(id)");
					}
				}
			} catch (SQLException e) {
				throw new AccesoDatosException(e.getMessage(), e);
			}
		} catch (SQLException e) {
			throw new AccesoDatosException(e.getMessage(), e);
		}
		return autoIncKeyFromApi;
	}
}
