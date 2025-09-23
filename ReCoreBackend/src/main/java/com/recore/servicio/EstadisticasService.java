package com.recore.servicio;

import com.recore.dao.UsuarioRepository;
import com.recore.dao.ProductoRepository;
import com.recore.dao.ReservaRepository;
import com.recore.dao.PuntuacionRepository;
import com.recore.dao.CategoriaRepository;
import com.recore.dto.EstadisticasDTO;
import com.recore.modelo.Producto;
import com.recore.modelo.Usuario;
import com.recore.util.RespuestaBase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Servicio para generar estadísticas y reportes para el panel de administración
 */
@Service
@Transactional(readOnly = true)
public class EstadisticasService {

    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    private ProductoRepository productoRepository;
    
    @Autowired
    private ReservaRepository reservaRepository;
    
    @Autowired
    private PuntuacionRepository puntuacionRepository;
    
    @Autowired
    private CategoriaRepository categoriaRepository;

    /**
     * Obtener estadísticas generales del sistema
     */
    public RespuestaBase<EstadisticasDTO> obtenerEstadisticasGenerales() {
        try {
            EstadisticasDTO estadisticas = new EstadisticasDTO();
            
            // Estadísticas generales
            estadisticas.setTotalUsuarios(usuarioRepository.count());
            estadisticas.setTotalProductos(productoRepository.count());
            estadisticas.setTotalReservas(reservaRepository.count());
            estadisticas.setTotalPuntuaciones(puntuacionRepository.count());
            
            // Estadísticas de reservas por estado
            estadisticas.setReservasPendientes(reservaRepository.countByEstadoNombre("Pendiente"));
            estadisticas.setReservasConfirmadas(reservaRepository.countByEstadoNombre("Confirmada"));
            estadisticas.setReservasCanceladas(reservaRepository.countByEstadoNombre("Cancelada"));
            estadisticas.setReservasCompletadas(reservaRepository.countByEstadoNombre("Completada"));
            
            // Productos más reservados (top 5)
            List<Object[]> productosMasReservados = reservaRepository.findProductosMasReservados();
            List<Map<String, Object>> listaProductosMasReservados = new ArrayList<>();
            
            for (Object[] resultado : productosMasReservados) {
                Map<String, Object> item = new HashMap<>();
                Long productoId = (Long) resultado[0];
                Long cantidadReservas = (Long) resultado[1];
                
                Producto producto = productoRepository.findById(productoId).orElse(null);
                if (producto != null) {
                    item.put("id", productoId);
                    item.put("nombre", producto.getNombre());
                    item.put("cantidadReservas", cantidadReservas);
                    listaProductosMasReservados.add(item);
                }
                
                if (listaProductosMasReservados.size() >= 5) {
                    break;
                }
            }
            estadisticas.setProductosMasReservados(listaProductosMasReservados);
            
            // Productos mejor puntuados (top 5)
            List<Object[]> productosMejorPuntuados = puntuacionRepository.findProductosMejorPuntuados();
            List<Map<String, Object>> listaProductosMejorPuntuados = new ArrayList<>();
            
            for (Object[] resultado : productosMejorPuntuados) {
                Map<String, Object> item = new HashMap<>();
                Long productoId = (Long) resultado[0];
                Double promedioPuntuacion = (Double) resultado[1];
                
                Producto producto = productoRepository.findById(productoId).orElse(null);
                if (producto != null) {
                    item.put("id", productoId);
                    item.put("nombre", producto.getNombre());
                    item.put("promedioPuntuacion", Math.round(promedioPuntuacion * 100.0) / 100.0);
                    listaProductosMejorPuntuados.add(item);
                }
                
                if (listaProductosMejorPuntuados.size() >= 5) {
                    break;
                }
            }
            estadisticas.setProductosMejorPuntuados(listaProductosMejorPuntuados);
            
            // Categorías más populares (por cantidad de productos)
            List<Object[]> categoriasMasPopulares = productoRepository.findCategoriasMasPopulares();
            List<Map<String, Object>> listaCategoriasMasPopulares = new ArrayList<>();
            
            for (Object[] resultado : categoriasMasPopulares) {
                Map<String, Object> item = new HashMap<>();
                Long categoriaId = (Long) resultado[0];
                Long cantidadProductos = (Long) resultado[1];
                String categoriaNombre = (String) resultado[2];
                
                item.put("id", categoriaId);
                item.put("nombre", categoriaNombre);
                item.put("cantidadProductos", cantidadProductos);
                listaCategoriasMasPopulares.add(item);
                
                if (listaCategoriasMasPopulares.size() >= 5) {
                    break;
                }
            }
            estadisticas.setCategoriasMasPopulares(listaCategoriasMasPopulares);
            
            // Usuarios más activos (por cantidad de reservas)
            List<Object[]> usuariosMasActivos = reservaRepository.findUsuariosMasActivos();
            List<Map<String, Object>> listaUsuariosMasActivos = new ArrayList<>();
            
            for (Object[] resultado : usuariosMasActivos) {
                Map<String, Object> item = new HashMap<>();
                Long usuarioId = (Long) resultado[0];
                Long cantidadReservas = (Long) resultado[1];
                
                Usuario usuario = usuarioRepository.findById(usuarioId).orElse(null);
                if (usuario != null) {
                    item.put("id", usuarioId);
                    item.put("nombre", usuario.getNombre() + " " + usuario.getApellido());
                    item.put("email", usuario.getEmail());
                    item.put("cantidadReservas", cantidadReservas);
                    listaUsuariosMasActivos.add(item);
                }
                
                if (listaUsuariosMasActivos.size() >= 5) {
                    break;
                }
            }
            estadisticas.setUsuariosMasActivos(listaUsuariosMasActivos);
            
            return new RespuestaBase<>(estadisticas);
        } catch (Exception e) {
            return new RespuestaBase<>(null, "Error al obtener estadísticas: " + e.getMessage());
        }
    }
    
