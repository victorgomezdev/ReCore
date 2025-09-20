package com.recore.modelo;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.recore.modelo.Usuario;
import com.recore.modelo.Producto;

/**
 * Entidad para la gestión de favoritos
 * Representa la relación entre usuarios y productos favoritos
 */
@Entity
@Table(name = "favoritos", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"idusuario", "idproducto"})
})
public class Favorito extends EntidadBase {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idusuario", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idproducto", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Producto producto;

    // Constructor por defecto
    public Favorito() {
    }

    // Constructor con parámetros
    public Favorito(Usuario usuario, Producto producto) {
        this.usuario = usuario;
        this.producto = producto;
    }

    // Getters y Setters
    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    @Override
    public String toString() {
        return "Favorito{" +
                "id=" + getId() +
                ", usuario=" + (usuario != null ? usuario.getId() : null) +
                ", producto=" + (producto != null ? producto.getId() : null) +
                ", fechaCreacion=" + getFechaCreacion() +
                ", fechaModificacion=" + getFechaModificacion() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Favorito)) return false;
        Favorito favorito = (Favorito) o;
        return getId() != null && getId().equals(favorito.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}