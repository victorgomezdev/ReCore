package CatsPrograming.ReCore.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
     * Ejecuta una consulta genérica SQL sin retorno
     */
    public int execQuery(String sql, Object... params) {
        try {
            return jdbcTemplate.update(sql, params);
        } catch (Exception e) {
            System.err.println("Error en execQuery: " + e.getMessage());
            throw e;
        }
    }

    /*
     * Retorna un solo registro como Map (o null si no hay resultados)
     */
    public Map<String, Object> getResult(String sql, Object... params) {
        try {
            List<Map<String, Object>> results = jdbcTemplate.queryForList(sql, params);
            return results.isEmpty() ? null : results.get(0);
        } catch (Exception e) {
            System.err.println("Error en getResult: " + e.getMessage());
            return null;
        }
    }

    // Retorna una lista de registros (cada uno como Map)
    public List<Map<String, Object>> getList(String sql, Object... params) {
        try {
            return jdbcTemplate.queryForList(sql, params);
        } catch (Exception e) {
            System.err.println("Error en getList: " + e.getMessage());
            return List.of();
        }
    }

    /**
     * Obtiene un registro por ID desde una tabla dada.
     * 
     * @param nombreTabla Nombre de la tabla.
     * @param id          Valor del ID.
     * @param columnaId   Nombre de la columna del ID (por defecto "id").
     * @return El registro como Map<String, Object>, o null si no existe.
     */
    public Map<String, Object> getById(String nombreTabla, int id, String columnaId) {
        if (columnaId == null || columnaId.isEmpty()) {
            columnaId = "id";
        }
        try {
            String sql = "SELECT * FROM " + nombreTabla + " WHERE " + columnaId + " = ?";
            return getResult(sql, id);
        } catch (Exception e) {
            System.err.println("Error en getById para tabla " + nombreTabla + ": " + e.getMessage());
            return null;
        }
    }

    /**
     * Ejecuta una consulta SQL y retorna el ID generado
     */
    public int insertAndGetID(String sql, Object... params) {
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
            System.err.println("Error en getID: " + e.getMessage());
            return 0;
        }
    }

    /**
     * Obtiene un entero desde una consulta SQL
     */
    public int getEntero(String sql, Object... params) {
        try {
            Integer result = jdbcTemplate.queryForObject(sql, Integer.class, params);
            return result != null ? result : 0;
        } catch (Exception e) {
            System.err.println("Error en obtenerEntero: " + e.getMessage());
            return 0;
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
            Map<String, Object> queryRow = getResult(sqlQuery, nombreTabla);
            if (queryRow == null || !queryRow.containsKey("id")) {
                System.err.println("[ReCore] No se encontró la query para la tabla: " + nombreTabla);
                return;
            }
            int idQuery = ((Number) queryRow.get("id")).intValue();

            // Busca idmenu por nombreMenu
            String sqlMenu = "SELECT id FROM re_menus WHERE nombre = ?";
            Map<String, Object> menuRow = getResult(sqlMenu, nombreMenu);
            int idMenu;
            if (menuRow != null && menuRow.containsKey("id")) {
                idMenu = ((Number) menuRow.get("id")).intValue();
            } else {
                // Crea menú si no existe
                String sqlInsertMenu = "INSERT INTO re_menus (nombre) VALUES (?)";
                idMenu = insertAndGetID(sqlInsertMenu, nombreMenu);
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

    /**
     * Verifica si una tabla existe
     */
    public boolean existeTabla(String nombreTabla) {
        try {
            String sql = "SELECT COUNT(*) FROM INFORMATION_SCHEMA.TABLES WHERE UPPER(TABLE_NAME) = UPPER(?)";
            int count = getEntero(sql, nombreTabla);
            return count > 0;
        } catch (Exception e) {
            System.err.println("Error verificando tabla " + nombreTabla + ": " + e.getMessage());
            return false;
        }
    }

    /**
     * Agrega un campo string a una tabla si no existe
     */
    public void agregarCampoStr(String tabla, String campo, int longitud, String valorDefault) {
        try {
            // Verificar si el campo ya existe
            String checkSql = "SELECT COUNT(*) FROM INFORMATION_SCHEMA.COLUMNS WHERE UPPER(TABLE_NAME) = UPPER(?) AND UPPER(COLUMN_NAME) = UPPER(?)";
            int exists = getEntero(checkSql, tabla, campo);

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

    public void dropFk(String table, String fkName) {
        try {
            System.out.println("Borrando foreign key " + table + "." + fkName + "...");
            String sql = "ALTER TABLE " + table + " DROP CONSTRAINT " + fkName; // Sin "FOREIGN KEY"
            execQuery(sql);
        } catch (Exception e) {
            System.err.println("Error eliminando FK: " + e.getMessage());
        }
    }

    public boolean existeFk(String table, String fkName) {
        try {
            String sql = """
                    SELECT COUNT(*)
                    FROM INFORMATION_SCHEMA.TABLE_CONSTRAINTS
                    WHERE UPPER(TABLE_NAME) = UPPER(?)
                    AND UPPER(CONSTRAINT_NAME) = UPPER(?)
                    AND CONSTRAINT_TYPE = 'FOREIGN KEY'
                    """;
            int count = getEntero(sql, table, fkName);
            return count > 0;
        } catch (Exception e) {
            System.err.println("Error verificando FK " + fkName + " en tabla " + table + ": " + e.getMessage());
            return false;
        }
    }

    /**
     * Sincroniza y actualiza la metadata de los campos de una tabla en
     * re_queries_fields.
     * Elimina campos que ya no existen, agrega nuevos y actualiza el tipo de campo.
     * Si el campo existe, actualiza el tipo; si no, lo inserta.
     */
    public void generateFieldsInfo(String nombreTabla) {
        if (!existeTabla(nombreTabla)) {
            System.err.println("[generateFieldsInfo] La tabla no existe: " + nombreTabla);
            return;
        }

        // Obtener o crear el query ID
        int idQuery = obtenerOCrearQueryId(nombreTabla);
        if (idQuery == 0) {
            System.err.println("[generateFieldsInfo] No se pudo obtener/crear query para: " + nombreTabla);
            return;
        }

        // Obtener campos actuales de la tabla (con tipo)
        String sqlCampos = "SELECT COLUMN_NAME, DATA_TYPE\r\n" + //
                "FROM INFORMATION_SCHEMA.COLUMNS\r\n" + //
                "WHERE UPPER(TABLE_NAME) = UPPER(?)\r\n" + //
                "ORDER BY ORDINAL_POSITION";
        List<Map<String, Object>> camposActuales = getList(sqlCampos, nombreTabla);

        if (camposActuales.isEmpty()) {
            System.err.println("[generateFieldsInfo] No se encontraron campos para: " + nombreTabla);
            return;
        }

        // Obtener campos existentes en re_queries_fields
        String sqlCamposExistentes = "SELECT field FROM re_queries_fields WHERE idquery = ?";
        List<Map<String, Object>> camposExistentes = getList(sqlCamposExistentes, idQuery);

        // Convertir a Sets para facilitar comparación
        Set<String> camposNuevos = camposActuales.stream()
                .map(campo -> (String) campo.get("COLUMN_NAME"))
                .collect(java.util.stream.Collectors.toSet());

        Set<String> camposViejos = camposExistentes.stream()
                .map(campo -> (String) campo.get("field"))
                .collect(java.util.stream.Collectors.toSet());

        // Detectar campos eliminados
        Set<String> camposEliminados = new HashSet<>(camposViejos);
        camposEliminados.removeAll(camposNuevos);

        // Detectar campos agregados
        Set<String> camposAgregados = new HashSet<>(camposNuevos);
        camposAgregados.removeAll(camposViejos);

        // Eliminar campos que ya no existen
        if (!camposEliminados.isEmpty()) {
            String sqlDeleteCampo = "DELETE FROM re_queries_fields WHERE idquery = ? AND field = ?";
            for (String campo : camposEliminados) {
                execQuery(sqlDeleteCampo, idQuery, campo);
                System.out.println("[generateFieldsInfo] Campo eliminado: " + campo);
            }
        }

        // Agregar campos nuevos con tipo
        if (!camposAgregados.isEmpty()) {
            String sqlInsertCampo = "INSERT INTO re_queries_fields (idquery, field, show_name, tipo) VALUES (?, ?, ?, ?)";
            for (String campo : camposAgregados) {
                Map<String, Object> campoInfo = camposActuales.stream()
                        .filter(c -> campo.equals(c.get("COLUMN_NAME")))
                        .findFirst()
                        .orElse(null);
                Object tipoObj = campoInfo != null ? campoInfo.get("DATA_TYPE") : null;
                String tipoCampo = tipoObj != null ? tipoObj.toString() : "";
                execQuery(sqlInsertCampo, idQuery, campo, campo, tipoCampo);
                System.out.println("[generateFieldsInfo] Campo agregado: " + campo + " tipo: " + tipoCampo);
            }
        }

        // Actualizar tipo de campo para los existentes
        String sqlUpdateTipo = "UPDATE re_queries_fields SET tipo = ? WHERE idquery = ? AND field = ?";
        for (String campo : camposNuevos) {
            Map<String, Object> campoInfo = camposActuales.stream()
                    .filter(c -> campo.equals(c.get("COLUMN_NAME")))
                    .findFirst()
                    .orElse(null);
            Object tipoObj = campoInfo != null ? campoInfo.get("DATA_TYPE") : null;
            String tipoCampo = tipoObj != null ? tipoObj.toString() : "";
            execQuery(sqlUpdateTipo, tipoCampo, idQuery, campo);
        }

        System.out.println("[generateFieldsInfo] Metadata actualizada para " + nombreTabla +
                " (+" + camposAgregados.size() + " -" + camposEliminados.size() + ")");
    }

    /**
     * Crea una tabla y automáticamente la registra en re_queries
     * 
     * @param nombreTabla Nombre de la tabla a crear
     * @param sql         SQL de creación de la tabla
     * @return true si se creó exitosamente, false si ya existía o hubo error
     */
    public boolean crearTabla(String nombreTabla, String sql, String menuName) {
        // 1. Verificar que no exista la tabla
        if (existeTabla(nombreTabla)) {
            System.out.println("[crearTabla] La tabla " + nombreTabla + " ya existe");
            return false;
        }

        try {
            // 2. Crear la tabla
            execQuery(sql);
            System.out.println("[crearTabla] Tabla " + nombreTabla + " creada exitosamente");

            // 3. Insertar en re_queries
            insertarEnReQueries(nombreTabla, menuName);

            return true;
        } catch (Exception e) {
            System.err.println("[crearTabla] Error al crear tabla " + nombreTabla + ": " + e.getMessage());
            return false;
        }
    }

    /**
     * Inserta una tabla en re_queries con configuración por defecto
     * 
     * @param nombreTabla Nombre de la tabla
     * @return ID del query creado, 0 si hay error
     */
    private int insertarEnReQueries(String nombreTabla, String menuName) {
        try {
            // Verificar si ya existe un query para esta tabla
            String sqlCheck = "SELECT id FROM re_queries WHERE table_name = ?";
            Integer existingId = getEnteroNullable(sqlCheck, nombreTabla);
            if (existingId != null) {
                System.out.println(
                        "[insertarEnReQueries] Ya existe query para " + nombreTabla + " con ID: " + existingId);
                return existingId;
            }

            // Crear nombre amigable
            String queryName = nombreTabla.replace("re_", "").replace("_", " ");
            queryName = queryName.substring(0, 1).toUpperCase() + queryName.substring(1);

            // Si vino un menu, busca su ID
            int idmenu = 1; // Default menu ID

            if (menuName != null && !menuName.isEmpty()) {
                try {
                    String sqlMenu = "SELECT id FROM re_menus WHERE nombre = ?";
                    Map<String, Object> menuResult = getResult(sqlMenu, menuName);
                    if (menuResult != null && menuResult.containsKey("id")) {
                        idmenu = ((Number) menuResult.get("id")).intValue();
                    } else {
                        // Crear menú si no existe
                        String sqlInsertMenu = "INSERT INTO re_menus (nombre, icon, color, orden, activo) VALUES (?, '📁', '#666666', 99, 1)";
                        idmenu = insertAndGetID(sqlInsertMenu, menuName);
                        System.out.println("[insertarEnReQueries] Menú creado: " + menuName + " (id: " + idmenu + ")");
                    }
                } catch (Exception e) {
                    System.err.println(
                            "[insertarEnReQueries] Error buscando/creando menú, usando default: " + e.getMessage());
                    idmenu = 1;
                }
            }
            String sqlInsert = """
                    INSERT INTO re_queries (idmenu, table_name, query_name, query_description, fields,
                                          can_insert, can_edit, can_delete, debil, icon, checksum)
                    VALUES (?, ?, ?, ?, '*', 1, 1, 1, 0, 'table', 0)
                    """;

            int queryId = insertAndGetID(sqlInsert, idmenu, nombreTabla, queryName,
                    "Query automático para " + nombreTabla);

            System.out.println("[insertarEnReQueries] Query creado para " + nombreTabla + " con ID: " + queryId);
            return queryId;

        } catch (Exception e) {
            System.err.println("[insertarEnReQueries] Error: " + e.getMessage());
            return 0;
        }
    }

    /**
     * Obtiene el ID del query para una tabla, o lo crea si no existe
     * 
     * @param nombreTabla Nombre de la tabla
     * @return ID del query, 0 si hay error
     */
    private int obtenerOCrearQueryId(String nombreTabla) {
        try {
            String sqlCheck = "SELECT id FROM re_queries WHERE table_name = ?";
            Integer existingId = getEnteroNullable(sqlCheck, nombreTabla);
            if (existingId != null) {
                return existingId;
            }

            // Si no existe, crearlo
            return insertarEnReQueries(nombreTabla, "Root");
        } catch (Exception e) {
            System.err.println("[obtenerOCrearQueryId] Error: " + e.getMessage());
            return 0;
        }
    }

    /**
     * Obtiene un entero que puede ser null
     * 
     * @param sql    Query SQL
     * @param params Parámetros
     * @return Integer o null si no hay resultado
     */
    private Integer getEnteroNullable(String sql, Object... params) {
        try {
            return jdbcTemplate.queryForObject(sql, Integer.class, params);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Modifica una columna existente en una tabla
     * 
     * @param nombreTabla     Nombre de la tabla
     * @param nombreColumna   Nombre de la columna
     * @param nuevaDefinicion Nueva definición de la columna (ej: "DECIMAL(12,2) NOT
     *                        NULL")
     */
    public void modificarColumna(String nombreTabla, String nombreColumna, String nuevaDefinicion) {
        try {
            // Verificar que la tabla y columna existan
            if (!existeTabla(nombreTabla)) {
                System.err.println("[modificarColumna] La tabla no existe: " + nombreTabla);
                return;
            }

            String checkColumnSql = "SELECT COUNT(*) FROM INFORMATION_SCHEMA.COLUMNS WHERE UPPER(TABLE_NAME) = UPPER(?) AND UPPER(COLUMN_NAME) = UPPER(?)";
            int columnExists = getEntero(checkColumnSql, nombreTabla, nombreColumna);

            if (columnExists == 0) {
                System.err.println("[modificarColumna] La columna no existe: " + nombreTabla + "." + nombreColumna);
                return;
            }

            // Ejecutar ALTER TABLE
            String sql = "ALTER TABLE " + nombreTabla + " ALTER COLUMN " + nombreColumna + " " + nuevaDefinicion;
            execQuery(sql);
            System.out.println("[modificarColumna] Columna modificada: " + nombreTabla + "." + nombreColumna + " -> "
                    + nuevaDefinicion);

        } catch (Exception e) {
            System.err.println("[modificarColumna] Error modificando columna " + nombreTabla + "." + nombreColumna
                    + ": " + e.getMessage());
        }
    }

    /**
     * Construye un WHERE flexible a partir de una cadena tipo:
     * campo1=valor1, campo2>valor2, campo3 LIKE 'abc%', campo4!=valor4
     * Soporta operadores: =, !=, <, >, <=, >=, LIKE
     */
    public static Map<String, Object> buildWhereClause(String baseSql, String where, String orderBy) {
        StringBuilder sql = new StringBuilder(baseSql);
        List<Object> parametros = new ArrayList<>();

        if (where == null || where.trim().isEmpty()) {
            Map<String, Object> resultado = new HashMap<>();
            resultado.put("sql", sql.toString());
            resultado.put("params", parametros.toArray());
            return resultado;
        }

        String[] condiciones = where.split(",");
        boolean primero = true;
        for (String condicion : condiciones) {
            condicion = condicion.trim();
            if (condicion.isEmpty())
                continue;

            // Buscar operador (orden de mayor a menor longitud)
            String[] operadores = { ">=", "<=", "!=", "=", ">", "<", " LIKE " };
            String campo = null, operador = null, valor = null;
            for (String candidato : operadores) {
                int idx = condicion.indexOf(candidato);
                if (idx > 0) {
                    campo = condicion.substring(0, idx).trim();
                    operador = candidato.trim();
                    valor = condicion.substring(idx + candidato.length()).trim();
                    break;
                }
            }
            if (campo == null || operador == null || valor == null)
                continue;

            // Quitar comillas si es string
            if ((valor.startsWith("'") && valor.endsWith("'")) || (valor.startsWith("\"") && valor.endsWith("\""))) {
                valor = valor.substring(1, valor.length() - 1);
            }
            if (!primero)
                sql.append(" AND ");
            sql.append(campo).append(" ").append(operador).append(" ?");
            parametros.add(valor);
            primero = false;
        }

        // Agregar ORDER BY si se especifica
        if (orderBy != null && !orderBy.trim().isEmpty()) {
            sql.append(" ORDER BY ").append(orderBy);
        }

        Map<String, Object> resultado = new HashMap<>();
        resultado.put("sql", sql.toString());
        resultado.put("params", parametros.toArray());
        return resultado;
    }

    public List<Map<String, Object>> getTabla(String tabla, String where, String orderBy) {
        String baseSql = "SELECT * FROM " + tabla;
        Map<String, Object> whereClause = buildWhereClause(baseSql, where, orderBy);
        String sqlFinal = (String) whereClause.get("sql");
        Object[] params = (Object[]) whereClause.get("params");
        try {
            return this.getList(sqlFinal, params);
        } catch (Exception e) {
            System.err.println("Error en getTabla: " + e.getMessage());
            return List.of();
        }
    }

    public Map<String, Object> getQueryInfo(String tabla) {
        String baseSql = "SELECT * FROM re_queries WHERE table_name = ?";
        try {
            return this.getResult(baseSql, new Object[] { tabla });
        } catch (Exception e) {
            System.err.println("Error en getTablaSingle: " + e.getMessage());
            return null;
        }
    }

    public List<Map<String, Object>> getQueryFields(Integer idquery) {
        String sql = "SELECT * FROM re_queries_fields WHERE idquery = ?";
        try {
            return this.getList(sql, new Object[] { idquery });
        } catch (Exception e) {
            System.err.println("Error en getAllQueries: " + e.getMessage());
            return List.of();
        }
    }
}
