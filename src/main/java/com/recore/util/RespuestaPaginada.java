package com.recore.util;

import java.util.List;

/*
* Módulo 3 - APIs
* Clase 7 - API REST - Parte 2
* Response Entity - Clase base para respuestas paginadas
*/
public class RespuestaPaginada<T> extends RespuestaBase<List<T>> {
	private int paginaActual;
	private int totalPaginas;
	private long totalElementos;
	private int elementosPorPagina;

	public RespuestaPaginada() {
		super();
	}

	public RespuestaPaginada(List<T> datos, long totalElementos, int paginaActual, int elementosPorPagina) {
		super(datos);
		this.totalElementos = totalElementos;
		this.paginaActual = paginaActual;
		this.elementosPorPagina = elementosPorPagina;
		this.totalPaginas = elementosPorPagina > 0 ? (int) Math.ceil((double) totalElementos / elementosPorPagina) : 0;
	}

	public RespuestaPaginada(List<T> datos, String mensaje) {
		super(datos, mensaje);
	}

	public int getPaginaActual() {
		return paginaActual;
	}

	public void setPaginaActual(int paginaActual) {
		this.paginaActual = paginaActual;
	}

	public int getTotalPaginas() {
		return totalPaginas;
	}

	public void setTotalPaginas(int totalPaginas) {
		this.totalPaginas = totalPaginas;
	}

	public long getTotalElementos() {
		return totalElementos;
	}

	public void setTotalElementos(long totalElementos) {
		this.totalElementos = totalElementos;
	}

	public int getElementosPorPagina() {
		return elementosPorPagina;
	}

	public void setElementosPorPagina(int elementosPorPagina) {
		this.elementosPorPagina = elementosPorPagina;
	}
}
