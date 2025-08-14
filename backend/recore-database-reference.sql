-- ================================================
-- RECORE DATABASE STRUCTURE - REFERENCIA COMPLETA
-- Basado en análisis de SC3 Core y diseño mejorado
-- Fecha: 14 de agosto de 2025
-- ================================================

-- ================================================
-- CORE MODULE - INFRAESTRUCTURA DEL SISTEMA
-- ================================================

-- Tabla de roles del sistema (reemplaza los boolean de SC3)
CREATE TABLE re_roles (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL UNIQUE COMMENT 'Nombre del rol (cliente, proveedor, empleado, usuario)',
    descripcion VARCHAR(200) COMMENT 'Descripción del rol',
    activo TINYINT(1) DEFAULT 1 COMMENT 'Si el rol está activo',
    fecha_creacion DATETIME DEFAULT CURRENT_TIMESTAMP,
    fecha_modificacion DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB COMMENT='Catálogo de roles del sistema - Reemplaza boolean fields de SC3';

-- Datos iniciales de roles
INSERT INTO re_roles (nombre, descripcion) VALUES 
('cliente', 'Cliente del sistema'),
('proveedor', 'Proveedor de productos/servicios'),
('empleado', 'Empleado de la empresa'),
('usuario', 'Usuario del sistema'),
('administrador', 'Administrador del sistema');

-- Tabla de metadatos de consultas (equivalente a sc_querys)
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
    module_name VARCHAR(50) COMMENT 'Módulo al que pertenece',
    fecha_creacion DATETIME DEFAULT CURRENT_TIMESTAMP,
    fecha_modificacion DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB COMMENT='Metadatos de consultas - Equivalente a sc_querys';

-- Tabla de metadatos de campos (equivalente a sc_fields)
CREATE TABLE re_fields (
    id INT AUTO_INCREMENT PRIMARY KEY,
    idquery INT NOT NULL COMMENT 'Referencia a re_querys',
    field_ VARCHAR(100) NOT NULL COMMENT 'Nombre del campo',
    show_name VARCHAR(200) COMMENT 'Nombre a mostrar',
    field_type VARCHAR(20) COMMENT 'Tipo de campo (string, int, date, decimal, boolean)',
    field_length INT COMMENT 'Longitud del campo',
    field_decimals INT COMMENT 'Decimales para campos numéricos',
    is_required TINYINT(1) DEFAULT 0 COMMENT 'Si es requerido',
    is_editable TINYINT(1) DEFAULT 1 COMMENT 'Si es editable',
    visible TINYINT(1) DEFAULT 1 COMMENT 'Si es visible',
    default_value_exp VARCHAR(500) COMMENT 'Valor por defecto',
    field_help VARCHAR(1000) COMMENT 'Ayuda del campo',
    grupo VARCHAR(100) COMMENT 'Grupo visual',
    subgrupo VARCHAR(100) COMMENT 'Subgrupo visual',
    class VARCHAR(200) COMMENT 'Clase CSS',
    file_field TINYINT(1) DEFAULT 0 COMMENT 'Si es campo de archivo',
    rich_text TINYINT(1) DEFAULT 0 COMMENT 'Si es texto enriquecido',
    color_field TINYINT(1) DEFAULT 0 COMMENT 'Si es campo color',
    ocultar_vacio TINYINT(1) DEFAULT 0 COMMENT 'Ocultar si está vacío',
    orden INT DEFAULT 0 COMMENT 'Orden de presentación',
    FOREIGN KEY (idquery) REFERENCES re_querys(id) ON DELETE CASCADE,
    UNIQUE KEY uk_query_field (idquery, field_)
) ENGINE=InnoDB COMMENT='Metadatos de campos - Equivalente a sc_fields';

-- Tabla de perfiles de usuario (equivalente a sc_perfiles)
CREATE TABLE re_perfiles (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL UNIQUE COMMENT 'Nombre del perfil',
    descripcion VARCHAR(200) COMMENT 'Descripción del perfil',
    activo TINYINT(1) DEFAULT 1 COMMENT 'Si está activo',
    es_administrador TINYINT(1) DEFAULT 0 COMMENT 'Si tiene permisos de administrador',
    fecha_creacion DATETIME DEFAULT CURRENT_TIMESTAMP,
    fecha_modificacion DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB COMMENT='Perfiles de usuario - Equivalente a sc_perfiles';

-- Datos iniciales de perfiles
INSERT INTO re_perfiles (nombre, descripcion, es_administrador) VALUES 
('Administrador', 'Perfil de administrador del sistema', 1),
('Usuario', 'Perfil de usuario estándar', 0),
('Invitado', 'Perfil de solo lectura', 0);

-- Tabla de usuarios para login (mejora de sc_usuarios)
CREATE TABLE re_usuarios (
    id INT AUTO_INCREMENT PRIMARY KEY,
    idpersona INT COMMENT 'Referencia a re_personas (se crea en PersonasModule)',
    idperfil INT NOT NULL COMMENT 'Referencia al perfil',
    usuario VARCHAR(50) NOT NULL UNIQUE COMMENT 'Nombre de usuario para login',
    password VARCHAR(255) NOT NULL COMMENT 'Password hasheado',
    email VARCHAR(100) COMMENT 'Email del usuario',
    activo TINYINT(1) DEFAULT 1 COMMENT 'Si está activo',
    ultimo_login DATETIME COMMENT 'Fecha del último login',
    intentos_fallidos INT DEFAULT 0 COMMENT 'Intentos de login fallidos',
    bloqueado_hasta DATETIME COMMENT 'Bloqueado hasta esta fecha',
    token_recuperacion VARCHAR(255) COMMENT 'Token para recuperar password',
    fecha_creacion DATETIME DEFAULT CURRENT_TIMESTAMP,
    fecha_modificacion DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (idperfil) REFERENCES re_perfiles(id),
    INDEX idx_usuario (usuario),
    INDEX idx_email (email)
) ENGINE=InnoDB COMMENT='Usuarios del sistema - Mejora de sc_usuarios';

-- Tabla de permisos por perfil y query
CREATE TABLE re_permisos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    idperfil INT NOT NULL COMMENT 'Referencia al perfil',
    idquery INT NOT NULL COMMENT 'Referencia a la consulta',
    puede_ver TINYINT(1) DEFAULT 0 COMMENT 'Puede ver registros',
    puede_insertar TINYINT(1) DEFAULT 0 COMMENT 'Puede insertar',
    puede_actualizar TINYINT(1) DEFAULT 0 COMMENT 'Puede actualizar',
    puede_eliminar TINYINT(1) DEFAULT 0 COMMENT 'Puede eliminar',
    puede_exportar TINYINT(1) DEFAULT 0 COMMENT 'Puede exportar',
    filtro_adicional VARCHAR(500) COMMENT 'Filtro SQL adicional',
    FOREIGN KEY (idperfil) REFERENCES re_perfiles(id) ON DELETE CASCADE,
    FOREIGN KEY (idquery) REFERENCES re_querys(id) ON DELETE CASCADE,
    UNIQUE KEY uk_perfil_query (idperfil, idquery)
) ENGINE=InnoDB COMMENT='Permisos por perfil y consulta';

-- ================================================
-- PERSONAS MODULE - GESTIÓN DE PERSONAS
-- ================================================

-- Tabla principal de personas (mejora de gen_personas)
CREATE TABLE re_personas (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(120) NOT NULL COMMENT 'Nombre de la persona',
    apellido VARCHAR(120) COMMENT 'Apellido de la persona',
    documento VARCHAR(20) COMMENT 'Número de documento',
    tipo_documento VARCHAR(10) DEFAULT 'DNI' COMMENT 'Tipo de documento (DNI, CUIT, etc)',
    email VARCHAR(100) COMMENT 'Email principal',
    telefono VARCHAR(20) COMMENT 'Teléfono principal',
    celular VARCHAR(20) COMMENT 'Teléfono celular',
    direccion VARCHAR(200) COMMENT 'Dirección completa',
    ciudad VARCHAR(100) COMMENT 'Ciudad',
    provincia VARCHAR(100) COMMENT 'Provincia/Estado',
    codigo_postal VARCHAR(10) COMMENT 'Código postal',
    pais VARCHAR(50) DEFAULT 'Argentina' COMMENT 'País',
    fecha_nacimiento DATE COMMENT 'Fecha de nacimiento',
    genero VARCHAR(10) COMMENT 'Género (M/F/Otro)',
    estado_civil VARCHAR(20) COMMENT 'Estado civil',
    profesion VARCHAR(100) COMMENT 'Profesión u ocupación',
    observaciones TEXT COMMENT 'Observaciones generales',
    activo TINYINT(1) DEFAULT 1 COMMENT 'Si está activa',
    fecha_creacion DATETIME DEFAULT CURRENT_TIMESTAMP,
    fecha_modificacion DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_documento (documento),
    INDEX idx_email (email),
    INDEX idx_nombre_apellido (nombre, apellido)
) ENGINE=InnoDB COMMENT='Personas del sistema - Mejora de gen_personas sin boolean fields';

-- Tabla de relación personas-roles (reemplaza boolean fields)
CREATE TABLE re_personas_roles (
    id INT AUTO_INCREMENT PRIMARY KEY,
    idpersona INT NOT NULL COMMENT 'Referencia a la persona',
    idrol INT NOT NULL COMMENT 'Referencia al rol',
    fecha_asignacion DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT 'Cuándo se asignó el rol',
    fecha_vencimiento DATE COMMENT 'Cuándo vence el rol (opcional)',
    activo TINYINT(1) DEFAULT 1 COMMENT 'Si la asignación está activa',
    observaciones VARCHAR(500) COMMENT 'Observaciones de la asignación',
    FOREIGN KEY (idpersona) REFERENCES re_personas(id) ON DELETE CASCADE,
    FOREIGN KEY (idrol) REFERENCES re_roles(id),
    UNIQUE KEY uk_persona_rol (idpersona, idrol)
) ENGINE=InnoDB COMMENT='Relación personas-roles - Reemplaza boolean fields de SC3';

-- ================================================
-- CONFIGURACIÓN INICIAL - METADATOS
-- ================================================

-- Metadatos para re_personas
INSERT INTO re_querys (queryname, table_, description, icon, module_name) 
VALUES ('qrepersonas', 're_personas', 'Gestión de Personas', 'fa-users', 'personas');

-- Metadatos para re_roles  
INSERT INTO re_querys (queryname, table_, description, icon, module_name)
VALUES ('qreroles', 're_roles', 'Catálogo de Roles', 'fa-user-tag', 'core');

-- Metadatos para re_usuarios
INSERT INTO re_querys (queryname, table_, description, icon, module_name)
VALUES ('qreusuarios', 're_usuarios', 'Usuarios del Sistema', 'fa-user-lock', 'core');

-- ================================================
-- ÍNDICES ADICIONALES PARA PERFORMANCE
-- ================================================

-- Índices para re_personas
CREATE INDEX idx_personas_activo ON re_personas(activo);
CREATE INDEX idx_personas_ciudad ON re_personas(ciudad);

-- Índices para re_personas_roles
CREATE INDEX idx_personas_roles_activo ON re_personas_roles(activo);
CREATE INDEX idx_personas_roles_fecha ON re_personas_roles(fecha_asignacion);

-- Índices para re_usuarios
CREATE INDEX idx_usuarios_activo ON re_usuarios(activo);
CREATE INDEX idx_usuarios_ultimo_login ON re_usuarios(ultimo_login);

-- ================================================
-- VISTAS ÚTILES
-- ================================================

-- Vista de personas con sus roles
CREATE VIEW v_personas_completa AS
SELECT 
    p.id,
    p.nombre,
    p.apellido,
    CONCAT(p.nombre, ' ', COALESCE(p.apellido, '')) as nombre_completo,
    p.documento,
    p.email,
    p.telefono,
    p.activo,
    GROUP_CONCAT(r.nombre ORDER BY r.nombre SEPARATOR ', ') as roles,
    p.fecha_creacion
FROM re_personas p
LEFT JOIN re_personas_roles pr ON p.id = pr.idpersona AND pr.activo = 1
LEFT JOIN re_roles r ON pr.idrol = r.id AND r.activo = 1
GROUP BY p.id;

-- Vista de usuarios con datos de persona
CREATE VIEW v_usuarios_completa AS
SELECT 
    u.id,
    u.usuario,
    u.email as email_usuario,
    u.activo,
    u.ultimo_login,
    p.nombre,
    p.apellido,
    CONCAT(p.nombre, ' ', COALESCE(p.apellido, '')) as nombre_completo,
    p.documento,
    p.email as email_persona,
    pf.nombre as perfil
FROM re_usuarios u
LEFT JOIN re_personas p ON u.idpersona = p.id
LEFT JOIN re_perfiles pf ON u.idperfil = pf.id;

-- ================================================
-- COMENTARIOS Y DOCUMENTACIÓN
-- ================================================

/*
DIFERENCIAS PRINCIPALES CON SC3 CORE:

1. ELIMINACIÓN DE BOOLEAN FIELDS:
   - SC3: gen_personas.es_cliente, es_proveedor, es_empleado
   - ReCore: re_personas_roles con relación a re_roles
   
2. MEJORAS EN ESTRUCTURA:
   - Separación clara entre Core y módulos
   - Metadatos más completos
   - Mejor normalización
   - Soporte multi-tenant desde diseño
   
3. CONVENCIONES RECORE:
   - Prefijo re_ para todas las tablas
   - DECIMAL(18,6) para precisión
   - Nombres en español excepto legacy
   - Cada cliente = base de datos separada
   
4. ESCALABILIDAD:
   - Roles dinámicos vs boolean fijos
   - Permisos granulares por consulta
   - Metadatos extensibles
   - Módulos independientes

Este esquema sirve como referencia completa para la implementación
en Spring Boot usando DBUtils con nomenclatura re* equivalente a sc3*.
*/
