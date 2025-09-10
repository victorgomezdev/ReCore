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

@Component
public class QueryDao implements IDao<Query> {
	@Autowired
	DBUtils db;

	private static final String SQL_INSERT = "INSERT INTO re_queries" +
			"(idmenu, table, query_name, query_description, fields, can_insert, can_edit, can_delete, debil, icon, checksum) "
			+
			"VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

	private static final String SQL_UPDATE = "UPDATE re_queries " +
			"SET idmenu = ?, table = ?, query_name = ?, query_description = ?, fields = ?, can_insert = ?, can_edit = ?, can_delete = ?, debil = ?, icon = ?, checksum = ? "
			+
			"WHERE id = ?";

	private static final String SQL_SELECT_BY_ID = "SELECT * FROM re_queries WHERE id = ?";

	private static final String SQL_DELETE = "DELETE FROM re_queries WHERE id = ?";

	private static final String SQL_SELECT_ALL = "SELECT * FROM re_queries";

	private static final String SQL_SELECT_WHERE = "SELECT * FROM re_queries WHERE ";

	@Override
	public boolean insert(Query query) {
		try {
			db.execQuery(SQL_INSERT, query.getIdMenu(), query.getTable(), query.getQueryName(),
					query.getQueryDescription(),
					query.getFields(), query.getCanInsert(), query.getCanEdit(), query.getCanDelete(), query.getDebil(),
					query.getIcon(), query.getChecksum());
			return true;
		} catch (Exception e) {
			db.log("re_queries", 0, "InsertQuery", null, null);
			return false;
		}
	}

	@Override
	public boolean update(Query query) {
		try {
			db.execQuery(SQL_UPDATE, query.getIdMenu(), query.getTable(), query.getQueryName(),
					query.getQueryDescription(), query.getFields(), query.getCanInsert(), query.getCanEdit(),
					query.getCanDelete(), query.getDebil(), query.getIcon(), query.getChecksum(), query.getId());
			return true;
		} catch (Exception e) {
			db.log("re_queries", 0, "UpdateQuery", null, null);
			return false;
		}
	}

	@Override
	public boolean delete(int id) {
		try {
			db.execQuery(SQL_DELETE, id);
			return true;
		} catch (Exception e) {
			db.log("re_queries", 0, "DeleteQuery", null, null);
			return false;
		}
	}

	@Override
	public Query getById(int id) {
		try {
			Map<String, Object> result = db.getResult(SQL_SELECT_BY_ID, id);
			if (result != null) {
				return new Query(result);
			}
			return null;
		} catch (Exception e) {
			db.log("re_queries", 0, "GetByIdQuery", null, null);
			return null;
		}
	}

	@Override
	public List<Query> getAll() {
		try {
			List<Map<String, Object>> results = db.getList(SQL_SELECT_ALL);
			List<Query> queries = new ArrayList<>();
			for (Map<String, Object> result : results) {
				queries.add(new Query(result));
			}
			return queries;
		} catch (Exception e) {
			db.log("re_queries", 0, "GetAllQueries", null, null);
			return Collections.emptyList();
		}
	}

	@Override
	public List<Query> getWhere(String where) {
		if (where == null || where.trim().isEmpty()) {
			return getAll();
		}

		Map<String, Object> clause = DBUtils.buildWhereClause(where, where, "");
		try {
			List<Map<String, Object>> results = db.getList(SQL_SELECT_WHERE + clause);
			List<Query> queries = new ArrayList<>();
			for (Map<String, Object> result : results) {
				queries.add(new Query(result));
			}
			return queries;
		} catch (Exception e) {
			db.log("re_queries", 0, "GetWhereQueries", null, null);
			return Collections.emptyList();
		}
	}

}
