package com.recore.modelo;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

/**
 * Entidad Estado para gestionar los estados de las reservas
 * Estados: Pendiente, Confirmada, Cancelada, Completada
 */
@Entity
@Table(name = "estados")
public class Estado extends EntidadBase {

    @Column(name = "nombre", length = 50, nullable = false, unique = true)
    @NotBlank(message = "El nombre del estado es obligatorio")
    @Size(max = 50, message = "El nombre del estado no puede exceder 50 caracteres")
    private String nombre;

    @Column(name = "descripcion", length = 255)
    @Size(max = 255, message = "La descripción no puede exceder 255 caracteres")
    private String descripcion;

    @Column(name = "activo", columnDefinition = "TINYINT(1) DEFAULT 1")
    private Boolean activo;

    @OneToMany(mappedBy = "estado", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "estado"})
    private List<Reserva> reservas;

    // Constructor por defecto
    public Estado() {
        this.activo = true;
    }

    // Constructor con parámetros
    public Estado(String nombre, String descripcion) {
        this();
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    // Getters y Setters
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public List<Reserva> getReservas() {
        return reservas;
    }

    public void setReservas(List<Reserva> reservas) {
        this.reservas = reservas;
    }

    @Override
    public String toString() {
        return "Estado{" +
                "id=" + getId() +
                ", nombre='" + nombre + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", activo=" + activo +
                '}';
    }
}