package com.recore.servicio;

import com.recore.modelo.Favorito;
import com.recore.modelo.Usuario;
import com.recore.modelo.Producto;
import com.recore.repositorio.FavoritoRepository;
import com.recore.dao.UsuarioRepository;
import com.recore.dao.ProductoRepository;
import com.recore.util.RespuestaBase;
import com.recore.util.RespuestaPaginada;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * Servicio para la gestión de favoritos
 * Implementa la lógica de negocio y validaciones para la relación usuario-producto
 */
@Service
@Transactional
public class FavoritoService implements BaseServicio<Favorito> {

    @Autowired
    private FavoritoRepository favoritoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ProductoRepository productoRepository;

    @Override
    public RespuestaBase<Favorito> guardar(Favorito favorito) {
        try {
            // Validar que el usuario existe
            if (favorito.getUsuario() == null || favorito.getUsuario().getId() == null) {
                return new RespuestaBase<>(null, "El usuario es requerido");
            }

            Optional<Usuario> usuarioOpt = usuarioRepository.findById(favorito.getUsuario().getId());
            if (!usuarioOpt.isPresent()) {
                return new RespuestaBase<>(null, "El usuario con ID " + favorito.getUsuario().getId() + " no existe");
            }

            // Validar que el producto existe
            if (favorito.getProducto() == null || favorito.getProducto().getId() == null) {
                return new RespuestaBase<>(null, "El producto es requerido");
            }

            Optional<Producto> productoOpt = productoRepository.findById(favorito.getProducto().getId());
            if (!productoOpt.isPresent()) {
                return new RespuestaBase<>(null, "El producto con ID " + favorito.getProducto().getId() + " no existe");
            }

            // Verificar si ya existe la relación
            if (favorito.getId() == null) {
                boolean existe = favoritoRepository.existsByUsuarioIdAndProductoId(
                    favorito.getUsuario().getId(), 
                    favorito.getProducto().getId()
                );
                if (existe) {
                    return new RespuestaBase<>(null, "El producto ya está en favoritos del usuario");
                }
            }

            // Establecer las entidades completas
            favorito.setUsuario(usuarioOpt.get());
            favorito.setProducto(productoOpt.get());

            // Manejar fechas
            LocalDateTime now = LocalDateTime.now();
            if (favorito.getId() == null) {
                favorito.setFechaCreacion(now);
            }
            favorito.setFechaModificacion(now);

            Favorito favoritoGuardado = favoritoRepository.save(favorito);
            return new RespuestaBase<>(favoritoGuardado);

        } catch (Exception e) {
            return new RespuestaBase<>(null, "Error al guardar el favorito: " + e.getMessage());
        }
    }

    /**
     * Actualizar un favorito existente
     */
    public RespuestaBase<Favorito> actualizar(Long id, Favorito favorito) {
        try {
            Optional<Favorito> favoritoExistente = favoritoRepository.findById(id);
            if (!favoritoExistente.isPresent()) {
                return new RespuestaBase<>(null, "Favorito no encontrado con ID: " + id);
            }

            favorito.setId(id);
            favorito.setFechaCreacion(favoritoExistente.get().getFechaCreacion());
            return guardar(favorito);

        } catch (Exception e) {
            return new RespuestaBase<>(null, "Error al actualizar el favorito: " + e.getMessage());
        }
    }

    @Override
    public RespuestaBase<Favorito> buscarPorId(Long id) {
        try {
            Optional<Favorito> favorito = favoritoRepository.findById(id);
            if (favorito.isPresent()) {
                return new RespuestaBase<>(favorito.get());
            }
            return new RespuestaBase<>(null, "Favorito no encontrado con ID: " + id);
        } catch (Exception e) {
            return new RespuestaBase<>(null, "Error al buscar el favorito: " + e.getMessage());
        }
    }

    @Override
    public RespuestaPaginada<Favorito> listarTodos(Pageable pageable) {
        try {
            Page<Favorito> page = favoritoRepository.findAll(pageable);
            return new RespuestaPaginada<Favorito>(page.getContent(), page.getTotalElements(),
                    page.getNumber(), page.getSize());
        } catch (Exception e) {
            return new RespuestaPaginada<Favorito>(null, "Error al listar favoritos: " + e.getMessage());
        }
    }

