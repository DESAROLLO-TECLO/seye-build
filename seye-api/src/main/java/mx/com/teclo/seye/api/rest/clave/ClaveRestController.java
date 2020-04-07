package mx.com.teclo.seye.api.rest.clave;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import mx.com.teclo.arquitectura.ortogonales.exception.BusinessException;
import mx.com.teclo.arquitectura.ortogonales.exception.NotFoundException;
import mx.com.teclo.seye.negocio.service.clave.ClaveService;
import mx.com.teclo.seye.persistencia.vo.clave.PasswordVO;
import mx.com.teclo.seye.persistencia.vo.clave.StPasswordVO;

@RestController
@RequestMapping("/clave")
public class ClaveRestController {
	
	@Autowired
	private ClaveService claveService;

	@RequestMapping(method = RequestMethod.GET)
	//@PreAuthorize("hasAnyAuthority('SERVICE1_CAT_ENCUESTA')")
	public ResponseEntity<List<PasswordVO>> encuesta() throws NotFoundException {
		List<PasswordVO> listToReturn = claveService.claves();
		return new ResponseEntity<List<PasswordVO>>(listToReturn, HttpStatus.OK);
	}
	
	@RequestMapping(value="/sugerido", method = RequestMethod.GET, produces ="text/plain")
	//@PreAuthorize("hasAnyAuthority('SERVICE1_CAT_ENCUESTA')")
	public ResponseEntity<String> clave() throws NotFoundException {
		String strReturn = claveService.clave();
		return new ResponseEntity<String>(strReturn, HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Boolean> nuevo(@RequestBody PasswordVO vo)throws NotFoundException, BusinessException{
		Boolean result = claveService.guardaclave(vo, null);
		return new ResponseEntity<Boolean>(result, HttpStatus.OK);
	} 
	
	@RequestMapping(value="/st", method = RequestMethod.GET)
	//@PreAuthorize("hasAnyAuthority('SERVICE1_CAT_ENCUESTA')")
	public ResponseEntity<List<StPasswordVO>> st() throws NotFoundException {
		List<StPasswordVO> list = claveService.stPassword();
		return new ResponseEntity<List<StPasswordVO>>(list, HttpStatus.OK);
	}
	
	@RequestMapping(value="/activar", method = RequestMethod.POST)
	public ResponseEntity<Boolean> activar(@RequestBody PasswordVO vo)throws NotFoundException, BusinessException{
		Boolean result = claveService.activarClave(vo);
		return new ResponseEntity<Boolean>(result, HttpStatus.OK);
	}
}
