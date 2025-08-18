package CatsPrograming.ReCore.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import CatsPrograming.ReCore.modules.core.UsuariosModule;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private UsuariosModule usuariosModule;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {

        int usuarioId = usuariosModule.validarLogin(request.getEmail(), request.getPassword());

        if (usuarioId > 0) {
            // Token simple para prueba inicial
            String token = "token_" + usuarioId + "_" + System.currentTimeMillis();

            LoginResponse response = new LoginResponse();
            response.setSuccess(true);
            response.setToken(token);
            response.setPersonaId(usuarioId); // Ahora es usuarioId
            response.setMessage("Login exitoso");

            return ResponseEntity.ok(response);
        } else {
            ErrorResponse error = new ErrorResponse();
            error.setSuccess(false);
            error.setMessage("Credenciales inválidas");

            return ResponseEntity.badRequest().body(error);
        }
    }

    public static class LoginRequest {
        private String email;
        private String password;

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }

    public static class LoginResponse {
        private boolean success;
        private String token;
        private int personaId;
        private String message;

        public boolean isSuccess() {
            return success;
        }

        public void setSuccess(boolean success) {
            this.success = success;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public int getPersonaId() {
            return personaId;
        }

        public void setPersonaId(int personaId) {
            this.personaId = personaId;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }

    public static class ErrorResponse {
        private boolean success;
        private String message;

        public boolean isSuccess() {
            return success;
        }

        public void setSuccess(boolean success) {
            this.success = success;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        int idusuario = usuariosModule.crearUsuario(request.getEmail(), request.getPassword(), null);
        if (idusuario > 0) {
            RegisterResponse response = new RegisterResponse();
            response.setSuccess(true);
            response.setMessage("Usuario creado con éxito.");
            return ResponseEntity.ok(response);
        } else {
            ErrorResponse error = new ErrorResponse();
            error.setSuccess(false);
            error.setMessage("Error al crear usuario.");
            return ResponseEntity.badRequest().body(error);
        }
    }

    private static class RegisterRequest {
        private String email;
        private String password;

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }

    private static class RegisterResponse {
        private boolean success;
        private String message;

        public boolean isSuccess() {
            return success;
        }

        public void setSuccess(boolean success) {
            this.success = success;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}