    /**
     * Obtener estadísticas de usuarios
     */
    public RespuestaBase<Map<String, Object>> obtenerEstadisticasUsuarios() {
        try {
            Map<String, Object> estadisticas = new HashMap<>();
            
            // Total de usuarios
            Long totalUsuarios = usuarioRepository.count();
            estadisticas.put("totalUsuarios", totalUsuarios);
            
            // Usuarios administradores
            Long usuariosAdmin = usuarioRepository.countByEsAdmin(true);
            estadisticas.put("usuariosAdmin", usuariosAdmin);
            
            // Usuarios regulares
            Long usuariosRegulares = usuarioRepository.countByEsAdmin(false);
            estadisticas.put("usuariosRegulares", usuariosRegulares);
            
            // Usuarios más activos (por cantidad de reservas)
            List<Object[]> usuariosMasActivos = reservaRepository.findUsuariosMasActivos();
            List<Map<String, Object>> listaUsuariosMasActivos = new ArrayList<>();
            
            for (Object[] resultado : usuariosMasActivos) {
                Map<String, Object> item = new HashMap<>();
                Long usuarioId = (Long) resultado[0];
                Long cantidadReservas = (Long) resultado[1];
                
                Usuario usuario = usuarioRepository.findById(usuarioId).orElse(null);
                if (usuario != null) {
                    item.put("id", usuarioId);
                    item.put("nombre", usuario.getNombre() + " " + usuario.getApellido());
                    item.put("email", usuario.getEmail());
                    item.put("cantidadReservas", cantidadReservas);
                    listaUsuariosMasActivos.add(item);
                }
                
                if (listaUsuariosMasActivos.size() >= 10) {
                    break;
                }
            }
            estadisticas.put("usuariosMasActivos", listaUsuariosMasActivos);
            
            return new RespuestaBase<>(estadisticas);
        } catch (Exception e) {
            return new RespuestaBase<>(null, "Error al obtener estadísticas de usuarios: " + e.getMessage());
        }
    }
    
