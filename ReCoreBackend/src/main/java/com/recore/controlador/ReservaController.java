package com.recore.controlador;

import com.recore.modelo.Reserva;
import com.recore.servicio.ReservaService;
import com.recore.util.RespuestaBase;
import com.recore.util.RespuestaPaginada;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

/**
 * Controlador REST para gestión de reservas
 * Proporciona endpoints para CRUD y operaciones específicas de reservas
 */
@RestController
@RequestMapping("/api/reservas")
public class ReservaController {

    @Autowired
    private ReservaService reservaService;

    /**
     * Crear una nueva reserva
     */
    @PostMapping
    public ResponseEntity<RespuestaBase<Reserva>> crear(@RequestBody Reserva reserva) {
        RespuestaBase<Reserva> respuesta = reservaService.guardar(reserva);
        if (!respuesta.isExito()) {
            return ResponseEntity.badRequest().body(respuesta);
        }
        return ResponseEntity.ok(respuesta);
    }

    /**
     * Actualizar una reserva existente
     */
    @PutMapping("/{id}")
    public ResponseEntity<RespuestaBase<Reserva>> actualizar(@PathVariable Long id, @RequestBody Reserva reserva) {
        reserva.setId(id);
        RespuestaBase<Reserva> respuesta = reservaService.guardar(reserva);
        if (!respuesta.isExito()) {
            return ResponseEntity.badRequest().body(respuesta);
        }
        return ResponseEntity.ok(respuesta);
    }

    /**
     * Eliminar una reserva
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<RespuestaBase<Void>> eliminar(@PathVariable Long id) {
        RespuestaBase<Void> respuesta = reservaService.eliminar(id);
        if (!respuesta.isExito()) {
            return ResponseEntity.badRequest().body(respuesta);
        }
        return ResponseEntity.ok(respuesta);
    }

    /**
     * Buscar reserva por ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<RespuestaBase<Reserva>> buscarPorId(@PathVariable Long id) {
        RespuestaBase<Reserva> respuesta = reservaService.buscarPorId(id);
        if (!respuesta.isExito()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(respuesta);
    }

    /**
     * Buscar todas las reservas con paginación
     */
    @GetMapping
    public ResponseEntity<RespuestaPaginada<Reserva>> buscarTodas(
            @RequestParam(defaultValue = "0") int pagina,
            @RequestParam(defaultValue = "10") int tamanio) {
        Pageable pageable = PageRequest.of(pagina, tamanio);
        RespuestaPaginada<Reserva> respuesta = reservaService.listarTodos(pageable);
        return ResponseEntity.ok(respuesta);
    }

    /**
     * Buscar reservas por usuario
     */
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<RespuestaPaginada<Reserva>> buscarPorUsuario(
            @PathVariable Long usuarioId,
            @RequestParam(defaultValue = "0") int pagina,
            @RequestParam(defaultValue = "10") int tamanio) {
        Pageable pageable = PageRequest.of(pagina, tamanio);
        RespuestaPaginada<Reserva> respuesta = reservaService.buscarPorUsuario(usuarioId, pageable);
        return ResponseEntity.ok(respuesta);
    }

    /**
     * Buscar reservas por producto
     */
    @GetMapping("/producto/{productoId}")
    public ResponseEntity<RespuestaPaginada<Reserva>> buscarPorProducto(
            @PathVariable Long productoId,
            @RequestParam(defaultValue = "0") int pagina,
            @RequestParam(defaultValue = "10") int tamanio) {
        Pageable pageable = PageRequest.of(pagina, tamanio);
        RespuestaPaginada<Reserva> respuesta = reservaService.buscarPorProducto(productoId, pageable);
        return ResponseEntity.ok(respuesta);
    }

    /**
     * Verificar disponibilidad de un producto en fechas específicas
     */
    @GetMapping("/disponibilidad/{productoId}")
    public ResponseEntity<RespuestaBase<Boolean>> verificarDisponibilidad(
            @PathVariable Long productoId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin) {
        RespuestaBase<Boolean> respuesta = reservaService.verificarDisponibilidad(productoId, fechaInicio, fechaFin);
        return ResponseEntity.ok(respuesta);
    }

    /**
     * Confirmar una reserva (cambiar estado a "Confirmada")
     */
    @PatchMapping("/{id}/confirmar")
    public ResponseEntity<RespuestaBase<Reserva>> confirmarReserva(@PathVariable Long id) {
        RespuestaBase<Reserva> respuesta = reservaService.confirmarReserva(id);
        if (!respuesta.isExito()) {
            return ResponseEntity.badRequest().body(respuesta);
        }
        return ResponseEntity.ok(respuesta);
    }

