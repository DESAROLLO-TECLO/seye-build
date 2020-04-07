package mx.com.teclo.seye.negocio.service.reporte;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.transaction.Transactional;
import javax.xml.transform.TransformerException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lowagie.text.pdf.PdfCopyFields;
import com.lowagie.text.pdf.PdfReader;

import mx.com.teclo.arquitectura.ortogonales.exception.BusinessException;
import mx.com.teclo.arquitectura.ortogonales.exception.NotFoundException;
import mx.com.teclo.arquitectura.ortogonales.service.comun.UsuarioFirmadoService;
import mx.com.teclo.generaexcel.v2.PeticioReporteVO;
import mx.com.teclo.generaexcel.v2.PropiedadesReporte;
import mx.com.teclo.generaexcel.v2.ReporteExcel;
import mx.com.teclo.seye.negocio.service.encuesta.EncuentaService;
import mx.com.teclo.seye.persistencia.hibernate.dao.encuesta.ConfigReporteDAO;
import mx.com.teclo.seye.persistencia.hibernate.dao.encuesta.PreguntasDAO;
import mx.com.teclo.seye.persistencia.hibernate.dao.encuesta.UsuarioEncuestaRespuestaDAO;
import mx.com.teclo.seye.persistencia.hibernate.dao.usuarioencuesta.UsuAtributoDAO;
import mx.com.teclo.seye.persistencia.hibernate.dao.usuarioencuestaintentos.UsuarioEncuentaIntentoDAO;
import mx.com.teclo.seye.persistencia.hibernate.dto.encuesta.ConfigReporteDTO;
import mx.com.teclo.seye.persistencia.hibernate.dto.encuesta.OpcionesDTO;
import mx.com.teclo.seye.persistencia.hibernate.dto.encuesta.PreguntasDTO;
import mx.com.teclo.seye.persistencia.hibernate.dto.encuesta.UsuAtributoDTO;
import mx.com.teclo.seye.persistencia.hibernate.dto.encuesta.UsuarioEncuestaIntentosDTO;
import mx.com.teclo.seye.persistencia.hibernate.dto.encuesta.UsuaroEncuestaRespuestaDTO;
import mx.com.teclo.seye.persistencia.vo.encuesta.EncuestaGeneralVO;
import mx.com.teclo.seye.persistencia.vo.encuesta.EncuestaUsuarioIntentoPDFMVO;
import mx.com.teclo.seye.persistencia.vo.encuesta.ResumenReporteVO;
import mx.com.teclo.seye.persistencia.vo.encuesta.UsuarioEncuestaVO;
import mx.com.teclo.seye.persistencia.vo.reporte.ReporteTMPVO;
import mx.com.teclo.seye.persistencia.vo.reporte.ReporteVO;
import mx.com.teclo.seye.util.comun.RutinasTiempoImpl;
import mx.com.teclo.seye.util.enumerado.RespuestaHttp;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Service
public class ReporteServiceImpl implements ReporteService{

	@Autowired
	private RutinasTiempoImpl rutinasTiempo;
	
	@Autowired
	private ReporteExcel reporteExcel;
	
	@Autowired
	private EncuentaService encuentaService;
	
	@Autowired
	private ServletContext context;
	
	@Autowired
	private UsuarioFirmadoService userSession;
	
	@Autowired
	private UsuAtributoDAO usuAtributoDAO;
	
	@Autowired
	private UsuarioEncuentaIntentoDAO usuarioEncuentaIntentoDAO;
	
	@Autowired
	private UsuarioEncuestaRespuestaDAO usuarioEncuestaRespuestaDAO;
	
	@Autowired
	private PreguntasDAO preguntasDAO;
	
	@Autowired
	private ConfigReporteDAO configReporteDAO;
	
	private static final Logger logger = Logger.getLogger(ReporteServiceImpl.class);
	
