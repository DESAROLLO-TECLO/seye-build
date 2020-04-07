package mx.com.teclo.seye.persistencia.hibernate.dao.catalogo;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import mx.com.teclo.arquitectura.persistencia.comun.dao.BaseDaoHibernate;
import mx.com.teclo.seye.persistencia.hibernate.dto.catalogo.GradoDTO;

@Repository
public class GradoDAOImpl extends BaseDaoHibernate<GradoDTO> implements GradoDAO{

	@SuppressWarnings("unchecked")
	@Override
	public List<GradoDTO> grado() {
		Criteria c = getCurrentSession().createCriteria(GradoDTO.class);
		c.add(Restrictions.eq("stActivo", 1));
		c.addOrder(Order.asc("nuOrden"));
		return (List<GradoDTO>)c.list();
	}

}