    @Override
    public RespuestaBase<Void> eliminar(Long id) {
        try {
            if (!favoritoRepository.existsById(id)) {
                return new RespuestaBase<>(null, "Favorito no encontrado con ID: " + id);
            }
            favoritoRepository.deleteById(id);
            return new RespuestaBase<>(null, "Favorito eliminado correctamente");
        } catch (Exception e) {
            return new RespuestaBase<>(null, "Error al eliminar el favorito: " + e.getMessage());
        }
    }

    @Override
    public RespuestaBase<Boolean> existe(Long id) {
        try {
            boolean existe = favoritoRepository.existsById(id);
            return new RespuestaBase<>(existe);
        } catch (Exception e) {
            return new RespuestaBase<>(false, "Error al verificar existencia: " + e.getMessage());
        }
    }

    // Métodos específicos para favoritos

    /**
     * Crear un favorito usando IDs directamente
     */
    public RespuestaBase<Favorito> crearFavorito(Long usuarioId, Long productoId) {
        try {
            // Validar que el usuario existe
            Optional<Usuario> usuarioOpt = usuarioRepository.findById(usuarioId);
            if (!usuarioOpt.isPresent()) {
                return new RespuestaBase<>(null, "El usuario con ID " + usuarioId + " no existe");
            }

            // Validar que el producto existe
            Optional<Producto> productoOpt = productoRepository.findById(productoId);
            if (!productoOpt.isPresent()) {
                return new RespuestaBase<>(null, "El producto con ID " + productoId + " no existe");
            }

            // Verificar si ya existe la relación
            boolean existe = favoritoRepository.existsByUsuarioIdAndProductoId(usuarioId, productoId);
            if (existe) {
                return new RespuestaBase<>(null, "El producto ya está en favoritos del usuario");
            }

            // Crear el favorito
            Favorito favorito = new Favorito();
            favorito.setUsuario(usuarioOpt.get());
            favorito.setProducto(productoOpt.get());

            // Manejar fechas
            LocalDateTime now = LocalDateTime.now();
            favorito.setFechaCreacion(now);
            favorito.setFechaModificacion(now);

            // Guardar
            Favorito favoritoGuardado = favoritoRepository.save(favorito);
            return new RespuestaBase<>(favoritoGuardado, "Favorito creado exitosamente");

        } catch (Exception e) {
            return new RespuestaBase<>(null, "Error al crear favorito: " + e.getMessage());
        }
    }

    /**
     * Buscar todos los favoritos de un usuario
     */
    public RespuestaPaginada<Favorito> buscarFavoritosPorUsuario(Long usuarioId, Pageable pageable) {
        try {
            if (!usuarioRepository.existsById(usuarioId)) {
                return new RespuestaPaginada<Favorito>(null, "Usuario no encontrado con ID: " + usuarioId);
            }
            Page<Favorito> page = favoritoRepository.findByUsuarioId(usuarioId, pageable);
            return new RespuestaPaginada<Favorito>(page.getContent(), page.getTotalElements(),
                    page.getNumber(), page.getSize());
        } catch (Exception e) {
            return new RespuestaPaginada<Favorito>(null, "Error al buscar favoritos del usuario: " + e.getMessage());
        }
    }

    /**
     * Buscar usuarios que marcaron un producto como favorito
     */
    public RespuestaPaginada<Favorito> buscarUsuariosPorProducto(Long productoId, Pageable pageable) {
        try {
            if (!productoRepository.existsById(productoId)) {
                return new RespuestaPaginada<Favorito>(null, "Producto no encontrado con ID: " + productoId);
            }
            Page<Favorito> page = favoritoRepository.findByProductoId(productoId, pageable);
            return new RespuestaPaginada<Favorito>(page.getContent(), page.getTotalElements(),
                    page.getNumber(), page.getSize());
        } catch (Exception e) {
            return new RespuestaPaginada<Favorito>(null, "Error al buscar usuarios del producto: " + e.getMessage());
        }
    }

    /**
     * Buscar una relación específica entre usuario y producto
     */
    public RespuestaBase<Favorito> buscarRelacion(Long usuarioId, Long productoId) {
        try {
            Optional<Favorito> favorito = favoritoRepository.findByUsuarioIdAndProductoId(usuarioId, productoId);
            if (favorito.isPresent()) {
                return new RespuestaBase<>(favorito.get());
            }
            return new RespuestaBase<>(null, "No se encontró la relación entre usuario y producto");
        } catch (Exception e) {
            return new RespuestaBase<>(null, "Error al buscar la relación: " + e.getMessage());
        }
    }

