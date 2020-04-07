package mx.com.teclo.seye.persistencia.hibernate.dto.catalogo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TEE010C_EE_GRADO")
public class GradoDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3338058256303248907L;

	@Id
	@Column(name = "ID_GRADO", unique = true, nullable = false, precision = 11, scale = 0)
	private Long idGrado;

	@Column(name = "CD_GRADO", nullable = false, length = 15)
	private String cdGrado;

	@Column(name = "NB_GRADO", nullable = false, length = 100)
	private String nbGrado;

	@Column(name = "NU_ORDEN", nullable = false, precision = 11, scale = 0)
	private Integer nuOrden;

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
	 * @return the idGrado
	 */
	public Long getIdGrado() {
		return idGrado;
	}

	/**
	 * @param idGrado the idGrado to set
	 */
	public void setIdGrado(Long idGrado) {
		this.idGrado = idGrado;
	}

	/**
	 * @return the cdGrado
	 */
	public String getCdGrado() {
		return cdGrado;
	}

	/**
	 * @param cdGrado the cdGrado to set
	 */
	public void setCdGrado(String cdGrado) {
		this.cdGrado = cdGrado;
	}

	/**
	 * @return the nbGrado
	 */
	public String getNbGrado() {
		return nbGrado;
	}

	/**
	 * @param nbGrado the nbGrado to set
	 */
	public void setNbGrado(String nbGrado) {
		this.nbGrado = nbGrado;
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
	 * @return the nuOrden
	 */
	public Integer getNuOrden() {
		return nuOrden;
	}

	/**
	 * @param nuOrden the nuOrden to set
	 */
	public void setNuOrden(Integer nuOrden) {
		this.nuOrden = nuOrden;
	}

}
