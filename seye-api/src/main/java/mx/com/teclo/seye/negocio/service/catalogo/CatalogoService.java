package mx.com.teclo.seye.negocio.service.catalogo;

import java.util.List;

import mx.com.teclo.arquitectura.ortogonales.exception.NotFoundException;
import mx.com.teclo.seye.persistencia.vo.catalogo.ConfiguracionVO;
import mx.com.teclo.seye.persistencia.vo.catalogo.EstatusCalificacionVO;
import mx.com.teclo.seye.persistencia.vo.catalogo.GradoVO;
import mx.com.teclo.seye.persistencia.vo.catalogo.GrupoVO;
import mx.com.teclo.seye.persistencia.vo.catalogo.PuestoVO;
import mx.com.teclo.seye.persistencia.vo.catalogo.SedeVO;
import mx.com.teclo.seye.persistencia.vo.catalogo.StEncuestaVO;
import mx.com.teclo.seye.persistencia.vo.catalogo.TipoEncuestaVO;
import mx.com.teclo.seye.persistencia.vo.catalogo.TipoPreguntaVO;

public interface CatalogoService {

	/**
	 * @Descripción: Método para consultar el catálogos del los
	 * tipos de encuesta
	 * @author Jorge Luis
	 * @return List<TipoEncuestaVO>
	 */
	public List<TipoEncuestaVO> tipoEncuesta() throws NotFoundException;
	
	/**
	 * @Descripción: Método para consultar el catálogos del los
	 * tipos de pregunta
	 * @author Jorge Luis
	 * @return List<TipoPreguntaVO>
	 */
	public List<TipoPreguntaVO> tipoPregunta() throws NotFoundException;

	/**
	 * @Descripción: Método para consultar el catálogos del los
	 * tipos de grado
	 * @author Jorge Luis
	 * @return List<GradoVO>
	 */
	public List<GradoVO> grado() throws NotFoundException;
	
	/**
	 * @Descripción: Método para consultar el catálogos del los
	 * puestos
	 * @author Jorge Luis
	 * @return List<PuestoVO>
	 */
	public List<PuestoVO> puesto() throws NotFoundException;
	
	/**
	 * @Descripción: Método para consultar el catálogos del sedes
	 * @author Jorge Luis
	 * @return List<SedeVO>
	 */
	public List<SedeVO> sede()  throws NotFoundException;
	
	/**
	 * @Descripción: Método para consultar el catálogos del grupos
	 * @author Jorge Luis
	 * @return List<GrupoVO>
	 */
	public List<GrupoVO> grupo()  throws NotFoundException;
	
	/**
	 * @Descripción: Método para consultar el catálogos del los
	 * tipos de pregunta
	 * @author Jorge Luis
	 * @return List<StEncuestaDTO>
	 */
	public List<StEncuestaVO> stEncuesta() throws NotFoundException;

	/**
	 * @Descripción: Método para consultar el catálogos del los
	 * estatus de calificación
	 * @author Jorge Luis
	 * @return List<EstatusCalificacionVO>
	 */
	public List<EstatusCalificacionVO> calificacion() throws NotFoundException;
	
	
	/**
	 * @Descripción: Método para consultar una lista de configuraciones
	 * y restricciones
	 * @author Jorge Luis
	 * @return List<ConfiguracionVO>
	 */
	public List<ConfiguracionVO> configuracion() throws NotFoundException;
	
	/**
	 * @Descripción: Método para consultar un parámetro 
	 * mediante su codigo identificador
	 * @author Jorge Luis
	 * @return ConfiguracionVO
	 */
	public ConfiguracionVO configuracion(String cdLlavePConfig)throws NotFoundException;
	
}
