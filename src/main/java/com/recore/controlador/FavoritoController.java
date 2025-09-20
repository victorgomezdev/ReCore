package com.recore.controlador;

import com.recore.modelo.Favorito;
import com.recore.servicio.FavoritoService;
import com.recore.util.RespuestaBase;
import com.recore.util.RespuestaPaginada;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controlador REST para la gestión de favoritos
 * Maneja las operaciones CRUD y funcionalidades específicas de favoritos
 */
@RestController
@RequestMapping("/api/favoritos")
public class FavoritoController {

    @Autowired
    private FavoritoService favoritoService;

    /**
     * Crear un nuevo favorito
     */
    @PostMapping
    public ResponseEntity<RespuestaBase<Favorito>> crear(@RequestBody Favorito favorito) {
        RespuestaBase<Favorito> respuesta = favoritoService.guardar(favorito);
        if (!respuesta.isExito()) {
            return ResponseEntity.badRequest().body(respuesta);
        }
        return ResponseEntity.ok(respuesta);
    }

    /**
     * Crear un nuevo favorito usando IDs directamente
     */
    @PostMapping("/crear")
    public ResponseEntity<RespuestaBase<Favorito>> crearConIds(
            @RequestParam Long usuarioId, 
            @RequestParam Long productoId) {
        RespuestaBase<Favorito> respuesta = favoritoService.crearFavorito(usuarioId, productoId);
        if (!respuesta.isExito()) {
            return ResponseEntity.badRequest().body(respuesta);
        }
        return ResponseEntity.ok(respuesta);
    }

    /**
     * Actualizar un favorito existente
     */
    @PutMapping("/{id}")
    public ResponseEntity<RespuestaBase<Favorito>> actualizar(@PathVariable Long id, @RequestBody Favorito favorito) {
        RespuestaBase<Favorito> respuesta = favoritoService.actualizar(id, favorito);
        if (!respuesta.isExito()) {
            return ResponseEntity.badRequest().body(respuesta);
        }
        return ResponseEntity.ok(respuesta);
    }

    /**
     * Buscar favorito por ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<RespuestaBase<Favorito>> buscarPorId(@PathVariable Long id) {
        RespuestaBase<Favorito> respuesta = favoritoService.buscarPorId(id);
        if (respuesta.getDatos() != null) {
            return ResponseEntity.ok(respuesta);
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Listar todos los favoritos con paginación
     */
    @GetMapping("/listarTodos")
    public ResponseEntity<RespuestaPaginada<Favorito>> listarTodos(
            @RequestParam(defaultValue = "0") int pagina,
            @RequestParam(defaultValue = "10") int tamanio) {
        Pageable paginacion = PageRequest.of(pagina, tamanio);
        RespuestaPaginada<Favorito> respuesta = favoritoService.listarTodos(paginacion);
        return ResponseEntity.ok(respuesta);
    }

    /**
     * Eliminar favorito por ID
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<RespuestaBase<Void>> eliminar(@PathVariable Long id) {
        RespuestaBase<Void> respuesta = favoritoService.eliminar(id);
        return ResponseEntity.ok(respuesta);
    }

    /**
     * Verificar si existe un favorito por ID
     */
    @GetMapping("/existe/{id}")
    public ResponseEntity<RespuestaBase<Boolean>> existe(@PathVariable Long id) {
        RespuestaBase<Boolean> respuesta = favoritoService.existe(id);
        return ResponseEntity.ok(respuesta);
    }

    // Endpoints específicos para relaciones usuario-producto

    /**
     * Buscar todos los favoritos de un usuario específico
     */
    @GetMapping("/usuario/{usuarioId}/favoritos")
    public ResponseEntity<RespuestaPaginada<Favorito>> buscarFavoritosPorUsuario(
            @PathVariable Long usuarioId,
            @RequestParam(defaultValue = "0") int pagina,
            @RequestParam(defaultValue = "10") int tamanio) {
        Pageable paginacion = PageRequest.of(pagina, tamanio);
        RespuestaPaginada<Favorito> respuesta = favoritoService.buscarFavoritosPorUsuario(usuarioId, paginacion);
        return ResponseEntity.ok(respuesta);
    }

    /**
     * Buscar todos los usuarios que marcaron un producto como favorito
     */
    @GetMapping("/producto/{productoId}/usuarios")
    public ResponseEntity<RespuestaPaginada<Favorito>> buscarUsuariosPorProducto(
            @PathVariable Long productoId,
            @RequestParam(defaultValue = "0") int pagina,
            @RequestParam(defaultValue = "10") int tamanio) {
        Pageable paginacion = PageRequest.of(pagina, tamanio);
        RespuestaPaginada<Favorito> respuesta = favoritoService.buscarUsuariosPorProducto(productoId, paginacion);
        return ResponseEntity.ok(respuesta);
    }

