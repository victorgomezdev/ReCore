package com.recore.servicio;

import com.recore.modelo.Caracteristica;
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

/**
 * Módulo 4 - Backend avanzado
 * Clase 10 - Inyección de dependencias
 * Servicio para la gestión de características
 */
@Service
@Transactional
public class CaracteristicaService implements BaseServicio<Caracteristica> {

    @Autowired
    private CaracteristicaRepository caracteristicaRepository;

    @Override
    public RespuestaBase<Caracteristica> guardar(Caracteristica caracteristica) {
        try {
            // Validar que el nombre no esté vacío
            if (caracteristica.getNombre() == null || caracteristica.getNombre().trim().isEmpty()) {
                return new RespuestaBase<Caracteristica>(null, "El nombre de la característica es obligatorio");
            }

            // Verificar si ya existe una característica con ese nombre
            Caracteristica caracteristicaExistente = caracteristicaRepository.findByNombre(caracteristica.getNombre().trim());

            // Si estamos creando una nueva característica
            if (caracteristica.getId() == null) {
                if (caracteristicaExistente != null) {
                    return new RespuestaBase<Caracteristica>(null, "Ya existe una característica con el nombre: " + caracteristica.getNombre());
                }
            } else {
                // Si estamos actualizando una característica existente
                if (caracteristicaExistente != null && !caracteristicaExistente.getId().equals(caracteristica.getId())) {
                    return new RespuestaBase<Caracteristica>(null,
                            "El nombre " + caracteristica.getNombre() + " ya está en uso por otra característica");
                }
            }

            // Limpiar espacios en blanco del nombre
            caracteristica.setNombre(caracteristica.getNombre().trim());
            
            // Limpiar espacios en blanco del icono si no es null
            if (caracteristica.getIcono() != null) {
                caracteristica.setIcono(caracteristica.getIcono().trim());
            }

            LocalDateTime now = LocalDateTime.now();
            if (caracteristica.getId() == null) {
                caracteristica.setFechaCreacion(now);
            }
            caracteristica.setFechaModificacion(now);
            
            return new RespuestaBase<Caracteristica>(caracteristicaRepository.save(caracteristica));
        } catch (Exception e) {
            return new RespuestaBase<Caracteristica>(null, "Error al guardar la característica: " + e.getMessage());
        }
    }

    @Override
    public RespuestaBase<Caracteristica> buscarPorId(Long id) {
        try {
            return new RespuestaBase<Caracteristica>(caracteristicaRepository.findById(id).orElse(null));
        } catch (Exception e) {
            return new RespuestaBase<Caracteristica>(null, "Error al buscar la característica: " + e.getMessage());
        }
    }

    @Override
    public RespuestaPaginada<Caracteristica> listarTodos(Pageable paginacion) {
        try {
            Page<Caracteristica> pagina = caracteristicaRepository.findAll(paginacion);
            RespuestaPaginada<Caracteristica> respuesta = new RespuestaPaginada<Caracteristica>(
                pagina.getContent(), 
                pagina.getTotalElements(), 
                pagina.getNumber(), 
                pagina.getSize()
            );
            respuesta.setMensaje("Características obtenidas exitosamente");
            return respuesta;
        } catch (Exception e) {
            return new RespuestaPaginada<Caracteristica>(null, "Error al listar características: " + e.getMessage());
        }
    }

    @Override
    public RespuestaBase<Void> eliminar(Long id) {
        try {
            if (!caracteristicaRepository.existsById(id)) {
                return new RespuestaBase<Void>("La característica con ID " + id + " no existe", true);
            }
            caracteristicaRepository.deleteById(id);
            return new RespuestaBase<Void>("Característica eliminada correctamente", false);
        } catch (Exception e) {
            return new RespuestaBase<Void>("Error al eliminar la característica: " + e.getMessage(), true);
        }
    }

    @Override
    public RespuestaBase<Boolean> existe(Long id) {
        try {
            return new RespuestaBase<Boolean>(caracteristicaRepository.existsById(id));
        } catch (Exception e) {
            return new RespuestaBase<Boolean>(false, "Error al verificar existencia: " + e.getMessage());
        }
    }

    // Métodos adicionales específicos para características
    public RespuestaBase<Caracteristica> buscarPorNombre(String nombre) {
        try {
            return new RespuestaBase<Caracteristica>(caracteristicaRepository.findByNombre(nombre));
        } catch (Exception e) {
            return new RespuestaBase<Caracteristica>(null, "Error al buscar por nombre: " + e.getMessage());
        }
    }

    public RespuestaPaginada<Caracteristica> buscarPorNombreContiene(String nombre, Pageable paginacion) {
        try {
            Page<Caracteristica> pagina = caracteristicaRepository.findByNombreContainingIgnoreCase(nombre, paginacion);
            RespuestaPaginada<Caracteristica> respuesta = new RespuestaPaginada<Caracteristica>(
                pagina.getContent(), 
                pagina.getTotalElements(), 
                pagina.getNumber(), 
                pagina.getSize()
            );
            respuesta.setMensaje("Búsqueda completada exitosamente");
            return respuesta;
        } catch (Exception e) {
            return new RespuestaPaginada<Caracteristica>(null, "Error al buscar características: " + e.getMessage());
        }
    }
}