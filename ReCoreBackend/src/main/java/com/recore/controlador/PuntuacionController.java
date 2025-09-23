package com.recore.controlador;

import com.recore.modelo.Puntuacion;
import com.recore.servicio.PuntuacionService;
import com.recore.util.RespuestaBase;
import com.recore.util.RespuestaPaginada;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.Map;

/**
 * Controlador REST para la gestión de puntuaciones y reseñas
 * Proporciona endpoints para operaciones CRUD y consultas específicas
 */
@RestController
@RequestMapping("/api/puntuaciones")
@CrossOrigin(origins = "*")
public class PuntuacionController {

    @Autowired
    private PuntuacionService puntuacionService;

    /**
     * Crear nueva puntuación
     */
    @PostMapping
    public ResponseEntity<RespuestaBase<Puntuacion>> crear(@Valid @RequestBody Puntuacion puntuacion) {
        RespuestaBase<Puntuacion> respuesta = puntuacionService.guardar(puntuacion);
        
        if (respuesta.isExito()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(respuesta);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
        }
    }

    /**
     * Actualizar puntuación existente
     */
    @PutMapping("/{id}")
    public ResponseEntity<RespuestaBase<Puntuacion>> actualizar(@PathVariable Long id, @Valid @RequestBody Puntuacion puntuacion) {
        puntuacion.setId(id);
        RespuestaBase<Puntuacion> respuesta = puntuacionService.guardar(puntuacion);
        
        if (respuesta.isExito()) {
            return ResponseEntity.ok(respuesta);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
        }
    }

    /**
     * Actualizar puntuación por usuario y producto
     */
    @PutMapping("/usuario/{usuarioId}/producto/{productoId}")
    public ResponseEntity<RespuestaBase<Puntuacion>> actualizarPorUsuarioYProducto(
            @PathVariable Long usuarioId,
            @PathVariable Long productoId,
            @RequestParam Integer puntuacion,
            @RequestParam(required = false) String comentario,
            @RequestParam(required = false, defaultValue = "true") Boolean recomendado) {
        
        RespuestaBase<Puntuacion> respuesta = puntuacionService.actualizarPuntuacion(
            usuarioId, productoId, puntuacion, comentario, recomendado);
        
        if (respuesta.isExito()) {
            return ResponseEntity.ok(respuesta);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
        }
    }

