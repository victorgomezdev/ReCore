package CatsPrograming.ReCore.dao.Articulos;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import CatsPrograming.ReCore.dao.DBUtils;
import CatsPrograming.ReCore.dao.IDao;
import CatsPrograming.ReCore.models.Articulos.CategoriaArticulo;

@Repository
public class CategoriaArticuloDao implements IDao<CategoriaArticulo> {

	@Autowired
	DBUtils db;

	private static final String SQL_INSERT = "INSERT INTO re_categorias_articulos (idcategoria, idarticulo, activo) VALUES (?, ?, ?)";

	private static final String SQL_UPDATE = "UPDATE re_categorias_articulos SET activo = ? WHERE id = ?";

	private static final String SQL_DELETE = "DELETE FROM re_categorias_articulos WHERE id = ?";

	private static final String SQL_SELECT_BY_ID = "SELECT * FROM re_categorias_articulos WHERE id = ?";

	private static final String SQL_SELECT_ALL = "SELECT * FROM re_categorias_articulos";

	private static final String SQL_FIND_WHERE = "SELECT * FROM re_categorias_articulos WHERE ";

	@Override
	public boolean insert(CategoriaArticulo categoriaArticulo) {
		try {
			// Validar que los campos requeridos no sean null
			if (categoriaArticulo.getIdcategoria() == null) {
				System.err.println("Error: idcategoria es null");
				return false;
			}
			if (categoriaArticulo.getIdarticulo() == null) {
				System.err.println("Error: idarticulo es null");
				return false;
			}
			if (categoriaArticulo.getActivo() == null) {
				System.err.println("Error: activo es null");
				return false;
			}

			System.out.println("Insertando relación categoria-articulo: " + categoriaArticulo.getIdcategoria() + " - "
					+ categoriaArticulo.getIdarticulo());
			System.out.println("SQL: " + SQL_INSERT);

			// Intentar inserción básica primero
			int rowsAffected = db.execQuery(SQL_INSERT, categoriaArticulo.getIdcategoria(),
					categoriaArticulo.getIdarticulo(),
					categoriaArticulo.getActivo());

			if (rowsAffected > 0) {
				// Obtener el último ID insertado
				String getLastIdSql = "SELECT MAX(id) FROM re_categorias_articulos WHERE idcategoria = ? AND idarticulo = ?";
				int id = db.getEntero(getLastIdSql, categoriaArticulo.getIdcategoria(),
						categoriaArticulo.getIdarticulo());

				System.out.println("ID de relación obtenido: " + id);

				categoriaArticulo.setId(id);
				return true;
			}

			return false;
		} catch (Exception e) {
			System.err.println("Error en insert CategoriaArticulo: " + e.getMessage());
			e.printStackTrace();
			db.log("re_categorias_articulos", 0, "InsertCategoriaArticulo", null, null);
			return false;
		}
	}

	@Override
	public boolean update(CategoriaArticulo categoriaArticulo) {
		try {
			db.execQuery(SQL_UPDATE, categoriaArticulo.getActivo(), categoriaArticulo.getId());
			return true;
		} catch (Exception e) {
			db.log("re_categorias_articulos", 0, "UpdateCategoriaArticulo", null, null);
			return false;
		}
	}

	@Override
	public boolean delete(int id) {
		try {
			db.execQuery(SQL_DELETE, id);
			return true;
		} catch (Exception e) {
			db.log("re_categorias_articulos", 0, "DeleteCategoriaArticulo", null, null);
			return false;
		}
	}

	@Override
	public CategoriaArticulo getById(int id) {
		try {
			Map<String, Object> result = db.getResult(SQL_SELECT_BY_ID, id);
			if (result != null && !result.isEmpty()) {
				return new CategoriaArticulo(result);
			}
			return null;
		} catch (Exception e) {
			db.log("re_categorias_articulos", 0, "GetCategoriaArticuloById", null, null);
			return null;
		}
	}

	@Override
	public List<CategoriaArticulo> getAll() {
		try {
			List<Map<String, Object>> results = db.getList(SQL_SELECT_ALL);
			List<CategoriaArticulo> categoriasArticulos = new ArrayList<>();
			for (Map<String, Object> row : results) {
				categoriasArticulos.add(new CategoriaArticulo(row));
			}
			return categoriasArticulos;
		} catch (Exception e) {
			db.log("re_categorias_articulos", 0, "findAll", null, null);
			return java.util.Collections.emptyList();
		}
	}

	@Override
	public List<CategoriaArticulo> getWhere(String where) {
		if (where == null || where.trim().isEmpty()) {
			return getAll();
		}

		Map<String, Object> clause = DBUtils.buildWhereClause(SQL_FIND_WHERE, where, "nombre ASC");

		try {
			List<Map<String, Object>> results = db.getList(clause.get("sql").toString(), clause.get("params"));
			List<CategoriaArticulo> categoriasArticulos = new ArrayList<>();
			for (Map<String, Object> row : results) {
				categoriasArticulos.add(new CategoriaArticulo(row));
			}
			return categoriasArticulos;
		} catch (Exception e) {
			db.log("re_categorias_articulos", 0, "getWhere", null, null);
			return java.util.Collections.emptyList();
		}
	}

	/**
	 * Obtener categorías de un artículo específico
	 */
	public List<CategoriaArticulo> getCategoriasPorArticulo(int idArticulo) {
		return getWhere("idarticulo=" + idArticulo + " AND activo=1");
	}

	/**
	 * Obtener artículos de una categoría específica
	 */
	public List<CategoriaArticulo> getArticulosPorCategoria(int idCategoria) {
		return getWhere("idcategoria=" + idCategoria + " AND activo=1");
	}

	/**
	 * Verificar si existe la relación entre categoría y artículo
	 */
	public boolean existeRelacion(int idCategoria, int idArticulo) {
		String sql = "SELECT COUNT(*) FROM re_categorias_articulos WHERE idcategoria = ? AND idarticulo = ? AND activo = 1";
		int count = db.getEntero(sql, idCategoria, idArticulo);
		return count > 0;
	}

	/**
	 * Eliminar todas las categorías de un artículo
	 */
	public boolean eliminarCategoriasPorArticulo(int idArticulo) {
		try {
			String sql = "DELETE FROM re_categorias_articulos WHERE idarticulo = ?";
			db.execQuery(sql, idArticulo);
			return true;
		} catch (Exception e) {
			db.log("re_categorias_articulos", 0, "eliminarCategoriasPorArticulo", null, null);
			return false;
		}
	}

	/**
	 * Asignar categorías a un artículo (elimina las existentes y agrega las nuevas)
	 */
	public boolean asignarCategoriasAArticulo(int idArticulo, List<Integer> idsCategoria) {
		try {
			// Eliminar categorías existentes
			eliminarCategoriasPorArticulo(idArticulo);

			// Agregar nuevas categorías
			for (Integer idCategoria : idsCategoria) {
				CategoriaArticulo relacion = new CategoriaArticulo(idCategoria, idArticulo, 1);
				if (!insert(relacion)) {
					return false;
				}
			}
			return true;
		} catch (Exception e) {
			db.log("re_categorias_articulos", 0, "asignarCategoriasAArticulo", null, null);
			return false;
		}
	}
}
