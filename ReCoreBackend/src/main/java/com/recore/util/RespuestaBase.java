package com.recore.util;

/*
* Módulo 3 - APIs
* Clase 7 - API REST - Parte 2
* Response Entity - Clase base para estandarizar respuestas
*/
public class RespuestaBase<T> {
	private boolean exito;
	private String mensaje;
	private T datos;

	public RespuestaBase() {
		this.exito = true;
	}

	public RespuestaBase(T datos) {
		this.exito = datos != null;
		this.datos = datos;
	}

	public RespuestaBase(T datos, String mensaje) {
		this.exito = datos != null;
		this.datos = datos;
		this.mensaje = mensaje;
	}

	public RespuestaBase(String mensaje, boolean error) {
		this.exito = !error;
		this.mensaje = mensaje;
	}

	public boolean isExito() {
		return exito;
	}

	public void setExito(boolean exito) {
		this.exito = exito;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public T getDatos() {
		return datos;
	}

	public void setDatos(T datos) {
		this.datos = datos;
	}
}
