package com.recore.dao;

import com.recore.modelo.ProductoCaracteristica;
import com.recore.modelo.Producto;
import com.recore.modelo.Caracteristica;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

/**
 * Repository para la gestión de relaciones productos-características
 */
@Repository
public interface ProductoCaracteristicaRepository extends JpaRepository<ProductoCaracteristica, Long> {
    
    // Buscar todas las características de un producto específico
    List<ProductoCaracteristica> findByProducto(Producto producto);
    
    // Buscar todas las características de un producto por ID
    List<ProductoCaracteristica> findByProductoId(Long productoId);
    
    // Buscar todas las características de un producto por ID con paginación
    Page<ProductoCaracteristica> findByProductoId(Long productoId, Pageable pageable);
    
    // Buscar todos los productos que tienen una característica específica
    List<ProductoCaracteristica> findByCaracteristica(Caracteristica caracteristica);
    
    // Buscar todos los productos que tienen una característica por ID
    List<ProductoCaracteristica> findByCaracteristicaId(Long caracteristicaId);
    
    // Buscar todos los productos que tienen una característica por ID con paginación
    Page<ProductoCaracteristica> findByCaracteristicaId(Long caracteristicaId, Pageable pageable);
    
    // Buscar una relación específica entre producto y característica
    Optional<ProductoCaracteristica> findByProductoIdAndCaracteristicaId(Long productoId, Long caracteristicaId);
    
    // Verificar si existe una relación entre producto y característica
    boolean existsByProductoIdAndCaracteristicaId(Long productoId, Long caracteristicaId);
    
    // Eliminar todas las características de un producto
    void deleteByProductoId(Long productoId);
    
    // Eliminar todos los productos que tienen una característica específica
    void deleteByCaracteristicaId(Long caracteristicaId);
    
    // Eliminar una relación específica entre producto y característica
    void deleteByProductoIdAndCaracteristicaId(Long productoId, Long caracteristicaId);
    
    // Contar cuántas características tiene un producto
    long countByProductoId(Long productoId);
    
    // Contar cuántos productos tienen una característica específica
    long countByCaracteristicaId(Long caracteristicaId);
    
    // Buscar productos que tengan múltiples características específicas
    @Query("SELECT pc FROM ProductoCaracteristica pc WHERE pc.caracteristica.id IN :caracteristicaIds")
    List<ProductoCaracteristica> findByCaracteristicaIdIn(@Param("caracteristicaIds") List<Long> caracteristicaIds);
    
    // Buscar productos que tengan múltiples características específicas con paginación
    @Query("SELECT pc FROM ProductoCaracteristica pc WHERE pc.caracteristica.id IN :caracteristicaIds")
    Page<ProductoCaracteristica> findByCaracteristicaIdIn(@Param("caracteristicaIds") List<Long> caracteristicaIds, Pageable pageable);
    
    // Buscar características que contengan texto en el nombre para un producto específico
    @Query("SELECT pc FROM ProductoCaracteristica pc WHERE pc.producto.id = :productoId AND LOWER(pc.caracteristica.nombre) LIKE LOWER(CONCAT('%', :nombre, '%'))")
    List<ProductoCaracteristica> findByProductoIdAndCaracteristicaNombreContaining(@Param("productoId") Long productoId, @Param("nombre") String nombre);
    
    // Buscar productos que contengan texto en el nombre y tengan una característica específica
    @Query("SELECT pc FROM ProductoCaracteristica pc WHERE pc.caracteristica.id = :caracteristicaId AND LOWER(pc.producto.nombre) LIKE LOWER(CONCAT('%', :nombre, '%'))")
    List<ProductoCaracteristica> findByCaracteristicaIdAndProductoNombreContaining(@Param("caracteristicaId") Long caracteristicaId, @Param("nombre") String nombre);
}