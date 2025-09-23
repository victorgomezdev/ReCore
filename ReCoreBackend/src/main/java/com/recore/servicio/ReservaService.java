package com.recore.servicio;

import com.recore.modelo.Reserva;
import com.recore.modelo.Usuario;
import com.recore.modelo.Producto;
import com.recore.modelo.Estado;
import com.recore.dao.ReservaRepository;
import com.recore.dao.UsuarioRepository;
import com.recore.dao.ProductoRepository;
import com.recore.dao.EstadoRepository;
import com.recore.util.RespuestaBase;
import com.recore.util.RespuestaPaginada;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Servicio para la gestión de reservas
 * Implementa operaciones CRUD y lógica de negocio para reservas
 */
@Service
@Transactional
public class ReservaService implements BaseServicio<Reserva> {

    @Autowired
    private ReservaRepository reservaRepository;
    
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    private ProductoRepository productoRepository;
    
    @Autowired
    private EstadoRepository estadoRepository;
    
    @Autowired
    private EmailService emailService;

    @Override
    public RespuestaBase<Reserva> guardar(Reserva reserva) {
        try {
            // Validaciones básicas
            if (reserva.getFechaInicio() == null || reserva.getFechaFin() == null) {
                return new RespuestaBase<Reserva>(null, "Las fechas de inicio y fin son obligatorias");
            }
            
            if (reserva.getFechaInicio().isAfter(reserva.getFechaFin())) {
                return new RespuestaBase<Reserva>(null, "La fecha de inicio no puede ser posterior a la fecha de fin");
            }
            
            if (reserva.getFechaInicio().isBefore(LocalDate.now())) {
                return new RespuestaBase<Reserva>(null, "La fecha de inicio no puede ser anterior a hoy");
            }

            // Verificar que existan las entidades relacionadas
            if (reserva.getUsuario() == null || reserva.getUsuario().getId() == null) {
                return new RespuestaBase<Reserva>(null, "El usuario es obligatorio");
            }
            
            if (reserva.getProducto() == null || reserva.getProducto().getId() == null) {
                return new RespuestaBase<Reserva>(null, "El producto es obligatorio");
            }

            // Cargar entidades completas
            Optional<Usuario> usuario = usuarioRepository.findById(reserva.getUsuario().getId());
            if (!usuario.isPresent()) {
                return new RespuestaBase<Reserva>(null, "Usuario no encontrado");
            }
            
            Optional<Producto> producto = productoRepository.findById(reserva.getProducto().getId());
            if (!producto.isPresent()) {
                return new RespuestaBase<Reserva>(null, "Producto no encontrado");
            }

            // Si no se especifica estado, asignar "Pendiente" por defecto
            if (reserva.getEstado() == null || reserva.getEstado().getId() == null) {
                Optional<Estado> estadoPendiente = estadoRepository.findByNombre("Pendiente");
                if (estadoPendiente.isPresent()) {
                    reserva.setEstado(estadoPendiente.get());
                } else {
                    return new RespuestaBase<Reserva>(null, "Estado 'Pendiente' no encontrado en el sistema");
                }
            } else {
                Optional<Estado> estado = estadoRepository.findById(reserva.getEstado().getId());
                if (!estado.isPresent()) {
                    return new RespuestaBase<Reserva>(null, "Estado no encontrado");
                }
                reserva.setEstado(estado.get());
            }

            // Verificar disponibilidad del producto en las fechas solicitadas
            if (reserva.getId() == null) { // Solo para nuevas reservas
                RespuestaBase<Boolean> disponibilidad = verificarDisponibilidad(
                    reserva.getProducto().getId(), 
                    reserva.getFechaInicio(), 
                    reserva.getFechaFin()
                );
                
                if (!disponibilidad.isExito() || !disponibilidad.getDatos()) {
                    return new RespuestaBase<Reserva>(null, 
                        disponibilidad.getMensaje() != null ? disponibilidad.getMensaje() : 
                        "El producto no está disponible en las fechas seleccionadas");
                }
            } else {
                // Para actualizaciones, verificar que no haya conflictos con otras reservas
                // excluyendo la reserva actual que se está actualizando
                List<Reserva> reservasConflictivas = reservaRepository.findReservasEnFechas(
                    reserva.getProducto().getId(), 
                    reserva.getFechaInicio(), 
                    reserva.getFechaFin()
                );
                
                // Filtrar para excluir la reserva actual
                boolean hayConflicto = reservasConflictivas.stream()
                    .anyMatch(r -> !r.getId().equals(reserva.getId()) && 
                              "Confirmada".equals(r.getEstado().getNombre()));
                
                if (hayConflicto) {
                    return new RespuestaBase<Reserva>(null, 
                        "No se puede actualizar la reserva porque hay conflictos con otras reservas confirmadas en esas fechas");
                }
            }

            // Asignar entidades cargadas
            reserva.setUsuario(usuario.get());
            reserva.setProducto(producto.get());

            LocalDateTime now = LocalDateTime.now();
            boolean esNuevaReserva = reserva.getId() == null;
            
            if (esNuevaReserva) {
                reserva.setFechaCreacion(now);
            }
            reserva.setFechaModificacion(now);
            
            // Guardar la reserva
            Reserva reservaGuardada = reservaRepository.save(reserva);
            
            // Si es una nueva reserva y está confirmada, enviar email de confirmación
            if (esNuevaReserva && "Confirmada".equals(reservaGuardada.getEstado().getNombre())) {
                try {
                    emailService.enviarConfirmacionReserva(reservaGuardada);
                } catch (Exception e) {
                    // Log del error pero no afecta el flujo principal
                    System.err.println("Error al enviar email de confirmación: " + e.getMessage());
                }
            }
            
            return new RespuestaBase<Reserva>(reservaGuardada);
        } catch (Exception e) {
            return new RespuestaBase<Reserva>(null, "Error al guardar la reserva: " + e.getMessage());
        }
    }

