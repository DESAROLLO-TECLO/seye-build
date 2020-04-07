package mx.com.teclo.seye.persistencia.hibernate.dao.clave;

import java.util.List;

import mx.com.teclo.arquitectura.persistencia.comun.dao.BaseDao;
import mx.com.teclo.seye.persistencia.hibernate.dto.clave.StPasswordDTO;

public interface StPasswordDAO extends BaseDao<StPasswordDTO>{

	/**
	 * Consulta todos los registros existentes dentro del catálogo
	 * @author jorgeluis
	 * @return List<StPasswordDTO>
	 */
	public List<StPasswordDTO> stPassword();
	
	/**
	 * Consulta el registro por su copdigo
	 * @return StPasswordDTO
	 */
	public StPasswordDTO stPassword(String cdStPassword); 
	
}
