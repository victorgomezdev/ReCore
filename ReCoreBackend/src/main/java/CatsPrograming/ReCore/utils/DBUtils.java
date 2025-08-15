package CatsPrograming.ReCore.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Map;

/**
 * Utilidades de base de datos para ReCore
 * Adaptado para funcionar con Spring Boot y H2
 */
@Component
public class DBUtils {

    @Autowired
    public JdbcTemplate jdbcTemplate;
    
    // Context para logs (simplificado)
    private Integer logPersonaId;
    private String logIp;
    private String logApp;
    private String logAccion;

    /**
     * Ejecuta una consulta SQL sin retorno
     */
    public void reExecQuery(String sql, Object... params) {
        try {
            jdbcTemplate.update(sql, params);
        } catch (Exception e) {
            System.err.println("Error en reExecQuery: " + e.getMessage());
            throw e;
        }
    }
    
    /**
     * Ejecuta una consulta SQL y retorna el ID generado
     */
    public int reExecQueryGetId(String sql, Object... params) {
        try {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                for (int i = 0; i < params.length; i++) {
                    ps.setObject(i + 1, params[i]);
                }
                return ps;
            }, keyHolder);
            
            Number key = keyHolder.getKey();
            return key != null ? key.intValue() : 0;
        } catch (Exception e) {
            System.err.println("Error en reExecQueryGetId: " + e.getMessage());
            return 0;
        }
    }
    
    /**
     * Obtiene un entero desde una consulta SQL
     */
    public int reObtenerEntero(String sql, Object... params) {
        try {
            Integer result = jdbcTemplate.queryForObject(sql, Integer.class, params);
            return result != null ? result : 0;
        } catch (Exception e) {
            System.err.println("Error en reObtenerEntero: " + e.getMessage());
            return 0;
        }
    }
    
    /**
     * Verifica si una tabla existe
     */
    public boolean reExisteTabla(String nombreTabla) {
        try {
            String sql = "SELECT COUNT(*) FROM INFORMATION_SCHEMA.TABLES WHERE UPPER(TABLE_NAME) = UPPER(?)";
            int count = reObtenerEntero(sql, nombreTabla);
            return count > 0;
        } catch (Exception e) {
            System.err.println("Error verificando tabla " + nombreTabla + ": " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Agrega un campo string a una tabla si no existe
     */
    public void reAgregarCampoStr(String tabla, String campo, int longitud) {
        reAgregarCampoStr(tabla, campo, longitud, null);
    }
    
    public void reAgregarCampoStr(String tabla, String campo, int longitud, String valorDefault) {
        try {
            // Verificar si el campo ya existe
            String checkSql = "SELECT COUNT(*) FROM INFORMATION_SCHEMA.COLUMNS WHERE UPPER(TABLE_NAME) = UPPER(?) AND UPPER(COLUMN_NAME) = UPPER(?)";
            int exists = reObtenerEntero(checkSql, tabla, campo);
            
            if (exists == 0) {
                String sql = "ALTER TABLE " + tabla + " ADD COLUMN " + campo + " VARCHAR(" + longitud + ")";
                if (valorDefault != null) {
                    sql += " DEFAULT '" + valorDefault + "'";
                }
                reExecQuery(sql);
                System.out.println("[DBUtils] Campo agregado: " + tabla + "." + campo);
            }
        } catch (Exception e) {
            System.err.println("Error agregando campo " + campo + " a " + tabla + ": " + e.getMessage());
        }
    }
    
    // Métodos de contexto de log (simplificados)
    public void setLogContext(Integer personaId, String ip, String app, String accion) {
        this.logPersonaId = personaId;
        this.logIp = ip;
        this.logApp = app;
        this.logAccion = accion;
    }
    
    public void clearLogContext() {
        this.logPersonaId = null;
        this.logIp = null;
        this.logApp = null;
        this.logAccion = null;
    }
    
    /**
     * Método simplificado de log
     */
    public void reLog(String tabla, int registroId, String accion, String valorAnterior, String valorNuevo) {
        try {
            System.out.println(String.format("[ReCore Log] Tabla: %s, ID: %d, Acción: %s, Usuario: %s", 
                tabla, registroId, accion, logPersonaId));
        } catch (Exception e) {
            System.err.println("Error en log: " + e.getMessage());
        }
    }
}