    @Override
    public RespuestaBase<Reserva> buscarPorId(Long id) {
        try {
            Optional<Reserva> reserva = reservaRepository.findById(id);
            if (reserva.isPresent()) {
                return new RespuestaBase<Reserva>(reserva.get());
            } else {
                return new RespuestaBase<Reserva>(null, "Reserva no encontrada con ID: " + id);
            }
        } catch (Exception e) {
            return new RespuestaBase<Reserva>(null, "Error al buscar la reserva: " + e.getMessage());
        }
    }

    @Override
    public RespuestaPaginada<Reserva> listarTodos(Pageable pageable) {
        try {
            Page<Reserva> page = reservaRepository.findAll(pageable);
            return new RespuestaPaginada<Reserva>(page.getContent(), page.getTotalElements(), 
                    page.getNumber(), page.getSize());
        } catch (Exception e) {
            return new RespuestaPaginada<Reserva>(null, "Error al buscar reservas: " + e.getMessage());
        }
    }

    @Override
    public RespuestaBase<Void> eliminar(Long id) {
        try {
            Optional<Reserva> reserva = reservaRepository.findById(id);
            if (reserva.isPresent()) {
                reservaRepository.deleteById(id);
                return new RespuestaBase<Void>(null, "Reserva eliminada correctamente");
            } else {
                return new RespuestaBase<Void>(null, "Reserva no encontrada con ID: " + id);
            }
        } catch (Exception e) {
            return new RespuestaBase<Void>(null, "Error al eliminar la reserva: " + e.getMessage());
        }
    }

