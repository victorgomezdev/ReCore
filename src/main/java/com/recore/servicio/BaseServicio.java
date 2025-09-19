package com.recore.servicio;

import com.recore.util.RespuestaBase;
import com.recore.util.RespuestaPaginada;
import org.springframework.data.domain.Pageable;

/*
* Módulo 4 - Clase 10 - Inyección de dependencias
* Servicio base con operaciones CRUD genéricas
*/
public interface BaseServicio<T> {

	RespuestaBase<T> guardar(T entidad);

	RespuestaBase<T> buscarPorId(Long id);

	RespuestaPaginada<T> listarTodos(Pageable paginacion);

	RespuestaBase<Void> eliminar(Long id);

	RespuestaBase<Boolean> existe(Long id);
}
