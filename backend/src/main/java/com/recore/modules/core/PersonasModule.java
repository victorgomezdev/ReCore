package com.recore.modules.core;

import com.recore.utils.DBUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Map;

/**
 * Módulo de Personas de ReCore
 * Gestiona el registro y autenticación de usuarios:
 * - re_personas (registro de personas/usuarios)
 * - re_personas_roles (roles asignados a cada persona)
 * 
 * Reemplaza el patrón de boolean fields (es_cliente, es_proveedor) de SC3
 * por un sistema moderno de roles múltiples por persona
 */
@Component
public class PersonasModule {

    @Autowired
    private DBUtils db;
    
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    /**
     * Inicializa y actualiza las tablas del módulo Personas
     * Implementa patrón de actualización de versión similar a SC3 Core
     */
    public void reUpdateVersionPersonas2025() {
        
        // ============================================
        // TABLA: re_personas
        // ============================================
        crearTablaPersonas();
        
        // ============================================  
        // TABLA: re_personas_roles
        // ============================================
        crearTablaPersonasRoles();
        
        // Insertar roles básicos si no existen
        insertarRolesBasicos();
        
        // Crear usuario administrador por defecto si no existe
        crearUsuarioAdministrador();
    }

    /**
     * Crea la tabla re_personas para registro de usuarios
     * Equivalente mejorado de gen_personas de SC3 Core
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
                    fecha_registro DATETIME DEFAULT CURRENT_TIMESTAMP,
                    fecha_ultimo_login DATETIME,
                    intentos_login INT DEFAULT 0,
                    bloqueado_hasta DATETIME,
                    token_verificacion VARCHAR(255),
                    token_reset_password VARCHAR(255),
                    fecha_expira_token DATETIME,
                    INDEX idx_email (email),
                    INDEX idx_cedula (cedula),
                    INDEX idx_activo (activo),
                    INDEX idx_fecha_registro (fecha_registro)
                )
                """;
            db.reExecQuery(sql);
        }

        // Agregar campos adicionales si no existen
        db.reAgregarCampoStr("re_personas", "avatar_url", 500);
        db.reAgregarCampoStr("re_personas", "idioma_preferido", 10, "es");
        db.reAgregarCampoStr("re_personas", "timezone", 50, "America/Bogota");
        db.reAgregarCampoStr("re_personas", "metadata_json", 2000);
    }

    /**
     * Crea la tabla re_personas_roles para asignar múltiples roles a cada persona
     * Reemplaza el patrón de boolean fields (es_cliente, es_proveedor) de SC3
     */
    private void crearTablaPersonasRoles() {
        if (!db.reExisteTabla("re_personas_roles")) {
            String sql = """
                CREATE TABLE re_personas_roles (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    persona_id INT NOT NULL,
                    rol_id INT NOT NULL,
                    asignado_por INT,
                    fecha_asignacion DATETIME DEFAULT CURRENT_TIMESTAMP,
                    fecha_expiracion DATETIME,
                    activo BOOLEAN DEFAULT TRUE,
                    notas TEXT,
                    FOREIGN KEY (persona_id) REFERENCES re_personas(id) ON DELETE CASCADE,
                    FOREIGN KEY (rol_id) REFERENCES re_roles(id) ON DELETE CASCADE,
                    FOREIGN KEY (asignado_por) REFERENCES re_personas(id) ON DELETE SET NULL,
                    UNIQUE KEY unique_persona_rol (persona_id, rol_id),
                    INDEX idx_persona (persona_id),
                    INDEX idx_rol (rol_id),
                    INDEX idx_activo (activo),
                    INDEX idx_fecha_asignacion (fecha_asignacion)
                )
                """;
            db.reExecQuery(sql);
        }

        // Agregar campos adicionales si no existen
        db.reAgregarCampoStr("re_personas_roles", "contexto", 100); // ej: "sucursal_1", "hotel_5"
        db.reAgregarCampoDecimal("re_personas_roles", "prioridad", 3, 0, 1.0); // orden de roles
    }

