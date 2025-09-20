package com.recore.modelo;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Modelo para la relación muchos a muchos entre productos y características
 * Representa la tabla intermedia productos_caracteristicas
 */
@Entity
@Table(name = "productos_caracteristicas")
public class ProductoCaracteristica extends EntidadBase {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idproducto", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Producto producto;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idcaracteristica", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Caracteristica caracteristica;

    // Constructor por defecto
    public ProductoCaracteristica() {
    }

    // Constructor con parámetros
    public ProductoCaracteristica(Producto producto, Caracteristica caracteristica) {
        this.producto = producto;
        this.caracteristica = caracteristica;
    }

    // Getters y Setters
    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public Caracteristica getCaracteristica() {
        return caracteristica;
    }

    public void setCaracteristica(Caracteristica caracteristica) {
        this.caracteristica = caracteristica;
    }

    @Override
    public String toString() {
        return "ProductoCaracteristica{" +
                "id=" + getId() +
                ", producto=" + (producto != null ? producto.getNombre() : "null") +
                ", caracteristica=" + (caracteristica != null ? caracteristica.getNombre() : "null") +
                ", fechaCreacion=" + getFechaCreacion() +
                '}';
    }
}