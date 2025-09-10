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
			// Crear tablas en el orden correcto: primero las tablas base, luego las
			// dependientes
			crearTablaMenus();
			crearTablaQuerys();
			crearTablaFields();

			// Después de crear todas las tablas, generar metadata
			generarMetadataInicial();

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

		// Crear tabla sin metadata automática
		if (db.existeTabla("re_menus")) {
			System.out.println("[ReCore] Tabla re_menus ya existe");
		} else {
			db.execQuery(sql);
			System.out.println("[ReCore] Tabla re_menus creada");
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

		// Crear tabla sin metadata automática
		if (db.existeTabla("re_queries")) {
			System.out.println("[ReCore] Tabla re_queries ya existe");
		} else {
			db.execQuery(sql);
			// Crear índices por separado (compatible con H2)
			db.execQuery("CREATE INDEX idx_re_queries_table ON re_queries (table_name)");
			System.out.println("[ReCore] Tabla re_queries creada");
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
						tipo VARCHAR(60),
						ancho INT DEFAULT NULL
				    )
				""";

		// Crear tabla sin metadata automática
		if (db.existeTabla("re_queries_fields")) {
			System.out.println("[ReCore] Tabla re_queries_fields ya existe");
		} else {
			db.execQuery(sql);
			System.out.println("[ReCore] Tabla re_queries_fields creada");
		}
	}

	public List<Map<String, Object>> obtenerMenu() {
		List<Map<String, Object>> resultado = new ArrayList<>();
		try {
			// Obtener todos los menús activos ordenados
			String sqlMenus = "SELECT id, nombre, icon, color, orden FROM re_menus WHERE activo = 1 ORDER BY nombre";
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
	 * Genera la metadata inicial después de crear todas las tablas
	 */
	private void generarMetadataInicial() {
		try {
			// Agregar foreign keys
			db.addForeignKey("re_queries", "idmenu", "re_menus", "id", false, false);
			db.addForeignKey("re_queries_fields", "idquery", "re_queries", "id", false, false);

			// Crear menú Root si no existe
			String sqlCheckRoot = "SELECT COUNT(*) FROM re_menus WHERE nombre = 'Root'";
			if (db.getEntero(sqlCheckRoot) == 0) {
				String sqlInsertRoot = "INSERT INTO re_menus (nombre, icon, color, orden, activo) VALUES ('Root', '🏠', '#000000', 0, 1)";
				db.execQuery(sqlInsertRoot);
				System.out.println("[ReCore] Menú Root creado");
			}

			// Generar metadata para las tablas del sistema
			db.generateFieldsInfo("re_menus");
			db.generateFieldsInfo("re_queries");
			db.generateFieldsInfo("re_queries_fields");

			System.out.println("[ReCore] Metadata inicial generada");
		} catch (Exception e) {
			System.err.println("[ReCore] Error generando metadata inicial: " + e.getMessage());
		}
	}
}
