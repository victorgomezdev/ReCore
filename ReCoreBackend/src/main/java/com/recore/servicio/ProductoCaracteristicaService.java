package com.recore.servicio;

import com.recore.modelo.ProductoCaracteristica;
import com.recore.modelo.Producto;
import com.recore.modelo.Caracteristica;
import com.recore.dao.ProductoCaracteristicaRepository;
import com.recore.dao.ProductoRepository;
import com.recore.dao.CaracteristicaRepository;
import com.recore.util.RespuestaBase;
import com.recore.util.RespuestaPaginada;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Servicio para la gestión de relaciones productos-características
 */
@Service
@Transactional
public class ProductoCaracteristicaService implements BaseServicio<ProductoCaracteristica> {

    @Autowired
    private ProductoCaracteristicaRepository productoCaracteristicaRepository;
    
    @Autowired
    private ProductoRepository productoRepository;
    
    @Autowired
    private CaracteristicaRepository caracteristicaRepository;

    @Override
    public RespuestaBase<ProductoCaracteristica> guardar(ProductoCaracteristica productoCaracteristica) {
        try {
            // Validar que el producto existe
            if (productoCaracteristica.getProducto() == null || productoCaracteristica.getProducto().getId() == null) {
                return new RespuestaBase<ProductoCaracteristica>(null, "El producto es obligatorio");
            }
            
            if (!productoRepository.existsById(productoCaracteristica.getProducto().getId())) {
                return new RespuestaBase<ProductoCaracteristica>(null, "El producto especificado no existe");
            }

            // Validar que la característica existe
            if (productoCaracteristica.getCaracteristica() == null || productoCaracteristica.getCaracteristica().getId() == null) {
                return new RespuestaBase<ProductoCaracteristica>(null, "La característica es obligatoria");
            }
            
            if (!caracteristicaRepository.existsById(productoCaracteristica.getCaracteristica().getId())) {
                return new RespuestaBase<ProductoCaracteristica>(null, "La característica especificada no existe");
            }

            // Verificar si ya existe la relación (solo para nuevas relaciones)
            if (productoCaracteristica.getId() == null) {
                boolean existeRelacion = productoCaracteristicaRepository.existsByProductoIdAndCaracteristicaId(
                    productoCaracteristica.getProducto().getId(), 
                    productoCaracteristica.getCaracteristica().getId()
                );
                
                if (existeRelacion) {
                    return new RespuestaBase<ProductoCaracteristica>(null, 
                        "Ya existe una relación entre este producto y esta característica");
                }
            }

            LocalDateTime now = LocalDateTime.now();
            if (productoCaracteristica.getId() == null) {
                productoCaracteristica.setFechaCreacion(now);
            }
            productoCaracteristica.setFechaModificacion(now);
            
            return new RespuestaBase<ProductoCaracteristica>(productoCaracteristicaRepository.save(productoCaracteristica));
        } catch (Exception e) {
            return new RespuestaBase<ProductoCaracteristica>(null, "Error al guardar la relación producto-característica: " + e.getMessage());
        }
    }

    @Override
    public RespuestaBase<ProductoCaracteristica> buscarPorId(Long id) {
        try {
            return new RespuestaBase<ProductoCaracteristica>(productoCaracteristicaRepository.findById(id).orElse(null));
        } catch (Exception e) {
            return new RespuestaBase<ProductoCaracteristica>(null, "Error al buscar la relación: " + e.getMessage());
        }
    }

    @Override
    public RespuestaPaginada<ProductoCaracteristica> listarTodos(Pageable paginacion) {
        try {
            Page<ProductoCaracteristica> pagina = productoCaracteristicaRepository.findAll(paginacion);
            return new RespuestaPaginada<ProductoCaracteristica>(pagina.getContent(), pagina.getTotalElements(),
                    pagina.getNumber(), pagina.getSize());
        } catch (Exception e) {
            return new RespuestaPaginada<ProductoCaracteristica>(null, "Error al listar relaciones: " + e.getMessage());
        }
    }

    @Override
    public RespuestaBase<Void> eliminar(Long id) {
        try {
            if (!productoCaracteristicaRepository.existsById(id)) {
                return new RespuestaBase<Void>("La relación no existe", true);
            }
            productoCaracteristicaRepository.deleteById(id);
            return new RespuestaBase<Void>("Relación eliminada correctamente", false);
        } catch (Exception e) {
            return new RespuestaBase<Void>("Error al eliminar la relación: " + e.getMessage(), true);
        }
    }

    @Override
    public RespuestaBase<Boolean> existe(Long id) {
        try {
            return new RespuestaBase<Boolean>(productoCaracteristicaRepository.existsById(id));
        } catch (Exception e) {
            return new RespuestaBase<Boolean>(false, "Error al verificar existencia: " + e.getMessage());
        }
    }

    // Métodos específicos para relaciones productos-características
    
