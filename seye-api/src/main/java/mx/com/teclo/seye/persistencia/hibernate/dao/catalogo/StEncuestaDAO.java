package mx.com.teclo.seye.persistencia.hibernate.dao.catalogo;

import java.util.List;

import mx.com.teclo.arquitectura.persistencia.comun.dao.BaseDao;
import mx.com.teclo.seye.persistencia.hibernate.dto.catalogo.StEncuestaDTO;

public interface StEncuestaDAO extends BaseDao<StEncuestaDTO>{

	/**
	 * @Descripción: Método para consultar el catálogos del los
	 * tipos de pregunta
	 * @author Jorge Luis
	 * @return List<StEncuestaDTO>
	 */
	public List<StEncuestaDTO> stEncuesta();
	
	/**
	 * @Descripción: Método para consultar un esttatus 
	 * de encuesta por su identificador unico
	 * @author Jorge Luis
	 * @return List<StEncuestaDTO>
	 */
	public StEncuestaDTO encuesta(String cdStEncuesta);
}
