package mx.com.teclo.seye.persistencia.hibernate.dao.catalogo;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import mx.com.teclo.arquitectura.persistencia.comun.dao.BaseDaoHibernate;
import mx.com.teclo.seye.persistencia.hibernate.dto.catalogo.PuestoDTO;

@Repository
public class PuestoDAOImpl extends BaseDaoHibernate<PuestoDTO> implements PuestoDAO{

	@SuppressWarnings("unchecked")
	@Override
	public List<PuestoDTO> puesto() {
		Criteria c = getCurrentSession().createCriteria(PuestoDTO.class);
		c.add(Restrictions.eq("stActivo", 1));
		c.addOrder(Order.asc("nuOrden"));
		return (List<PuestoDTO>)c.list();
	}

}
