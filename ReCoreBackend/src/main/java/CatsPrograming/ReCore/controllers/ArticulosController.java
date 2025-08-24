package CatsPrograming.ReCore.controllers;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import CatsPrograming.ReCore.modules.articulos.ArticulosModule;

@RestController
@RequestMapping("api/articulos")
@CrossOrigin(origins = "*")
public class ArticulosController {

	@Autowired
	private ArticulosModule articulosModule;

	@GetMapping("/getArticuloId")
	public ResponseEntity<?> getArticuloPorId(@RequestParam int id) {
		Map<String, Object> articulo = articulosModule.getArticuloPorId(id);
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
		private Map<String, Object> data;

		public GetArticuloIdResponse(boolean success, String message, Map<String, Object> data) {
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

		public Map<String, Object> getData() {
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
			List<Map<String, Object>> articulos = articulosModule.getArticulos(idcategoria, activo, nombre,
					descripcion);
			return ResponseEntity.ok(new BuscarArticulosResponse(true, "OK", articulos));
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(new BuscarArticulosResponse(false, e.getMessage(), null));
		}
	}

	public static class BuscarArticulosResponse {
		private boolean success;
		private String message;
		private List<Map<String, Object>> data;

		public BuscarArticulosResponse(boolean success, String message, List<Map<String, Object>> data) {
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

		public List<Map<String, Object>> getData() {
			return data;
		}
	}

	@DeleteMapping("/eliminar/{id}")
	public ResponseEntity<?> eliminarArticulo(@PathVariable int id) {
		boolean eliminado = articulosModule.eliminarArticulo(id);
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
		boolean actualizado = articulosModule.actualizarArticulo(
				request.getId(),
				request.getNombre(),
				request.getIdcategoria(),
				request.getDescripcion(),
				request.getPrecio(),
				request.isActivo());
		if (actualizado) {
			return ResponseEntity.ok(new ActualizarArticuloResponse(true, "Artículo actualizado correctamente"));
		} else {
			return ResponseEntity.badRequest()
					.body(new ActualizarArticuloResponse(false, "No se pudo actualizar el artículo"));
		}
	}

	public static class ActualizarArticuloRequest {
		private int id;
		private String nombre;
		private int idcategoria;
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

		public int getIdcategoria() {
			return idcategoria;
		}

		public void setIdcategoria(int idcategoria) {
			this.idcategoria = idcategoria;
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

}