    /**
     * Cancelar una reserva (cambiar estado a "Cancelada")
     */
    @PatchMapping("/{id}/cancelar")
    public ResponseEntity<RespuestaBase<Reserva>> cancelarReserva(
            @PathVariable Long id,
            @RequestParam(required = false) String motivo) {
        RespuestaBase<Reserva> respuesta = reservaService.cancelarReserva(id, motivo);
        if (!respuesta.isExito()) {
            return ResponseEntity.badRequest().body(respuesta);
        }
        return ResponseEntity.ok(respuesta);
    }

    /**
     * Completar una reserva (cambiar estado a "Completada")
     */
    @PatchMapping("/{id}/completar")
    public ResponseEntity<RespuestaBase<Reserva>> completarReserva(@PathVariable Long id) {
        RespuestaBase<Reserva> respuesta = reservaService.completarReserva(id);
        if (!respuesta.isExito()) {
            return ResponseEntity.badRequest().body(respuesta);
        }
        return ResponseEntity.ok(respuesta);
    }

    /**
     * Buscar historial de reservas de un usuario
     */
    @GetMapping("/historial/usuario/{usuarioId}")
    public ResponseEntity<RespuestaPaginada<Reserva>> buscarHistorialUsuario(
            @PathVariable Long usuarioId,
            @RequestParam(defaultValue = "0") int pagina,
            @RequestParam(defaultValue = "10") int tamanio) {
        Pageable pageable = PageRequest.of(pagina, tamanio);
        RespuestaPaginada<Reserva> respuesta = reservaService.buscarHistorialUsuario(usuarioId, pageable);
        if (!respuesta.isExito()) {
            return ResponseEntity.badRequest().body(respuesta);
        }
        return ResponseEntity.ok(respuesta);
    }

    /**
     * Buscar historial de reservas de un usuario con filtros avanzados
     */
    @GetMapping("/historial/usuario/{usuarioId}/filtrado")
    public ResponseEntity<RespuestaPaginada<Reserva>> buscarHistorialUsuarioFiltrado(
            @PathVariable Long usuarioId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaDesde,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaHasta,
            @RequestParam(required = false) List<String> estados,
            @RequestParam(defaultValue = "0") int pagina,
            @RequestParam(defaultValue = "10") int tamanio,
            @RequestParam(defaultValue = "fechaCreacion") String ordenarPor,
            @RequestParam(defaultValue = "desc") String direccion) {

        // Crear objeto de paginación con ordenamiento
        Sort sort = "asc".equalsIgnoreCase(direccion) ? Sort.by(ordenarPor).ascending()
                : Sort.by(ordenarPor).descending();
        Pageable pageable = PageRequest.of(pagina, tamanio, sort);

        RespuestaPaginada<Reserva> respuesta = reservaService.buscarHistorialUsuarioFiltrado(
                usuarioId, fechaDesde, fechaHasta, estados, pageable);

        if (!respuesta.isExito()) {
            return ResponseEntity.badRequest().body(respuesta);
        }
        return ResponseEntity.ok(respuesta);
    }

    /**
     * Buscar reservas por estado
     */
    @GetMapping("/estado/{nombreEstado}")
    public ResponseEntity<RespuestaPaginada<Reserva>> buscarPorEstado(
            @PathVariable String nombreEstado,
            @RequestParam(defaultValue = "0") int pagina,
            @RequestParam(defaultValue = "10") int tamanio) {
        Pageable pageable = PageRequest.of(pagina, tamanio);
        RespuestaPaginada<Reserva> respuesta = reservaService.buscarPorEstado(nombreEstado, pageable);
        if (!respuesta.isExito()) {
            return ResponseEntity.badRequest().body(respuesta);
        }
        return ResponseEntity.ok(respuesta);
    }

    /**
     * Buscar reservas por usuario y estado
     */
    @GetMapping("/usuario/{usuarioId}/estado/{nombreEstado}")
    public ResponseEntity<RespuestaPaginada<Reserva>> buscarPorUsuarioYEstado(
            @PathVariable Long usuarioId,
            @PathVariable String nombreEstado,
            @RequestParam(defaultValue = "0") int pagina,
            @RequestParam(defaultValue = "10") int tamanio) {
        Pageable pageable = PageRequest.of(pagina, tamanio);
        RespuestaPaginada<Reserva> respuesta = reservaService.buscarPorUsuarioYEstado(usuarioId, nombreEstado,
                pageable);
        if (!respuesta.isExito()) {
            return ResponseEntity.badRequest().body(respuesta);
        }
        return ResponseEntity.ok(respuesta);
    }

    /**
     * Verificar si un usuario puede reservar un producto en fechas específicas
     */
    @GetMapping("/puede-reservar")
    public ResponseEntity<RespuestaBase<Boolean>> puedeUsuarioReservar(
            @RequestParam Long usuarioId,
            @RequestParam Long productoId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin) {
        RespuestaBase<Boolean> respuesta = reservaService.puedeUsuarioReservar(
                usuarioId, productoId, fechaInicio, fechaFin);
        return ResponseEntity.ok(respuesta);
    }
}