package mx.com.teclo.seye.persistencia.hibernate.dao.usuarioencuesta;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import mx.com.teclo.arquitectura.persistencia.comun.dao.BaseDaoHibernate;
import mx.com.teclo.seye.persistencia.hibernate.dto.encuesta.UsuAtributoDTO;

@Repository
public class UsuAtributoDAOImpl extends BaseDaoHibernate<UsuAtributoDTO> implements UsuAtributoDAO{

	@Override
	public UsuAtributoDTO usuario(Long idUsuario) {
		Criteria c = getCurrentSession().createCriteria(UsuAtributoDTO.class);
		c.createAlias("usuario", "usr");
		c.add(Restrictions.eq("usr.idUsuario", idUsuario));
		c.add(Restrictions.eq("stActivo", 1));
		return (UsuAtributoDTO)c.uniqueResult();
	}

}
