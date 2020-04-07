package mx.com.teclo.seye.negocio.service.clave;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import mx.com.teclo.arquitectura.ortogonales.exception.BusinessException;
import mx.com.teclo.arquitectura.ortogonales.exception.NotFoundException;
import mx.com.teclo.arquitectura.ortogonales.service.comun.UsuarioFirmadoService;
import mx.com.teclo.arquitectura.ortogonales.util.ResponseConverter;
import mx.com.teclo.seye.negocio.service.usuario.UsuarioService;
import mx.com.teclo.seye.persistencia.hibernate.dao.clave.PasswordDAO;
import mx.com.teclo.seye.persistencia.hibernate.dao.clave.PasswordPerfilDAO;
import mx.com.teclo.seye.persistencia.hibernate.dao.clave.PerfilAplicarDAO;
import mx.com.teclo.seye.persistencia.hibernate.dao.clave.StPasswordDAO;
import mx.com.teclo.seye.persistencia.hibernate.dao.usuario.UsuarioDAO;
import mx.com.teclo.seye.persistencia.hibernate.dto.clave.PasswordDTO;
import mx.com.teclo.seye.persistencia.hibernate.dto.clave.PasswordPerfilDTO;
import mx.com.teclo.seye.persistencia.hibernate.dto.clave.PerfilAplicarDTO;
import mx.com.teclo.seye.persistencia.hibernate.dto.clave.StPasswordDTO;
import mx.com.teclo.seye.persistencia.hibernate.dto.usuario.UsuarioDTO;
import mx.com.teclo.seye.persistencia.vo.clave.PasswordVO;
import mx.com.teclo.seye.persistencia.vo.clave.StPasswordVO;
import mx.com.teclo.seye.util.enumerado.RespuestaHttp;

@Service
public class ClaveServiceImpl implements ClaveService{

	@Autowired
	private PasswordDAO passwordDAO;
	
	@Autowired
	private UsuarioFirmadoService userSession;
	
	@Autowired
	private PasswordPerfilDAO passwordPerfilDAO;
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	@Qualifier("usuariosDAO")
	private UsuarioDAO usuarioDAO;
	
	@Value("${app.config.codigo}")
	private String cdApp;
	
	@Autowired
	private StPasswordDAO stPasswordDAO;
	
	@Autowired
	private PerfilAplicarDAO perfilAplicarDAO;
	
	
	@Transactional
	@Override
	public List<PasswordVO> claves() throws NotFoundException{
		List<PasswordDTO> pListDTO = passwordDAO.claves();
		if(pListDTO.isEmpty())
			throw new NotFoundException(RespuestaHttp.NOT_FOUND.getMessage());
		List<PasswordVO> pListVO = ResponseConverter.converterLista(new ArrayList<>(), pListDTO, PasswordVO.class);
		return pListVO;
	}