    /**
     * Verificar disponibilidad de un producto en fechas específicas
     * Implementa lógica mejorada para verificar conflictos de fechas
     */
    public RespuestaBase<Boolean> verificarDisponibilidad(Long productoId, LocalDate fechaInicio, LocalDate fechaFin) {
        try {
            // Validaciones básicas de fechas
            if (fechaInicio == null || fechaFin == null) {
                return new RespuestaBase<Boolean>(false, "Las fechas de inicio y fin son obligatorias");
            }
            
            if (fechaInicio.isAfter(fechaFin)) {
                return new RespuestaBase<Boolean>(false, "La fecha de inicio no puede ser posterior a la fecha de fin");
            }
            
            if (fechaInicio.isBefore(LocalDate.now())) {
                return new RespuestaBase<Boolean>(false, "La fecha de inicio no puede ser anterior a hoy");
            }
            
            // Verificar que el producto existe
            if (!productoRepository.existsById(productoId)) {
                return new RespuestaBase<Boolean>(false, "El producto no existe");
            }
            
            // Buscar reservas confirmadas que se solapen con las fechas solicitadas
            List<Reserva> reservasConflictivas = reservaRepository.findReservasConflictivas(
                productoId, fechaInicio, fechaFin);
            
            boolean disponible = reservasConflictivas.isEmpty();
            
            if (!disponible) {
                StringBuilder detalle = new StringBuilder();
                detalle.append("Producto no disponible - existe(n) ")
                      .append(reservasConflictivas.size())
                      .append(" reserva(s) confirmada(s) en esas fechas. ");
                
                // Agregar fechas ocupadas para mejor información al usuario
                detalle.append("Fechas ocupadas: ");
                for (int i = 0; i < Math.min(reservasConflictivas.size(), 3); i++) {
                    Reserva r = reservasConflictivas.get(i);
                    detalle.append(r.getFechaInicio()).append(" a ").append(r.getFechaFin());
                    if (i < Math.min(reservasConflictivas.size(), 3) - 1) {
                        detalle.append(", ");
                    }
                }
                
                if (reservasConflictivas.size() > 3) {
                    detalle.append(" y ").append(reservasConflictivas.size() - 3).append(" más");
                }
                
                return new RespuestaBase<Boolean>(false, detalle.toString());
            }
            
            return new RespuestaBase<Boolean>(true, "Producto disponible en las fechas seleccionadas");
        } catch (Exception e) {
            return new RespuestaBase<Boolean>(false, "Error al verificar disponibilidad: " + e.getMessage());
        }
    }

    /**
     * Buscar reservas por usuario
     */
    public RespuestaPaginada<Reserva> buscarPorUsuario(Long usuarioId, Pageable pageable) {
        try {
            Page<Reserva> page = reservaRepository.findByUsuarioId(usuarioId, pageable);
            return new RespuestaPaginada<Reserva>(page.getContent(), page.getTotalElements(), 
                    page.getNumber(), page.getSize());
        } catch (Exception e) {
            return new RespuestaPaginada<Reserva>(null, "Error al buscar reservas del usuario: " + e.getMessage());
        }
    }

    /**
     * Buscar reservas por producto
     */
    public RespuestaPaginada<Reserva> buscarPorProducto(Long productoId, Pageable pageable) {
        try {
            Page<Reserva> page = reservaRepository.findByProductoId(productoId, pageable);
            return new RespuestaPaginada<Reserva>(page.getContent(), page.getTotalElements(), 
                    page.getNumber(), page.getSize());
        } catch (Exception e) {
            return new RespuestaPaginada<Reserva>(null, "Error al buscar reservas del producto: " + e.getMessage());
        }
    }

    /**
     * Confirmar una reserva
     */
    public RespuestaBase<Reserva> confirmarReserva(Long reservaId) {
        try {
            Optional<Reserva> reservaOpt = reservaRepository.findById(reservaId);
            if (!reservaOpt.isPresent()) {
                return new RespuestaBase<Reserva>(null, "Reserva no encontrada");
            }

            Reserva reserva = reservaOpt.get();
            
            // Verificar que la reserva esté en estado "Pendiente"
            if (!"Pendiente".equals(reserva.getEstado().getNombre())) {
                return new RespuestaBase<Reserva>(null, "Solo se pueden confirmar reservas en estado 'Pendiente'");
            }

            // Verificar disponibilidad nuevamente
            RespuestaBase<Boolean> disponibilidad = verificarDisponibilidad(
                reserva.getProducto().getId(), 
                reserva.getFechaInicio(), 
                reserva.getFechaFin()
            );
            
            if (!disponibilidad.isExito() || !disponibilidad.getDatos()) {
                return new RespuestaBase<Reserva>(null, "El producto ya no está disponible en esas fechas");
            }

            // Cambiar estado a "Confirmada"
            Optional<Estado> estadoConfirmada = estadoRepository.findByNombre("Confirmada");
            if (!estadoConfirmada.isPresent()) {
                return new RespuestaBase<Reserva>(null, "Estado 'Confirmada' no encontrado en el sistema");
            }

            // Guardar el estado anterior para notificaciones
            String estadoAnterior = reserva.getEstado().getNombre();
            
            // Actualizar estado
            reserva.setEstado(estadoConfirmada.get());
            reserva.setFechaConfirmacion(LocalDateTime.now());
            reserva.setFechaModificacion(LocalDateTime.now());
            
            // Guardar la reserva
            Reserva reservaGuardada = reservaRepository.save(reserva);
            
            // Enviar email de confirmación
            try {
                emailService.enviarConfirmacionReserva(reservaGuardada);
            } catch (Exception e) {
                // Log del error pero no afecta el flujo principal
                System.err.println("Error al enviar email de confirmación: " + e.getMessage());
            }

            return new RespuestaBase<Reserva>(reservaGuardada);
        } catch (Exception e) {
            return new RespuestaBase<Reserva>(null, "Error al confirmar la reserva: " + e.getMessage());
        }
    }

