package mx.com.teclo.seye.negocio.service.encuesta;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.com.teclo.arquitectura.ortogonales.exception.NotFoundException;
import mx.com.teclo.arquitectura.ortogonales.util.ResponseConverter;
import mx.com.teclo.seye.persistencia.hibernate.dao.encuesta.PreguntasDAO;
import mx.com.teclo.seye.persistencia.hibernate.dao.encuesta.SeccionDAO;
import mx.com.teclo.seye.persistencia.hibernate.dao.encuesta.UsuarioEncuestaRespuestaDAO;
import mx.com.teclo.seye.persistencia.hibernate.dao.usuarioencuestaintentos.UsuarioEncuentaIntentoDAO;
import mx.com.teclo.seye.persistencia.hibernate.dto.encuesta.PreguntasDTO;
import mx.com.teclo.seye.persistencia.hibernate.dto.encuesta.SeccionDTO;
import mx.com.teclo.seye.persistencia.hibernate.dto.encuesta.UsuarioEncuestaIntentosDTO;
import mx.com.teclo.seye.persistencia.hibernate.dto.encuesta.UsuaroEncuestaRespuestaDTO;
import mx.com.teclo.seye.persistencia.vo.encuesta.IntentoDetalleVO;
import mx.com.teclo.seye.persistencia.vo.encuesta.OpcionVO;
import mx.com.teclo.seye.persistencia.vo.encuesta.PreguntaVO;
import mx.com.teclo.seye.persistencia.vo.encuesta.SeccionVO;
import mx.com.teclo.seye.persistencia.vo.encuesta.UsuarioEncuestaRespuestaVO;
import mx.com.teclo.seye.util.enumerado.RespuestaHttp;

@Service
public class DetalleIntentoServiceImpl implements DetalleIntentoService{

	@Autowired
	private UsuarioEncuentaIntentoDAO usuarioEncuentaIntentoDAO;
	
	@Autowired
	private UsuarioEncuestaRespuestaDAO usuarioEncuestaRespuestaDAO;
	
	@Autowired
	private PreguntasDAO preguntasDAO;
	
	@Autowired
	private SeccionDAO seccionDAO;
	
	@Transactional
	@Override
	public IntentoDetalleVO detalleIntento(Long idIntento) throws NotFoundException{
		
		UsuarioEncuestaIntentosDTO ueiDTO = usuarioEncuentaIntentoDAO.getIntentoById(idIntento);
		if(ueiDTO == null)
			throw new NotFoundException(RespuestaHttp.NOT_FOUND.getMessage());
		IntentoDetalleVO idVO = ResponseConverter.copiarPropiedadesFull(ueiDTO, IntentoDetalleVO.class);
		List<UsuaroEncuestaRespuestaDTO> uerListDTO = usuarioEncuestaRespuestaDAO.repuestas(ueiDTO.getIdUsuEncuIntento());
		List<UsuarioEncuestaRespuestaVO> uerListVO = fitroUsuarioRespuesta(uerListDTO);
		idVO.setResumenRespuesta(uerListVO);
		return idVO;
	}

	@Transactional
	@Override
	public List<UsuarioEncuestaRespuestaVO> fitroUsuarioRespuesta(List<UsuaroEncuestaRespuestaDTO> uerListDTO) {
		List<UsuarioEncuestaRespuestaVO> uerListVO = null;
		UsuarioEncuestaRespuestaVO uerVO = null;
		PreguntaVO pVO = null;
		if(!uerListDTO.isEmpty()) {
			uerListVO = new ArrayList<>();
			for(UsuaroEncuestaRespuestaDTO uerDTO: uerListDTO) {
				uerVO = ResponseConverter.copiarPropiedadesFull(uerDTO, UsuarioEncuestaRespuestaVO.class);
				PreguntasDTO pDTO = preguntasDAO.findOne(uerVO.getId().getIdPregunta());
				if(pDTO != null) {
					pVO = ResponseConverter.copiarPropiedadesFull(pDTO, PreguntaVO.class);
					if(pVO != null) {
						for(OpcionVO oVO: pVO.getOpciones()) {
							if(uerDTO.getOpcionesDTO() != null && oVO.getIdOpcion().equals(uerDTO.getOpcionesDTO().getIdOpcion())) {
								oVO.setStMarcado(1);
							}else {
								oVO.setStMarcado(0);
							}
							oVO.setStCorrecto(uerDTO.getStCorrecto());
						}
					}
					uerVO.setPregunta(pVO);
				}
				uerListVO.add(uerVO);
			}
			//
			return orderListRespuesta(uerListVO);
		}
		return null;
	}
	
	@Override
	public List<UsuarioEncuestaRespuestaVO> orderListRespuesta(List<UsuarioEncuestaRespuestaVO> uerListVO){
		Collections.sort(uerListVO, new Comparator<UsuarioEncuestaRespuestaVO>(){
		  public int compare(UsuarioEncuestaRespuestaVO o1, UsuarioEncuestaRespuestaVO o2){
		     return o1.getPregunta().getNuOrden().compareTo(o2.getPregunta().getNuOrden());
		  }
		});
		return uerListVO;
	} 

	@Transactional
	@Override
	public Map<String, Object> detalleFinalizar(List<UsuarioEncuestaRespuestaVO> l) {
		Map<Long, Long> idSecciones = new HashMap<>();
		for(UsuarioEncuestaRespuestaVO uerDTO: l) {
			idSecciones.put(uerDTO.getId().getIdSeccion(), uerDTO.getId().getIdSeccion());
		}
		List<SeccionDTO> sListDTO = new ArrayList<>();
		if(!idSecciones.isEmpty()) {
			for(Map.Entry<Long, Long> id: idSecciones.entrySet()) {
				sListDTO.add(seccionDAO.findOne(id.getKey()));
			}
		}
		
		List<SeccionVO> sListVO = ResponseConverter.converterLista(new ArrayList<>(), sListDTO, SeccionVO.class);
		List<PreguntaVO> pListVO = null; 
		if(!sListVO.isEmpty()) {
			for(SeccionVO sVO: sListVO) {
				pListVO = new ArrayList<>();
				for(UsuarioEncuestaRespuestaVO uerVO: l) {
					if(uerVO.getId().getIdSeccion().equals(sVO.getIdSeccion())) {
						pListVO.add(uerVO.getPregunta());
					}
				}
				//sVO.setPreguntas(pListVO);
				sVO.setPreguntas(orderList(pListVO)); // ORDENAMOS LA LISTA DE PREGUNTAS
				sVO.setPreguntas(sVO.getPreguntas()); // ORDENA LAS OPCIONES DE LAS PREGUNTAS
			}
		}
		Map<String, Object> mapReturn = new HashMap<>();
		mapReturn.put("secciones", sListVO);
		return mapReturn;
	}
	
	@Override
	public List<PreguntaVO> orderList(List<PreguntaVO> pListVO){
		Collections.sort(pListVO, new Comparator<PreguntaVO>(){
		  public int compare(PreguntaVO o1, PreguntaVO o2){
		     return o1.getNuOrden().compareTo(o2.getNuOrden());
		  }
		});
		for(PreguntaVO p : pListVO) {
			p.setOpciones(orderOpciones(p.getOpciones()));
		}
		return pListVO;
	}
	
	private List<OpcionVO> orderOpciones(List<OpcionVO> l){
		Collections.sort(l, new Comparator<OpcionVO>(){
			  public int compare(OpcionVO o1, OpcionVO o2){
			     return o1.getNuOrden().compareTo(o2.getNuOrden());
			  }
			});
		return l;
	}

}
