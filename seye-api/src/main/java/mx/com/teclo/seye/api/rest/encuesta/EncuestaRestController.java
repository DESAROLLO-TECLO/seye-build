
package mx.com.teclo.seye.api.rest.encuesta;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import mx.com.teclo.arquitectura.ortogonales.exception.BusinessException;
import mx.com.teclo.arquitectura.ortogonales.exception.NotFoundException;
import mx.com.teclo.seye.negocio.service.encuesta.DetalleIntentoService;
import mx.com.teclo.seye.negocio.service.encuesta.EncuentaService;
import mx.com.teclo.seye.negocio.service.encuesta.RespuestaService;
import mx.com.teclo.seye.persistencia.vo.encuesta.EncuestaGeneralVO;
import mx.com.teclo.seye.persistencia.vo.encuesta.EncuestaTipoRestanteVO;
import mx.com.teclo.seye.persistencia.vo.encuesta.EncuestasConUsuarioTotalesVO;
import mx.com.teclo.seye.persistencia.vo.encuesta.EncuestasConUsuarioVO;
import mx.com.teclo.seye.persistencia.vo.encuesta.IntentoDetalleVO;
import mx.com.teclo.seye.persistencia.vo.encuesta.UserRespuestaVO;
import mx.com.teclo.seye.persistencia.vo.encuesta.UsuarioEncuestaDetalleVO;
import mx.com.teclo.seye.persistencia.vo.encuesta.UsuarioEncuestaIntentosVO;
import mx.com.teclo.seye.persistencia.vo.encuesta.UsuarioEncuestaVO;

@RestController
@RequestMapping("/encuesta")
public class EncuestaRestController {
	
	@Autowired
	private EncuentaService encuentaService;
	
	@Autowired
	private RespuestaService respuestaService;
	
	@Autowired
	private DetalleIntentoService detalleIntentoService;
	
	@RequestMapping(value="/detalle/intento", method = RequestMethod.GET)
	@PreAuthorize("hasAnyAuthority('SERVICE16_ENC_INT_DETALLE')")
	public ResponseEntity<IntentoDetalleVO> detalleIntento (@RequestParam("idIntento") Long idIntento) throws NotFoundException {
		IntentoDetalleVO voReturn = detalleIntentoService.detalleIntento(idIntento);
		return new ResponseEntity<IntentoDetalleVO>(voReturn, HttpStatus.OK);
	}
	
	@RequestMapping(value="/usuario", method = RequestMethod.GET)
	@PreAuthorize("hasAnyAuthority('SERVICE8_ENC_ENCUESTAS_USUARIO')")
	public ResponseEntity<EncuestaGeneralVO> encuestaUsuario () throws NotFoundException {
		EncuestaGeneralVO voReturn = encuentaService.encuestaFolio();
		return new ResponseEntity<EncuestaGeneralVO>(voReturn, HttpStatus.OK);
	}
	
