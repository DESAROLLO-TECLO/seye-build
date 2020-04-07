package mx.com.teclo.seye.negocio.service.clave;

import java.util.List;

import mx.com.teclo.arquitectura.ortogonales.exception.BusinessException;
import mx.com.teclo.arquitectura.ortogonales.exception.NotFoundException;
import mx.com.teclo.seye.persistencia.hibernate.dto.clave.PasswordDTO;
import mx.com.teclo.seye.persistencia.vo.clave.PasswordVO;
import mx.com.teclo.seye.persistencia.vo.clave.StPasswordVO;

public interface ClaveService {

	/**
	 * Método para consultar todos los registros 
	 * incluyendo historicos y actuales en la tabla de claves
	 * @author Jorge Luis
	 * @return List<PasswordVO>
	 */
	public List<PasswordVO> claves() throws NotFoundException; 
	
	/**
	 * Descripción: Servicio para generar el codigo 
	 * de verificación de la contraseña
	 * @author Jorge Luis
	 * @return String
	*/
	public String clave();
	
	/**
	 * Descripción: Método para guardar una nueva clave 
	 * que aplique para todos los usuarios con los perfiles
	 * seleccionados
	 * @author Jorge Luis
	 * @return Boolean
	*/
	public Boolean guardaclave(PasswordVO vo, PasswordDTO pDTO)throws NotFoundException, BusinessException;
	
	/**
	 * Descripción: Método para consultar todos los estatus de los
	 * registros
	 * @author Jorge Luis
	 * @return List<StPasswordVO>
	*/
	public List<StPasswordVO> stPassword()throws NotFoundException;
	
	/**
	 * Descripción: Método para activar una clave mediante una tarea programada
	 * y actualizar los registros pertienentes dentro de las entidades
	 * correspondientes
	 * @author Jorge Luis
	 * @return Boolean
	*/
	public Boolean claveCron()throws NotFoundException, BusinessException ;
	
	/**
	 * Descripción: Método para activar una clave existenten dentro del sistema
	 * guardado como pendiente y actualizar registros petinentes
	 * correspondientes
	 * @author Jorge Luis
	 * @return Boolean
	*/
	public Boolean activarClave(PasswordVO vo)throws NotFoundException, BusinessException ;
}

