package com.recore.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
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
    private JdbcTemplate jdbcTemplate;

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

    // ============================================
    // EJECUCIÓN DE QUERIES
    // ============================================

    /**
     * Ejecuta una consulta SQL y retorna filas afectadas
     * Equivalente a execQuery() de BDObject en SC3 Core
     */
    public int reExecQuery(String sql, Object... parametros) {
        try {
            return jdbcTemplate.update(sql, parametros);
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
    public Long reExecInsert(String sql, Object... parametros) {
        // TODO: Implementar con KeyHolder para obtener ID generado
        reExecQuery(sql, parametros);
        return null; // Por ahora retorna null, mejorar después
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
