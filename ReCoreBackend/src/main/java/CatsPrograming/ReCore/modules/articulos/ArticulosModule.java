package CatsPrograming.ReCore.modules.articulos;

import java.math.BigDecimal;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import CatsPrograming.ReCore.utils.DBUtils;

public class ArticulosModule {

	@Autowired
	private DBUtils db;

	public void init() {
		System.out.println("[ReCore] Iniciando módulo de artículos");

		try {
			// Crear tablas de artículos
			crearTablaCategorias();
			crearTablaArticulos();
		} catch (Exception e) {
			System.err.println("[ReCore] Error en inicialización de artículos: " + e.getMessage());
		}
	}

	private void crearTablaCategorias() {
		if (!db.existeTabla("re_categorias")) {
			try {
				String sql = """
							CREATE TABLE re_categorias (
								id INT AUTO_INCREMENT PRIMARY KEY,
								nombre VARCHAR(60) NOT NULL UNIQUE,
								descripcion VARCHAR(255) NOT NULL,
								activo TINYINT(1) NOT NULL
							)
						""";

				db.execQuery(sql);

				// Crea índices por separado (Compatible con H2)
				db.execQuery("CREATE INDEX idx_categoria_nombre ON re_categorias (nombre)");
				db.execQuery("CREATE INDEX idx_categoria_activo ON re_categorias (activo)");

				db.generateFieldsInfo("re_categorias", 0);

				System.out.println("[ReCore] Tabla re_categorias creada");
			} catch (Exception e) {
				System.err.println("[ReCore] Error al crear tabla re_categorias: " + e.getMessage());
			}
		}
	}

	private void crearTablaArticulos() {
		if (!db.existeTabla("re_articulos")) {
			try {
				String sql = """
							CREATE TABLE re_articulos (
								id INT AUTO_INCREMENT PRIMARY KEY,
								nombre VARCHAR(100) NOT NULL UNIQUE,
								idcategoria INT NOT NULL,
								descripcion VARCHAR(255) NOT NULL,
								precio DECIMAL(10, 6) NOT NULL,
								activo TINYINT(1) NOT NULL
							)
						""";

				db.execQuery(sql);

				// Crea índices por separado (Compatible con H2)
				db.execQuery("CREATE INDEX idx_articulo_nombre ON re_articulos (nombre)");
				db.execQuery("CREATE INDEX idx_articulo_categoria ON re_articulos (idcategoria)");
				db.execQuery("CREATE INDEX idx_articulo_descripcion ON re_articulos (descripcion)");
				db.execQuery("CREATE INDEX idx_articulo_precio ON re_articulos (precio)");
				db.execQuery("CREATE INDEX idx_articulo_activo ON re_articulos (activo)");

				db.generateFieldsInfo("re_articulos", 0);
				db.addForeignKey("re_articulos", "idcategoria", "re_categorias", "id", false, false);
				System.out.println("[ReCore] Tabla re_articulos creada");
			} catch (Exception e) {
				System.err.println("[ReCore] Error al crear tabla re_articulos: " + e.getMessage());
			}
		}
	}

	private boolean verificarArticuloExistente(String nombre, int idcategoria) {
		String sql = "SELECT COUNT(*) FROM re_articulos WHERE nombre = ? AND idcategoria = ?";
		int count = db.obtenerEntero(sql, nombre, idcategoria);
		return count > 0;
	}

	public int crearArticulo(String nombre, int idcategoria, String descripcion, BigDecimal precio,
			boolean activo) {
		try {
			if (verificarArticuloExistente(nombre, idcategoria)) {
				System.err.println("[ReCore] El artículo ya existe: " + nombre);
				return 0;
			}

			String sql = """
					INSERT INTO re_articulos (nombre, idcategoria, descripcion, precio, activo)
					VALUES (?, ?, ?, ?, ?)
					""";
			return db.execQueryGetId(sql, nombre, idcategoria, descripcion, precio, activo);
		} catch (Exception e) {
			System.err.println("[ReCore] Error al crear artículo: " + e.getMessage());
			return 0;
		}
	}

	public boolean eliminarArticulo(int id) {
		try {
			String sql = "DELETE FROM re_articulos WHERE id = ?";
			int rowsAffected = db.execQuery(sql, id);
			return rowsAffected > 0;
		} catch (Exception e) {
			System.err.println("[ReCore] Error al eliminar artículo: " + e.getMessage());
			return false;
		}
	}

	public boolean actualizarArticulo(int id, String nombre, int idcategoria, String descripcion,
			BigDecimal precio, boolean activo) {
		try {
			String sql = """
					UPDATE re_articulos SET nombre = ?, idcategoria = ?, descripcion = ?, precio = ?, activo = ?
					WHERE id = ?
					""";
			int rowsAffected = db.execQuery(sql, nombre, idcategoria, descripcion, precio, activo, id);
			return rowsAffected > 0;
		} catch (Exception e) {
			System.err.println("[ReCore] Error al actualizar artículo: " + e.getMessage());
			return false;
		}
	}

	public Map<String, Object> getArticuloPorId(int id) {
		String sql = "SELECT a.*, c.nombre AS categoria_nombre, c.descripcion AS categoria_descripcion FROM re_articulos a "
				+ "LEFT JOIN re_categorias c ON a.idcategoria = c.id WHERE a.id = ?";
		return db.execQueryResult(sql, id);
	}

	public java.util.List<java.util.Map<String, Object>> getArticulos(Integer idcategoria, Boolean activo,
			String nombre, String descripcion) {
		StringBuilder sql = new StringBuilder();
		sql.append(
				"SELECT a.*, c.nombre AS categoria_nombre, c.descripcion AS categoria_descripcion FROM re_articulos a ");
		sql.append("LEFT JOIN re_categorias c ON a.idcategoria = c.id WHERE 1=1 ");
		java.util.List<Object> params = new java.util.ArrayList<>();
		if (idcategoria != null) {
			sql.append(" AND a.idcategoria = ?");
			params.add(idcategoria);
		}
		if (activo != null) {
			sql.append(" AND a.activo = ?");
			params.add(activo ? 1 : 0);
		}
		if (nombre != null && !nombre.isBlank()) {
			sql.append(" AND a.nombre LIKE ?");
			params.add("%" + nombre + "%");
		}
		if (descripcion != null && !descripcion.isBlank()) {
			sql.append(" AND a.descripcion LIKE ?");
			params.add("%" + descripcion + "%");
		}
		sql.append(" ORDER BY a.id DESC");
		return db.execQueryList(sql.toString(), params.toArray());
	}
}
