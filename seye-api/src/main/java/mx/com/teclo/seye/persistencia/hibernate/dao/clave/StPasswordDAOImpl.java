package mx.com.teclo.seye.persistencia.hibernate.dao.clave;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import mx.com.teclo.arquitectura.persistencia.comun.dao.BaseDaoHibernate;
import mx.com.teclo.seye.persistencia.hibernate.dto.clave.StPasswordDTO;

@Repository
public class StPasswordDAOImpl extends BaseDaoHibernate<StPasswordDTO> implements StPasswordDAO{

	@SuppressWarnings("unchecked")
	@Override
	public List<StPasswordDTO> stPassword() {
		Criteria c = getCurrentSession().createCriteria(StPasswordDTO.class);
		c.add(Restrictions.eq("stActivo", 1));
		return (List<StPasswordDTO>)c.list();
	}

	@Override
	public StPasswordDTO stPassword(String cdStPassword) {
		Criteria c = getCurrentSession().createCriteria(StPasswordDTO.class);
		c.add(Restrictions.eq("stActivo", 1));
		c.add(Restrictions.eq("cdStPassword", cdStPassword));
		return (StPasswordDTO)c.uniqueResult();
	}

}
