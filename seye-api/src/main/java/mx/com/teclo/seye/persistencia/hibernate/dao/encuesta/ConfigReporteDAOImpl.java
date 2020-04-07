package mx.com.teclo.seye.persistencia.hibernate.dao.encuesta;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import mx.com.teclo.arquitectura.persistencia.comun.dao.BaseDaoHibernate;
import mx.com.teclo.seye.persistencia.hibernate.dto.encuesta.ConfigReporteDTO;

@Repository
public class ConfigReporteDAOImpl extends BaseDaoHibernate<ConfigReporteDTO> implements ConfigReporteDAO {

	@Override
	public ConfigReporteDTO getConfiguracionPorIdEncuesta(Long idEncuesta) {
		Criteria criteria = getCurrentSession().createCriteria(ConfigReporteDTO.class);
		criteria.add(Restrictions.eq("idEncuesta", idEncuesta));
		criteria.add(Restrictions.eq("stActivo", 1));
		return (ConfigReporteDTO) criteria.uniqueResult();
	}
}
