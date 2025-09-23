package com.recore.modelo;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Size;

/**
 * Entidad Puntuacion para gestionar las puntuaciones y reseñas de productos
 * Relaciona usuarios con productos mediante puntuaciones del 1 al 5
 */
@Entity
@Table(name = "puntuaciones", 
       uniqueConstraints = @UniqueConstraint(name = "unique_puntuacion", columnNames = {"idusuario", "idproducto"}))
public class Puntuacion extends EntidadBase {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idusuario", nullable = false, foreignKey = @ForeignKey(name = "fk_puntuacion_usuario"))
    @NotNull(message = "El usuario es obligatorio")
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idproducto", nullable = false, foreignKey = @ForeignKey(name = "fk_puntuacion_producto"))
    @NotNull(message = "El producto es obligatorio")
    private Producto producto;

    @Column(name = "puntuacion", nullable = false)
    @NotNull(message = "La puntuación es obligatoria")
    @Min(value = 1, message = "La puntuación mínima es 1")
    @Max(value = 5, message = "La puntuación máxima es 5")
    private Integer puntuacion;

    @Column(name = "comentario", length = 1000)
    @Size(max = 1000, message = "El comentario no puede exceder 1000 caracteres")
    private String comentario;

    @Column(name = "recomendado", columnDefinition = "TINYINT(1)")
    private Boolean recomendado;

    // Constructor por defecto
    public Puntuacion() {
        this.recomendado = true;
    }

    // Constructor con parámetros básicos
    public Puntuacion(Usuario usuario, Producto producto, Integer puntuacion) {
        this();
        this.usuario = usuario;
        this.producto = producto;
        this.puntuacion = puntuacion;
    }

    // Constructor completo
    public Puntuacion(Usuario usuario, Producto producto, Integer puntuacion, String comentario, Boolean recomendado) {
        this.usuario = usuario;
        this.producto = producto;
        this.puntuacion = puntuacion;
        this.comentario = comentario;
        this.recomendado = recomendado;
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

    public Integer getPuntuacion() {
        return puntuacion;
    }

    public void setPuntuacion(Integer puntuacion) {
        this.puntuacion = puntuacion;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public Boolean getRecomendado() {
        return recomendado;
    }

    public void setRecomendado(Boolean recomendado) {
        this.recomendado = recomendado;
    }

    /**
     * Método de utilidad para verificar si es una puntuación positiva (4 o 5)
     */
    public boolean isPuntuacionPositiva() {
        return puntuacion != null && puntuacion >= 4;
    }

    /**
     * Método de utilidad para verificar si es una puntuación negativa (1 o 2)
     */
    public boolean isPuntuacionNegativa() {
        return puntuacion != null && puntuacion <= 2;
    }

    /**
     * Método de utilidad para verificar si tiene comentario
     */
    public boolean tieneComentario() {
        return comentario != null && !comentario.trim().isEmpty();
    }

    @Override
    public String toString() {
        return "Puntuacion{" +
                "id=" + getId() +
                ", usuario=" + (usuario != null ? usuario.getEmail() : "null") +
                ", producto=" + (producto != null ? producto.getNombre() : "null") +
                ", puntuacion=" + puntuacion +
                ", recomendado=" + recomendado +
                ", tieneComentario=" + tieneComentario() +
                '}';
    }
}