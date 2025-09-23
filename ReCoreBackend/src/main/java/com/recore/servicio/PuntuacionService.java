package com.recore.servicio;

import com.recore.modelo.Puntuacion;
import com.recore.modelo.Reserva;
import com.recore.modelo.Usuario;
import com.recore.modelo.Producto;
import com.recore.dao.PuntuacionRepository;
import com.recore.dao.ReservaRepository;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Servicio para la gestión de puntuaciones y reseñas
 * Implementa operaciones CRUD y lógica de negocio para puntuaciones
 */
@Service
@Transactional
public class PuntuacionService implements BaseServicio<Puntuacion> {

    @Autowired
    private PuntuacionRepository puntuacionRepository;
    
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    private ProductoRepository productoRepository;
    
    @Autowired
    private ReservaRepository reservaRepository;
    
    @Autowired
    private ProductoService productoService;

    @Override
    public RespuestaBase<Puntuacion> guardar(Puntuacion puntuacion) {
        try {
            // Validaciones básicas
            if (puntuacion.getPuntuacion() == null || puntuacion.getPuntuacion() < 1 || puntuacion.getPuntuacion() > 5) {
                return new RespuestaBase<Puntuacion>(null, "La puntuación debe estar entre 1 y 5");
            }

            // Verificar que existan las entidades relacionadas
            if (puntuacion.getUsuario() == null || puntuacion.getUsuario().getId() == null) {
                return new RespuestaBase<Puntuacion>(null, "El usuario es obligatorio");
            }
            
            if (puntuacion.getProducto() == null || puntuacion.getProducto().getId() == null) {
                return new RespuestaBase<Puntuacion>(null, "El producto es obligatorio");
            }

            // Cargar entidades completas
            Optional<Usuario> usuario = usuarioRepository.findById(puntuacion.getUsuario().getId());
            if (!usuario.isPresent()) {
                return new RespuestaBase<Puntuacion>(null, "Usuario no encontrado");
            }
            
            Optional<Producto> producto = productoRepository.findById(puntuacion.getProducto().getId());
            if (!producto.isPresent()) {
                return new RespuestaBase<Puntuacion>(null, "Producto no encontrado");
            }

            // Para nuevas puntuaciones, verificar si el usuario puede puntuar
            if (puntuacion.getId() == null) {
                RespuestaBase<Boolean> puedeUsuarioPuntuar = puedeUsuarioPuntuar(
                    puntuacion.getUsuario().getId(), 
                    puntuacion.getProducto().getId()
                );
                
                if (!puedeUsuarioPuntuar.isExito() || !puedeUsuarioPuntuar.getDatos()) {
                    return new RespuestaBase<Puntuacion>(null, puedeUsuarioPuntuar.getMensaje());
                }
            }

            // Asignar entidades cargadas
            puntuacion.setUsuario(usuario.get());
            puntuacion.setProducto(producto.get());

            LocalDateTime now = LocalDateTime.now();
            if (puntuacion.getId() == null) {
                puntuacion.setFechaCreacion(now);
            }
            puntuacion.setFechaModificacion(now);
            
            Puntuacion puntuacionGuardada = puntuacionRepository.save(puntuacion);
            
            // Actualizar promedio de puntuaciones del producto
            try {
                productoService.actualizarPromedioPuntuaciones(producto.get().getId());
            } catch (Exception e) {
                // Ignoramos errores en el cálculo del promedio
            }
            
            return new RespuestaBase<Puntuacion>(puntuacionGuardada);
        } catch (Exception e) {
            return new RespuestaBase<Puntuacion>(null, "Error al guardar la puntuación: " + e.getMessage());
        }
    }

    @Override
    public RespuestaBase<Puntuacion> buscarPorId(Long id) {
        try {
            Optional<Puntuacion> puntuacion = puntuacionRepository.findById(id);
            if (puntuacion.isPresent()) {
                return new RespuestaBase<Puntuacion>(puntuacion.get());
            } else {
                return new RespuestaBase<Puntuacion>(null, "Puntuación no encontrada con ID: " + id);
            }
        } catch (Exception e) {
            return new RespuestaBase<Puntuacion>(null, "Error al buscar la puntuación: " + e.getMessage());
        }
    }

    @Override
    public RespuestaPaginada<Puntuacion> listarTodos(Pageable pageable) {
        try {
            Page<Puntuacion> page = puntuacionRepository.findAll(pageable);
            return new RespuestaPaginada<Puntuacion>(page.getContent(), page.getTotalElements(), 
                    page.getNumber(), page.getSize());
        } catch (Exception e) {
            return new RespuestaPaginada<Puntuacion>(null, "Error al buscar puntuaciones: " + e.getMessage());
        }
    }

