-- Creación de la base de datos
CREATE DATABASE IF NOT EXISTS recore2;

USE recore2;

-- Tabla de estados para reservas
CREATE TABLE
	IF NOT EXISTS estados (
		id INT AUTO_INCREMENT PRIMARY KEY,
		nombre VARCHAR(50) NOT NULL,
		fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP
	);

-- Tabla de usuarios
CREATE TABLE
	IF NOT EXISTS usuarios (
		id INT AUTO_INCREMENT PRIMARY KEY,
		nombre VARCHAR(100) NOT NULL,
		apellido VARCHAR(100) NOT NULL,
		email VARCHAR(100) NOT NULL UNIQUE,
		password VARCHAR(255) NOT NULL,
		es_admin TINYINT DEFAULT 0,
		fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
		fecha_modificacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
	);

-- Tabla de categorías
CREATE TABLE
	IF NOT EXISTS categorias (
		id INT AUTO_INCREMENT PRIMARY KEY,
		titulo VARCHAR(100) NOT NULL,
		descripcion VARCHAR(500),
		imagen VARCHAR(255),
		fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
		fecha_modificacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
	);

-- Tabla de productos
CREATE TABLE
	IF NOT EXISTS productos (
		id INT AUTO_INCREMENT PRIMARY KEY,
		idcategoria INT,
		nombre VARCHAR(100) NOT NULL,
		descripcion VARCHAR(500),
		precio DECIMAL(12, 6),
		puntuacion_promedio DECIMAL(3, 2) DEFAULT 0.0,
		total_puntuaciones INT DEFAULT 0,
		fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
		fecha_modificacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
		FOREIGN KEY (idcategoria) REFERENCES categorias (id) ON DELETE SET NULL
	);

-- Tabla de imágenes de productos
CREATE TABLE
	IF NOT EXISTS imagenes (
		id INT AUTO_INCREMENT PRIMARY KEY,
		idproducto INT NOT NULL,
		url VARCHAR(500) NOT NULL,
		alt_text VARCHAR(255),
		es_principal TINYINT DEFAULT 0,
		orden_visualizacion INT DEFAULT 0,
		fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
		fecha_modificacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
		FOREIGN KEY (idproducto) REFERENCES productos (id) ON DELETE CASCADE
	);

-- Tabla de características
CREATE TABLE
	IF NOT EXISTS caracteristicas (
		id INT AUTO_INCREMENT PRIMARY KEY,
		nombre VARCHAR(100) NOT NULL,
		icono VARCHAR(255),
		fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
		fecha_modificacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
	);

-- Tabla de relación productos-características
CREATE TABLE
	IF NOT EXISTS productos_caracteristicas (
		id INT AUTO_INCREMENT PRIMARY KEY,
		idproducto INT NOT NULL,
		idcaracteristica INT NOT NULL,
		fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
		FOREIGN KEY (idproducto) REFERENCES productos (id) ON DELETE CASCADE,
		FOREIGN KEY (idcaracteristica) REFERENCES caracteristicas (id) ON DELETE CASCADE
	);

-- Tabla de favoritos
CREATE TABLE
	IF NOT EXISTS favoritos (
		id INT AUTO_INCREMENT PRIMARY KEY,
		idusuario INT NOT NULL,
		idproducto INT NOT NULL,
		fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
		fecha_modificacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
		UNIQUE KEY unique_favorito (idusuario, idproducto),
		FOREIGN KEY (idusuario) REFERENCES usuarios (id) ON DELETE CASCADE,
		FOREIGN KEY (idproducto) REFERENCES productos (id) ON DELETE CASCADE
	);

-- Tabla de reservas
CREATE TABLE
	IF NOT EXISTS reservas (
		id INT AUTO_INCREMENT PRIMARY KEY,
		idusuario INT NOT NULL,
		idproducto INT NOT NULL,
		idestado INT NOT NULL,
		fecha_inicio DATE NOT NULL,
		fecha_fin DATE NOT NULL,
		fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
		fecha_modificacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
		FOREIGN KEY (idusuario) REFERENCES usuarios (id) ON DELETE CASCADE,
		FOREIGN KEY (idproducto) REFERENCES productos (id) ON DELETE CASCADE,
		FOREIGN KEY (idestado) REFERENCES estados (id) ON DELETE RESTRICT
	);

-- Tabla de puntuaciones
CREATE TABLE
	IF NOT EXISTS puntuaciones (
		id INT AUTO_INCREMENT PRIMARY KEY,
		idusuario INT NOT NULL,
		idproducto INT NOT NULL,
		puntuacion INT NOT NULL CHECK (
			puntuacion >= 1
			AND puntuacion <= 5
		),
		comentario VARCHAR(500),
		fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
		FOREIGN KEY (idusuario) REFERENCES usuarios (id) ON DELETE CASCADE,
		FOREIGN KEY (idproducto) REFERENCES productos (id) ON DELETE CASCADE
	);

-- Índices para optimización
CREATE INDEX idx_productos_categoria ON productos (idcategoria);

CREATE INDEX idx_reservas_usuario ON reservas (idusuario);

CREATE INDEX idx_reservas_producto ON reservas (idproducto);

CREATE INDEX idx_puntuaciones_producto ON puntuaciones (idproducto);

CREATE INDEX idx_imagenes_producto ON imagenes (idproducto);

CREATE INDEX idx_favoritos_usuario ON favoritos (idusuario);

CREATE INDEX idx_favoritos_producto ON favoritos (idproducto);

-- Inserción de estados iniciales
INSERT INTO
	estados (nombre)
VALUES
	('pendiente'),
	('confirmada'),
	('cancelada'),
	('completada');