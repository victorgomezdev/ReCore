# Guía de Configuración - ReCore

## Prerrequisitos
- **Java 17 o superior**
- **Node.js 18 o superior**
- **IntelliJ IDEA** (recomendado para backend)
- **Postman** (para testing de APIs)

## Instalación

### Backend (Spring Boot)
1. Abrir IntelliJ IDEA
2. File → Open → Seleccionar carpeta `ReCoreBackend`
3. Esperar sincronización de Maven
4. Verificar configuración de Java (File → Project Structure)
5. Ejecutar `ReCoreApplication.java`

### Frontend (React + Vite)
```bash
# En la raíz del proyecto
npm install
npm run dev
```

### Base de Datos H2
- **Consola H2**: http://localhost:8080/h2-console
- **JDBC URL**: jdbc:h2:mem:recore_dev
- **Usuario**: SA
- **Password**: (vacío)

## Configuración de Desarrollo
- Puerto Backend: 8080
- Puerto Frontend: 3000
- Base de datos: H2 en memoria (se resetea al reiniciar)
- CORS habilitado para localhost:3000

## Problemas Comunes
- Si Maven no sincroniza: File → Reload Maven Projects
- Si H2 no conecta: Verificar que el backend esté corriendo
- Si CORS falla: Verificar configuración en application.properties