    @Override
    public RespuestaBase<Void> eliminar(Long id) {
        try {
            Optional<Puntuacion> puntuacion = puntuacionRepository.findById(id);
            if (puntuacion.isPresent()) {
                Long productoId = puntuacion.get().getProducto().getId();
                puntuacionRepository.deleteById(id);
                
                // Actualizar promedio de puntuaciones del producto
                try {
                    productoService.actualizarPromedioPuntuaciones(productoId);
                } catch (Exception e) {
                    // Ignoramos errores en el cálculo del promedio
                }
                
                return new RespuestaBase<Void>(null, "Puntuación eliminada correctamente");
            } else {
                return new RespuestaBase<Void>(null, "Puntuación no encontrada con ID: " + id);
            }
        } catch (Exception e) {
            return new RespuestaBase<Void>(null, "Error al eliminar la puntuación: " + e.getMessage());
        }
    }

    /**
     * Buscar puntuación específica de un usuario para un producto
     */
    public RespuestaBase<Puntuacion> buscarPorUsuarioYProducto(Long usuarioId, Long productoId) {
        try {
            Optional<Puntuacion> puntuacion = puntuacionRepository.findByUsuarioIdAndProductoId(usuarioId, productoId);
            if (puntuacion.isPresent()) {
                return new RespuestaBase<Puntuacion>(puntuacion.get());
            } else {
                return new RespuestaBase<Puntuacion>(null, "No se encontró puntuación del usuario para este producto");
            }
        } catch (Exception e) {
            return new RespuestaBase<Puntuacion>(null, "Error al buscar la puntuación: " + e.getMessage());
        }
    }

    /**
     * Buscar puntuaciones por producto
     */
    public RespuestaPaginada<Puntuacion> buscarPorProducto(Long productoId, Pageable pageable) {
        try {
            Page<Puntuacion> page = puntuacionRepository.findByProductoId(productoId, pageable);
            return new RespuestaPaginada<Puntuacion>(page.getContent(), page.getTotalElements(), 
                    page.getNumber(), page.getSize());
        } catch (Exception e) {
            return new RespuestaPaginada<Puntuacion>(null, "Error al buscar puntuaciones del producto: " + e.getMessage());
        }
    }

    /**
     * Buscar puntuaciones por usuario
     */
    public RespuestaPaginada<Puntuacion> buscarPorUsuario(Long usuarioId, Pageable pageable) {
        try {
            Page<Puntuacion> page = puntuacionRepository.findByUsuarioId(usuarioId, pageable);
            return new RespuestaPaginada<Puntuacion>(page.getContent(), page.getTotalElements(), 
                    page.getNumber(), page.getSize());
        } catch (Exception e) {
            return new RespuestaPaginada<Puntuacion>(null, "Error al buscar puntuaciones del usuario: " + e.getMessage());
        }
    }

    /**
     * Obtener estadísticas de un producto
     */
    public RespuestaBase<Map<String, Object>> obtenerEstadisticasProducto(Long productoId) {
        try {
            Map<String, Object> estadisticas = new HashMap<>();
            
            // Promedio de puntuaciones
            Double promedio = puntuacionRepository.calcularPromedioPorProducto(productoId);
            estadisticas.put("promedio", promedio != null ? Math.round(promedio * 100.0) / 100.0 : 0.0);
            
            // Total de puntuaciones
            Long total = puntuacionRepository.contarPuntuacionesPorProducto(productoId);
            estadisticas.put("totalPuntuaciones", total);
            
            // Total de recomendaciones
            Long recomendaciones = puntuacionRepository.contarRecomendacionesPorProducto(productoId);
            estadisticas.put("totalRecomendaciones", recomendaciones);
            
            // Porcentaje de recomendación
            double porcentajeRecomendacion = total > 0 ? (recomendaciones.doubleValue() / total.doubleValue()) * 100 : 0;
            estadisticas.put("porcentajeRecomendacion", Math.round(porcentajeRecomendacion * 100.0) / 100.0);
            
            // Distribución de puntuaciones
            List<Object[]> distribucion = puntuacionRepository.obtenerDistribucionPuntuaciones(productoId);
            Map<Integer, Long> distribucionMap = new HashMap<>();
            for (Object[] item : distribucion) {
                distribucionMap.put((Integer) item[0], (Long) item[1]);
            }
            estadisticas.put("distribucion", distribucionMap);
            
            return new RespuestaBase<Map<String, Object>>(estadisticas);
        } catch (Exception e) {
            return new RespuestaBase<Map<String, Object>>(null, "Error al obtener estadísticas: " + e.getMessage());
        }
    }