    /**
     * Buscar todas las características de un producto específico
     */
    public RespuestaPaginada<ProductoCaracteristica> buscarCaracteristicasPorProducto(Long productoId, Pageable paginacion) {
        try {
            if (!productoRepository.existsById(productoId)) {
                return new RespuestaPaginada<ProductoCaracteristica>(null, "El producto especificado no existe");
            }
            
            Page<ProductoCaracteristica> pagina = productoCaracteristicaRepository.findByProductoId(productoId, paginacion);
            return new RespuestaPaginada<ProductoCaracteristica>(pagina.getContent(), pagina.getTotalElements(),
                    pagina.getNumber(), pagina.getSize());
        } catch (Exception e) {
            return new RespuestaPaginada<ProductoCaracteristica>(null, "Error al buscar características del producto: " + e.getMessage());
        }
    }

    /**
     * Buscar todos los productos que tienen una característica específica
     */
    public RespuestaPaginada<ProductoCaracteristica> buscarProductosPorCaracteristica(Long caracteristicaId, Pageable paginacion) {
        try {
            if (!caracteristicaRepository.existsById(caracteristicaId)) {
                return new RespuestaPaginada<ProductoCaracteristica>(null, "La característica especificada no existe");
            }
            
            Page<ProductoCaracteristica> pagina = productoCaracteristicaRepository.findByCaracteristicaId(caracteristicaId, paginacion);
            return new RespuestaPaginada<ProductoCaracteristica>(pagina.getContent(), pagina.getTotalElements(),
                    pagina.getNumber(), pagina.getSize());
        } catch (Exception e) {
            return new RespuestaPaginada<ProductoCaracteristica>(null, "Error al buscar productos con la característica: " + e.getMessage());
        }
    }

    /**
     * Buscar una relación específica entre producto y característica
     */
    public RespuestaBase<ProductoCaracteristica> buscarRelacion(Long productoId, Long caracteristicaId) {
        try {
            Optional<ProductoCaracteristica> relacion = productoCaracteristicaRepository.findByProductoIdAndCaracteristicaId(productoId, caracteristicaId);
            return new RespuestaBase<ProductoCaracteristica>(relacion.orElse(null));
        } catch (Exception e) {
            return new RespuestaBase<ProductoCaracteristica>(null, "Error al buscar la relación: " + e.getMessage());
        }
    }

    /**
     * Verificar si existe una relación entre producto y característica
     */
    public RespuestaBase<Boolean> existeRelacion(Long productoId, Long caracteristicaId) {
        try {
            boolean existe = productoCaracteristicaRepository.existsByProductoIdAndCaracteristicaId(productoId, caracteristicaId);
            return new RespuestaBase<Boolean>(existe);
        } catch (Exception e) {
            return new RespuestaBase<Boolean>(false, "Error al verificar la relación: " + e.getMessage());
        }
    }

    /**
     * Eliminar todas las características de un producto
     */
    public RespuestaBase<Void> eliminarCaracteristicasDeProducto(Long productoId) {
        try {
            if (!productoRepository.existsById(productoId)) {
                return new RespuestaBase<Void>("El producto especificado no existe", true);
            }
            
            productoCaracteristicaRepository.deleteByProductoId(productoId);
            return new RespuestaBase<Void>("Características del producto eliminadas correctamente", false);
        } catch (Exception e) {
            return new RespuestaBase<Void>("Error al eliminar características del producto: " + e.getMessage(), true);
        }
    }

    /**
     * Eliminar una relación específica entre producto y característica
     */
    public RespuestaBase<Void> eliminarRelacion(Long productoId, Long caracteristicaId) {
        try {
            if (!productoCaracteristicaRepository.existsByProductoIdAndCaracteristicaId(productoId, caracteristicaId)) {
                return new RespuestaBase<Void>("La relación no existe", true);
            }
            
            productoCaracteristicaRepository.deleteByProductoIdAndCaracteristicaId(productoId, caracteristicaId);
            return new RespuestaBase<Void>("Relación eliminada correctamente", false);
        } catch (Exception e) {
            return new RespuestaBase<Void>("Error al eliminar la relación: " + e.getMessage(), true);
        }
    }

    /**
     * Contar cuántas características tiene un producto
     */
    public RespuestaBase<Long> contarCaracteristicasDeProducto(Long productoId) {
        try {
            if (!productoRepository.existsById(productoId)) {
                return new RespuestaBase<Long>(null, "El producto especificado no existe");
            }
            
            long count = productoCaracteristicaRepository.countByProductoId(productoId);
            return new RespuestaBase<Long>(count);
        } catch (Exception e) {
            return new RespuestaBase<Long>(null, "Error al contar características: " + e.getMessage());
        }
    }

    /**
     * Contar cuántos productos tienen una característica específica
     */
    public RespuestaBase<Long> contarProductosConCaracteristica(Long caracteristicaId) {
        try {
            if (!caracteristicaRepository.existsById(caracteristicaId)) {
                return new RespuestaBase<Long>(null, "La característica especificada no existe");
            }
            
            long count = productoCaracteristicaRepository.countByCaracteristicaId(caracteristicaId);
            return new RespuestaBase<Long>(count);
        } catch (Exception e) {
            return new RespuestaBase<Long>(null, "Error al contar productos: " + e.getMessage());
        }
    }
}