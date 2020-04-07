package mx.com.teclo.seye.persistencia.hibernate.dao.encuesta;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import mx.com.teclo.arquitectura.persistencia.comun.dao.BaseDaoHibernate;
import mx.com.teclo.seye.persistencia.hibernate.dto.encuesta.UsuarioEncuestaDetalleDTO;

@Repository
public class EncuestaDetalleDAOImpl extends BaseDaoHibernate<UsuarioEncuestaDetalleDTO> implements EncuestaDetalleDAO{

	@Override
	public UsuarioEncuestaDetalleDTO getEncuestaDetalle(Long idEncuesta, Long idUsuarioEncuesta) {
		Criteria criteria = getCurrentSession().createCriteria(UsuarioEncuestaDetalleDTO.class);
		criteria.createAlias("encuesta", "encuesta");
		criteria.createAlias("usuario", "usuario");
		
		criteria.createAlias("encuesta.secciones", "secciones");
		criteria.createAlias("secciones.preguntas", "preguntas");
		criteria.createAlias("preguntas.opciones", "opciones");
		
		criteria.add(Restrictions.eq("encuesta.idEncuesta", idEncuesta));
		criteria.add(Restrictions.eq("idUsuarioEncuesta", idUsuarioEncuesta));
		
		criteria.add(Restrictions.eq("stActivo", 1));
		criteria.add(Restrictions.eq("encuesta.stActivo", 1));
		criteria.add(Restrictions.eq("secciones.stActivo", 1));
		criteria.add(Restrictions.eq("preguntas.stActivo", 1));
		criteria.add(Restrictions.eq("opciones.stActivo", 1));
		
		criteria.addOrder(Order.asc("secciones.nuOrden"));
		criteria.addOrder(Order.asc("preguntas.nuOrden"));
		criteria.addOrder(Order.asc("opciones.nuOrden"));
		
		return (UsuarioEncuestaDetalleDTO) criteria.uniqueResult();
	}

	@Override
	public UsuarioEncuestaDetalleDTO getEncuestaDetalleByUsuario(Long idEncuesta, Long idUsuario) {
		Criteria criteria = getCurrentSession().createCriteria(UsuarioEncuestaDetalleDTO.class);
		criteria.createAlias("encuesta", "encuesta");
		criteria.createAlias("usuario", "usuario");
		
		criteria.createAlias("encuesta.secciones", "secciones");
		criteria.createAlias("secciones.preguntas", "preguntas");
		criteria.createAlias("preguntas.opciones", "opciones");
		
		criteria.add(Restrictions.eq("encuesta.idEncuesta", idEncuesta));
		criteria.add(Restrictions.eq("usuario.idUsuario", idUsuario));
 		
		criteria.add(Restrictions.eq("stActivo", 1));
		criteria.add(Restrictions.eq("encuesta.stActivo", 1));
		criteria.add(Restrictions.eq("secciones.stActivo", 1));
		criteria.add(Restrictions.eq("preguntas.stActivo", 1));
		criteria.add(Restrictions.eq("opciones.stActivo", 1));
		
		criteria.addOrder(Order.asc("secciones.nuOrden"));
		criteria.addOrder(Order.asc("preguntas.nuOrden"));
		criteria.addOrder(Order.asc("opciones.nuOrden"));
		
		return (UsuarioEncuestaDetalleDTO) criteria.uniqueResult();
	}
}