	@GetMapping(value = "/encuesta")
//	@PreAuthorize(value = "BUSCA_TODAS_ENCUESTAS_CON_USUARIO")
	public ResponseEntity<List<EncuestasConUsuarioTotalesVO>> encuesta (
			@RequestParam(value = "tipoBusqueda", required = true) Integer tipoBusqueda,
			@RequestParam(value = "valorBusqueda", required = false) String valorBusqueda,
			@RequestParam(value = "fhInicio", required = false) String fInicio, 
			@RequestParam(value = "fhFin", required = false) String fhFin, 
			@RequestParam(value = "stEncuesta", required = true) Integer stEncuesta
			) {
		List<EncuestasConUsuarioTotalesVO> listaEncuentaPorUsuario = encuentaService.buscaTodasEncuestasConUsuario(
				tipoBusqueda, valorBusqueda, fInicio, fhFin, stEncuesta);
		
		return  new ResponseEntity<List<EncuestasConUsuarioTotalesVO>>(listaEncuentaPorUsuario, HttpStatus.OK);
	}
	
	
	@RequestMapping(value="/finalizar", method = RequestMethod.PUT)
	public ResponseEntity<UsuarioEncuestaIntentosVO> finalizarEncuesta(
		@RequestBody UsuarioEncuestaIntentosVO usuarioEncuestaIntentosVO
			) throws NotFoundException {
		UsuarioEncuestaIntentosVO encuestaIntentosVO =null;
		try {
			//1.- Finalizar intento
			encuestaIntentosVO = encuentaService.finalizarIntento(usuarioEncuestaIntentosVO.getIdUsuEncuIntento(), false);
			//2.- Calificar intento
			respuestaService.calificarIntentoEncuesta(usuarioEncuestaIntentosVO.getIdUsuEncuIntento());
			//3.- Actuaizar a falso el campo aplicar encuesta y contar total de intentos 
			encuentaService.finalizarEncuesta(encuestaIntentosVO);
			encuestaIntentosVO.setIdUsuEncuIntento(null);
			encuestaIntentosVO.setUsuarioEncuesta(null);
			encuestaIntentosVO.setEsProcesoExitoso(true);
		} catch (Exception e) {
			System.err.println(e.getMessage());
			encuestaIntentosVO=new UsuarioEncuestaIntentosVO();
			encuestaIntentosVO.setEsProcesoExitoso(false);
		}
		
		if(encuestaIntentosVO != null) {
			// Consultamos los datoas del itento por el ID 
			encuestaIntentosVO = encuentaService.detalle(usuarioEncuestaIntentosVO.getIdUsuEncuIntento(), true);
		}
		return new ResponseEntity<UsuarioEncuestaIntentosVO>(encuestaIntentosVO, HttpStatus.OK);
	}

	@RequestMapping(value="/detalle", method = RequestMethod.GET)
	public ResponseEntity<UsuarioEncuestaDetalleVO> encuestaDetalle (
			@RequestParam(value="idEncuesta") Long idEncuesta, 
			@RequestParam(value="idUsuarioEncuesta") Long idUsuarioEncuesta) throws NotFoundException{
		UsuarioEncuestaDetalleVO encuesta = encuentaService.encuestaDetalle(idEncuesta, idUsuarioEncuesta); 
		return new ResponseEntity<UsuarioEncuestaDetalleVO>(encuesta, HttpStatus.OK);
		
		
	}
	
	@RequestMapping(value="/cargar", method = RequestMethod.GET)
	public ResponseEntity<Boolean> cargarEncuesta (
			@RequestParam(value="idEncuesta") long idEncuesta) throws NotFoundException {
		try {
			encuentaService.cargarEncuesta(idEncuesta);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new NotFoundException("Ha ocurrido un imprevisto!, por favor contacte al administrador.");
			
			
		}
		return new ResponseEntity<Boolean>(true, HttpStatus.OK);
	}
	
	@RequestMapping(value="/respuestas", method = RequestMethod.POST)
	@PreAuthorize("hasAnyAuthority('SERVICE14_ENC_RESPUESTAS')")
	public ResponseEntity<Boolean> respuestas(@RequestBody List<UserRespuestaVO> l) throws NotFoundException, BusinessException {
		Boolean b = encuentaService.guardarRespuestas(l);
		return new ResponseEntity<Boolean>(b, HttpStatus.OK);
	}
	
	@RequestMapping(value="/intento", method = RequestMethod.GET)
	@PreAuthorize("hasAnyAuthority('SERVICE10_ENC_BY_CD')")
	public ResponseEntity<EncuestaGeneralVO> encuestaDetalle(@RequestParam("idEncuesta") Long idEncuesta) throws NotFoundException{
		EncuestaGeneralVO voReturn = encuentaService.encuestaIntento(idEncuesta);
		return new ResponseEntity<EncuestaGeneralVO>(voReturn, HttpStatus.OK);
	}
	
	@RequestMapping(value="/intento", method = RequestMethod.POST)
	@PreAuthorize("hasAnyAuthority('SERVICE13_ENC_INTENTO')")
	public ResponseEntity<Boolean> nuevo(@RequestBody UsuarioEncuestaVO vo) throws NotFoundException, BusinessException{
		Boolean b = encuentaService.nuevoIntento(vo);
		return new ResponseEntity<Boolean>(b, HttpStatus.OK);
	}
	
