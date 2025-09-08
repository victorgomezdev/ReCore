package CatsPrograming.ReCore.modules.articulos;

import java.math.BigDecimal;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Component;

import CatsPrograming.ReCore.dao.DBUtils;

@Component
public class ArticulosModule {

	@Autowired
	private DBUtils db;

	public void init() {
		try {
			// Crear tablas en orden correcto (primero las principales, luego las de
			// relación)
			crearTablaCategorias();
			crearTablaArticulos();
			crearTablaCategoriasArticulos();
		} catch (Exception e) {
			// Error en inicialización
		}
	}

	private void crearTablaCategorias() {
		String sql = """
				CREATE TABLE re_categorias (
					id INT AUTO_INCREMENT PRIMARY KEY,
					nombre VARCHAR(60) NOT NULL UNIQUE,
					descripcion VARCHAR(255) NOT NULL,
					activo TINYINT NOT NULL
				)
				""";

		if (db.crearTabla("re_categorias", sql)) {
			try {
				// Crea índices por separado (Compatible con H2)
				db.execQuery("CREATE INDEX idx_categoria_nombre ON re_categorias (nombre)");
				db.execQuery("CREATE INDEX idx_categoria_activo ON re_categorias (activo)");

				// Generar metadata de campos
				db.generateFieldsInfo("re_categorias");
			} catch (Exception e) {
				// Error al crear índices
			}
		}
	}

	private void crearTablaArticulos() {
		String sql = """
				CREATE TABLE re_articulos (
					id INT AUTO_INCREMENT PRIMARY KEY,
					nombre VARCHAR(100) NOT NULL UNIQUE,
					descripcion VARCHAR(255) NOT NULL,
					precio DECIMAL(12, 2) NOT NULL,
					activo TINYINT NOT NULL
				)
				""";

		if (db.crearTabla("re_articulos", sql)) {
			try {
				// Crea índices por separado (Compatible con H2)
				db.execQuery("CREATE INDEX idx_articulo_nombre ON re_articulos (nombre)");
				db.execQuery("CREATE INDEX idx_articulo_descripcion ON re_articulos (descripcion)");
				db.execQuery("CREATE INDEX idx_articulo_precio ON re_articulos (precio)");
				db.execQuery("CREATE INDEX idx_articulo_activo ON re_articulos (activo)");

				// Generar metadata de campos
				db.generateFieldsInfo("re_articulos");
			} catch (Exception e) {
				// Error al crear índices
			}
		}
	}

	private void crearTablaCategoriasArticulos() {
		String sql = """
				CREATE TABLE re_categorias_articulos (
					id INT AUTO_INCREMENT PRIMARY KEY,
					idcategoria INT NOT NULL,
					idarticulo INT NOT NULL,
					activo TINYINT NOT NULL DEFAULT 1,
					UNIQUE(idcategoria, idarticulo)
				)
				""";

		if (db.crearTabla("re_categorias_articulos", sql)) {
			try {
				// Crea índices por separado (Compatible con H2)
				db.execQuery("CREATE INDEX idx_categoria_articulo_categoria ON re_categorias_articulos (idcategoria)");
				db.execQuery("CREATE INDEX idx_categoria_articulo_articulo ON re_categorias_articulos (idarticulo)");
				db.execQuery("CREATE INDEX idx_categoria_articulo_activo ON re_categorias_articulos (activo)");

				// Crear foreign keys
				db.addForeignKey("re_categorias_articulos", "idcategoria", "re_categorias", "id", false, false);
				db.addForeignKey("re_categorias_articulos", "idarticulo", "re_articulos", "id", false, false);

				// Generar metadata de campos
				db.generateFieldsInfo("re_categorias_articulos");
			} catch (Exception e) {
				// Error al crear índices o FK
			}
		}
	}

