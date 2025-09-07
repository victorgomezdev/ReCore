package CatsPrograming.ReCore.dao.Core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import CatsPrograming.ReCore.dao.DBUtils;
import CatsPrograming.ReCore.dao.IDao;
import CatsPrograming.ReCore.models.Core.Menu;

/**
 * CREATE TABLE re_menus (
 * id INT AUTO_INCREMENT PRIMARY KEY,
 * nombre VARCHAR(60) NOT NULL,
 * activo TINYINT(3) NOT NULL,
 * orden INT NOT NULL,
 * icon VARCHAR(60) NOT NULL,
 * color VARCHAR(60) NOT NULL
 * )
 */
@Component
public class MenuDao implements IDao<Menu> {
	@Autowired
	DBUtils db;

	private static final String SQL_INSERT = "INSERT INTO re_menus (nombre, activo, orden, icon, color) VALUES (?, ?, ?, ?, ?)";

	private static final String SQL_UPDATE = "UPDATE re_menus SET nombre = ?, activo = ?, orden = ?, icon = ?, color = ? WHERE id = ?";

	private static final String SQL_SELECT_BY_ID = "SELECT * FROM re_menus WHERE id = ?";

	private static final String SQL_DELETE = "DELETE FROM re_menus WHERE id = ?";

	private static final String SQL_SELECT_ALL = "SELECT * FROM re_menus";

	private static final String SQL_SELECT_WHERE = "SELECT * FROM re_menus WHERE ";

	@Override
	public boolean insert(Menu menu) {
		try {
			db.execQuery(SQL_INSERT, menu.getNombre(), menu.getActivo(), menu.getOrden(), menu.getIcon(),
					menu.getColor());
			return true;
		} catch (Exception e) {
			db.log("re_menus", 0, "InsertMenu", null, null);
			return false;
		}
	}

	@Override
	public boolean update(Menu menu) {
		try {
			db.execQuery(SQL_UPDATE, menu.getNombre(), menu.getActivo(), menu.getOrden(), menu.getIcon(),
					menu.getColor(), menu.getId());
			return true;
		} catch (Exception e) {
			db.log("re_menus", 0, "UpdateMenu", null, null);
			return false;
		}
	}

	@Override
	public boolean delete(int id) {
		try {
			db.execQuery(SQL_DELETE, id);
			return true;
		} catch (Exception e) {
			db.log("re_menus", 0, "DeleteMenu", null, null);
			return false;
		}
	}

	@Override
	public Menu getById(int id) {
		try {
			Map<String, Object> result = db.getResult(SQL_SELECT_BY_ID, id);
			if (result != null) {
				return new Menu(result);
			}
			return null;
		} catch (Exception e) {
			db.log("re_menus", 0, "getById", null, null);
			return null;
		}
	}

	@Override
	public List<Menu> getAll() {
		try {
			List<Map<String, Object>> results = db.getList(SQL_SELECT_ALL);
			List<Menu> menus = new java.util.ArrayList<>();
			for (Map<String, Object> row : results) {
				menus.add(new Menu(row));
			}
			return menus;
		} catch (Exception e) {
			db.log("re_menus", 0, "getAll", null, null);
			return Collections.emptyList();
		}
	}

	@Override
	public List<Menu> getWhere(String where) {
		if (where == null || where.trim().isEmpty()) {
			return getAll();
		}

		Map<String, Object> clause = DBUtils.buildWhereClause(SQL_SELECT_WHERE, where);

		try {
			List<Map<String, Object>> results = db.getList(clause.get("sql").toString(), clause.get("params"));
			List<Menu> menus = new ArrayList<>();
			for (Map<String, Object> row : results) {
				menus.add(new Menu(row));
			}
			return menus;
		} catch (Exception e) {
			db.log("re_menus", 0, "getWhere", null, null);
			return Collections.emptyList();
		}
	}

	/**
	 * Obtiene todos los menús activos con sus queries/tablas asociadas
	 */
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
			db.log("re_menus", 0, "obtenerMenu", null, null);
			System.err.println("Error en obtenerMenu: " + e.getMessage());
		}
		return resultado;
	}

}
