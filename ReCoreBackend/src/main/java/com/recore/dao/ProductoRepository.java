package com.recore.dao;

import com.recore.modelo.Producto;
import com.recore.modelo.Categoria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Módulo 4 - Backend avanzado
 * Clase 11 - ORM con JPA/Hibernate
 * Spring Data JPA Repository para Producto
 */
@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {

        // Buscar productos por nombre
        Producto findByNombre(String nombre);

        // Buscar productos que contengan texto en el nombre
        List<Producto> findByNombreContaining(String nombre);

        // Buscar productos que contengan texto en el nombre con paginación
        Page<Producto> findByNombreContaining(String nombre, Pageable pageable);

        // Buscar productos que contengan texto en la descripción
        List<Producto> findByDescripcionContaining(String descripcion);

        // Buscar productos que contengan texto en la descripción con paginación
        Page<Producto> findByDescripcionContaining(String descripcion, Pageable pageable);

        // Buscar productos que contengan texto en el nombre o en la descripción
        @Query("SELECT p FROM Producto p WHERE LOWER(p.nombre) LIKE LOWER(CONCAT('%', :texto, '%')) OR LOWER(p.descripcion) LIKE LOWER(CONCAT('%', :texto, '%'))")
        List<Producto> findByNombreOrDescripcionContaining(@Param("texto") String texto);

        // Buscar productos que contengan texto en el nombre o en la descripción con
        // paginación
        @Query("SELECT p FROM Producto p WHERE LOWER(p.nombre) LIKE LOWER(CONCAT('%', :texto, '%')) OR LOWER(p.descripcion) LIKE LOWER(CONCAT('%', :texto, '%'))")
        Page<Producto> findByNombreOrDescripcionContaining(@Param("texto") String texto, Pageable pageable);

        // Buscar productos por categoría
        List<Producto> findByCategoria(Categoria categoria);

        // Buscar productos por categoría con paginación
        Page<Producto> findByCategoria(Categoria categoria, Pageable pageable);

        // Buscar productos por ID de categoría
        List<Producto> findByCategoriaId(Long categoriaId);

        // Buscar productos por rango de precio
        List<Producto> findByPrecioBetween(BigDecimal precioMin, BigDecimal precioMax);

        // Buscar productos por rango de precio con paginación
        Page<Producto> findByPrecioBetween(BigDecimal precioMin, BigDecimal precioMax, Pageable pageable);

        // Buscar productos con precio menor o igual
        List<Producto> findByPrecioLessThanEqual(BigDecimal precio);

        // Buscar productos con precio mayor o igual
        List<Producto> findByPrecioGreaterThanEqual(BigDecimal precio);

        // Búsqueda avanzada con múltiples criterios
        @Query("SELECT DISTINCT p FROM Producto p " +
                        "LEFT JOIN p.categoria c " +
                        "LEFT JOIN ProductoCaracteristica pc ON pc.producto = p " +
                        "LEFT JOIN pc.caracteristica car " +
                        "WHERE (:texto IS NULL OR LOWER(p.nombre) LIKE LOWER(CONCAT('%', :texto, '%')) OR LOWER(p.descripcion) LIKE LOWER(CONCAT('%', :texto, '%'))) "
                        +
                        "AND (:categoriaId IS NULL OR c.id = :categoriaId) " +
                        "AND (:precioMin IS NULL OR p.precio >= :precioMin) " +
                        "AND (:precioMax IS NULL OR p.precio <= :precioMax) " +
                        "AND (:caracteristicaId IS NULL OR car.id = :caracteristicaId)")
        Page<Producto> busquedaAvanzada(
                        @Param("texto") String texto,
                        @Param("categoriaId") Long categoriaId,
                        @Param("precioMin") BigDecimal precioMin,
                        @Param("precioMax") BigDecimal precioMax,
                        @Param("caracteristicaId") Long caracteristicaId,
                        Pageable pageable);

        // Búsqueda por disponibilidad en fechas
        @Query("SELECT DISTINCT p FROM Producto p " +
                        "WHERE p.id NOT IN (" +
                        "    SELECT r.producto.id FROM Reserva r " +
                        "    WHERE r.estado.nombre = 'Confirmada' " +
                        "    AND ((r.fechaInicio <= :fechaFin AND r.fechaFin >= :fechaInicio))" +
                        ")")
        Page<Producto> findByDisponibilidadEnFechas(
                        @Param("fechaInicio") LocalDate fechaInicio,
                        @Param("fechaFin") LocalDate fechaFin,
                        Pageable pageable);

        // Buscar productos ordenados por popularidad (número de reservas)
        @Query("SELECT p, COUNT(r) as reservas FROM Producto p " +
                        "LEFT JOIN Reserva r ON p.id = r.producto.id " +
                        "GROUP BY p.id " +
                        "ORDER BY reservas DESC")
        Page<Producto> findAllOrderByPopularidad(Pageable pageable);

        // Buscar productos ordenados por puntuación promedio
        @Query("SELECT p, COALESCE(AVG(pun.puntuacion), 0) as promedio FROM Producto p " +
                        "LEFT JOIN Puntuacion pun ON p.id = pun.producto.id " +
                        "GROUP BY p.id " +
                        "ORDER BY promedio DESC")
        Page<Producto> findAllOrderByPuntuacionPromedio(Pageable pageable);

        // Buscar productos ordenados por fecha de creación (más recientes primero)
        @Query("SELECT p FROM Producto p ORDER BY p.fechaCreacion DESC")
        Page<Producto> findAllOrderByFechaCreacionDesc(Pageable pageable);
        
        /**
         * Obtener categorías más populares (con más productos)
         */
        @Query("SELECT p.categoria.id, COUNT(p) as total, p.categoria.titulo FROM Producto p " +
               "WHERE p.categoria IS NOT NULL " +
               "GROUP BY p.categoria.id, p.categoria.titulo ORDER BY total DESC")
        List<Object[]> findCategoriasMasPopulares();
}