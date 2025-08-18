# Changelog - ReCore

Todos los cambios importantes del proyecto se documentan en este archivo.

El formato está basado en [Keep a Changelog](https://keepachangelog.com/es-ES/1.0.0/),
y este proyecto adhiere a [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [Unreleased]

### Pendiente
- Implementación de JWT tokens
- Sistema de registro de usuarios
- Dashboard de administración
- Sistema de roles avanzado

## [0.2.0] - 2025-08-15

### Added
- Backend completo con Spring Boot 3.5.4
- Base de datos H2 configurada
- Sistema de autenticación con BCrypt
- API REST para login
- Usuario administrador por defecto
- Navegación en navbar implementada
- Configuración CORS para desarrollo

### Changed
- Migración completa del backend anterior
- Limpieza de estructura de proyecto
- Organización de archivos SQL en carpeta database

### Fixed
- Problemas de dependencias Maven resueltos
- Configuración de base de datos corregida
- Sintaxis SQL compatible con H2

### Removed
- Backend anterior con problemas
- Archivos temporales eliminados

## [0.1.0] - 2025-08-14

### Added
- Estructura inicial del proyecto
- Frontend React con Vite
- Configuración básica de navegación
- Páginas de Login y Register
- Sistema básico de formularios

### Technical Details
- React 19.1.0
- Vite para desarrollo
- React Router para navegación
- Estructura modular de componentes

## Notas de Versiones

### [0.2.0] - Unificación del Backend
Esta versión marca la unificación completa del backend usando Spring Boot y H2. 
Se eliminó el backend anterior que tenía problemas de configuración y se creó 
uno nuevo desde start.spring.io con todas las dependencias correctas.

**Breaking Changes**: 
- La base de datos cambió de MySQL a H2 para desarrollo
- Los endpoints pueden haber cambiado de estructura

**Migration Guide**:
- Usar nuevas credenciales: admin@recore.com / admin123
- Backend ahora corre en puerto 8080
- H2 console disponible en /h2-console
