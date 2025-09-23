-- Inserción de estados iniciales
INSERT IGNORE INTO estados (nombre, descripcion, activo) VALUES 
('Pendiente', 'Reserva pendiente de confirmación', true),
('Confirmada', 'Reserva confirmada', true),
('Cancelada', 'Reserva cancelada', true),
('Completada', 'Reserva completada', true);

-- Inserción de categorías iniciales
INSERT IGNORE INTO categorias (titulo, descripcion, imagen) VALUES 
('Electrónicos', 'Dispositivos y gadgets electrónicos', 'electronics.jpg'),
('Deportes', 'Artículos deportivos y equipamiento', 'sports.jpg'),
('Hogar', 'Artículos para el hogar y decoración', 'home.jpg'),
('Libros', 'Libros y material de lectura', 'books.jpg');

-- Inserción de características iniciales
INSERT IGNORE INTO caracteristicas (nombre, icono) VALUES 
('WiFi', 'wifi-icon.png'),
('Bluetooth', 'bluetooth-icon.png'),
('Resistente al agua', 'waterproof-icon.png'),
('Batería larga duración', 'battery-icon.png'),
('Pantalla táctil', 'touchscreen-icon.png');

-- Inserción de usuarios iniciales
INSERT IGNORE INTO usuarios (nombre, apellido, email, password, es_admin) VALUES 
('Admin', 'Sistema', 'admin@recore.com', '$2a$10$N9qo8uLOickgx2ZMRZoMye', true),
('Juan', 'Pérez', 'juan@email.com', '$2a$10$N9qo8uLOickgx2ZMRZoMye', false),
('María', 'González', 'maria@email.com', '$2a$10$N9qo8uLOickgx2ZMRZoMye', false);