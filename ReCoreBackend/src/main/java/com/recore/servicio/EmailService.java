package com.recore.servicio;

import com.recore.modelo.Reserva;
import com.recore.modelo.Usuario;
import com.recore.util.RespuestaBase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

import java.util.HashMap;
import java.util.Map;

/**
 * Servicio para el envío de correos electrónicos
 */
@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;
    
    @Autowired
    private TemplateEngine templateEngine;
    
    @Value("${recore.email.from}")
    private String emailFrom;
    
    @Value("${recore.email.from-name}")
    private String emailFromName;
    
    /**
     * Enviar correo electrónico
     */
    public RespuestaBase<Void> enviarEmail(String destinatario, String asunto, String plantilla, Map<String, Object> variables) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            
            helper.setFrom(emailFrom, emailFromName);
            helper.setTo(destinatario);
            helper.setSubject(asunto);
            
            Context context = new Context();
            if (variables != null) {
                variables.forEach(context::setVariable);
            }
            
            String contenido = templateEngine.process(plantilla, context);
            helper.setText(contenido, true);
            
            mailSender.send(message);
            
            return new RespuestaBase<>(null, "Correo enviado correctamente");
        } catch (Exception e) {
            return new RespuestaBase<>(null, "Error al enviar correo: " + e.getMessage());
        }
    }
    
    /**
     * Enviar confirmación de reserva
     */
    public RespuestaBase<Void> enviarConfirmacionReserva(Reserva reserva) {
        try {
            Usuario usuario = reserva.getUsuario();
            String destinatario = usuario.getEmail();
            String asunto = "Confirmación de reserva - ReCore Alquileres";
            String plantilla = "email/confirmacion-reserva";
            
            Map<String, Object> variables = new HashMap<>();
            variables.put("usuario", usuario);
            variables.put("reserva", reserva);
            variables.put("producto", reserva.getProducto());
            
            return enviarEmail(destinatario, asunto, plantilla, variables);
        } catch (Exception e) {
            return new RespuestaBase<>(null, "Error al enviar confirmación de reserva: " + e.getMessage());
        }
    }
    
    /**
     * Enviar notificación de cambio de estado de reserva
     */
    public RespuestaBase<Void> enviarCambioEstadoReserva(Reserva reserva, String estadoAnterior) {
        try {
            Usuario usuario = reserva.getUsuario();
            String destinatario = usuario.getEmail();
            String asunto = "Actualización de reserva - ReCore Alquileres";
            String plantilla = "email/cambio-estado-reserva";
            
            Map<String, Object> variables = new HashMap<>();
            variables.put("usuario", usuario);
            variables.put("reserva", reserva);
            variables.put("producto", reserva.getProducto());
            variables.put("estadoAnterior", estadoAnterior);
            variables.put("estadoNuevo", reserva.getEstado().getNombre());
            
            return enviarEmail(destinatario, asunto, plantilla, variables);
        } catch (Exception e) {
            return new RespuestaBase<>(null, "Error al enviar notificación de cambio de estado: " + e.getMessage());
        }
    }
    
    /**
     * Enviar recordatorio de reserva próxima
     */
    public RespuestaBase<Void> enviarRecordatorioReserva(Reserva reserva, int diasRestantes) {
        try {
            Usuario usuario = reserva.getUsuario();
            String destinatario = usuario.getEmail();
            String asunto = "Recordatorio de reserva - ReCore Alquileres";
            String plantilla = "email/recordatorio-reserva";
            
            Map<String, Object> variables = new HashMap<>();
            variables.put("usuario", usuario);
            variables.put("reserva", reserva);
            variables.put("producto", reserva.getProducto());
            variables.put("diasRestantes", diasRestantes);
            
            return enviarEmail(destinatario, asunto, plantilla, variables);
        } catch (Exception e) {
            return new RespuestaBase<>(null, "Error al enviar recordatorio de reserva: " + e.getMessage());
        }
    }
    
    /**
     * Enviar bienvenida a nuevo usuario
     */
    public RespuestaBase<Void> enviarBienvenidaUsuario(Usuario usuario) {
        try {
            String destinatario = usuario.getEmail();
            String asunto = "Bienvenido a ReCore Alquileres";
            String plantilla = "email/bienvenida-usuario";
            
            Map<String, Object> variables = new HashMap<>();
            variables.put("usuario", usuario);
            
            return enviarEmail(destinatario, asunto, plantilla, variables);
        } catch (Exception e) {
            return new RespuestaBase<>(null, "Error al enviar correo de bienvenida: " + e.getMessage());
        }
    }
}