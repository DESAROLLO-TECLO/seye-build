package mx.com.teclo.seye.persistencia.hibernate.dto.encuesta;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import mx.com.teclo.seye.persistencia.hibernate.dto.catalogo.GradoDTO;
import mx.com.teclo.seye.persistencia.hibernate.dto.catalogo.GrupoDTO;
import mx.com.teclo.seye.persistencia.hibernate.dto.catalogo.PuestoDTO;
import mx.com.teclo.seye.persistencia.hibernate.dto.catalogo.SedeDTO;
import mx.com.teclo.seye.persistencia.hibernate.dto.usuario.UsuarioDTO;

@Entity
@Table(name = "TEE009D_EE_USU_ATRIBUTO")
public class UsuAtributoDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8918179056050890984L;

	@Id
	@Column(name = "ID_USU_ATRIBUTO", unique = true, nullable = false, precision = 11, scale = 0)
	private Long idUsuAtributo;

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_USUARIO", referencedColumnName = "ID_USUARIO", nullable = false)
	private UsuarioDTO usuario;

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_SEDE", referencedColumnName = "ID_SEDE", nullable = false)
	private SedeDTO sede;

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_GRADO", referencedColumnName = "ID_GRADO", nullable = false)
	private GradoDTO grado;

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_PUESTO", referencedColumnName = "ID_PUESTO", nullable = false)
	private PuestoDTO puesto;

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_GRUPO", referencedColumnName = "ID_GRUPO", nullable = false)
	private GrupoDTO grupo;

	@Column(name = "ST_ACCESO", nullable = false, precision = 1, scale = 0)
	private Integer stAcceso;

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
	 * @return the usuario
	 */
	public UsuarioDTO getUsuario() {
		return usuario;
	}

	/**
	 * @param usuario the usuario to set
	 */
	public void setUsuario(UsuarioDTO usuario) {
		this.usuario = usuario;
	}

	/**
	 * @return the sede
	 */
	public SedeDTO getSede() {
		return sede;
	}

	/**
	 * @param sede the sede to set
	 */
	public void setSede(SedeDTO sede) {
		this.sede = sede;
	}

	/**
	 * @return the grado
	 */
	public GradoDTO getGrado() {
		return grado;
	}

	/**
	 * @param grado the grado to set
	 */
	public void setGrado(GradoDTO grado) {
		this.grado = grado;
	}

	/**
	 * @return the puesto
	 */
	public PuestoDTO getPuesto() {
		return puesto;
	}

	/**
	 * @param puesto the puesto to set
	 */
	public void setPuesto(PuestoDTO puesto) {
		this.puesto = puesto;
	}

	/**
	 * @return the grupo
	 */
	public GrupoDTO getGrupo() {
		return grupo;
	}

	/**
	 * @param grupo the grupo to set
	 */
	public void setGrupo(GrupoDTO grupo) {
		this.grupo = grupo;
	}

	/**
	 * @return the stAcceso
	 */
	public Integer getStAcceso() {
		return stAcceso;
	}

	/**
	 * @param stAcceso the stAcceso to set
	 */
	public void setStAcceso(Integer stAcceso) {
		this.stAcceso = stAcceso;
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

	/**
	 * @return the idUsuAtributo
	 */
	public Long getIdUsuAtributo() {
		return idUsuAtributo;
	}

	/**
	 * @param idUsuAtributo the idUsuAtributo to set
	 */
	public void setIdUsuAtributo(Long idUsuAtributo) {
		this.idUsuAtributo = idUsuAtributo;
	}

}
