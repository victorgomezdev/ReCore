package com.recore.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.Optional;

/*
* Módulo 4 - Clase 11 - ORM
* DAO base con operaciones CRUD genéricas
*/
public interface BaseDao<T> {

	T guardar(T entidad);

	Optional<T> buscarPorId(Long id);

	Page<T> listarTodos(Pageable paginacion);

	void eliminar(Long id);

	boolean existe(Long id);
}
