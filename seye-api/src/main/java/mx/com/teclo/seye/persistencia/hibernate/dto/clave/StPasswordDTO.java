package mx.com.teclo.seye.persistencia.hibernate.dto.clave;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TEE024C_ST_PASSWORD")
public class StPasswordDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7917061255776743424L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID_ST_PASSWORD", unique = true, nullable = false, precision = 11, scale = 0)
	private Long idStPassword;

	@Column(name = "CD_ST_PASSWORD", nullable = false, length = 20)
	private String cdStPassword;

	@Column(name = "CD_COLOR", nullable = false, length = 20)
	private String cdColor;

	@Column(name = "NB_ST_PASSWORD", nullable = false, length = 100)
	private String nbStPassword;

	@Column(name = "TX_ST_PASSWORD", nullable = false, length = 250)
	private String txStPassword;

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
	 * @return the idStPassword
	 */
	public Long getIdStPassword() {
		return idStPassword;
	}

	/**
	 * @param idStPassword the idStPassword to set
	 */
	public void setIdStPassword(Long idStPassword) {
		this.idStPassword = idStPassword;
	}

	/**
	 * @return the cdStPassword
	 */
	public String getCdStPassword() {
		return cdStPassword;
	}

	/**
	 * @param cdStPassword the cdStPassword to set
	 */
	public void setCdStPassword(String cdStPassword) {
		this.cdStPassword = cdStPassword;
	}

	/**
	 * @return the nbStPassword
	 */
	public String getNbStPassword() {
		return nbStPassword;
	}

	/**
	 * @param nbStPassword the nbStPassword to set
	 */
	public void setNbStPassword(String nbStPassword) {
		this.nbStPassword = nbStPassword;
	}

	/**
	 * @return the txStPassword
	 */
	public String getTxStPassword() {
		return txStPassword;
	}

	/**
	 * @param txStPassword the txStPassword to set
	 */
	public void setTxStPassword(String txStPassword) {
		this.txStPassword = txStPassword;
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
	 * @return the cdColor
	 */
	public String getCdColor() {
		return cdColor;
	}

	/**
	 * @param cdColor the cdColor to set
	 */
	public void setCdColor(String cdColor) {
		this.cdColor = cdColor;
	}

}
