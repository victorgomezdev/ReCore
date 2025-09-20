package com.recore.servicio;

import com.recore.dao.ImagenRepository;
import com.recore.dao.ProductoRepository;
import com.recore.modelo.Imagen;
import com.recore.modelo.Producto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/*
* Servicio para manejar la lógica de negocio de las imágenes
* Incluye validaciones y operaciones complejas
*/
@Service
@Transactional
public class ImagenService {

	@Autowired
	private ImagenRepository imagenRepository;

	@Autowired
	private ProductoRepository productoRepository;

	// Crear una nueva imagen
	public Imagen crearImagen(Imagen imagen) {
		// Validar que el producto existe
		if (imagen.getProducto() == null || imagen.getProducto().getId() == null) {
			throw new RuntimeException("El producto es requerido para crear una imagen");
		}

		Optional<Producto> producto = productoRepository.findById(imagen.getProducto().getId());
		if (producto.isEmpty()) {
			throw new RuntimeException("El producto especificado no existe");
		}

		// Validar URL
		if (imagen.getUrl() == null || imagen.getUrl().trim().isEmpty()) {
			throw new RuntimeException("La URL de la imagen es requerida");
		}

		// Verificar si ya existe una imagen con la misma URL
		Optional<Imagen> imagenExistente = imagenRepository.findByUrl(imagen.getUrl());
		if (imagenExistente.isPresent()) {
			throw new RuntimeException("Ya existe una imagen con esta URL");
		}

		// Si se marca como principal, desmarcar otras imágenes principales del mismo producto
		if (imagen.getEsPrincipal() != null && imagen.getEsPrincipal()) {
			desmarcarImagenesPrincipales(imagen.getProducto().getId());
		}

		// Si no se especifica orden, asignar el siguiente disponible
		if (imagen.getOrdenVisualizacion() == null || imagen.getOrdenVisualizacion() == 0) {
			Integer maxOrden = imagenRepository.findMaxOrdenVisualizacionByProductoId(imagen.getProducto().getId());
			imagen.setOrdenVisualizacion(maxOrden + 1);
		}

		// Si es la primera imagen del producto, marcarla como principal automáticamente
		long cantidadImagenes = imagenRepository.countByProductoId(imagen.getProducto().getId());
		if (cantidadImagenes == 0) {
			imagen.setEsPrincipal(true);
		}

		return imagenRepository.save(imagen);
	}

	// Actualizar una imagen existente
	public Imagen actualizarImagen(Long id, Imagen imagenActualizada) {
		Optional<Imagen> imagenExistente = imagenRepository.findById(id);
		if (imagenExistente.isEmpty()) {
			throw new RuntimeException("Imagen no encontrada con ID: " + id);
		}

		Imagen imagen = imagenExistente.get();

		// Actualizar campos
		if (imagenActualizada.getUrl() != null) {
			// Verificar que la nueva URL no esté en uso por otra imagen
			Optional<Imagen> otraImagen = imagenRepository.findByUrl(imagenActualizada.getUrl());
			if (otraImagen.isPresent() && !otraImagen.get().getId().equals(id)) {
				throw new RuntimeException("Ya existe otra imagen con esta URL");
			}
			imagen.setUrl(imagenActualizada.getUrl());
		}

		if (imagenActualizada.getAltText() != null) {
			imagen.setAltText(imagenActualizada.getAltText());
		}

		if (imagenActualizada.getOrdenVisualizacion() != null) {
			imagen.setOrdenVisualizacion(imagenActualizada.getOrdenVisualizacion());
		}

		// Manejar cambio de imagen principal
		if (imagenActualizada.getEsPrincipal() != null && imagenActualizada.getEsPrincipal()) {
			desmarcarImagenesPrincipales(imagen.getProducto().getId());
			imagen.setEsPrincipal(true);
		} else if (imagenActualizada.getEsPrincipal() != null && !imagenActualizada.getEsPrincipal()) {
			imagen.setEsPrincipal(false);
		}

		return imagenRepository.save(imagen);
	}

	// Buscar imagen por ID
	public Optional<Imagen> buscarPorId(Long id) {
		return imagenRepository.findById(id);
	}

