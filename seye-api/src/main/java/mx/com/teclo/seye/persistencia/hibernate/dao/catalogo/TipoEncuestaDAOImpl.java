package mx.com.teclo.seye.persistencia.hibernate.dao.catalogo;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import mx.com.teclo.arquitectura.persistencia.comun.dao.BaseDaoHibernate;
import mx.com.teclo.seye.persistencia.hibernate.dto.catalogo.TipoEncuestaDTO;

@Repository
public class TipoEncuestaDAOImpl extends BaseDaoHibernate<TipoEncuestaDTO> implements TipoEncuestaDAO{

	@SuppressWarnings("unchecked")
	@Override
	public List<TipoEncuestaDTO> tipoEncuesta() {
		Criteria c = getCurrentSession().createCriteria(TipoEncuestaDTO.class);
		c.add(Restrictions.eq("stActivo", 1));
		c.addOrder(Order.asc("nuOrden"));
		return (List<TipoEncuestaDTO>)c.list();
	}

}
