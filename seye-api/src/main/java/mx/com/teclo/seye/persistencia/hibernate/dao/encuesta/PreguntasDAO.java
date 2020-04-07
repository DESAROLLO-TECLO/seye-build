package mx.com.teclo.seye.persistencia.hibernate.dao.encuesta;

import java.util.List;

import mx.com.teclo.arquitectura.persistencia.comun.dao.BaseDao;
import mx.com.teclo.seye.persistencia.hibernate.dto.encuesta.PreguntasDTO;

public interface PreguntasDAO extends BaseDao<PreguntasDTO>{

	/**
	 * @Descripción: Método para consultar las preguntas por seccion
	 * @author jorgeluis
	 * @return List<PreguntasDTO>
	 */
	public List<PreguntasDTO> preguntas(Long idSeccion);
	
	/**
	 * @Descripción: Método para consultar una preguntas por si identificador unico (op = true, considera st_activo = 1, op = false no considera el st_activo)
	 * @author jorgeluis
	 * @return PreguntasDTO
	 * @param Long idPregunta, Boolean op
	 */
	public PreguntasDTO pregunta(Long idPregunta, Boolean op);
	
}
