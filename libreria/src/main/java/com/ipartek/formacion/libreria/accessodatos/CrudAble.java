package com.ipartek.formacion.libreria.accessodatos;

import java.util.List;

/**
 * Interfaz para especificar los metodos de <b>CRUD</b>:
 * <ul>
 * <li>Create</li>
 * <li>Read</li>
 * <li>Update</li>
 * <li>Delete</li>
 * </ul>
 * 
 * @author Mikel
 *
 */
public interface CrudAble<P> {

	// Create
	boolean insert(P pojo);

	// Read
	/**
	 * Recupera todos los pojo
	 * 
	 * @return si no existe resultados retorna Lista vacia, no null
	 */
	List<P> getAll();

	/**
	 * Buscamos un pojo por su identificador
	 * 
	 * @param id
	 */
	P getById(Long id);

	// Upadte
	boolean update(P pojo);

	// Delete
	boolean delete(Long id);
}
