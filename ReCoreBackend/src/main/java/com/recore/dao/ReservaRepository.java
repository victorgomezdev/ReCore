package com.recore.dao;

import com.recore.modelo.Reserva;
import com.recore.modelo.Usuario;
import com.recore.modelo.Producto;
import com.recore.modelo.Estado;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA Repository para Reserva
 * Gestiona las operaciones de base de datos para las reservas
 */
@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Long> {
    
    /**
     * Buscar reservas por usuario
     */
    Page<Reserva> findByUsuario(Usuario usuario, Pageable pageable);
    
    /**
     * Buscar reservas por usuario ID
     */
    @Query("SELECT r FROM Reserva r WHERE r.usuario.id = :usuarioId")
    Page<Reserva> findByUsuarioId(@Param("usuarioId") Long usuarioId, Pageable pageable);
    
    /**
     * Buscar reservas por producto
     */
    Page<Reserva> findByProducto(Producto producto, Pageable pageable);
    
    /**
     * Buscar reservas por producto ID
     */
    @Query("SELECT r FROM Reserva r WHERE r.producto.id = :productoId")
    Page<Reserva> findByProductoId(@Param("productoId") Long productoId, Pageable pageable);
    
    /**
     * Buscar reservas por estado
     */
    Page<Reserva> findByEstado(Estado estado, Pageable pageable);
    
    /**
     * Buscar reservas por estado nombre
     */
    @Query("SELECT r FROM Reserva r WHERE r.estado.nombre = :estadoNombre")
    Page<Reserva> findByEstadoNombre(@Param("estadoNombre") String estadoNombre, Pageable pageable);
    
    /**
     * Buscar reservas en un rango de fechas
     */
    @Query("SELECT r FROM Reserva r WHERE r.fechaInicio >= :fechaInicio AND r.fechaFin <= :fechaFin")
    List<Reserva> findByFechaInicioGreaterThanEqualAndFechaFinLessThanEqual(
            @Param("fechaInicio") LocalDate fechaInicio, 
            @Param("fechaFin") LocalDate fechaFin);
    
    /**
     * Verificar disponibilidad de producto en fechas específicas
     * Busca reservas confirmadas que se solapen con las fechas dadas
     */
    @Query("SELECT r FROM Reserva r WHERE r.producto.id = :productoId " +
           "AND r.estado.nombre = 'Confirmada' " +
           "AND ((r.fechaInicio <= :fechaFin AND r.fechaFin >= :fechaInicio))")
    List<Reserva> findReservasConflictivas(
            @Param("productoId") Long productoId,
            @Param("fechaInicio") LocalDate fechaInicio,
            @Param("fechaFin") LocalDate fechaFin);
    
    /**
     * Buscar reservas activas (confirmadas) de un usuario
     */
    @Query("SELECT r FROM Reserva r WHERE r.usuario.id = :usuarioId " +
           "AND r.estado.nombre = 'Confirmada' " +
           "AND r.fechaFin >= CURRENT_DATE")
    List<Reserva> findReservasActivasByUsuario(@Param("usuarioId") Long usuarioId);
    
    /**
     * Buscar reservas próximas a vencer (en los próximos N días)
     */
    @Query("SELECT r FROM Reserva r WHERE r.estado.nombre = 'Confirmada' " +
           "AND r.fechaInicio BETWEEN CURRENT_DATE AND :fechaLimite")
    List<Reserva> findReservasProximasAIniciar(@Param("fechaLimite") LocalDate fechaLimite);
    
    /**
     * Contar reservas por estado
     */
    @Query("SELECT COUNT(r) FROM Reserva r WHERE r.estado.nombre = :estadoNombre")
    Long countByEstadoNombre(@Param("estadoNombre") String estadoNombre);
    
    /**
     * Buscar historial de reservas de un usuario (todas las reservas)
     */
    @Query("SELECT r FROM Reserva r WHERE r.usuario.id = :usuarioId ORDER BY r.fechaCreacion DESC")
    Page<Reserva> findHistorialByUsuario(@Param("usuarioId") Long usuarioId, Pageable pageable);
    
    /**
     * Buscar reservas por usuario y estado
     */
    @Query("SELECT r FROM Reserva r WHERE r.usuario.id = :usuarioId AND r.estado.nombre = :estadoNombre ORDER BY r.fechaCreacion DESC")
    Page<Reserva> findByUsuarioIdAndEstadoNombre(
            @Param("usuarioId") Long usuarioId, 
            @Param("estadoNombre") String estadoNombre, 
            Pageable pageable);
            
    /**
     * Buscar reservas por usuario, producto y estado
     */
    @Query("SELECT r FROM Reserva r WHERE r.usuario.id = :usuarioId AND r.producto.id = :productoId AND r.estado.nombre = :estadoNombre")
    List<Reserva> findByUsuarioIdAndProductoIdAndEstadoNombre(
            @Param("usuarioId") Long usuarioId,
            @Param("productoId") Long productoId,
            @Param("estadoNombre") String estadoNombre);
    
    /**
     * Buscar historial filtrado por usuario y fechas
     */
    @Query("SELECT r FROM Reserva r WHERE r.usuario.id = :usuarioId " +
           "AND ((r.fechaInicio BETWEEN :fechaDesde AND :fechaHasta) " +
           "OR (r.fechaFin BETWEEN :fechaDesde AND :fechaHasta)) " +
           "ORDER BY r.fechaCreacion DESC")
    Page<Reserva> findHistorialFiltradoByUsuario(
            @Param("usuarioId") Long usuarioId,
            @Param("fechaDesde") LocalDate fechaDesde,
            @Param("fechaHasta") LocalDate fechaHasta,
            Pageable pageable);
    
    /**
     * Buscar historial filtrado por usuario, fechas y estados
     */
    @Query("SELECT r FROM Reserva r WHERE r.usuario.id = :usuarioId " +
           "AND ((r.fechaInicio BETWEEN :fechaDesde AND :fechaHasta) " +
           "OR (r.fechaFin BETWEEN :fechaDesde AND :fechaHasta)) " +
           "AND r.estado.nombre IN :estados " +
           "ORDER BY r.fechaCreacion DESC")
    Page<Reserva> findHistorialFiltradoByUsuarioAndEstados(
            @Param("usuarioId") Long usuarioId,
            @Param("fechaDesde") LocalDate fechaDesde,
            @Param("fechaHasta") LocalDate fechaHasta,
            @Param("estados") List<String> estados,
            Pageable pageable);
    
    /**
     * Buscar reservas por producto y fechas para verificar disponibilidad
     */
    @Query("SELECT r FROM Reserva r WHERE r.producto.id = :productoId " +
           "AND r.estado.nombre IN ('Confirmada', 'Pendiente') " +
           "AND r.fechaInicio <= :fechaFin AND r.fechaFin >= :fechaInicio")
    List<Reserva> findReservasEnFechas(
            @Param("productoId") Long productoId,
            @Param("fechaInicio") LocalDate fechaInicio,
            @Param("fechaFin") LocalDate fechaFin);
            
    /**
     * Obtener productos más reservados
     */
    @Query("SELECT r.producto.id, COUNT(r) as total FROM Reserva r " +
           "GROUP BY r.producto.id ORDER BY total DESC")
    List<Object[]> findProductosMasReservados();
    
    /**
     * Obtener usuarios más activos (con más reservas)
     */
    @Query("SELECT r.usuario.id, COUNT(r) as total FROM Reserva r " +
           "GROUP BY r.usuario.id ORDER BY total DESC")
    List<Object[]> findUsuariosMasActivos();
    
    /**
     * Obtener estadísticas de reservas por mes
     */
    @Query("SELECT FUNCTION('YEAR', r.fechaCreacion) as anio, " +
           "FUNCTION('MONTH', r.fechaCreacion) as mes, " +
           "COUNT(r) as total FROM Reserva r " +
           "GROUP BY anio, mes ORDER BY anio DESC, mes DESC")
    List<Object[]> findEstadisticasPorMes();
}