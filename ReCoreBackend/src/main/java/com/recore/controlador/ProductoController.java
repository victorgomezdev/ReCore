package com.recore.controlador;

import com.recore.modelo.Producto;
import com.recore.servicio.ProductoService;
import com.recore.util.RespuestaBase;
import com.recore.util.RespuestaPaginada;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Controlador REST para gestión de productos
 */
@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @PostMapping
    public ResponseEntity<RespuestaBase<Producto>> crear(@RequestBody Producto producto) {
        RespuestaBase<Producto> respuesta = productoService.guardar(producto);
        if (!respuesta.isExito()) {
            return ResponseEntity.badRequest().body(respuesta);
        }
        return ResponseEntity.ok(respuesta);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RespuestaBase<Producto>> actualizar(@PathVariable Long id, @RequestBody Producto producto) {
        producto.setId(id);
        RespuestaBase<Producto> respuesta = productoService.guardar(producto);
        if (!respuesta.isExito()) {
            return ResponseEntity.badRequest().body(respuesta);
        }
        return ResponseEntity.ok(respuesta);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<RespuestaBase<Void>> eliminar(@PathVariable Long id) {
        RespuestaBase<Void> respuesta = productoService.eliminar(id);
        return ResponseEntity.ok(respuesta);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RespuestaBase<Producto>> buscarPorId(@PathVariable Long id) {
        RespuestaBase<Producto> respuesta = productoService.buscarPorId(id);
        if (respuesta.getDatos() != null) {
            return ResponseEntity.ok(respuesta);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/listarTodos")
    public ResponseEntity<RespuestaPaginada<Producto>> listarTodos(
            @RequestParam(defaultValue = "0") int pagina,
            @RequestParam(defaultValue = "10") int tamanio) {
        Pageable paginacion = PageRequest.of(pagina, tamanio);
        RespuestaPaginada<Producto> respuesta = productoService.listarTodos(paginacion);
        return ResponseEntity.ok(respuesta);
    }

    // Endpoints específicos para productos
    @GetMapping("/buscarPorNombre/{nombre}")
    public ResponseEntity<RespuestaBase<Producto>> buscarPorNombre(@PathVariable String nombre) {
        RespuestaBase<Producto> respuesta = productoService.buscarPorNombre(nombre);
        if (respuesta.getDatos() != null) {
            return ResponseEntity.ok(respuesta);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/buscarPorCategoria/{categoriaId}")
    public ResponseEntity<RespuestaPaginada<Producto>> buscarPorCategoria(
            @PathVariable Long categoriaId,
            @RequestParam(defaultValue = "0") int pagina,
            @RequestParam(defaultValue = "10") int tamanio) {
        Pageable paginacion = PageRequest.of(pagina, tamanio);
        RespuestaPaginada<Producto> respuesta = productoService.buscarPorCategoria(categoriaId, paginacion);
        return ResponseEntity.ok(respuesta);
    }

    @GetMapping("/buscarPorRangoPrecio")
    public ResponseEntity<RespuestaPaginada<Producto>> buscarPorRangoPrecio(
            @RequestParam BigDecimal precioMin,
            @RequestParam BigDecimal precioMax,
            @RequestParam(defaultValue = "0") int pagina,
            @RequestParam(defaultValue = "10") int tamanio) {
        Pageable paginacion = PageRequest.of(pagina, tamanio);
        RespuestaPaginada<Producto> respuesta = productoService.buscarPorRangoPrecio(precioMin, precioMax, paginacion);
        return ResponseEntity.ok(respuesta);
    }

    @GetMapping("/buscarPorNombreContiene/{nombre}")
    public ResponseEntity<RespuestaPaginada<Producto>> buscarPorNombreContiene(
            @PathVariable String nombre,
            @RequestParam(defaultValue = "0") int pagina,
            @RequestParam(defaultValue = "10") int tamanio) {
        Pageable paginacion = PageRequest.of(pagina, tamanio);
        RespuestaPaginada<Producto> respuesta = productoService.buscarPorNombreContiene(nombre, paginacion);
        return ResponseEntity.ok(respuesta);
    }

    /**
     * Buscar productos por descripción que contenga el texto
     */
    @GetMapping("/buscarPorDescripcionContiene/{descripcion}")
    public ResponseEntity<RespuestaPaginada<Producto>> buscarPorDescripcionContiene(
            @PathVariable String descripcion,
            @RequestParam(defaultValue = "0") int pagina,
            @RequestParam(defaultValue = "10") int tamanio) {
        Pageable paginacion = PageRequest.of(pagina, tamanio);
        RespuestaPaginada<Producto> respuesta = productoService.buscarPorDescripcionContiene(descripcion, paginacion);
        return ResponseEntity.ok(respuesta);
    }

    /**
     * Buscar productos por texto en nombre o descripción
     */
    @GetMapping("/buscar")
    public ResponseEntity<RespuestaPaginada<Producto>> buscarPorTexto(
            @RequestParam String texto,
            @RequestParam(defaultValue = "0") int pagina,
            @RequestParam(defaultValue = "10") int tamanio,
            @RequestParam(defaultValue = "nombre") String ordenarPor,
            @RequestParam(defaultValue = "asc") String direccion) {

        // Crear objeto de paginación con ordenamiento
        Sort sort = "asc".equalsIgnoreCase(direccion) ? Sort.by(ordenarPor).ascending()
                : Sort.by(ordenarPor).descending();
        Pageable paginacion = PageRequest.of(pagina, tamanio, sort);

        RespuestaPaginada<Producto> respuesta = productoService.buscarPorTexto(texto, paginacion);
        return ResponseEntity.ok(respuesta);
    }

    /**
     * Búsqueda avanzada de productos con múltiples criterios
     */
    @GetMapping("/busquedaAvanzada")
    public ResponseEntity<RespuestaPaginada<Producto>> busquedaAvanzada(
            @RequestParam(required = false) String texto,
            @RequestParam(required = false) Long categoriaId,
            @RequestParam(required = false) BigDecimal precioMin,
            @RequestParam(required = false) BigDecimal precioMax,
            @RequestParam(required = false) Long caracteristicaId,
            @RequestParam(defaultValue = "0") int pagina,
            @RequestParam(defaultValue = "10") int tamanio,
            @RequestParam(defaultValue = "nombre") String ordenarPor,
            @RequestParam(defaultValue = "asc") String direccion) {

        // Crear objeto de paginación con ordenamiento
        Sort sort = "asc".equalsIgnoreCase(direccion) ? Sort.by(ordenarPor).ascending()
                : Sort.by(ordenarPor).descending();
        Pageable paginacion = PageRequest.of(pagina, tamanio, sort);

        RespuestaPaginada<Producto> respuesta = productoService.busquedaAvanzada(
                texto, categoriaId, precioMin, precioMax, caracteristicaId, paginacion);

        if (!respuesta.isExito()) {
            return ResponseEntity.badRequest().body(respuesta);
        }
        return ResponseEntity.ok(respuesta);
    }

    /**
     * Buscar productos disponibles en un rango de fechas
     */
    @GetMapping("/disponibles")
    public ResponseEntity<RespuestaPaginada<Producto>> buscarDisponibles(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin,
            @RequestParam(defaultValue = "0") int pagina,
            @RequestParam(defaultValue = "10") int tamanio,
            @RequestParam(defaultValue = "nombre") String ordenarPor,
            @RequestParam(defaultValue = "asc") String direccion) {

        // Crear objeto de paginación con ordenamiento
        Sort sort = "asc".equalsIgnoreCase(direccion) ? Sort.by(ordenarPor).ascending()
                : Sort.by(ordenarPor).descending();
        Pageable paginacion = PageRequest.of(pagina, tamanio, sort);

        RespuestaPaginada<Producto> respuesta = productoService.buscarPorDisponibilidadEnFechas(
                fechaInicio, fechaFin, paginacion);

        if (!respuesta.isExito()) {
            return ResponseEntity.badRequest().body(respuesta);
        }
        return ResponseEntity.ok(respuesta);
    }

    /**
     * Listar productos ordenados por popularidad (número de reservas)
     */
    @GetMapping("/populares")
    public ResponseEntity<RespuestaPaginada<Producto>> listarPorPopularidad(
            @RequestParam(defaultValue = "0") int pagina,
            @RequestParam(defaultValue = "10") int tamanio) {

        Pageable paginacion = PageRequest.of(pagina, tamanio);
        RespuestaPaginada<Producto> respuesta = productoService.listarPorPopularidad(paginacion);

        if (!respuesta.isExito()) {
            return ResponseEntity.badRequest().body(respuesta);
        }
        return ResponseEntity.ok(respuesta);
    }

    /**
     * Listar productos ordenados por puntuación promedio
     */
    @GetMapping("/mejorValorados")
    public ResponseEntity<RespuestaPaginada<Producto>> listarPorPuntuacion(
            @RequestParam(defaultValue = "0") int pagina,
            @RequestParam(defaultValue = "10") int tamanio) {

        Pageable paginacion = PageRequest.of(pagina, tamanio);
        RespuestaPaginada<Producto> respuesta = productoService.listarPorPuntuacion(paginacion);

        if (!respuesta.isExito()) {
            return ResponseEntity.badRequest().body(respuesta);
        }
        return ResponseEntity.ok(respuesta);
    }

    /**
     * Listar productos ordenados por fecha de creación (más recientes primero)
     */
    @GetMapping("/recientes")
    public ResponseEntity<RespuestaPaginada<Producto>> listarPorFechaCreacion(
            @RequestParam(defaultValue = "0") int pagina,
            @RequestParam(defaultValue = "10") int tamanio) {

        Pageable paginacion = PageRequest.of(pagina, tamanio);
        RespuestaPaginada<Producto> respuesta = productoService.listarPorFechaCreacion(paginacion);

        if (!respuesta.isExito()) {
            return ResponseEntity.badRequest().body(respuesta);
        }
        return ResponseEntity.ok(respuesta);
    }

    /**
     * Actualizar el promedio de puntuaciones de un producto
     */
    @PatchMapping("/{id}/actualizarPromedioPuntuaciones")
    public ResponseEntity<RespuestaBase<Producto>> actualizarPromedioPuntuaciones(@PathVariable Long id) {
        RespuestaBase<Producto> respuesta = productoService.actualizarPromedioPuntuaciones(id);

        if (!respuesta.isExito()) {
            return ResponseEntity.badRequest().body(respuesta);
        }
        return ResponseEntity.ok(respuesta);
    }

    /**
     * Actualizar todos los promedios de puntuaciones
     */
    @PatchMapping("/actualizarTodosLosPromediosPuntuaciones")
    public ResponseEntity<RespuestaBase<Integer>> actualizarTodosLosPromediosPuntuaciones() {
        RespuestaBase<Integer> respuesta = productoService.actualizarTodosLosPromediosPuntuaciones();

        if (!respuesta.isExito()) {
            return ResponseEntity.badRequest().body(respuesta);
        }
        return ResponseEntity.ok(respuesta);
    }
}