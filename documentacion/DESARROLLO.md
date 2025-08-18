# Guía de Desarrollo - ReCore

## Flujo de Trabajo

### Para el Backend
1. **Iniciar IntelliJ** → Abrir proyecto ReCoreBackend
2. **Ejecutar aplicación** → Run ReCoreApplication.java
3. **Verificar logs** → Consola de IntelliJ
4. **Testing** → Postman con endpoints documentados

### Para el Frontend  
1. **Terminal** → `npm run dev`
2. **Desarrollo** → http://localhost:3000
3. **Hot Reload** → Cambios automáticos

## Comandos Útiles

### Maven (Backend)
```bash
# Compilar proyecto
./mvnw clean compile

# Ejecutar tests
./mvnw test

# Generar JAR
./mvnw clean package
```

### NPM (Frontend)
```bash
# Instalar dependencias
npm install

# Desarrollo
npm run dev

# Build producción
npm run build
```

## Testing

### Backend Testing
- **Herramienta**: Postman
- **Base URL**: http://localhost:8080
- **Endpoints**: Ver documentación API.md

### Frontend Testing
- **Manual**: Navegador en localhost:3000
- **Navegación**: Probar botones Login/Register

## Convenciones de Código

### Java (Backend)
- **Packages**: CamelCase
- **Classes**: PascalCase
- **Methods**: camelCase
- **Constants**: UPPER_SNAKE_CASE

### JavaScript (Frontend)
- **Components**: PascalCase (Home.jsx)
- **Functions**: camelCase
- **Constants**: UPPER_SNAKE_CASE
- **Files**: kebab-case o PascalCase

## Debugging

### Backend
- **Logs**: Consola de IntelliJ
- **Breakpoints**: Debugger de IntelliJ
- **H2 Console**: http://localhost:8080/h2-console

### Frontend
- **DevTools**: F12 en navegador
- **Console.log**: Para debugging
- **React DevTools**: Extensión recomendada

## Estructura de Commits
```
feat: Nueva funcionalidad
fix: Corrección de bug
refactor: Refactorización de código
docs: Cambios en documentación
style: Cambios de formato
test: Agregar/modificar tests
```
