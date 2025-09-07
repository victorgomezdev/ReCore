package CatsPrograming.ReCore.dao.Core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import CatsPrograming.ReCore.dao.DBUtils;
import CatsPrograming.ReCore.dao.IDao;
import CatsPrograming.ReCore.models.Core.Query;
import CatsPrograming.ReCore.models.Core.QueryFields;

@Component
public class QueryFieldsDao implements IDao<QueryFields> {

	@Autowired
	DBUtils db;

	private static final String SQL_INSERT = "INSERT INTO re_queries_fields (idquery, field, show_name, is_required, password_field, color_field, rich_text, is_editable, visible, ocultar_vacio, grupo, field_help, ancho)"
			+
			"VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

	private static final String SQL_UPDATE = "UPDATE re_queries_fields " +
			"SET idquery = ?, field = ?, show_name = ?, is_required = ?, password_field = ?, color_field = ?, rich_text = ?, is_editable = ?, visible = ?, ocultar_vacio = ?, grupo = ?, field_help = ?, ancho = ? WHERE id = ?";

	private static final String SQL_SELECT_BY_ID = "SELECT * FROM re_queries_fields WHERE id = ?";

	private static final String SQL_DELETE = "DELETE FROM re_queries_fields WHERE id = ?";

	private static final String SQL_SELECT_ALL = "SELECT * FROM re_queries_fields";

	private static final String SQL_SELECT_WHERE = "SELECT * FROM re_queries_fields WHERE ";

	@Override
	public boolean insert(QueryFields type) {
		try {
			db.execQuery(SQL_INSERT, type.getId(), type.getField(), type.getShowName(), type.getRequired(),
					type.getPasswordField(), type.getColorField(), type.getRichText(), type.getIsEditable(),
					type.getVisible(), type.getOcultarVacio(), type.getGrupo(), type.getFieldHelp(), type.getAncho());
			return true;
		} catch (Exception e) {
			db.log("re_query_fields", 0, "insertQueryFields", null, null);
		}
		return false;
	}

	@Override
	public boolean update(QueryFields type) {
		try {
			db.execQuery(SQL_UPDATE, type.getId(), type.getField(), type.getShowName(), type.getRequired(),
					type.getPasswordField(), type.getColorField(), type.getRichText(), type.getIsEditable(),
					type.getVisible(), type.getOcultarVacio(), type.getGrupo(), type.getFieldHelp(), type.getAncho());
			return true;
		} catch (Exception e) {
			db.log("re_query_fields", 0, "updateQueryFields", null, null);
			return false;
		}
	}

	@Override
	public boolean delete(int id) {
		try {
			db.execQuery(SQL_DELETE, id);
			return true;
		} catch (Exception e) {
			db.log("re_query_fields", 0, "deleteQueryFields", null, null);
			return false;
		}
	}

	@Override
	public QueryFields getById(int id) {
		QueryDao queryDao = new QueryDao();
		try {
			Map<String, Object> result = db.getResult(SQL_SELECT_BY_ID, id);
			if (result != null) {
				return new QueryFields(result, queryDao.getById((Integer) result.get("idquery")));
			}
			return null;
		} catch (Exception e) {
			db.log("re_query_fields", 0, "getById", null, null);
			return null;
		}
	}

	@Override
	public List<QueryFields> getAll() {
		QueryDao queryDao = new QueryDao();
		try {
			List<Map<String, Object>> results = db.getList(SQL_SELECT_ALL);
			List<QueryFields> queryFieldsList = new ArrayList<>();
			for (Map<String, Object> result : results) {
				Query query = queryDao.getById((Integer) result.get("idquery"));
				queryFieldsList.add(new QueryFields(result, query));
			}
			return queryFieldsList;
		} catch (Exception e) {
			db.log("re_query_fields", 0, "getAll", null, null);
			return Collections.emptyList();
		}
	}

	@Override
	public List<QueryFields> getWhere(String where) {
		QueryDao queryDao = new QueryDao();
		Map<String, Object> clause = DBUtils.buildWhereClause(SQL_SELECT_WHERE, where);
		List<QueryFields> queryFieldsList = new ArrayList<>();
		try {
			List<Map<String, Object>> results = db.getList(clause.get("sql").toString(), clause.get("params"));
			for (Map<String, Object> result : results) {
				Query query = queryDao.getById((Integer) result.get("idquery"));
				queryFieldsList.add(new QueryFields(result, query));
			}
			return queryFieldsList;
		} catch (Exception e) {
			db.log("re_query_fields", 0, "getWhere", null, null);
			return Collections.emptyList();
		}
	}
}