	// Buscar todas las imágenes de un producto
	public List<Imagen> buscarPorProducto(Long productoId) {
		return imagenRepository.findByProductoIdOrderByOrdenVisualizacionAsc(productoId);
	}

	// Buscar imágenes de un producto con paginación
	public Page<Imagen> buscarPorProductoPaginado(Long productoId, Pageable pageable) {
		return imagenRepository.findByProductoIdOrderByOrdenVisualizacionAsc(productoId, pageable);
	}

	// Buscar imagen principal de un producto
	public Optional<Imagen> buscarImagenPrincipal(Long productoId) {
		return imagenRepository.findByProductoIdAndEsPrincipalTrue(productoId);
	}

	// Buscar todas las imágenes principales
	public List<Imagen> buscarTodasImagenesPrincipales() {
		return imagenRepository.findAllImagenesPrincipales();
	}

	// Buscar imágenes por texto alternativo
	public List<Imagen> buscarPorAltText(String altText) {
		return imagenRepository.findByAltTextContainingIgnoreCase(altText);
	}

	// Eliminar imagen
	public void eliminarImagen(Long id) {
		Optional<Imagen> imagen = imagenRepository.findById(id);
		if (imagen.isEmpty()) {
			throw new RuntimeException("Imagen no encontrada con ID: " + id);
		}

		Imagen imagenAEliminar = imagen.get();
		Long productoId = imagenAEliminar.getProducto().getId();
		boolean eraPrincipal = imagenAEliminar.getEsPrincipal();

		imagenRepository.deleteById(id);

		// Si era la imagen principal, asignar otra como principal
		if (eraPrincipal) {
			asignarNuevaImagenPrincipal(productoId);
		}
	}

	// Eliminar todas las imágenes de un producto
	public void eliminarImagenesDeProducto(Long productoId) {
		imagenRepository.deleteByProductoId(productoId);
	}

	// Establecer imagen como principal
	public Imagen establecerComoPrincipal(Long imagenId) {
		Optional<Imagen> imagen = imagenRepository.findById(imagenId);
		if (imagen.isEmpty()) {
			throw new RuntimeException("Imagen no encontrada con ID: " + imagenId);
		}

		Imagen imagenPrincipal = imagen.get();
		desmarcarImagenesPrincipales(imagenPrincipal.getProducto().getId());
		imagenPrincipal.setEsPrincipal(true);

		return imagenRepository.save(imagenPrincipal);
	}

	// Reordenar imágenes de un producto
	public List<Imagen> reordenarImagenes(Long productoId, List<Long> ordenImagenes) {
		List<Imagen> imagenes = imagenRepository.findByProductoIdOrderByOrdenVisualizacionAsc(productoId);

		for (int i = 0; i < ordenImagenes.size(); i++) {
			Long imagenId = ordenImagenes.get(i);
			Optional<Imagen> imagen = imagenes.stream()
					.filter(img -> img.getId().equals(imagenId))
					.findFirst();

			if (imagen.isPresent()) {
				imagen.get().setOrdenVisualizacion(i + 1);
				imagenRepository.save(imagen.get());
			}
		}

		return imagenRepository.findByProductoIdOrderByOrdenVisualizacionAsc(productoId);
	}

	// Contar imágenes de un producto
	public long contarImagenesDeProducto(Long productoId) {
		return imagenRepository.countByProductoId(productoId);
	}

	// Métodos privados auxiliares

	private void desmarcarImagenesPrincipales(Long productoId) {
		List<Imagen> imagenesPrincipales = imagenRepository.findByProductoIdAndEsPrincipal(productoId, true);
		for (Imagen imagen : imagenesPrincipales) {
			imagen.setEsPrincipal(false);
			imagenRepository.save(imagen);
		}
	}

	private void asignarNuevaImagenPrincipal(Long productoId) {
		List<Imagen> imagenes = imagenRepository.findByProductoIdOrderByOrdenVisualizacionAsc(productoId);
		if (!imagenes.isEmpty()) {
			Imagen primeraImagen = imagenes.get(0);
			primeraImagen.setEsPrincipal(true);
			imagenRepository.save(primeraImagen);
		}
	}
}