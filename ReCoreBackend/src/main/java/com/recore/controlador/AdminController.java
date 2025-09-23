package com.recore.controlador;

import com.recore.dto.EstadisticasDTO;
import com.recore.modelo.Usuario;
import com.recore.servicio.EstadisticasService;
import com.recore.servicio.UsuarioService;
import com.recore.servicio.ProductoService;
import com.recore.servicio.ReservaService;
import com.recore.servicio.PuntuacionService;
import com.recore.util.RespuestaBase;
import com.recore.util.RespuestaPaginada;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Controlador para el panel de administración
 * Todos los endpoints requieren rol de ADMIN
 */
@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasRole('ADMIN')")
@CrossOrigin(origins = "*")
public class AdminController {

    @Autowired
    private EstadisticasService estadisticasService;
    
    @Autowired
    private UsuarioService usuarioService;
    
    @Autowired
    private ProductoService productoService;
    
    @Autowired
    private ReservaService reservaService;
    
    @Autowired
    private PuntuacionService puntuacionService;
    
    /**
     * Obtener estadísticas generales del sistema
     */
    @GetMapping("/estadisticas/general")
    public ResponseEntity<RespuestaBase<EstadisticasDTO>> obtenerEstadisticasGenerales() {
        RespuestaBase<EstadisticasDTO> respuesta = estadisticasService.obtenerEstadisticasGenerales();
        
        if (respuesta.isExito()) {
            return ResponseEntity.ok(respuesta);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(respuesta);
        }
    }
    
    /**
     * Obtener estadísticas de usuarios
     */
    @GetMapping("/estadisticas/usuarios")
    public ResponseEntity<RespuestaBase<Map<String, Object>>> obtenerEstadisticasUsuarios() {
        RespuestaBase<Map<String, Object>> respuesta = estadisticasService.obtenerEstadisticasUsuarios();
        
        if (respuesta.isExito()) {
            return ResponseEntity.ok(respuesta);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(respuesta);
        }
    }
    
    /**
     * Obtener estadísticas de productos
     */
    @GetMapping("/estadisticas/productos")
    public ResponseEntity<RespuestaBase<Map<String, Object>>> obtenerEstadisticasProductos() {
        RespuestaBase<Map<String, Object>> respuesta = estadisticasService.obtenerEstadisticasProductos();
        
        if (respuesta.isExito()) {
            return ResponseEntity.ok(respuesta);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(respuesta);
        }
    }
    
    /**
     * Obtener estadísticas de reservas
     */
    @GetMapping("/estadisticas/reservas")
    public ResponseEntity<RespuestaBase<Map<String, Object>>> obtenerEstadisticasReservas() {
        RespuestaBase<Map<String, Object>> respuesta = estadisticasService.obtenerEstadisticasReservas();
        
        if (respuesta.isExito()) {
            return ResponseEntity.ok(respuesta);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(respuesta);
        }
    }
    
    /**
     * Listar todos los usuarios (con paginación)
     */
    @GetMapping("/usuarios")
    public ResponseEntity<RespuestaPaginada<Usuario>> listarUsuarios(
            @RequestParam(defaultValue = "0") int pagina,
            @RequestParam(defaultValue = "10") int tamanio,
            @RequestParam(defaultValue = "id") String ordenarPor,
            @RequestParam(defaultValue = "asc") String direccion) {
        
        Sort.Direction sortDirection = direccion.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable paginacion = PageRequest.of(pagina, tamanio, Sort.by(sortDirection, ordenarPor));
        
        RespuestaPaginada<Usuario> respuesta = usuarioService.listarTodos(paginacion);
        
        if (respuesta.isExito()) {
            return ResponseEntity.ok(respuesta);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(respuesta);
        }
    }
    
    /**
     * Buscar usuario por ID
     */
    @GetMapping("/usuarios/{id}")
    public ResponseEntity<RespuestaBase<Usuario>> buscarUsuarioPorId(@PathVariable Long id) {
        RespuestaBase<Usuario> respuesta = usuarioService.buscarPorId(id);
        
        if (respuesta.isExito() && respuesta.getDatos() != null) {
            return ResponseEntity.ok(respuesta);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
        }
    }
    
    /**
     * Actualizar usuario (incluyendo rol)
     */
    @PutMapping("/usuarios/{id}")
    public ResponseEntity<RespuestaBase<Usuario>> actualizarUsuario(
            @PathVariable Long id, 
            @RequestBody Usuario usuario) {
        
        usuario.setId(id);
        RespuestaBase<Usuario> respuesta = usuarioService.guardar(usuario);
        
        if (respuesta.isExito()) {
            return ResponseEntity.ok(respuesta);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
        }
    }
    
    /**
     * Cambiar rol de usuario (admin o no)
     */
    @PatchMapping("/usuarios/{id}/rol")
    public ResponseEntity<RespuestaBase<Usuario>> cambiarRolUsuario(
            @PathVariable Long id, 
            @RequestParam Boolean esAdmin) {
        
        RespuestaBase<Usuario> respuestaBusqueda = usuarioService.buscarPorId(id);
        
        if (!respuestaBusqueda.isExito() || respuestaBusqueda.getDatos() == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuestaBusqueda);
        }
        
        Usuario usuario = respuestaBusqueda.getDatos();
        usuario.setEsAdmin(esAdmin);
        
        RespuestaBase<Usuario> respuesta = usuarioService.guardar(usuario);
        
        if (respuesta.isExito()) {
            return ResponseEntity.ok(respuesta);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
        }
    }
    
    /**
     * Eliminar usuario
     */
    @DeleteMapping("/usuarios/{id}")
    public ResponseEntity<RespuestaBase<Void>> eliminarUsuario(@PathVariable Long id) {
        RespuestaBase<Void> respuesta = usuarioService.eliminar(id);
        
        if (respuesta.isExito()) {
            return ResponseEntity.ok(respuesta);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
        }
    }
}