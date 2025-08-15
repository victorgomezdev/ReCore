package CatsPrograming.ReCore.config;

import CatsPrograming.ReCore.modules.core.PersonasModule;
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
    private PersonasModule personasModule;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("[ReCore] Inicializando base de datos...");
        
        try {
            // Inicializar módulo de personas (crea tablas y usuario admin)
            personasModule.reUpdateVersionPersonas2025();
            
            System.out.println("[ReCore] Base de datos inicializada correctamente");
            System.out.println("[ReCore] Usuario admin disponible: admin@recore.com / admin123");
            
        } catch (Exception e) {
            System.err.println("[ReCore] Error al inicializar base de datos: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
