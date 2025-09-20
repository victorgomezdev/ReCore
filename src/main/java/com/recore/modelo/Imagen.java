package com.recore.modelo;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/*
* Entidad para manejar las imágenes de los productos
* Permite múltiples imágenes por producto con orden y imagen principal
*/
@Entity
@Table(name = "imagenes")
public class Imagen extends EntidadBase {

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idproducto", nullable = false)
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "imagenes"})
	private Producto producto;

	@Column(name = "url", nullable = false, length = 500)
	private String url;

	@Column(name = "alt_text", length = 255)
	private String altText;

	@Column(name = "es_principal")
	private Boolean esPrincipal = false;

	@Column(name = "orden_visualizacion")
	private Integer ordenVisualizacion = 0;

	// Constructores
	public Imagen() {
	}

	public Imagen(Producto producto, String url, String altText, Boolean esPrincipal, Integer ordenVisualizacion) {
		this.producto = producto;
		this.url = url;
		this.altText = altText;
		this.esPrincipal = esPrincipal;
		this.ordenVisualizacion = ordenVisualizacion;
	}

	// Getters y Setters
	public Producto getProducto() {
		return producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getAltText() {
		return altText;
	}

	public void setAltText(String altText) {
		this.altText = altText;
	}

	public Boolean getEsPrincipal() {
		return esPrincipal;
	}

	public void setEsPrincipal(Boolean esPrincipal) {
		this.esPrincipal = esPrincipal;
	}

	public Integer getOrdenVisualizacion() {
		return ordenVisualizacion;
	}

	public void setOrdenVisualizacion(Integer ordenVisualizacion) {
		this.ordenVisualizacion = ordenVisualizacion;
	}

	@Override
	public String toString() {
		return "Imagen{" +
				"id=" + getId() +
				", url='" + url + '\'' +
				", altText='" + altText + '\'' +
				", esPrincipal=" + esPrincipal +
				", ordenVisualizacion=" + ordenVisualizacion +
				'}';
	}
}