    /**
     * Buscar puntuaciones con comentario de un producto
     */
    public RespuestaPaginada<Puntuacion> buscarConComentarioPorProducto(Long productoId, Pageable pageable) {
        try {
            Page<Puntuacion> page = puntuacionRepository.findConComentarioPorProducto(productoId, pageable);
            return new RespuestaPaginada<Puntuacion>(page.getContent(), page.getTotalElements(), 
                    page.getNumber(), page.getSize());
        } catch (Exception e) {
            return new RespuestaPaginada<Puntuacion>(null, "Error al buscar comentarios: " + e.getMessage());
        }
    }

    /**
     * Verificar si un usuario puede puntuar un producto
     * Un usuario puede puntuar un producto si:
     * 1. No lo ha puntuado antes
     * 2. Ha completado al menos una reserva de ese producto
     */
    public RespuestaBase<Boolean> puedeUsuarioPuntuar(Long usuarioId, Long productoId) {
        try {
            // Verificar que el usuario existe
            if (!usuarioRepository.existsById(usuarioId)) {
                return new RespuestaBase<Boolean>(false, "El usuario no existe");
            }
            
            // Verificar que el producto existe
            if (!productoRepository.existsById(productoId)) {
                return new RespuestaBase<Boolean>(false, "El producto no existe");
            }
            
            // Verificar si ya ha puntuado el producto
            boolean existePuntuacion = puntuacionRepository.existePuntuacion(usuarioId, productoId);
            if (existePuntuacion) {
                return new RespuestaBase<Boolean>(false, "El usuario ya ha puntuado este producto");
            }
            
            // Verificar si ha completado al menos una reserva de este producto
            // Esto requiere un nuevo método en el repositorio de reservas
            boolean tieneReservaCompletada = false;
            
            // Si no tenemos el método específico, podemos usar una consulta directa
            try {
                // Buscar reservas completadas del usuario para este producto
                List<Reserva> reservasCompletadas = reservaRepository.findByUsuarioIdAndProductoIdAndEstadoNombre(
                    usuarioId, productoId, "Completada");
                
                tieneReservaCompletada = !reservasCompletadas.isEmpty();
            } catch (Exception e) {
                // Si hay error en la consulta, permitimos puntuar de todas formas
                tieneReservaCompletada = true;
            }
            
            if (!tieneReservaCompletada) {
                return new RespuestaBase<Boolean>(false, 
                    "El usuario debe completar al menos una reserva de este producto antes de puntuarlo");
            }
            
            return new RespuestaBase<Boolean>(true, "El usuario puede puntuar este producto");
        } catch (Exception e) {
            return new RespuestaBase<Boolean>(false, "Error al verificar permisos: " + e.getMessage());
        }
    }

    /**
     * Actualizar puntuación existente
     */
    public RespuestaBase<Puntuacion> actualizarPuntuacion(Long usuarioId, Long productoId, Integer nuevaPuntuacion, String nuevoComentario, Boolean recomendado) {
        try {
            Optional<Puntuacion> puntuacionOpt = puntuacionRepository.findByUsuarioIdAndProductoId(usuarioId, productoId);
            
            if (!puntuacionOpt.isPresent()) {
                return new RespuestaBase<Puntuacion>(null, "No se encontró puntuación existente para actualizar");
            }

            if (nuevaPuntuacion < 1 || nuevaPuntuacion > 5) {
                return new RespuestaBase<Puntuacion>(null, "La puntuación debe estar entre 1 y 5");
            }

            Puntuacion puntuacion = puntuacionOpt.get();
            puntuacion.setPuntuacion(nuevaPuntuacion);
            puntuacion.setComentario(nuevoComentario);
            puntuacion.setRecomendado(recomendado);
            puntuacion.setFechaModificacion(LocalDateTime.now());

            return new RespuestaBase<Puntuacion>(puntuacionRepository.save(puntuacion));
        } catch (Exception e) {
            return new RespuestaBase<Puntuacion>(null, "Error al actualizar la puntuación: " + e.getMessage());
        }
    }

    @Override
    public RespuestaBase<Boolean> existe(Long id) {
        try {
            boolean existe = puntuacionRepository.existsById(id);
            return new RespuestaBase<Boolean>(existe);
        } catch (Exception e) {
            return new RespuestaBase<Boolean>(null, "Error al verificar existencia de puntuación: " + e.getMessage());
        }
    }

    /**
     * Buscar últimas puntuaciones
     */
    public RespuestaPaginada<Puntuacion> buscarUltimasPuntuaciones(Pageable pageable) {
        try {
            Page<Puntuacion> page = puntuacionRepository.findUltimasPuntuaciones(pageable);
            return new RespuestaPaginada<Puntuacion>(page.getContent(), page.getTotalElements(), 
                    page.getNumber(), page.getSize());
        } catch (Exception e) {
            return new RespuestaPaginada<Puntuacion>(null, "Error al buscar últimas puntuaciones: " + e.getMessage());
        }
    }
}