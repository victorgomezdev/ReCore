package com.recore.dao;

import com.recore.modelo.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Módulo 4 - Backend avanzado
 * Clase 11 - ORM con JPA/Hibernate
 * Spring Data JPA Repository para Usuario
 */
@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
	Usuario findByEmail(String email);
	
	/**
	 * Contar usuarios por rol (admin o no)
	 */
	Long countByEsAdmin(Boolean esAdmin);
	
	/**
	 * Buscar usuarios por rol (admin o no)
	 */
	List<Usuario> findByEsAdmin(Boolean esAdmin);
}
