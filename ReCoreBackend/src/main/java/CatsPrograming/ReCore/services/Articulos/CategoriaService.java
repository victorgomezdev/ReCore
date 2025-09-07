package CatsPrograming.ReCore.services.Articulos;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import CatsPrograming.ReCore.dao.Articulos.CategoriaDao;
import CatsPrograming.ReCore.models.Articulos.Categoria;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaDao categoriaDao;

    public void insertCategoria(Categoria categoria) {
        categoriaDao.insert(categoria);
    }

    public boolean updateCategoria(Categoria categoria) {
        return categoriaDao.update(categoria);
    }

    public boolean deleteCategoria(int id) {
        return categoriaDao.delete(id);
    }

    public Categoria getCategoriaById(int id) {
        return categoriaDao.getById(id);
    }

    public List<Categoria> getAllCategorias() {
        return categoriaDao.getAll();
    }

    public List<Categoria> getCategoriasWhere(String where) {
        return categoriaDao.getWhere(where);
    }

    public List<Categoria> getCategoriasActivas() {
        return categoriaDao.getCategoriasActivas();
    }
}
