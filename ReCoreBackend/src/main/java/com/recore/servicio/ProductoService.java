package com.recore.servicio;

import com.recore.modelo.Producto;
import com.recore.modelo.Categoria;
import com.recore.dao.ProductoRepository;
import com.recore.dao.CategoriaRepository;
import com.recore.dao.PuntuacionRepository;
import com.recore.util.RespuestaBase;
import com.recore.util.RespuestaPaginada;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.math.BigDecimal;

/**
 * Servicio para la gestión de productos
 */
@Service
@Transactional
public class ProductoService implements BaseServicio<Producto> {

    @Autowired
    private ProductoRepository productoRepository;
    
    @Autowired
    private CategoriaRepository categoriaRepository;
    
    @Autowired
    private PuntuacionRepository puntuacionRepository;

    @Override
    public RespuestaBase<Producto> guardar(Producto producto) {
        try {
            // Verificar si ya existe un producto con ese nombre
            Producto productoExistente = productoRepository.findByNombre(producto.getNombre());

            // Si estamos creando un nuevo producto
            if (producto.getId() == null) {
                if (productoExistente != null) {
                    return new RespuestaBase<Producto>(null,
                            "Ya existe un producto con el nombre: " + producto.getNombre());
                }
            } else {
                // Si estamos actualizando un producto existente
                if (productoExistente != null && !productoExistente.getId().equals(producto.getId())) {
                    return new RespuestaBase<Producto>(null,
                            "El nombre " + producto.getNombre() + " ya está en uso por otro producto");
                }
            }

            // Validar precio
            if (producto.getPrecio() == null || producto.getPrecio().compareTo(BigDecimal.ZERO) <= 0) {
                return new RespuestaBase<Producto>(null, "El precio debe ser mayor a 0");
            }

            // Verificar que la categoría existe
            if (producto.getCategoria() != null && producto.getCategoria().getId() != null) {
                if (!categoriaRepository.existsById(producto.getCategoria().getId())) {
                    return new RespuestaBase<Producto>(null, "La categoría especificada no existe");
                }
            }

            LocalDateTime now = LocalDateTime.now();
            if (producto.getId() == null) {
                producto.setFechaCreacion(now);
            }
            producto.setFechaModificacion(now);
            return new RespuestaBase<Producto>(productoRepository.save(producto));
        } catch (Exception e) {
            return new RespuestaBase<Producto>(null, "Error al guardar el producto: " + e.getMessage());
        }
    }

    @Override
    public RespuestaBase<Producto> buscarPorId(Long id) {
        try {
            return new RespuestaBase<Producto>(productoRepository.findById(id).orElse(null));
        } catch (Exception e) {
            return new RespuestaBase<Producto>(null, "Error al buscar el producto: " + e.getMessage());
        }
    }

    @Override
    public RespuestaPaginada<Producto> listarTodos(Pageable paginacion) {
        try {
            Page<Producto> pagina = productoRepository.findAll(paginacion);
            return new RespuestaPaginada<Producto>(pagina.getContent(), pagina.getTotalElements(),
                    pagina.getNumber(), pagina.getSize());
        } catch (Exception e) {
            return new RespuestaPaginada<Producto>(null, "Error al listar productos: " + e.getMessage());
        }
    }

    @Override
    public RespuestaBase<Void> eliminar(Long id) {
        try {
            productoRepository.deleteById(id);
            return new RespuestaBase<Void>("Producto eliminado correctamente", false);
        } catch (Exception e) {
            return new RespuestaBase<Void>("Error al eliminar el producto: " + e.getMessage(), true);
        }
    }

    @Override
    public RespuestaBase<Boolean> existe(Long id) {
        try {
            return new RespuestaBase<Boolean>(productoRepository.existsById(id));
        } catch (Exception e) {
            return new RespuestaBase<Boolean>(false, "Error al verificar existencia: " + e.getMessage());
        }
    }

    // Métodos específicos para productos
    public RespuestaBase<Producto> buscarPorNombre(String nombre) {
        try {
            return new RespuestaBase<Producto>(productoRepository.findByNombre(nombre));
        } catch (Exception e) {
            return new RespuestaBase<Producto>(null, "Error al buscar por nombre: " + e.getMessage());
        }
    }

    public RespuestaPaginada<Producto> buscarPorCategoria(Long categoriaId, Pageable paginacion) {
        try {
            Categoria categoria = categoriaRepository.findById(categoriaId).orElse(null);
            if (categoria == null) {
                return new RespuestaPaginada<Producto>(null, "Categoría no encontrada");
            }
            Page<Producto> pagina = productoRepository.findByCategoria(categoria, paginacion);
            return new RespuestaPaginada<Producto>(pagina.getContent(), pagina.getTotalElements(),
                    pagina.getNumber(), pagina.getSize());
        } catch (Exception e) {
            return new RespuestaPaginada<Producto>(null, "Error al buscar por categoría: " + e.getMessage());
        }
    }

