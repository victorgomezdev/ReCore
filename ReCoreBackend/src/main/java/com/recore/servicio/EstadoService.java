package com.recore.servicio;

import com.recore.modelo.Estado;
import com.recore.dao.EstadoRepository;
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
 * Servicio para la gestión de estados de reservas
 * Implementa operaciones CRUD y lógica de negocio para estados
 */
@Service
@Transactional
public class EstadoService implements BaseServicio<Estado> {

    @Autowired
    private EstadoRepository estadoRepository;

    @Override
    public RespuestaBase<Estado> guardar(Estado estado) {
        try {
            // Verificar si ya existe un estado con ese nombre
            Optional<Estado> estadoExistente = estadoRepository.findByNombre(estado.getNombre());

            // Si estamos creando un nuevo estado
            if (estado.getId() == null) {
                if (estadoExistente.isPresent()) {
                    return new RespuestaBase<Estado>(null, "Ya existe un estado con el nombre: " + estado.getNombre());
                }
            } else {
                // Si estamos actualizando un estado existente
                if (estadoExistente.isPresent() && !estadoExistente.get().getId().equals(estado.getId())) {
                    return new RespuestaBase<Estado>(null,
                            "El nombre " + estado.getNombre() + " ya está en uso por otro estado");
                }
            }

            LocalDateTime now = LocalDateTime.now();
            if (estado.getId() == null) {
                estado.setFechaCreacion(now);
            }
            estado.setFechaModificacion(now);
            return new RespuestaBase<Estado>(estadoRepository.save(estado));
        } catch (Exception e) {
            return new RespuestaBase<Estado>(null, "Error al guardar el estado: " + e.getMessage());
        }
    }

    @Override
    public RespuestaBase<Estado> buscarPorId(Long id) {
        try {
            Optional<Estado> estado = estadoRepository.findById(id);
            if (estado.isPresent()) {
                return new RespuestaBase<Estado>(estado.get());
            } else {
                return new RespuestaBase<Estado>(null, "Estado no encontrado con ID: " + id);
            }
        } catch (Exception e) {
            return new RespuestaBase<Estado>(null, "Error al buscar el estado: " + e.getMessage());
        }
    }

    @Override
    public RespuestaPaginada<Estado> listarTodos(Pageable pageable) {
        try {
            Page<Estado> page = estadoRepository.findAll(pageable);
            return new RespuestaPaginada<Estado>(page.getContent(), page.getTotalElements(), page.getNumber(),
                    page.getSize());
        } catch (Exception e) {
            return new RespuestaPaginada<Estado>(null, "Error al buscar estados: " + e.getMessage());
        }
    }

    @Override
    public RespuestaBase<Void> eliminar(Long id) {
        try {
            Optional<Estado> estado = estadoRepository.findById(id);
            if (estado.isPresent()) {
                estadoRepository.deleteById(id);
                return new RespuestaBase<Void>(null);
            } else {
                return new RespuestaBase<Void>(null, "Estado no encontrado con ID: " + id);
            }
        } catch (Exception e) {
            return new RespuestaBase<Void>(null, "Error al eliminar el estado: " + e.getMessage());
        }
    }

    /**
     * Buscar estado por nombre
     */
    public RespuestaBase<Estado> buscarPorNombre(String nombre) {
        try {
            Optional<Estado> estado = estadoRepository.findByNombre(nombre);
            if (estado.isPresent()) {
                return new RespuestaBase<Estado>(estado.get());
            } else {
                return new RespuestaBase<Estado>(null, "Estado no encontrado con nombre: " + nombre);
            }
        } catch (Exception e) {
            return new RespuestaBase<Estado>(null, "Error al buscar el estado: " + e.getMessage());
        }
    }

    /**
     * Buscar todos los estados activos
     */
    public RespuestaBase<List<Estado>> buscarEstadosActivos() {
        try {
            List<Estado> estados = estadoRepository.findByActivoTrue();
            return new RespuestaBase<List<Estado>>(estados);
        } catch (Exception e) {
            return new RespuestaBase<List<Estado>>(null, "Error al buscar estados activos: " + e.getMessage());
        }
    }

    @Override
    public RespuestaBase<Boolean> existe(Long id) {
        try {
            boolean existe = estadoRepository.existsById(id);
            return new RespuestaBase<Boolean>(existe);
        } catch (Exception e) {
            return new RespuestaBase<Boolean>(null, "Error al verificar existencia del estado: " + e.getMessage());
        }
    }

    /**
     * Activar o desactivar un estado
     */
    public RespuestaBase<Estado> cambiarEstadoActivo(Long id, boolean activo) {
        try {
            Optional<Estado> estadoOpt = estadoRepository.findById(id);
            if (estadoOpt.isPresent()) {
                Estado estado = estadoOpt.get();
                estado.setActivo(activo);
                estado.setFechaModificacion(LocalDateTime.now());
                return new RespuestaBase<Estado>(estadoRepository.save(estado));
            } else {
                return new RespuestaBase<Estado>(null, "Estado no encontrado con ID: " + id);
            }
        } catch (Exception e) {
            return new RespuestaBase<Estado>(null, "Error al cambiar estado activo: " + e.getMessage());
        }
    }
}