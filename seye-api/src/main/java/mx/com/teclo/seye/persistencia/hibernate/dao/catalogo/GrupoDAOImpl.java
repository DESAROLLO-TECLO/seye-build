package mx.com.teclo.seye.persistencia.hibernate.dao.catalogo;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import mx.com.teclo.arquitectura.persistencia.comun.dao.BaseDaoHibernate;
import mx.com.teclo.seye.persistencia.hibernate.dto.catalogo.GrupoDTO;

@Repository
public class GrupoDAOImpl extends BaseDaoHibernate<GrupoDTO> implements GrupoDAO{

	@SuppressWarnings("unchecked")
	@Override
	public List<GrupoDTO> grupo() {
		Criteria c = getCurrentSession().createCriteria(GrupoDTO.class);
		c.add(Restrictions.eq("stActivo", 1));
		c.addOrder(Order.asc("nuOrden"));
		return (List<GrupoDTO>)c.list();
	}

	
}
