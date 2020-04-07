package mx.com.teclo.seye.persistencia.hibernate.dao.encuesta;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import mx.com.teclo.arquitectura.persistencia.comun.dao.BaseDaoHibernate;
import mx.com.teclo.seye.persistencia.hibernate.dto.encuesta.SeccionDTO;

@Repository
public class SeccionDAOImpl extends BaseDaoHibernate<SeccionDTO> implements SeccionDAO {

	@SuppressWarnings("unchecked")
	@Override
	public List<SeccionDTO> seccionesEncuesta(Long idEncuesta) {
		Criteria c = getCurrentSession().createCriteria(SeccionDTO.class);
		c.createAlias("idEncuesta", "e");
		c.add(Restrictions.eq("stActivo", 1));
		c.add(Restrictions.eq("e.stActivo", 1));
		c.add(Restrictions.eq("e.idEncuesta", idEncuesta));
		c.addOrder(Order.asc("idSeccion"));
		return (List<SeccionDTO>) c.list();
	}

	@Override
	public SeccionDTO seccion(Long idSeccion) {
		Criteria c = getCurrentSession().createCriteria(SeccionDTO.class);
		c.add(Restrictions.eq("idSeccion", idSeccion));
		c.add(Restrictions.eq("stActivo", 1));
		return (SeccionDTO) c.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Integer> seccionesContestadasEncuesta(Integer idEncuesta) {
		List<Integer> listaIdSecciones = null;
		StringBuilder strQuery = new StringBuilder(
						" SELECT "+
						" 	uer.ID_SECCION AS idSeccion "+
						" FROM "+
						" 	TEE003D_EE_USU_ENC_RESP uer "+
						" WHERE "+
						" 	uer.ID_USU_ENCU_INTENTO= "+ idEncuesta +
						" 	AND ID_SECCION NOT IN( "+
						" 	SELECT "+
						" 		uerTwo.ID_SECCION "+
						" 	FROM "+
						" 		TEE003D_EE_USU_ENC_RESP uerTwo "+
						" 	WHERE "+
						" 		uerTwo.ID_USU_ENCU_INTENTO= "+ idEncuesta +
						" 		AND uerTwo.ID_OPCION IS NULL "+
						" 	GROUP BY "+
						" 		(uerTwo.ID_SECCION, "+
						" 		uerTwo.ID_OPCION) ) "+
						" 	GROUP BY (uer.ID_SECCION) ");
		SQLQuery resultado = getCurrentSession().createSQLQuery(strQuery.toString());
		listaIdSecciones = (List<Integer>) resultado.list();

		return listaIdSecciones;
	}

}
