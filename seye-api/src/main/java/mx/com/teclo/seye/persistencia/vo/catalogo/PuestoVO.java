package mx.com.teclo.seye.persistencia.vo.catalogo;

import java.io.Serializable;

public class PuestoVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5988731090886692321L;

	private Long idPuesto;
	private String cdPuesto;
	private String nbPuesto;
	private String txPuesto;
	private Integer nuOrden;
	private Integer stActivo;

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
	 * @return the txPuesto
	 */
	public String getTxPuesto() {
		return txPuesto;
	}

	/**
	 * @param txPuesto the txPuesto to set
	 */
	public void setTxPuesto(String txPuesto) {
		this.txPuesto = txPuesto;
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
