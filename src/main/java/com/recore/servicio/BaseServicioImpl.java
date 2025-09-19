package com.recore.servicio;

import com.recore.dao.BaseDao;
import com.recore.modelo.EntidadBase;
import com.recore.util.RespuestaBase;
import com.recore.util.RespuestaPaginada;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

/*
* Módulo 4 - Clase 10 - Inyección de dependencias
* Implementación base del servicio con operaciones CRUD genéricas
*/
@Transactional
public abstract class BaseServicioImpl<T extends EntidadBase> implements BaseServicio<T> {

	protected abstract BaseDao<T> getDao();

	@Override
	public RespuestaBase<T> guardar(T entidad) {
		RespuestaBase<T> respuesta = new RespuestaBase<>();
		try {
			T entidadGuardada = getDao().guardar(entidad);
			respuesta.setExito(true);
			respuesta.setMensaje("Registro guardado exitosamente");
			respuesta.setDatos(entidadGuardada);
		} catch (Exception e) {
			respuesta.setExito(false);
			respuesta.setMensaje("Error al guardar el registro: " + e.getMessage());
		}
		return respuesta;
	}

	@Override
	public RespuestaBase<T> buscarPorId(Long id) {
		RespuestaBase<T> respuesta = new RespuestaBase<>();
		try {
			getDao().buscarPorId(id).ifPresentOrElse(
					entidad -> {
						respuesta.setExito(true);
						respuesta.setMensaje("Registro encontrado");
						respuesta.setDatos(entidad);
					},
					() -> {
						respuesta.setExito(false);
						respuesta.setMensaje("Registro no encontrado");
					});
		} catch (Exception e) {
			respuesta.setExito(false);
			respuesta.setMensaje("Error al buscar el registro: " + e.getMessage());
		}
		return respuesta;
	}

	@Override
	public RespuestaPaginada<T> listarTodos(Pageable paginacion) {
		RespuestaPaginada<T> respuesta = new RespuestaPaginada<>();
		try {
			Page<T> pagina = getDao().listarTodos(paginacion);
			respuesta.setExito(true);
			respuesta.setMensaje("Registros recuperados exitosamente");
			respuesta.setDatos(pagina.getContent());
			respuesta.setPaginaActual(pagina.getNumber());
			respuesta.setTotalPaginas(pagina.getTotalPages());
			respuesta.setTotalElementos(pagina.getTotalElements());
			respuesta.setElementosPorPagina(pagina.getSize());
		} catch (Exception e) {
			respuesta.setExito(false);
			respuesta.setMensaje("Error al recuperar los registros: " + e.getMessage());
		}
		return respuesta;
	}

	@Override
	public RespuestaBase<Void> eliminar(Long id) {
		RespuestaBase<Void> respuesta = new RespuestaBase<>();
		try {
			if (getDao().existe(id)) {
				getDao().eliminar(id);
				respuesta.setExito(true);
				respuesta.setMensaje("Registro eliminado exitosamente");
			} else {
				respuesta.setExito(false);
				respuesta.setMensaje("Registro no encontrado");
			}
		} catch (Exception e) {
			respuesta.setExito(false);
			respuesta.setMensaje("Error al eliminar el registro: " + e.getMessage());
		}
		return respuesta;
	}

	@Override
	public RespuestaBase<Boolean> existe(Long id) {
		RespuestaBase<Boolean> respuesta = new RespuestaBase<>();
		try {
			respuesta.setExito(true);
			respuesta.setMensaje("Consulta realizada exitosamente");
			respuesta.setDatos(getDao().existe(id));
		} catch (Exception e) {
			respuesta.setExito(false);
			respuesta.setMensaje("Error al verificar existencia: " + e.getMessage());
		}
		return respuesta;
	}
}
