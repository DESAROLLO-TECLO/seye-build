package mx.com.teclo.seye.persistencia.hibernate.dao.catalogo;

import java.util.List;

import mx.com.teclo.arquitectura.persistencia.comun.dao.BaseDao;
import mx.com.teclo.seye.persistencia.hibernate.dto.catalogo.GrupoDTO;

public interface GrupoDAO extends BaseDao<GrupoDTO>{

	/**
	 * @Descripción: Método para consultar el catálogos del grupos
	 * @author Jorge Luis
	 * @return List<GrupoDTO>
	 */
	public List<GrupoDTO> grupo();
}
