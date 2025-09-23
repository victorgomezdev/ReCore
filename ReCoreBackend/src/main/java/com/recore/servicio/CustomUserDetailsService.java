package com.recore.servicio;

import com.recore.dao.UsuarioRepository;
import com.recore.modelo.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Servicio personalizado para cargar detalles de usuario para Spring Security
 * Implementa UserDetailsService para integrar con el sistema de autenticación
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByEmail(email);
        
        if (usuario == null) {
            throw new UsernameNotFoundException("Usuario no encontrado con email: " + email);
        }
        
        return usuario;
    }
}