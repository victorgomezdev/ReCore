package com.recore.modelo;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/*
* Módulo 4 - Clase 11 - ORM
* Entidad base con campos comunes para todas las entidades
*/
@MappedSuperclass
public abstract class EntidadBase {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "fecha_creacion")
	private LocalDateTime fechaCreacion;

	@Column(name = "fecha_modificacion")
	private LocalDateTime fechaModificacion;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDateTime getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(LocalDateTime fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public LocalDateTime getFechaModificacion() {
		return fechaModificacion;
	}

	public void setFechaModificacion(LocalDateTime fechaModificacion) {
		this.fechaModificacion = fechaModificacion;
	}

	@PrePersist
	public void prePersist() {
		fechaCreacion = LocalDateTime.now();
		fechaModificacion = LocalDateTime.now();
	}

	@PreUpdate
	public void preUpdate() {
		fechaModificacion = LocalDateTime.now();
	}
}
