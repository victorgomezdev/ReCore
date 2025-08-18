package CatsPrograming.ReCore.modules.core;

import CatsPrograming.ReCore.utils.DBUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Map;

/**
 * Módulo de Usuarios de ReCore
 * Gestiona autenticación y acceso al sistema
 */
@Component
public class UsuariosModule {

    @Autowired
    private DBUtils db;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    /**
     * Inicializar las tablas y datos básicos de usuarios
     */
    public void init() {
        System.out.println("[ReCore] Inicializando módulo de usuarios...");

        try {
            // Crear tablas de usuarios
            crearTablaUsuarios();
            crearTablaRoles();
            crearTablaUsuariosRoles();

            // Insertar datos básicos
            insertarRolesBasicos();
            crearUsuarioAdministrador();

            System.out.println("[ReCore] Módulo de usuarios inicializado correctamente");

        } catch (Exception e) {
            System.err.println("[ReCore] Error en inicialización de usuarios: " + e.getMessage());
        }
    }

    /**
     * Crea la tabla re_usuarios para credenciales y acceso al sistema
     */
    private void crearTablaUsuarios() {
        if (!db.existeTabla("re_usuarios")) {

            try {
                String sql = """
                        CREATE TABLE re_usuarios (
                            id INT AUTO_INCREMENT PRIMARY KEY,
                            persona_id INT,
                            email VARCHAR(255) UNIQUE NOT NULL,
                            password_hash VARCHAR(255) NOT NULL,
                            activo BOOLEAN DEFAULT TRUE,
                            fecha_registro TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                            fecha_ultimo_login TIMESTAMP,
                            intentos_login INT DEFAULT 0,
                            bloqueado_hasta TIMESTAMP,
                            token_verificacion VARCHAR(255),
                            token_reset_password VARCHAR(255),
                            fecha_expira_token TIMESTAMP
                        )
                        """;
                db.execQuery(sql);

                // Crear índices por separado (compatible con H2)

                db.execQuery("CREATE INDEX idx_usuario_email ON re_usuarios (email)");
                db.execQuery("CREATE INDEX idx_usuario_activo ON re_usuarios (activo)");
                db.execQuery("CREATE INDEX idx_usuario_persona ON re_usuarios (persona_id)");

                db.generateFieldsInfo("re_usuarios", 0);

                System.out.println("[ReCore] Tabla re_usuarios creada");
            } catch (Exception e) {
                System.out.println("[ReCore] Error al crear índices de la tabla re_usuarios: " + e.getMessage());
            }
        }
    }

    /**
     * Crea la tabla re_roles
     */
    private void crearTablaRoles() {
        if (!db.existeTabla("re_roles")) {
            try {
                String sql = """
                        CREATE TABLE re_roles (
                            id INT AUTO_INCREMENT PRIMARY KEY,
                            codigo VARCHAR(50) UNIQUE NOT NULL,
                            nombre VARCHAR(100) NOT NULL,
                            descripcion TEXT,
                            activo BOOLEAN DEFAULT TRUE,
                            fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP
                        )
                        """;
                db.execQuery(sql);
                db.generateFieldsInfo("re_roles", 0);

                System.out.println("[ReCore] Tabla re_roles creada");
            } catch (Exception e) {
                System.out.println("[ReCore] Error al crear tabla re_roles: " + e.getMessage());
            }
        }
    }

    /**
     * Crea la tabla re_usuarios_roles (permisos asociados a USUARIOS)
     */
    private void crearTablaUsuariosRoles() {
        if (!db.existeTabla("re_usuarios_roles")) {
            try {
                String sql = """
                        CREATE TABLE re_usuarios_roles (
                            id INT AUTO_INCREMENT PRIMARY KEY,
                            usuario_id INT NOT NULL,
                            rol_id INT NOT NULL,
                            fecha_asignacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                            asignado_por INT,
                            activo BOOLEAN DEFAULT TRUE,
                            notas TEXT
                        )
                        """;
                db.execQuery(sql);
                db.generateFieldsInfo("re_usuarios_roles", 0);
                // Crear indices por separado (compatible con H2)
                db.execQuery("ALTER TABLE re_usuarios_roles ADD FOREIGN KEY (usuario_id) REFERENCES re_usuarios(id)");
                db.execQuery("ALTER TABLE re_usuarios_roles ADD FOREIGN KEY (rol_id) REFERENCES re_roles(id)");
                db.execQuery("CREATE UNIQUE INDEX unique_usuario_rol ON re_usuarios_roles (usuario_id, rol_id)");
            } catch (Exception e) {
                System.out.println("[ReCore] Constraints ya existen: " + e.getMessage());
            }

            System.out.println("[ReCore] Tabla re_usuarios_roles creada");
        }
    }

