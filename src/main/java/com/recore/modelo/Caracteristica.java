package com.recore.modelo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Módulo 4 - Backend avanzado
 * Clase 11 - ORM con JPA/Hibernate
 * Entidad Caracteristica para gestión de características de productos
 */
@Entity
@Table(name = "caracteristicas")
public class Caracteristica extends EntidadBase {

    @Column(name = "nombre", length = 100, nullable = false)
    @NotBlank(message = "El nombre de la característica es obligatorio")
    @Size(max = 100, message = "El nombre no puede exceder 100 caracteres")
    private String nombre;

    @Column(name = "icono", length = 255)
    @Size(max = 255, message = "El icono no puede exceder 255 caracteres")
    private String icono;

    // Constructor por defecto
    public Caracteristica() {
    }

    // Constructor con parámetros
    public Caracteristica(String nombre, String icono) {
        this.nombre = nombre;
        this.icono = icono;
    }

    // Getters y Setters
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getIcono() {
        return icono;
    }

    public void setIcono(String icono) {
        this.icono = icono;
    }

    @Override
    public String toString() {
        return "Caracteristica{" +
                "id=" + getId() +
                ", nombre='" + nombre + '\'' +
                ", icono='" + icono + '\'' +
                ", fechaCreacion=" + getFechaCreacion() +
                ", fechaModificacion=" + getFechaModificacion() +
                '}';
    }
}