	@Transactional
	@Override
	public String clave() {
		final int MIN_LENGTH = 6;
		Random r = new Random();
		char[] cadenaCaracteres = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'j', 'k', 'm', 'n', 'p', 'q', 'r', 's', 't',
				'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'J', 'K', 'M', 'N', 'P', 'Q', 'R',
				'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '2', '3', '4', '5', '6', '7', '8', '9', };
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < MIN_LENGTH; i++) {
			sb.append(cadenaCaracteres[r.nextInt(cadenaCaracteres.length)]);
		}
		return sb.toString();
	}

	@Transactional
	@Override
	public Boolean guardaclave(PasswordVO vo, PasswordDTO cron) throws NotFoundException, BusinessException{
		
		Long idUser = userSession.getUsuarioFirmadoVO() == null ? 99L: userSession.getUsuarioFirmadoVO().getId();
		// DEBEMOS OBTENER EL PARÁMETRO ACTUAL PARA COMPARAR SI ES VALIDO ACTUALIZAR EL REGISTRO
		
		PasswordDTO pDTO = null;
		PasswordDTO p2DTO = null;
		StPasswordDTO sDTO = null;
		
		if(vo.getEstatus() != null) {
			
			sDTO = new StPasswordDTO();
			ResponseConverter.copiarPropriedades(sDTO, vo.getEstatus());
			
			if(vo.getEstatus().getCdStPassword().equals("TEE024C_ACTUAL")) {
				// DEBEMOS CONSUTLAR LA CLAVE ACTUAL PARA DESACTIVARLA
				// Y DEJAR VIGENTE EL NUEVO REGISTRO ENTRANTE
				pDTO = passwordDAO.clave();
				if(pDTO != null) {
					pDTO.setStActual(0);
					pDTO.setFhModificacion(new Date());
					pDTO.setFhVigenciaFin(new Date());
					pDTO.setIdUsrModifica(idUser);
					// DEBEMOS VOLVER HISTORICO ESTE REGISTRO
					StPasswordDTO stHist = stPasswordDAO.stPassword("TEE024C_HIST");
					pDTO.setEstatus(stHist);
					passwordDAO.update(pDTO);
					// NO ES NECESARIO APAGAR LOS REGISTROS EN LA TABLA INTERMEDIA
					// YA QUE LA CLAVE ACTUAL SERÁ LA UNICA EN ST_ACTUAL = 1
				}
			}
			
			if(cron != null) {
				p2DTO = cron;
			}else {
				p2DTO = new PasswordDTO();
				p2DTO.setTxValor(usuarioService.toggleEncryption(vo.getTxValor(),"encrypt"));
				p2DTO.setStActivo(1);
				p2DTO.setIdUsrCreacion(idUser);
				p2DTO.setFhCreacion(new Date());
				p2DTO.setIdUsrModifica(idUser);
				p2DTO.setFhModificacion(new Date());	
				p2DTO.setEstatus(sDTO);
			}
			
			if(vo.getEstatus().getCdStPassword().equals("TEE024C_ACTUAL")) {
				p2DTO.setStActual(1);
				p2DTO.setFhVigenciaIni(new Date());
			}else if(vo.getEstatus().getCdStPassword().equals("TEE024C_PENDIENTE")) {
				p2DTO.setStActual(0);
			}else {
				// ES HISTÓRICO
				p2DTO.setStActual(0);
			}
			
			if(p2DTO.getIdPassword() != null) {				
				passwordDAO.update(p2DTO);
			}else {
				passwordDAO.save(p2DTO);
			}
			
		}else {
			throw new BusinessException("No se puede guardar el registro sin el estatus");
		}
		
		// VERIFICAR LOS PERFILES QUE ESTARÁN ASOCIADOS AL REGISTRO
		List<PerfilAplicarDTO> paListDTO = perfilAplicarDAO.perfilAplicar();
		if(paListDTO.isEmpty())
			throw new BusinessException("No existe ningún perfil para realizar el cambio de clave");
		
		guardarPerfilPwd(paListDTO, p2DTO);
		
		// DESPUES DE HABER GUARDADO TODOS LOS DATOS CONSULTAREMOS
		// A TODOS LOS USUARIOS MEDIANTE SU PERFIL Y ATUALIZAMORES SU PASSWORD DE USO
		List<List<UsuarioDTO>> uListGenDTO = null;
		for(PerfilAplicarDTO paDTO : paListDTO) {
			uListGenDTO = new ArrayList<>();
				List<UsuarioDTO> uListDTO = usuarioDAO.findUsersByProfile(paDTO.getPerfil().getCdPerfil(), cdApp, true);
				uListGenDTO.add(uListDTO);
		}
		
		// SE DEBERÁ APLICAR EL CAMBIO DE CLAVE CUANDIO SE RECIBA UN
		// ESTATUS ACTUAL
		if(vo.getEstatus().getCdStPassword().equals("TEE024C_ACTUAL")) {
			actualizaUsuario(uListGenDTO, p2DTO);
		}
		return true;
	}

	
	@Transactional
	private void guardarPerfilPwd (List<PerfilAplicarDTO> paListDTO, PasswordDTO p2DTO) {
		Long idUser = userSession.getUsuarioFirmadoVO() == null ? 99L: userSession.getUsuarioFirmadoVO().getId();
		// DEBEMOS OBTENER EL PARÁMETRO ACTUAL PARA COMPARAR SI ES VALIDO ACTUALIZAR EL REGISTRO
		PasswordPerfilDTO ppDTO = null;
		for(PerfilAplicarDTO paDTO : paListDTO) {
			ppDTO = new PasswordPerfilDTO();
			ppDTO.setPassword(p2DTO);
			ppDTO.setPerfil(paDTO.getPerfil());
			ppDTO.setStActivo(1);
			ppDTO.setIdUsrCreacion(idUser);
			ppDTO.setFhCreacion(new Date());
			ppDTO.setIdUsrModifica(idUser);
			ppDTO.setFhModificacion(new Date());
			passwordPerfilDAO.save(ppDTO);
		}
	}
	
	@Transactional
	private void actualizaUsuario(List<List<UsuarioDTO>> uListGenDTO, PasswordDTO p2DTO) {
		Long idUser = userSession.getUsuarioFirmadoVO() == null ? 99L: userSession.getUsuarioFirmadoVO().getId();
		if(uListGenDTO != null)
			for(List<UsuarioDTO> l : uListGenDTO) {
				for(UsuarioDTO uDTO: l) {
					uDTO.setNbContrasenia(p2DTO.getTxValor());
					uDTO.setIdUsrModifica(idUser);
					uDTO.setFhModificacion(new Date());
					usuarioDAO.update(uDTO);
				}
			}
	}
	
	
	
	@Transactional
	@Override
	public List<StPasswordVO> stPassword() throws NotFoundException{
		List<StPasswordDTO> sListDTO = stPasswordDAO.stPassword();
		if(sListDTO.isEmpty())
			throw new NotFoundException(RespuestaHttp.NOT_FOUND.getMessage());
		List<StPasswordVO> sListVO = ResponseConverter.converterLista(new ArrayList<>(), sListDTO, StPasswordVO.class);
		return sListVO;
	}
	
	//EL CRON SE EJECUTARÁ A LAS 11:59 PARA REALIZAR EL AJUSTE DE LAS CONTRASEÑAS DIARIAS
	@Scheduled(cron="0 59 23 * * ?")
	@Transactional
	@Override
	public Boolean claveCron () throws NotFoundException, BusinessException {
		PasswordVO pVO = new PasswordVO();
		
		StPasswordVO stVO = new StPasswordVO();
		stVO.setIdStPassword(1L);
		stVO.setCdStPassword("TEE024C_ACTUAL");
		stVO.setNbStPassword("ACTUAL");
		pVO.setEstatus(stVO);
		PasswordDTO existente = null;
		// Consulta en BD si hay registros con estatus pendiente
		// para agarrar cualquier registro de forma alreatoria
		List<PasswordDTO> pendientes = passwordDAO.clavesPendientes("TEE024C_PENDIENTE");
		if(pendientes.isEmpty()) {
			// NO EXISTEN REGISTRO PENDIENTES, LO CUAL HAY QUE GENRAR UNO
			pVO.setTxValor(clave());
		}else{
			// EXISTEM REGISTROS PENDIENTES LO CUAL HAY QUE ESCOGER UNO ALEATORIO
			StPasswordDTO st = new StPasswordDTO();
			ResponseConverter.copiarPropriedades(st, stVO);
			existente = aleatorio(pendientes);
			existente.setEstatus(st);
			// existente.setTxValor(usuarioService.toggleEncryption(existente.getTxValor(),"decrypt"));
		}
		guardaclave(pVO, existente);
		return true;
	}
	
	private PasswordDTO aleatorio(List<PasswordDTO> pendientes) {
		// Instanciamos la clase Random
		int position = 0;
		Random random = new Random();
		while (pendientes.size()>0){
			// Elegimos un índice al azar, entre 0 y el número de cartas que quedan por sacar
			position = random.nextInt(pendientes.size());
		   break;
		}
		if(position >= 0) {
			PasswordDTO pDTO = null;
			pDTO = pendientes.get(position);
			return pDTO; 
		}
		return null;
	}

	@Transactional
	@Override
	public Boolean activarClave(PasswordVO vo) throws NotFoundException, BusinessException {
		
		if(vo == null)
			throw new BusinessException("No se puede activar una clave indefinida");
		
		StPasswordVO stVO = new StPasswordVO();
		stVO.setIdStPassword(1L);
		stVO.setCdStPassword("TEE024C_ACTUAL");
		stVO.setNbStPassword("ACTUAL");
		vo.setEstatus(stVO);
		
		// DEBEMOS OBNTENER EL REGISTRO PENDIENTE QUE SE ACTIVARÁ 
		// MENDIENTE SU IDENFICADOR UNICO
		PasswordDTO pDTO = passwordDAO.findOne(vo.getIdPassword());
		
		if(pDTO == null)
			throw new NotFoundException("No se ha encontrado la nueva clave en el sistema");
		// SE MANDA A INVOCAR LA FUNCIÓN GENERICA PARA ACTIVAR CLAVE PENDIENTE
		StPasswordDTO st = new StPasswordDTO();
		ResponseConverter.copiarPropriedades(st, stVO);
		pDTO.setEstatus(st);
		guardaclave(vo, pDTO);
		return true;
	}
}

