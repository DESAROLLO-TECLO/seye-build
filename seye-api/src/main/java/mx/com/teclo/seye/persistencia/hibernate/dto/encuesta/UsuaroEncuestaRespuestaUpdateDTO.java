/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.com.teclo.seye.persistencia.hibernate.dto.encuesta;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author unitis0521
 */
@Entity
@Table(name = "TEE003D_EE_USU_ENC_RESP", catalog = "")
public class UsuaroEncuestaRespuestaUpdateDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    private UsuaroEncuestaRespuestaDTOPK id;
    @Basic(optional = false)
    @Column(name = "ID_OPCION", nullable = false)
    private Long idOpcion;
    @Column(name = "FH_LECTURA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fhLectura;
    @Column(name = "FH_RESPUESTA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fhRespuesta;
    @Column(name = "NU_INTENTOS")
    private Integer nuIntentos;
    @Basic(optional = false)
    @Column(name = "ST_ACTIVO", nullable = false)
    private Integer stActivo;
	/**
	 * @return the id
	 */
	public UsuaroEncuestaRespuestaDTOPK getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(UsuaroEncuestaRespuestaDTOPK id) {
		this.id = id;
	}
	/**
	 * @return the idOpcion
	 */
	public Long getIdOpcion() {
		return idOpcion;
	}
	/**
	 * @param idOpcion the idOpcion to set
	 */
	public void setIdOpcion(Long idOpcion) {
		this.idOpcion = idOpcion;
	}
	/**
	 * @return the fhLectura
	 */
	public Date getFhLectura() {
		return fhLectura;
	}
	/**
	 * @param fhLectura the fhLectura to set
	 */
	public void setFhLectura(Date fhLectura) {
		this.fhLectura = fhLectura;
	}
	/**
	 * @return the fhRespuesta
	 */
	public Date getFhRespuesta() {
		return fhRespuesta;
	}
	/**
	 * @param fhRespuesta the fhRespuesta to set
	 */
	public void setFhRespuesta(Date fhRespuesta) {
		this.fhRespuesta = fhRespuesta;
	}
	/**
	 * @return the nuIntentos
	 */
	public Integer getNuIntentos() {
		return nuIntentos;
	}
	/**
	 * @param nuIntentos the nuIntentos to set
	 */
	public void setNuIntentos(Integer nuIntentos) {
		this.nuIntentos = nuIntentos;
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
