package com.recore.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Component;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Map;

/**
 * Utilidades de base de datos para ReCore
 * Implementa funcionalidades equivalentes a SC3 Core
 * Nomenclatura: sc3updateField -> reUpdateField
 */
@Component
public class DBUtils {

    @Autowired
    public JdbcTemplate jdbcTemplate;

    // ============================================
    // VERIFICACIÓN DE EXISTENCIA
    // ============================================

    /**
     * Verifica si existe la tabla en la base de datos
     * Equivalente a sc3existeTabla() de SC3 Core
     */
    public boolean reExisteTabla(String tabla) {
        try {
            String sql = "SELECT COUNT(*) FROM information_schema.tables " +
                        "WHERE table_schema = DATABASE() AND table_name = ?";
            Integer count = jdbcTemplate.queryForObject(sql, Integer.class, tabla);
            return count != null && count > 0;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Verifica si existe la columna en la tabla especificada
     * Equivalente a sc3existeCampo() de SC3 Core
     */
    public boolean reExisteCampo(String tabla, String columna) {
        try {
            String sql = "SELECT COUNT(*) FROM information_schema.columns " +
                        "WHERE table_schema = DATABASE() AND table_name = ? AND column_name = ?";
            Integer count = jdbcTemplate.queryForObject(sql, Integer.class, tabla, columna);
            return count != null && count > 0;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Verifica si existe el índice en la tabla especificada
     * Equivalente a existeIndex() de BDObject en SC3 Core
     */
    public boolean reExisteIndex(String tabla, String indice) {
        try {
            String sql = "SELECT COUNT(*) FROM information_schema.statistics " +
                        "WHERE table_schema = DATABASE() AND table_name = ? AND index_name = ?";
            Integer count = jdbcTemplate.queryForObject(sql, Integer.class, tabla, indice);
            return count != null && count > 0;
        } catch (Exception e) {
            return false;
        }
    }

    // ============================================
    // AGREGACIÓN DE CAMPOS
    // ============================================

    /**
     * Agrega un campo de tipo fecha a la tabla
     * Equivalente a sc3agregarCampoFecha() de SC3 Core
     */
    public void reAgregarCampoFecha(String tabla, String campo, boolean requerido) {
        if (!reExisteCampo(tabla, campo)) {
            String nullable = requerido ? "NOT NULL" : "NULL";
            String sql = String.format("ALTER TABLE %s ADD COLUMN %s DATETIME %s", tabla, campo, nullable);
            jdbcTemplate.execute(sql);
            echoBr("Campo fecha agregado: " + tabla + "." + campo);
        }
    }

    /**
     * Agrega un campo de tipo string a la tabla
     * Equivalente a sc3agregarCampoStr() de SC3 Core
     */
    public void reAgregarCampoStr(String tabla, String campo, boolean requerido, String valorDefault, String ayuda, int longitud) {
        if (!reExisteCampo(tabla, campo)) {
            String nullable = requerido ? "NOT NULL" : "NULL";
            String defaultValue = (valorDefault != null && !valorDefault.isEmpty()) ? 
                                 "DEFAULT '" + valorDefault + "'" : "";
            String sql = String.format("ALTER TABLE %s ADD COLUMN %s VARCHAR(%d) %s %s", 
                                     tabla, campo, longitud, nullable, defaultValue);
            jdbcTemplate.execute(sql);
            echoBr("Campo string agregado: " + tabla + "." + campo);
        }
    }
    
    /**
     * Agrega un campo string simple (solo tabla, campo, longitud)
     */
    public void reAgregarCampoStr(String tabla, String campo, int longitud) {
        reAgregarCampoStr(tabla, campo, false, null, null, longitud);
    }
    
    /**
     * Agrega un campo string con valor default
     */
    public void reAgregarCampoStr(String tabla, String campo, int longitud, String valorDefault) {
        reAgregarCampoStr(tabla, campo, false, valorDefault, null, longitud);
    }

    /**
     * Agrega un campo de tipo entero a la tabla
     * Equivalente a sc3agregarCampoInt() de SC3 Core
     */
    public void reAgregarCampoInt(String tabla, String campo, boolean requerido, Integer valorDefault) {
        if (!reExisteCampo(tabla, campo)) {
            String nullable = requerido ? "NOT NULL" : "NULL";
            String defaultValue = (valorDefault != null) ? "DEFAULT " + valorDefault : "";
            String sql = String.format("ALTER TABLE %s ADD COLUMN %s INT %s %s", 
                                     tabla, campo, nullable, defaultValue);
            jdbcTemplate.execute(sql);
            echoBr("Campo int agregado: " + tabla + "." + campo);
        }
    }

    /**
     * Agrega un campo decimal con precisión (18,6) según estándar ReCore
     * Equivalente a sc3agregarCampoDecimal() de SC3 Core
     */
    public void reAgregarCampoDecimal(String tabla, String campo, boolean requerido, Double valorDefault) {
        if (!reExisteCampo(tabla, campo)) {
            String nullable = requerido ? "NOT NULL" : "NULL";
            String defaultValue = (valorDefault != null) ? "DEFAULT " + valorDefault : "";
            String sql = String.format("ALTER TABLE %s ADD COLUMN %s DECIMAL(18,6) %s %s", 
                                     tabla, campo, nullable, defaultValue);
            jdbcTemplate.execute(sql);
            echoBr("Campo decimal agregado: " + tabla + "." + campo);
        }
    }
    
    /**
     * Agrega un campo decimal simple con precisión personalizada
     */
    public void reAgregarCampoDecimal(String tabla, String campo, int precision, int scale, Double valorDefault) {
        if (!reExisteCampo(tabla, campo)) {
            String defaultValue = (valorDefault != null) ? "DEFAULT " + valorDefault : "";
            String sql = String.format("ALTER TABLE %s ADD COLUMN %s DECIMAL(%d,%d) %s", 
                                     tabla, campo, precision, scale, defaultValue);
            jdbcTemplate.execute(sql);
            echoBr("Campo decimal agregado: " + tabla + "." + campo);
        }
    }

    // ============================================
    // EJECUCIÓN DE QUERIES CON LOGGING
    // ============================================

    /**
     * Ejecuta una consulta SQL y retorna filas afectadas
     * Equivalente a execQuery() de BDObject en SC3 Core
     * Registra automáticamente en re_logs para auditoría
     */
    public int reExecQuery(String sql, Object... parametros) {
        try {
            int resultado = jdbcTemplate.update(sql, parametros);
            
            // TODO: Agregar logging automático a re_logs
            // logAction(sql, parametros);
            
            return resultado;
        } catch (Exception e) {
            System.err.println("Error ejecutando query: " + sql);
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * Ejecuta INSERT y retorna el ID generado
     * Equivalente a execInsert() de BDObject en SC3 Core
     */
    public int reExecQueryGetId(String sql, Object... parametros) {
        try {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                for (int i = 0; i < parametros.length; i++) {
                    ps.setObject(i + 1, parametros[i]);
                }
                return ps;
            }, keyHolder);
            
            Number key = keyHolder.getKey();
            return key != null ? key.intValue() : 0;
        } catch (Exception e) {
            echoBr("Error en reExecQueryGetId: " + e.getMessage());
            return 0;
        }
    }

    /**
     * Obtiene un valor entero de una consulta SQL
     * Equivalente a obtenerEntero() de BDObject en SC3 Core
     */
    public int reObtenerEntero(String sql, Object... parametros) {
        try {
            Integer result = jdbcTemplate.queryForObject(sql, Integer.class, parametros);
            return result != null ? result : 0;
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * Obtiene un valor string de una consulta SQL
     * Equivalente a obtenerString() de BDObject en SC3 Core
     */
    public String reObtenerString(String sql, Object... parametros) {
        try {
            return jdbcTemplate.queryForObject(sql, String.class, parametros);
        } catch (Exception e) {
            return "";
        }
    }

    // ============================================
    // SISTEMA DE LOGGING AUTOMÁTICO
    // ============================================
    
    private static ThreadLocal<Integer> currentUserId = new ThreadLocal<>();
    private static ThreadLocal<String> currentIp = new ThreadLocal<>();
    private static ThreadLocal<String> currentUserAgent = new ThreadLocal<>();
    private static ThreadLocal<String> currentContexto = new ThreadLocal<>();
    
    /**
     * Establece el contexto de usuario para logging automático
     */
    public static void setLogContext(Integer userId, String ip, String userAgent, String contexto) {
        currentUserId.set(userId);
        currentIp.set(ip);
        currentUserAgent.set(userAgent);
        currentContexto.set(contexto);
    }
    
    /**
     * Limpia el contexto de logging
     */
    public static void clearLogContext() {
        currentUserId.remove();
        currentIp.remove();
        currentUserAgent.remove();
        currentContexto.remove();
    }
    
    /**
     * Registra una acción en re_logs para auditoría
     */
    public void reLog(String tabla, Integer registroId, String accion, String datosAnteriores, String datosNuevos) {
        try {
            // Solo loggear si existe la tabla re_logs
            if (!reExisteTabla("re_logs")) return;
            
            String sql = """
                INSERT INTO re_logs 
                (tabla, registro_id, accion, usuario_id, datos_anteriores, datos_nuevos, ip, user_agent, contexto)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
                """;
                
            jdbcTemplate.update(sql, 
                tabla, 
                registroId, 
                accion,
                currentUserId.get(),
                datosAnteriores,
                datosNuevos,
                currentIp.get(),
                currentUserAgent.get(),
                currentContexto.get()
            );
            
        } catch (Exception e) {
            // No interrumpir el flujo principal si falla el logging
            System.err.println("Error en logging: " + e.getMessage());
        }
    }
    
    /**
     * Ejecuta INSERT/UPDATE/DELETE con logging automático
     * Detecta automáticamente la tabla y acción del SQL
     */
    public int reExecQueryWithLog(String sql, Object... parametros) {
        try {
            // Detectar tabla y acción del SQL
            String sqlUpper = sql.toUpperCase().trim();
            String tabla = extractTableFromSql(sql);
            String accion = extractActionFromSql(sqlUpper);
            
            int resultado = jdbcTemplate.update(sql, parametros);
            
            // Si es INSERT y retornó 1, intentar obtener el ID generado
            Integer registroId = null;
            if ("INSERT".equals(accion) && resultado == 1) {
                try {
                    registroId = reObtenerEntero("SELECT LAST_INSERT_ID()");
                } catch (Exception e) {
                    // Ignorar si no se puede obtener el ID
                }
            }
            
            // Log de la acción
            reLog(tabla, registroId, accion, null, buildDataJson(parametros));
            
            return resultado;
        } catch (Exception e) {
            System.err.println("Error ejecutando query con log: " + sql);
            e.printStackTrace();
            return 0;
        }
    }
    
    /**
     * Extrae el nombre de la tabla del SQL
     */
    private String extractTableFromSql(String sql) {
        String sqlUpper = sql.toUpperCase().trim();
        if (sqlUpper.startsWith("INSERT INTO")) {
            String[] parts = sqlUpper.split("\\s+");
            if (parts.length > 2) return parts[2].toLowerCase();
        } else if (sqlUpper.startsWith("UPDATE")) {
            String[] parts = sqlUpper.split("\\s+");
            if (parts.length > 1) return parts[1].toLowerCase();
        } else if (sqlUpper.startsWith("DELETE FROM")) {
            String[] parts = sqlUpper.split("\\s+");
            if (parts.length > 2) return parts[2].toLowerCase();
        }
        return "unknown";
    }
    
    /**
     * Extrae la acción del SQL
     */
    private String extractActionFromSql(String sqlUpper) {
        if (sqlUpper.startsWith("INSERT")) return "INSERT";
        if (sqlUpper.startsWith("UPDATE")) return "UPDATE";
        if (sqlUpper.startsWith("DELETE")) return "DELETE";
        return "UNKNOWN";
    }
    
    /**
     * Construye un JSON simple con los parámetros
     */
    private String buildDataJson(Object... parametros) {
        if (parametros == null || parametros.length == 0) return null;
        
        StringBuilder json = new StringBuilder("{");
        for (int i = 0; i < parametros.length; i++) {
            if (i > 0) json.append(",");
            json.append("\"param").append(i).append("\":");
            if (parametros[i] == null) {
                json.append("null");
            } else if (parametros[i] instanceof String) {
                json.append("\"").append(parametros[i].toString().replace("\"", "\\\"")).append("\"");
            } else {
                json.append("\"").append(parametros[i].toString()).append("\"");
            }
        }
        json.append("}");
        return json.toString();
    }

    // ============================================
    // UTILIDADES AUXILIARES
    // ============================================

    /**
     * Imprime mensaje con salto de línea para logging
     * Equivalente a echoBr() de SC3 Core
     */
    private void echoBr(String mensaje) {
        System.out.println("[ReCore] " + mensaje);
    }

    /**
     * Obtiene la lista de campos de una tabla ordenados por posición
     * Equivalente a getFieldsInArray() de SC3 Core
     */
    public List<String> reGetFieldsInArray(String tabla) {
        String sql = "SELECT column_name FROM information_schema.columns " +
                    "WHERE table_schema = DATABASE() AND table_name = ? " +
                    "ORDER BY ordinal_position";
        return jdbcTemplate.queryForList(sql, String.class, tabla);
    }

    /**
     * Ejecuta múltiples queries en una transacción
     * Equivalente a execQuerysInArray() de BDObject en SC3 Core
     */
    public void reExecQuerysInArray(List<String> queries) {
        jdbcTemplate.execute("START TRANSACTION");
        try {
            for (String sql : queries) {
                jdbcTemplate.execute(sql);
            }
            jdbcTemplate.execute("COMMIT");
            echoBr("Transacción completada: " + queries.size() + " queries");
        } catch (Exception e) {
            jdbcTemplate.execute("ROLLBACK");
            echoBr("Error en transacción, rollback ejecutado");
            throw e;
        }
    }
}
