package CatsPrograming.ReCore.modules.core;

import org.springframework.stereotype.Component;

import CatsPrograming.ReCore.dao.DBUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * Módulo para gestionar las tablas de la base de datos
 * y sus respectivos campo y menúes
 */
@Component
public class QueriesModule {
	@Autowired
	private DBUtils db;

	public void init() {
		System.out.println("[ReCore] Iniciando módulo de querys");

		try {
			// Crear tablas de querys
			crearTablaMenus();
			crearTablaQuerys();
			crearTablaFields();

			// Insertar datos de ejemplo
			insertarDatosEjemplo();

			System.out.println("[ReCore] Módulo de querys inicializado correctamente");
		} catch (Exception e) {
			System.err.println("[ReCore] Error en inicialización de querys: " + e.getMessage());
			e.printStackTrace();
		}
	}

	private void crearTablaMenus() {
		String sql = """
					CREATE TABLE re_menus (
						id INT AUTO_INCREMENT PRIMARY KEY,
						nombre VARCHAR(60) NOT NULL,
						icon VARCHAR(60) NOT NULL,
						color VARCHAR(60) NOT NULL,
						orden INT NOT NULL,
						activo TINYINT NOT NULL
					)
				""";

		if (db.crearTabla("re_menus", sql)) {
			try {
				// Generar metadata de campos
				db.generateFieldsInfo("re_menus");
				System.out.println("[ReCore] Tabla re_menus creada");
			} catch (Exception e) {
				System.err.println("[ReCore] Error al procesar metadata de re_menus: " + e.getMessage());
			}
		}
	}

	/**
	 * Crea la tabla re_queries para gestionar tablas de manera dinámica
	 */
	private void crearTablaQuerys() {
		String sql = """
				    CREATE TABLE re_queries (
				        id INT AUTO_INCREMENT PRIMARY KEY,
				        idmenu INT,
				        table_name VARCHAR(60) NOT NULL,
				        query_name VARCHAR(60) NOT NULL,
				        query_description VARCHAR(60) NOT NULL,
				        fields VARCHAR(700) NOT NULL,
				        can_insert TINYINT NOT NULL,
				        can_edit TINYINT NOT NULL,
				        can_delete TINYINT NOT NULL,
				        debil TINYINT NOT NULL,
				        icon VARCHAR(60) NOT NULL,
				        checksum INT NOT NULL
				    )
				""";

		if (db.crearTabla("re_queries", sql)) {
			try {
				// Crear índices por separado (compatible con H2)
				db.execQuery("CREATE INDEX idx_re_queries_table ON re_queries (table_name)");
				// Agregar foreign key de idmenu a re_menus(id)
				db.addForeignKey("re_queries", "idmenu", "re_menus", "id", false, false);

				// Generar metadata de campos
				db.generateFieldsInfo("re_queries");
				System.out.println("[ReCore] Tabla re_queries creada");
			} catch (Exception e) {
				System.err.println("[ReCore] Error al crear índices de la tabla re_queries: " + e.getMessage());
			}
		}
	}

	private void crearTablaFields() {
		String sql = """
				    CREATE TABLE re_queries_fields (
				        id INT AUTO_INCREMENT PRIMARY KEY,
						idquery INT NOT NULL,
						field VARCHAR(60) NOT NULL,
						show_name VARCHAR(60) NOT NULL,
						is_required TINYINT DEFAULT 0,
						password_field TINYINT DEFAULT NULL,
						color_field TINYINT DEFAULT NULL,
						rich_text TINYINT DEFAULT NULL,
						is_editable TINYINT DEFAULT 0,
						visible TINYINT DEFAULT 1,
						ocultar_vacio TINYINT DEFAULT 0,
						grupo VARCHAR(60),
						field_help VARCHAR(255),
						ancho INT DEFAULT NULL
				    )
				""";

		if (db.crearTabla("re_queries_fields", sql)) {
			try {
				db.addForeignKey("re_queries_fields", "idquery", "re_queries", "id", false, false);

				// Generar metadata de campos
				db.generateFieldsInfo("re_queries_fields");
				System.out.println("[ReCore] Tabla re_queries_fields creada");
			} catch (Exception e) {
				System.err.println("[ReCore] Error al crear índices de la tabla re_queries_fields: " + e.getMessage());
			}
		}
	}

