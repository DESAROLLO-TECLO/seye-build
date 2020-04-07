package mx.com.teclo.seye.persistencia.hibernate.dao.catalogo;

import java.util.List;

import mx.com.teclo.arquitectura.persistencia.comun.dao.BaseDao;
import mx.com.teclo.seye.persistencia.hibernate.dto.catalogo.TipoPreguntaDTO;

public interface TipoPreguntaDAO extends BaseDao<TipoPreguntaDTO>{
	
	/**
	 * @Descripción: Método para consultar el catálogos del los
	 * tipos de pregunta
	 * @author Jorge Luis
	 * @return List<TipoPreguntaDTO>
	 */
	public List<TipoPreguntaDTO> tipoPregunta();
}
