package CatsPrograming.ReCore.services.Core;

import java.util.List;

import CatsPrograming.ReCore.dao.Core.QueryFieldsDao;
import CatsPrograming.ReCore.models.Core.QueryFields;

public class QueryFieldsService {
	private QueryFieldsDao queryFieldDao;

	public QueryFieldsService(QueryFieldsDao queryFieldDao) {
		this.queryFieldDao = queryFieldDao;
	}

	public void insertQueryField(QueryFields queryField) {
		queryFieldDao.insert(queryField);
	}

	public void updateQueryField(QueryFields queryField) {
		queryFieldDao.update(queryField);
	}

	public void deleteQueryField(int id) {
		queryFieldDao.delete(id);
	}

	public QueryFields getQueryFieldById(int id) {
		return queryFieldDao.getById(id);
	}

	public List<QueryFields> getAllQueryFields() {
		return queryFieldDao.getAll();
	}

	public List<QueryFields> getQueryFieldsWhere(String where) {
		return queryFieldDao.getWhere(where);
	}
}
