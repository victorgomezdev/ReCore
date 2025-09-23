package com.recore.modelo;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.DecimalMin;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Entidad Reserva para gestionar las reservas de productos
 * Relaciona usuarios con productos en fechas específicas
 */
@Entity
@Table(name = "reservas")
public class Reserva extends EntidadBase {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idusuario", nullable = false, foreignKey = @ForeignKey(name = "fk_reserva_usuario"))
    @NotNull(message = "El usuario es obligatorio")
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idproducto", nullable = false, foreignKey = @ForeignKey(name = "fk_reserva_producto"))
    @NotNull(message = "El producto es obligatorio")
    private Producto producto;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idestado", nullable = false, foreignKey = @ForeignKey(name = "fk_reserva_estado"))
    @NotNull(message = "El estado es obligatorio")
    private Estado estado;

    @Column(name = "fecha_inicio", nullable = false)
    @NotNull(message = "La fecha de inicio es obligatoria")
    private LocalDate fechaInicio;

    @Column(name = "fecha_fin", nullable = false)
    @NotNull(message = "La fecha de fin es obligatoria")
    private LocalDate fechaFin;

    @Column(name = "precio_total", precision = 10, scale = 2, nullable = false)
    @NotNull(message = "El precio total es obligatorio")
    @DecimalMin(value = "0.0", inclusive = false, message = "El precio total debe ser mayor a 0")
    private BigDecimal precioTotal;

    @Column(name = "observaciones", length = 500)
    private String observaciones;

    @Column(name = "fecha_confirmacion")
    private LocalDateTime fechaConfirmacion;

    @Column(name = "fecha_cancelacion")
    private LocalDateTime fechaCancelacion;

    @Column(name = "motivo_cancelacion", length = 255)
    private String motivoCancelacion;

    // Constructor por defecto
    public Reserva() {
    }

    // Constructor con parámetros básicos
    public Reserva(Usuario usuario, Producto producto, Estado estado, LocalDate fechaInicio, LocalDate fechaFin, BigDecimal precioTotal) {
        this.usuario = usuario;
        this.producto = producto;
        this.estado = estado;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.precioTotal = precioTotal;
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

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }

    public BigDecimal getPrecioTotal() {
        return precioTotal;
    }

    public void setPrecioTotal(BigDecimal precioTotal) {
        this.precioTotal = precioTotal;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public LocalDateTime getFechaConfirmacion() {
        return fechaConfirmacion;
    }

    public void setFechaConfirmacion(LocalDateTime fechaConfirmacion) {
        this.fechaConfirmacion = fechaConfirmacion;
    }

    public LocalDateTime getFechaCancelacion() {
        return fechaCancelacion;
    }

    public void setFechaCancelacion(LocalDateTime fechaCancelacion) {
        this.fechaCancelacion = fechaCancelacion;
    }

    public String getMotivoCancelacion() {
        return motivoCancelacion;
    }

    public void setMotivoCancelacion(String motivoCancelacion) {
        this.motivoCancelacion = motivoCancelacion;
    }

    /**
     * Método de utilidad para calcular la duración de la reserva en días
     */
    public long getDuracionEnDias() {
        if (fechaInicio != null && fechaFin != null) {
            return fechaFin.toEpochDay() - fechaInicio.toEpochDay() + 1;
        }
        return 0;
    }

    /**
     * Método de utilidad para verificar si la reserva está activa
     */
    public boolean isActiva() {
        return estado != null && "Confirmada".equals(estado.getNombre());
    }

    @Override
    public String toString() {
        return "Reserva{" +
                "id=" + getId() +
                ", usuario=" + (usuario != null ? usuario.getEmail() : "null") +
                ", producto=" + (producto != null ? producto.getNombre() : "null") +
                ", estado=" + (estado != null ? estado.getNombre() : "null") +
                ", fechaInicio=" + fechaInicio +
                ", fechaFin=" + fechaFin +
                ", precioTotal=" + precioTotal +
                '}';
    }
}