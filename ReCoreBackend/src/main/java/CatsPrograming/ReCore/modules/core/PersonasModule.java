package CatsPrograming.ReCore.modules.core;

import CatsPrograming.ReCore.utils.DBUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Map;

/**
 * Módulo de Personas de ReCore
 * Gestiona el registro y autenticación de usuarios
 */
@Component
public class PersonasModule {

    @Autowired
    private DBUtils db;
    
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    /**
     * Inicializa las tablas del módulo Personas
     */
    public void reUpdateVersionPersonas2025() {
        System.out.println("[ReCore] Inicializando módulo de personas...");
        
        try {
            // Crear tablas básicas
            crearTablaPersonas();
            crearTablaRoles();
            crearTablaPersonasRoles();
            
            // Insertar datos básicos
            insertarRolesBasicos();
            crearUsuarioAdministrador();
            
            System.out.println("[ReCore] Módulo de personas inicializado correctamente");
            
        } catch (Exception e) {
            System.err.println("[ReCore] Error en inicialización: " + e.getMessage());
            // No lanzar excepción para que el backend pueda continuar
        }
    }

    /**
     * Crea la tabla re_personas para registro de usuarios
     */
    private void crearTablaPersonas() {
        if (!db.reExisteTabla("re_personas")) {
            String sql = """
                CREATE TABLE re_personas (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    nombre VARCHAR(100) NOT NULL,
                    apellido VARCHAR(100) NOT NULL,
                    email VARCHAR(255) UNIQUE NOT NULL,
                    telefono VARCHAR(20),
                    direccion TEXT,
                    fecha_nacimiento DATE,
                    cedula VARCHAR(20) UNIQUE,
                    password_hash VARCHAR(255),
                    email_verificado BOOLEAN DEFAULT FALSE,
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
            db.reExecQuery(sql);
            
            // Crear índices por separado (compatible con H2)
            try {
                db.reExecQuery("CREATE INDEX idx_email ON re_personas (email)");
                db.reExecQuery("CREATE INDEX idx_cedula ON re_personas (cedula)");
                db.reExecQuery("CREATE INDEX idx_activo ON re_personas (activo)");
                db.reExecQuery("CREATE INDEX idx_fecha_registro ON re_personas (fecha_registro)");
            } catch (Exception e) {
                System.out.println("[ReCore] Índices ya existen: " + e.getMessage());
            }
            
            System.out.println("[ReCore] Tabla re_personas creada");
        }
    }

    /**
     * Crea la tabla re_roles
     */
    private void crearTablaRoles() {
        if (!db.reExisteTabla("re_roles")) {
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
            db.reExecQuery(sql);
            System.out.println("[ReCore] Tabla re_roles creada");
        }
    }

    /**
     * Crea la tabla re_personas_roles (compatible con H2)
     */
    private void crearTablaPersonasRoles() {
        if (!db.reExisteTabla("re_personas_roles")) {
            // Crear tabla sin constraints primero
            String sql = """
                CREATE TABLE re_personas_roles (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    persona_id INT NOT NULL,
                    rol_id INT NOT NULL,
                    fecha_asignacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                    asignado_por INT,
                    activo BOOLEAN DEFAULT TRUE,
                    notas TEXT
                )
                """;
            db.reExecQuery(sql);
            
            // Agregar constraints por separado
            try {
                db.reExecQuery("ALTER TABLE re_personas_roles ADD FOREIGN KEY (persona_id) REFERENCES re_personas(id)");
                db.reExecQuery("ALTER TABLE re_personas_roles ADD FOREIGN KEY (rol_id) REFERENCES re_roles(id)");
                db.reExecQuery("CREATE UNIQUE INDEX unique_persona_rol ON re_personas_roles (persona_id, rol_id)");
            } catch (Exception e) {
                System.out.println("[ReCore] Constraints ya existen o no se pudieron crear: " + e.getMessage());
            }
            
            System.out.println("[ReCore] Tabla re_personas_roles creada");
        }
    }

    /**
     * Inserta los roles básicos del sistema
     */
    private void insertarRolesBasicos() {
        String checkSql = "SELECT COUNT(*) FROM re_roles";
        int rolesCount = db.reObtenerEntero(checkSql);
        
        if (rolesCount == 0) {
            String[] roles = {
                "('admin', 'Administrador', 'Administrador del sistema con acceso completo', TRUE)",
                "('cliente', 'Cliente', 'Cliente que puede realizar compras', TRUE)",
                "('vendedor', 'Vendedor', 'Vendedor que puede gestionar productos', TRUE)",
                "('invitado', 'Invitado', 'Usuario temporal/invitado', TRUE)"
            };
            
            for (String rol : roles) {
                String sql = "INSERT INTO re_roles (codigo, nombre, descripcion, activo) VALUES " + rol;
                db.reExecQuery(sql);
            }
            System.out.println("[ReCore] Roles básicos insertados");
        }
    }

    /**
     * Crea un usuario administrador por defecto si no existe
     */
    private void crearUsuarioAdministrador() {
        String checkSql = "SELECT COUNT(*) FROM re_personas WHERE email = 'admin@recore.com'";
        int adminCount = db.reObtenerEntero(checkSql);
        
        if (adminCount == 0) {
            // Crear usuario administrador con password "admin123"
            String password = passwordEncoder.encode("admin123");
            
            String insertPersona = """
                INSERT INTO re_personas 
                (nombre, apellido, email, password_hash, email_verificado, activo, cedula) 
                VALUES 
                ('Admin', 'ReCore', 'admin@recore.com', ?, TRUE, TRUE, 'ADMIN001')
                """;
            
            int personaId = db.reExecQueryGetId(insertPersona, password);
            
            // Asignar rol de administrador si existe la tabla de roles
            try {
                String getRolAdminSql = "SELECT id FROM re_roles WHERE codigo = 'admin'";
                int rolAdminId = db.reObtenerEntero(getRolAdminSql);
                
                if (rolAdminId > 0 && db.reExisteTabla("re_personas_roles")) {
                    String insertRol = """
                        INSERT INTO re_personas_roles 
                        (persona_id, rol_id, asignado_por, notas) 
                        VALUES 
                        (?, ?, 1, 'Usuario administrador por defecto del sistema')
                        """;
                    db.reExecQuery(insertRol, personaId, rolAdminId);
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
                SELECT id, password_hash, activo, bloqueado_hasta, intentos_login 
                FROM re_personas 
                WHERE email = ? AND email_verificado = TRUE
                """;
            
            List<Map<String, Object>> resultados = db.jdbcTemplate.queryForList(sql, email);
            
            if (resultados.isEmpty()) {
                System.out.println("[ReCore] Usuario no encontrado: " + email);
                return 0; // Usuario no encontrado
            }
            
            Map<String, Object> usuario = resultados.get(0);
            int personaId = (Integer) usuario.get("id");
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
                    UPDATE re_personas 
                    SET intentos_login = 0, fecha_ultimo_login = CURRENT_TIMESTAMP 
                    WHERE id = ?
                    """;
                db.reExecQuery(resetSql, personaId);
                
                System.out.println("[ReCore] Login exitoso para: " + email);
                return personaId;
            } else {
                // Password incorrecto - incrementar intentos
                incrementarIntentosLogin(personaId, intentosLogin);
                
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
    private void incrementarIntentosLogin(int personaId, int intentosActuales) {
        int nuevosIntentos = intentosActuales + 1;
        
        if (nuevosIntentos >= 5) {
            // Bloquear por 30 minutos después de 5 intentos fallidos
            String sql = """
                UPDATE re_personas 
                SET intentos_login = ?, bloqueado_hasta = DATEADD('MINUTE', 30, CURRENT_TIMESTAMP)
                WHERE id = ?
                """;
            db.reExecQuery(sql, nuevosIntentos, personaId);
        } else {
            String sql = """
                UPDATE re_personas 
                SET intentos_login = ?
                WHERE id = ?
                """;
            db.reExecQuery(sql, nuevosIntentos, personaId);
        }
    }
}