    /**
     * Buscar una relación específica entre usuario y producto
     */
    @GetMapping("/usuario/{usuarioId}/producto/{productoId}")
    public ResponseEntity<RespuestaBase<Favorito>> buscarRelacion(
            @PathVariable Long usuarioId,
            @PathVariable Long productoId) {
        RespuestaBase<Favorito> respuesta = favoritoService.buscarRelacion(usuarioId, productoId);
        if (respuesta.getDatos() != null) {
            return ResponseEntity.ok(respuesta);
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Verificar si existe una relación entre usuario y producto
     */
    @GetMapping("/existe/usuario/{usuarioId}/producto/{productoId}")
    public ResponseEntity<RespuestaBase<Boolean>> existeRelacion(
            @PathVariable Long usuarioId,
            @PathVariable Long productoId) {
        RespuestaBase<Boolean> respuesta = favoritoService.existeRelacion(usuarioId, productoId);
        return ResponseEntity.ok(respuesta);
    }

    /**
     * Eliminar todos los favoritos de un usuario
     */
    @DeleteMapping("/usuario/{usuarioId}/favoritos")
    public ResponseEntity<RespuestaBase<Void>> eliminarFavoritosDeUsuario(@PathVariable Long usuarioId) {
        RespuestaBase<Void> respuesta = favoritoService.eliminarFavoritosDeUsuario(usuarioId);
        return ResponseEntity.ok(respuesta);
    }

    /**
     * Eliminar una relación específica entre usuario y producto
     */
    @DeleteMapping("/usuario/{usuarioId}/producto/{productoId}")
    public ResponseEntity<RespuestaBase<Void>> eliminarRelacion(
            @PathVariable Long usuarioId,
            @PathVariable Long productoId) {
        RespuestaBase<Void> respuesta = favoritoService.eliminarRelacion(usuarioId, productoId);
        return ResponseEntity.ok(respuesta);
    }

    /**
     * Contar cuántos favoritos tiene un usuario
     */
    @GetMapping("/usuario/{usuarioId}/favoritos/contar")
    public ResponseEntity<RespuestaBase<Long>> contarFavoritosDeUsuario(@PathVariable Long usuarioId) {
        RespuestaBase<Long> respuesta = favoritoService.contarFavoritosDeUsuario(usuarioId);
        return ResponseEntity.ok(respuesta);
    }

    /**
     * Contar cuántas veces un producto ha sido marcado como favorito
     */
    @GetMapping("/producto/{productoId}/usuarios/contar")
    public ResponseEntity<RespuestaBase<Long>> contarUsuariosConProducto(@PathVariable Long productoId) {
        RespuestaBase<Long> respuesta = favoritoService.contarUsuariosConProducto(productoId);
        return ResponseEntity.ok(respuesta);
    }

    /**
     * Buscar favoritos de un usuario por texto en el nombre del producto
     */
    @GetMapping("/usuario/{usuarioId}/buscar")
    public ResponseEntity<RespuestaPaginada<Favorito>> buscarFavoritosPorTexto(
            @PathVariable Long usuarioId,
            @RequestParam String texto,
            @RequestParam(defaultValue = "0") int pagina,
            @RequestParam(defaultValue = "10") int tamanio) {
        Pageable paginacion = PageRequest.of(pagina, tamanio);
        RespuestaPaginada<Favorito> respuesta = favoritoService.buscarFavoritosPorTexto(usuarioId, texto, paginacion);
        return ResponseEntity.ok(respuesta);
    }

    /**
     * Buscar favoritos de un usuario por categoría del producto
     */
    @GetMapping("/usuario/{usuarioId}/categoria/{categoriaId}")
    public ResponseEntity<RespuestaPaginada<Favorito>> buscarFavoritosPorCategoria(
            @PathVariable Long usuarioId,
            @PathVariable Long categoriaId,
            @RequestParam(defaultValue = "0") int pagina,
            @RequestParam(defaultValue = "10") int tamanio) {
        Pageable paginacion = PageRequest.of(pagina, tamanio);
        RespuestaPaginada<Favorito> respuesta = favoritoService.buscarFavoritosPorCategoria(usuarioId, categoriaId, paginacion);
        return ResponseEntity.ok(respuesta);
    }
}