    /**
     * Obtener estadísticas de productos
     */
    public RespuestaBase<Map<String, Object>> obtenerEstadisticasProductos() {
        try {
            Map<String, Object> estadisticas = new HashMap<>();
            
            // Total de productos
            Long totalProductos = productoRepository.count();
            estadisticas.put("totalProductos", totalProductos);
            
            // Total de categorías
            Long totalCategorias = categoriaRepository.count();
            estadisticas.put("totalCategorias", totalCategorias);
            
            // Productos más reservados (top 10)
            List<Object[]> productosMasReservados = reservaRepository.findProductosMasReservados();
            List<Map<String, Object>> listaProductosMasReservados = new ArrayList<>();
            
            for (Object[] resultado : productosMasReservados) {
                Map<String, Object> item = new HashMap<>();
                Long productoId = (Long) resultado[0];
                Long cantidadReservas = (Long) resultado[1];
                
                Producto producto = productoRepository.findById(productoId).orElse(null);
                if (producto != null) {
                    item.put("id", productoId);
                    item.put("nombre", producto.getNombre());
                    item.put("cantidadReservas", cantidadReservas);
                    listaProductosMasReservados.add(item);
                }
                
                if (listaProductosMasReservados.size() >= 10) {
                    break;
                }
            }
            estadisticas.put("productosMasReservados", listaProductosMasReservados);
            
            // Productos mejor puntuados (top 10)
            List<Object[]> productosMejorPuntuados = puntuacionRepository.findProductosMejorPuntuados();
            List<Map<String, Object>> listaProductosMejorPuntuados = new ArrayList<>();
            
            for (Object[] resultado : productosMejorPuntuados) {
                Map<String, Object> item = new HashMap<>();
                Long productoId = (Long) resultado[0];
                Double promedioPuntuacion = (Double) resultado[1];
                
                Producto producto = productoRepository.findById(productoId).orElse(null);
                if (producto != null) {
                    item.put("id", productoId);
                    item.put("nombre", producto.getNombre());
                    item.put("promedioPuntuacion", Math.round(promedioPuntuacion * 100.0) / 100.0);
                    listaProductosMejorPuntuados.add(item);
                }
                
                if (listaProductosMejorPuntuados.size() >= 10) {
                    break;
                }
            }
            estadisticas.put("productosMejorPuntuados", listaProductosMejorPuntuados);
            
            return new RespuestaBase<>(estadisticas);
        } catch (Exception e) {
            return new RespuestaBase<>(null, "Error al obtener estadísticas de productos: " + e.getMessage());
        }
    }
    
    /**
     * Obtener estadísticas de reservas
     */
    public RespuestaBase<Map<String, Object>> obtenerEstadisticasReservas() {
        try {
            Map<String, Object> estadisticas = new HashMap<>();
            
            // Total de reservas
            Long totalReservas = reservaRepository.count();
            estadisticas.put("totalReservas", totalReservas);
            
            // Reservas por estado
            Long reservasPendientes = reservaRepository.countByEstadoNombre("Pendiente");
            Long reservasConfirmadas = reservaRepository.countByEstadoNombre("Confirmada");
            Long reservasCanceladas = reservaRepository.countByEstadoNombre("Cancelada");
            Long reservasCompletadas = reservaRepository.countByEstadoNombre("Completada");
            
            estadisticas.put("reservasPendientes", reservasPendientes);
            estadisticas.put("reservasConfirmadas", reservasConfirmadas);
            estadisticas.put("reservasCanceladas", reservasCanceladas);
            estadisticas.put("reservasCompletadas", reservasCompletadas);
            
            // Distribución porcentual
            Map<String, Double> distribucionPorcentual = new HashMap<>();
            distribucionPorcentual.put("pendientes", totalReservas > 0 ? (reservasPendientes * 100.0 / totalReservas) : 0);
            distribucionPorcentual.put("confirmadas", totalReservas > 0 ? (reservasConfirmadas * 100.0 / totalReservas) : 0);
            distribucionPorcentual.put("canceladas", totalReservas > 0 ? (reservasCanceladas * 100.0 / totalReservas) : 0);
            distribucionPorcentual.put("completadas", totalReservas > 0 ? (reservasCompletadas * 100.0 / totalReservas) : 0);
            
            estadisticas.put("distribucionPorcentual", distribucionPorcentual);
            
            return new RespuestaBase<>(estadisticas);
        } catch (Exception e) {
            return new RespuestaBase<>(null, "Error al obtener estadísticas de reservas: " + e.getMessage());
        }
    }
}