package mx.com.teclo.seye.persistencia.vo.catalogo;

import java.io.Serializable;

public class SedeVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3075833104008583356L;

	private Long idSede;
	private String cdSede;
	private String nbSede;
	private String txSede;
	private Integer nuOrden;
	private Integer stActivo;

	/**
	 * @return the idSede
	 */
	public Long getIdSede() {
		return idSede;
	}

	/**
	 * @param idSede the idSede to set
	 */
	public void setIdSede(Long idSede) {
		this.idSede = idSede;
	}

	/**
	 * @return the cdSede
	 */
	public String getCdSede() {
		return cdSede;
	}

	/**
	 * @param cdSede the cdSede to set
	 */
	public void setCdSede(String cdSede) {
		this.cdSede = cdSede;
	}

	/**
	 * @return the nbSede
	 */
	public String getNbSede() {
		return nbSede;
	}

	/**
	 * @param nbSede the nbSede to set
	 */
	public void setNbSede(String nbSede) {
		this.nbSede = nbSede;
	}

	/**
	 * @return the txSede
	 */
	public String getTxSede() {
		return txSede;
	}

	/**
	 * @param txSede the txSede to set
	 */
	public void setTxSede(String txSede) {
		this.txSede = txSede;
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

}
