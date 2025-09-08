package CatsPrograming.ReCore.controllers;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import CatsPrograming.ReCore.models.Articulos.Articulo;
import CatsPrograming.ReCore.services.Articulos.ArticulosService;

@RestController
@RequestMapping("api/articulos")
@CrossOrigin(origins = "*")
public class ArticulosController {

	@Autowired
	private ArticulosService articulosService;

	@GetMapping("/getArticuloId")
	public ResponseEntity<?> getArticuloPorId(@RequestParam int id) {
		Articulo articulo = articulosService.getArticuloById(id);
		if (articulo != null) {
			return ResponseEntity.ok(new GetArticuloIdResponse(true, "OK", articulo));
		} else {
			return ResponseEntity.badRequest()
					.body(new GetArticuloIdResponse(false, "No se encontró el artículo", null));
		}
	}

	public static class GetArticuloIdResponse {
		private boolean success;
		private String message;
		private Articulo data;

		public GetArticuloIdResponse(boolean success, String message, Articulo data) {
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

		public Articulo getData() {
			return data;
		}
	}

	@GetMapping("/getArticulos")
	public ResponseEntity<?> getArticulos(
			@RequestParam(required = false) Integer idcategoria,
			@RequestParam(required = false) Boolean activo,
			@RequestParam(required = false) String nombre,
			@RequestParam(required = false) String descripcion) {
		try {
			List<Articulo> articulos = articulosService.buscarArticulos(idcategoria, activo, nombre, descripcion);
			return ResponseEntity.ok(new BuscarArticulosResponse(true, "OK", articulos));
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(new BuscarArticulosResponse(false, e.getMessage(), null));
		}
	}

	public static class BuscarArticulosResponse {
		private boolean success;
		private String message;
		private List<Articulo> data;

		public BuscarArticulosResponse(boolean success, String message, List<Articulo> data) {
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

		public List<Articulo> getData() {
			return data;
		}
	}

	@PostMapping("/crear")
	public ResponseEntity<?> crearArticulo(@RequestBody CrearArticuloRequest request) {
		try {
			Articulo articulo = new Articulo(request.getNombre(), request.getDescripcion(),
					request.getPrecio(), request.isActivo() ? 1 : 0);

			int idArticulo = articulosService.crearArticuloConCategorias(articulo, request.getIdsCategorias());

			if (idArticulo > 0) {
				return ResponseEntity
						.ok(new CrearArticuloResponse(true, "Artículo creado correctamente", idArticulo));
			} else {
				return ResponseEntity.badRequest()
						.body(new CrearArticuloResponse(false, "No se pudo crear el artículo", 0));
			}
		} catch (Exception e) {
			return ResponseEntity.badRequest()
					.body(new CrearArticuloResponse(false, "Error al crear artículo: " + e.getMessage(), 0));
		}
	}

	public static class CrearArticuloRequest {
		private String nombre;
		private List<Integer> idsCategorias;
		private String descripcion;
		private BigDecimal precio;
		private boolean activo;

		// Getters y setters
		public String getNombre() {
			return nombre;
		}

		public void setNombre(String nombre) {
			this.nombre = nombre;
		}

		public List<Integer> getIdsCategorias() {
			return idsCategorias;
		}

		public void setIdsCategorias(List<Integer> idsCategorias) {
			this.idsCategorias = idsCategorias;
		}

		public String getDescripcion() {
			return descripcion;
		}

		public void setDescripcion(String descripcion) {
			this.descripcion = descripcion;
		}

		public BigDecimal getPrecio() {
			return precio;
		}

		public void setPrecio(BigDecimal precio) {
			this.precio = precio;
		}

		public boolean isActivo() {
			return activo;
		}

		public void setActivo(boolean activo) {
			this.activo = activo;
		}
	}

	public static class CrearArticuloResponse {
		private boolean success;
		private String message;
		private int id;

		public CrearArticuloResponse(boolean success, String message, int id) {
			this.success = success;
			this.message = message;
			this.id = id;
		}

		public boolean isSuccess() {
			return success;
		}

		public void setSuccess(boolean success) {
			this.success = success;
		}

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public String getMessage() {
			return message;
		}

		public void setMessage(String message) {
			this.message = message;
		}
	}

	@DeleteMapping("/eliminar/{id}")
	public ResponseEntity<?> eliminarArticulo(@PathVariable int id) {
		boolean eliminado = articulosService.deleteArticulo(id);
		if (eliminado) {
			return ResponseEntity.ok(new EliminarArticuloResponse(true, "Artículo eliminado correctamente"));
		} else {
			return ResponseEntity.badRequest()
					.body(new EliminarArticuloResponse(false, "No se pudo eliminar el artículo"));
		}
	}

