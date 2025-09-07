package CatsPrograming.ReCore.services.Articulos;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import CatsPrograming.ReCore.dao.Articulos.ArticuloDao;
import CatsPrograming.ReCore.models.Articulos.Articulo;

@Service
public class ArticulosService {

	@Autowired
	private ArticuloDao articuloDao;

	@Autowired
	private CategoriaArticuloService categoriaArticuloService;

	public void insertArticulo(Articulo articulo) {
		articuloDao.insert(articulo);
	}

	public boolean updateArticulo(Articulo articulo) {
		return articuloDao.update(articulo);
	}

	public boolean deleteArticulo(int id) {
		return articuloDao.delete(id);
	}

	public Articulo getArticuloById(int id) {
		return articuloDao.getById(id);
	}

	public List<Articulo> getAllArticulos() {
		return articuloDao.getAll();
	}

	public List<Articulo> getArticulosWhere(String where) {
		return articuloDao.getWhere(where);
	}

	public List<Articulo> buscarArticulos(Integer idcategoria, Boolean activo, String nombre, String descripcion) {
		return articuloDao.buscarArticulos(idcategoria, activo, nombre, descripcion);
	}

	public int crearArticuloConCategorias(Articulo articulo, List<Integer> idsCategoria) {
		return articuloDao.insertarArticuloConCategorias(articulo, idsCategoria);
	}

	public boolean actualizarArticuloConCategorias(Articulo articulo, List<Integer> idsCategoria) {
		return articuloDao.actualizarArticuloConCategorias(articulo, idsCategoria);
	}

	public boolean asignarCategoriasAArticulo(int idArticulo, List<Integer> idsCategoria) {
		return categoriaArticuloService.asignarCategoriasAArticulo(idArticulo, idsCategoria);
	}

	public boolean eliminarCategoriasPorArticulo(int idArticulo) {
		return categoriaArticuloService.eliminarCategoriasPorArticulo(idArticulo);
	}
}
