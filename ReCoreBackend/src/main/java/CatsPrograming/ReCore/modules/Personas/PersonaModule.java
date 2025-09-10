package CatsPrograming.ReCore.modules.Personas;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import CatsPrograming.ReCore.dao.DBUtils;

/**
 * PersonasModule - SOLO maneja datos personales
 * Los usuarios (credenciales) se manejan en UsuariosModule
 */
@Service
public class PersonaModule {

    @Autowired
    private DBUtils db;

    public PersonaModule() {
        System.out.println("[ReCore] PersonasModule iniciado - Solo maneja datos personales");
    }

    /**
     * Inicializar las tablas SOLO PARA PERSONAS
     */
    public void init() {
        try {
            crearTablaPersonas();
            System.out.println("[ReCore] PersonasModule init completado - Solo tabla personas");
        } catch (Exception e) {
            System.err.println("Error inicializando PersonasModule: " + e.getMessage());
        }
    }

    /**
     * Crea la tabla re_personas SOLO para datos personales
     */
    private void crearTablaPersonas() {
        String sql = """
                CREATE TABLE re_personas (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    nombre VARCHAR(100) NOT NULL,
                    apellido VARCHAR(100) NOT NULL,
                    dni VARCHAR(15) UNIQUE,
                    cuit VARCHAR(15) UNIQUE,
                    email VARCHAR(255),
                    telefono VARCHAR(20),
                    direccion VARCHAR(255),
                    fecha_nacimiento DATE,
                    fecha_registro TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                    notas VARCHAR(255)
                )
                """;

        if (db.crearTabla("re_personas", sql, "Personas")) {
            try {
                // Crear índices por separado (compatible con H2)
                db.execQuery("CREATE INDEX idx_persona_email ON re_personas (email)");
                db.execQuery("CREATE INDEX idx_dni ON re_personas (dni)");
                db.execQuery("CREATE INDEX idx_fecha_registro ON re_personas (fecha_registro)");

                // Generar metadata de campos
                db.generateFieldsInfo("re_personas");
                System.out.println("[ReCore] Tabla re_personas creada (datos personales + email)");
            } catch (Exception e) {
                System.err.println("[ReCore] Error al procesar metadata de re_personas: " + e.getMessage());
            }
        }
    }

    /**
     * Crear nueva persona
     */
    public int crearPersona(String nombre, String apellido, String email, String telefono, String direccion,
            String dni) {
        try {
            String sql = """
                    INSERT INTO re_personas
                    (nombre, apellido, email, telefono, direccion, dni)
                    VALUES
                    (?, ?, ?, ?, ?, ?)
                    """;

            int personaId = db.insertAndGetID(sql, nombre, apellido, email, telefono, direccion, dni);
            System.out.println("[ReCore] Persona creada: " + nombre + " " + apellido + " | ID: " + personaId);
            return personaId;

        } catch (Exception e) {
            System.err.println("Error creando persona: " + e.getMessage());
            return 0;
        }
    }
}
