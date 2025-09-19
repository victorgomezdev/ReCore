-- Creación del usuario administrador
INSERT INTO
	usuarios (nombre, apellido, email, password, es_admin)
VALUES
	(
		'Admin',
		'Sistema',
		'admin@recore.com',
		'admin123',
		1
	);

-- Insertar categorías de ejemplo
INSERT INTO
	categorias (titulo, descripcion, imagen)
VALUES
	(
		'Electrónicos',
		'Dispositivos y gadgets electrónicos',
		'electronics.jpg'
	),
	(
		'Herramientas',
		'Herramientas para trabajo y hogar',
		'tools.jpg'
	),
	(
		'Deportes',
		'Equipamiento deportivo',
		'sports.jpg'
	);

-- Insertar características comunes
INSERT INTO
	caracteristicas (nombre, icono)
VALUES
	('WiFi', 'wifi-icon.png'),
	('Bluetooth', 'bluetooth-icon.png'),
	('Portátil', 'portable-icon.png'),
	('Recargable', 'rechargeable-icon.png');

-- Insertar productos de ejemplo
INSERT INTO
	productos (idcategoria, nombre, descripcion, precio)
VALUES
	(
		1,
		'Tablet Pro',
		'Tablet de última generación',
		599.99
	),
	(
		1,
		'Smartwatch X',
		'Reloj inteligente con GPS',
		299.99
	),
	(
		2,
		'Kit de herramientas',
		'Set completo de herramientas',
		149.99
	),
	(
		3,
		'Bicicleta de montaña',
		'Bicicleta todo terreno',
		799.99
	);

-- Asociar productos con características
INSERT INTO
	productos_caracteristicas (idproducto, idcaracteristica)
VALUES
	(1, 1), -- Tablet con WiFi
	(1, 2), -- Tablet con Bluetooth
	(1, 3), -- Tablet Portátil
	(1, 4), -- Tablet Recargable
	(2, 1), -- Smartwatch con WiFi
	(2, 2), -- Smartwatch con Bluetooth
	(2, 4);

-- Smartwatch Recargable
-- Insertar imágenes para los productos
INSERT INTO
	imagenes (idproducto, url, es_principal)
VALUES
	(1, 'tablet-1.jpg', 1),
	(1, 'tablet-2.jpg', 0),
	(2, 'smartwatch-1.jpg', 1),
	(3, 'tools-1.jpg', 1),
	(4, 'bike-1.jpg', 1);