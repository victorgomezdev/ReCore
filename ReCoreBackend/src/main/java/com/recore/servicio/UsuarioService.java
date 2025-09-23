package com.recore.servicio;

import com.recore.modelo.Usuario;
import com.recore.dao.UsuarioRepository;
import com.recore.util.RespuestaBase;
import com.recore.util.RespuestaPaginada;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;

/**
 * Módulo 4 - Backend avanzado
 * Clase 10 - Inyección de dependencias
 * Servicio para la gestión de usuarios
 */
@Service
@Transactional
public class UsuarioService implements BaseServicio<Usuario> {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Override
	public RespuestaBase<Usuario> guardar(Usuario usuario) {
		try {
			// Verificar si ya existe un usuario con ese email
			Usuario usuarioExistente = usuarioRepository.findByEmail(usuario.getEmail());

			// Si estamos creando un nuevo usuario
			if (usuario.getId() == null) {
				if (usuarioExistente != null) {
					return new RespuestaBase<Usuario>(null, "Ya existe un usuario con el email: " + usuario.getEmail());
				}
			} else {
				// Si estamos actualizando un usuario existente
				if (usuarioExistente != null && !usuarioExistente.getId().equals(usuario.getId())) {
					return new RespuestaBase<Usuario>(null,
							"El email " + usuario.getEmail() + " ya está en uso por otro usuario");
				}
			}

			LocalDateTime now = LocalDateTime.now();
			if (usuario.getId() == null) {
				usuario.setFechaCreacion(now);
			}
			usuario.setFechaModificacion(now);
			return new RespuestaBase<Usuario>(usuarioRepository.save(usuario));
		} catch (Exception e) {
			return new RespuestaBase<Usuario>(null, "Error al guardar el usuario: " + e.getMessage());
		}
	}

	@Override
	public RespuestaBase<Usuario> buscarPorId(Long id) {
		try {
			return new RespuestaBase<Usuario>(usuarioRepository.findById(id).orElse(null));
		} catch (Exception e) {
			return new RespuestaBase<Usuario>(null, "Error al buscar el usuario: " + e.getMessage());
		}
	}

	@Override
	public RespuestaPaginada<Usuario> listarTodos(Pageable paginacion) {
		try {
			Page<Usuario> pagina = usuarioRepository.findAll(paginacion);
			return new RespuestaPaginada<Usuario>(pagina.getContent(), pagina.getTotalElements(),
					pagina.getNumber(), pagina.getSize());
		} catch (Exception e) {
			return new RespuestaPaginada<Usuario>(null, "Error al listar usuarios: " + e.getMessage());
		}
	}

	@Override
	public RespuestaBase<Void> eliminar(Long id) {
		try {
			usuarioRepository.deleteById(id);
			return new RespuestaBase<Void>("Usuario eliminado correctamente", false);
		} catch (Exception e) {
			return new RespuestaBase<Void>("Error al eliminar el usuario: " + e.getMessage(), true);
		}
	}

	@Override
	public RespuestaBase<Boolean> existe(Long id) {
		try {
			return new RespuestaBase<Boolean>(usuarioRepository.existsById(id));
		} catch (Exception e) {
			return new RespuestaBase<Boolean>(false, "Error al verificar existencia: " + e.getMessage());
		}
	}

	public RespuestaBase<Usuario> buscarPorEmail(String email) {
		try {
			return new RespuestaBase<Usuario>(usuarioRepository.findByEmail(email));
		} catch (Exception e) {
			return new RespuestaBase<Usuario>(null, "Error al buscar por email: " + e.getMessage());
		}
	}
}
