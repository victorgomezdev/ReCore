package CatsPrograming.ReCore.dao.Articulos;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import CatsPrograming.ReCore.dao.DBUtils;
import CatsPrograming.ReCore.dao.IDao;
import CatsPrograming.ReCore.models.Articulos.Articulo;
import CatsPrograming.ReCore.models.Articulos.Categoria;

@Repository
public class ArticuloDao implements IDao<Articulo> {

	@Autowired
	DBUtils db;

	@Autowired
	CategoriaDao categoriaDao;

	@Autowired
	CategoriaArticuloDao categoriaArticuloDao;

	private static final String SQL_INSERT = "INSERT INTO re_articulos (nombre, descripcion, precio, activo) VALUES (?, ?, ?, ?)";

	private static final String SQL_UPDATE = "UPDATE re_articulos SET nombre = ?, descripcion = ?, precio = ?, activo = ? WHERE id = ?";

	private static final String SQL_DELETE = "DELETE FROM re_articulos WHERE id = ?";

	private static final String SQL_SELECT_BY_ID = "SELECT * FROM re_articulos WHERE id = ?";

	private static final String SQL_SELECT_ALL = "SELECT * FROM re_articulos";

	private static final String SQL_FIND_WHERE = "SELECT * FROM re_articulos WHERE ";

	@Override
	public boolean insert(Articulo articulo) {
		try {
			// Validar que los campos requeridos no sean null
			if (articulo.getNombre() == null || articulo.getNombre().trim().isEmpty()) {
				return false;
			}
			if (articulo.getDescripcion() == null) {
				return false;
			}
			if (articulo.getPrecio() == null) {
				return false;
			}
			if (articulo.getActivo() == null) {
				return false;
			}

			// Intentar inserción básica primero
			int rowsAffected = db.execQuery(SQL_INSERT, articulo.getNombre(), articulo.getDescripcion(),
					articulo.getPrecio(), articulo.getActivo());

			if (rowsAffected > 0) {
				// Obtener el último ID insertado
				String getLastIdSql = "SELECT MAX(id) FROM re_articulos WHERE nombre = ?";
				int id = db.getEntero(getLastIdSql, articulo.getNombre());

				articulo.setId(id);
				return true;
			}

			return false;
		} catch (Exception e) {
			db.log("re_articulos", 0, "InsertArticulo", null, null);
			return false;
		}
	}

	@Override
	public boolean update(Articulo articulo) {
		try {
			db.execQuery(SQL_UPDATE, articulo.getNombre(), articulo.getDescripcion(),
					articulo.getPrecio(), articulo.getActivo(), articulo.getId());
			return true;
		} catch (Exception e) {
			db.log("re_articulos", 0, "UpdateArticulo", null, null);
			return false;
		}
	}

	@Override
	public boolean delete(int id) {
		try {
			// Primero eliminar las relaciones con categorías
			categoriaArticuloDao.eliminarCategoriasPorArticulo(id);
			// Luego eliminar el artículo
			db.execQuery(SQL_DELETE, id);
			return true;
		} catch (Exception e) {
			db.log("re_articulos", 0, "DeleteArticulo", null, null);
			return false;
		}
	}

	@Override
	public Articulo getById(int id) {
		try {
			Map<String, Object> result = db.getResult(SQL_SELECT_BY_ID, id);
			if (result != null) {
				Articulo articulo = new Articulo(result);
				cargarCategorias(articulo);
				return articulo;
			}
			return null;
		} catch (Exception e) {
			db.log("re_articulos", 0, "findById", null, null);
			return null;
		}
	}

	@Override
	public List<Articulo> getAll() {
		try {
			List<Map<String, Object>> results = db.getList(SQL_SELECT_ALL);
			List<Articulo> articulos = new ArrayList<>();
			for (Map<String, Object> row : results) {
				Articulo articulo = new Articulo(row);
				cargarCategorias(articulo);
				articulos.add(articulo);
			}
			return articulos;
		} catch (Exception e) {
			db.log("re_articulos", 0, "findAll", null, null);
			return java.util.Collections.emptyList();
		}
	}