    /**
     * Inserta los roles básicos del sistema
     */
    private void insertarRolesBasicos() {
        try {
            String checkSql = "SELECT COUNT(*) FROM re_roles";
            int rolesCount = db.obtenerEntero(checkSql);

            if (rolesCount == 0) {
                String[] roles = {
                        "('admin', 'Administrador', 'Administrador del sistema con acceso completo', TRUE)",
                        "('cliente', 'Cliente', 'Cliente que puede realizar compras', TRUE)",
                        "('vendedor', 'Vendedor', 'Vendedor que puede gestionar productos', TRUE)",
                        "('invitado', 'Invitado', 'Usuario temporal/invitado', TRUE)"
                };

                for (String rol : roles) {
                    String sql = "INSERT INTO re_roles (codigo, nombre, descripcion, activo) VALUES " + rol;
                    db.execQuery(sql);
                }
                System.out.println("[ReCore] Roles básicos insertados");
            }
        } catch (Exception e) {
            System.out.println("[ReCore] Error al insertar roles básicos: " + e.getMessage());
        }
    }

    /**
     * Crea un usuario administrador por defecto si no existe
     */
    private void crearUsuarioAdministrador() {
        String checkSql = "SELECT COUNT(*) FROM re_usuarios WHERE email = 'admin@recore.com'";
        int adminCount = db.obtenerEntero(checkSql);

        // Asignar rol de administrador
        if (adminCount == 0) {
            try {
                // Crear usuario administrador (sin persona asociada)
                String password = passwordEncoder.encode("admin123");
                String insertUsuario = """
                        INSERT INTO re_usuarios
                        (email, password_hash, activo)
                        VALUES
                        ('admin@recore.com', ?, TRUE)
                        """;
                int usuarioId = db.execQueryGetId(insertUsuario, password);

                String getRolAdminSql = "SELECT id FROM re_roles WHERE codigo = 'admin'";
                int rolAdminId = db.obtenerEntero(getRolAdminSql);

                if (rolAdminId > 0) {
                    String insertRol = """
                            INSERT INTO re_usuarios_roles
                            (usuario_id, rol_id, asignado_por, notas)
                            VALUES
                            (?, ?, 1, 'Usuario administrador por defecto del sistema')
                            """;
                    db.execQuery(insertRol, usuarioId, rolAdminId);
                }
            } catch (Exception e) {
                System.out.println("[ReCore] No se pudo asignar rol al admin: " + e.getMessage());
            }

            System.out.println("[ReCore] Usuario admin creado - Email: admin@recore.com | Password: admin123");
        }
    }

