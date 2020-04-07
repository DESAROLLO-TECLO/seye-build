package mx.com.teclo.seye.negocio.service.catalogo;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.com.teclo.arquitectura.ortogonales.exception.NotFoundException;
import mx.com.teclo.arquitectura.ortogonales.util.ResponseConverter;
import mx.com.teclo.seye.persistencia.hibernate.dao.catalogo.ConfiguracionParamDAO;
import mx.com.teclo.seye.persistencia.hibernate.dao.catalogo.EstatusCalificacionDAO;
import mx.com.teclo.seye.persistencia.hibernate.dao.catalogo.GradoDAO;
import mx.com.teclo.seye.persistencia.hibernate.dao.catalogo.GrupoDAO;
import mx.com.teclo.seye.persistencia.hibernate.dao.catalogo.PuestoDAO;
import mx.com.teclo.seye.persistencia.hibernate.dao.catalogo.SedeDAO;
import mx.com.teclo.seye.persistencia.hibernate.dao.catalogo.StEncuestaDAO;
import mx.com.teclo.seye.persistencia.hibernate.dao.catalogo.TipoEncuestaDAO;
import mx.com.teclo.seye.persistencia.hibernate.dao.catalogo.TipoPreguntaDAO;
import mx.com.teclo.seye.persistencia.hibernate.dto.catalogo.ConfiguracionDTO;
import mx.com.teclo.seye.persistencia.hibernate.dto.catalogo.GradoDTO;
import mx.com.teclo.seye.persistencia.hibernate.dto.catalogo.GrupoDTO;
import mx.com.teclo.seye.persistencia.hibernate.dto.catalogo.PuestoDTO;
import mx.com.teclo.seye.persistencia.hibernate.dto.catalogo.SedeDTO;
import mx.com.teclo.seye.persistencia.hibernate.dto.catalogo.StEncuestaDTO;
import mx.com.teclo.seye.persistencia.hibernate.dto.catalogo.TipoEncuestaDTO;
import mx.com.teclo.seye.persistencia.hibernate.dto.catalogo.TipoPreguntaDTO;
import mx.com.teclo.seye.persistencia.hibernate.dto.encuesta.EstatusCalificacionDTO;
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

@Service
public class CatalogoServiceImpl implements CatalogoService{

	@Autowired
	private TipoEncuestaDAO tipoEncuestaDAO;
	
	@Autowired
	private TipoPreguntaDAO tipoPreguntaDAO;
	
	@Autowired
	private GradoDAO gradoDAO;
	
	@Autowired
	private PuestoDAO puestoDAO;
	
	@Autowired
	private SedeDAO sedeDAO;
	
	@Autowired
	private GrupoDAO grupoDAO;
	
	@Autowired
	private StEncuestaDAO stEncuestaDAO;
	
	@Autowired
	private EstatusCalificacionDAO estatusCalificacionDAO;
	
	@Autowired
	private ConfiguracionParamDAO configuracionDAO;
	
	@Transactional
	@Override
	public List<TipoEncuestaVO> tipoEncuesta() throws NotFoundException{
		List<TipoEncuestaDTO> a1 =  tipoEncuestaDAO.tipoEncuesta();
		if(a1.isEmpty())
			throw new NotFoundException(RespuestaHttp.NOT_FOUND.getMessage());
		List<TipoEncuestaVO> listReturn = ResponseConverter.converterLista(new ArrayList<>(), a1, TipoEncuestaVO.class);
		return listReturn;
	}

	@Transactional
	@Override
	public List<TipoPreguntaVO> tipoPregunta() throws NotFoundException {
		List<TipoPreguntaDTO> a1 =  tipoPreguntaDAO.tipoPregunta();
		if(a1.isEmpty())
			throw new NotFoundException(RespuestaHttp.NOT_FOUND.getMessage());
		List<TipoPreguntaVO> listReturn = ResponseConverter.converterLista(new ArrayList<>(), a1, TipoPreguntaVO.class);
		return listReturn;
	}

	@Transactional
	@Override
	public List<GradoVO> grado() throws NotFoundException {
		List<GradoDTO> a1 =  gradoDAO.grado();
		if(a1.isEmpty())
			throw new NotFoundException(RespuestaHttp.NOT_FOUND.getMessage());
		List<GradoVO> listReturn = ResponseConverter.converterLista(new ArrayList<>(), a1, GradoVO.class);
		return listReturn;
	}

	@Transactional
	@Override
	public List<PuestoVO> puesto() throws NotFoundException{
		List<PuestoDTO> a1 =  puestoDAO.puesto();
		if(a1.isEmpty())
			throw new NotFoundException(RespuestaHttp.NOT_FOUND.getMessage());
		List<PuestoVO> listReturn = ResponseConverter.converterLista(new ArrayList<>(), a1, PuestoVO.class);
		return listReturn;
	}

	@Transactional
	@Override
	public List<SedeVO> sede()  throws NotFoundException{
		List<SedeDTO> a1 =  sedeDAO.sede();
		if(a1.isEmpty())
			throw new NotFoundException(RespuestaHttp.NOT_FOUND.getMessage());
		List<SedeVO> listReturn = ResponseConverter.converterLista(new ArrayList<>(), a1, SedeVO.class);
		return listReturn;
	}

	@Transactional
	@Override
	public List<GrupoVO> grupo()  throws NotFoundException{
		List<GrupoDTO> a1 =  grupoDAO.grupo();
		if(a1.isEmpty())
			throw new NotFoundException(RespuestaHttp.NOT_FOUND.getMessage());
		List<GrupoVO> listReturn = ResponseConverter.converterLista(new ArrayList<>(), a1, GrupoVO.class);
		return listReturn;
	}

	@Transactional
	@Override
	public List<StEncuestaVO> stEncuesta() throws NotFoundException {
		List<StEncuestaDTO> a1 =  stEncuestaDAO.stEncuesta();
		if(a1.isEmpty())
			throw new NotFoundException(RespuestaHttp.NOT_FOUND.getMessage());
		List<StEncuestaVO> listReturn = ResponseConverter.converterLista(new ArrayList<>(), a1, StEncuestaVO.class);
		return listReturn;
	}

	@Transactional
	@Override
	public List<EstatusCalificacionVO> calificacion() throws NotFoundException {
		List<EstatusCalificacionDTO> a1 =  estatusCalificacionDAO.calificacion();
		if(a1.isEmpty())
			throw new NotFoundException(RespuestaHttp.NOT_FOUND.getMessage());
		List<EstatusCalificacionVO> listReturn = ResponseConverter.converterLista(new ArrayList<>(), a1, EstatusCalificacionVO.class);
		return listReturn;
	}

	@Transactional
	@Override
	public List<ConfiguracionVO> configuracion() throws NotFoundException{
		List<ConfiguracionDTO> a1 =  configuracionDAO.configuracion();
		if(a1.isEmpty())
			throw new NotFoundException(RespuestaHttp.NOT_FOUND.getMessage());
		List<ConfiguracionVO> listReturn = ResponseConverter.converterLista(new ArrayList<>(), a1, ConfiguracionVO.class);
		return listReturn;
	}

	@Transactional
	@Override
	public ConfiguracionVO configuracion(String cdLlavePConfig) throws NotFoundException{
		ConfiguracionVO voReturn = null;
		ConfiguracionDTO a1 =  configuracionDAO.configuracion(cdLlavePConfig);
		if(a1 == null)
			throw new NotFoundException(RespuestaHttp.NOT_FOUND.getMessage());
		voReturn = new ConfiguracionVO();
		ResponseConverter.copiarPropriedades(voReturn, a1);
		return voReturn;
	}

}
