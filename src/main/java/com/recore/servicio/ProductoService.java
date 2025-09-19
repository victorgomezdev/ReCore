package com.recore.servicio;

import com.recore.modelo.Producto;
import com.recore.modelo.Categoria;
import com.recore.dao.ProductoRepository;
import com.recore.dao.CategoriaRepository;
import com.recore.util.RespuestaBase;
import com.recore.util.RespuestaPaginada;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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

    @Override
    public RespuestaBase<Producto> guardar(Producto producto) {
        try {
            // Verificar si ya existe un producto con ese nombre
            Producto productoExistente = productoRepository.findByNombre(producto.getNombre());

            // Si estamos creando un nuevo producto
            if (producto.getId() == null) {
                if (productoExistente != null) {
                    return new RespuestaBase<Producto>(null, "Ya existe un producto con el nombre: " + producto.getNombre());
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

    public RespuestaPaginada<Producto> buscarPorRangoPrecio(BigDecimal precioMin, BigDecimal precioMax, Pageable paginacion) {
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
}