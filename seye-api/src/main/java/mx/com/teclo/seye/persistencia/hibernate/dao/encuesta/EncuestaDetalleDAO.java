package mx.com.teclo.seye.persistencia.hibernate.dao.encuesta;

import mx.com.teclo.arquitectura.persistencia.comun.dao.BaseDao;
import mx.com.teclo.seye.persistencia.hibernate.dto.encuesta.UsuarioEncuestaDetalleDTO;

public interface EncuestaDetalleDAO extends BaseDao<UsuarioEncuestaDetalleDTO> {

	UsuarioEncuestaDetalleDTO getEncuestaDetalle(Long idEncuesta, Long idUsuarioEncuesta);
	UsuarioEncuestaDetalleDTO getEncuestaDetalleByUsuario(Long idEncuesta, Long idUsuarioEncuesta);

}
