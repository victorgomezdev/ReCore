package com.recore.controlador;

import com.recore.modelo.ProductoCaracteristica;
import com.recore.servicio.ProductoCaracteristicaService;
import com.recore.util.RespuestaBase;
import com.recore.util.RespuestaPaginada;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controlador REST para gestión de relaciones productos-características
 */
@RestController
@RequestMapping("/api/productos-caracteristicas")
public class ProductoCaracteristicaController {

    @Autowired
    private ProductoCaracteristicaService productoCaracteristicaService;

    /**
     * Crear una nueva relación producto-característica
     */
    @PostMapping
    public ResponseEntity<RespuestaBase<ProductoCaracteristica>> crear(@RequestBody ProductoCaracteristica productoCaracteristica) {
        RespuestaBase<ProductoCaracteristica> respuesta = productoCaracteristicaService.guardar(productoCaracteristica);
        if (!respuesta.isExito()) {
            return ResponseEntity.badRequest().body(respuesta);
        }
        return ResponseEntity.ok(respuesta);
    }

    /**
     * Actualizar una relación producto-característica existente
     */
    @PutMapping("/{id}")
    public ResponseEntity<RespuestaBase<ProductoCaracteristica>> actualizar(@PathVariable Long id, @RequestBody ProductoCaracteristica productoCaracteristica) {
        productoCaracteristica.setId(id);
        RespuestaBase<ProductoCaracteristica> respuesta = productoCaracteristicaService.guardar(productoCaracteristica);
        if (!respuesta.isExito()) {
            return ResponseEntity.badRequest().body(respuesta);
        }
        return ResponseEntity.ok(respuesta);
    }

    /**
     * Eliminar una relación producto-característica por ID
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<RespuestaBase<Void>> eliminar(@PathVariable Long id) {
        RespuestaBase<Void> respuesta = productoCaracteristicaService.eliminar(id);
        return ResponseEntity.ok(respuesta);
    }

    /**
     * Buscar una relación producto-característica por ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<RespuestaBase<ProductoCaracteristica>> buscarPorId(@PathVariable Long id) {
        RespuestaBase<ProductoCaracteristica> respuesta = productoCaracteristicaService.buscarPorId(id);
        if (respuesta.getDatos() != null) {
            return ResponseEntity.ok(respuesta);
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Listar todas las relaciones con paginación
     */
    @GetMapping("/listarTodos")
    public ResponseEntity<RespuestaPaginada<ProductoCaracteristica>> listarTodos(
            @RequestParam(defaultValue = "0") int pagina,
            @RequestParam(defaultValue = "10") int tamanio) {
        Pageable paginacion = PageRequest.of(pagina, tamanio);
        RespuestaPaginada<ProductoCaracteristica> respuesta = productoCaracteristicaService.listarTodos(paginacion);
        return ResponseEntity.ok(respuesta);
    }

    /**
     * Verificar si existe una relación por ID
     */
    @GetMapping("/existe/{id}")
    public ResponseEntity<RespuestaBase<Boolean>> existe(@PathVariable Long id) {
        RespuestaBase<Boolean> respuesta = productoCaracteristicaService.existe(id);
        return ResponseEntity.ok(respuesta);
    }

    // Endpoints específicos para relaciones productos-características

    /**
     * Buscar todas las características de un producto específico
     */
    @GetMapping("/producto/{productoId}/caracteristicas")
    public ResponseEntity<RespuestaPaginada<ProductoCaracteristica>> buscarCaracteristicasPorProducto(
            @PathVariable Long productoId,
            @RequestParam(defaultValue = "0") int pagina,
            @RequestParam(defaultValue = "10") int tamanio) {
        Pageable paginacion = PageRequest.of(pagina, tamanio);
        RespuestaPaginada<ProductoCaracteristica> respuesta = productoCaracteristicaService.buscarCaracteristicasPorProducto(productoId, paginacion);
        return ResponseEntity.ok(respuesta);
    }

    /**
     * Buscar todos los productos que tienen una característica específica
     */
    @GetMapping("/caracteristica/{caracteristicaId}/productos")
    public ResponseEntity<RespuestaPaginada<ProductoCaracteristica>> buscarProductosPorCaracteristica(
            @PathVariable Long caracteristicaId,
            @RequestParam(defaultValue = "0") int pagina,
            @RequestParam(defaultValue = "10") int tamanio) {
        Pageable paginacion = PageRequest.of(pagina, tamanio);
        RespuestaPaginada<ProductoCaracteristica> respuesta = productoCaracteristicaService.buscarProductosPorCaracteristica(caracteristicaId, paginacion);
        return ResponseEntity.ok(respuesta);
    }

    /**
     * Buscar una relación específica entre producto y característica
     */
    @GetMapping("/producto/{productoId}/caracteristica/{caracteristicaId}")
    public ResponseEntity<RespuestaBase<ProductoCaracteristica>> buscarRelacion(
            @PathVariable Long productoId,
            @PathVariable Long caracteristicaId) {
        RespuestaBase<ProductoCaracteristica> respuesta = productoCaracteristicaService.buscarRelacion(productoId, caracteristicaId);
        if (respuesta.getDatos() != null) {
            return ResponseEntity.ok(respuesta);
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Verificar si existe una relación entre producto y característica
     */
    @GetMapping("/existe/producto/{productoId}/caracteristica/{caracteristicaId}")
    public ResponseEntity<RespuestaBase<Boolean>> existeRelacion(
            @PathVariable Long productoId,
            @PathVariable Long caracteristicaId) {
        RespuestaBase<Boolean> respuesta = productoCaracteristicaService.existeRelacion(productoId, caracteristicaId);
        return ResponseEntity.ok(respuesta);
    }

    /**
     * Eliminar todas las características de un producto
     */
    @DeleteMapping("/producto/{productoId}/caracteristicas")
    public ResponseEntity<RespuestaBase<Void>> eliminarCaracteristicasDeProducto(@PathVariable Long productoId) {
        RespuestaBase<Void> respuesta = productoCaracteristicaService.eliminarCaracteristicasDeProducto(productoId);
        return ResponseEntity.ok(respuesta);
    }

    /**
     * Eliminar una relación específica entre producto y característica
     */
    @DeleteMapping("/producto/{productoId}/caracteristica/{caracteristicaId}")
    public ResponseEntity<RespuestaBase<Void>> eliminarRelacion(
            @PathVariable Long productoId,
            @PathVariable Long caracteristicaId) {
        RespuestaBase<Void> respuesta = productoCaracteristicaService.eliminarRelacion(productoId, caracteristicaId);
        return ResponseEntity.ok(respuesta);
    }

    /**
     * Contar cuántas características tiene un producto
     */
    @GetMapping("/producto/{productoId}/caracteristicas/contar")
    public ResponseEntity<RespuestaBase<Long>> contarCaracteristicasDeProducto(@PathVariable Long productoId) {
        RespuestaBase<Long> respuesta = productoCaracteristicaService.contarCaracteristicasDeProducto(productoId);
        return ResponseEntity.ok(respuesta);
    }

    /**
     * Contar cuántos productos tienen una característica específica
     */
    @GetMapping("/caracteristica/{caracteristicaId}/productos/contar")
    public ResponseEntity<RespuestaBase<Long>> contarProductosConCaracteristica(@PathVariable Long caracteristicaId) {
        RespuestaBase<Long> respuesta = productoCaracteristicaService.contarProductosConCaracteristica(caracteristicaId);
        return ResponseEntity.ok(respuesta);
    }
}