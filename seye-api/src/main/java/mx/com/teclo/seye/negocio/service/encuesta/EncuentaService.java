package mx.com.teclo.seye.negocio.service.encuesta;

import java.util.List;

import mx.com.teclo.arquitectura.ortogonales.exception.BusinessException;
import mx.com.teclo.arquitectura.ortogonales.exception.NotFoundException;
import mx.com.teclo.seye.persistencia.vo.encuesta.EncuestaGeneralVO;
import mx.com.teclo.seye.persistencia.vo.encuesta.EncuestaTipoRestanteVO;
import mx.com.teclo.seye.persistencia.vo.encuesta.EncuestasConUsuarioTotalesVO;
import mx.com.teclo.seye.persistencia.vo.encuesta.EncuestasConUsuarioVO;
import mx.com.teclo.seye.persistencia.vo.encuesta.UserRespuestaVO;
import mx.com.teclo.seye.persistencia.vo.encuesta.UsuarioEncuestaDetalleVO;
import mx.com.teclo.seye.persistencia.vo.encuesta.UsuarioEncuestaIntentosVO;
import mx.com.teclo.seye.persistencia.vo.encuesta.UsuarioEncuestaVO;

public interface EncuentaService {
	
	/**
	 * Busca la encuentas por usuario del sistema
	 * 
	 * @author Damian Pérez
	 * @param idUsuario Long
	 * @return ListUsuarioEncuestaVO>
	 */
	List<UsuarioEncuestaVO> buscaEncuentasPorUsuario();
	
	/**
	 * Busca todas las encuentas con su usario asociado
	 * 
	 * @author Damian Pérez
	 * @return List<EncuestasUsuariosVO>
	 */
	List<EncuestasConUsuarioTotalesVO> buscaTodasEncuestasConUsuario(Integer tipoBusqueda, String valorBusqueda, String fInicio, String fhFin, Integer stEncuesta);
	
	
	/**
	 * @author Fernando Campero
	 * @param idUsuario Long
	 * @param idEncuesta Long
	 * @return EncuestaVO
	 * 
	 * Busca la encuesta por aplicar y regresa la encuesta, seccmentos y preguntas
	 */
	UsuarioEncuestaDetalleVO encuestaDetalle(Long idEncuesta, Long idUsuarioEncuesta) throws NotFoundException;
	
	/**
	 * @author Fernando Campero
	 * @param idUsuario Long
	 * @param idEncuesta Long
	 * @return void
	 * 
	 * Carga la encuesta, las preguntas que vienen en la encuesta para guardar posteriormente las     respuestas 
	 */
	void cargarEncuesta(Long idEncuesta);
	
	/**
	 * @Descripción: Se guardan las repuestas marcadas por el usuario
	 * @author jorgeluis
	 * @param List<UserRespuestaVO>
	 * @return Boolean 
	 */
	public Boolean guardarRespuestas(List<UserRespuestaVO> l)throws BusinessException;
	
	/**
	 * @author Fernando Campero
	 * @param respuestas RespuestasVO
	 * @return UsuarioEncuestaRespuestaVO
	 * 
	 * Guarda las respuesas de la encuesta por respuesta 
	 */
	UsuarioEncuestaIntentosVO calificarEncuesta(Long idUsuario, Long idEncuesta, Integer nuIntentos, Long idUsrCreacion);
	
	/**
	 * @Descripción: Método para consultar la encuesta por su
	 * código identificador
	 * @author jorgeluis
	 * @return EncuestaGeneralVO
	 */
	public EncuestaGeneralVO encuestaFolio()throws NotFoundException;
	
	/**
	 * @Descripción: Método para consultar la encuesta mediante el
	 * identificador unico del registro
	 * @author jorgeluis
	 * @return EncuestaGeneralVO
	 */
	public EncuestaGeneralVO encuestaFolio(Long idUsuarioEncuesta, Long idUsuario)throws NotFoundException;
	
	/**
	 * @Descripción: Método para consultar los intentos del usuario
	 * al una encuesta dada
	 * @author jorgeluis
	 * @return EncuestaGeneralVO
	 */
	public EncuestaGeneralVO encuestaIntento(Long idEncuesta)throws NotFoundException;
	/**
	 * @author Fernando Campero
	 * @param respuestas RespuestasVO
	 * @return UsuarioEncuestaRespuestaVO
	 * 
	 * Guarda las respuesas de la encuesta por respuesta 
	 */
	public List<EncuestasConUsuarioVO> encuestadosEncuesta(Long idEncuesta);
	
	/**
	 * @Descripción: Método para consultar los intentos del usuario
	 * al una encuesta dada
	 * @author jorgeluis
	 * @param UsuarioEncuestaVO
	 * @exception BusinessException
	 * @return Boolean
	 */
	public Boolean nuevoIntento(UsuarioEncuestaVO vo) throws BusinessException;
	
	/**
	 * Metodo para actualizar como finalizado el intento de contestar un cuestionario o evaluación
	 * 
	 * @param idUsuEncuIntento
	 * @return
	 * @throws NotFoundException
	 */
	public UsuarioEncuestaIntentosVO finalizarIntento(Long idUsuEncuIntento, Boolean b) throws BusinessException;
	
	/**
	 * Finaliza una encuesta actualizando su estatus y la fecha de termino
	 * 
	 * @author Damian Pérez
	 * @param idEncuesta Long
	 * @param idUsuario Long
	 */
	public void finalizarEncuesta(UsuarioEncuestaIntentosVO encuestaIntentosVO);
	
	/**
	 * Consulta el detalle del intento en base de datos mediante el ID
	 * 
	 * @author jorgeluis
	 * @return UsuarioEncuestaIntentosVO
	 */
	public UsuarioEncuestaIntentosVO detalle(Long idUsuEncuIntento, Boolean finalizar) throws NotFoundException;
	
	/**
	 * Método para realizar el pausado de la encuesta
	 * @author jorgeluis
	 * @return Boolean
	 */
	public Boolean pausar(Long intento, Integer nuConsumidos) throws NotFoundException;
	
	List<EncuestaTipoRestanteVO> getListFibalizaPorTiempo(Long idEncuesta);
	
	EncuestaTipoRestanteVO validarPorTiempoRestante(Long idUsuarioEncuesta);
}
