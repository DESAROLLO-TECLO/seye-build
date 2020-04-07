package mx.com.teclo.seye.persistencia.vo.encuesta;

import java.io.Serializable;

public class EncuestasConUsuarioTotalesVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1830770138654250478L;
	private Long idEncuesta;
	private Long id_TipoEncuesta;
	private String cdEncuesta;
	private String nbEncuesta;
	private String txEncuesta;
	private String txInstrucciones;
	private Integer nuPreguntas;
	private String fhVigIni;
	private String fhVigFin;
	private Integer nuCalificacionApro;
	private Integer nuSecciones;
	private String cdTipoEncuesta;
	private String nbTipoEncuesta;
	private String nuEncuestados;
	private Integer promedio;
	private Integer aplicacionProm;
	private String efectividad;
	private Integer nuOrden;
	private String nbEncuestaOrigen;
	
	public Long getIdEncuesta() {
		return idEncuesta;
	}
	public void setIdEncuesta(Long idEncuesta) {
		this.idEncuesta = idEncuesta;
	}
	public Long getId_TipoEncuesta() {
		return id_TipoEncuesta;
	}
	public void setId_TipoEncuesta(Long id_TipoEncuesta) {
		this.id_TipoEncuesta = id_TipoEncuesta;
	}
	public String getCdEncuesta() {
		return cdEncuesta;
	}
	public void setCdEncuesta(String cdEncuesta) {
		this.cdEncuesta = cdEncuesta;
	}
	public String getNbEncuesta() {
		return nbEncuesta;
	}
	public void setNbEncuesta(String nbEncuesta) {
		this.nbEncuesta = nbEncuesta;
	}
	public String getTxEncuesta() {
		return txEncuesta;
	}
	public void setTxEncuesta(String txEncuesta) {
		this.txEncuesta = txEncuesta;
	}
	public String getTxInstrucciones() {
		return txInstrucciones;
	}
	public void setTxInstrucciones(String txInstrucciones) {
		this.txInstrucciones = txInstrucciones;
	}
	public Integer getNuPreguntas() {
		return nuPreguntas;
	}
	public void setNuPreguntas(Integer nuPreguntas) {
		this.nuPreguntas = nuPreguntas;
	}
	public String getFhVigIni() {
		return fhVigIni;
	}
	public void setFhVigIni(String fhVigIni) {
		this.fhVigIni = fhVigIni;
	}
	public String getFhVigFin() {
		return fhVigFin;
	}
	public void setFhVigFin(String fhVigFin) {
		this.fhVigFin = fhVigFin;
	}
	public Integer getNuCalificacionApro() {
		return nuCalificacionApro;
	}
	public void setNuCalificacionApro(Integer nuCalificacionApro) {
		this.nuCalificacionApro = nuCalificacionApro;
	}
	public Integer getNuSecciones() {
		return nuSecciones;
	}
	public void setNuSecciones(Integer nuSecciones) {
		this.nuSecciones = nuSecciones;
	}
	public String getCdTipoEncuesta() {
		return cdTipoEncuesta;
	}
	public void setCdTipoEncuesta(String cdTipoEncuesta) {
		this.cdTipoEncuesta = cdTipoEncuesta;
	}
	public String getNbTipoEncuesta() {
		return nbTipoEncuesta;
	}
	public void setNbTipoEncuesta(String nbTipoEncuesta) {
		this.nbTipoEncuesta = nbTipoEncuesta;
	}
	public String getNuEncuestados() {
		return nuEncuestados;
	}
	public void setNuEncuestados(String nuEncuestados) {
		this.nuEncuestados = nuEncuestados;
	}
	public Integer getPromedio() {
		return promedio;
	}
	public void setPromedio(Integer promedio) {
		this.promedio = promedio;
	}
	public Integer getAplicacionProm() {
		return aplicacionProm;
	}
	public void setAplicacionProm(Integer aplicacionProm) {
		this.aplicacionProm = aplicacionProm;
	}
	public String getEfectividad() {
		return efectividad;
	}
	public void setEfectividad(String efectividad) {
		this.efectividad = efectividad;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public Integer getNuOrden() {
		return nuOrden;
	}
	public void setNuOrden(Integer nuOrden) {
		this.nuOrden = nuOrden;
	}
	public String getNbEncuestaOrigen() {
		return nbEncuestaOrigen;
	}
	public void setNbEncuestaOrigen(String nbEncuestaOrigen) {
		this.nbEncuestaOrigen = nbEncuestaOrigen;
	}
}