	public List<Map<String, Object>> obtenerMenu() {
		List<Map<String, Object>> resultado = new ArrayList<>();
		try {
			// Obtener todos los menús activos ordenados
			String sqlMenus = "SELECT id, nombre, icon, color, orden FROM re_menus WHERE activo = 1 ORDER BY orden";
			List<Map<String, Object>> menus = db.getList(sqlMenus);

			// Para cada menú, obtener sus queries (tablas asociadas)
			for (Map<String, Object> menu : menus) {
				Integer idMenu = (Integer) menu.get("id");
				String sqlTablas = "SELECT id, table_name, query_name, query_description, icon FROM re_queries WHERE idmenu = ?";
				List<Map<String, Object>> tablas = db.getList(sqlTablas, idMenu);
				menu.put("tablas", tablas);
				resultado.add(menu);
			}
		} catch (Exception e) {
			System.err.println("[ReCore] Error al obtener menús con tablas: " + e.getMessage());
		}
		return resultado;
	}

	/**
	 * Inserta datos de ejemplo en las tablas de menús y queries
	 */
	private void insertarDatosEjemplo() {
		try {
			// Verificar si ya existen datos en re_menus
			String checkMenus = "SELECT COUNT(*) FROM re_menus";
			int countMenus = db.getEntero(checkMenus);

			if (countMenus == 0) {
				// Insertar menús de ejemplo
				String insertMenu1 = "INSERT INTO re_menus (nombre, icon, color, orden, activo) VALUES (?, ?, ?, ?, ?)";
				db.execQuery(insertMenu1, "Artículos", "📦", "#3498db", 1, 1);

				String insertMenu2 = "INSERT INTO re_menus (nombre, icon, color, orden, activo) VALUES (?, ?, ?, ?, ?)";
				db.execQuery(insertMenu2, "Usuarios", "👥", "#e74c3c", 2, 1);

				String insertMenu3 = "INSERT INTO re_menus (nombre, icon, color, orden, activo) VALUES (?, ?, ?, ?, ?)";
				db.execQuery(insertMenu3, "Configuración", "⚙️", "#95a5a6", 3, 1);

				System.out.println("[ReCore] Datos de ejemplo insertados en re_menus");
			}

			// Verificar si ya existen datos en re_queries
			String checkQueries = "SELECT COUNT(*) FROM re_queries";
			int countQueries = db.getEntero(checkQueries);

			if (countQueries == 0) {
				// Obtener IDs de los menús creados
				String getMenuArticulos = "SELECT id FROM re_menus WHERE nombre = 'Artículos'";
				int idMenuArticulos = db.getEntero(getMenuArticulos);

				String getMenuUsuarios = "SELECT id FROM re_menus WHERE nombre = 'Usuarios'";
				int idMenuUsuarios = db.getEntero(getMenuUsuarios);

				// Insertar queries de ejemplo
				if (idMenuArticulos > 0) {
					String insertQuery1 = "INSERT INTO re_queries (idmenu, table_name, query_name, query_description, fields, can_insert, can_edit, can_delete, debil, icon, checksum) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
					db.execQuery(insertQuery1, idMenuArticulos, "re_articulos", "Gestión de Artículos",
							"Administrar productos y artículos", "id,nombre,descripcion,precio,activo", 1, 1, 1, 0,
							"📦", 0);

					String insertQuery2 = "INSERT INTO re_queries (idmenu, table_name, query_name, query_description, fields, can_insert, can_edit, can_delete, debil, icon, checksum) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
					db.execQuery(insertQuery2, idMenuArticulos, "re_categorias", "Gestión de Categorías",
							"Administrar categorías de productos", "id,nombre,descripcion,activo", 1, 1, 1, 0, "🏷️",
							0);
				}

				if (idMenuUsuarios > 0) {
					String insertQuery3 = "INSERT INTO re_queries (idmenu, table_name, query_name, query_description, fields, can_insert, can_edit, can_delete, debil, icon, checksum) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
					db.execQuery(insertQuery3, idMenuUsuarios, "re_usuarios", "Gestión de Usuarios",
							"Administrar usuarios del sistema", "id,email,activo", 1, 1, 1, 0, "👤", 0);
				}

				System.out.println("[ReCore] Datos de ejemplo insertados en re_queries");
			}

		} catch (Exception e) {
			System.err.println("[ReCore] Error al insertar datos de ejemplo: " + e.getMessage());
			e.printStackTrace();
		}
	}
}
