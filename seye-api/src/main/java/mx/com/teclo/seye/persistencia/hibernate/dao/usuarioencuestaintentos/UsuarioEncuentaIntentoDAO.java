package mx.com.teclo.seye.persistencia.hibernate.dao.usuarioencuestaintentos;

import java.util.List;

import mx.com.teclo.arquitectura.persistencia.comun.dao.BaseDao;
import mx.com.teclo.seye.persistencia.hibernate.dto.encuesta.UsuarioEncuestaIntentosDTO;

public interface UsuarioEncuentaIntentoDAO extends BaseDao<UsuarioEncuestaIntentosDTO>{
	
	/**
	 * @author Damian Pérez
	 * @param idEncuesta Long
	 * @return UsuarioEncuestaDTO
	 */
	UsuarioEncuestaIntentosDTO buscaUnaEncuestaParaFinalizar(Long idEncuesta);
	
	/**
	 * 
	 * @author JLGD
	 * @param idEncuesta
	 * @return
	 */
	public UsuarioEncuestaIntentosDTO buscaUsuEncuIntento(Long idUsuEncuIntento);
	
	/**
	 * @author jorgeluis
	 * @param idUsuario
	 * @param idEncuesta
	 * @return UsuarioEncuestaDTO
	 */
	public UsuarioEncuestaIntentosDTO encuestaUsuario(Long idUsuario, Long idEncuesta);
	

	
	UsuarioEncuestaIntentosDTO getEncuestaByUsuario(Long idEncuesta, Long idUsuario);
	UsuarioEncuestaIntentosDTO getIntentoById(Long idIntentoEncuesta);
	
	/**
	 * @author jorgeluis
	 * @param idUsuEncuIntento
	 * @return List<UsuarioEncuestaIntentosDTO>
	 */
	public List<UsuarioEncuestaIntentosDTO> usuarioEncuesta(Long idUsuarioEncuesta);
	
	/**
	 * @author jorgeluis
	 * @param idUsuarioEncuesta
	 * @param idUsuEncuIntento
	 * @return List<UsuarioEncuestaIntentosDTO>
	 */
	public UsuarioEncuestaIntentosDTO usuarioEncuesta(Long idUsuarioEncuesta, Long idUsuEncuIntento);
	
	/**Método para obtener los registros del usuario de la misma encuesta
	 * estos para determinar la calificación mas alta obtenida
	 * @author jorgeluis
	 * @return List<UsuarioEncuestaIntentosDTO>
	 */
	public List<UsuarioEncuestaIntentosDTO> intentoMismaEncuesta(Long idUsuarioEncuesta);
	
	/**Método para obtener los registros que están pendientes o sin iniciar
	 * @author jorgeluis
	 * @return List<UsuarioEncuestaIntentosDTO>
	 */
	public List<UsuarioEncuestaIntentosDTO> buscaPendientes(Long idUsuarioEncuesta);
	
}
