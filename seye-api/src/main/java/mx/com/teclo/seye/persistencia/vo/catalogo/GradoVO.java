package mx.com.teclo.seye.persistencia.vo.catalogo;

import java.io.Serializable;

public class GradoVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3632822890737083019L;

	private Long idGrado;
	private String cdGrado;
	private String nbGrado;
	private String txGrado;
	private Integer nuOrden;
	private Integer stActivo;

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
	 * @return the txGrado
	 */
	public String getTxGrado() {
		return txGrado;
	}

	/**
	 * @param txGrado the txGrado to set
	 */
	public void setTxGrado(String txGrado) {
		this.txGrado = txGrado;
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
