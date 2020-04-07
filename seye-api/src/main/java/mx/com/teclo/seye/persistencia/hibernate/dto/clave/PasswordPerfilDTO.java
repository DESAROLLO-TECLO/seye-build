package mx.com.teclo.seye.persistencia.hibernate.dto.clave;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import mx.com.teclo.seye.persistencia.hibernate.dto.perfil.PerfilDTO;

@Entity
@Table(name = "TEE021D_PASSWORD_PERFIL")
public class PasswordPerfilDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4714427417574624975L;

	@Id
	@SequenceGenerator(name = "SQEE021D", sequenceName = "SQEE021D_PASSWORD_P", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQEE021D")
	@Column(name = "ID_PASSWORD_PERFIL", unique = true, nullable = false, precision = 11, scale = 0)
	private Long idPasswordPerfil;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_PASSWORD", nullable = false)
	private PasswordDTO password;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_PERFIL", nullable = false)
	private PerfilDTO perfil;

	@Column(name = "ST_ACTIVO", nullable = false, precision = 1, scale = 0)
	private Integer stActivo;

	@Column(name = "ID_USR_CREACION", nullable = false, precision = 11, scale = 0)
	private Long idUsrCreacion;

	@Column(name = "FH_CREACION", nullable = false, length = 7)
	private Date fhCreacion;

	@Column(name = "ID_USR_MODIFICA", nullable = false, precision = 11, scale = 0)
	private Long idUsrModifica;

	@Column(name = "FH_MODIFICACION", nullable = false, length = 7)
	private Date fhModificacion;

	/**
	 * @return the idPasswordPerfil
	 */
	public Long getIdPasswordPerfil() {
		return idPasswordPerfil;
	}

	/**
	 * @param idPasswordPerfil the idPasswordPerfil to set
	 */
	public void setIdPasswordPerfil(Long idPasswordPerfil) {
		this.idPasswordPerfil = idPasswordPerfil;
	}

	/**
	 * @return the password
	 */
	public PasswordDTO getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(PasswordDTO password) {
		this.password = password;
	}

	/**
	 * @return the perfil
	 */
	public PerfilDTO getPerfil() {
		return perfil;
	}

	/**
	 * @param perfil the perfil to set
	 */
	public void setPerfil(PerfilDTO perfil) {
		this.perfil = perfil;
	}

	

	/**
	 * @return the stActivo
	 */
	public Integer getStActivo() {
		return stActivo;
	}

	/**
	 * @param stActivo the stActivo to set
	 */
	public void setStActivo(Integer stActivo) {
		this.stActivo = stActivo;
	}

	/**
	 * @return the idUsrCreacion
	 */
	public Long getIdUsrCreacion() {
		return idUsrCreacion;
	}

	/**
	 * @param idUsrCreacion the idUsrCreacion to set
	 */
	public void setIdUsrCreacion(Long idUsrCreacion) {
		this.idUsrCreacion = idUsrCreacion;
	}

	/**
	 * @return the fhCreacion
	 */
	public Date getFhCreacion() {
		return fhCreacion;
	}

	/**
	 * @param fhCreacion the fhCreacion to set
	 */
	public void setFhCreacion(Date fhCreacion) {
		this.fhCreacion = fhCreacion;
	}

	/**
	 * @return the idUsrModifica
	 */
	public Long getIdUsrModifica() {
		return idUsrModifica;
	}

	/**
	 * @param idUsrModifica the idUsrModifica to set
	 */
	public void setIdUsrModifica(Long idUsrModifica) {
		this.idUsrModifica = idUsrModifica;
	}

	/**
	 * @return the fhModificacion
	 */
	public Date getFhModificacion() {
		return fhModificacion;
	}

	/**
	 * @param fhModificacion the fhModificacion to set
	 */
	public void setFhModificacion(Date fhModificacion) {
		this.fhModificacion = fhModificacion;
	}

}
