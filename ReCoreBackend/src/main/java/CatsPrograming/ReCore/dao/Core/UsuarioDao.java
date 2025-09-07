package CatsPrograming.ReCore.dao.Core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import CatsPrograming.ReCore.dao.DBUtils;
import CatsPrograming.ReCore.dao.IDao;
import CatsPrograming.ReCore.models.Core.Usuario;

@Component
public class UsuarioDao implements IDao<Usuario> {

	@Autowired
	DBUtils db;

	private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	private static final String SQL_INSERT = "INSERT INTO " +
			"re_usuarios (idpersona, email, password_hash, activo, fecha_ultimo_login, bloqueado_hasta, token_verificacion, token_reset_password, fecha_expira_token) "
			+
			"VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

	private static final String SQL_UPDATE = "UPDATE re_usuarios " +
			"SET idpersona = ?, email = ?, password_hash = ?, activo = ?, fecha_ultimo_login = ?, bloqueado_hasta = ?, token_verificacion = ?, token_reset_password = ?, fecha_expira_token = ? WHERE id = ?";

	private static final String SQL_SELECT_BY_ID = "SELECT * FROM re_usuarios WHERE id = ?";

	private static final String SQL_DELETE = "DELETE FROM re_usuarios WHERE id = ?";

	private static final String SQL_SELECT_ALL = "SELECT * FROM re_usuarios";

	private static final String SQL_SELECT_WHERE = "SELECT * FROM re_usuarios WHERE ";

	@Override
	public boolean insert(Usuario usuario) {
		try {
			db.execQuery(SQL_INSERT, usuario.getIdpersona(), usuario.getEmail(), usuario.getPasswordHash(),
					usuario.isActivo(), usuario.getFechaUltimoLogin(), usuario.getBloqueadoHasta(),
					usuario.getTokenVerificacion(), usuario.getTokenResetPassword(), usuario.getFechaExpiraToken());
			return true;
		} catch (Exception e) {
			db.log("re_usuarios", 0, "InsertUsuario", null, null);
		}
		return false;
	}

	@Override
	public boolean update(Usuario type) {
		try {
			db.execQuery(SQL_UPDATE, type.getIdpersona(), type.getEmail(), type.getPasswordHash(),
					type.isActivo(), type.getFechaUltimoLogin(), type.getBloqueadoHasta(),
					type.getTokenVerificacion(), type.getTokenResetPassword(), type.getFechaExpiraToken(),
					type.getId());
			return true;
		} catch (Exception e) {
			db.log("re_usuarios", type.getId(), "UpdateUsuario", null, null);
			return false;
		}
	}

	@Override
	public boolean delete(int id) {
		try {
			db.execQuery(SQL_DELETE, id);
			return true;
		} catch (Exception e) {
			db.log("re_usuarios", id, "DeleteUsuario", null, null);
			return false;
		}
	}

	@Override
	public Usuario getById(int id) {
		try {
			Map<String, Object> result = db.getResult(SQL_SELECT_BY_ID, id);
			if (result != null) {
				return new Usuario(result);
			}
			return null;
		} catch (Exception e) {
			db.log("re_usuarios", id, "GetUsuarioById", null, null);
			return null;
		}
	}

	@Override
	public List<Usuario> getAll() {
		try {
			List<Map<String, Object>> results = db.getList(SQL_SELECT_ALL);
			List<Usuario> usuarios = new ArrayList<>();
			for (Map<String, Object> result : results) {
				usuarios.add(new Usuario(result));
			}
			return usuarios;
		} catch (Exception e) {
			db.log("re_usuarios", 0, "GetAllUsuarios", null, null);
			return Collections.emptyList();
		}
	}

	@Override
	public List<Usuario> getWhere(String where) {
		if (where == null || where.trim().isEmpty()) {
			return getAll();
		}

		Map<String, Object> clause = DBUtils.buildWhereClause(SQL_SELECT_WHERE, where);

		try {
			List<Map<String, Object>> results = db.getList(clause.get("sql").toString(), clause.get("params"));
			List<Usuario> usuarios = new ArrayList<>();
			for (Map<String, Object> row : results) {
				usuarios.add(new Usuario(row));
			}
			return usuarios;
		} catch (Exception e) {
			db.log("re_usuarios", 0, "GetUsuariosWhere", null, null);
			return Collections.emptyList();
		}
	}

	/**
	 * Valida las credenciales de login
	 */
	public int validarLogin(String email, String password) {
		try {
			String sql = """
					SELECT u.id as idusuario, u.password_hash, u.activo, u.bloqueado_hasta, u.intentos_login,
						   p.id as idpersona, p.nombre, p.apellido
					FROM re_usuarios u
					LEFT JOIN re_personas p ON u.idpersona = p.id
					WHERE u.email = ?
					""";

			List<Map<String, Object>> resultados = db.getList(sql, email);

			if (resultados.isEmpty()) {
				System.out.println("[ReCore] Usuario no encontrado: " + email);
				return 0;
			}

			Map<String, Object> usuario = resultados.get(0);
			String passwordHash = (String) usuario.get("password_hash");
			Boolean activo = (Boolean) usuario.get("activo");

			// Verificar si está activo
			if (activo == null || !activo) {
				System.out.println("[ReCore] Usuario inactivo: " + email);
				return 0;
			}

			// Usar BCrypt para verificar el password
			if (passwordEncoder.matches(password, passwordHash)) {
				Integer idUsuario = (Integer) usuario.get("idusuario");
				System.out.println("[ReCore] Login exitoso para usuario: " + email + ", ID: " + idUsuario);
				return idUsuario;
			} else {
				System.out.println("[ReCore] Password incorrecto para usuario: " + email);
				return 0;
			}

		} catch (Exception e) {
			db.log("re_usuarios", 0, "validarLogin", null, null);
			System.err.println("Error en validarLogin: " + e.getMessage());
			return 0;
		}
	}

	/**
	 * Crear nuevo usuario
	 */
	public int crearUsuario(String email, String password, Integer personaId) {
		try {
			// Verificar que el email no exista
			if (emailExistente(email) > 0) {
				System.out.println("[ReCore] Email ya existe: " + email);
				return 0;
			}

			// Usar BCrypt para encriptar la contraseña
			String passwordHash = passwordEncoder.encode(password);

			// Crear usuario usando el método insert existente
			Usuario nuevoUsuario = new Usuario(personaId, email, passwordHash, true,
					java.time.LocalDate.now(), null, 0, null, null, null, null);
			insert(nuevoUsuario);

			// Como insert() no retorna ID, necesitamos obtenerlo
			String sql = "SELECT id FROM re_usuarios WHERE email = ? ORDER BY id DESC LIMIT 1";
			int usuarioId = db.getEntero(sql, email);

			System.out.println("[ReCore] Usuario creado: " + email + " | ID: " + usuarioId);
			return usuarioId;

		} catch (Exception e) {
			db.log("re_usuarios", 0, "crearUsuario", null, null);
			System.err.println("Error creando usuario: " + e.getMessage());
			e.printStackTrace();
			return 0;
		}
	}

	/**
	 * Verificar si email ya existe
	 */
	public int emailExistente(String email) {
		try {
			String sql = "SELECT COUNT(*) FROM re_usuarios WHERE email = ?";
			return db.getEntero(sql, email);
		} catch (Exception e) {
			db.log("re_usuarios", 0, "emailExistente", null, null);
			return 0;
		}
	}

}
