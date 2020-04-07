package mx.com.teclo.seye.api.rest.catalogo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import mx.com.teclo.arquitectura.ortogonales.exception.NotFoundException;
import mx.com.teclo.seye.negocio.service.catalogo.CatalogoService;
import mx.com.teclo.seye.persistencia.vo.catalogo.ConfiguracionVO;
import mx.com.teclo.seye.persistencia.vo.catalogo.EstatusCalificacionVO;
import mx.com.teclo.seye.persistencia.vo.catalogo.GradoVO;
import mx.com.teclo.seye.persistencia.vo.catalogo.GrupoVO;
import mx.com.teclo.seye.persistencia.vo.catalogo.PuestoVO;
import mx.com.teclo.seye.persistencia.vo.catalogo.SedeVO;
import mx.com.teclo.seye.persistencia.vo.catalogo.StEncuestaVO;
import mx.com.teclo.seye.persistencia.vo.catalogo.TipoEncuestaVO;
import mx.com.teclo.seye.persistencia.vo.catalogo.TipoPreguntaVO;
import mx.com.teclo.seye.util.enumerado.RespuestaHttp;

@RestController
@RequestMapping("/catalogo")
public class CatalogoRestController {
	
	@Autowired
	private CatalogoService catalogoService;

	@RequestMapping(value="/encuesta", method = RequestMethod.GET)
	@PreAuthorize("hasAnyAuthority('SERVICE1_CAT_ENCUESTA')")
	public ResponseEntity<List<TipoEncuestaVO>> encuesta() throws NotFoundException {
		List<TipoEncuestaVO> listToReturn = catalogoService.tipoEncuesta();
		return new ResponseEntity<List<TipoEncuestaVO>>(listToReturn, HttpStatus.OK);
	}
	
	@RequestMapping(value="/pregunta", method = RequestMethod.GET)
	@PreAuthorize("hasAnyAuthority('SERVICE2_CAT_PREGUNTA')")
	public ResponseEntity<List<TipoPreguntaVO>> pregunta() throws NotFoundException {
		List<TipoPreguntaVO> listToReturn = catalogoService.tipoPregunta();
		if(listToReturn.isEmpty())
			throw new NotFoundException(RespuestaHttp.NOT_FOUND.getMessage());
		return new ResponseEntity<List<TipoPreguntaVO>>(listToReturn, HttpStatus.OK);
	}
	
	@RequestMapping(value="/grado", method = RequestMethod.GET)
	@PreAuthorize("hasAnyAuthority('SERVICE3_CAT_GRADO')")
	public ResponseEntity<List<GradoVO>> grado() throws NotFoundException {
		List<GradoVO> listToReturn = catalogoService.grado();
		if(listToReturn.isEmpty())
			throw new NotFoundException(RespuestaHttp.NOT_FOUND.getMessage());
		return new ResponseEntity<List<GradoVO>>(listToReturn, HttpStatus.OK);
	}
	
	@RequestMapping(value="/puesto", method = RequestMethod.GET)
	@PreAuthorize("hasAnyAuthority('SERVICE4_CAT_PUESTO')")
	public ResponseEntity<List<PuestoVO>> puesto() throws NotFoundException {
		List<PuestoVO> listToReturn = catalogoService.puesto();
		if(listToReturn.isEmpty())
			throw new NotFoundException(RespuestaHttp.NOT_FOUND.getMessage());
		return new ResponseEntity<List<PuestoVO>>(listToReturn, HttpStatus.OK);
	}
	
	@RequestMapping(value="/sede", method = RequestMethod.GET)
	@PreAuthorize("hasAnyAuthority('SERVICE5_CAT_SEDE')")
	public ResponseEntity<List<SedeVO>> sede() throws NotFoundException {
		List<SedeVO> listToReturn = catalogoService.sede();
		if(listToReturn.isEmpty())
			throw new NotFoundException(RespuestaHttp.NOT_FOUND.getMessage());
		return new ResponseEntity<List<SedeVO>>(listToReturn, HttpStatus.OK);
	}
	
	@RequestMapping(value="/grupo", method = RequestMethod.GET)
	@PreAuthorize("hasAnyAuthority('SERVICE6_CAT_GRUPO')")
	public ResponseEntity<List<GrupoVO>> grupo() throws NotFoundException {
		List<GrupoVO> listToReturn = catalogoService.grupo();
		if(listToReturn.isEmpty())
			throw new NotFoundException(RespuestaHttp.NOT_FOUND.getMessage());
		return new ResponseEntity<List<GrupoVO>>(listToReturn, HttpStatus.OK);
	}
	
	@RequestMapping(value="/stEncuesta", method = RequestMethod.GET)
	@PreAuthorize("hasAnyAuthority('SERVICE9_CAT_ST_ENCUESTA')")
	public ResponseEntity<List<StEncuestaVO>> stEncuesta() throws NotFoundException {
		List<StEncuestaVO> listToReturn = catalogoService.stEncuesta();
		if(listToReturn.isEmpty())
			throw new NotFoundException(RespuestaHttp.NOT_FOUND.getMessage());
		return new ResponseEntity<List<StEncuestaVO>>(listToReturn, HttpStatus.OK);
	}
	
	@RequestMapping(value="/calificacion", method = RequestMethod.GET)
	@PreAuthorize("hasAnyAuthority('SERVICE12_CAT_CALIFICACION')")
	public ResponseEntity<List<EstatusCalificacionVO>> calificacion() throws NotFoundException {
		List<EstatusCalificacionVO> listToReturn = catalogoService.calificacion();
		if(listToReturn.isEmpty())
			throw new NotFoundException(RespuestaHttp.NOT_FOUND.getMessage());
		return new ResponseEntity<List<EstatusCalificacionVO>>(listToReturn, HttpStatus.OK);
	}
	
	@RequestMapping(value="/parametro", method = RequestMethod.GET)
	public ResponseEntity<List<ConfiguracionVO>> parametro() throws NotFoundException {
		List<ConfiguracionVO> listToReturn = catalogoService.configuracion();
		if(listToReturn.isEmpty())
			throw new NotFoundException(RespuestaHttp.NOT_FOUND.getMessage());
		return new ResponseEntity<List<ConfiguracionVO>>(listToReturn, HttpStatus.OK);
	}
	
	@RequestMapping(value="/parametroCd", method = RequestMethod.GET)
	public ResponseEntity<ConfiguracionVO> parametro(@RequestParam("cdParametro") String cdParametro) throws NotFoundException {
		ConfiguracionVO listToReturn = catalogoService.configuracion(cdParametro);
		if(listToReturn == null)
			throw new NotFoundException(RespuestaHttp.NOT_FOUND.getMessage());
		return new ResponseEntity<ConfiguracionVO>(listToReturn, HttpStatus.OK);
	}
}
