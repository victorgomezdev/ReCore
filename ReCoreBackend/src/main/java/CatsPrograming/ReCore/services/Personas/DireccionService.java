package CatsPrograming.ReCore.services.Personas;

import java.util.List;

import CatsPrograming.ReCore.dao.Personas.DireccionDao;
import CatsPrograming.ReCore.models.Personas.Direccion;

public class DireccionService {
	private DireccionDao direccionDao;

	public DireccionService(DireccionDao direccionDao) {
		this.direccionDao = direccionDao;
	}

	public void insertDireccion(Direccion direccion) {
		direccionDao.insert(direccion);
	}

	public boolean updateDireccion(Direccion direccion) {
		return direccionDao.update(direccion);
	}

	public boolean deleteDireccion(int id) {
		return direccionDao.delete(id);
	}

	public Direccion getDireccionById(int id) {
		return direccionDao.getById(id);
	}

	public List<Direccion> getAllDirecciones() {
		return direccionDao.getAll();
	}

	public List<Direccion> getDireccionesWhere(String where) {
		return direccionDao.getWhere(where);
	}
}
