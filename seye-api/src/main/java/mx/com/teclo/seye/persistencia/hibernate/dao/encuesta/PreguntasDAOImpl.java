package mx.com.teclo.seye.persistencia.hibernate.dao.encuesta;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import mx.com.teclo.arquitectura.persistencia.comun.dao.BaseDaoHibernate;
import mx.com.teclo.seye.persistencia.hibernate.dto.encuesta.PreguntasDTO;

@Repository
public class PreguntasDAOImpl extends BaseDaoHibernate<PreguntasDTO> implements PreguntasDAO{

	@SuppressWarnings("unchecked")
	@Override
	public List<PreguntasDTO> preguntas(Long idSeccion) {
		Criteria c = getCurrentSession().createCriteria(PreguntasDTO.class);
		c.createAlias("idSeccion", "s");
		c.add(Restrictions.eq("stActivo", 1));
		c.add(Restrictions.eq("s.stActivo", 1));
		c.add(Restrictions.eq("s.idSeccion", idSeccion));
		c.addOrder(Order.asc("nuOrden"));
		return (List<PreguntasDTO>) c.list();
	}

	@Override
	public PreguntasDTO pregunta(Long idPregunta, Boolean op) {
		Criteria c = getCurrentSession().createCriteria(PreguntasDTO.class);
		c.add(Restrictions.eq("idPregunta", idPregunta));
		if(op)
			c.add(Restrictions.eq("stActivo", 1));
		return (PreguntasDTO)c.uniqueResult();
	}
}