	@Override
	public byte[] reporteEXCEL(ReporteVO vo) {
		ByteArrayOutputStream bo = null;
		byte [] byteReturn = null;
		List<Object> headers = new ArrayList<>(vo.getHeader());
		List<String> subtitulos = new ArrayList<String>();
		subtitulos.add("Fecha de Consulta: "+rutinasTiempo.getStringDateFromFormta("dd/MM/yyyy HH:ss", new Date()));
		subtitulos.add(" ");
		PropiedadesReporte pReporteVO = new PropiedadesReporte();
		pReporteVO.setTxTituloExcel(vo.getTitulo()==null?"Reporte_encuesta":vo.getTitulo());
		pReporteVO.setNbReporte(vo.getTitulo()==null?"Reporte_encuesta":vo.getTitulo());
		pReporteVO.setTxExtension(".xlsx");
		pReporteVO.setSubtitulos(subtitulos);
		List<Object> valuesArray = new ArrayList<>(vo.getValues());
		PeticioReporteVO peticionReporteVO = new PeticioReporteVO(pReporteVO, headers, valuesArray);
		try {
			bo = reporteExcel.generaReporte(peticionReporteVO);
		} catch (IOException e) {
			e.printStackTrace();
		}
		if(bo != null) {
			byteReturn = bo.toByteArray();
		}
		return byteReturn;
	}

