package mx.com.teclo.seye.persistencia.hibernate.dao.clave;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import mx.com.teclo.arquitectura.persistencia.comun.dao.BaseDaoHibernate;
import mx.com.teclo.seye.persistencia.hibernate.dto.clave.PerfilAplicarDTO;

@Repository
public class PerfilAplicarDAOImpl extends BaseDaoHibernate<PerfilAplicarDTO> implements PerfilAplicarDAO{

	@SuppressWarnings("unchecked")
	@Override
	public List<PerfilAplicarDTO> perfilAplicar() {
		Criteria c = getCurrentSession().createCriteria(PerfilAplicarDTO.class);
		c.add(Restrictions.eq("stActivo", 1));
		return (List<PerfilAplicarDTO>)c.list();
	}

}
