package com.recore.dao;

import com.recore.modelo.Puntuacion;
import com.recore.modelo.Usuario;
import com.recore.modelo.Producto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA Repository para Puntuacion
 * Gestiona las operaciones de base de datos para las puntuaciones y reseñas
 */
@Repository
public interface PuntuacionRepository extends JpaRepository<Puntuacion, Long> {
    
    /**
     * Buscar puntuación específica de un usuario para un producto
     */
    Optional<Puntuacion> findByUsuarioAndProducto(Usuario usuario, Producto producto);
    
    /**
     * Buscar puntuación por IDs de usuario y producto
     */
    @Query("SELECT p FROM Puntuacion p WHERE p.usuario.id = :usuarioId AND p.producto.id = :productoId")
    Optional<Puntuacion> findByUsuarioIdAndProductoId(@Param("usuarioId") Long usuarioId, @Param("productoId") Long productoId);
    
    /**
     * Buscar todas las puntuaciones de un producto
     */
    Page<Puntuacion> findByProducto(Producto producto, Pageable pageable);
    
    /**
     * Buscar puntuaciones por producto ID
     */
    @Query("SELECT p FROM Puntuacion p WHERE p.producto.id = :productoId ORDER BY p.fechaCreacion DESC")
    Page<Puntuacion> findByProductoId(@Param("productoId") Long productoId, Pageable pageable);
    
    /**
     * Buscar todas las puntuaciones de un usuario
     */
    Page<Puntuacion> findByUsuario(Usuario usuario, Pageable pageable);
    
    /**
     * Buscar puntuaciones por usuario ID
     */
    @Query("SELECT p FROM Puntuacion p WHERE p.usuario.id = :usuarioId ORDER BY p.fechaCreacion DESC")
    Page<Puntuacion> findByUsuarioId(@Param("usuarioId") Long usuarioId, Pageable pageable);
    
    /**
     * Calcular promedio de puntuaciones de un producto
     */
    @Query("SELECT AVG(p.puntuacion) FROM Puntuacion p WHERE p.producto.id = :productoId")
    Double calcularPromedioPorProducto(@Param("productoId") Long productoId);
    
    /**
     * Contar total de puntuaciones de un producto
     */
    @Query("SELECT COUNT(p) FROM Puntuacion p WHERE p.producto.id = :productoId")
    Long contarPuntuacionesPorProducto(@Param("productoId") Long productoId);
    
    /**
     * Buscar puntuaciones por valor específico
     */
    @Query("SELECT p FROM Puntuacion p WHERE p.puntuacion = :puntuacion")
    List<Puntuacion> findByPuntuacion(@Param("puntuacion") Integer puntuacion);
    
    /**
     * Buscar puntuaciones de un producto por valor específico
     */
    @Query("SELECT p FROM Puntuacion p WHERE p.producto.id = :productoId AND p.puntuacion = :puntuacion")
    List<Puntuacion> findByProductoIdAndPuntuacion(@Param("productoId") Long productoId, @Param("puntuacion") Integer puntuacion);
    
    /**
     * Buscar puntuaciones recomendadas de un producto
     */
    @Query("SELECT p FROM Puntuacion p WHERE p.producto.id = :productoId AND p.recomendado = true")
    List<Puntuacion> findRecomendadasPorProducto(@Param("productoId") Long productoId);
    
    /**
     * Buscar puntuaciones con comentario de un producto
     */
    @Query("SELECT p FROM Puntuacion p WHERE p.producto.id = :productoId AND p.comentario IS NOT NULL AND p.comentario != ''")
    Page<Puntuacion> findConComentarioPorProducto(@Param("productoId") Long productoId, Pageable pageable);
    
    /**
     * Obtener distribución de puntuaciones por producto
     */
    @Query("SELECT p.puntuacion, COUNT(p) FROM Puntuacion p WHERE p.producto.id = :productoId GROUP BY p.puntuacion ORDER BY p.puntuacion")
    List<Object[]> obtenerDistribucionPuntuaciones(@Param("productoId") Long productoId);
    
    /**
     * Buscar productos mejor puntuados (promedio >= 4)
     */
    @Query("SELECT p.producto.id, AVG(p.puntuacion) as promedio FROM Puntuacion p " +
           "GROUP BY p.producto.id HAVING AVG(p.puntuacion) >= 4 ORDER BY promedio DESC")
    List<Object[]> findProductosMejorPuntuados();
    
    /**
     * Verificar si un usuario ya puntuó un producto
     */
    @Query("SELECT COUNT(p) > 0 FROM Puntuacion p WHERE p.usuario.id = :usuarioId AND p.producto.id = :productoId")
    boolean existePuntuacion(@Param("usuarioId") Long usuarioId, @Param("productoId") Long productoId);
    
    /**
     * Buscar últimas puntuaciones (más recientes)
     */
    @Query("SELECT p FROM Puntuacion p ORDER BY p.fechaCreacion DESC")
    Page<Puntuacion> findUltimasPuntuaciones(Pageable pageable);
    
    /**
     * Buscar puntuaciones por rango de valores
     */
    @Query("SELECT p FROM Puntuacion p WHERE p.puntuacion BETWEEN :minimo AND :maximo")
    List<Puntuacion> findByPuntuacionBetween(@Param("minimo") Integer minimo, @Param("maximo") Integer maximo);
    
    /**
     * Contar puntuaciones recomendadas por producto
     */
    @Query("SELECT COUNT(p) FROM Puntuacion p WHERE p.producto.id = :productoId AND p.recomendado = true")
    Long contarRecomendacionesPorProducto(@Param("productoId") Long productoId);
}