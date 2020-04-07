package mx.com.teclo.seye.negocio.service.encuesta;

import java.util.List;
import java.util.Map;

import mx.com.teclo.arquitectura.ortogonales.exception.NotFoundException;
import mx.com.teclo.seye.persistencia.hibernate.dto.encuesta.UsuaroEncuestaRespuestaDTO;
import mx.com.teclo.seye.persistencia.vo.encuesta.IntentoDetalleVO;
import mx.com.teclo.seye.persistencia.vo.encuesta.PreguntaVO;
import mx.com.teclo.seye.persistencia.vo.encuesta.UsuarioEncuestaRespuestaVO;

public interface DetalleIntentoService {

	/**
	 * @Descripción: Método para consultar todos los detalles del
	 * intento actual del usuario en sesión
	 * @author jorgeluis
	 * @return IntentoDetalleVO
	 */
	public IntentoDetalleVO detalleIntento(Long idIntento) throws NotFoundException;
	
	/**
	 * @Descripción: Método para filtarr las prespuestad del usuario
	 * @author jorgeluis
	 * @return List<UsuarioEncuestaRespuestaVO>
	 */
	public List<UsuarioEncuestaRespuestaVO> fitroUsuarioRespuesta(List<UsuaroEncuestaRespuestaDTO> uerlistDTO);
	
	/**
	 * Clasificar la respuesta mendiante mapas para grupar 
	 * por secciones y agregar sus preguntas y respuestas
	 * @author jorgeluis
	 * @return UsuarioEncuestaIntentosVO
	 */
	public Map<String, Object> detalleFinalizar(List<UsuarioEncuestaRespuestaVO> l);
	
	/**
	 * Método para ordenar las preguntas que pertenecen auna sección
	 * @author jorgeluis
	 * @return List<PreguntaVO>
	 */
	public List<PreguntaVO> orderList(List<PreguntaVO> pListVO);
	
	/**
	 * Me´todo para ordenar los elementos del array 
	 * mediante del numero de orden de la pregunta
	 * @author jorgeluis
	 * @return List<PreguntaVO>
	 */
	public List<UsuarioEncuestaRespuestaVO> orderListRespuesta(List<UsuarioEncuestaRespuestaVO> uerListVO);
}
