# API Documentation - ReCore

## Autenticación

### POST /api/auth/login
Autenticar usuario en el sistema.

**Endpoint**: `POST http://localhost:8080/api/auth/login`

**Headers**:
```
Content-Type: application/json
```

**Body**:
```json
{
  "email": "admin@recore.com",
  "password": "admin123"
}
```

**Respuesta Exitosa (200)**:
```json
{
  "success": true,
  "message": "Login exitoso",
  "token": "jwt_token_here",
  "user": {
    "id": 1,
    "email": "admin@recore.com",
    "name": "Admin"
  }
}
```

**Respuesta Error (401)**:
```json
{
  "success": false,
  "message": "Credenciales inválidas"
}
```

## Testing con Postman

### Colección de Requests
1. **Login Admin**
   - Method: POST
   - URL: http://localhost:8080/api/auth/login
   - Body: JSON con credenciales admin

### Variables de Entorno Sugeridas
- `base_url`: http://localhost:8080
- `admin_email`: admin@recore.com
- `admin_password`: admin123

## Notas
- Todos los endpoints requieren Content-Type: application/json
- Los tokens JWT se implementarán en futuras versiones
- La base de datos H2 se resetea al reiniciar el backend
