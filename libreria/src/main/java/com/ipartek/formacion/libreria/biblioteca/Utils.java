package com.ipartek.formacion.libreria.biblioteca;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.ipartek.formacion.libreria.controladores.ControladorException;

public class Utils {

	public static Properties leerPropiedades(String filename) {
		try {
			
			InputStream input = Utils.class.getClassLoader().getResourceAsStream(filename);
			
			if(input==null){
				throw new BibliotecaException("No se ha podido leer el fichero " + filename);
			}

			//load a properties file from class path, inside static method
			Properties prop = new Properties();
			
			prop.load(input);

			return prop;
		} catch (IOException e) {
			throw new BibliotecaException("No se ha podido leer el fichero", e);
		}
	}

	public static long extraerId(String id) {
		long idLong;
		
		if (id == null) {
			throw new ControladorException("Necesito un id");
		}
		
		try {
			idLong = Long.parseLong(id);
		} catch (NumberFormatException e) {
			throw new ControladorException("El id no era numérico", e);
		}
		return idLong;
	}
}