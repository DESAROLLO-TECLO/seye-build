/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.com.teclo.seye.persistencia.hibernate.dto.encuesta;

import java.io.Serializable;
import java.sql.Blob;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author unitis0521
 */
@Entity
@Table(name = "TEE022C_CONF_REPORTES", catalog = "")
public class ConfigReporteDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@Basic(optional = false)
	@Column(name = "ID_CONFIG", nullable = false)
	private Long idConfig;
	@Column(name = "ID_ENCUESTA", nullable = false)
	private Long idEncuesta;
	
	@Column(name = "TX_T_ENCUESTA", length = 250)
	private String txTituloEncuesta;
	@Column(name = "TX_C_ENCUESTA_C", length = 250)
	private String txCuerpoEncuestaCert; 
	@Column(name = "TX_C_ENCUESTA_I", length = 250)
	private String txCuerpoEncuestaInd;
	
	@Column(name = "LB_IMG_1")
	private Blob lbImg1;
	@Column(name = "LB_IMG_2")
	private Blob lbImg2;
	@Column(name = "LB_IMG_3")
	private Blob lbImg3;
	@Column(name = "LB_IMG_4")
	private Blob lbImg4;
	@Column(name = "LB_IMG_5")
	private Blob lbImg5;
	@Column(name = "LB_IMG_6")
	private Blob lbImg6;
	@Column(name = "LB_IMG_7")
	private Blob lbImg7;
	
	@Basic(optional = false)
	@Column(name = "ST_ACTIVO", nullable = false)
	private Integer stActivo;
	@Basic(optional = false)
	@Column(name = "ID_USR_CREACION", nullable = false)
	private Long idUsrCreacion;
	@Basic(optional = false)
	@Column(name = "FH_CREACION", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date fhCreacion;
	@Basic(optional = false)
	@Column(name = "ID_USR_MODIFICA", nullable = false)
	private Long idUsrModifica;
	@Basic(optional = false)
	@Column(name = "FH_MODIFICACION", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date fhModificacion;
	public Long getIdConfig() {
		return idConfig;
	}
	public void setIdConfig(Long idConfig) {
		this.idConfig = idConfig;
	}
	public Long getIdEncuesta() {
		return idEncuesta;
	}
	public void setIdEncuesta(Long idEncuesta) {
		this.idEncuesta = idEncuesta;
	}
	public String getTxTituloEncuesta() {
		return txTituloEncuesta;
	}
	public void setTxTituloEncuesta(String txTituloEncuesta) {
		this.txTituloEncuesta = txTituloEncuesta;
	}
	public String getTxCuerpoEncuestaCert() {
		return txCuerpoEncuestaCert;
	}
	public void setTxCuerpoEncuestaCert(String txCuerpoEncuestaCert) {
		this.txCuerpoEncuestaCert = txCuerpoEncuestaCert;
	}
	public String getTxCuerpoEncuestaInd() {
		return txCuerpoEncuestaInd;
	}
	public void setTxCuerpoEncuestaInd(String txCuerpoEncuestaInd) {
		this.txCuerpoEncuestaInd = txCuerpoEncuestaInd;
	}
	public Blob getLbImg1() {
		return lbImg1;
	}
	public void setLbImg1(Blob lbImg1) {
		this.lbImg1 = lbImg1;
	}
	public Blob getLbImg2() {
		return lbImg2;
	}
	public void setLbImg2(Blob lbImg2) {
		this.lbImg2 = lbImg2;
	}
	public Blob getLbImg3() {
		return lbImg3;
	}
	public void setLbImg3(Blob lbImg3) {
		this.lbImg3 = lbImg3;
	}
	public Blob getLbImg4() {
		return lbImg4;
	}
	public void setLbImg4(Blob lbImg4) {
		this.lbImg4 = lbImg4;
	}
	public Blob getLbImg5() {
		return lbImg5;
	}
	public void setLbImg5(Blob lbImg5) {
		this.lbImg5 = lbImg5;
	}
	public Blob getLbImg6() {
		return lbImg6;
	}
	public void setLbImg6(Blob lbImg6) {
		this.lbImg6 = lbImg6;
	}
	public Integer getStActivo() {
		return stActivo;
	}
	public void setStActivo(Integer stActivo) {
		this.stActivo = stActivo;
	}
	public Long getIdUsrCreacion() {
		return idUsrCreacion;
	}
	public void setIdUsrCreacion(Long idUsrCreacion) {
		this.idUsrCreacion = idUsrCreacion;
	}
	public Date getFhCreacion() {
		return fhCreacion;
	}
	public void setFhCreacion(Date fhCreacion) {
		this.fhCreacion = fhCreacion;
	}
	public Long getIdUsrModifica() {
		return idUsrModifica;
	}
	public void setIdUsrModifica(Long idUsrModifica) {
		this.idUsrModifica = idUsrModifica;
	}
	public Date getFhModificacion() {
		return fhModificacion;
	}
	public void setFhModificacion(Date fhModificacion) {
		this.fhModificacion = fhModificacion;
	}
	public Blob getLbImg7() {
		return lbImg7;
	}
	public void setLbImg7(Blob lbImg7) {
		this.lbImg7 = lbImg7;
	}
}