    /**
     * Cancelar una reserva
     */
    public RespuestaBase<Reserva> cancelarReserva(Long reservaId, String motivo) {
        try {
            Optional<Reserva> reservaOpt = reservaRepository.findById(reservaId);
            if (!reservaOpt.isPresent()) {
                return new RespuestaBase<Reserva>(null, "Reserva no encontrada");
            }

            Reserva reserva = reservaOpt.get();
            
            // Verificar que la reserva no esté ya cancelada
            if ("Cancelada".equals(reserva.getEstado().getNombre())) {
                return new RespuestaBase<Reserva>(null, "La reserva ya está cancelada");
            }
            
            // Verificar que la reserva no esté ya completada
            if ("Completada".equals(reserva.getEstado().getNombre())) {
                return new RespuestaBase<Reserva>(null, "No se puede cancelar una reserva que ya está completada");
            }

            // Cambiar estado a "Cancelada"
            Optional<Estado> estadoCancelada = estadoRepository.findByNombre("Cancelada");
            if (!estadoCancelada.isPresent()) {
                return new RespuestaBase<Reserva>(null, "Estado 'Cancelada' no encontrado en el sistema");
            }
            
            // Guardar el estado anterior para notificaciones
            String estadoAnterior = reserva.getEstado().getNombre();

            reserva.setEstado(estadoCancelada.get());
            reserva.setFechaCancelacion(LocalDateTime.now());
            reserva.setMotivoCancelacion(motivo);
            reserva.setFechaModificacion(LocalDateTime.now());
            
            // Guardar la reserva
            Reserva reservaGuardada = reservaRepository.save(reserva);
            
            // Enviar email de cambio de estado
            try {
                emailService.enviarCambioEstadoReserva(reservaGuardada, estadoAnterior);
            } catch (Exception e) {
                // Log del error pero no afecta el flujo principal
                System.err.println("Error al enviar email de cancelación: " + e.getMessage());
            }

            return new RespuestaBase<Reserva>(reservaGuardada);
        } catch (Exception e) {
            return new RespuestaBase<Reserva>(null, "Error al cancelar la reserva: " + e.getMessage());
        }
    }
    
    /**
     * Completar una reserva (cambiar estado a "Completada")
     */
    public RespuestaBase<Reserva> completarReserva(Long reservaId) {
        try {
            Optional<Reserva> reservaOpt = reservaRepository.findById(reservaId);
            if (!reservaOpt.isPresent()) {
                return new RespuestaBase<Reserva>(null, "Reserva no encontrada");
            }

            Reserva reserva = reservaOpt.get();
            
            // Verificar que la reserva esté en estado "Confirmada"
            if (!"Confirmada".equals(reserva.getEstado().getNombre())) {
                return new RespuestaBase<Reserva>(null, "Solo se pueden completar reservas en estado 'Confirmada'");
            }
            
            // Verificar que la fecha de fin sea anterior o igual a hoy
            if (reserva.getFechaFin().isAfter(LocalDate.now())) {
                return new RespuestaBase<Reserva>(null, 
                    "No se puede completar una reserva antes de su fecha de finalización (" + 
                    reserva.getFechaFin() + ")");
            }

            // Cambiar estado a "Completada"
            Optional<Estado> estadoCompletada = estadoRepository.findByNombre("Completada");
            if (!estadoCompletada.isPresent()) {
                return new RespuestaBase<Reserva>(null, "Estado 'Completada' no encontrado en el sistema");
            }
            
            // Guardar el estado anterior para notificaciones
            String estadoAnterior = reserva.getEstado().getNombre();

            reserva.setEstado(estadoCompletada.get());
            reserva.setFechaModificacion(LocalDateTime.now());
            
            // Guardar la reserva
            Reserva reservaGuardada = reservaRepository.save(reserva);
            
            // Enviar email de cambio de estado
            try {
                emailService.enviarCambioEstadoReserva(reservaGuardada, estadoAnterior);
            } catch (Exception e) {
                // Log del error pero no afecta el flujo principal
                System.err.println("Error al enviar email de completado: " + e.getMessage());
            }

            return new RespuestaBase<Reserva>(reservaGuardada);
        } catch (Exception e) {
            return new RespuestaBase<Reserva>(null, "Error al completar la reserva: " + e.getMessage());
        }
    }

