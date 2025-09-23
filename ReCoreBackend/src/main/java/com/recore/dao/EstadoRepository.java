package com.recore.dao;

import com.recore.modelo.Estado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA Repository para Estado
 * Gestiona las operaciones de base de datos para los estados de reservas
 */
@Repository
public interface EstadoRepository extends JpaRepository<Estado, Long> {
    
    /**
     * Buscar estado por nombre
     */
    Optional<Estado> findByNombre(String nombre);
    
    /**
     * Buscar estados activos
     */
    List<Estado> findByActivoTrue();
    
    /**
     * Verificar si existe un estado con el nombre dado
     */
    boolean existsByNombre(String nombre);
    
    /**
     * Buscar estados por nombre que contenga el texto (búsqueda parcial)
     */
    @Query("SELECT e FROM Estado e WHERE e.nombre LIKE %?1% AND e.activo = true")
    List<Estado> findByNombreContainingAndActivoTrue(String nombre);
}