package com.recore.dto;

import java.util.List;
import java.util.Map;

/**
 * DTO para estadísticas y reportes del panel de administración
 */
public class EstadisticasDTO {
    
    // Estadísticas generales
    private Long totalUsuarios;
    private Long totalProductos;
    private Long totalReservas;
    private Long totalPuntuaciones;
    
    // Estadísticas de reservas
    private Long reservasPendientes;
    private Long reservasConfirmadas;
    private Long reservasCanceladas;
    private Long reservasCompletadas;
    
    // Estadísticas de productos
    private List<Map<String, Object>> productosMasReservados;
    private List<Map<String, Object>> productosMejorPuntuados;
    private List<Map<String, Object>> categoriasMasPopulares;
    
    // Estadísticas de usuarios
    private List<Map<String, Object>> usuariosMasActivos;
    
    // Constructores
    public EstadisticasDTO() {}

    // Getters y Setters
    public Long getTotalUsuarios() {
        return totalUsuarios;
    }

    public void setTotalUsuarios(Long totalUsuarios) {
        this.totalUsuarios = totalUsuarios;
    }

    public Long getTotalProductos() {
        return totalProductos;
    }

    public void setTotalProductos(Long totalProductos) {
        this.totalProductos = totalProductos;
    }

    public Long getTotalReservas() {
        return totalReservas;
    }

    public void setTotalReservas(Long totalReservas) {
        this.totalReservas = totalReservas;
    }

    public Long getTotalPuntuaciones() {
        return totalPuntuaciones;
    }

    public void setTotalPuntuaciones(Long totalPuntuaciones) {
        this.totalPuntuaciones = totalPuntuaciones;
    }

    public Long getReservasPendientes() {
        return reservasPendientes;
    }

    public void setReservasPendientes(Long reservasPendientes) {
        this.reservasPendientes = reservasPendientes;
    }

    public Long getReservasConfirmadas() {
        return reservasConfirmadas;
    }

    public void setReservasConfirmadas(Long reservasConfirmadas) {
        this.reservasConfirmadas = reservasConfirmadas;
    }

    public Long getReservasCanceladas() {
        return reservasCanceladas;
    }

    public void setReservasCanceladas(Long reservasCanceladas) {
        this.reservasCanceladas = reservasCanceladas;
    }

    public Long getReservasCompletadas() {
        return reservasCompletadas;
    }

    public void setReservasCompletadas(Long reservasCompletadas) {
        this.reservasCompletadas = reservasCompletadas;
    }

    public List<Map<String, Object>> getProductosMasReservados() {
        return productosMasReservados;
    }

    public void setProductosMasReservados(List<Map<String, Object>> productosMasReservados) {
        this.productosMasReservados = productosMasReservados;
    }

    public List<Map<String, Object>> getProductosMejorPuntuados() {
        return productosMejorPuntuados;
    }

    public void setProductosMejorPuntuados(List<Map<String, Object>> productosMejorPuntuados) {
        this.productosMejorPuntuados = productosMejorPuntuados;
    }

    public List<Map<String, Object>> getCategoriasMasPopulares() {
        return categoriasMasPopulares;
    }

    public void setCategoriasMasPopulares(List<Map<String, Object>> categoriasMasPopulares) {
        this.categoriasMasPopulares = categoriasMasPopulares;
    }

    public List<Map<String, Object>> getUsuariosMasActivos() {
        return usuariosMasActivos;
    }

    public void setUsuariosMasActivos(List<Map<String, Object>> usuariosMasActivos) {
        this.usuariosMasActivos = usuariosMasActivos;
    }
}