    public RespuestaPaginada<Producto> buscarPorRangoPrecio(BigDecimal precioMin, BigDecimal precioMax,
            Pageable paginacion) {
        try {
            Page<Producto> pagina = productoRepository.findByPrecioBetween(precioMin, precioMax, paginacion);
            return new RespuestaPaginada<Producto>(pagina.getContent(), pagina.getTotalElements(),
                    pagina.getNumber(), pagina.getSize());
        } catch (Exception e) {
            return new RespuestaPaginada<Producto>(null, "Error al buscar por rango de precio: " + e.getMessage());
        }
    }

    public RespuestaPaginada<Producto> buscarPorNombreContiene(String nombre, Pageable paginacion) {
        try {
            Page<Producto> pagina = productoRepository.findByNombreContaining(nombre, paginacion);
            return new RespuestaPaginada<Producto>(pagina.getContent(), pagina.getTotalElements(),
                    pagina.getNumber(), pagina.getSize());
        } catch (Exception e) {
            return new RespuestaPaginada<Producto>(null, "Error al buscar por nombre que contiene: " + e.getMessage());
        }
    }

    /**
     * Buscar productos por descripción que contenga el texto
     */
    public RespuestaPaginada<Producto> buscarPorDescripcionContiene(String descripcion, Pageable paginacion) {
        try {
            Page<Producto> pagina = productoRepository.findByDescripcionContaining(descripcion, paginacion);
            return new RespuestaPaginada<Producto>(pagina.getContent(), pagina.getTotalElements(),
                    pagina.getNumber(), pagina.getSize());
        } catch (Exception e) {
            return new RespuestaPaginada<Producto>(null,
                    "Error al buscar por descripción que contiene: " + e.getMessage());
        }
    }

    /**
     * Buscar productos por nombre o descripción que contenga el texto
     */
    public RespuestaPaginada<Producto> buscarPorTexto(String texto, Pageable paginacion) {
        try {
            if (texto == null || texto.trim().isEmpty()) {
                return listarTodos(paginacion);
            }

            Page<Producto> pagina = productoRepository.findByNombreOrDescripcionContaining(texto, paginacion);
            return new RespuestaPaginada<Producto>(pagina.getContent(), pagina.getTotalElements(),
                    pagina.getNumber(), pagina.getSize());
        } catch (Exception e) {
            return new RespuestaPaginada<Producto>(null, "Error al buscar por texto: " + e.getMessage());
        }
    }

    /**
     * Búsqueda avanzada de productos con múltiples criterios
     */
    public RespuestaPaginada<Producto> busquedaAvanzada(
            String texto,
            Long categoriaId,
            BigDecimal precioMin,
            BigDecimal precioMax,
            Long caracteristicaId,
            Pageable paginacion) {
        try {
            // Validar que la categoría existe si se especifica
            if (categoriaId != null && !categoriaRepository.existsById(categoriaId)) {
                return new RespuestaPaginada<Producto>(null, "La categoría especificada no existe");
            }

            // Validar rango de precios
            if (precioMin != null && precioMax != null && precioMin.compareTo(precioMax) > 0) {
                return new RespuestaPaginada<Producto>(null,
                        "El precio mínimo no puede ser mayor que el precio máximo");
            }

            Page<Producto> pagina = productoRepository.busquedaAvanzada(
                    texto, categoriaId, precioMin, precioMax, caracteristicaId, paginacion);

            return new RespuestaPaginada<Producto>(pagina.getContent(), pagina.getTotalElements(),
                    pagina.getNumber(), pagina.getSize());
        } catch (Exception e) {
            return new RespuestaPaginada<Producto>(null, "Error en búsqueda avanzada: " + e.getMessage());
        }
    }

    /**
     * Buscar productos disponibles en un rango de fechas
     */
    public RespuestaPaginada<Producto> buscarPorDisponibilidadEnFechas(
            LocalDate fechaInicio,
            LocalDate fechaFin,
            Pageable paginacion) {
        try {
            // Validar fechas
            if (fechaInicio == null || fechaFin == null) {
                return new RespuestaPaginada<Producto>(null, "Las fechas de inicio y fin son obligatorias");
            }

            if (fechaInicio.isAfter(fechaFin)) {
                return new RespuestaPaginada<Producto>(null,
                        "La fecha de inicio no puede ser posterior a la fecha de fin");
            }

            if (fechaInicio.isBefore(LocalDate.now())) {
                return new RespuestaPaginada<Producto>(null, "La fecha de inicio no puede ser anterior a hoy");
            }

            Page<Producto> pagina = productoRepository.findByDisponibilidadEnFechas(fechaInicio, fechaFin, paginacion);

            return new RespuestaPaginada<Producto>(pagina.getContent(), pagina.getTotalElements(),
                    pagina.getNumber(), pagina.getSize());
        } catch (Exception e) {
            return new RespuestaPaginada<Producto>(null, "Error al buscar por disponibilidad: " + e.getMessage());
        }
    }

