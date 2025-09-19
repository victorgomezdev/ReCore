package com.recore.controlador;

import com.recore.modelo.Producto;
import com.recore.servicio.ProductoService;
import com.recore.util.RespuestaBase;
import com.recore.util.RespuestaPaginada;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.math.BigDecimal;

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
}