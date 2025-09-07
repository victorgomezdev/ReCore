package CatsPrograming.ReCore.services.Core;

import java.util.List;

import CatsPrograming.ReCore.dao.Core.QueryDao;
import CatsPrograming.ReCore.models.Core.Query;

public class QueryService {
	private QueryDao queryDao;

	public QueryService(QueryDao queryDao) {
		this.queryDao = queryDao;
	}

	public void insertQuery(Query query) {
		queryDao.insert(query);
	}

	public void updateQuery(Query query) {
		queryDao.update(query);
	}

	public void deleteQuery(int id) {
		queryDao.delete(id);
	}

	public Query getQueryById(int id) {
		return queryDao.getById(id);
	}

	public List<Query> getAllQueries() {
		return queryDao.getAll();
	}

	public List<Query> getQueriesWhere(String where) {
		return queryDao.getWhere(where);
	}
}
