package com.recore.dao;

import com.recore.modelo.Imagen;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/*
* Repositorio para manejar las operaciones de base de datos de las imágenes
* Incluye métodos específicos para búsquedas por producto y características
*/
@Repository
public interface ImagenRepository extends JpaRepository<Imagen, Long> {

	// Buscar todas las imágenes de un producto específico
	List<Imagen> findByProductoIdOrderByOrdenVisualizacionAsc(Long productoId);

	// Buscar todas las imágenes de un producto con paginación
	Page<Imagen> findByProductoIdOrderByOrdenVisualizacionAsc(Long productoId, Pageable pageable);

	// Buscar la imagen principal de un producto
	Optional<Imagen> findByProductoIdAndEsPrincipalTrue(Long productoId);

	// Buscar imágenes por producto y si es principal
	List<Imagen> findByProductoIdAndEsPrincipal(Long productoId, Boolean esPrincipal);

	// Contar imágenes de un producto
	long countByProductoId(Long productoId);

	// Buscar imágenes por URL (para evitar duplicados)
	Optional<Imagen> findByUrl(String url);

	// Buscar todas las imágenes principales
	@Query("SELECT i FROM Imagen i WHERE i.esPrincipal = true ORDER BY i.producto.nombre")
	List<Imagen> findAllImagenesPrincipales();

	// Buscar imágenes por rango de orden de visualización
	@Query("SELECT i FROM Imagen i WHERE i.producto.id = :productoId AND i.ordenVisualizacion BETWEEN :ordenMin AND :ordenMax ORDER BY i.ordenVisualizacion")
	List<Imagen> findByProductoIdAndOrdenVisualizacionBetween(
			@Param("productoId") Long productoId,
			@Param("ordenMin") Integer ordenMin,
			@Param("ordenMax") Integer ordenMax
	);

	// Obtener el máximo orden de visualización para un producto
	@Query("SELECT COALESCE(MAX(i.ordenVisualizacion), 0) FROM Imagen i WHERE i.producto.id = :productoId")
	Integer findMaxOrdenVisualizacionByProductoId(@Param("productoId") Long productoId);

	// Buscar imágenes por texto alternativo
	List<Imagen> findByAltTextContainingIgnoreCase(String altText);

	// Eliminar todas las imágenes de un producto
	void deleteByProductoId(Long productoId);

	// Verificar si existe una imagen principal para un producto
	boolean existsByProductoIdAndEsPrincipalTrue(Long productoId);
}