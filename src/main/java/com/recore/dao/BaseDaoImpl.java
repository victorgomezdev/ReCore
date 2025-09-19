package com.recore.dao;

import com.recore.modelo.EntidadBase;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

/*
* Módulo 4 - Clase 11 - ORM
* Implementación base del DAO con operaciones CRUD genéricas
*/
@Transactional
public abstract class BaseDaoImpl<T extends EntidadBase> implements BaseDao<T> {

	protected abstract JpaRepository<T, Long> getRepository();

	@Override
	public T guardar(T entidad) {
		return getRepository().save(entidad);
	}

	@Override
	public Optional<T> buscarPorId(Long id) {
		return getRepository().findById(id);
	}

	@Override
	public Page<T> listarTodos(Pageable paginacion) {
		return getRepository().findAll(paginacion);
	}

	@Override
	public void eliminar(Long id) {
		getRepository().deleteById(id);
	}

	@Override
	public boolean existe(Long id) {
		return getRepository().existsById(id);
	}
}
