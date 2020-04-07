package mx.com.teclo.seye.persistencia.hibernate.dao.encuesta;

import java.util.List;

import mx.com.teclo.arquitectura.persistencia.comun.dao.BaseDao;
import mx.com.teclo.seye.persistencia.hibernate.dto.encuesta.SeccionDTO;

public interface SeccionDAO extends BaseDao<SeccionDTO>{

	/**
	 * @Descripción: Método para consultar las secciones que pertenecen
	 * a cierta encuesta por su identificador
	 * @author jorgeluis
	 * @return List<SeccionDTO>
	 */
	public List<SeccionDTO> seccionesEncuesta(Long idEncuesta);
	
	/**
	 * @Descripción: Método para consultar la sección medienta
	 * su identificador unico
	 * @author jorgeluis
	 * @return SeccionDTO
	 */
	public SeccionDTO seccion(Long idSeccion);
	

	/**
	 * @Descripción: Método para consultar las secciones respondidas de cada encuesta
	 * @author Fjmb
	 * @return List<Long>
	 */
	public List<Integer> seccionesContestadasEncuesta(Integer idEncuesta);
	
}
