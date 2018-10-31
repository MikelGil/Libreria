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
import com.mysql.cj.jdbc.CallableStatement;

public class AlumnoMySqlDAO implements CrudAble<Alumno> {

	private String urlBD;
	private String usuarioBD;
	private String passwordBD;

	private static AlumnoMySqlDAO INSTANCE = null;

	public static synchronized AlumnoMySqlDAO getInstance() throws ClassNotFoundException {
		if (INSTANCE == null) {
			Class.forName("com.mysql.cj.jdbc.Driver");
			INSTANCE = new AlumnoMySqlDAO();
		}

		Properties prop = Utils.leerPropiedades("libreria.properties");

		INSTANCE.urlBD = prop.getProperty("url");
		INSTANCE.usuarioBD = prop.getProperty("usuario");
		INSTANCE.passwordBD = prop.getProperty("password");

		return INSTANCE;
	}

	@Override
	public boolean insert(Alumno pojo) {
		try (Connection con = DriverManager.getConnection(urlBD, usuarioBD, passwordBD)) {
			String sql = "{call alumnos_insert(?, ?, ?)}";
			try (CallableStatement cst = (CallableStatement) con.prepareCall(sql)) {
				cst.setString(1, pojo.getNombre());
				cst.setString(2, pojo.getApellido());
				cst.setString(3, pojo.getDni());

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
	public List<Alumno> getAll() {
		ArrayList<Alumno> alumnos = new ArrayList<>();

		try (Connection con = DriverManager.getConnection(urlBD, usuarioBD, passwordBD)) {
			String sql = "{call alumnos_getAll()}";
			try (CallableStatement cst = (CallableStatement) con.prepareCall(sql)) {

				try (ResultSet rs = cst.executeQuery()) {
					while (rs.next()) {
						Alumno alumno = new Alumno(rs.getLong("id"), 
								rs.getString("nombre"),
								rs.getString("apellido"),
								rs.getString("dni"));
								
						alumnos.add(alumno);
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

		return alumnos;
	}

	@Override
	public Alumno getById(Long id) {
		Alumno alumno = null;

		try (Connection con = DriverManager.getConnection(urlBD, usuarioBD, passwordBD)) {
			String sql = "{call alumnos_getById(?)}";
			try (CallableStatement cst = (CallableStatement) con.prepareCall(sql)) {
				cst.setLong(1, id);
				try (ResultSet rs = cst.executeQuery()) {
					if (rs.next()) {
						alumno = new Alumno(rs.getLong("id"), 
										rs.getString("nombre"),
										rs.getString("apellido"),
										rs.getString("dni"));
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

		return alumno;
	}

	@Override
	public boolean update(Alumno pojo) {
		try (Connection con = DriverManager.getConnection(urlBD, usuarioBD, passwordBD)) {
			String sql = "{call alumnos_update(?, ?, ?, ?)}";

			try (CallableStatement cst = (CallableStatement) con.prepareCall(sql)) {
				cst.setLong(1, pojo.getId());
				cst.setString(2, pojo.getNombre());
				cst.setString(3, pojo.getApellido());
				cst.setString(4, pojo.getDni());

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

			String sql = "{call alumnos_DeleteById(?)}";
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
	
	public Long obtenerPrestamos(Long id) {
		Long resultado = (long) 0;
		try (Connection con = DriverManager.getConnection(urlBD, usuarioBD, passwordBD)) {
			String sql = "{call alumnos_countPrestamos(?)}";

			try (CallableStatement cst = (CallableStatement) con.prepareCall(sql)) {
				cst.setLong(1, id);

				try (ResultSet rs = cst.executeQuery()){
					if (rs.next()) {
						resultado = rs.getLong("COUNT(*)");
					}
				}catch (SQLException e) {
					throw new AccesoDatosException(e.getMessage(), e);
				}

			} catch (SQLException e) {
				throw new AccesoDatosException(e.getMessage(), e);
			}
		} catch (SQLException e) {
			throw new AccesoDatosException(e.getMessage(), e);
		}

		return resultado;
	}
	
	public List<Alumno> getAllWithoutPrestamo() {
		ArrayList<Alumno> alumnos = new ArrayList<>();

		try (Connection con = DriverManager.getConnection(urlBD, usuarioBD, passwordBD)) {
			String sql = "{call alumnos_getAllWithoutPrestamo()}";
			try (CallableStatement cst = (CallableStatement) con.prepareCall(sql)) {

				try (ResultSet rs = cst.executeQuery()) {
					while (rs.next()) {
						Alumno alumno = new Alumno(rs.getLong("id"), 
								rs.getString("nombre"),
								rs.getString("apellido"),
								rs.getString("dni"));
								
						alumnos.add(alumno);
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

		return alumnos;
	}
}