    /**
     * Inserta los roles básicos del sistema si no existen
     */
    private void insertarRolesBasicos() {
        // Verificar si ya existen roles
        String countSql = "SELECT COUNT(*) FROM re_roles";
        int rolesCount = db.reObtenerEntero(countSql);
        
        if (rolesCount == 0) {
            // Roles básicos del sistema
            String[] roles = {
                "('admin', 'Administrador del Sistema', 'Acceso completo al sistema', TRUE)",
                "('usuario', 'Usuario Registrado', 'Usuario básico registrado', TRUE)",
                "('cliente', 'Cliente', 'Cliente del negocio', TRUE)",
                "('proveedor', 'Proveedor', 'Proveedor de servicios/productos', TRUE)",
                "('empleado', 'Empleado', 'Empleado de la empresa', TRUE)",
                "('supervisor', 'Supervisor', 'Supervisor de área', TRUE)",
                "('gerente', 'Gerente', 'Gerente de sucursal/área', TRUE)",
                "('invitado', 'Invitado', 'Usuario temporal/invitado', TRUE)"
            };
            
            for (String rol : roles) {
                String sql = "INSERT INTO re_roles (codigo, nombre, descripcion, activo) VALUES " + rol;
                db.reExecQuery(sql);
            }
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
            
            // Asignar rol de administrador
            String getRolAdminSql = "SELECT id FROM re_roles WHERE codigo = 'admin'";
            int rolAdminId = db.reObtenerEntero(getRolAdminSql);
            
            if (rolAdminId > 0) {
                String insertRol = """
                    INSERT INTO re_personas_roles 
                    (persona_id, rol_id, asignado_por, notas) 
                    VALUES 
                    (?, ?, 1, 'Usuario administrador por defecto del sistema')
                    """;
                db.reExecQuery(insertRol, personaId, rolAdminId);
            }
            
            System.out.println("[ReCore] Usuario admin creado - Email: admin@recore.com | Password: admin123");
        }
    }

    /**
     * Valida las credenciales de login de un usuario
     * @param email Email del usuario
     * @param password Password en texto plano
     * @return ID de la persona si las credenciales son válidas, 0 si no
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
                return 0;
            }
            
            // Verificar si está bloqueado
            if (bloqueadoHasta != null) {
                // TODO: Verificar si el bloqueo ya expiró
                return 0;
            }
            
            // Verificar password
            if (passwordEncoder.matches(password, passwordHash)) {
                // Login exitoso - resetear intentos fallidos
                String resetSql = """
                    UPDATE re_personas 
                    SET intentos_login = 0, fecha_ultimo_login = NOW() 
                    WHERE id = ?
                    """;
                db.reExecQuery(resetSql, personaId);
                
                // Log del login exitoso
                db.setLogContext(personaId, "127.0.0.1", "ReCore-Backend", "login_exitoso");
                db.reLog("re_personas", personaId, "LOGIN_SUCCESS", null, 
                    String.format("{\"email\":\"%s\"}", email));
                db.clearLogContext();
                
                return personaId;
            } else {
                // Password incorrecto - incrementar intentos
                incrementarIntentosLogin(personaId, intentosLogin);
                
                // Log del intento fallido
                db.setLogContext(null, "127.0.0.1", "ReCore-Backend", "login_fallido");
                db.reLog("re_personas", personaId, "LOGIN_FAILED", null, 
                    String.format("{\"email\":\"%s\",\"intentos\":%d}", email, intentosLogin + 1));
                db.clearLogContext();
                
                return 0;
            }
            
        } catch (Exception e) {
            System.err.println("Error en validarLogin: " + e.getMessage());
            return 0;
        }
    }
    
    /**
     * Incrementa los intentos de login fallidos y bloquea si es necesario
     */
    private void incrementarIntentosLogin(int personaId, int intentosActuales) {
        int nuevosIntentos = intentosActuales + 1;
        
        if (nuevosIntentos >= 5) {
            // Bloquear por 30 minutos después de 5 intentos fallidos
            String sql = """
                UPDATE re_personas 
                SET intentos_login = ?, bloqueado_hasta = DATE_ADD(NOW(), INTERVAL 30 MINUTE)
                WHERE id = ?
                """;
            db.reExecQuery(sql, nuevosIntentos, personaId);
        } else {
            String sql = "UPDATE re_personas SET intentos_login = ? WHERE id = ?";
            db.reExecQuery(sql, nuevosIntentos, personaId);
        }
    }

