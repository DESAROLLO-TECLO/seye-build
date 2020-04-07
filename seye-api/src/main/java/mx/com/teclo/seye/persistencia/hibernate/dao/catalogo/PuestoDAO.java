package mx.com.teclo.seye.persistencia.hibernate.dao.catalogo;

import java.util.List;

import mx.com.teclo.arquitectura.persistencia.comun.dao.BaseDao;
import mx.com.teclo.seye.persistencia.hibernate.dto.catalogo.PuestoDTO;

public interface PuestoDAO extends BaseDao<PuestoDTO>{

	/**
	 * @Descripción: Método para consultar el catálogos del los
	 * puestos
	 * @author Jorge Luis
	 * @return List<PuestoDTO>
	 */
	public List<PuestoDTO> puesto();
	
}
