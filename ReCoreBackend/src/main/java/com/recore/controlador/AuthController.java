package com.recore.controlador;

import com.recore.dao.UsuarioRepository;
import com.recore.dto.AuthResponse;
import com.recore.dto.LoginRequest;
import com.recore.dto.RegisterRequest;
import com.recore.modelo.Usuario;
import com.recore.servicio.EmailService;
import com.recore.util.JwtUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

/**
 * Controlador para autenticación y registro de usuarios
 * Maneja los endpoints de login y register con JWT
 */
@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;
    
    @Autowired
    private EmailService emailService;

    /**
     * Endpoint para login de usuarios
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest) {
        try {
            // Autenticar al usuario
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    loginRequest.getEmail(),
                    loginRequest.getPassword()
                )
            );

            // Obtener el usuario autenticado
            Usuario usuario = (Usuario) authentication.getPrincipal();

            // Generar token JWT
            String token = jwtUtil.generateToken(usuario);

            // Crear respuesta
            AuthResponse authResponse = new AuthResponse(
                token,
                usuario.getId(),
                usuario.getNombre(),
                usuario.getApellido(),
                usuario.getEmail(),
                usuario.getEsAdmin()
            );

            return ResponseEntity.ok(authResponse);

        } catch (AuthenticationException e) {
            return ResponseEntity.badRequest()
                .body("Credenciales inválidas: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                .body("Error interno del servidor: " + e.getMessage());
        }
    }

    /**
     * Endpoint para registro de nuevos usuarios
     */
    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest registerRequest) {
        try {
            // Verificar si el email ya existe
            if (usuarioRepository.findByEmail(registerRequest.getEmail()) != null) {
                return ResponseEntity.badRequest()
                    .body("El email ya está registrado");
            }

            // Crear nuevo usuario
            Usuario nuevoUsuario = new Usuario();
            nuevoUsuario.setNombre(registerRequest.getNombre());
            nuevoUsuario.setApellido(registerRequest.getApellido());
            nuevoUsuario.setEmail(registerRequest.getEmail());
            nuevoUsuario.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
            nuevoUsuario.setEsAdmin(false); // Por defecto no es admin

            // Guardar usuario
            Usuario usuarioGuardado = usuarioRepository.save(nuevoUsuario);
            
            // Enviar email de bienvenida
            try {
                emailService.enviarBienvenidaUsuario(usuarioGuardado);
            } catch (Exception e) {
                // Log del error pero no afecta el flujo principal
                System.err.println("Error al enviar email de bienvenida: " + e.getMessage());
            }

            // Generar token JWT
            String token = jwtUtil.generateToken(usuarioGuardado);

            // Crear respuesta
            AuthResponse authResponse = new AuthResponse(
                token,
                usuarioGuardado.getId(),
                usuarioGuardado.getNombre(),
                usuarioGuardado.getApellido(),
                usuarioGuardado.getEmail(),
                usuarioGuardado.getEsAdmin()
            );

            return ResponseEntity.ok(authResponse);

        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                .body("Error al registrar usuario: " + e.getMessage());
        }
    }

    /**
     * Endpoint para verificar si un token es válido
     */
    @GetMapping("/verify")
    public ResponseEntity<?> verifyToken(@RequestHeader("Authorization") String authHeader) {
        try {
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                String token = authHeader.substring(7);
                String email = jwtUtil.extractUsername(token);
                
                Usuario usuario = usuarioRepository.findByEmail(email);
                if (usuario != null && jwtUtil.validateToken(token, usuario)) {
                    return ResponseEntity.ok("Token válido");
                }
            }
            return ResponseEntity.badRequest().body("Token inválido");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Token inválido: " + e.getMessage());
        }
    }
}