    @Override
    public RespuestaBase<Boolean> existe(Long id) {
        try {
            boolean existe = reservaRepository.existsById(id);
            return new RespuestaBase<Boolean>(existe);
        } catch (Exception e) {
            return new RespuestaBase<Boolean>(null, "Error al verificar existencia de reserva: " + e.getMessage());
        }
    }

    /**
     * Buscar historial de reservas de un usuario
     */
    public RespuestaPaginada<Reserva> buscarHistorialUsuario(Long usuarioId, Pageable pageable) {
        try {
            // Verificar que el usuario existe
            if (!usuarioRepository.existsById(usuarioId)) {
                return new RespuestaPaginada<Reserva>(null, "Usuario no encontrado");
            }
            
            Page<Reserva> page = reservaRepository.findHistorialByUsuario(usuarioId, pageable);
            return new RespuestaPaginada<Reserva>(page.getContent(), page.getTotalElements(), 
                    page.getNumber(), page.getSize());
        } catch (Exception e) {
            return new RespuestaPaginada<Reserva>(null, "Error al buscar historial: " + e.getMessage());
        }
    }
    
    /**
     * Buscar historial de reservas de un usuario con filtros avanzados
     */
    public RespuestaPaginada<Reserva> buscarHistorialUsuarioFiltrado(
            Long usuarioId, 
            LocalDate fechaDesde, 
            LocalDate fechaHasta, 
            List<String> estados,
            Pageable pageable) {
        try {
            // Verificar que el usuario existe
            if (!usuarioRepository.existsById(usuarioId)) {
                return new RespuestaPaginada<Reserva>(null, "Usuario no encontrado");
            }
            
            // Si no se especifican fechas, usar valores por defecto
            if (fechaDesde == null) {
                fechaDesde = LocalDate.now().minusYears(1); // Por defecto, último año
            }
            
            if (fechaHasta == null) {
                fechaHasta = LocalDate.now().plusYears(1); // Por defecto, próximo año
            }
            
            // Si no se especifican estados, usar todos
            if (estados == null || estados.isEmpty()) {
                Page<Reserva> page = reservaRepository.findHistorialFiltradoByUsuario(
                    usuarioId, fechaDesde, fechaHasta, pageable);
                return new RespuestaPaginada<Reserva>(page.getContent(), page.getTotalElements(), 
                        page.getNumber(), page.getSize());
            } else {
                Page<Reserva> page = reservaRepository.findHistorialFiltradoByUsuarioAndEstados(
                    usuarioId, fechaDesde, fechaHasta, estados, pageable);
                return new RespuestaPaginada<Reserva>(page.getContent(), page.getTotalElements(), 
                        page.getNumber(), page.getSize());
            }
        } catch (Exception e) {
            return new RespuestaPaginada<Reserva>(null, "Error al buscar historial filtrado: " + e.getMessage());
        }
    }
    
