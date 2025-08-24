package CatsPrograming.ReCore.modules.core;

import org.springframework.stereotype.Component;

import org.springframework.beans.factory.annotation.Autowired;

import CatsPrograming.ReCore.utils.DBUtils;

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
		} catch (Exception e) {
			System.err.println("[ReCore] Error en inicialización de querys: " + e.getMessage());
		}
	}

	private void crearTablaMenus() {
		if (!db.existeTabla("re_menus")) {
			try {
				String sql = """
							CREATE TABLE re_menus (
								id INT AUTO_INCREMENT PRIMARY KEY,
								nombre VARCHAR(60) NOT NULL,
								activo TINYINT(3) NOT NULL,
								orden INT NOT NULL,
								icon VARCHAR(60) NOT NULL,
								color VARCHAR(60) NOT NULL
							)
						""";

				db.execQuery(sql);
				db.generateFieldsInfo("re_menus", 0);
				System.out.println("[ReCore] Tabla re_menus creada");
			} catch (Exception e) {
				System.err.println("[ReCore] Error al crear tabla re_menus: " + e.getMessage());
			}
		}
	}

	/**
	 * Crea la tabla re_queries para gestionar tablas de manera dinámica
	 */
	private void crearTablaQuerys() {
		if (!db.existeTabla("re_queries")) {
			try {
				String sql = """
						    CREATE TABLE re_queries (
						        id INT AUTO_INCREMENT PRIMARY KEY,
						        idmenu INT,
						        table VARCHAR(60) NOT NULL,
						        query_name VARCHAR(60) NOT NULL,
						        query_description VARCHAR(60) NOT NULL,
						        fields VARCHAR(700) NOT NULL,
						        can_insert TINYINT(3) UNSIGNED NOT NULL,
						        can_edit TINYINT(3) UNSIGNED NOT NULL,
						        can_delete TINYINT(3) UNSIGNED NOT NULL,
						        debil TINYINT(3) UNSIGNED NOT NULL,
						        icon VARCHAR(60) NOT NULL,
						        checksum INT NOT NULL
						    )
						""";
				db.execQuery(sql);
				db.generateFieldsInfo("re_queries", 0);
				// Crear índices por separado (compatible con H2)
				db.execQuery("CREATE INDEX idx_re_queries_table ON re_queries (table)");
				db.execQuery("CREATE INDEX idx_re_queries_queryname ON re_queries (query_name)");
				// Agregar foreign key de idmenu a re_menus(id)
				db.addForeignKey("re_queries", "idmenu", "re_menus", "id", false, false);
			} catch (Exception e) {
				System.err.println("[ReCore] Error al crear índices de la tabla re_queries: " + e.getMessage());
			}

			System.out.println("[ReCore] Tabla re_queries creada");

		}
	}

	private void crearTablaFields() {
		if (!db.existeTabla("re_queries_fields")) {
			try {
				String sql = """
						    CREATE TABLE re_queries_fields (
						        id INT AUTO_INCREMENT PRIMARY KEY,
								idquery INT NOT NULL,
								field VARCHAR(60) NOT NULL,
								show_name VARCHAR(60) NOT NULL,
								is_required TINYINT(3) DEFAULT 0,
								password_field TINYINT(3) DEFAULT NULL,
								color_field TINYINT(3) DEFAULT NULL,
								rich_text TINYINT(3) DEFAULT NULL,
								is_editable TINYINT(3) DEFAULT 0,
								visible TINYINT(3) DEFAULT 1,
								ocultar_vacio TINYINT(3) DEFAULT 0,
								grupo VARCHAR(60),
								field_help VARCHAR(255),
								ancho INT DEFAULT NULL
						    )
						""";
				db.execQuery(sql);
				db.addForeignKey("re_queries_fields", "idquery", "re_queries", "id", false, false);
				db.generateFieldsInfo("re_queries_fields", 0);
			} catch (Exception e) {
				System.err.println("[ReCore] Error al crear índices de la tabla re_queries_fields: " + e.getMessage());
			}

			System.out.println("[ReCore] Tabla re_queries_fields creada");
		}
	}
}
