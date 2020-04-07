package mx.com.teclo.seye.persistencia.hibernate.dao.encuesta;

import mx.com.teclo.arquitectura.persistencia.comun.dao.BaseDao;
import mx.com.teclo.seye.persistencia.hibernate.dto.encuesta.ConfigReporteDTO;

public interface ConfigReporteDAO extends BaseDao<ConfigReporteDTO>{

	ConfigReporteDTO getConfiguracionPorIdEncuesta(Long idTipoEncuesta);
}
