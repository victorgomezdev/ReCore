package CatsPrograming.ReCore.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import CatsPrograming.ReCore.models.Articulos.Categoria;
import CatsPrograming.ReCore.services.Articulos.CategoriaService;

@RestController
@RequestMapping("api/categorias")
@CrossOrigin(origins = "*")
public class CategoriasController {

	@Autowired
	private CategoriaService categoriaService;

	@GetMapping("/getCategorias")
	public ResponseEntity<?> getCategorias() {
		try {
			List<Categoria> categorias = categoriaService.getAllCategorias();
			return ResponseEntity.ok(new GetCategoriasResponse(true, "OK", categorias));
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(new GetCategoriasResponse(false, e.getMessage(), null));
		}
	}

	@GetMapping("/getCategoriasActivas")
	public ResponseEntity<?> getCategoriasActivas() {
		try {
			List<Categoria> categorias = categoriaService.getCategoriasActivas();
			return ResponseEntity.ok(new GetCategoriasResponse(true, "OK", categorias));
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(new GetCategoriasResponse(false, e.getMessage(), null));
		}
	}

	@GetMapping("/getCategoria/{id}")
	public ResponseEntity<?> getCategoriaById(@PathVariable int id) {
		Categoria categoria = categoriaService.getCategoriaById(id);
		if (categoria != null) {
			return ResponseEntity.ok(new GetCategoriaResponse(true, "OK", categoria));
		} else {
			return ResponseEntity.badRequest()
					.body(new GetCategoriaResponse(false, "No se encontró la categoría", null));
		}
	}

	@PostMapping("/crear")
	public ResponseEntity<?> crearCategoria(@RequestBody CrearCategoriaRequest request) {
		try {
			Categoria categoria = new Categoria(request.getNombre(), request.getDescripcion(),
					request.isActivo() ? 1 : 0);
			categoriaService.insertCategoria(categoria);
			return ResponseEntity.ok(new CrearCategoriaResponse(true, "Categoría creada correctamente"));
		} catch (Exception e) {
			return ResponseEntity.badRequest()
					.body(new CrearCategoriaResponse(false, "Error al crear categoría: " + e.getMessage()));
		}
	}

	@PostMapping("/actualizar")
	public ResponseEntity<?> actualizarCategoria(@RequestBody ActualizarCategoriaRequest request) {
		try {
			Categoria categoria = new Categoria(request.getId(), request.getNombre(),
					request.getDescripcion(), request.isActivo() ? 1 : 0);
			boolean actualizado = categoriaService.updateCategoria(categoria);
			if (actualizado) {
				return ResponseEntity.ok(new ActualizarCategoriaResponse(true, "Categoría actualizada correctamente"));
			} else {
				return ResponseEntity.badRequest()
						.body(new ActualizarCategoriaResponse(false, "No se pudo actualizar la categoría"));
			}
		} catch (Exception e) {
			return ResponseEntity.badRequest()
					.body(new ActualizarCategoriaResponse(false, "Error al actualizar categoría: " + e.getMessage()));
		}
	}

	@DeleteMapping("/eliminar/{id}")
	public ResponseEntity<?> eliminarCategoria(@PathVariable int id) {
		boolean eliminado = categoriaService.deleteCategoria(id);
		if (eliminado) {
			return ResponseEntity.ok(new EliminarCategoriaResponse(true, "Categoría eliminada correctamente"));
		} else {
			return ResponseEntity.badRequest()
					.body(new EliminarCategoriaResponse(false, "No se pudo eliminar la categoría"));
		}
	}

	// Response classes
	public static class GetCategoriasResponse {
		private boolean success;
		private String message;
		private List<Categoria> data;

		public GetCategoriasResponse(boolean success, String message, List<Categoria> data) {
			this.success = success;
			this.message = message;
			this.data = data;
		}

		public boolean isSuccess() {
			return success;
		}

		public String getMessage() {
			return message;
		}

		public List<Categoria> getData() {
			return data;
		}
	}

	public static class GetCategoriaResponse {
		private boolean success;
		private String message;
		private Categoria data;

		public GetCategoriaResponse(boolean success, String message, Categoria data) {
			this.success = success;
			this.message = message;
			this.data = data;
		}

		public boolean isSuccess() {
			return success;
		}

		public String getMessage() {
			return message;
		}

		public Categoria getData() {
			return data;
		}
	}

	public static class CrearCategoriaRequest {
		private String nombre;
		private String descripcion;
		private boolean activo;

		public String getNombre() {
			return nombre;
		}

		public void setNombre(String nombre) {
			this.nombre = nombre;
		}

		public String getDescripcion() {
			return descripcion;
		}

		public void setDescripcion(String descripcion) {
			this.descripcion = descripcion;
		}

		public boolean isActivo() {
			return activo;
		}

		public void setActivo(boolean activo) {
			this.activo = activo;
		}
	}

	public static class ActualizarCategoriaRequest {
		private int id;
		private String nombre;
		private String descripcion;
		private boolean activo;

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public String getNombre() {
			return nombre;
		}

		public void setNombre(String nombre) {
			this.nombre = nombre;
		}

		public String getDescripcion() {
			return descripcion;
		}

		public void setDescripcion(String descripcion) {
			this.descripcion = descripcion;
		}

		public boolean isActivo() {
			return activo;
		}

		public void setActivo(boolean activo) {
			this.activo = activo;
		}
	}

	public static class CrearCategoriaResponse {
		private boolean success;
		private String message;

		public CrearCategoriaResponse(boolean success, String message) {
			this.success = success;
			this.message = message;
		}

		public boolean isSuccess() {
			return success;
		}

		public String getMessage() {
			return message;
		}
	}

	public static class ActualizarCategoriaResponse {
		private boolean success;
		private String message;

		public ActualizarCategoriaResponse(boolean success, String message) {
			this.success = success;
			this.message = message;
		}

		public boolean isSuccess() {
			return success;
		}

		public String getMessage() {
			return message;
		}
	}

	public static class EliminarCategoriaResponse {
		private boolean success;
		private String message;

		public EliminarCategoriaResponse(boolean success, String message) {
			this.success = success;
			this.message = message;
		}

		public boolean isSuccess() {
			return success;
		}

		public String getMessage() {
			return message;
		}
	}
}