	@Override
	public List<Articulo> getWhere(String where) {
		if (where == null || where.trim().isEmpty()) {
			return getAll();
		}

		Map<String, Object> clause = DBUtils.buildWhereClause(SQL_FIND_WHERE, where);

		try {
			List<Map<String, Object>> results = db.getList(clause.get("sql").toString(), clause.get("params"));
			List<Articulo> articulos = new ArrayList<>();
			for (Map<String, Object> row : results) {
				Articulo articulo = new Articulo(row);
				cargarCategorias(articulo);
				articulos.add(articulo);
			}
			return articulos;
		} catch (Exception e) {
			db.log("re_articulos", 0, "getWhere", null, null);
			return java.util.Collections.emptyList();
		}
	}

	/**
	 * Método helper para cargar las categorías de un artículo
	 */
	private void cargarCategorias(Articulo articulo) {
		if (articulo.getId() != null) {
			try {
				String sql = """
						SELECT c.* FROM re_categorias c
						INNER JOIN re_categorias_articulos ca ON c.id = ca.idcategoria
						WHERE ca.idarticulo = ? AND ca.activo = 1
						ORDER BY c.nombre
						""";
				List<Map<String, Object>> results = db.getList(sql, articulo.getId());
				List<Categoria> categorias = new ArrayList<>();
				for (Map<String, Object> row : results) {
					categorias.add(new Categoria(row));
				}
				articulo.setCategorias(categorias);
			} catch (Exception e) {
				System.err.println(
						"Error cargando categorías para artículo ID " + articulo.getId() + ": " + e.getMessage());
			}
		}
	}

	/**
	 * Buscar artículos con filtros específicos
	 */
	public List<Articulo> buscarArticulos(Integer idcategoria, Boolean activo, String nombre, String descripcion) {
		StringBuilder sql = new StringBuilder();
		List<Object> parametros = new ArrayList<>();

		if (idcategoria != null) {
			// Si se filtra por categoría, usar JOIN con la tabla intermedia
			sql.append("""
					SELECT DISTINCT a.* FROM re_articulos a
					INNER JOIN re_categorias_articulos ca ON a.id = ca.idarticulo
					WHERE ca.idcategoria = ? AND ca.activo = 1
					""");
			parametros.add(idcategoria);
		} else {
			// Si no se filtra por categoría, consultar solo artículos
			sql.append("SELECT a.* FROM re_articulos a WHERE 1=1 ");
		}

		if (activo != null) {
			sql.append(" AND a.activo = ?");
			parametros.add(activo ? 1 : 0);
		}
		if (nombre != null && !nombre.trim().isEmpty()) {
			sql.append(" AND a.nombre LIKE ?");
			parametros.add("%" + nombre + "%");
		}
		if (descripcion != null && !descripcion.trim().isEmpty()) {
			sql.append(" AND a.descripcion LIKE ?");
			parametros.add("%" + descripcion + "%");
		}
		sql.append(" ORDER BY a.id DESC");

		try {
			List<Map<String, Object>> results = db.getList(sql.toString(), parametros.toArray());
			List<Articulo> articulos = new ArrayList<>();
			for (Map<String, Object> row : results) {
				Articulo articulo = new Articulo(row);
				cargarCategorias(articulo);
				articulos.add(articulo);
			}
			return articulos;
		} catch (Exception e) {
			db.log("re_articulos", 0, "buscarArticulos", null, null);
			return java.util.Collections.emptyList();
		}
	}

	/**
	 * Crear artículo con sus categorías
	 */
	public int insertarArticuloConCategorias(Articulo articulo, List<Integer> idsCategoria) {
		try {
			// Insertar el artículo usando el método insert estándar
			boolean insertado = insert(articulo);

			if (insertado && articulo.getId() != null && articulo.getId() > 0) {
				if (idsCategoria != null && !idsCategoria.isEmpty()) {
					// Asignar categorías
					categoriaArticuloDao.asignarCategoriasAArticulo(articulo.getId(), idsCategoria);
				}
				return articulo.getId();
			}

			return 0;
		} catch (Exception e) {
			db.log("re_articulos", 0, "insertarArticuloConCategorias", null, null);
			return 0;
		}
	}

	/**
	 * Actualizar artículo y sus categorías
	 */
	public boolean actualizarArticuloConCategorias(Articulo articulo, List<Integer> idsCategoria) {
		try {
			// Actualizar el artículo
			boolean actualizado = update(articulo);

			if (actualizado && idsCategoria != null) {
				// Actualizar categorías
				categoriaArticuloDao.asignarCategoriasAArticulo(articulo.getId(), idsCategoria);
			}

			return actualizado;
		} catch (Exception e) {
			db.log("re_articulos", 0, "actualizarArticuloConCategorias", null, null);
			return false;
		}
	}
}
