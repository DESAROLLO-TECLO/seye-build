package mx.com.teclo.seye.persistencia.hibernate.dao.encuesta;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import mx.com.teclo.arquitectura.persistencia.comun.dao.BaseDaoHibernate;
import mx.com.teclo.seye.persistencia.hibernate.dto.encuesta.EncuestasDTO;

@Repository
public class EncuestasDAOImpl extends BaseDaoHibernate<EncuestasDTO> implements EncuestasDAO{

	@Override
	public EncuestasDTO encuestaFolio(String cdFolio) {
		Criteria c = getCurrentSession().createCriteria(EncuestasDTO.class);
		c.add(Restrictions.eq("stActivo", 1));
		c.add(Restrictions.eq("cdEncuesta", cdFolio));
		return (EncuestasDTO)c.uniqueResult();
	}

	@Override
	public EncuestasDTO encuestaIntento(Long idEncuesta) {
		Criteria c = getCurrentSession().createCriteria(EncuestasDTO.class);
		c.add(Restrictions.eq("stActivo", 1));
		c.add(Restrictions.eq("idEncuesta", idEncuesta));
		return (EncuestasDTO)c.uniqueResult();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<EncuestasDTO> buscaTodasEncuestasConUsuario(
			Integer tipoBusqueda, String valorBusqueda, Date fInicio, Date fhFin, Integer stEncuesta){
		Criteria criteria = getCurrentSession().createCriteria(EncuestasDTO.class);
		criteria.createAlias("usuarioEncuesta", "us");
		criteria.createAlias("us.usuarioEncuestaIntentos", "intentos");
		switch(tipoBusqueda) {
			case 1:
				criteria.add(Restrictions.eq("cdEncuesta", valorBusqueda.toUpperCase()));
				break;
			case 2:
				criteria.add(Restrictions.eq("nbEncuesta", valorBusqueda.toUpperCase()));
				break;
		}
		
		if(fInicio!=null) {
			criteria.add(Restrictions.or(Restrictions.gt("fhVigIni", fInicio), Restrictions.le("fhVigFin", fhFin)));
		}
		criteria.add(Restrictions.eq("intentos.stMostrar", true));
		criteria.add(Restrictions.eq("stActivo", 1));
		
		return (List<EncuestasDTO>) criteria.list();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<EncuestasDTO> buscaEncuestaPorIdConUsuario(Long idEncuesta) {
		Criteria criteria = getCurrentSession().createCriteria(EncuestasDTO.class);
		criteria.createAlias("usuarioEncuesta", "us");
		criteria.add(Restrictions.eq("idEncuesta", idEncuesta));
		criteria.add(Restrictions.eq("stActivo", 1));
		return (List<EncuestasDTO>) criteria.list();
	}
}
