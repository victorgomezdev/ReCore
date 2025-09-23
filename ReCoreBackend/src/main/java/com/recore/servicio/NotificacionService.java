package com.recore.servicio;

import com.recore.dao.ReservaRepository;
import com.recore.modelo.Reserva;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

/**
 * Servicio para gestionar notificaciones automáticas
 * Incluye tareas programadas para enviar recordatorios y notificaciones
 */
@Service
public class NotificacionService {

    @Autowired
    private ReservaRepository reservaRepository;
    
    @Autowired
    private EmailService emailService;
    
    /**
     * Enviar recordatorios de reservas que comienzan en los próximos días
     * Se ejecuta todos los días a las 9:00 AM
     */
    @Scheduled(cron = "0 0 9 * * ?")
    @Transactional(readOnly = true)
    public void enviarRecordatoriosReservas() {
        try {
            // Recordatorios para reservas que comienzan en 3 días
            enviarRecordatoriosPorDiasRestantes(3);
            
            // Recordatorios para reservas que comienzan mañana
            enviarRecordatoriosPorDiasRestantes(1);
        } catch (Exception e) {
            System.err.println("Error al enviar recordatorios de reservas: " + e.getMessage());
        }
    }
    
    /**
     * Enviar recordatorios para reservas que comienzan en X días
     */
    private void enviarRecordatoriosPorDiasRestantes(int diasRestantes) {
        try {
            LocalDate fechaObjetivo = LocalDate.now().plusDays(diasRestantes);
            
            // Buscar reservas confirmadas que comienzan en la fecha objetivo
            List<Reserva> reservasProximas = reservaRepository.findReservasProximasAIniciar(fechaObjetivo);
            
            for (Reserva reserva : reservasProximas) {
                try {
                    emailService.enviarRecordatorioReserva(reserva, diasRestantes);
                } catch (Exception e) {
                    System.err.println("Error al enviar recordatorio para reserva ID " + 
                        reserva.getId() + ": " + e.getMessage());
                }
            }
            
            System.out.println("Enviados " + reservasProximas.size() + 
                " recordatorios para reservas que comienzan en " + diasRestantes + " día(s)");
        } catch (Exception e) {
            System.err.println("Error al procesar recordatorios para reservas a " + 
                diasRestantes + " días: " + e.getMessage());
        }
    }
    
    /**
     * Enviar notificaciones de bienvenida a nuevos usuarios
     * Este método puede ser llamado desde el servicio de registro
     */
    @Transactional(readOnly = true)
    public void enviarBienvenidaUsuario(Long usuarioId) {
        try {
            // Implementación pendiente
        } catch (Exception e) {
            System.err.println("Error al enviar bienvenida a usuario: " + e.getMessage());
        }
    }
}