package mx.com.teclo.seye.persistencia.hibernate.dao.catalogo;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import mx.com.teclo.arquitectura.persistencia.comun.dao.BaseDaoHibernate;
import mx.com.teclo.seye.persistencia.hibernate.dto.catalogo.TipoPreguntaDTO;

@Repository
public class TipoPreguntaDAOImpl extends BaseDaoHibernate<TipoPreguntaDTO> implements TipoPreguntaDAO{

	@SuppressWarnings("unchecked")
	@Override
	public List<TipoPreguntaDTO> tipoPregunta() {
		Criteria c = getCurrentSession().createCriteria(TipoPreguntaDTO.class);
		c.add(Restrictions.eq("stActivo", 1));
		c.addOrder(Order.asc("nuOrden"));
		return (List<TipoPreguntaDTO>)c.list();
	}

}
