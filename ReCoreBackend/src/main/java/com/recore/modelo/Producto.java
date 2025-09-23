package com.recore.modelo;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.math.BigDecimal;
import java.util.List;

/**
 * Módulo 4 - Backend avanzado
 * Clase 11 - ORM con JPA/Hibernate
 * Modelo para la entidad Producto
 */
@Entity
@Table(name = "productos")
public class Producto extends EntidadBase {

    @Column(name = "nombre", length = 100, nullable = false)
    private String nombre;

    @Column(name = "descripcion", length = 500)
    private String descripcion;

    @Column(name = "precio", precision = 12, scale = 6)
    private BigDecimal precio;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idcategoria")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Categoria categoria;

    @OneToMany(mappedBy = "producto", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "producto"})
    private List<Imagen> imagenes;

    @OneToMany(mappedBy = "producto", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "producto"})
    private List<Reserva> reservas;

    @OneToMany(mappedBy = "producto", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "producto"})
    private List<Puntuacion> puntuaciones;
    
    @Column(name = "puntuacion_promedio")
    private Double puntuacionPromedio;
    
    @Column(name = "total_puntuaciones")
    private Integer totalPuntuaciones;

    // Constructor por defecto
    public Producto() {
        this.puntuacionPromedio = 0.0;
        this.totalPuntuaciones = 0;
    }

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

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public List<Imagen> getImagenes() {
        return imagenes;
    }

    public void setImagenes(List<Imagen> imagenes) {
        this.imagenes = imagenes;
    }

    public List<Reserva> getReservas() {
        return reservas;
    }

    public void setReservas(List<Reserva> reservas) {
        this.reservas = reservas;
    }

    public List<Puntuacion> getPuntuaciones() {
        return puntuaciones;
    }

    public void setPuntuaciones(List<Puntuacion> puntuaciones) {
        this.puntuaciones = puntuaciones;
    }
    
    public Double getPuntuacionPromedio() {
        return puntuacionPromedio;
    }
    
    public void setPuntuacionPromedio(Double puntuacionPromedio) {
        this.puntuacionPromedio = puntuacionPromedio;
    }
    
    public Integer getTotalPuntuaciones() {
        return totalPuntuaciones;
    }
    
    public void setTotalPuntuaciones(Integer totalPuntuaciones) {
        this.totalPuntuaciones = totalPuntuaciones;
    }
    
    /**
     * Método de utilidad para calcular el promedio de puntuaciones
     * Nota: Este método no actualiza la base de datos, solo calcula el promedio
     */
    public void calcularPuntuacionPromedio() {
        if (puntuaciones == null || puntuaciones.isEmpty()) {
            this.puntuacionPromedio = 0.0;
            this.totalPuntuaciones = 0;
            return;
        }
        
        double suma = 0;
        int total = 0;
        
        for (Puntuacion p : puntuaciones) {
            if (p.getPuntuacion() != null) {
                suma += p.getPuntuacion();
                total++;
            }
        }
        
        this.puntuacionPromedio = total > 0 ? Math.round((suma / total) * 100.0) / 100.0 : 0.0;
        this.totalPuntuaciones = total;
    }
}