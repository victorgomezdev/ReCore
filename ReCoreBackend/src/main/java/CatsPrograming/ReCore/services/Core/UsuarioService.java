package CatsPrograming.ReCore.services.Core;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import CatsPrograming.ReCore.dao.Core.UsuarioDao;
import CatsPrograming.ReCore.models.Core.Usuario;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioDao usuarioDao;

	public void insertUsuario(Usuario usuario) {
		usuarioDao.insert(usuario);
	}

	public boolean updateUsuario(Usuario usuario) {
		return usuarioDao.update(usuario);
	}

	public boolean deleteUsuario(int id) {
		return usuarioDao.delete(id);
	}

	public Usuario getUsuarioById(int id) {
		return usuarioDao.getById(id);
	}

	public List<Usuario> getAllUsuarios() {
		return usuarioDao.getAll();
	}

	public List<Usuario> getUsuariosWhere(String where) {
		return usuarioDao.getWhere(where);
	}

	public int validarLogin(String email, String password) {
		return usuarioDao.validarLogin(email, password);
	}

	public int crearUsuario(String email, String password, Integer personaId) {
		return usuarioDao.crearUsuario(email, password, personaId);
	}

	public int emailExistente(String email) {
		return usuarioDao.emailExistente(email);
	}
}
