package mx.com.teclo.seye.persistencia.hibernate.dao.clave;

import java.util.List;

import mx.com.teclo.arquitectura.persistencia.comun.dao.BaseDao;
import mx.com.teclo.seye.persistencia.hibernate.dto.clave.PasswordDTO;

public interface PasswordDAO extends BaseDao<PasswordDTO> {

	/**
	 * Consulta todos los registros de contraeñar 
	 * incluyendo historicos y actuales
	 * @author jorgeluis
	 * @return List<PasswordDTO>
	 */
	public List<PasswordDTO> claves();
	
	/**
	 * Consulta todos los registros de contraeña
	 * mediante el ST_ACTUAL = 1
	 * @author jorgeluis
	 * @return PasswordDTO
	 */
	public PasswordDTO clave();
	
	/**
	 * Consulta todos los registros utilizando 
	 * el filtro de pendiente
	 * @author jorgeluis
	 * @return List<PasswordDTO>
	 */
	public List<PasswordDTO> clavesPendientes(String cdSt);
	
}
