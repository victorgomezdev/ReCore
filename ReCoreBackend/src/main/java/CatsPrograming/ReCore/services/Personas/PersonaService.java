package CatsPrograming.ReCore.services.Personas;

import java.util.List;

import CatsPrograming.ReCore.dao.Personas.PersonaDao;
import CatsPrograming.ReCore.models.Personas.Persona;

public class PersonaService {
	private PersonaDao personaDao;

	public PersonaService(PersonaDao personaDao) {
		this.personaDao = personaDao;
	}

	public Void insertPersona(Persona persona) {
		personaDao.insert(persona);
		return null;
	}

	public boolean updatePersona(Persona persona) {
		return personaDao.update(persona);
	}

	public boolean deletePersona(int id) {
		return personaDao.delete(id);
	}

	public Persona getPersonaById(int id) {
		return personaDao.getById(id);
	}

	public List<Persona> getAllPersonas() {
		return personaDao.getAll();
	}

	public List<Persona> getPersonasWhere(String where) {
		return personaDao.getWhere(where);
	}

	public Persona getPersonaByDni(String dni) {
		return personaDao.getPersonaByDni(dni);
	}

}