package com.recore.controlador;

import com.recore.modelo.Categoria;
import com.recore.servicio.CategoriaService;
import com.recore.util.RespuestaBase;
import com.recore.util.RespuestaPaginada;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controlador REST para gestión de categorías
 */
@RestController
@RequestMapping("/api/categorias")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    @PostMapping
    public ResponseEntity<RespuestaBase<Categoria>> crear(@RequestBody Categoria categoria) {
        RespuestaBase<Categoria> respuesta = categoriaService.guardar(categoria);
        if (!respuesta.isExito()) {
            return ResponseEntity.badRequest().body(respuesta);
        }
        return ResponseEntity.ok(respuesta);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RespuestaBase<Categoria>> actualizar(@PathVariable Long id, @RequestBody Categoria categoria) {
        categoria.setId(id);
        RespuestaBase<Categoria> respuesta = categoriaService.guardar(categoria);
        if (!respuesta.isExito()) {
            return ResponseEntity.badRequest().body(respuesta);
        }
        return ResponseEntity.ok(respuesta);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<RespuestaBase<Void>> eliminar(@PathVariable Long id) {
        RespuestaBase<Void> respuesta = categoriaService.eliminar(id);
        return ResponseEntity.ok(respuesta);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RespuestaBase<Categoria>> buscarPorId(@PathVariable Long id) {
        RespuestaBase<Categoria> respuesta = categoriaService.buscarPorId(id);
        if (respuesta.getDatos() != null) {
            return ResponseEntity.ok(respuesta);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/listarTodos")
    public ResponseEntity<RespuestaPaginada<Categoria>> listarTodos(
            @RequestParam(defaultValue = "0") int pagina,
            @RequestParam(defaultValue = "10") int tamanio) {
        Pageable paginacion = PageRequest.of(pagina, tamanio);
        RespuestaPaginada<Categoria> respuesta = categoriaService.listarTodos(paginacion);
        return ResponseEntity.ok(respuesta);
    }
}