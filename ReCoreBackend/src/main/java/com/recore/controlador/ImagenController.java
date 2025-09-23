package com.recore.controlador;

import com.recore.modelo.Imagen;
import com.recore.servicio.ImagenService;
import com.recore.util.RespuestaBase;
import com.recore.util.RespuestaPaginada;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/*
* Controlador REST para manejar las operaciones de imágenes
* Proporciona endpoints para CRUD y operaciones específicas de imágenes
*/
@RestController
@RequestMapping("/api/imagenes")
@CrossOrigin(origins = "*")
public class ImagenController {

	@Autowired
	private ImagenService imagenService;

	// Crear una nueva imagen
	@PostMapping
	public ResponseEntity<RespuestaBase<Imagen>> crearImagen(@RequestBody Imagen imagen) {
		try {
			Imagen nuevaImagen = imagenService.crearImagen(imagen);
			RespuestaBase<Imagen> respuesta = new RespuestaBase<>(nuevaImagen, "Imagen creada exitosamente");
			return ResponseEntity.status(HttpStatus.CREATED).body(respuesta);
		} catch (Exception e) {
			RespuestaBase<Imagen> respuesta = new RespuestaBase<>(null, "Error al crear la imagen: " + e.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
		}
	}

	// Actualizar una imagen existente
	@PutMapping("/{id}")
	public ResponseEntity<RespuestaBase<Imagen>> actualizarImagen(
			@PathVariable Long id,
			@RequestBody Imagen imagen) {
		try {
			Imagen imagenActualizada = imagenService.actualizarImagen(id, imagen);
			RespuestaBase<Imagen> respuesta = new RespuestaBase<>(imagenActualizada, "Imagen actualizada exitosamente");
			return ResponseEntity.ok(respuesta);
		} catch (Exception e) {
			RespuestaBase<Imagen> respuesta = new RespuestaBase<>(null, "Error al actualizar la imagen: " + e.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
		}
	}

	// Buscar imagen por ID
	@GetMapping("/{id}")
	public ResponseEntity<RespuestaBase<Imagen>> buscarPorId(@PathVariable Long id) {
		try {
			Optional<Imagen> imagen = imagenService.buscarPorId(id);
			if (imagen.isPresent()) {
				RespuestaBase<Imagen> respuesta = new RespuestaBase<>(imagen.get(), "Imagen encontrada");
				return ResponseEntity.ok(respuesta);
			} else {
				RespuestaBase<Imagen> respuesta = new RespuestaBase<>(null, "Imagen no encontrada con ID: " + id);
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
			}
		} catch (Exception e) {
			RespuestaBase<Imagen> respuesta = new RespuestaBase<>(null, "Error al buscar la imagen: " + e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(respuesta);
		}
	}

	// Buscar imágenes por producto
	@GetMapping("/producto/{productoId}")
	public ResponseEntity<RespuestaBase<List<Imagen>>> buscarPorProducto(@PathVariable Long productoId) {
		try {
			List<Imagen> imagenes = imagenService.buscarPorProducto(productoId);
			RespuestaBase<List<Imagen>> respuesta = new RespuestaBase<>(imagenes, "Imágenes del producto obtenidas exitosamente");
			return ResponseEntity.ok(respuesta);
		} catch (Exception e) {
			RespuestaBase<List<Imagen>> respuesta = new RespuestaBase<>(null, "Error al buscar imágenes del producto: " + e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(respuesta);
		}
	}

	// Buscar imágenes por producto con paginación
	@GetMapping("/producto/{productoId}/paginado")
	public ResponseEntity<RespuestaPaginada<Imagen>> buscarPorProductoPaginado(
			@PathVariable Long productoId,
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size) {
		try {
			Pageable pageable = PageRequest.of(page, size);
			Page<Imagen> paginaImagenes = imagenService.buscarPorProductoPaginado(productoId, pageable);

			RespuestaPaginada<Imagen> respuesta = new RespuestaPaginada<>(
					paginaImagenes.getContent(),
					paginaImagenes.getTotalElements(),
					paginaImagenes.getNumber(),
					paginaImagenes.getSize()
			);
			respuesta.setMensaje("Imágenes del producto obtenidas exitosamente");
			return ResponseEntity.ok(respuesta);
		} catch (Exception e) {
			RespuestaPaginada<Imagen> respuesta = new RespuestaPaginada<>(
					null,
					"Error al buscar imágenes del producto: " + e.getMessage()
			);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(respuesta);
		}
	}

	// Buscar imagen principal de un producto
	@GetMapping("/producto/{productoId}/principal")
	public ResponseEntity<RespuestaBase<Imagen>> buscarImagenPrincipal(@PathVariable Long productoId) {
		try {
			Optional<Imagen> imagen = imagenService.buscarImagenPrincipal(productoId);
			if (imagen.isPresent()) {
				RespuestaBase<Imagen> respuesta = new RespuestaBase<>(imagen.get(), "Imagen principal encontrada");
				return ResponseEntity.ok(respuesta);
			} else {
				RespuestaBase<Imagen> respuesta = new RespuestaBase<>(null, "No se encontró imagen principal para el producto ID: " + productoId);
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
			}
		} catch (Exception e) {
			RespuestaBase<Imagen> respuesta = new RespuestaBase<>(null, "Error al buscar imagen principal: " + e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(respuesta);
		}
	}

	// Buscar todas las imágenes principales
	@GetMapping("/principales")
	public ResponseEntity<RespuestaBase<List<Imagen>>> buscarTodasImagenesPrincipales() {
		try {
			List<Imagen> imagenes = imagenService.buscarTodasImagenesPrincipales();
			RespuestaBase<List<Imagen>> respuesta = new RespuestaBase<>(imagenes, "Imágenes principales obtenidas exitosamente");
			return ResponseEntity.ok(respuesta);
		} catch (Exception e) {
			RespuestaBase<List<Imagen>> respuesta = new RespuestaBase<>(null, "Error al buscar imágenes principales: " + e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(respuesta);
		}
	}

	// Buscar imágenes por texto alternativo
	@GetMapping("/buscarPorAltText")
	public ResponseEntity<RespuestaBase<List<Imagen>>> buscarPorAltText(@RequestParam String altText) {
		try {
			List<Imagen> imagenes = imagenService.buscarPorAltText(altText);
			RespuestaBase<List<Imagen>> respuesta = new RespuestaBase<>(imagenes, "Imágenes encontradas por texto alternativo");
			return ResponseEntity.ok(respuesta);
		} catch (Exception e) {
			RespuestaBase<List<Imagen>> respuesta = new RespuestaBase<>(null, "Error al buscar imágenes por texto alternativo: " + e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(respuesta);
		}
	}

	// Establecer imagen como principal
	@PutMapping("/{id}/establecerPrincipal")
	public ResponseEntity<RespuestaBase<Imagen>> establecerComoPrincipal(@PathVariable Long id) {
		try {
			Imagen imagen = imagenService.establecerComoPrincipal(id);
			RespuestaBase<Imagen> respuesta = new RespuestaBase<>(imagen, "Imagen establecida como principal exitosamente");
			return ResponseEntity.ok(respuesta);
		} catch (Exception e) {
			RespuestaBase<Imagen> respuesta = new RespuestaBase<>(null, "Error al establecer imagen como principal: " + e.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
		}
	}

	// Reordenar imágenes de un producto
	@PutMapping("/producto/{productoId}/reordenar")
	public ResponseEntity<RespuestaBase<List<Imagen>>> reordenarImagenes(
			@PathVariable Long productoId,
			@RequestBody List<Long> ordenImagenes) {
		try {
			List<Imagen> imagenes = imagenService.reordenarImagenes(productoId, ordenImagenes);
			RespuestaBase<List<Imagen>> respuesta = new RespuestaBase<>(imagenes, "Imágenes reordenadas exitosamente");
			return ResponseEntity.ok(respuesta);
		} catch (Exception e) {
			RespuestaBase<List<Imagen>> respuesta = new RespuestaBase<>(null, "Error al reordenar imágenes: " + e.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
		}
	}

	// Contar imágenes de un producto
	@GetMapping("/producto/{productoId}/contar")
	public ResponseEntity<RespuestaBase<Long>> contarImagenesDeProducto(@PathVariable Long productoId) {
		try {
			long cantidad = imagenService.contarImagenesDeProducto(productoId);
			RespuestaBase<Long> respuesta = new RespuestaBase<>(cantidad, "Cantidad de imágenes obtenida exitosamente");
			return ResponseEntity.ok(respuesta);
		} catch (Exception e) {
			RespuestaBase<Long> respuesta = new RespuestaBase<>(null, "Error al contar imágenes: " + e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(respuesta);
		}
	}

	// Eliminar imagen
	@DeleteMapping("/{id}")
	public ResponseEntity<RespuestaBase<String>> eliminarImagen(@PathVariable Long id) {
		try {
			imagenService.eliminarImagen(id);
			RespuestaBase<String> respuesta = new RespuestaBase<>("Imagen con ID " + id + " eliminada", "Imagen eliminada exitosamente");
			return ResponseEntity.ok(respuesta);
		} catch (Exception e) {
			RespuestaBase<String> respuesta = new RespuestaBase<>(null, "Error al eliminar la imagen: " + e.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
		}
	}

	// Eliminar todas las imágenes de un producto
	@DeleteMapping("/producto/{productoId}")
	public ResponseEntity<RespuestaBase<String>> eliminarImagenesDeProducto(@PathVariable Long productoId) {
		try {
			imagenService.eliminarImagenesDeProducto(productoId);
			RespuestaBase<String> respuesta = new RespuestaBase<>("Imágenes del producto ID " + productoId + " eliminadas", "Todas las imágenes del producto eliminadas exitosamente");
			return ResponseEntity.ok(respuesta);
		} catch (Exception e) {
			RespuestaBase<String> respuesta = new RespuestaBase<>(null, "Error al eliminar imágenes del producto: " + e.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
		}
	}
}