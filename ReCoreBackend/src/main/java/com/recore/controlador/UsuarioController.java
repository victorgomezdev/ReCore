package com.recore.controlador;

import com.recore.modelo.Usuario;
import com.recore.servicio.UsuarioService;
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
 * Controlador REST para gestión de usuarios
 */
@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

	@Autowired
	private UsuarioService usuarioService;

	@PostMapping
	public ResponseEntity<RespuestaBase<Usuario>> crear(@RequestBody Usuario usuario) {
		RespuestaBase<Usuario> respuesta = usuarioService.guardar(usuario);
		if (!respuesta.isExito()) {
			return ResponseEntity.badRequest().body(respuesta);
		}
		return ResponseEntity.ok(respuesta);
	}

	@PutMapping("/{id}")
	public ResponseEntity<RespuestaBase<Usuario>> actualizar(@PathVariable Long id, @RequestBody Usuario usuario) {
		usuario.setId(id);
		RespuestaBase<Usuario> respuesta = usuarioService.guardar(usuario);
		if (!respuesta.isExito()) {
			return ResponseEntity.badRequest().body(respuesta);
		}
		return ResponseEntity.ok(respuesta);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<RespuestaBase<Void>> eliminar(@PathVariable Long id) {
		RespuestaBase<Void> respuesta = usuarioService.eliminar(id);
		return ResponseEntity.ok(respuesta);
	}

	@GetMapping("/{id}")
	public ResponseEntity<RespuestaBase<Usuario>> buscarPorId(@PathVariable Long id) {
		RespuestaBase<Usuario> respuesta = usuarioService.buscarPorId(id);
		if (respuesta.getDatos() != null) {
			return ResponseEntity.ok(respuesta);
		}
		return ResponseEntity.notFound().build();
	}

	@GetMapping("/listarTodos")
	public ResponseEntity<RespuestaPaginada<Usuario>> listarTodos(
			@RequestParam(defaultValue = "0") int pagina,
			@RequestParam(defaultValue = "10") int tamanio) {
		Pageable paginacion = PageRequest.of(pagina, tamanio);
		RespuestaPaginada<Usuario> respuesta = usuarioService.listarTodos(paginacion);
		return ResponseEntity.ok(respuesta);
	}
}