	private boolean verificarArticuloExistente(String nombre) {
		String sql = "SELECT COUNT(*) FROM re_articulos WHERE nombre = ?";
		int count = db.getEntero(sql, nombre);
		return count > 0;
	}

	public int crearArticulo(String nombre, String descripcion, BigDecimal precio, boolean activo) {
		try {
			if (verificarArticuloExistente(nombre)) {
				return 0;
			}

			String sql = """
					INSERT INTO re_articulos (nombre, descripcion, precio, activo)
					VALUES (?, ?, ?, ?)
					""";
			return db.insertAndGetID(sql, nombre, descripcion, precio, activo);
		} catch (Exception e) {
			return 0;
		}
	}

	public boolean asignarCategoriaAArticulo(int idArticulo, int idCategoria) {
		try {
			// Verificar si ya existe la relación
			String checkSql = "SELECT COUNT(*) FROM re_categorias_articulos WHERE idarticulo = ? AND idcategoria = ?";
			int count = db.getEntero(checkSql, idArticulo, idCategoria);
			if (count > 0) {
				return false;
			}

			String sql = """
					INSERT INTO re_categorias_articulos (idarticulo, idcategoria, activo)
					VALUES (?, ?, 1)
					""";
			int rowsAffected = db.execQuery(sql, idArticulo, idCategoria);
			return rowsAffected > 0;
		} catch (Exception e) {
			return false;
		}
	}

	public boolean removerCategoriaDeArticulo(int idArticulo, int idCategoria) {
		try {
			String sql = "DELETE FROM re_categorias_articulos WHERE idarticulo = ? AND idcategoria = ?";
			int rowsAffected = db.execQuery(sql, idArticulo, idCategoria);
			return rowsAffected > 0;
		} catch (Exception e) {
			return false;
		}
	}

	public boolean eliminarArticulo(int id) {
		try {
			String sql = "DELETE FROM re_articulos WHERE id = ?";
			int rowsAffected = db.execQuery(sql, id);
			return rowsAffected > 0;
		} catch (Exception e) {
			return false;
		}
	}

	public boolean actualizarArticulo(int id, String nombre, String descripcion,
			BigDecimal precio, boolean activo) {
		try {
			String sql = """
					UPDATE re_articulos SET nombre = ?, descripcion = ?, precio = ?, activo = ?
					WHERE id = ?
					""";
			int rowsAffected = db.execQuery(sql, nombre, descripcion, precio, activo, id);
			return rowsAffected > 0;
		} catch (Exception e) {
			return false;
		}
	}

	public Map<String, Object> getArticuloPorId(int id) {
		String sql = "SELECT * FROM re_articulos WHERE id = ?";
		return db.getResult(sql, id);
	}

	public java.util.List<java.util.Map<String, Object>> getCategoriasPorArticulo(int idArticulo) {
		String sql = """
				SELECT c.* FROM re_categorias c
				INNER JOIN re_categorias_articulos ca ON c.id = ca.idcategoria
				WHERE ca.idarticulo = ? AND ca.activo = 1
				ORDER BY c.nombre
				""";
		return db.getList(sql, idArticulo);
	}

	public java.util.List<java.util.Map<String, Object>> getArticulos(Integer idcategoria, Boolean activo,
			String nombre, String descripcion) {
		StringBuilder sql = new StringBuilder();
		java.util.List<Object> params = new java.util.ArrayList<>();

		if (idcategoria != null) {
			// Si se filtra por categoría, usar JOIN con la tabla intermedia
			sql.append("""
					SELECT DISTINCT a.* FROM re_articulos a
					INNER JOIN re_categorias_articulos ca ON a.id = ca.idarticulo
					WHERE ca.idcategoria = ? AND ca.activo = 1
					""");
			params.add(idcategoria);
		} else {
			// Si no se filtra por categoría, consultar solo artículos
			sql.append("SELECT a.* FROM re_articulos a WHERE 1=1 ");
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
		return db.getList(sql.toString(), params.toArray());
	}

}
