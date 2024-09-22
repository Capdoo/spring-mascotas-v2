package com.mascotas.app.utils;

public class StringUtil {
	
	private String data;

	public StringUtil() {
		super();
	}
	
	//ParaMascotas
	public String obtenerEspecieToken(String concatenado) {
		String[] datos = concatenado.split("#");
		return datos[0];
	}
	
	public String obtenerRazaToken(String concatenado) {
		String[] datos = concatenado.split("#");
		return datos[1];
	}
}