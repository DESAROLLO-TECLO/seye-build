package mx.com.teclo.seye.persistencia.hibernate.dao.usuarioencuesta;

import java.util.List;

import mx.com.teclo.arquitectura.persistencia.comun.dao.BaseDao;
import mx.com.teclo.seye.persistencia.hibernate.dto.encuesta.UsuarioEncuestaDTO;

public interface UsuarioEncuentaDAO extends BaseDao<UsuarioEncuestaDTO>{
	
	/**
	 * @author Damian Pérez
	 * @param idUsuario Long
	 * @return List<Tee002dEeUsuEncuesta>
	 */
	List<UsuarioEncuestaDTO> buscaEncuentasPorUsuario(Long idUsuario);
	
	/**
	 * @author jorgeluis
	 * @param idUsuario
	 * @param idEncuesta
	 * @return List<UsuarioEncuestaDTO>
	 */
	List<UsuarioEncuestaDTO> encuestaIntentos(Long idUsuario, Long idEncuesta);
	/**
	 * @author Fjmb
	 * @param idUsuario
	 * @param idEncuesta
	 * @return  UsuarioEncuestaDTO 
	 */
	  UsuarioEncuestaDTO  getEncuestaByIdUser(Long idUsuario, Long idEncuesta);

  	/**
	 * @author jorgeluis
	 * @param idUsuario
	 * @param idUsuarioEncuesta
	 * @return UsuarioEncuestaDTO
	 */
	public UsuarioEncuestaDTO usuarioEncuesta(Long idUsuario, Long idUsuarioEncuesta);
}
