package CatsPrograming.ReCore.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import CatsPrograming.ReCore.services.Core.MenuService;
import java.util.List;
import java.util.Map;

/**
 * Aquí se pondrán todas las funciones básicas del sistema
 * EJ: Obtención de menu
 */
@RestController
@RequestMapping("/api/core")
public class CoreController {

	@Autowired
	private MenuService menuService;

	/**
	 * Obtiene todos los menús y sus tablas asociadas
	 *
	 */
	@GetMapping("/getMenu")
	public ResponseEntity<?> getMenu() {
		List<Map<String, Object>> data = menuService.obtenerMenu();
		if (data != null && !data.isEmpty()) {
			return ResponseEntity.ok(new GetMenuResponse(true, "OK", data));
		} else {
			return ResponseEntity.badRequest().body(new GetMenuResponse(false, "No se encontraron menús", null));
		}
	}

	public static class GetMenuResponse {
		private boolean success;
		private String message;
		private List<Map<String, Object>> data;

		public GetMenuResponse(boolean success, String message, List<Map<String, Object>> data) {
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
}
