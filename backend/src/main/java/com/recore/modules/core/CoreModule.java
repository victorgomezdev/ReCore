package com.recore.modules.core;

import com.recore.utils.DBUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Módulo Core de ReCore
 * Gestiona la infraestructura básica del sistema:
 * - re_roles (catálogo de roles)
 * - re_usuarios (usuarios para login) 
 * - re_querys (metadatos de consultas)
 * - re_fields (metadatos de campos)
 * - re_perfiles (perfiles de usuario)
 * - re_permisos (permisos por perfil)
 */
@Component
public class CoreModule {

    @Autowired
    private DBUtils db;
    
    @Autowired
    private PersonasModule personasModule;

    /**
     * Inicializa y actualiza las tablas del módulo Core
     * Implementa patrón de actualización de versión similar a SC3 Core
     */
    public void reUpdateVersionCore2025() {
        
        // ============================================
        // TABLA: re_roles
        // ============================================
        crearTablaRoles();
        
        // ============================================  
        // TABLA: re_perfiles
        // ============================================
        crearTablaPerfiles();
        
        // ============================================
        // TABLA: re_querys  
        // ============================================
        crearTablaQuerys();
        
        // ============================================
        // TABLA: re_fields
        // ============================================
        crearTablaFields();
        
        // ============================================
        // TABLA: re_usuarios
        // ============================================ 
        crearTablaUsuarios();
        
        // ============================================
        // TABLA: re_permisos
        // ============================================
        crearTablaPermisos();
        
        // ============================================
        // TABLA: re_logs (Auditoría centralizada)
        // ============================================
        crearTablaLogs();
        
        // ============================================
        // MÓDULO: PersonasModule (Registro y Login)
        // ============================================
        personasModule.reUpdateVersionPersonas2025();
        
        System.out.println("[ReCore] CoreModule actualizado correctamente");
    }

    /**
     * Crea la tabla re_roles (catálogo de roles del sistema)
     * Reemplaza los campos boolean de SC3 (es_cliente, es_proveedor, etc)
     */
    private void crearTablaRoles() {
        String tabla = "re_roles";
        
        if (!db.reExisteTabla(tabla)) {
            String sql = """
                CREATE TABLE re_roles (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    codigo VARCHAR(50) NOT NULL UNIQUE COMMENT 'Código único del rol',
                    nombre VARCHAR(50) NOT NULL COMMENT 'Nombre del rol',
                    descripcion VARCHAR(200) COMMENT 'Descripción del rol',
                    activo TINYINT(1) DEFAULT 1 COMMENT 'Si el rol está activo'
                ) ENGINE=InnoDB COMMENT='Catálogo de roles - Reemplaza boolean fields de SC3'
                """;
            
            db.reExecQuery(sql);
            
            // Insertar roles iniciales
            db.reExecQuery("INSERT INTO re_roles (nombre, descripcion) VALUES (?, ?)", 
                          "cliente", "Cliente del sistema");
            db.reExecQuery("INSERT INTO re_roles (nombre, descripcion) VALUES (?, ?)", 
                          "proveedor", "Proveedor de productos/servicios");
            db.reExecQuery("INSERT INTO re_roles (nombre, descripcion) VALUES (?, ?)", 
                          "empleado", "Empleado de la empresa");
            db.reExecQuery("INSERT INTO re_roles (nombre, descripcion) VALUES (?, ?)", 
                          "usuario", "Usuario del sistema");
            db.reExecQuery("INSERT INTO re_roles (nombre, descripcion) VALUES (?, ?)", 
                          "administrador", "Administrador del sistema");
                          
            System.out.println("[ReCore] Tabla re_roles creada con roles iniciales");
        }
    }

    /**
     * Crea la tabla re_perfiles (perfiles de usuario)
     * Implementa funcionalidad similar a sc_perfiles de SC3 Core
     */
    private void crearTablaPerfiles() {
        String tabla = "re_perfiles";
        
        if (!db.reExisteTabla(tabla)) {
            String sql = """
                CREATE TABLE re_perfiles (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    nombre VARCHAR(100) NOT NULL UNIQUE COMMENT 'Nombre del perfil',
                    descripcion VARCHAR(200) COMMENT 'Descripción del perfil',
                    activo TINYINT(1) DEFAULT 1 COMMENT 'Si está activo',
                    es_administrador TINYINT(1) DEFAULT 0 COMMENT 'Si tiene permisos de administrador'
                ) ENGINE=InnoDB COMMENT='Perfiles de usuario - Equivalente a sc_perfiles'
                """;
            
            db.reExecQuery(sql);
            
            // Insertar perfiles iniciales
            db.reExecQuery("INSERT INTO re_perfiles (nombre, descripcion, es_administrador) VALUES (?, ?, ?)", 
                          "Administrador", "Perfil de administrador del sistema", 1);
            db.reExecQuery("INSERT INTO re_perfiles (nombre, descripcion, es_administrador) VALUES (?, ?, ?)", 
                          "Usuario", "Perfil de usuario estándar", 0);
            db.reExecQuery("INSERT INTO re_perfiles (nombre, descripcion, es_administrador) VALUES (?, ?, ?)", 
                          "Invitado", "Perfil de solo lectura", 0);
                          
            System.out.println("[ReCore] Tabla re_perfiles creada con perfiles iniciales");
        }
    }

