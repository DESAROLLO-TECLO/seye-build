package mx.com.teclo.seye.persistencia.hibernate.dao.clave;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import mx.com.teclo.arquitectura.persistencia.comun.dao.BaseDaoHibernate;
import mx.com.teclo.seye.persistencia.hibernate.dto.clave.PasswordDTO;

@Repository
public class PasswordDAOImpl extends BaseDaoHibernate<PasswordDTO> implements PasswordDAO{

	@SuppressWarnings("unchecked")
	@Override
	public List<PasswordDTO> claves() {
		Criteria c = getCurrentSession().createCriteria(PasswordDTO.class);
		c.createAlias("estatus", "st");
		c.add(Restrictions.eq("stActivo", 1));
		//c.addOrder(Order.desc("fhVigenciaIni"));
		c.addOrder(Order.asc("st.idStPassword"));
		return (List<PasswordDTO>)c.list();
	}

	@Override
	public PasswordDTO clave() {
		Criteria c = getCurrentSession().createCriteria(PasswordDTO.class);
		c.add(Restrictions.eq("stActual", 1));
		c.add(Restrictions.eq("stActivo", 1));
		return (PasswordDTO)c.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PasswordDTO> clavesPendientes(String cdSt) {
		Criteria c = getCurrentSession().createCriteria(PasswordDTO.class);
		c.createAlias("estatus", "st");
		c.add(Restrictions.eq("stActivo", 1));
		c.add(Restrictions.eq("st.stActivo", 1));
		c.add(Restrictions.eq("st.cdStPassword", cdSt));
		return (List<PasswordDTO>)c.list();
	}

	
	
}
