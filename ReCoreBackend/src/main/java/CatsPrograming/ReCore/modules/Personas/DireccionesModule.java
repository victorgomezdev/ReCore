package CatsPrograming.ReCore.modules.Personas;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import CatsPrograming.ReCore.dao.DBUtils;

@Component
public class DireccionesModule {

	@Autowired
	private DBUtils db;

	public void init() {
		System.out.println("[ReCore] Iniciando módulo de direcciones");

		try {
			// Crear tablas de direcciones
			crearTablaDirecciones();
		} catch (Exception e) {
			System.err.println("[ReCore] Error en inicialización de direcciones: " + e.getMessage());
		}
	}

	private void crearTablaDirecciones() {
		if (!db.existeTabla("re_direcciones")) {
			try {
				String sql = """
						CREATE TABLE re_direcciones (
							id INT AUTO_INCREMENT PRIMARY KEY,
							idpersona INT,
							calle VARCHAR(255) NOT NULL,
							numero VARCHAR(10) NOT NULL,
							ciudad VARCHAR(100) NOT NULL,
							provincia VARCHAR(100) NOT NULL,
							codigo_postal VARCHAR(10) NOT NULL,
							geo_referencia VARCHAR(255) NOT NULL
						)
						""";
				db.execQuery(sql);
				db.generateFieldsInfo("re_direcciones", 0);

				// Crear índices por separado (compatible con H2)
				db.execQuery("CREATE INDEX idx_direccion_persona ON re_direcciones (idpersona)");
			} catch (Exception e) {
				System.out.println("[ReCore] Índices ya existen: " + e.getMessage());
			}

			System.out.println("[ReCore] Tabla re_direcciones creada (direcciones)");
		}
	}
}
