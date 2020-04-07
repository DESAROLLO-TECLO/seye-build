package mx.com.teclo.seye.persistencia.hibernate.dao.catalogo;

import java.util.List;

import mx.com.teclo.arquitectura.persistencia.comun.dao.BaseDao;
import mx.com.teclo.seye.persistencia.hibernate.dto.catalogo.SedeDTO;

public interface SedeDAO extends BaseDao<SedeDTO>{

	/**
	 * @Descripción: Método para consultar el catálogos del sedes
	 * @author Jorge Luis
	 * @return List<SedeVO>
	 */
	public List<SedeDTO> sede();
	
}
