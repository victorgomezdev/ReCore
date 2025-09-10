package CatsPrograming.ReCore.dao.Personas;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import CatsPrograming.ReCore.dao.DBUtils;
import CatsPrograming.ReCore.dao.IDao;
import CatsPrograming.ReCore.models.Personas.Persona;

/**
 * CREATE TABLE re_personas (
 * id INT AUTO_INCREMENT PRIMARY KEY,
 * nombre VARCHAR(100) NOT NULL,
 * apellido VARCHAR(100) NOT NULL,
 * dni VARCHAR(15) UNIQUE,
 * cuit VARCHAR(15) UNIQUE,
 * email VARCHAR(255),
 * telefono VARCHAR(20),
 * direccion VARCHAR(255),
 * fecha_nacimiento DATE,
 * fecha_registro TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
 * notas VARCHAR(255)
 */
public class PersonaDao implements IDao<Persona> {
	@Autowired
	DBUtils db;

	private static final String SQL_INSERT = "INSERT INTO re_personas " +
			"(nombre, apellido, dni, cuit, email, telefono, fecha_nacimiento, fecha_registro, notas) VALUES " +
			"(?, ?, ?, ?, ?, ?, ?, ?, ?)";

	private static final String SQL_UPDATE = "UPDATE re_personas " +
			"SET nombre = ?, apellido = ?, dni = ?, cuit = ?, email = ?, telefono = ?, fecha_nacimiento = ?, fecha_registro = ?, notas = ? WHERE id = ?";

	private static final String SQL_SELECT_BY_ID = "SELECT * FROM re_personas WHERE id = ?";

	private static final String SQL_DELETE = "DELETE FROM re_personas WHERE id = ?";

	private static final String SQL_FIND_ALL = "SELECT * FROM re_personas";

	private static final String SQL_FIND_WHERE = "SELECT * FROM re_personas WHERE ";

	private static final String SQL_FIND_BY_DNI = "SELECT * FROM re_personas WHERE dni = ?";

	@Override
	public boolean insert(Persona persona) {
		try {
			db.execQuery(SQL_INSERT, persona.getNombre(), persona.getApellido(), persona.getDni(), persona.getCuit(),
					persona.getEmail(), persona.getTelefono(), persona.getFechaNacimiento(), persona.getFechaRegistro(),
					persona.getNotas());
			return true;
		} catch (Exception e) {
			db.log("re_personas", 0, "InsertPersona", null, null);
		}
		return false;
	}

	@Override
	public boolean update(Persona persona) {
		try {
			db.execQuery(SQL_UPDATE, persona.getNombre(), persona.getApellido(), persona.getDni(), persona.getCuit(),
					persona.getEmail(), persona.getTelefono(), persona.getFechaNacimiento(), persona.getFechaRegistro(),
					persona.getNotas(), persona.getId());
			return true;
		} catch (Exception e) {
			db.log("re_personas", 0, "UpdatePersona", null, null);
			return false;
		}
	}

	@Override
	public boolean delete(int id) {
		try {
			db.execQuery(SQL_DELETE, id);
			return true;
		} catch (Exception e) {
			db.log("re_personas", 0, "DeletePersona", null, null);
			return false;
		}
	}

	@Override
	public Persona getById(int id) {
		try {
			Map<String, Object> result = db.getResult(SQL_SELECT_BY_ID, id);
			if (result != null) {
				DireccionDao direccionDao = new DireccionDao();
				return new Persona(result, direccionDao.getAllByPersona(id));
			}
			return null;
		} catch (Exception e) {
			db.log("re_personas", 0, "findById", null, null);
			return null;
		}
	}

	@Override
	public List<Persona> getAll() {
		try {
			DireccionDao direccionDao = new DireccionDao();
			List<Map<String, Object>> results = db.getList(SQL_FIND_ALL);
			List<Persona> personas = new ArrayList<>();
			for (Map<String, Object> row : results) {
				personas.add(new Persona(row, direccionDao.getAllByPersona((Integer) row.get("id"))));
			}
			return personas;
		} catch (Exception e) {
			db.log("re_personas", 0, "findAll", null, null);
			return java.util.Collections.emptyList();
		}
	}

	@Override
	public List<Persona> getWhere(String where) {
		DireccionDao direccionDao = new DireccionDao();
		if (where == null || where.trim().isEmpty()) {
			return getAll();
		}

		Map<String, Object> clause = DBUtils.buildWhereClause(SQL_FIND_WHERE, where, "nombre ASC");

		try {
			List<Map<String, Object>> results = db.getList(clause.get("sql").toString(), clause.get("params"));
			List<Persona> personas = new ArrayList<>();
			for (Map<String, Object> row : results) {
				personas.add(new Persona(row, direccionDao.getAllByPersona((Integer) row.get("id"))));
			}
			return personas;
		} catch (Exception e) {
			db.log("re_personas", 0, "getWhere", null, null);
			return java.util.Collections.emptyList();
		}
	}

	/**
	 * Buscar persona por DNI
	 */
	public Persona getPersonaByDni(String dni) {
		try {
			Map<String, Object> result = db.getResult(SQL_FIND_BY_DNI, dni);
			if (result != null) {
				DireccionDao direccionDao = new DireccionDao();
				return new Persona(result, direccionDao.getAllByPersona((Integer) result.get("id")));
			}
			return null;
		} catch (Exception e) {
			System.err.println("Error buscando persona: " + e.getMessage());
			return null;
		}
	}

}
