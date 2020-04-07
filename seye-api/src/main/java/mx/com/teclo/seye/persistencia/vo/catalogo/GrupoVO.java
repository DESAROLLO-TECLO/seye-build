package mx.com.teclo.seye.persistencia.vo.catalogo;

import java.io.Serializable;

public class GrupoVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4029809029138145118L;

	private Long idGrupo;
	private String cdGrupo;
	private String nbGrupo;
	private String txGrupo;
	private Integer nuOrden;
	private Integer stActivo;

	/**
	 * @return the idGrupo
	 */
	public Long getIdGrupo() {
		return idGrupo;
	}

	/**
	 * @param idGrupo the idGrupo to set
	 */
	public void setIdGrupo(Long idGrupo) {
		this.idGrupo = idGrupo;
	}

	/**
	 * @return the cdGrupo
	 */
	public String getCdGrupo() {
		return cdGrupo;
	}

	/**
	 * @param cdGrupo the cdGrupo to set
	 */
	public void setCdGrupo(String cdGrupo) {
		this.cdGrupo = cdGrupo;
	}

	/**
	 * @return the nbGrupo
	 */
	public String getNbGrupo() {
		return nbGrupo;
	}

	/**
	 * @param nbGrupo the nbGrupo to set
	 */
	public void setNbGrupo(String nbGrupo) {
		this.nbGrupo = nbGrupo;
	}

	/**
	 * @return the txGrupo
	 */
	public String getTxGrupo() {
		return txGrupo;
	}

	/**
	 * @param txGrupo the txGrupo to set
	 */
	public void setTxGrupo(String txGrupo) {
		this.txGrupo = txGrupo;
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
