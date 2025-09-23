package com.recore.controlador;

import com.recore.modelo.Estado;
import com.recore.servicio.EstadoService;
import com.recore.util.RespuestaBase;
import com.recore.util.RespuestaPaginada;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para gestión de estados de reservas
 * Proporciona endpoints para CRUD y operaciones específicas de estados
 */
@RestController
@RequestMapping("/api/estados")
public class EstadoController {

    @Autowired
    private EstadoService estadoService;

    /**
     * Crear un nuevo estado
     */
    @PostMapping
    public ResponseEntity<RespuestaBase<Estado>> crear(@RequestBody Estado estado) {
        RespuestaBase<Estado> respuesta = estadoService.guardar(estado);
        if (!respuesta.isExito()) {
            return ResponseEntity.badRequest().body(respuesta);
        }
        return ResponseEntity.ok(respuesta);
    }

    /**
     * Actualizar un estado existente
     */
    @PutMapping("/{id}")
    public ResponseEntity<RespuestaBase<Estado>> actualizar(@PathVariable Long id, @RequestBody Estado estado) {
        estado.setId(id);
        RespuestaBase<Estado> respuesta = estadoService.guardar(estado);
        if (!respuesta.isExito()) {
            return ResponseEntity.badRequest().body(respuesta);
        }
        return ResponseEntity.ok(respuesta);
    }

    /**
     * Eliminar un estado
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<RespuestaBase<Void>> eliminar(@PathVariable Long id) {
        RespuestaBase<Void> respuesta = estadoService.eliminar(id);
        if (!respuesta.isExito()) {
            return ResponseEntity.badRequest().body(respuesta);
        }
        return ResponseEntity.ok(respuesta);
    }

    /**
     * Buscar estado por ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<RespuestaBase<Estado>> buscarPorId(@PathVariable Long id) {
        RespuestaBase<Estado> respuesta = estadoService.buscarPorId(id);
        if (!respuesta.isExito()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(respuesta);
    }

    /**
     * Buscar todos los estados con paginación
     */
    @GetMapping
    public ResponseEntity<RespuestaPaginada<Estado>> buscarTodos(
            @RequestParam(defaultValue = "0") int pagina,
            @RequestParam(defaultValue = "10") int tamanio) {
        Pageable pageable = PageRequest.of(pagina, tamanio);
        RespuestaPaginada<Estado> respuesta = estadoService.listarTodos(pageable);
        return ResponseEntity.ok(respuesta);
    }

    /**
     * Buscar estado por nombre
     */
    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<RespuestaBase<Estado>> buscarPorNombre(@PathVariable String nombre) {
        RespuestaBase<Estado> respuesta = estadoService.buscarPorNombre(nombre);
        if (!respuesta.isExito()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(respuesta);
    }

    /**
     * Buscar todos los estados activos
     */
    @GetMapping("/activos")
    public ResponseEntity<RespuestaBase<List<Estado>>> buscarEstadosActivos() {
        RespuestaBase<List<Estado>> respuesta = estadoService.buscarEstadosActivos();
        return ResponseEntity.ok(respuesta);
    }

    /**
     * Activar o desactivar un estado
     */
    @PatchMapping("/{id}/activo")
    public ResponseEntity<RespuestaBase<Estado>> cambiarEstadoActivo(
            @PathVariable Long id, 
            @RequestParam Boolean activo) {
        RespuestaBase<Estado> respuesta = estadoService.cambiarEstadoActivo(id, activo);
        if (!respuesta.isExito()) {
            return ResponseEntity.badRequest().body(respuesta);
        }
        return ResponseEntity.ok(respuesta);
    }
}