    /**
     * Verificar si existe una relación entre usuario y producto
     */
    public RespuestaBase<Boolean> existeRelacion(Long usuarioId, Long productoId) {
        try {
            boolean existe = favoritoRepository.existsByUsuarioIdAndProductoId(usuarioId, productoId);
            return new RespuestaBase<>(existe);
        } catch (Exception e) {
            return new RespuestaBase<>(false, "Error al verificar la relación: " + e.getMessage());
        }
    }

    /**
     * Eliminar todos los favoritos de un usuario
     */
    public RespuestaBase<Void> eliminarFavoritosDeUsuario(Long usuarioId) {
        try {
            if (!usuarioRepository.existsById(usuarioId)) {
                return new RespuestaBase<>(null, "Usuario no encontrado con ID: " + usuarioId);
            }
            favoritoRepository.deleteByUsuarioId(usuarioId);
            return new RespuestaBase<>(null, "Favoritos del usuario eliminados correctamente");
        } catch (Exception e) {
            return new RespuestaBase<>(null, "Error al eliminar favoritos del usuario: " + e.getMessage());
        }
    }

    /**
     * Eliminar una relación específica entre usuario y producto
     */
    public RespuestaBase<Void> eliminarRelacion(Long usuarioId, Long productoId) {
        try {
            Optional<Favorito> favorito = favoritoRepository.findByUsuarioIdAndProductoId(usuarioId, productoId);
            if (!favorito.isPresent()) {
                return new RespuestaBase<>(null, "No se encontró la relación entre usuario y producto");
            }
            favoritoRepository.deleteById(favorito.get().getId());
            return new RespuestaBase<>(null, "Relación eliminada correctamente");
        } catch (Exception e) {
            return new RespuestaBase<>(null, "Error al eliminar la relación: " + e.getMessage());
        }
    }

    /**
     * Contar cuántos favoritos tiene un usuario
     */
    public RespuestaBase<Long> contarFavoritosDeUsuario(Long usuarioId) {
        try {
            if (!usuarioRepository.existsById(usuarioId)) {
                return new RespuestaBase<>(0L, "Usuario no encontrado con ID: " + usuarioId);
            }
            long count = favoritoRepository.countByUsuarioId(usuarioId);
            return new RespuestaBase<>(count);
        } catch (Exception e) {
            return new RespuestaBase<>(0L, "Error al contar favoritos: " + e.getMessage());
        }
    }

    /**
     * Contar cuántas veces un producto ha sido marcado como favorito
     */
    public RespuestaBase<Long> contarUsuariosConProducto(Long productoId) {
        try {
            if (!productoRepository.existsById(productoId)) {
                return new RespuestaBase<>(0L, "Producto no encontrado con ID: " + productoId);
            }
            long count = favoritoRepository.countByProductoId(productoId);
            return new RespuestaBase<>(count);
        } catch (Exception e) {
            return new RespuestaBase<>(0L, "Error al contar usuarios: " + e.getMessage());
        }
    }

    /**
     * Buscar favoritos por texto en el nombre del producto
     */
    public RespuestaPaginada<Favorito> buscarFavoritosPorTexto(Long usuarioId, String texto, Pageable pageable) {
        try {
            if (!usuarioRepository.existsById(usuarioId)) {
                return new RespuestaPaginada<Favorito>(null, "Usuario no encontrado con ID: " + usuarioId);
            }
            Page<Favorito> page = favoritoRepository.findByUsuarioIdAndProductoNombreContaining(usuarioId, texto, pageable);
            return new RespuestaPaginada<Favorito>(page.getContent(), page.getTotalElements(),
                    page.getNumber(), page.getSize());
        } catch (Exception e) {
            return new RespuestaPaginada<Favorito>(null, "Error al buscar favoritos por texto: " + e.getMessage());
        }
    }

    /**
     * Buscar favoritos por categoría del producto
     */
    public RespuestaPaginada<Favorito> buscarFavoritosPorCategoria(Long usuarioId, Long categoriaId, Pageable pageable) {
        try {
            if (!usuarioRepository.existsById(usuarioId)) {
                return new RespuestaPaginada<Favorito>(null, "Usuario no encontrado con ID: " + usuarioId);
            }
            Page<Favorito> page = favoritoRepository.findByUsuarioIdAndProductoCategoriaId(usuarioId, categoriaId, pageable);
            return new RespuestaPaginada<Favorito>(page.getContent(), page.getTotalElements(),
                    page.getNumber(), page.getSize());
        } catch (Exception e) {
            return new RespuestaPaginada<Favorito>(null, "Error al buscar favoritos por categoría: " + e.getMessage());
        }
    }
}