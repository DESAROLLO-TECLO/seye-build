package mx.com.teclo.seye.persistencia.vo.clave;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import mx.com.teclo.seye.persistencia.vo.perfil.PerfilVO;

public class PasswordVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4024993062190443345L;

	private Long idPassword;
	private String txValor;
	private Date fhVigenciaIni;
	private Date fhVigenciaFin;
	private Integer stActual;
	private Integer stActivo;
	private List<PerfilVO> perfiles;
	private StPasswordVO estatus;

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
	 * @return the perfiles
	 */
	public List<PerfilVO> getPerfiles() {
		return perfiles;
	}

	/**
	 * @param perfiles the perfiles to set
	 */
	public void setPerfiles(List<PerfilVO> perfiles) {
		this.perfiles = perfiles;
	}

	/**
	 * @return the estatus
	 */
	public StPasswordVO getEstatus() {
		return estatus;
	}

	/**
	 * @param estatus the estatus to set
	 */
	public void setEstatus(StPasswordVO estatus) {
		this.estatus = estatus;
	}

}
