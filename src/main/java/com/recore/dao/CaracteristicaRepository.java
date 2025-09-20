package com.recore.dao;

import com.recore.modelo.Caracteristica;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * Módulo 4 - Backend avanzado
 * Clase 11 - ORM con JPA/Hibernate
 * Spring Data JPA Repository para Caracteristica
 */
@Repository
public interface CaracteristicaRepository extends JpaRepository<Caracteristica, Long> {
    
    // Buscar característica por nombre exacto
    Caracteristica findByNombre(String nombre);
    
    // Buscar características que contengan texto en el nombre
    List<Caracteristica> findByNombreContaining(String nombre);
    
    // Buscar características que contengan texto en el nombre con paginación
    Page<Caracteristica> findByNombreContaining(String nombre, Pageable pageable);
    
    // Buscar características que contengan texto en el nombre (ignorando mayúsculas/minúsculas)
    List<Caracteristica> findByNombreContainingIgnoreCase(String nombre);
    
    // Buscar características que contengan texto en el nombre con paginación (ignorando mayúsculas/minúsculas)
    Page<Caracteristica> findByNombreContainingIgnoreCase(String nombre, Pageable pageable);
    
    // Verificar si existe una característica con el nombre dado
    boolean existsByNombre(String nombre);
    
    // Verificar si existe una característica con el nombre dado (ignorando mayúsculas/minúsculas)
    boolean existsByNombreIgnoreCase(String nombre);
}