    /**
     * Listar productos ordenados por popularidad (número de reservas)
     */
    public RespuestaPaginada<Producto> listarPorPopularidad(Pageable paginacion) {
        try {
            Page<Producto> pagina = productoRepository.findAllOrderByPopularidad(paginacion);
            return new RespuestaPaginada<Producto>(pagina.getContent(), pagina.getTotalElements(),
                    pagina.getNumber(), pagina.getSize());
        } catch (Exception e) {
            return new RespuestaPaginada<Producto>(null, "Error al listar por popularidad: " + e.getMessage());
        }
    }

    /**
     * Listar productos ordenados por puntuación promedio
     */
    public RespuestaPaginada<Producto> listarPorPuntuacion(Pageable paginacion) {
        try {
            Page<Producto> pagina = productoRepository.findAllOrderByPuntuacionPromedio(paginacion);
            return new RespuestaPaginada<Producto>(pagina.getContent(), pagina.getTotalElements(),
                    pagina.getNumber(), pagina.getSize());
        } catch (Exception e) {
            return new RespuestaPaginada<Producto>(null, "Error al listar por puntuación: " + e.getMessage());
        }
    }

    /**
     * Listar productos ordenados por fecha de creación (más recientes primero)
     */
    public RespuestaPaginada<Producto> listarPorFechaCreacion(Pageable paginacion) {
        try {
            Page<Producto> pagina = productoRepository.findAllOrderByFechaCreacionDesc(paginacion);
            return new RespuestaPaginada<Producto>(pagina.getContent(), pagina.getTotalElements(),
                    pagina.getNumber(), pagina.getSize());
        } catch (Exception e) {
            return new RespuestaPaginada<Producto>(null, "Error al listar por fecha de creación: " + e.getMessage());
        }
    }

    /**
     * Actualizar el promedio de puntuaciones de un producto
     */
    public RespuestaBase<Producto> actualizarPromedioPuntuaciones(Long productoId) {
        try {
            Optional<Producto> productoOpt = productoRepository.findById(productoId);
            if (!productoOpt.isPresent()) {
                return new RespuestaBase<Producto>(null, "Producto no encontrado");
            }

            Producto producto = productoOpt.get();

            // Obtener el promedio de puntuaciones
            Double promedio = puntuacionRepository.calcularPromedioPorProducto(productoId);

            // Obtener el total de puntuaciones
            Long total = puntuacionRepository.contarPuntuacionesPorProducto(productoId);

            // Actualizar el producto
            producto.setPuntuacionPromedio(promedio != null ? promedio : 0.0);
            producto.setTotalPuntuaciones(total != null ? total.intValue() : 0);
            producto.setFechaModificacion(LocalDateTime.now());

            // Guardar el producto
            return new RespuestaBase<Producto>(productoRepository.save(producto));
        } catch (Exception e) {
            return new RespuestaBase<Producto>(null, "Error al actualizar promedio de puntuaciones: " + e.getMessage());
        }
    }

    /**
     * Actualizar todos los promedios de puntuaciones
     */
    @Transactional
    public RespuestaBase<Integer> actualizarTodosLosPromediosPuntuaciones() {
        try {
            List<Producto> productos = productoRepository.findAll();
            int actualizados = 0;

            for (Producto producto : productos) {
                try {
                    // Obtener el promedio de puntuaciones
                    Double promedio = puntuacionRepository.calcularPromedioPorProducto(producto.getId());

                    // Obtener el total de puntuaciones
                    Long total = puntuacionRepository.contarPuntuacionesPorProducto(producto.getId());

                    // Actualizar el producto
                    producto.setPuntuacionPromedio(promedio != null ? promedio : 0.0);
                    producto.setTotalPuntuaciones(total != null ? total.intValue() : 0);
                    producto.setFechaModificacion(LocalDateTime.now());

                    // Guardar el producto
                    productoRepository.save(producto);
                    actualizados++;
                } catch (Exception e) {
                    // Ignorar errores individuales y continuar con el siguiente producto
                }
            }

            return new RespuestaBase<Integer>(actualizados, "Se actualizaron " + actualizados + " productos");
        } catch (Exception e) {
            return new RespuestaBase<Integer>(0, "Error al actualizar todos los promedios: " + e.getMessage());
        }
    }
}