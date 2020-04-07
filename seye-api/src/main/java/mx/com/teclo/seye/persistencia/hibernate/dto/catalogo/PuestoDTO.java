package mx.com.teclo.seye.persistencia.hibernate.dto.catalogo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TEE011C_EE_PUESTO")
public class PuestoDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8150626956516103272L;

	@Id
	@Column(name = "ID_PUESTO", unique = true, nullable = false, precision = 11, scale = 0)
	private Long idPuesto;

	@Column(name = "CD_PUESTO", nullable = false, length = 15)
	private String cdPuesto;

	@Column(name = "NB_PUESTO", nullable = false, length = 100)
	private String nbPuesto;

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
	 * @return the idPuesto
	 */
	public Long getIdPuesto() {
		return idPuesto;
	}

	/**
	 * @param idPuesto the idPuesto to set
	 */
	public void setIdPuesto(Long idPuesto) {
		this.idPuesto = idPuesto;
	}

	/**
	 * @return the cdPuesto
	 */
	public String getCdPuesto() {
		return cdPuesto;
	}

	/**
	 * @param cdPuesto the cdPuesto to set
	 */
	public void setCdPuesto(String cdPuesto) {
		this.cdPuesto = cdPuesto;
	}

	/**
	 * @return the nbPuesto
	 */
	public String getNbPuesto() {
		return nbPuesto;
	}

	/**
	 * @param nbPuesto the nbPuesto to set
	 */
	public void setNbPuesto(String nbPuesto) {
		this.nbPuesto = nbPuesto;
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
