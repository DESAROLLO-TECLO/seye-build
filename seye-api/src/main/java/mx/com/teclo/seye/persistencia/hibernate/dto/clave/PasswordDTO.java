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

@Entity
@Table(name = "TEE020D_PASSWORD")
public class PasswordDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4024993062190443345L;

	@Id
	@SequenceGenerator(name = "SQEE020D", sequenceName = "SQEE020D_PASSWORD", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQEE020D")
	@Column(name = "ID_PASSWORD", unique = true, nullable = false, precision = 11, scale = 0)
	private Long idPassword;

	@Column(name = "TX_VALOR", nullable = false, length = 300)
	private String txValor;

	@Column(name = "FH_VIGENCIA_INI", nullable = false, length = 7)
	private Date fhVigenciaIni;

	@Column(name = "FH_VIGENCIA_FIN", nullable = true, length = 7)
	private Date fhVigenciaFin;

	@Column(name = "ST_ACTUAL", nullable = false, precision = 1, scale = 0)
	private Integer stActual;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_ST_PASSWORD", nullable = false)
	private StPasswordDTO estatus;

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
	 * @return the idPassword
	 */
	public Long getIdPassword() {
		return idPassword;
	}

	/**
	 * @param idPassword the idPassword to set
	 */
	public void setIdPassword(Long idPassword) {
		this.idPassword = idPassword;
	}

	/**
	 * @return the txValor
	 */
	public String getTxValor() {
		return txValor;
	}

	/**
	 * @param txValor the txValor to set
	 */
	public void setTxValor(String txValor) {
		this.txValor = txValor;
	}

	/**
	 * @return the fhVigenciaIni
	 */
	public Date getFhVigenciaIni() {
		return fhVigenciaIni;
	}

	/**
	 * @param fhVigenciaIni the fhVigenciaIni to set
	 */
	public void setFhVigenciaIni(Date fhVigenciaIni) {
		this.fhVigenciaIni = fhVigenciaIni;
	}

	/**
	 * @return the fhVigenciaFin
	 */
	public Date getFhVigenciaFin() {
		return fhVigenciaFin;
	}

	/**
	 * @param fhVigenciaFin the fhVigenciaFin to set
	 */
	public void setFhVigenciaFin(Date fhVigenciaFin) {
		this.fhVigenciaFin = fhVigenciaFin;
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
	 * @return the stActual
	 */
	public Integer getStActual() {
		return stActual;
	}

	/**
	 * @param stActual the stActual to set
	 */
	public void setStActual(Integer stActual) {
		this.stActual = stActual;
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
	 * @return the estatus
	 */
	public StPasswordDTO getEstatus() {
		return estatus;
	}

	/**
	 * @param estatus the estatus to set
	 */
	public void setEstatus(StPasswordDTO estatus) {
		this.estatus = estatus;
	}

	
}
