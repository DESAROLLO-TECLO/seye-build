package mx.com.teclo.seye.persistencia.hibernate.dao.clave;

import java.util.List;

import mx.com.teclo.arquitectura.persistencia.comun.dao.BaseDao;
import mx.com.teclo.seye.persistencia.hibernate.dto.clave.PerfilAplicarDTO;

public interface PerfilAplicarDAO extends BaseDao<PerfilAplicarDTO>{

	/**
	 * Método para consultar todos los perfiles que se aplicarán
	 * cambio de clave
	 * @author Jorge Luis
	 * @return List<PerfilAplicarDTO>
	 */
	public List<PerfilAplicarDTO> perfilAplicar();
}