    /**
     * Buscar reservas por estado
     */
    public RespuestaPaginada<Reserva> buscarPorEstado(String nombreEstado, Pageable pageable) {
        try {
            // Verificar que el estado existe
            Optional<Estado> estado = estadoRepository.findByNombre(nombreEstado);
            if (!estado.isPresent()) {
                return new RespuestaPaginada<Reserva>(null, "Estado '" + nombreEstado + "' no encontrado");
            }
            
            Page<Reserva> page = reservaRepository.findByEstadoNombre(nombreEstado, pageable);
            return new RespuestaPaginada<Reserva>(page.getContent(), page.getTotalElements(), 
                    page.getNumber(), page.getSize());
        } catch (Exception e) {
            return new RespuestaPaginada<Reserva>(null, "Error al buscar reservas por estado: " + e.getMessage());
        }
    }
    
    /**
     * Buscar reservas por usuario y estado
     */
    public RespuestaPaginada<Reserva> buscarPorUsuarioYEstado(Long usuarioId, String nombreEstado, Pageable pageable) {
        try {
            // Verificar que el usuario existe
            if (!usuarioRepository.existsById(usuarioId)) {
                return new RespuestaPaginada<Reserva>(null, "Usuario no encontrado");
            }
            
            // Verificar que el estado existe
            Optional<Estado> estado = estadoRepository.findByNombre(nombreEstado);
            if (!estado.isPresent()) {
                return new RespuestaPaginada<Reserva>(null, "Estado '" + nombreEstado + "' no encontrado");
            }
            
            // Buscar reservas por usuario y estado
            Page<Reserva> page = reservaRepository.findByUsuarioIdAndEstadoNombre(usuarioId, nombreEstado, pageable);
            return new RespuestaPaginada<Reserva>(page.getContent(), page.getTotalElements(), 
                    page.getNumber(), page.getSize());
        } catch (Exception e) {
            return new RespuestaPaginada<Reserva>(null, "Error al buscar reservas por usuario y estado: " + e.getMessage());
        }
    }
    
    /**
     * Verificar si un usuario puede reservar un producto en fechas específicas
     * Realiza validaciones completas de disponibilidad y reglas de negocio
     */
    public RespuestaBase<Boolean> puedeUsuarioReservar(Long usuarioId, Long productoId, LocalDate fechaInicio, LocalDate fechaFin) {
        try {
            // Validar que el usuario existe
            if (!usuarioRepository.existsById(usuarioId)) {
                return new RespuestaBase<Boolean>(false, "El usuario no existe");
            }
            
            // Validar que el producto existe
            if (!productoRepository.existsById(productoId)) {
                return new RespuestaBase<Boolean>(false, "El producto no existe");
            }
            
            // Validar fechas
            if (fechaInicio == null || fechaFin == null) {
                return new RespuestaBase<Boolean>(false, "Las fechas de inicio y fin son obligatorias");
            }
            
            if (fechaInicio.isAfter(fechaFin)) {
                return new RespuestaBase<Boolean>(false, "La fecha de inicio no puede ser posterior a la fecha de fin");
            }
            
            if (fechaInicio.isBefore(LocalDate.now())) {
                return new RespuestaBase<Boolean>(false, "La fecha de inicio no puede ser anterior a hoy");
            }
            
            // Verificar disponibilidad del producto
            RespuestaBase<Boolean> disponibilidad = verificarDisponibilidad(productoId, fechaInicio, fechaFin);
            if (!disponibilidad.isExito() || !disponibilidad.getDatos()) {
                return disponibilidad;
            }
            
            // Verificar si el usuario tiene reservas activas que se solapen con las fechas solicitadas
            List<Reserva> reservasActivas = reservaRepository.findReservasActivasByUsuario(usuarioId);
            boolean tieneReservasSolapadas = reservasActivas.stream()
                .anyMatch(r -> (fechaInicio.isBefore(r.getFechaFin()) || fechaInicio.isEqual(r.getFechaFin())) && 
                              (fechaFin.isAfter(r.getFechaInicio()) || fechaFin.isEqual(r.getFechaInicio())));
            
            if (tieneReservasSolapadas) {
                return new RespuestaBase<Boolean>(false, 
                    "El usuario ya tiene reservas activas que se solapan con las fechas solicitadas");
            }
            
            // Si pasa todas las validaciones, el usuario puede reservar
            return new RespuestaBase<Boolean>(true, "El usuario puede reservar el producto en las fechas seleccionadas");
        } catch (Exception e) {
            return new RespuestaBase<Boolean>(false, "Error al verificar si el usuario puede reservar: " + e.getMessage());
        }
    }
}