    /**
     * Eliminar puntuación
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<RespuestaBase<Void>> eliminar(@PathVariable Long id) {
        RespuestaBase<Void> respuesta = puntuacionService.eliminar(id);
        
        if (respuesta.isExito()) {
            return ResponseEntity.ok(respuesta);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
        }
    }

    /**
     * Buscar puntuación por ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<RespuestaBase<Puntuacion>> buscarPorId(@PathVariable Long id) {
        RespuestaBase<Puntuacion> respuesta = puntuacionService.buscarPorId(id);
        
        if (respuesta.isExito()) {
            return ResponseEntity.ok(respuesta);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
        }
    }

    /**
     * Listar todas las puntuaciones con paginación
     */
    @GetMapping
    public ResponseEntity<RespuestaPaginada<Puntuacion>> listarTodos(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "fechaCreacion") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir) {
        
        Sort sort = sortDir.equalsIgnoreCase("desc") ? 
            Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        
        RespuestaPaginada<Puntuacion> respuesta = puntuacionService.listarTodos(pageable);
        
        if (respuesta.isExito()) {
            return ResponseEntity.ok(respuesta);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(respuesta);
        }
    }

    /**
     * Buscar puntuación específica de un usuario para un producto
     */
    @GetMapping("/usuario/{usuarioId}/producto/{productoId}")
    public ResponseEntity<RespuestaBase<Puntuacion>> buscarPorUsuarioYProducto(
            @PathVariable Long usuarioId,
            @PathVariable Long productoId) {
        
        RespuestaBase<Puntuacion> respuesta = puntuacionService.buscarPorUsuarioYProducto(usuarioId, productoId);
        
        if (respuesta.isExito()) {
            return ResponseEntity.ok(respuesta);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
        }
    }

    /**
     * Buscar puntuaciones por producto
     */
    @GetMapping("/producto/{productoId}")
    public ResponseEntity<RespuestaPaginada<Puntuacion>> buscarPorProducto(
            @PathVariable Long productoId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "fechaCreacion") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir) {
        
        Sort sort = sortDir.equalsIgnoreCase("desc") ? 
            Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        
        RespuestaPaginada<Puntuacion> respuesta = puntuacionService.buscarPorProducto(productoId, pageable);
        
        if (respuesta.isExito()) {
            return ResponseEntity.ok(respuesta);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(respuesta);
        }
    }

    /**
     * Buscar puntuaciones por usuario
     */
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<RespuestaPaginada<Puntuacion>> buscarPorUsuario(
            @PathVariable Long usuarioId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "fechaCreacion") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir) {
        
        Sort sort = sortDir.equalsIgnoreCase("desc") ? 
            Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        
        RespuestaPaginada<Puntuacion> respuesta = puntuacionService.buscarPorUsuario(usuarioId, pageable);
        
        if (respuesta.isExito()) {
            return ResponseEntity.ok(respuesta);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(respuesta);
        }
    }

    /**
     * Obtener estadísticas de un producto
     */
    @GetMapping("/producto/{productoId}/estadisticas")
    public ResponseEntity<RespuestaBase<Map<String, Object>>> obtenerEstadisticasProducto(@PathVariable Long productoId) {
        RespuestaBase<Map<String, Object>> respuesta = puntuacionService.obtenerEstadisticasProducto(productoId);
        
        if (respuesta.isExito()) {
            return ResponseEntity.ok(respuesta);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(respuesta);
        }
    }

    /**
     * Buscar puntuaciones con comentario de un producto
     */
    @GetMapping("/producto/{productoId}/comentarios")
    public ResponseEntity<RespuestaPaginada<Puntuacion>> buscarComentariosPorProducto(
            @PathVariable Long productoId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "fechaCreacion") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir) {
        
        Sort sort = sortDir.equalsIgnoreCase("desc") ? 
            Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        
        RespuestaPaginada<Puntuacion> respuesta = puntuacionService.buscarConComentarioPorProducto(productoId, pageable);
        
        if (respuesta.isExito()) {
            return ResponseEntity.ok(respuesta);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(respuesta);
        }
    }

    /**
     * Verificar si un usuario puede puntuar un producto
     */
    @GetMapping("/usuario/{usuarioId}/producto/{productoId}/puede-puntuar")
    public ResponseEntity<RespuestaBase<Boolean>> puedeUsuarioPuntuar(
            @PathVariable Long usuarioId,
            @PathVariable Long productoId) {
        
        RespuestaBase<Boolean> respuesta = puntuacionService.puedeUsuarioPuntuar(usuarioId, productoId);
        
        return ResponseEntity.ok(respuesta);
    }

    /**
     * Buscar últimas puntuaciones del sistema
     */
    @GetMapping("/ultimas")
    public ResponseEntity<RespuestaPaginada<Puntuacion>> buscarUltimasPuntuaciones(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        
        Pageable pageable = PageRequest.of(page, size, Sort.by("fechaCreacion").descending());
        
        RespuestaPaginada<Puntuacion> respuesta = puntuacionService.buscarUltimasPuntuaciones(pageable);
        
        if (respuesta.isExito()) {
            return ResponseEntity.ok(respuesta);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(respuesta);
        }
    }

    /**
     * Buscar puntuaciones por rango de puntuación
     */
    @GetMapping("/rango")
    public ResponseEntity<RespuestaPaginada<Puntuacion>> buscarPorRangoPuntuacion(
            @RequestParam Integer puntuacionMin,
            @RequestParam Integer puntuacionMax,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "fechaCreacion") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir) {
        
        // Validar rango
        if (puntuacionMin < 1 || puntuacionMax > 5 || puntuacionMin > puntuacionMax) {
            RespuestaPaginada<Puntuacion> respuestaError = new RespuestaPaginada<Puntuacion>(
                null, "Rango de puntuación inválido. Debe estar entre 1 y 5, y min <= max");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuestaError);
        }
        
        Sort sort = sortDir.equalsIgnoreCase("desc") ? 
            Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        
        // Nota: Este método requeriría implementación en el servicio y repositorio
        // Por ahora retornamos todas las puntuaciones
        RespuestaPaginada<Puntuacion> respuesta = puntuacionService.listarTodos(pageable);
        
        if (respuesta.isExito()) {
            return ResponseEntity.ok(respuesta);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(respuesta);
        }
    }
}