    /**
     * Crea la tabla re_querys (metadatos de consultas)
     * Implementa funcionalidad similar a sc_querys de SC3 Core
     */
    private void crearTablaQuerys() {
        String tabla = "re_querys";
        
        if (!db.reExisteTabla(tabla)) {
            String sql = """
                CREATE TABLE re_querys (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    queryname VARCHAR(100) NOT NULL UNIQUE COMMENT 'Nombre único de la consulta',
                    table_ VARCHAR(100) NOT NULL COMMENT 'Tabla principal',
                    description VARCHAR(200) COMMENT 'Descripción de la consulta',
                    key_field VARCHAR(50) DEFAULT 'id' COMMENT 'Campo clave',
                    can_insert TINYINT(1) DEFAULT 1 COMMENT 'Permite insertar',
                    can_update TINYINT(1) DEFAULT 1 COMMENT 'Permite actualizar',
                    can_delete TINYINT(1) DEFAULT 1 COMMENT 'Permite eliminar',
                    order_field VARCHAR(100) DEFAULT 'id' COMMENT 'Campo de ordenamiento',
                    page_size INT DEFAULT 20 COMMENT 'Tamaño de página',
                    icon VARCHAR(100) COMMENT 'Icono FontAwesome',
                    active TINYINT(1) DEFAULT 1 COMMENT 'Si está activa',
                    module_name VARCHAR(50) COMMENT 'Módulo al que pertenece'
                ) ENGINE=InnoDB COMMENT='Metadatos de consultas - Equivalente a sc_querys'
                """;
            
            db.reExecQuery(sql);
            System.out.println("[ReCore] Tabla re_querys creada");
        }
    }

    /**
     * Crea la tabla re_fields (metadatos de campos)
     * Implementa funcionalidad similar a sc_fields de SC3 Core
     */
    private void crearTablaFields() {
        String tabla = "re_fields";
        
        if (!db.reExisteTabla(tabla)) {
            String sql = """
                CREATE TABLE re_fields (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    idquery INT NOT NULL COMMENT 'Referencia a re_querys',
                    field_ VARCHAR(100) NOT NULL COMMENT 'Nombre del campo',
                    show_name VARCHAR(200) COMMENT 'Nombre a mostrar',
                    field_type VARCHAR(20) COMMENT 'Tipo de campo',
                    field_length INT COMMENT 'Longitud del campo',
                    is_required TINYINT(1) DEFAULT 0 COMMENT 'Si es requerido',
                    is_editable TINYINT(1) DEFAULT 1 COMMENT 'Si es editable',
                    visible TINYINT(1) DEFAULT 1 COMMENT 'Si es visible',
                    default_value_exp VARCHAR(500) COMMENT 'Valor por defecto',
                    field_help VARCHAR(1000) COMMENT 'Ayuda del campo',
                    grupo VARCHAR(100) COMMENT 'Grupo visual',
                    orden INT DEFAULT 0 COMMENT 'Orden de presentación',
                    FOREIGN KEY (idquery) REFERENCES re_querys(id) ON DELETE CASCADE,
                    UNIQUE KEY uk_query_field (idquery, field_)
                ) ENGINE=InnoDB COMMENT='Metadatos de campos - Equivalente a sc_fields'
                """;
            
            db.reExecQuery(sql);
            System.out.println("[ReCore] Tabla re_fields creada");
        }
    }

