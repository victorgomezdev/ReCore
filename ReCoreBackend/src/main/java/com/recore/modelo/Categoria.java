package com.recore.modelo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Módulo 4 - Backend avanzado
 * Clase 11 - ORM con JPA/Hibernate
 * Modelo para la entidad Categoría
 */
@Entity
@Table(name = "categorias")
public class Categoria extends EntidadBase {

    @NotBlank(message = "El título de la categoría es obligatorio")
    @Size(max = 100, message = "El título no puede exceder 100 caracteres")
    @Column(name = "titulo", length = 100, nullable = false)
    private String titulo;

    @Size(max = 500, message = "La descripción no puede exceder 500 caracteres")
    @Column(name = "descripcion", length = 500)
    private String descripcion;

    @Size(max = 255, message = "La imagen no puede exceder 255 caracteres")
    @Column(name = "imagen", length = 255)
    private String imagen;

    // Constructor por defecto
    public Categoria() {
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }
}