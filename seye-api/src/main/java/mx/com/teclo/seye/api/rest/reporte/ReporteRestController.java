package mx.com.teclo.seye.api.rest.reporte;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import mx.com.teclo.arquitectura.ortogonales.exception.BusinessException;
import mx.com.teclo.arquitectura.ortogonales.exception.NotFoundException;
import mx.com.teclo.seye.negocio.service.reporte.ReporteService;
import mx.com.teclo.seye.persistencia.vo.encuesta.EncuestaUsuarioIntentoPDFMVO;
import mx.com.teclo.seye.persistencia.vo.encuesta.UserRespuestaVO;
import mx.com.teclo.seye.persistencia.vo.reporte.ReporteTMPVO;
import mx.com.teclo.seye.persistencia.vo.reporte.ReporteVO;

@RestController
@RequestMapping("/reporte")
public class ReporteRestController {

	@Autowired
	private ReporteService reporteService;
	
	@RequestMapping(value="/encuesta", method = RequestMethod.GET)
	@PreAuthorize("hasAnyAuthority('SERVICE15_REP_ENCUESTA')")
	public ResponseEntity<byte[]> encuesta (@RequestParam("idUsuarioEncuesta") Long idUsuarioEncuesta, @RequestParam("idIntento") Long idIntento, @RequestParam("idUsuario") Long idUsuario) throws NotFoundException, FileNotFoundException{
		idUsuario = idUsuario == 0 ? null : idUsuario;
		ByteArrayOutputStream os = reporteService.reportePDF(idUsuarioEncuesta, idIntento, idUsuario);
		byte [] byteToReturn = reporteService.getPDF(os);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.parseMediaType("application/pdf"));
		headers.add("Content-Disposition", "attachmnt; filename =Resumen_encuesta.pdf");
		headers.add("filename", "Resumen_encuesta.pdf");
		headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
		headers.setContentLength(byteToReturn.length);
		return new ResponseEntity<byte[]>(byteToReturn, headers, HttpStatus.OK);
	}
	
	@RequestMapping(value="/usuario", method = RequestMethod.POST)
	@PreAuthorize("hasAnyAuthority('SERVICE7_REP_USUARIO')")
	public ResponseEntity<byte[]> reporteUsuario (@RequestBody ReporteVO vo){
		byte [] byteToReturn = reporteService.reporteEXCEL(vo);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.parseMediaType("application/vnd.ms-excel"));
		
		String fileName = vo.getTitulo() != null ? vo.getTitulo(): "Reporte_encuesta"; 
		
		headers.add("Content-Disposition", "attachment; filename="+fileName+".xlsx");
		headers.add("filename", fileName+".xlsx");
		headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
		headers.setContentLength(byteToReturn.length);
		return new ResponseEntity<byte[]>(byteToReturn, headers, HttpStatus.OK);	
	}
	
	@RequestMapping(value="/pdf", method = RequestMethod.GET)
	@PreAuthorize("hasAnyAuthority('SERVICE15_REP_ENCUESTA')")
	public ResponseEntity<byte[]> pdf (
			@RequestParam("idUsuEncuIntento") Long idUsuEncuIntento, 
			@RequestParam("idUsuario") Long idUsuario,
			@RequestParam("opcion") String opcion
		) throws NotFoundException, FileNotFoundException, BusinessException,SQLException{
		ReporteTMPVO rTMPVO = reporteService.evaluaReporte(idUsuEncuIntento, idUsuario, opcion);
		byte [] byteToReturn = reporteService.getPDF(rTMPVO.getBao());
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.parseMediaType("application/pdf"));
		headers.add("Content-Disposition", "attachmnt; filename ="+rTMPVO.getFileName()+".pdf");
		headers.add("filename", rTMPVO.getFileName()+".pdf");
		headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
		headers.setContentLength(byteToReturn.length);
		return new ResponseEntity<byte[]>(byteToReturn, headers, HttpStatus.OK);
	}
	
	@RequestMapping(value="/pdfMultiple", method = RequestMethod.POST)
	@PreAuthorize("hasAnyAuthority('SERVICE15_REP_ENCUESTA')")
	public ResponseEntity<byte[]> pdfMasivo (
			@RequestBody List<EncuestaUsuarioIntentoPDFMVO> listaEncuestaUsuarioIntentoPDFMVO
			//@RequestParam("listaEncuestaUsuarioIntentoPDFMVO") List<EncuestaUsuarioIntentoPDFMVO> listaEncuestaUsuarioIntentoPDFMVO
		) throws NotFoundException, FileNotFoundException, BusinessException,SQLException{
		ReporteTMPVO rTMPVO = reporteService.generaPDFMultiple(listaEncuestaUsuarioIntentoPDFMVO);
		byte [] byteArchivo = reporteService.getPDF(rTMPVO.getBao());
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.parseMediaType("application/pdf"));
		headers.add("Content-Disposition", "attachmnt; filename ="+rTMPVO.getFileName()+".pdf");
		headers.add("filename", rTMPVO.getFileName()+".pdf");
		headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
		headers.setContentLength(byteArchivo.length);
		return new ResponseEntity<byte[]>(byteArchivo, headers, HttpStatus.OK);
	}
}
