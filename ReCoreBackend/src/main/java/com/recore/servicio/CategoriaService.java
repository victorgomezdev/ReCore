package com.recore.servicio;

import com.recore.modelo.Categoria;
import com.recore.dao.CategoriaRepository;
import com.recore.util.RespuestaBase;
import com.recore.util.RespuestaPaginada;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;

/**
 * Servicio para la gestión de categorías
 */
@Service
@Transactional
public class CategoriaService implements BaseServicio<Categoria> {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Override
    public RespuestaBase<Categoria> guardar(Categoria categoria) {
        try {
            // Verificar si ya existe una categoría con ese título
            Categoria categoriaExistente = categoriaRepository.findByTitulo(categoria.getTitulo());

            // Si estamos creando una nueva categoría
            if (categoria.getId() == null) {
                if (categoriaExistente != null) {
                    return new RespuestaBase<Categoria>(null, "Ya existe una categoría con el título: " + categoria.getTitulo());
                }
            } else {
                // Si estamos actualizando una categoría existente
                if (categoriaExistente != null && !categoriaExistente.getId().equals(categoria.getId())) {
                    return new RespuestaBase<Categoria>(null,
                            "El título " + categoria.getTitulo() + " ya está en uso por otra categoría");
                }
            }

            LocalDateTime now = LocalDateTime.now();
            if (categoria.getId() == null) {
                categoria.setFechaCreacion(now);
            }
            categoria.setFechaModificacion(now);
            return new RespuestaBase<Categoria>(categoriaRepository.save(categoria));
        } catch (Exception e) {
            return new RespuestaBase<Categoria>(null, "Error al guardar la categoría: " + e.getMessage());
        }
    }

    @Override
    public RespuestaBase<Categoria> buscarPorId(Long id) {
        try {
            return new RespuestaBase<Categoria>(categoriaRepository.findById(id).orElse(null));
        } catch (Exception e) {
            return new RespuestaBase<Categoria>(null, "Error al buscar la categoría: " + e.getMessage());
        }
    }

    @Override
    public RespuestaPaginada<Categoria> listarTodos(Pageable paginacion) {
        try {
            Page<Categoria> pagina = categoriaRepository.findAll(paginacion);
            return new RespuestaPaginada<Categoria>(pagina.getContent(), pagina.getTotalElements(),
                    pagina.getNumber(), pagina.getSize());
        } catch (Exception e) {
            return new RespuestaPaginada<Categoria>(null, "Error al listar categorías: " + e.getMessage());
        }
    }

    @Override
    public RespuestaBase<Void> eliminar(Long id) {
        try {
            categoriaRepository.deleteById(id);
            return new RespuestaBase<Void>("Categoría eliminada correctamente", false);
        } catch (Exception e) {
            return new RespuestaBase<Void>("Error al eliminar la categoría: " + e.getMessage(), true);
        }
    }

    @Override
    public RespuestaBase<Boolean> existe(Long id) {
        try {
            return new RespuestaBase<Boolean>(categoriaRepository.existsById(id));
        } catch (Exception e) {
            return new RespuestaBase<Boolean>(false, "Error al verificar existencia: " + e.getMessage());
        }
    }

    public RespuestaBase<Categoria> buscarPorTitulo(String titulo) {
        try {
            return new RespuestaBase<Categoria>(categoriaRepository.findByTitulo(titulo));
        } catch (Exception e) {
            return new RespuestaBase<Categoria>(null, "Error al buscar por título: " + e.getMessage());
        }
    }
}