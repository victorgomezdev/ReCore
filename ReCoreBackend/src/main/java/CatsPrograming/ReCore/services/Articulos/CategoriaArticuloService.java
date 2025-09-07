package CatsPrograming.ReCore.services.Articulos;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import CatsPrograming.ReCore.dao.Articulos.CategoriaArticuloDao;
import CatsPrograming.ReCore.models.Articulos.CategoriaArticulo;

@Service
public class CategoriaArticuloService {

	@Autowired
	private CategoriaArticuloDao categoriaArticuloDao;

	public void insertCategoriaArticulo(CategoriaArticulo categoriaArticulo) {
		categoriaArticuloDao.insert(categoriaArticulo);
	}

	public boolean updateCategoriaArticulo(CategoriaArticulo categoriaArticulo) {
		return categoriaArticuloDao.update(categoriaArticulo);
	}

	public boolean deleteCategoriaArticulo(int id) {
		return categoriaArticuloDao.delete(id);
	}

	public CategoriaArticulo getCategoriaArticuloById(int id) {
		return categoriaArticuloDao.getById(id);
	}

	public List<CategoriaArticulo> getAllCategoriasArticulos() {
		return categoriaArticuloDao.getAll();
	}

	public List<CategoriaArticulo> getCategoriasArticulosWhere(String where) {
		return categoriaArticuloDao.getWhere(where);
	}

	public List<CategoriaArticulo> getCategoriasPorArticulo(int idArticulo) {
		return categoriaArticuloDao.getCategoriasPorArticulo(idArticulo);
	}

	public List<CategoriaArticulo> getArticulosPorCategoria(int idCategoria) {
		return categoriaArticuloDao.getArticulosPorCategoria(idCategoria);
	}

	public boolean existeRelacion(int idCategoria, int idArticulo) {
		return categoriaArticuloDao.existeRelacion(idCategoria, idArticulo);
	}

	public boolean eliminarCategoriasPorArticulo(int idArticulo) {
		return categoriaArticuloDao.eliminarCategoriasPorArticulo(idArticulo);
	}

	public boolean asignarCategoriasAArticulo(int idArticulo, List<Integer> idsCategoria) {
		return categoriaArticuloDao.asignarCategoriasAArticulo(idArticulo, idsCategoria);
	}
}
