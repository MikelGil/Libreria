package com.ipartek.formacion.libreria.accessodatos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

import com.ipartek.formacion.libreria.biblioteca.Utils;
import com.ipartek.formacion.libreria.modelos.Usuario;
import com.mysql.cj.jdbc.CallableStatement;

public class UsuarioMySqlDAO implements CrudAble<Usuario> {

	private String urlBD;
	private String usuarioBD;
	private String passwordBD;

	private static UsuarioMySqlDAO INSTANCE = null;

	public static synchronized UsuarioMySqlDAO getInstance() throws ClassNotFoundException {
		if (INSTANCE == null) {
			Class.forName("com.mysql.cj.jdbc.Driver");
			INSTANCE = new UsuarioMySqlDAO();
		}

		Properties prop = Utils.leerPropiedades("libreria.properties");

		INSTANCE.urlBD = prop.getProperty("url");
		INSTANCE.usuarioBD = prop.getProperty("usuario");
		INSTANCE.passwordBD = prop.getProperty("password");

		return INSTANCE;
	}

	public Usuario getByName(String nombre, String password) {
		Usuario usuario = null;
		try (Connection conn = DriverManager.getConnection(urlBD, usuarioBD, passwordBD)) {
			String sql = "{call usuario_getByNombre(?, ?)}";
			try	(CallableStatement cst = (CallableStatement) conn.prepareCall(sql)){
				cst.setString(1, nombre);
				cst.setString(2, password);
				try (ResultSet rs = cst.executeQuery()){
					if (rs.next()) {
						usuario = new Usuario(rs.getLong("id"), rs.getString("nombre"), rs.getString("password"));
					} else {
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

		return usuario;
	}
	
	@Override
	public boolean insert(Usuario pojo) {
		throw new AccesoDatosException("NO IMPLEMENTADO");
	}

	@Override
	public List<Usuario> getAll() {
		throw new AccesoDatosException("NO IMPLEMENTADO");
	}

	@Override
	public Usuario getById(Long id) {
		throw new AccesoDatosException("NO IMPLEMENTADO");
	}

	@Override
	public boolean update(Usuario pojo) {
		throw new AccesoDatosException("NO IMPLEMENTADO");
	}

	@Override
	public boolean delete(Long id) {
		throw new AccesoDatosException("NO IMPLEMENTADO");
	}

}
