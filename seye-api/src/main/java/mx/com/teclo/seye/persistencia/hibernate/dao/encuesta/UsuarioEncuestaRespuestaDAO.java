package mx.com.teclo.seye.persistencia.hibernate.dao.encuesta;

import java.util.List;

import mx.com.teclo.arquitectura.persistencia.comun.dao.BaseDao;
import mx.com.teclo.seye.persistencia.hibernate.dto.encuesta.UsuaroEncuestaRespuestaDTO;

public interface UsuarioEncuestaRespuestaDAO extends BaseDao<UsuaroEncuestaRespuestaDTO> {

	List<UsuaroEncuestaRespuestaDTO> getRespuestas(Long idUsuEncuIntento, Long idEncuesta);
	
	/**
	 * @Descripción: Se guardan las repuestas marcadas por el usuario
	 * @author jorgeluis
	 * @param idUsuEncuIntento
	 * @param idEncuesta
	 * @param idSeccion
	 * @param idPregunta
	 * @return UsuaroEncuestaRespuestaDTO 
	 */
	public UsuaroEncuestaRespuestaDTO userEncuestaRespuesta(Long idUsuEncuIntento, Long idEncuesta, Long idSeccion, Long idPregunta);

	/**
	 * @Descripción: Se guardan las repuestas marcadas por el usuario
	 * @author jorgeluis
	 * @param idUsuEncuIntento
	 * @return List<UsuaroEncuestaRespuestaDTO>
	 */
	public List<UsuaroEncuestaRespuestaDTO> repuestas(Long idUsuEncuIntento);
}
