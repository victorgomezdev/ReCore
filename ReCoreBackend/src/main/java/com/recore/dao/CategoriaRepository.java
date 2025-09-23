package com.recore.dao;

import com.recore.modelo.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Módulo 4 - Backend avanzado
 * Clase 11 - ORM con JPA/Hibernate
 * Spring Data JPA Repository para Categoría
 */
@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
    Categoria findByTitulo(String titulo);
}