    /**
     * Crea la tabla re_usuarios (usuarios para login)
     * Mejora la funcionalidad de sc_usuarios de SC3 Core
     */
    private void crearTablaUsuarios() {
        String tabla = "re_usuarios";
        
        if (!db.reExisteTabla(tabla)) {
            String sql = """
                CREATE TABLE re_usuarios (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    idpersona INT COMMENT 'Referencia a re_personas',
                    idperfil INT NOT NULL COMMENT 'Referencia al perfil',
                    usuario VARCHAR(50) NOT NULL UNIQUE COMMENT 'Nombre de usuario',
                    password VARCHAR(255) NOT NULL COMMENT 'Password hasheado',
                    email VARCHAR(100) COMMENT 'Email del usuario',
                    activo TINYINT(1) DEFAULT 1 COMMENT 'Si está activo',
                    ultimo_login DATETIME COMMENT 'Fecha del último login',
                    intentos_fallidos INT DEFAULT 0 COMMENT 'Intentos fallidos',
                    FOREIGN KEY (idperfil) REFERENCES re_perfiles(id),
                    INDEX idx_usuario (usuario),
                    INDEX idx_email (email)
                ) ENGINE=InnoDB COMMENT='Usuarios del sistema - Mejora de sc_usuarios'
                """;
            
            db.reExecQuery(sql);
            System.out.println("[ReCore] Tabla re_usuarios creada");
        }
    }

    /**
     * Crea la tabla re_permisos (permisos por perfil y consulta)
     * Implementa sistema de permisos granulares
     */
    private void crearTablaPermisos() {
        String tabla = "re_permisos";
        
        if (!db.reExisteTabla(tabla)) {
            String sql = """
                CREATE TABLE re_permisos (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    idperfil INT NOT NULL COMMENT 'Referencia al perfil',
                    idquery INT NOT NULL COMMENT 'Referencia a la consulta',
                    puede_ver TINYINT(1) DEFAULT 0 COMMENT 'Puede ver registros',
                    puede_insertar TINYINT(1) DEFAULT 0 COMMENT 'Puede insertar',
                    puede_actualizar TINYINT(1) DEFAULT 0 COMMENT 'Puede actualizar',
                    puede_eliminar TINYINT(1) DEFAULT 0 COMMENT 'Puede eliminar',
                    puede_exportar TINYINT(1) DEFAULT 0 COMMENT 'Puede exportar',
                    FOREIGN KEY (idperfil) REFERENCES re_perfiles(id) ON DELETE CASCADE,
                    FOREIGN KEY (idquery) REFERENCES re_querys(id) ON DELETE CASCADE,
                    UNIQUE KEY uk_perfil_query (idperfil, idquery)
                ) ENGINE=InnoDB COMMENT='Permisos por perfil y consulta'
                """;
            
            db.reExecQuery(sql);
            System.out.println("[ReCore] Tabla re_permisos creada");
        }
    }

    /**
     * Crea la tabla re_logs para auditoría centralizada del sistema
     * Equivalente a sc_logs de SC3 Core - registra todas las modificaciones
     */
    private void crearTablaLogs() {
        String tabla = "re_logs";
        
        if (!db.reExisteTabla(tabla)) {
            String sql = """
                CREATE TABLE re_logs (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    tabla VARCHAR(100) NOT NULL COMMENT 'Tabla afectada',
                    registro_id INT COMMENT 'ID del registro afectado',
                    accion VARCHAR(20) NOT NULL COMMENT 'INSERT, UPDATE, DELETE',
                    usuario_id INT COMMENT 'Usuario que realizó la acción',
                    datos_anteriores JSON COMMENT 'Valores antes del cambio (UPDATE/DELETE)',
                    datos_nuevos JSON COMMENT 'Valores después del cambio (INSERT/UPDATE)',
                    ip VARCHAR(45) COMMENT 'IP del usuario',
                    user_agent TEXT COMMENT 'Navegador/cliente usado',
                    fecha DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT 'Fecha y hora de la acción',
                    contexto VARCHAR(100) COMMENT 'Contexto adicional (ej: API, admin, import)',
                    INDEX idx_tabla (tabla),
                    INDEX idx_registro (registro_id),
                    INDEX idx_accion (accion),
                    INDEX idx_usuario (usuario_id),
                    INDEX idx_fecha (fecha),
                    INDEX idx_tabla_registro (tabla, registro_id)
                ) ENGINE=InnoDB COMMENT='Auditoría centralizada - equivalente a sc_logs'
                """;
            
            db.reExecQuery(sql);
            System.out.println("[ReCore] Tabla re_logs creada");
        }

        // Agregar campos adicionales si no existen
        db.reAgregarCampoStr("re_logs", "modulo", 50); // ej: "core", "hotel", "pos"
        db.reAgregarCampoStr("re_logs", "session_id", 100);
    }
}
