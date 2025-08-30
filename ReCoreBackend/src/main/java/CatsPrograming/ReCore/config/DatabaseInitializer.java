package CatsPrograming.ReCore.config;

import CatsPrograming.ReCore.modules.Personas.PersonaModule;
import CatsPrograming.ReCore.modules.core.QueriesModule;
import CatsPrograming.ReCore.modules.core.UsuarioModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * Inicializador de base de datos para ReCore
 * Se ejecuta automáticamente al iniciar la aplicación
 */
@Component
public class DatabaseInitializer implements CommandLineRunner {

    @Autowired
    private PersonaModule personasModule;

    @Autowired
    private UsuarioModule usuariosModule;

    @Autowired
    private QueriesModule querysModule;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("[ReCore] Inicializando base de datos...");

        try {
            // Inicializar módulos separados
            querysModule.init(); // Crea tablas de gestión de querys y menu
            personasModule.init(); // Solo crea tabla de personas
            usuariosModule.init(); // Crea tablas de usuarios, roles y admin

            System.out.println("[ReCore] Base de datos inicializada correctamente");
            System.out.println("[ReCore] Usuario admin disponible: admin@recore.com / admin123");

        } catch (Exception e) {
            System.err.println("[ReCore] Error al inicializar base de datos: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
