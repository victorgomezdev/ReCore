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

    /**
     * Ejecuta una consulta SQL sin retorno
     */
    public int execQuery(String sql, Object... params) {
        try {
            return jdbcTemplate.update(sql, params);
        } catch (Exception e) {
            System.err.println("Error en execQuery: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Asocia una query (por nombre de tabla) a un menú (por nombre), creando el
     * menú si no existe.
     * Actualiza el campo idmenu en re_queries.
     * 
     * @param nombreTabla nombre de la tabla asociada a la query (campo tabla en
     *                    re_queries)
     * @param nombreMenu  nombre del menú (campo nombre en re_menus)
     */
    public void agregarQueryAMenu(String nombreTabla, String nombreMenu) {
        try {
            // Busca idquery por nombreTabla
            String sqlQuery = "SELECT id FROM re_queries WHERE tabla = ?";
            Map<String, Object> queryRow = execQueryResult(sqlQuery, nombreTabla);
            if (queryRow == null || !queryRow.containsKey("id")) {
                System.err.println("[ReCore] No se encontró la query para la tabla: " + nombreTabla);
                return;
            }
            int idQuery = ((Number) queryRow.get("id")).intValue();

            // Busca idmenu por nombreMenu
            String sqlMenu = "SELECT id FROM re_menus WHERE nombre = ?";
            Map<String, Object> menuRow = execQueryResult(sqlMenu, nombreMenu);
            int idMenu;
            if (menuRow != null && menuRow.containsKey("id")) {
                idMenu = ((Number) menuRow.get("id")).intValue();
            } else {
                // Crea menú si no existe
                String sqlInsertMenu = "INSERT INTO re_menus (nombre) VALUES (?)";
                idMenu = execQueryGetId(sqlInsertMenu, nombreMenu);
                System.out.println("[ReCore] Menú creado: " + nombreMenu + " (id: " + idMenu + ")");
            }

            // Actualiza idmenu en re_queries
            String sqlUpdate = "UPDATE re_queries SET idmenu = ? WHERE idquery = ?";
            execQuery(sqlUpdate, idMenu, idQuery);
            System.out.println("[ReCore] Query '" + nombreTabla + "' asociada al menú '" + nombreMenu + "'.");
        } catch (Exception e) {
            System.err.println("[ReCore] Error: " + e.getMessage());
        }
    }

    // Retorna un solo registro como Map (o null si no hay resultados)
    public Map<String, Object> execQueryResult(String sql, Object... params) {
        try {
            List<Map<String, Object>> results = jdbcTemplate.queryForList(sql, params);
            return results.isEmpty() ? null : results.get(0);
        } catch (Exception e) {
            System.err.println("Error en execQueryResult: " + e.getMessage());
            return null;
        }
    }

    // Retorna una lista de registros (cada uno como Map)
    public List<Map<String, Object>> execQueryList(String sql, Object... params) {
        try {
            return jdbcTemplate.queryForList(sql, params);
        } catch (Exception e) {
            System.err.println("Error en execQueryList: " + e.getMessage());
            return List.of();
        }
    }

    /**
     * Ejecuta una consulta SQL y retorna el ID generado
     */
    public int execQueryGetId(String sql, Object... params) {
        try {
            KeyHolder keyHolder = new GeneratedKeyHolder();

            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                for (int i = 0; i < params.length; i++) {
                    ps.setObject(i + 1, params[i]);
                }
                return ps;
            }, keyHolder);

            // Manejar el caso donde H2 devuelve múltiples columnas
            Map<String, Object> keys = keyHolder.getKeys();
            if (keys != null && !keys.isEmpty()) {
                // Buscar la primera clave que sea ID o similar
                for (String keyName : keys.keySet()) {
                    if (keyName.equalsIgnoreCase("ID") || keyName.equalsIgnoreCase("id")) {
                        Object idValue = keys.get(keyName);
                        if (idValue instanceof Number) {
                            return ((Number) idValue).intValue();
                        }
                    }
                }
                // Si no encuentra "ID", usar la primera clave numérica
                for (Object value : keys.values()) {
                    if (value instanceof Number) {
                        return ((Number) value).intValue();
                    }
                }
            }

            // Fallback al método original
            Number key = keyHolder.getKey();
            return key != null ? key.intValue() : 0;
        } catch (Exception e) {
            System.err.println("Error en execQueryGetId: " + e.getMessage());
            return 0;
        }
    }

    /**
     * Obtiene un entero desde una consulta SQL
     */
    public int obtenerEntero(String sql, Object... params) {
        try {
            Integer result = jdbcTemplate.queryForObject(sql, Integer.class, params);
            return result != null ? result : 0;
        } catch (Exception e) {
            System.err.println("Error en obtenerEntero: " + e.getMessage());
            return 0;
        }
    }

    /**
     * Verifica si una tabla existe
     */
    public boolean existeTabla(String nombreTabla) {
        try {
            String sql = "SELECT COUNT(*) FROM INFORMATION_SCHEMA.TABLES WHERE UPPER(TABLE_NAME) = UPPER(?)";
            int count = obtenerEntero(sql, nombreTabla);
            return count > 0;
        } catch (Exception e) {
            System.err.println("Error verificando tabla " + nombreTabla + ": " + e.getMessage());
            return false;
        }
    }

    /**
     * Agrega un campo string a una tabla si no existe
     */
    public void agregarCampoStr(String tabla, String campo, int longitud) {
        agregarCampoStr(tabla, campo, longitud, null);
    }

    public void agregarCampoStr(String tabla, String campo, int longitud, String valorDefault) {
        try {
            // Verificar si el campo ya existe
            String checkSql = "SELECT COUNT(*) FROM INFORMATION_SCHEMA.COLUMNS WHERE UPPER(TABLE_NAME) = UPPER(?) AND UPPER(COLUMN_NAME) = UPPER(?)";
            int exists = obtenerEntero(checkSql, tabla, campo);

            if (exists == 0) {
                String sql = "ALTER TABLE " + tabla + " ADD COLUMN " + campo + " VARCHAR(" + longitud + ")";
                if (valorDefault != null) {
                    sql += " DEFAULT '" + valorDefault + "'";
                }
                execQuery(sql);
                System.out.println("[DBUtils] Campo agregado: " + tabla + "." + campo);
            }
        } catch (Exception e) {
            System.err.println("Error agregando campo " + campo + " a " + tabla + ": " + e.getMessage());
        }
    }

    // Métodos de contexto de log (simplificados)
    public void setLogContext(Integer personaId, String ip, String app, String accion) {
        this.logPersonaId = personaId;
        // Los otros parámetros se reciben pero no se almacenan ya que no se usan
    }

    public void clearLogContext() {
        this.logPersonaId = null;
    }

    /**
     * Método simplificado de log
     */
    public void log(String tabla, int registroId, String accion, String valorAnterior, String valorNuevo) {
        try {
            System.out.println(String.format("[ReCore Log] Tabla: %s, ID: %d, Acción: %s, Usuario: %s",
                    tabla, registroId, accion, logPersonaId));
        } catch (Exception e) {
            System.err.println("Error en log: " + e.getMessage());
        }
    }

    /**
     * Crea un Foreign Key entre dos tablas.
     * Le pone un nombre automático: FK_tabla1_tabla2_campo
     */
    public void addForeignKey(String table1, String field1, String table2, String field2, boolean cascade,
            boolean regenerar) {
        String fkName = "FK_" + table1 + "_" + table2 + "_" + field1;
        if (fkName.length() > 45) {
            fkName = fkName.substring(0, 45);
        }
        String sql = "ALTER TABLE " + table1 + " ADD CONSTRAINT " + fkName +
                " FOREIGN KEY (" + field1 + ") REFERENCES " + table2 + " (" + field2 + ")";
        if (cascade) {
            sql += " ON DELETE CASCADE ON UPDATE CASCADE";
        } else {
            sql += " ON DELETE RESTRICT ON UPDATE RESTRICT";
        }

        // Si regenerar, borra la FK si existe
        if (regenerar && existeFk(table1, fkName)) {
            dropFk(table1, fkName);
        }

        // Solo crea si no existe
        if (!existeFk(table1, fkName)) {
            System.out.println("Creando " + fkName + "...");
            execQuery(sql);
        }
    }

    // Borra la foreign key de la tabla
    public void dropFk(String table, String fkName) {
        System.out.println("Borrando foreign key " + table + "." + fkName + "...");
        String sql = "ALTER TABLE " + table + " DROP FOREIGN KEY " + fkName + ";";
        execQuery(sql);
    }

    // Determina si existe un FK en la tabla dada
    public boolean existeFk(String table, String fkName) {
        String sql = "SHOW CREATE TABLE " + table;
        Map<String, Object> result = execQueryResult(sql);
        if (result == null || !result.containsKey("Create Table"))
            return false;
        String sqlCreate = result.get("Create Table").toString();
        if (sqlCreate.contains("CONSTRAINT `" + fkName + "`"))
            return true;
        if (sqlCreate.contains("KEY `" + fkName + "`"))
            return true;
        return false;
    }

    /**
     * Sincroniza la metadata de los campos de una tabla en la tabla
     * re_queries_fields.
     * Requiere el id de la query (idquery) y el nombre de la tabla física.
     * Elimina la metadata previa y la regenera a partir de INFORMATION_SCHEMA.
     */
    public void generateFieldsInfo(String nombreTabla, int idQuery) {
        if (!existeTabla(nombreTabla)) {
            System.err.println("[generateFieldsInfo] La tabla no existe: " + nombreTabla);
            return;
        }

        // Obtener los campos de la tabla
        String sqlCampos = "SELECT COLUMN_NAME FROM INFORMATION_SCHEMA.COLUMNS WHERE UPPER(TABLE_NAME) = UPPER(?) ORDER BY ORDINAL_POSITION";
        List<Map<String, Object>> campos = execQueryList(sqlCampos, nombreTabla);

        if (campos.isEmpty()) {
            System.err.println("[generateFieldsInfo] No se encontraron campos para la tabla: " + nombreTabla);
            return;
        }

        // Eliminar metadata previa de la tabla en re_queries_fields
        String sqlDelete = "DELETE FROM re_queries_fields WHERE idquery = ?";
        execQuery(sqlDelete, idQuery);

        // Insertar metadata básica de cada campo
        String sqlInsert = "INSERT INTO re_queries_fields (idquery, field, show_name, is_required, is_editable, visible) VALUES (?, ?, ?, 0, 1, 1)";
        for (Map<String, Object> campo : campos) {
            String nombreCampo = (String) campo.get("COLUMN_NAME");
            // Por defecto, show_name igual al nombre físico (puedes luego editarlo en UI)
            execQuery(sqlInsert, idQuery, nombreCampo, nombreCampo);
        }
        System.out.println("[generateFieldsInfo] Metadata de campos sincronizada para tabla: " + nombreTabla
                + ", idquery: " + idQuery);
    }
}
