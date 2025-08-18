# Arquitectura del Proyecto - ReCore

## Estructura General
```
ReCore/
├── documentacion/          # Documentación del proyecto
├── src/                    # Frontend React
├── ReCoreBackend/          # Backend Spring Boot
├── database/               # Scripts SQL
├── docs/                   # Documentación adicional
├── install/                # Archivos legacy
└── public/                 # Archivos estáticos
```

## Backend (Spring Boot)
```
ReCoreBackend/src/main/java/CatsPrograming/ReCore/
├── ReCoreApplication.java                    # Clase principal
├── config/
│   └── DatabaseInitializer.java            # Inicialización BD
├── controllers/
│   └── AuthController.java                 # Endpoints auth
├── modules/core/
│   └── PersonasModule.java                 # Lógica personas
└── utils/
    └── DBUtils.java                         # Utilidades BD
```

## Frontend (React)
```
src/
├── components/
│   └── Navbar/                              # Navegación
├── pages/
│   ├── Home/                                # Página inicio
│   ├── Login/                               # Página login
│   └── Register/                            # Página registro
└── utils/
    └── FormMiddleware.js                    # Utilidades formularios
```

## Base de Datos (H2)
```sql
-- Tablas principales
re_personas          # Usuarios del sistema
re_roles             # Roles disponibles  
re_personas_roles    # Asignación usuario-rol
```

## Flujo de Autenticación
1. Usuario envía credenciales → AuthController
2. AuthController → PersonasModule.validarLogin()
3. PersonasModule verifica con BCrypt
4. Respuesta JSON con resultado

## Seguridad
- **Passwords**: Encriptados con BCrypt
- **CORS**: Configurado para localhost:3000
- **JWT**: Pendiente implementación

## Patrones de Diseño
- **MVC**: Controladores, Servicios, DAOs
- **Modules**: Lógica por módulos (PersonasModule)
- **Utils**: Clases utilitarias reutilizables
