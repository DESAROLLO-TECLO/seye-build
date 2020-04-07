package mx.com.teclo.seye.persistencia.hibernate.dao.usuarioencuestaintentos;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import mx.com.teclo.arquitectura.persistencia.comun.dao.BaseDaoHibernate;
import mx.com.teclo.seye.persistencia.hibernate.dto.encuesta.UsuarioEncuestaIntentosDTO;

@Repository
public class UsuarioEncuestaIntentoDAOImpl extends BaseDaoHibernate<UsuarioEncuestaIntentosDTO> implements UsuarioEncuentaIntentoDAO {
	
	/**
	 * {@inheritDoc}
	 */
	public UsuarioEncuestaIntentosDTO buscaUnaEncuestaParaFinalizar(Long idEncuesta){
		Criteria criteria = getCurrentSession().createCriteria(UsuarioEncuestaIntentosDTO.class);
		criteria.createAlias("usuarioEncuesta", "usuarioEncuesta");
		criteria.createAlias("usuarioEncuesta.encuenta", "encuesta");
		criteria.add(Restrictions.eqOrIsNull("stActivo", true));
		criteria.add(Restrictions.eqOrIsNull("encuesta.idEncuesta", idEncuesta));
		return (UsuarioEncuestaIntentosDTO) criteria.uniqueResult();
	}

	@Override
	public UsuarioEncuestaIntentosDTO getIntentoById(Long idIntentoEncuesta) {
		Criteria criteria = getCurrentSession().createCriteria(UsuarioEncuestaIntentosDTO.class);
		criteria.add(Restrictions.eq("idUsuEncuIntento",idIntentoEncuesta));
  		criteria.add(Restrictions.eq("stActivo", true));

		return (UsuarioEncuestaIntentosDTO) criteria.uniqueResult();
 	}
	
	@Override
	public UsuarioEncuestaIntentosDTO buscaUsuEncuIntento(Long idUsuEncuIntento) {
		Criteria criteria = getCurrentSession().createCriteria(UsuarioEncuestaIntentosDTO.class);
		criteria.add(Restrictions.eqOrIsNull("stActivo", true));
		criteria.add(Restrictions.eq("idUsuEncuIntento", idUsuEncuIntento));
		return (UsuarioEncuestaIntentosDTO) criteria.uniqueResult();
	}

	@Override
	public UsuarioEncuestaIntentosDTO encuestaUsuario(Long idUsuario, Long idEncuesta) {
		Criteria c = getCurrentSession().createCriteria(UsuarioEncuestaIntentosDTO.class);
		c.createAlias("usuarioEncuesta", "ue");
		c.createAlias("ue.usuario", "u");
		c.createAlias("ue.encuesta", "e");
		
		c.add(Restrictions.eq("e.idEncuesta", idEncuesta));
		c.add(Restrictions.eq("u.idUsuario", idUsuario));
		
		c.add(Restrictions.eq("stActivo", true));
		c.add(Restrictions.eq("stMostrar", true));// EL stMostrar INDICA EL INTENTO ACTUAL QUE ESTÁ MODIFICA EL USUARIO
		
		return (UsuarioEncuestaIntentosDTO)c.uniqueResult();
	}

 
	@Override
	public UsuarioEncuestaIntentosDTO getEncuestaByUsuario(Long idEncuesta, Long idUsuario) {
		Criteria criteria = getCurrentSession().createCriteria(UsuarioEncuestaIntentosDTO.class);
		
		criteria.createAlias("usuarioEncuesta", "ue");
		criteria.createAlias("ue.encuesta", "en");
		criteria.createAlias("ue.usuario", "usr");
 
		criteria.add(Restrictions.eq("stActivo", true));
		criteria.add(Restrictions.eq("en.idEncuesta", idEncuesta));
		criteria.add(Restrictions.eq("usr.idUsuario", idUsuario));
		criteria.add(Restrictions.eq("stMostrar", true));

		return (UsuarioEncuestaIntentosDTO) criteria.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<UsuarioEncuestaIntentosDTO> usuarioEncuesta(Long idUsuarioEncuesta) {
		Criteria c = getCurrentSession().createCriteria(UsuarioEncuestaIntentosDTO.class);
		c.createAlias("usuarioEncuesta", "ue");
		c.add(Restrictions.eq("stActivo", true));
		c.add(Restrictions.eq("ue.idUsuarioEncuesta", idUsuarioEncuesta));
		c.add(Restrictions.eq("stMostrar", true));
		return (List<UsuarioEncuestaIntentosDTO>)c.list();
	}

	@Override
	public UsuarioEncuestaIntentosDTO usuarioEncuesta(Long idUsuarioEncuesta, Long idUsuEncuIntento) {
		Criteria c = getCurrentSession().createCriteria(UsuarioEncuestaIntentosDTO.class);
		c.createAlias("usuarioEncuesta", "ue");
		c.add(Restrictions.eq("stActivo", true));
		c.add(Restrictions.eq("ue.idUsuarioEncuesta", idUsuarioEncuesta));
		c.add(Restrictions.eq("idUsuEncuIntento", idUsuEncuIntento));
		return (UsuarioEncuestaIntentosDTO) c.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<UsuarioEncuestaIntentosDTO> intentoMismaEncuesta(Long idUsuarioEncuesta) {
		Criteria c = getCurrentSession().createCriteria(UsuarioEncuestaIntentosDTO.class);
		c.createAlias("usuarioEncuesta", "ue");
		c.add(Restrictions.eq("stActivo", true));
		c.add(Restrictions.eq("ue.idUsuarioEncuesta", idUsuarioEncuesta));
		
		c.addOrder(Order.desc("nuCalificacion"));
		c.addOrder(Order.desc("fhInicio"));
		
		return (List<UsuarioEncuestaIntentosDTO>)c.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<UsuarioEncuestaIntentosDTO> buscaPendientes(Long idUsuarioEncuesta) {
		Criteria c = getCurrentSession().createCriteria(UsuarioEncuestaIntentosDTO.class);
		c.createAlias("stEncuesta", "st");
		c.createAlias("usuarioEncuesta", "ue");
		
		Disjunction or = Restrictions.disjunction();
		or.add(Restrictions.eq("st.cdStEncuesta", "EC"));
		or.add(Restrictions.eq("st.cdStEncuesta", "NI"));
		c.add(or);
		c.add(Restrictions.eq("stMostrar", true));
		c.add(Restrictions.eq("ue.idUsuarioEncuesta", idUsuarioEncuesta));
		
		return (List<UsuarioEncuestaIntentosDTO>)c.list();
	}
 	
}
