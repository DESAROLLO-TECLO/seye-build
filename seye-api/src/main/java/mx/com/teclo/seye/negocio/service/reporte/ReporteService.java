package mx.com.teclo.seye.negocio.service.reporte;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.List;

import mx.com.teclo.arquitectura.ortogonales.exception.BusinessException;
import mx.com.teclo.arquitectura.ortogonales.exception.NotFoundException;
import mx.com.teclo.seye.persistencia.vo.encuesta.EncuestaUsuarioIntentoPDFMVO;
import mx.com.teclo.seye.persistencia.vo.reporte.ReporteTMPVO;
import mx.com.teclo.seye.persistencia.vo.reporte.ReporteVO;

public interface ReporteService {

	/**
	 * @Descripción: Reporte en excel del resumen de la 
	 * encuesta del usuario
	 * @author jorgeluis
	 * @param ReporteVO
	 * @return byte[]>
	 */
	public byte[] reporteEXCEL(ReporteVO vo);
	
	/**
	 * @Descripción: Reporte en PDF del resumen de la 
	 * evaluación del usuario
	 * @author jorgeluis
	 * @param idUsuarioEncuesta
	 * @param idIntento
	 * @return ByteArrayOutputStream
	 */
	public ByteArrayOutputStream reportePDF(Long idUsuarioEncuesta, Long idIntento, Long idUsuario) throws NotFoundException, FileNotFoundException;
	
	/**
	 * @Descripción: Método para extraer los bytes del
	 * reporte en PDF
	 * @author jorgeluis
	 * @return byte[]>
	 */
	public byte[] getPDF (ByteArrayOutputStream b) ;
	
	/**
	 * @Descripción: Reporte en PDF del resumen y constancia del
	 * twermino de la evaluación
	 * @author jorgeluis
	 * @param idUsuEncuIntento
	 * @return ByteArrayOutputStream
	 */
	public ByteArrayOutputStream reportePDFCertificado(Long idUsuEncuIntento, Long idUsuario) throws NotFoundException, FileNotFoundException;
	
	/**
	 * @Descripción: Reporte en PDF del resumen de preguntas y respuestas
	 * de la encuesta
	 * @author jorgeluis
	 * @param idUsuEncuIntento
	 * @return ByteArrayOutputStream
	 */
	public ByteArrayOutputStream reportePDFPreguntas(Long idUsuEncuIntento, Long idUsuario) throws NotFoundException, FileNotFoundException, SQLException;
	
	/**
	 * Descripción: Método para generar los PDF de constacia o preguntas,
	 * evaluando la opción según enviado de la petición HTTP
	 * @author jorgeluis
	 * @return ReporteTMPVO
	 */
	public ReporteTMPVO evaluaReporte (Long idUsuEncuIntento, Long idUsuario, String opcion)throws BusinessException, NotFoundException,FileNotFoundException,SQLException;
	
	public String camelCase(String text, String separador);
	
	/**
	 * Descripción: Método para generar los PDF multiples y unirlos en uno solo,
	 * evaluando la opción según enviado de la petición HTTP
	 * @author joseCastillo
	 * @return ReporteTMPVO
	 */
	public ReporteTMPVO generaPDFMultiple(List<EncuestaUsuarioIntentoPDFMVO> listaEncuestaUsuarioIntentoPDFMVO);
}
