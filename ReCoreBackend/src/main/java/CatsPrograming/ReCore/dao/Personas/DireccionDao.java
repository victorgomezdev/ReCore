package CatsPrograming.ReCore.dao.Personas;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import CatsPrograming.ReCore.dao.DBUtils;
import CatsPrograming.ReCore.dao.IDao;
import CatsPrograming.ReCore.models.Personas.Direccion;

@Component
public class DireccionDao implements IDao<Direccion> {

	@Autowired
	DBUtils db;

	private static final String SQL_INSERT = "INSERT INTO re_direcciones" +
			"(idpersona, calle, numero, ciudad, provincia, codigo_postal, geo_referencia) " +
			"VALUES (?, ?, ?, ?, ?, ?, ?)";

	private static final String SQL_UPDATE = "UPDATE re_direcciones " +
			"SET idpersona = ?, calle = ?, numero = ?, ciudad = ?, provincia = ?, codigo_postal = ?, geo_referencia = ? WHERE id = ?";

	private static final String SQL_SELECT_BY_ID = "SELECT * FROM re_direcciones WHERE id = ?";

	private static final String SQL_DELETE = "DELETE FROM re_direcciones WHERE id = ?";

	private static final String SQL_SELECT_ALL = "SELECT * FROM re_direcciones";

	private static final String SQL_SELECT_WHERE = "SELECT * FROM re_direcciones WHERE ";

	@Override
	public boolean insert(Direccion direccion) {
		try {
			db.execQuery(SQL_INSERT, direccion.getIdpersona(), direccion.getCalle(), direccion.getNumero(),
					direccion.getCiudad(), direccion.getProvincia(), direccion.getCodigoPostal(),
					direccion.getGeoReferencia());
			return true;
		} catch (Exception e) {
			db.log("re_direcciones", 0, "InsertDireccion", null, null);
		}
		return false;
	}

	@Override
	public boolean update(Direccion direccion) {
		try {
			db.execQuery(SQL_UPDATE, direccion.getIdpersona(), direccion.getCalle(), direccion.getNumero(),
					direccion.getCiudad(), direccion.getProvincia(), direccion.getCodigoPostal(),
					direccion.getGeoReferencia(), direccion.getId());
			return true;
		} catch (Exception e) {
			// Aqui intenta recuperar el valor "actual" para dejar claro en el log, de que
			// aquí se intentó pasar

			// TODO: el valor actual de la dirección, y crear un sistema de AppLogger para
			// que evoque en un archivo todos los errores
			db.log("re_direcciones", 0, "UpdateDireccion", null, null);
			return false;
		}
	}

	@Override
	public boolean delete(int id) {
		try {
			db.execQuery(SQL_DELETE, id);
			return true;
		} catch (Exception e) {
			db.log("re_direcciones", 0, "DeleteDireccion", null, null);
			return false;
		}
	}

	@Override
	public Direccion getById(int id) {
		try {
			Map<String, Object> result = db.getResult(SQL_SELECT_BY_ID, id);
			if (result != null) {
				return new Direccion(result);
			}
			return null;
		} catch (Exception e) {
			db.log("re_direcciones", 0, "getById", null, null);
			return null;
		}
	}

	@Override
	public List<Direccion> getAll() {
		try {
			List<Map<String, Object>> results = db.getList(SQL_SELECT_ALL);
			List<Direccion> direcciones = new java.util.ArrayList<>();
			for (Map<String, Object> row : results) {
				direcciones.add(new Direccion(row));
			}
			return direcciones;
		} catch (Exception e) {
			db.log("re_direcciones", 0, "getAll", null, null);
			return Collections.emptyList();
		}
	}

	@Override
	public List<Direccion> getWhere(String where) {
		if (where == null || where.trim().isEmpty()) {
			return getAll();
		}

		Map<String, Object> clause = DBUtils.buildWhereClause(SQL_SELECT_WHERE, where);

		try {
			List<Map<String, Object>> results = db.getList(clause.get("sql").toString(), clause.get("params"));
			List<Direccion> direcciones = new ArrayList<>();
			for (Map<String, Object> row : results) {
				direcciones.add(new Direccion(row));
			}
			return direcciones;
		} catch (Exception e) {
			db.log("re_direcciones", 0, "getWhere", null, null);
			return Collections.emptyList();
		}
	}

	public List<Direccion> getAllByPersona(Integer idpersona) {
		if (idpersona == null) {
			return Collections.emptyList();
		}

		String sql = "SELECT * FROM re_direcciones WHERE idpersona = ?";
		try {
			List<Map<String, Object>> results = db.getList(sql, idpersona);
			List<Direccion> direcciones = new ArrayList<>();
			for (Map<String, Object> row : results) {
				direcciones.add(new Direccion(row));
			}
			return direcciones;
		} catch (Exception e) {
			db.log("re_direcciones", 0, "getAllByPersona", null, null);
			return Collections.emptyList();
		}
	}
}