	public static class EliminarArticuloResponse {
		private boolean eliminado;
		private String mensaje;

		public EliminarArticuloResponse(boolean eliminado, String mensaje) {
			this.eliminado = eliminado;
			this.mensaje = mensaje;
		}

		public boolean isEliminado() {
			return eliminado;
		}

		public String getMensaje() {
			return mensaje;
		}
	}

	@PutMapping("/actualizar")
	public ResponseEntity<?> actualizarArticulo(@RequestBody ActualizarArticuloRequest request) {
		try {
			Articulo articulo = new Articulo(
					request.getId(),
					request.getNombre(),
					request.getDescripcion(),
					request.getPrecio(),
					request.isActivo() ? 1 : 0, null);

			boolean actualizado = articulosService.actualizarArticuloConCategorias(articulo,
					request.getIdsCategorias());

			if (actualizado) {
				return ResponseEntity.ok(new ActualizarArticuloResponse(true, "Artículo actualizado correctamente"));
			} else {
				return ResponseEntity.badRequest()
						.body(new ActualizarArticuloResponse(false, "No se pudo actualizar el artículo"));
			}
		} catch (Exception e) {
			return ResponseEntity.badRequest()
					.body(new ActualizarArticuloResponse(false, "Error al actualizar artículo: " + e.getMessage()));
		}
	}

	public static class ActualizarArticuloRequest {
		private int id;
		private String nombre;
		private List<Integer> idsCategorias;
		private String descripcion;
		private BigDecimal precio;
		private boolean activo;

		// Getters y setters
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

		public List<Integer> getIdsCategorias() {
			return idsCategorias;
		}

		public void setIdsCategorias(List<Integer> idsCategorias) {
			this.idsCategorias = idsCategorias;
		}

		public String getDescripcion() {
			return descripcion;
		}

		public void setDescripcion(String descripcion) {
			this.descripcion = descripcion;
		}

		public BigDecimal getPrecio() {
			return precio;
		}

		public void setPrecio(BigDecimal precio) {
			this.precio = precio;
		}

		public boolean isActivo() {
			return activo;
		}

		public void setActivo(boolean activo) {
			this.activo = activo;
		}
	}

	public static class ActualizarArticuloResponse {
		private boolean actualizado;
		private String mensaje;

		public ActualizarArticuloResponse(boolean actualizado, String mensaje) {
			this.actualizado = actualizado;
			this.mensaje = mensaje;
		}

		public boolean isActualizado() {
			return actualizado;
		}

		public String getMensaje() {
			return mensaje;
		}
	}

	@PostMapping("/asignarCategorias/{idArticulo}")
	public ResponseEntity<?> asignarCategoriasAArticulo(@PathVariable int idArticulo,
			@RequestBody List<Integer> idCategorias) {
		try {
			boolean asignado = articulosService.asignarCategoriasAArticulo(idArticulo, idCategorias);
			if (asignado) {
				return ResponseEntity.ok(new AsignarCategoriasResponse(true, "Categorías asignadas correctamente"));
			} else {
				return ResponseEntity.badRequest()
						.body(new AsignarCategoriasResponse(false, "No se pudieron asignar las categorías"));
			}
		} catch (Exception e) {
			return ResponseEntity.badRequest()
					.body(new AsignarCategoriasResponse(false, "Error al asignar categorías: " + e.getMessage()));
		}
	}

	@DeleteMapping("/removerCategorias/{idArticulo}")
	public ResponseEntity<?> removerCategoriasDeArticulo(@PathVariable int idArticulo) {
		try {
			boolean removido = articulosService.eliminarCategoriasPorArticulo(idArticulo);
			if (removido) {
				return ResponseEntity.ok(new AsignarCategoriasResponse(true, "Categorías removidas correctamente"));
			} else {
				return ResponseEntity.badRequest()
						.body(new AsignarCategoriasResponse(false, "No se pudieron remover las categorías"));
			}
		} catch (Exception e) {
			return ResponseEntity.badRequest()
					.body(new AsignarCategoriasResponse(false, "Error al remover categorías: " + e.getMessage()));
		}
	}

	public static class AsignarCategoriasResponse {
		private boolean success;
		private String mensaje;

		public AsignarCategoriasResponse(boolean success, String mensaje) {
			this.success = success;
			this.mensaje = mensaje;
		}

		public boolean isSuccess() {
			return success;
		}

		public String getMensaje() {
			return mensaje;
		}
	}
}