	@Transactional
	@Override
	public ByteArrayOutputStream reportePDF(Long idUsuarioEncuesta, Long idIntento, Long idUsuario) throws NotFoundException, FileNotFoundException {
		
		Long idUsr = idUsuario == null ? userSession.getUsuarioFirmadoVO().getId() : idUsuario;
		EncuestaGeneralVO egVO = encuentaService.encuestaFolio(idUsuarioEncuesta, idUsuario);
		UsuarioEncuestaVO ueVO = egVO.getUsuarioEncuestaVO();
		UsuAtributoDTO uaDTO = usuAtributoDAO.usuario(idUsr);
		UsuarioEncuestaIntentosDTO ueiDTO = usuarioEncuentaIntentoDAO.usuarioEncuesta(ueVO.getIdUsuarioEncuesta(), idIntento);
		ConfigReporteDTO configReporteDTO = configReporteDAO.getConfiguracionPorIdEncuesta(ueiDTO.getUsuarioEncuesta().getEncuesta().getIdEncuesta());
		Map<String, Object> param = new HashMap<>();
		String nbUsuario = uaDTO.getUsuario().getNbUsuario() + " " + uaDTO.getUsuario().getNbApaterno()+ " " + uaDTO.getUsuario().getNbAmaterno();
		nbUsuario = camelCase(nbUsuario, " ");
//		String fh = rutinasTiempo.getStringDateFromFormta("dd/MM/yyyy HH:mm:ss", new Date());
		String fhIni = "";
		
		if(ueiDTO.getFhFin()!=null) {
			Map<String, String> fhIniDesglosada = rutinasTiempo.obtenerFechaDesglosada(ueiDTO.getFhInicio());
			StringBuilder sbFhIni = new StringBuilder();
			Object day = fhIniDesglosada.get("day");
			Object month = fhIniDesglosada.get("month"); 
			Object year = fhIniDesglosada.get("year");
			sbFhIni.append(day +" de ");
			sbFhIni.append(month +" del ");
			sbFhIni.append(year);
			fhIni = sbFhIni.toString();
		}else {
			fhIni = camelCase(ueiDTO.getStEncuesta().getNbStEncuesta(), " ");
		}
		
		Map<String, String> fhDesglosada = rutinasTiempo.obtenerFechaDesglosada(new Date());
		StringBuilder fechaActual = new StringBuilder();
		
		Object day = fhDesglosada.get("day");
		Object month = fhDesglosada.get("month"); 
		Object year = fhDesglosada.get("year");
		
		fechaActual.append(day +" de ");
		fechaActual.append(month +" del ");
		fechaActual.append(year);
		
		
		InputStream img1 = null;
		InputStream img2 = null;
		InputStream img3 = null;
		InputStream img4 = null;
		
		try {
			img1 = configReporteDTO.getLbImg1().getBinaryStream();
			img2 = configReporteDTO.getLbImg2().getBinaryStream();
			img3 = configReporteDTO.getLbImg3().getBinaryStream();
			img4 = configReporteDTO.getLbImg4() != null ? configReporteDTO.getLbImg4().getBinaryStream() : null;
		}catch (Exception e) {
			// TODO: handle exception
		}
		
		String cuerpo = configReporteDTO.getTxCuerpoEncuestaInd();
		
		
		
		String [][] reemplazos ={
				{":nbEvaluado", nbUsuario == null ? "" : nbUsuario},
				{":empPlaca", uaDTO.getUsuario().getCdUsuario()== null ? "" : "<b>"+ uaDTO.getUsuario().getCdUsuario()+ "</b>"},
				{":nbEncuesta", ueVO.getEncuesta().getNbEncuesta() == null ? "" : ueVO.getEncuesta().getNbEncuesta()},
				{":nbResultado",  ueiDTO.getStCalificacion().getNbStCalificacion() == null ? "" : ueiDTO.getStCalificacion().getNbStCalificacion()},
				{":nuCal", ueiDTO.getNuCalificacion().toString() == null ? "" : ueiDTO.getNuCalificacion().toString()},
				{":fhInicio", fhIni}};
		
		
//		cuerpo.replaceAll(":nbEvaluado", nbUsuario);
//		cuerpo.replaceAll(":empPlaca", uaDTO.getUsuario().getCdUsuario());
//		cuerpo.replaceAll(":nbEncuesta", camelCase(ueVO.getEncuesta().getNbEncuesta(), " "));
//		cuerpo.replaceAll(":nbRultado", camelCase(ueiDTO.getStCalificacion().getNbStCalificacion(), " "));
//		cuerpo.replaceAll(":nuCal", ueiDTO.getNuCalificacion().toString());
//		cuerpo.replaceAll(":fhInicio", fhIni);
		
		for(String[] remplazar: reemplazos){
		cuerpo = cuerpo.replace(remplazar[0], remplazar[1]);
		param.put("CUERPO", cuerpo);
		}
		
//		String img1 = context.getRealPath("/WEB-INF/imagenes/imagen1.png");
//		String img2 = context.getRealPath("/WEB-INF/imagenes/imagen2.png");
//				
//		InputStream img1File = new FileInputStream(img1);
//		InputStream img2File = new FileInputStream(img2);
		
		String jasper = context.getRealPath("/WEB-INF/reporte/resumenEncuesta/resumen_ensuesta.jasper");
		
		
		param.put("IMAGEN1", img1);
		param.put("IMAGEN2", img2);
		param.put("IMAGEN3", img3);
		param.put("IMAGEN4", img4);
		
		param.put("TITULO", configReporteDTO.getTxTituloEncuesta());
		param.put("FECHA_ACTUAL",fechaActual.toString());
		
		param.put("TIPO_DOCUMENTO", ueVO.getEncuesta().getTipoEncuesta().getNbTipoEncuesta());
		param.put("NOMBRE_DOCUMENTO", ueVO.getEncuesta().getNbEncuesta());
		param.put("NOMBRE", nbUsuario);
		param.put("FECHA", fhIni);
		param.put("SEDE", uaDTO.getSede().getNbSede());
		param.put("GRADO", uaDTO.getGrado().getNbGrado());
		param.put("PLACA", uaDTO.getUsuario().getCdUsuario());
		if(ueiDTO != null && ueiDTO.getNuPreguntasCorrectas() != null) {			
			param.put("ACIERTOS", ueiDTO.getNuPreguntasCorrectas() == null ? "---" : ueiDTO.getNuPreguntasCorrectas().toString());
			param.put("CALIFICACION", ueiDTO.getNuCalificacion() == null ? "---" : ueiDTO.getNuCalificacion().toString());
		}else {
			param.put("ACIERTOS", "0");
			param.put("CALIFICACION", "0");
		}
		
		// Obtener las preguntas actuales
		List<UsuaroEncuestaRespuestaDTO> uerListDTO = usuarioEncuestaRespuestaDAO.repuestas(ueiDTO.getIdUsuEncuIntento());
		
		param.put("FOLIO", (ueiDTO != null && ueiDTO.getUsuarioEncuesta() != null) ? ueiDTO.getUsuarioEncuesta().getEncuesta().getCdEncuesta():"---");
		param.put("PORCENTAJE", (ueiDTO != null && ueiDTO.getUsuarioEncuesta() != null) ? ueiDTO.getUsuarioEncuesta().getEncuesta().getNuCalificacionApro().toString() :"---");
		
		param.put("RESP_C", (ueiDTO!= null && ueiDTO.getNuPreguntasCorrectas() != null) ? ueiDTO.getNuPreguntasCorrectas().toString(): "---" );
		param.put("RESP_INC",(ueiDTO!= null && ueiDTO.getNuPreguntasCorrectas() != null) ? ueiDTO.getNuPreguntasIncorr().toString(): "---");
		param.put("RESULTADO", ueiDTO != null ? ueiDTO.getStEncuesta().getNbStEncuesta(): "SIN RESULTADO");
		param.put("PUNTOS_OBT","POR DEFINIR");
		
		Map<String, Object> map = count(uerListDTO);
		if(map != null) {
			Integer preguntas = (Integer) map.get("PREGUNTAS");
			Integer secciones = (Integer) map.get("SECCIONES");
			param.put("PREGUNTAS", preguntas != null ? preguntas.toString(): "0");	
			param.put("SECCIONES", secciones != null ? secciones.toString(): "0");
		}
		
		if(ueiDTO != null) {
			if(ueiDTO.getFhInicio() != null && ueiDTO.getFhFin() == null) {
				param.put("DURACION", "En curso");
			}else if(ueiDTO.getFhInicio() != null && ueiDTO.getFhFin() != null) {
				Map<String, Object> difFecha = rutinasTiempo.diferenciaFechas(ueiDTO.getFhInicio(), ueiDTO.getFhFin(), "dd/MM/yyyy HH:mm:ss");
				String days = (String) difFecha.get("days");
				String hours =(String) difFecha.get("hours");
				String minutes =(String) difFecha.get("minutes");
				String seconds =(String) difFecha.get("seconds");
				StringBuilder sb = new StringBuilder();
				if(days != null && !days.equals("0")) {
					sb.append(days + " días, ");
				}
				if(hours != null && !hours.equals("0")) {
					sb.append(hours + " horas, ");
				}
				if(minutes != null && !minutes.equals("0")) {
					sb.append(minutes + " minutos, ");
				}
				if(seconds != null && !seconds.equals("0")) {
					sb.append(seconds + " segundos");
				}
				param.put("DURACION", sb.toString());
			}
		}
		
		
		
		// AGREGAR LA LISTA DE PREGUNTAS RESPONDIDAS
		/*List<SeccionDTO> sListDTO = seccionDAO.seccionesEncuesta(ueVO.getEncuesta().getIdEncuesta());
		if(!sListDTO.isEmpty()) {
			List<SeccionVO> sListVO = ResponseConverter.converterLista(new ArrayList<>(), sListDTO, SeccionVO.class);
			if(!sListVO.isEmpty()) {
				for(SeccionVO sVO : sListVO) {
					List<PreguntasDTO> pListDTO = preguntasDAO.preguntas(sVO.getIdSeccion());
					List<PreguntaVO> pListVO = ResponseConverter.converterLista(new ArrayList<>(), pListDTO, PreguntaVO.class);
					sVO.setPreguntas(pListVO);
				}
				
				for(SeccionVO sVO : sListVO) {
					List<PreguntasDTO> pListDTO = preguntasDAO.preguntas(sVO.getIdSeccion());
					if(!pListDTO.isEmpty()) {
						for(PreguntasDTO pDTO : pListDTO) {
							UsuaroEncuestaRespuestaDTO uerDTO = 
									usuarioEncuestaRespuestaDAO.userEncuestaRespuesta(
											ueiDTO.getIdUsuEncuIntento(), 
											ueVO.getEncuesta().getIdEncuesta(), 
											sVO.getIdSeccion(), 
											pDTO.getIdPregunta());
							if(uerDTO != null) {
								rrListVO.add(new ResumenReporteVO( pDTO.getTxPregunta(), uerDTO.getOpcionesDTO().getTxOpcion()));
							}
						}
					}
				}
				
				JRBeanCollectionDataSource DS = new JRBeanCollectionDataSource(rrListVO);
				param.put("listPreguntas", DS);	
			}	
		}*/
		
		ByteArrayOutputStream reporte =  new ByteArrayOutputStream();
		try {
			reporte.write(JasperRunManager.runReportToPdf(jasper, param,new JREmptyDataSource()));
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return reporte;
	}

	@Override
	public byte[] getPDF(ByteArrayOutputStream b) {
		if(b != null)
			return b.toByteArray();
		return null;
	}
	
	private Map<String, Object> count(List<UsuaroEncuestaRespuestaDTO> uerListDTO){
		if(uerListDTO == null)
			return null;
		Map<String, Object> mapReturn = new HashMap<>();
		mapReturn.put("PREGUNTAS", uerListDTO.size());
		
		
		Map<String, Object> secciones = new HashMap<>();
		for(UsuaroEncuestaRespuestaDTO uerDTO: uerListDTO) {
			secciones.put(uerDTO.getId().getIdSeccion().toString(), uerDTO.getId().getIdSeccion());
		}
		if(!secciones.isEmpty()) {
			mapReturn.put("SECCIONES", secciones.size());
		}
		
		return mapReturn;
	}

	@Transactional
	@Override
	public ByteArrayOutputStream reportePDFCertificado(Long idUsuEncuIntento, Long idUsuario) throws NotFoundException, FileNotFoundException {
		UsuarioEncuestaIntentosDTO ueiDTO = usuarioEncuentaIntentoDAO.getIntentoById(idUsuEncuIntento);
		if(ueiDTO == null)
			throw new NotFoundException(RespuestaHttp.NOT_FOUND.getMessage());
		
		Long idUsr = idUsuario;
		UsuAtributoDTO uaDTO = usuAtributoDAO.usuario(idUsr);
		String nbUsuario = uaDTO.getUsuario().getNbUsuario() + " " + uaDTO.getUsuario().getNbApaterno()+ " " + uaDTO.getUsuario().getNbAmaterno();
		
		nbUsuario = camelCase(nbUsuario, " ");
		
		ConfigReporteDTO configReporteDTO = configReporteDAO.getConfiguracionPorIdEncuesta(ueiDTO.getUsuarioEncuesta().getEncuesta().getIdEncuesta());
		
		InputStream img1 = null;
		InputStream img2 = null;
		InputStream img3 = null;
		InputStream img4 = null;
		
		try {
			img1 = configReporteDTO.getLbImg1().getBinaryStream();
			img2 = configReporteDTO.getLbImg2().getBinaryStream();
			img3 = configReporteDTO.getLbImg3().getBinaryStream();
			img4 = configReporteDTO.getLbImg4() != null ? configReporteDTO.getLbImg4().getBinaryStream() : null;
		}catch (Exception e) {
			// TODO: handle exception
		}
		
		String cuerpo = configReporteDTO.getTxCuerpoEncuestaCert();
		
		String jasper = context.getRealPath("/WEB-INF/reporte/resumenConstancia/resumen_constancia.jasper");
		Map<String, Object> param = new HashMap<>();
		param.put("NOMBRE", nbUsuario);
		param.put("NOMBRE_DOCUMENTO", ueiDTO.getUsuarioEncuesta().getEncuesta().getNbEncuesta());
		param.put("RESULTADO", ueiDTO != null ? ueiDTO.getStEncuesta().getNbStEncuesta(): "SIN RESULTADO");
		
		String fhInicio="";
		//FORMATEAR FECHAS
		if(ueiDTO.getFhFin() != null) {
			Map<String, String> fhDesglosada = rutinasTiempo.obtenerFechaDesglosada(ueiDTO.getFhInicio());
			StringBuilder sb = new StringBuilder();
			Object day = fhDesglosada.get("day");
			Object month = fhDesglosada.get("month"); 
			Object year = fhDesglosada.get("year");
			sb.append(day +" de ");
			sb.append(month +" del ");
			sb.append(year);
			param.put("FECHA", sb.toString());
			cuerpo.replace(":fhInicio", sb.toString());
			param.put("FECHA", sb.toString());
			fhInicio = sb.toString();
		}else {
			param.put("FECHA", " No Concluido");
			fhInicio = camelCase(ueiDTO.getStEncuesta().getNbStEncuesta(), " ");
		}
		
		String [][] reemplazos ={
				{":nbEvaluado", nbUsuario == null ? "" : nbUsuario},
				{":nbEncuesta", ueiDTO.getUsuarioEncuesta().getEncuesta().getNbEncuesta()},
				{":nbResultado",  ueiDTO.getStCalificacion().getNbStCalificacion() == null ? "" :  ueiDTO.getStCalificacion().getNbStCalificacion(), ueiDTO.getStEncuesta().getNbStEncuesta()},
				{":nuCal", ueiDTO.getNuCalificacion().toString() == null ? "" : ueiDTO.getNuCalificacion().toString()},
				{":fhInicio", fhInicio}};
		
		
		for(String[] remplazar: reemplazos){
			cuerpo = cuerpo.replace(remplazar[0], remplazar[1]);
		}
		
		Map<String, String> fhDesglosada = rutinasTiempo.obtenerFechaDesglosada(new Date());
		StringBuilder sb = new StringBuilder();
		
		Object day = fhDesglosada.get("day");
		Object month = fhDesglosada.get("month"); 
		Object year = fhDesglosada.get("year");
		
		sb.append(day +" de ");
		sb.append(month +" del ");
		sb.append(year);
		param.put("FECHA_ACTUAL", sb.toString());
		
		param.put("IMAGEN1", img1);
		param.put("IMAGEN2", img2);
		param.put("IMAGEN3", img3);
		param.put("IMAGEN4", img4);
		
		param.put("TITULO", configReporteDTO.getTxTituloEncuesta());
		param.put("CUERPO", cuerpo);
		
		ByteArrayOutputStream reporte =  new ByteArrayOutputStream();
		try {
			reporte.write(JasperRunManager.runReportToPdf(jasper, param,new JREmptyDataSource()));
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return reporte;
	}

	@Transactional
	@Override
	public ReporteTMPVO evaluaReporte(Long idUsuEncuIntento, Long idUsuario, String opcion) throws BusinessException, NotFoundException, FileNotFoundException, SQLException{
		ByteArrayOutputStream bao = null;
		ReporteTMPVO objectReturn = new ReporteTMPVO();
		if(opcion == null)
			throw new BusinessException("Se desconoce el tipo de reporte que se quiere generar, favor de validar");
		switch (opcion) {
		case  "C":
			bao = reportePDFCertificado(idUsuEncuIntento, idUsuario);
			objectReturn.setBao(bao);
			objectReturn.setFileName("Reporte Constancia");
			break;
		case  "P":
			bao = reportePDFPreguntas(idUsuEncuIntento, idUsuario);
			objectReturn.setBao(bao);
			objectReturn.setFileName("Reporte Preguntas");
			break;
		default:
			throw new BusinessException("Se desconoce el tipo de reporte que se quiere generar, favor de validar");
		}
		return objectReturn;
	}
	
	@Override
	public String camelCase(String text, String separador) {
		if(text == null || separador == null)
			return "--";
        String start = text;
        StringBuffer sb = new StringBuffer();
        String [] strSplit = start.split(separador);
        for (String s : strSplit) {
        	if(s != null && !s.equals("")) {
        		sb.append(Character.toUpperCase(s.charAt(0)));
                if (s.length() > 1) {
                    sb.append(s.substring(1, s.length()).toLowerCase());
                    sb.append(" ");
                }
        	}
        }
        return sb.toString();
    }

	@Override
	public ByteArrayOutputStream reportePDFPreguntas(Long idUsuEncuIntento, Long idUsuario) throws NotFoundException, FileNotFoundException, SQLException {
		UsuarioEncuestaIntentosDTO ueiDTO = usuarioEncuentaIntentoDAO.getIntentoById(idUsuEncuIntento);
		if(ueiDTO == null)
			throw new NotFoundException(RespuestaHttp.NOT_FOUND.getMessage());
		
		Long idUsr = idUsuario;
		UsuAtributoDTO uaDTO = usuAtributoDAO.usuario(idUsr);
		String nbUsuario = uaDTO.getUsuario().getNbUsuario() + " " + uaDTO.getUsuario().getNbApaterno()+ " " + uaDTO.getUsuario().getNbAmaterno();
		InputStream imgFile1=null;
		InputStream imgFile2=null;
		InputStream imgFile3=null;
		InputStream imgCorrecto=null;
		InputStream imgNoCorrecto=null;
		InputStream imgContestada=null;
		
		nbUsuario = camelCase(nbUsuario, " ");
		
		ConfigReporteDTO configReporteDTO = configReporteDAO.getConfiguracionPorIdEncuesta(ueiDTO.getUsuarioEncuesta().getEncuesta().getIdEncuesta());
	    try
	    {
		    imgFile1 = configReporteDTO.getLbImg1().getBinaryStream();
		    imgFile2 = configReporteDTO.getLbImg2().getBinaryStream();
			imgFile3 = configReporteDTO.getLbImg3().getBinaryStream();
		    imgCorrecto = configReporteDTO.getLbImg5().getBinaryStream();
		    imgNoCorrecto = configReporteDTO.getLbImg6().getBinaryStream();
		    imgContestada = configReporteDTO.getLbImg7().getBinaryStream();
	    }
	    catch(Exception exception)
	    {
	   
	    }
		
		
//		String img1 = context.getRealPath("/WEB-INF/imagenes/imagen1.png");
//		String img2 = context.getRealPath("/WEB-INF/imagenes/imagen2.png");
//		
//		InputStream img1File = new FileInputStream(img1);
//		InputStream img2File = new FileInputStream(img2);
		
		String jasper = context.getRealPath("/WEB-INF/reporte/resumenPreguntas/resumen_preguntas.jasper");
		Map<String, Object> param = new HashMap<>();
		param.put("NOMBRE", nbUsuario);
		param.put("TIPO_DOCUMENTO", ueiDTO.getUsuarioEncuesta().getEncuesta().getTipoEncuesta().getNbTipoEncuesta());
		param.put("NOMBRE_DOCUMENTO", ueiDTO.getUsuarioEncuesta().getEncuesta().getNbEncuesta());
		//FORMATEAR FECHAS
		if(ueiDTO.getFhFin() != null) {
			Map<String, String> fhDesglosada = rutinasTiempo.obtenerFechaDesglosada(ueiDTO.getFhInicio());
			StringBuilder sb = new StringBuilder();
			Object day = fhDesglosada.get("day");
			Object month = fhDesglosada.get("month"); 
			Object year = fhDesglosada.get("year");
			sb.append(day +" de ");
			sb.append(month +" del ");
			sb.append(year);
			param.put("FECHA", sb.toString());
		}else {
			param.put("FECHA", " No Concluido");
		}
		param.put("SEDE", uaDTO.getSede().getNbSede());
		param.put("GRADO",uaDTO.getGrado().getNbGrado());
		param.put("PLACA",uaDTO.getUsuario().getCdUsuario());
		param.put("RESULTADO", ueiDTO != null ? ueiDTO.getStCalificacion().getNbStCalificacion(): "Sin Resultado");
		
		if(ueiDTO != null && ueiDTO.getNuPreguntasCorrectas() != null) {			
			param.put("ACIERTOS", ueiDTO.getNuPreguntasCorrectas() == null ? "---" : ueiDTO.getNuPreguntasCorrectas().toString());
			param.put("CALIFICACION", ueiDTO.getNuCalificacion() == null ? "---" : ueiDTO.getNuCalificacion().toString());
		}else {
			param.put("ACIERTOS", "0");
			param.put("CALIFICACION", "0");
		}
		
		// Obtener las preguntas actuales
		List<UsuaroEncuestaRespuestaDTO> uerListDTO = usuarioEncuestaRespuestaDAO.repuestas(ueiDTO.getIdUsuEncuIntento());
		uerListDTO = preguntas(uerListDTO);
		List<ResumenReporteVO> rrListVO = null;
		ResumenReporteVO rrVO = null;
		
		if(!uerListDTO.isEmpty())
			rrListVO = new ArrayList<>();
		
		for(UsuaroEncuestaRespuestaDTO uerDTO: uerListDTO) {
//			PreguntasDTO pDTO = preguntasDAO.pregunta(uerDTO.getId().getIdPregunta());
			PreguntasDTO pDTO = uerDTO.getPregunta();
			rrVO = new ResumenReporteVO();
			rrVO.setPregunta(pDTO.getTxPregunta());
			rrVO.setNuOrden(pDTO.getNuOrden());
			if(uerDTO.getOpcionesDTO() != null) {
				rrVO.setOpcion(uerDTO.getOpcionesDTO().getTxOpcion());
				if(uerDTO.getStCorrecto()==1) {
					rrVO.setRespuesta(imgCorrecto);
				}
				else if(uerDTO.getStCorrecto()==0) {
					if(ueiDTO.getStCalificacion().getCdStCalificacion().equals("SC")) {
						rrVO.setRespuesta(imgContestada);
					}else {
						rrVO.setRespuesta(imgNoCorrecto);
						rrVO.setOpcionCorrecta(obtenerOpcionCorecta(pDTO.getOpciones()));
					}
				}
			}else {
				rrVO.setOpcion("NO SE MARCÓ RESPUESTA");
				rrVO.setRespuesta(null);
			}
			rrListVO.add(rrVO);
		}
		JRBeanCollectionDataSource DS = new JRBeanCollectionDataSource(ordenP(rrListVO));
		param.put("listPreguntas", DS);	
		
		param.put("IMAGEN1", imgFile1);
		param.put("IMAGEN2", imgFile2);
		param.put("IMAGEN3", imgFile3);
		
		ByteArrayOutputStream reporte =  new ByteArrayOutputStream();
		try {
			reporte.write(JasperRunManager.runReportToPdf(jasper, param,new JREmptyDataSource()));
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return reporte;
	}
	
	private String obtenerOpcionCorecta(List<OpcionesDTO> lista) {
		OpcionesDTO opC = new OpcionesDTO();
		for(OpcionesDTO op : lista) {
			if(op.getStCorrecto()==1) {
				opC = op;
			}
		}
		return opC.getTxOpcion();
	}
	
	@Transactional
	private List<UsuaroEncuestaRespuestaDTO> preguntas (List<UsuaroEncuestaRespuestaDTO> l){
		if(l.isEmpty())
			return l;
		for(UsuaroEncuestaRespuestaDTO uerDTO: l) {
			PreguntasDTO pDTO = preguntasDAO.pregunta(uerDTO.getId().getIdPregunta(), false);
//			PreguntasDTO pDTO = preguntasDAO.findOne(uerDTO.getId().getIdPregunta());
			uerDTO.setPregunta(pDTO);
		}
		return orden(l);
	} //uerListDTO = usuarioEncuestaRespuestaDAO.repuestas(ueiDTO.getIdUsuEncuIntento());
	
	
	private List<UsuaroEncuestaRespuestaDTO> orden(List<UsuaroEncuestaRespuestaDTO> l){
		Collections.sort(l, new Comparator<UsuaroEncuestaRespuestaDTO>(){
		  public int compare(UsuaroEncuestaRespuestaDTO o1, UsuaroEncuestaRespuestaDTO o2){
		     return o1.getPregunta().getNuOrden().compareTo(o2.getPregunta().getNuOrden());
		  }
		});
		return l;
	}
	
	private List<ResumenReporteVO> ordenP(List<ResumenReporteVO> l){
		Collections.sort(l, new Comparator<ResumenReporteVO>(){
		  public int compare(ResumenReporteVO o1, ResumenReporteVO o2){
		     return o1.getNuOrden().compareTo(o2.getNuOrden());
		  }
		});
		return l;
	}
	
	@Transactional
	@Override
	public ReporteTMPVO generaPDFMultiple(List<EncuestaUsuarioIntentoPDFMVO> listaEncuestaUsuarioIntentoPDFMVO) {
		ReporteTMPVO reporteFinalTMPVO = new ReporteTMPVO();
		ByteArrayOutputStream archivoFinal = new ByteArrayOutputStream();
		Integer vuelta = 0;
		String rutaTemp = "C:\\evaluaciones\\reporteMultiple\\";
		for (EncuestaUsuarioIntentoPDFMVO encuestaUsuarioIntentoPDFMVO : listaEncuestaUsuarioIntentoPDFMVO) {
			Long idUsuEncuIntento = encuestaUsuarioIntentoPDFMVO.getIdUsuEncuIntento();
			Long idUsuario = encuestaUsuarioIntentoPDFMVO.getIdUsuario(); 
			String opcion = encuestaUsuarioIntentoPDFMVO.getOpcion();
			
			ReporteTMPVO rTMPVO;
			try {
				rTMPVO = evaluaReporte(idUsuEncuIntento, idUsuario, opcion);
				//byte [] byteToReturn = getPDF(rTMPVO.getBao());
				
				if(vuelta > 0) {
					archivoFinal = unirPDF(archivoFinal, rTMPVO.getBao());
				}else {
					archivoFinal = rTMPVO.getBao();
				}
				reporteFinalTMPVO.setFileName(rTMPVO.getFileName());
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (BusinessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			vuelta++;
		}
		
		reporteFinalTMPVO.setBao(archivoFinal);
		return reporteFinalTMPVO;
	}
	
	public ByteArrayOutputStream unirPDF(ByteArrayOutputStream archivo1, ByteArrayOutputStream archivo2) {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		try {
			byte [] bytearchivo1 = getPDF(archivo1);
			byte [] byteArchivo2 = getPDF(archivo2);
			
			PdfReader reader1 = new PdfReader(bytearchivo1);
			PdfReader reader2 = new PdfReader(byteArchivo2);
			PdfCopyFields copy = new PdfCopyFields(out);
			copy.addDocument(reader1);
			copy.addDocument(reader2);
			copy.close();
			//archivoPDFconcat=convertDocToByteArray(archivoTemporal);
			//new File(archivoTemporal).delete();
		} catch (Exception e) {
			System.out.println(e);
		}
		return out;
	}

	public byte[] convertDocToByteArray(String sourcePath) {
		byte[] byteArray = null;
		
		//File file = null;
		//File tempFile = File.createTempFile("mificherotemporal",null);
		//tempFile.deleteOnExit();
//		BufferedWriter out = new BufferedWriter(new FileWriter(tempFile));
//		out.write("Esto es un fichero temporal");
//		out.close();
		try {
			File tempFile = File.createTempFile("mificherotemporal",null);
			tempFile = new File(sourcePath);
			byteArray = new byte[(int) tempFile.length()];
			FileInputStream fis=new FileInputStream(tempFile);
			fis.read(byteArray);
			fis.close();
			tempFile.deleteOnExit();
		} catch (FileNotFoundException e) {
			System.out.println("File Not found" + e);
		} catch (IOException e) {
			System.out.println("IO Ex" + e);
		}
		return byteArray;
	}
	
	public void unirPDFByteArray() {
		
	}
}
