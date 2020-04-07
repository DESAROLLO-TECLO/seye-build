package mx.com.teclo.seye.persistencia.hibernate.dao.usuarioencuesta;

import mx.com.teclo.arquitectura.persistencia.comun.dao.BaseDao;
import mx.com.teclo.seye.persistencia.hibernate.dto.encuesta.UsuAtributoDTO;

public interface UsuAtributoDAO extends BaseDao<UsuAtributoDTO>{

	/**
	 * @author jorgeluis
	 * @param idUsuario
	 * @return UsuarioEncuestaDTO
	 */
	public UsuAtributoDTO usuario(Long idUsuario);
	
}
