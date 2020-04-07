package mx.com.teclo.seye.persistencia.hibernate.dao.catalogo;

import java.util.List;

import mx.com.teclo.arquitectura.persistencia.comun.dao.BaseDao;
import mx.com.teclo.seye.persistencia.hibernate.dto.catalogo.TipoEncuestaDTO;

public interface TipoEncuestaDAO extends BaseDao<TipoEncuestaDTO>{

	/**
	 * @Descripción: Método para consultar el catálogos del los
	 * tipos de encuesta
	 * @author Jorge Luis
	 * @return List<TipoEncuestaDTO>
	 */
	public List<TipoEncuestaDTO> tipoEncuesta();
	
}
