package com.recore.dao;

import com.recore.modelo.Producto;
import com.recore.modelo.Categoria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.math.BigDecimal;

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
}