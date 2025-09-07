package CatsPrograming.ReCore.dao.Articulos;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import CatsPrograming.ReCore.dao.DBUtils;
import CatsPrograming.ReCore.dao.IDao;
import CatsPrograming.ReCore.models.Articulos.Categoria;

@Repository
public class CategoriaDao implements IDao<Categoria> {

    @Autowired
    DBUtils db;

    private static final String SQL_INSERT = "INSERT INTO re_categorias (nombre, descripcion, activo) VALUES (?, ?, ?)";

    private static final String SQL_UPDATE = "UPDATE re_categorias SET nombre = ?, descripcion = ?, activo = ? WHERE id = ?";

    private static final String SQL_DELETE = "DELETE FROM re_categorias WHERE id = ?";

    private static final String SQL_SELECT_BY_ID = "SELECT * FROM re_categorias WHERE id = ?";

    private static final String SQL_SELECT_ALL = "SELECT * FROM re_categorias";

    private static final String SQL_FIND_WHERE = "SELECT * FROM re_categorias WHERE ";

    @Override
    public boolean insert(Categoria categoria) {
        try {
            db.execQuery(SQL_INSERT, categoria.getNombre(), categoria.getDescripcion(), categoria.getActivo());
            return true;
        } catch (Exception e) {
            db.log("re_categorias", 0, "InsertCategoria", null, null);
            return false;
        }
    }

    @Override
    public boolean update(Categoria categoria) {
        try {
            db.execQuery(SQL_UPDATE, categoria.getNombre(), categoria.getDescripcion(),
                    categoria.getActivo(), categoria.getId());
            return true;
        } catch (Exception e) {
            db.log("re_categorias", 0, "UpdateCategoria", null, null);
            return false;
        }
    }

    @Override
    public boolean delete(int id) {
        try {
            db.execQuery(SQL_DELETE, id);
            return true;
        } catch (Exception e) {
            db.log("re_categorias", 0, "DeleteCategoria", null, null);
            return false;
        }
    }

    @Override
    public Categoria getById(int id) {
        try {
            Map<String, Object> result = db.getResult(SQL_SELECT_BY_ID, id);
            if (result != null) {
                return new Categoria(result);
            }
            return null;
        } catch (Exception e) {
            db.log("re_categorias", 0, "findById", null, null);
            return null;
        }
    }

    @Override
    public List<Categoria> getAll() {
        try {
            List<Map<String, Object>> results = db.getList(SQL_SELECT_ALL);
            List<Categoria> categorias = new ArrayList<>();
            for (Map<String, Object> row : results) {
                categorias.add(new Categoria(row));
            }
            return categorias;
        } catch (Exception e) {
            db.log("re_categorias", 0, "findAll", null, null);
            return java.util.Collections.emptyList();
        }
    }

    @Override
    public List<Categoria> getWhere(String where) {
        if (where == null || where.trim().isEmpty()) {
            return getAll();
        }

        Map<String, Object> clause = DBUtils.buildWhereClause(SQL_FIND_WHERE, where);

        try {
            List<Map<String, Object>> results = db.getList(clause.get("sql").toString(), clause.get("params"));
            List<Categoria> categorias = new ArrayList<>();
            for (Map<String, Object> row : results) {
                categorias.add(new Categoria(row));
            }
            return categorias;
        } catch (Exception e) {
            db.log("re_categorias", 0, "getWhere", null, null);
            return java.util.Collections.emptyList();
        }
    }

    /**
     * Obtener categorías activas
     */
    public List<Categoria> getCategoriasActivas() {
        return getWhere("activo=1");
    }
}