	@RequestMapping(value="/encuestadosEncuesta", method = RequestMethod.GET)
	public ResponseEntity<List<EncuestasConUsuarioVO>> encuestadosEncuesta(
			@RequestParam(value="idEncuesta") Long idEncuesta){
		finalizarEncuestaPorTiempo(idEncuesta);
		List<EncuestasConUsuarioVO> encuestasConUsuarioVO = null;
		encuestasConUsuarioVO = encuentaService.encuestadosEncuesta(idEncuesta);
		return new ResponseEntity<List<EncuestasConUsuarioVO>>(encuestasConUsuarioVO, HttpStatus.OK);
	}
	
	@RequestMapping(value="/pausar", method = RequestMethod.GET)
	public ResponseEntity<Boolean> pausar(
			@RequestParam(value="idIntento") Long idIntento,
			@RequestParam(value="nuConsumidos") Integer nuConsumidos) throws NotFoundException{
		Boolean result =  encuentaService.pausar(idIntento, nuConsumidos);
		return new ResponseEntity<Boolean>(result, HttpStatus.OK);
	}
	
	public Boolean finalizarEncuestaPorTiempo( Long idEncuesta){
		
		List<EncuestaTipoRestanteVO> lista = encuentaService.getListFibalizaPorTiempo(idEncuesta);
		UsuarioEncuestaIntentosVO encuestaIntentosVO =null;
		
		for(EncuestaTipoRestanteVO intento : lista) {
			try {
				//1.- Finalizar intento
				encuestaIntentosVO = encuentaService.finalizarIntento(intento.getIdUsuarioEncuesta(), false);
				//2.- Calificar intento
				respuestaService.calificarIntentoEncuesta(intento.getIdUsuarioEncuesta());
				//3.- Actuaizar a falso el campo aplicar encuesta y contar total de intentos 
				encuentaService.finalizarEncuesta(encuestaIntentosVO);
				encuestaIntentosVO.setIdUsuEncuIntento(null);
				encuestaIntentosVO.setUsuarioEncuesta(null);
				encuestaIntentosVO.setEsProcesoExitoso(true);
			} catch (Exception e) {
				System.err.println(e.getMessage());
				encuestaIntentosVO=new UsuarioEncuestaIntentosVO();
				encuestaIntentosVO.setEsProcesoExitoso(false);
			}
			
			if(encuestaIntentosVO != null) {
				// Consultamos los datoas del itento por el ID 
				try {
					encuestaIntentosVO = encuentaService.detalle(intento.getIdUsuarioEncuesta(), true);
				} catch (NotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
			
		return true;
	}
	
	@RequestMapping("/validaTiempoIntento")
	public ResponseEntity<EncuestaTipoRestanteVO> validarTiempoFaltante(
			@RequestParam(value="idUsuarioEncuesta") Long idUsuarioEncuesta) throws NotFoundException{
		EncuestaTipoRestanteVO intento = encuentaService.validarPorTiempoRestante(idUsuarioEncuesta);
		UsuarioEncuestaIntentosVO encuestaIntentosVO =null;
		if(intento.getStContinua()==0) {
			try {
				//1.- Finalizar intento
				encuestaIntentosVO = encuentaService.finalizarIntento(intento.getIdUsuarioEncuesta(), false);
				//2.- Calificar intento
				respuestaService.calificarIntentoEncuesta(intento.getIdUsuarioEncuesta());
				//3.- Actuaizar a falso el campo aplicar encuesta y contar total de intentos 
				encuentaService.finalizarEncuesta(encuestaIntentosVO);
				encuestaIntentosVO.setIdUsuEncuIntento(null);
				encuestaIntentosVO.setUsuarioEncuesta(null);
				encuestaIntentosVO.setEsProcesoExitoso(true);
			} catch (Exception e) {
				System.err.println(e.getMessage());
				encuestaIntentosVO=new UsuarioEncuestaIntentosVO();
				encuestaIntentosVO.setEsProcesoExitoso(false);
			}
			
			if(encuestaIntentosVO != null) {
				// Consultamos los datoas del itento por el ID 
				encuestaIntentosVO = encuentaService.detalle(intento.getIdUsuarioEncuesta(), true);
			}
		}
		
		return new ResponseEntity<EncuestaTipoRestanteVO>(intento, HttpStatus.OK);
	}
}
