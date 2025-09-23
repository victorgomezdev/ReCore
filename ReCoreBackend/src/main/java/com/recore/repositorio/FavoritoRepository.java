package com.recore.repositorio;

import com.recore.modelo.Favorito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository para la gestión de favoritos
 * Maneja las operaciones de base de datos para la relación usuario-producto
 */
@Repository
public interface FavoritoRepository extends JpaRepository<Favorito, Long> {

    /**
     * Buscar todos los favoritos de un usuario específico
     */
    @Query("SELECT f FROM Favorito f WHERE f.usuario.id = :usuarioId")
    Page<Favorito> findByUsuarioId(@Param("usuarioId") Long usuarioId, Pageable pageable);

    /**
     * Buscar todos los usuarios que marcaron como favorito un producto específico
     */
    @Query("SELECT f FROM Favorito f WHERE f.producto.id = :productoId")
    Page<Favorito> findByProductoId(@Param("productoId") Long productoId, Pageable pageable);

    /**
     * Buscar una relación específica entre usuario y producto
     */
    @Query("SELECT f FROM Favorito f WHERE f.usuario.id = :usuarioId AND f.producto.id = :productoId")
    Optional<Favorito> findByUsuarioIdAndProductoId(@Param("usuarioId") Long usuarioId, @Param("productoId") Long productoId);

    /**
     * Verificar si existe una relación entre usuario y producto
     */
    @Query("SELECT COUNT(f) > 0 FROM Favorito f WHERE f.usuario.id = :usuarioId AND f.producto.id = :productoId")
    boolean existsByUsuarioIdAndProductoId(@Param("usuarioId") Long usuarioId, @Param("productoId") Long productoId);

    /**
     * Contar cuántos favoritos tiene un usuario
     */
    @Query("SELECT COUNT(f) FROM Favorito f WHERE f.usuario.id = :usuarioId")
    long countByUsuarioId(@Param("usuarioId") Long usuarioId);

    /**
     * Contar cuántas veces un producto ha sido marcado como favorito
     */
    @Query("SELECT COUNT(f) FROM Favorito f WHERE f.producto.id = :productoId")
    long countByProductoId(@Param("productoId") Long productoId);

    /**
     * Eliminar todos los favoritos de un usuario
     */
    @Query("DELETE FROM Favorito f WHERE f.usuario.id = :usuarioId")
    void deleteByUsuarioId(@Param("usuarioId") Long usuarioId);

    /**
     * Eliminar todos los favoritos de un producto
     */
    @Query("DELETE FROM Favorito f WHERE f.producto.id = :productoId")
    void deleteByProductoId(@Param("productoId") Long productoId);

    /**
     * Buscar favoritos por texto en el nombre del producto
     */
    @Query("SELECT f FROM Favorito f WHERE f.usuario.id = :usuarioId AND LOWER(f.producto.nombre) LIKE LOWER(CONCAT('%', :texto, '%'))")
    Page<Favorito> findByUsuarioIdAndProductoNombreContaining(@Param("usuarioId") Long usuarioId, @Param("texto") String texto, Pageable pageable);

    /**
     * Buscar favoritos por categoría del producto
     */
    @Query("SELECT f FROM Favorito f WHERE f.usuario.id = :usuarioId AND f.producto.categoria.id = :categoriaId")
    Page<Favorito> findByUsuarioIdAndProductoCategoriaId(@Param("usuarioId") Long usuarioId, @Param("categoriaId") Long categoriaId, Pageable pageable);
}