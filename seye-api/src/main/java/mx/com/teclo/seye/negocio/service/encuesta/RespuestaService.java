package mx.com.teclo.seye.negocio.service.encuesta;

import java.util.List;

import mx.com.teclo.seye.persistencia.hibernate.dto.encuesta.UsuarioEncuestaIntentosDTO;
import mx.com.teclo.seye.persistencia.vo.encuesta.UsuarioEncuestaIntentosVO;

public interface RespuestaService {
	
	/**
	 * Califica las respuestas de cada intento 
	 * 
	 * @author Fjmb
	 * @param idUsuario Long
	 * @return UsuarioEncuestaIntentosVO
	 */
	UsuarioEncuestaIntentosVO calificarIntentoEncuesta(Long idIntentoEncuesta);
	
	Integer contarSecciones ( Integer idEncuesta);
	
	/**
	 * Determian el resultado mas alto
	 * 
	 * @author Fjmb
	 * @param idUsuario Long
	 * @return UsuarioEncuestaIntentosVO
	 */
	public Boolean determinarCalifAlta (List<UsuarioEncuestaIntentosDTO> ueiListDTO, UsuarioEncuestaIntentosDTO current);
}
