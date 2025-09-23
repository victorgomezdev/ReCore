-- Script para actualizar los promedios de puntuaciones de todos los productos existentes

-- Actualizar el promedio de puntuaciones
UPDATE productos p
SET puntuacion_promedio = (
    SELECT COALESCE(AVG(puntuacion), 0)
    FROM puntuaciones
    WHERE idproducto = p.id
);

-- Actualizar el total de puntuaciones
UPDATE productos p
SET total_puntuaciones = (
    SELECT COUNT(*)
    FROM puntuaciones
    WHERE idproducto = p.id
);

-- Mostrar los productos con sus promedios actualizados
SELECT id, nombre, puntuacion_promedio, total_puntuaciones
FROM productos
ORDER BY puntuacion_promedio DESC;