package com.recore.controlador;

import com.recore.modelo.Caracteristica;
import com.recore.servicio.CaracteristicaService;
import com.recore.util.RespuestaBase;
import com.recore.util.RespuestaPaginada;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Módulo 3 - APIs
 * Clase 6 - API REST
 * Controlador REST para gestión de características
 */
@RestController
@RequestMapping("/api/caracteristicas")
public class CaracteristicaController {

    @Autowired
    private CaracteristicaService caracteristicaService;

    /**
     * Crear una nueva característica
     * POST /api/caracteristicas
     */
    @PostMapping
    public ResponseEntity<RespuestaBase<Caracteristica>> crear(@RequestBody Caracteristica caracteristica) {
        RespuestaBase<Caracteristica> respuesta = caracteristicaService.guardar(caracteristica);
        if (!respuesta.isExito()) {
            return ResponseEntity.badRequest().body(respuesta);
        }
        return ResponseEntity.ok(respuesta);
    }

    /**
     * Actualizar una característica existente
     * PUT /api/caracteristicas/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<RespuestaBase<Caracteristica>> actualizar(@PathVariable Long id, @RequestBody Caracteristica caracteristica) {
        caracteristica.setId(id);
        RespuestaBase<Caracteristica> respuesta = caracteristicaService.guardar(caracteristica);
        if (!respuesta.isExito()) {
            return ResponseEntity.badRequest().body(respuesta);
        }
        return ResponseEntity.ok(respuesta);
    }

    /**
     * Eliminar una característica
     * DELETE /api/caracteristicas/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<RespuestaBase<Void>> eliminar(@PathVariable Long id) {
        RespuestaBase<Void> respuesta = caracteristicaService.eliminar(id);
        if (respuesta.isExito()) {
            return ResponseEntity.ok(respuesta);
        }
        return ResponseEntity.badRequest().body(respuesta);
    }

    /**
     * Buscar característica por ID
     * GET /api/caracteristicas/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<RespuestaBase<Caracteristica>> buscarPorId(@PathVariable Long id) {
        RespuestaBase<Caracteristica> respuesta = caracteristicaService.buscarPorId(id);
        if (respuesta.getDatos() != null) {
            return ResponseEntity.ok(respuesta);
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Listar todas las características con paginación
     * GET /api/caracteristicas/listarTodos?pagina=0&tamanio=10
     */
    @GetMapping("/listarTodos")
    public ResponseEntity<RespuestaPaginada<Caracteristica>> listarTodos(
            @RequestParam(defaultValue = "0") int pagina,
            @RequestParam(defaultValue = "10") int tamanio) {
        Pageable paginacion = PageRequest.of(pagina, tamanio);
        RespuestaPaginada<Caracteristica> respuesta = caracteristicaService.listarTodos(paginacion);
        return ResponseEntity.ok(respuesta);
    }

    /**
     * Buscar característica por nombre exacto
     * GET /api/caracteristicas/buscarPorNombre/{nombre}
     */
    @GetMapping("/buscarPorNombre/{nombre}")
    public ResponseEntity<RespuestaBase<Caracteristica>> buscarPorNombre(@PathVariable String nombre) {
        RespuestaBase<Caracteristica> respuesta = caracteristicaService.buscarPorNombre(nombre);
        if (respuesta.getDatos() != null) {
            return ResponseEntity.ok(respuesta);
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Buscar características que contengan texto en el nombre con paginación
     * GET /api/caracteristicas/buscar?nombre=texto&pagina=0&tamanio=10
     */
    @GetMapping("/buscar")
    public ResponseEntity<RespuestaPaginada<Caracteristica>> buscarPorNombreContiene(
            @RequestParam String nombre,
            @RequestParam(defaultValue = "0") int pagina,
            @RequestParam(defaultValue = "10") int tamanio) {
        Pageable paginacion = PageRequest.of(pagina, tamanio);
        RespuestaPaginada<Caracteristica> respuesta = caracteristicaService.buscarPorNombreContiene(nombre, paginacion);
        return ResponseEntity.ok(respuesta);
    }

    /**
     * Verificar si existe una característica por ID
     * GET /api/caracteristicas/existe/{id}
     */
    @GetMapping("/existe/{id}")
    public ResponseEntity<RespuestaBase<Boolean>> existe(@PathVariable Long id) {
        RespuestaBase<Boolean> respuesta = caracteristicaService.existe(id);
        return ResponseEntity.ok(respuesta);
    }
}