    /**
     * Obtiene los roles activos de una persona
     * @param personaId ID de la persona
     * @return Lista de códigos de roles
     */
    public String[] obtenerRolesPersona(int personaId) {
        try {
            String sql = """
                SELECT r.codigo 
                FROM re_roles r
                INNER JOIN re_personas_roles pr ON r.id = pr.rol_id
                WHERE pr.persona_id = ? 
                AND pr.activo = TRUE 
                AND r.activo = TRUE
                AND (pr.fecha_expiracion IS NULL OR pr.fecha_expiracion > NOW())
                ORDER BY pr.prioridad DESC, pr.fecha_asignacion ASC
                """;
            
            List<String> roles = db.jdbcTemplate.queryForList(sql, String.class, personaId);
            return roles.toArray(new String[0]);
            
        } catch (Exception e) {
            System.err.println("Error en obtenerRolesPersona: " + e.getMessage());
            return new String[0];
        }
    }

    /**
     * Registra un nuevo usuario en el sistema con logging automático
     * @param nombre Nombre del usuario
     * @param apellido Apellido del usuario  
     * @param email Email del usuario
     * @param password Password en texto plano
     * @return ID de la persona creada, 0 si hay error
     */
    public int registrarUsuario(String nombre, String apellido, String email, String password) {
        try {
            // Verificar que el email no existe
            String checkSql = "SELECT COUNT(*) FROM re_personas WHERE email = ?";
            int emailCount = db.reObtenerEntero(checkSql, email);
            
            if (emailCount > 0) {
                return 0; // Email ya existe
            }
            
            // Hash del password con BCrypt
            String passwordHash = passwordEncoder.encode(password);
            
            // Establecer contexto para logging
            db.setLogContext(null, "127.0.0.1", "ReCore-Backend", "registro_usuario");
            
            // Insertar nueva persona con logging automático
            String insertSql = """
                INSERT INTO re_personas 
                (nombre, apellido, email, password_hash, token_verificacion) 
                VALUES (?, ?, ?, ?, ?)
                """;
            
            String tokenVerificacion = java.util.UUID.randomUUID().toString();
            int personaId = db.reExecQueryGetId(insertSql, nombre, apellido, email, passwordHash, tokenVerificacion);
            
            if (personaId > 0) {
                // Log específico del registro
                db.reLog("re_personas", personaId, "REGISTER", null, 
                    String.format("{\"email\":\"%s\",\"nombre\":\"%s\",\"apellido\":\"%s\"}", 
                                email, nombre, apellido));
                
                // Asignar rol de usuario básico
                String getRolUsuarioSql = "SELECT id FROM re_roles WHERE codigo = 'usuario'";
                int rolUsuarioId = db.reObtenerEntero(getRolUsuarioSql);
                
                if (rolUsuarioId > 0) {
                    String insertRol = """
                        INSERT INTO re_personas_roles 
                        (persona_id, rol_id, notas) 
                        VALUES (?, ?, 'Rol asignado automáticamente en el registro')
                        """;
                    db.reExecQueryWithLog(insertRol, personaId, rolUsuarioId);
                }
            }
            
            // Limpiar contexto
            db.clearLogContext();
            
            return personaId;
            
        } catch (Exception e) {
            db.clearLogContext();
            System.err.println("Error en registrarUsuario: " + e.getMessage());
            return 0;
        }
    }
}
