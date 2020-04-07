package mx.com.teclo.seye.persistencia.hibernate.dao.usuarioencuesta;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import mx.com.teclo.arquitectura.persistencia.comun.dao.BaseDaoHibernate;
import mx.com.teclo.seye.persistencia.hibernate.dto.encuesta.UsuarioEncuestaDTO;

@Repository
public class UsuarioEncuestaDAOImpl extends BaseDaoHibernate<UsuarioEncuestaDTO> implements UsuarioEncuentaDAO {
	
	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	public List<UsuarioEncuestaDTO> buscaEncuentasPorUsuario(Long idUsuario){
		Criteria criteria = getCurrentSession().createCriteria(UsuarioEncuestaDTO.class);
		
		criteria.createAlias("usuario", "usuario");
		criteria.add(Restrictions.eq("usuario.idUsuario", idUsuario));
		criteria.add(Restrictions.eq("stActivo", true));
		
		return (List<UsuarioEncuestaDTO>) criteria.list();
	}
	

	@SuppressWarnings("unchecked")
	@Override
	public List<UsuarioEncuestaDTO> encuestaIntentos(Long idUsuario, Long idEncuesta) {
		Criteria criteria = getCurrentSession().createCriteria(UsuarioEncuestaDTO.class);
		criteria.createAlias("encuesta", "e");
		criteria.createAlias("usuario", "usr");
		criteria.add(Restrictions.eq("usr.idUsuario", idUsuario));
		criteria.add(Restrictions.eq("stActivo", true));
		criteria.add(Restrictions.eq("e.stActivo", 1));
		criteria.add(Restrictions.eq("e.idEncuesta", idEncuesta));
		return (List<UsuarioEncuestaDTO>) criteria.list();
	}


	@Override
	public UsuarioEncuestaDTO getEncuestaByIdUser(Long idUsuario, Long idEncuesta) {
		UsuarioEncuestaDTO usuarioEncuestaDTO= null;
		
		Criteria criteria = getCurrentSession().createCriteria(UsuarioEncuestaDTO.class);
		criteria.createAlias("encuesta", "e");
		criteria.createAlias("usuario", "usr");
		criteria.add(Restrictions.eq("usr.idUsuario", idUsuario));
		criteria.add(Restrictions.eq("stActivo", true));
		criteria.add(Restrictions.eq("e.stActivo", 1L));
		criteria.add(Restrictions.eq("e.idEncuesta", idEncuesta));
		usuarioEncuestaDTO = (UsuarioEncuestaDTO) criteria.uniqueResult();
		return usuarioEncuestaDTO;
	}


	@Override
	public UsuarioEncuestaDTO usuarioEncuesta(Long idUsuario, Long idUsuarioEncuesta) {
		Criteria c = getCurrentSession().createCriteria(UsuarioEncuestaDTO.class);
		c.createAlias("usuario", "usr");
		c.add(Restrictions.eq("usr.idUsuario", idUsuario));
		c.add(Restrictions.eq("stActivo", true));
		c.add(Restrictions.eq("idUsuarioEncuesta", idUsuarioEncuesta));
		return (UsuarioEncuestaDTO)c.uniqueResult();
	}
}
