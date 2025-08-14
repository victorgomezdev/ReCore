# ReCore - Sistema de Reservas

**ReCore** es una aplicación web para gestión de reservas desarrollada con **Java Spring Boot** (backend) y **React** (frontend).

## 🚀 Características principales

- Sistema de autenticación de usuarios
- Gestión de productos y categorías
- Sistema de reservas con calendario
- Panel de administración
- Búsqueda y filtrado de productos
- Galería de imágenes
- Sistema de favoritos
- Puntuación y reseñas

## 🛠️ Tecnologías

### Frontend
- **React** 19.1.0
- **React Scripts** 5.0.1
- **React Icons** 5.5.0

### Backend  
- **Java Spring Boot**
- **PostgreSQL/MySQL** (Base de datos)

## 📦 Instalación

### Frontend
```bash
npm install
npm start          # Servidor de desarrollo
npm run build     # Build de producción
npm test          # Ejecutar tests
```

### Backend
```bash
# Navegar a la carpeta backend
cd backend
./mvnw spring-boot:run  # Ejecutar aplicación Spring Boot
```

## 📂 Estructura del proyecto

```
ReCore/
├── src/                 # Código fuente React
├── public/              # Archivos públicos
├── backend/             # API Java Spring Boot
├── database/            # Scripts de base de datos
├── docs/                # Documentación del proyecto
└── install/             # Archivos de instalación PHP (legacy)
```

## 🎯 Sprints de desarrollo

El proyecto está organizado en 4 sprints principales:
- **Sprint 1**: Estructura básica y CRUD de productos
- **Sprint 2**: Autenticación y gestión de usuarios  
- **Sprint 3**: Búsquedas, favoritos y políticas
- **Sprint 4**: Sistema completo de reservas

Ver documentación detallada en `docs/DESARROLLO.md`

## 📄 Licencia

ISC License
