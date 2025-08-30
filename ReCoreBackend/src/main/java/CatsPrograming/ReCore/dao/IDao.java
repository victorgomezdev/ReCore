package CatsPrograming.ReCore.dao;

import java.util.List;

public interface IDao<T> {
	void insert(T type);

	boolean update(T type);

	boolean delete(int id);

	T getById(int id);

	List<T> getAll();

	List<T> getWhere(String where);
}