    /**
     * Valida las credenciales de login
     */
    public int validarLogin(String email, String password) {
        try {
            String sql = """
                    SELECT u.id as usuario_id, u.password_hash, u.activo, u.bloqueado_hasta, u.intentos_login,
                           p.id as persona_id, p.nombre, p.apellido
                    FROM re_usuarios u
                    LEFT JOIN re_personas p ON u.persona_id = p.id
                    WHERE u.email = ?
                    """;

            List<Map<String, Object>> resultados = db.jdbcTemplate.queryForList(sql, email);

            if (resultados.isEmpty()) {
                System.out.println("[ReCore] Usuario no encontrado: " + email);
                return 0;
            }

            Map<String, Object> usuario = resultados.get(0);
            int usuarioId = (Integer) usuario.get("usuario_id");
            String passwordHash = (String) usuario.get("password_hash");
            boolean activo = (Boolean) usuario.get("activo");
            Object bloqueadoHasta = usuario.get("bloqueado_hasta");
            int intentosLogin = (Integer) usuario.get("intentos_login");

            // Verificar si el usuario está activo
            if (!activo) {
                System.out.println("[ReCore] Usuario inactivo: " + email);
                return 0;
            }

            // Verificar si está bloqueado
            if (bloqueadoHasta != null) {
                System.out.println("[ReCore] Usuario bloqueado: " + email);
                return 0;
            }

            // Verificar password
            if (passwordEncoder.matches(password, passwordHash)) {
                // Login exitoso - resetear intentos fallidos
                String resetSql = """
                        UPDATE re_usuarios
                        SET intentos_login = 0, fecha_ultimo_login = CURRENT_TIMESTAMP
                        WHERE id = ?
                        """;
                db.execQuery(resetSql, usuarioId);

                System.out.println("[ReCore] Login exitoso para: " + email);
                return usuarioId;
            } else {
                // Password incorrecto - incrementar intentos
                incrementarIntentosLogin(usuarioId, intentosLogin);

                System.out.println("[ReCore] Password incorrecto para: " + email);
                return 0;
            }

        } catch (Exception e) {
            System.err.println("Error en validarLogin: " + e.getMessage());
            return 0;
        }
    }

    /**
     * Incrementa los intentos de login fallidos
     */
    private void incrementarIntentosLogin(int usuarioId, int intentosActuales) {
        int nuevosIntentos = intentosActuales + 1;

        if (nuevosIntentos >= 5) {
            // Bloquear por 30 minutos después de 5 intentos fallidos
            String sql = """
                    UPDATE re_usuarios
                    SET intentos_login = ?, bloqueado_hasta = DATEADD('MINUTE', 30, CURRENT_TIMESTAMP)
                    WHERE id = ?
                    """;
            db.execQuery(sql, nuevosIntentos, usuarioId);
        } else {
            String sql = """
                    UPDATE re_usuarios
                    SET intentos_login = ?
                    WHERE id = ?
                    """;
            db.execQuery(sql, nuevosIntentos, usuarioId);
        }
    }

    /**
     * Verifica si un email ya existe en la tabla de usuarios
     */
    public int emailExistente(String email) {
        try {
            String sql = "SELECT COUNT(*) FROM re_usuarios WHERE email = ?";
            return db.obtenerEntero(sql, email);
        } catch (Exception e) {
            System.err.println("Error en emailExistente: " + e.getMessage());
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

            String passwordHash = passwordEncoder.encode(password);
            String sql = """
                    INSERT INTO re_usuarios
                    (persona_id, email, password_hash, activo)
                    VALUES
                    (?, ?, ?, TRUE)
                    """;

            int usuarioId = db.execQueryGetId(sql, personaId, email, passwordHash);
            System.out.println("[ReCore] Usuario creado: " + email + " | ID: " + usuarioId);

            // Ahora asigna el rol "cliente" por defecto
            insertarRolAUsuario(usuarioId, "cliente");

            return usuarioId;

        } catch (Exception e) {
            System.err.println("Error creando usuario: " + e.getMessage());
            return 0;
        }
    }

    /**
     * Inserta un rol a un usuario dado el id de usuario y el nombre/código del rol
     * 
     * @param usuarioId id del usuario
     * @param nombreRol código del rol (por ejemplo: "admin", "cliente", etc)
     * @return true si se insertó correctamente, false en caso contrario
     */
    public void insertarRolAUsuario(int usuarioId, String nombreRol) {
        try {
            String getRolSql = "SELECT id FROM re_roles WHERE codigo = ?";
            int rolId = db.obtenerEntero(getRolSql, nombreRol);
            if (rolId > 0) {
                String insertRol = """
                        INSERT INTO re_usuarios_roles
                        (usuario_id, rol_id, asignado_por, notas)
                        VALUES
                        (?, ?, 1, 'Rol asignado por insertarRolAUsuario')
                        """;
                db.execQuery(insertRol, usuarioId, rolId);
            } else {
                System.out.println("[ReCore] No se encontró el rol: " + nombreRol);
            }
        } catch (Exception e) {
            System.err.println("[ReCore] Error en insertarRolAUsuario: " + e.getMessage());
        }
    }
}
