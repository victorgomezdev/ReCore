package CatsPrograming.ReCore.modules.core;

import CatsPrograming.ReCore.utils.DBUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * PersonasModule - SOLO maneja datos personales
 * Los usuarios (credenciales) se manejan en UsuariosModule
 */
@Service
public class PersonasModule {

    @Autowired
    private DBUtils db;

    public PersonasModule() {
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
        if (!db.existeTabla("re_personas")) {
            try {
                String sql = """
                        CREATE TABLE re_personas (
                            id INT AUTO_INCREMENT PRIMARY KEY,
                            nombre VARCHAR(100) NOT NULL,
                            apellido VARCHAR(100) NOT NULL,
                            email VARCHAR(255),
                            telefono VARCHAR(20),
                            direccion VARCHAR(255),
                            fecha_nacimiento DATE,
                            dni VARCHAR(20) UNIQUE,
                            fecha_registro TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                            notas VARCHAR(255)
                        )
                        """;
                db.execQuery(sql);
                db.generateFieldsInfo("re_personas", 0);
                
                // Crear índices por separado (compatible con H2)
                db.execQuery("CREATE INDEX idx_persona_email ON re_personas (email)");
                db.execQuery("CREATE INDEX idx_dni ON re_personas (dni)");
                db.execQuery("CREATE INDEX idx_fecha_registro ON re_personas (fecha_registro)");
            } catch (Exception e) {
                System.out.println("[ReCore] Índices ya existen: " + e.getMessage());
            }

            System.out.println("[ReCore] Tabla re_personas creada (datos personales + email)");
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

            int personaId = db.execQueryGetId(sql, nombre, apellido, email, telefono, direccion, dni);
            System.out.println("[ReCore] Persona creada: " + nombre + " " + apellido + " | ID: " + personaId);
            return personaId;

        } catch (Exception e) {
            System.err.println("Error creando persona: " + e.getMessage());
            return 0;
        }
    }

    /**
     * Buscar persona por DNI
     */
    public int buscarPersonaPorDni(String dni) {
        try {
            String sql = "SELECT id FROM re_personas WHERE dni = ?";
            return db.obtenerEntero(sql, dni);
        } catch (Exception e) {
            System.err.println("Error buscando persona: " + e.getMessage());
            return 0;
        }
    }

    /**
     * Verificar si email existe en personas
     */
    public int emailExisteEnPersonas(String email) {
        try {
            String sql = "SELECT COUNT(*) FROM re_personas WHERE email = ?";
            return db.obtenerEntero(sql, email);
        } catch (Exception e) {
            System.err.println("Error verificando email: " + e.getMessage());
            return 0;
        }
    }
}
