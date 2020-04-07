package mx.com.teclo.seye.persistencia.hibernate.dao.encuesta;

import java.util.Date;
import java.util.List;

import mx.com.teclo.arquitectura.persistencia.comun.dao.BaseDao;
import mx.com.teclo.seye.persistencia.hibernate.dto.encuesta.EncuestasDTO;
import mx.com.teclo.seye.persistencia.hibernate.dto.encuesta.UsuarioEncuestaDTO;

public interface EncuestasDAO extends BaseDao<EncuestasDTO>{

	/**
	 * @Descripción: Método para consultar la encuesta, utilizando
	 * el codigo del registro como filtro
	 * @author jorgeluis
	 * @return EncuestasDTO
	 */
	public EncuestasDTO encuestaFolio(String cdFolio);

	/**
	 * @Descripción: Método para consultar la encuesta por su identificador
	 * único
	 * @author jorgeluis
	 * @return EncuestasDTO
	 */
	public EncuestasDTO encuestaIntento(Long idEncuesta);

	/**
	 * @Descripción: 
	 * único
	 * @author f.campero
	 * @return List<EncuestasDTO>
	 */
	List<EncuestasDTO> buscaTodasEncuestasConUsuario(Integer tipoBusqueda, String valorBusqueda, Date fInicio, Date fhFin, Integer stEncuesta);
	
	/**
	 * @Descripción: 
	 * único
	 * @author f.campero
	 * @return List<EncuestasDTO>
	 */
	List<EncuestasDTO> buscaEncuestaPorIdConUsuario(Long idEncuesta);

}
