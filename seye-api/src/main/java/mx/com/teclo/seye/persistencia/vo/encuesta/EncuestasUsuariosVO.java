package mx.com.teclo.seye.persistencia.vo.encuesta;

import java.util.Date;

import mx.com.teclo.seye.persistencia.vo.usuario.UsuarioVO;

public class EncuestasUsuariosVO {
	
	private Long idUsuarioEncuesta;
	private UsuarioVO usuario;
    private Integer nuIntegerentos;
    private Boolean stAplicaEncuesta;
    private Boolean stActivo;
    private Long idUsrCreacion;
    private Date fhCreacion;
    private Long idUsrModifica;
    private Date fhModificacion;
    private EncuestaVO encuesta;
    
	public Long getIdUsuarioEncuesta() {
		return idUsuarioEncuesta;
	}
	public void setIdUsuarioEncuesta(Long idUsuarioEncuesta) {
		this.idUsuarioEncuesta = idUsuarioEncuesta;
	}
	public UsuarioVO getUsuario() {
		return usuario;
	}
	public void setUsuario(UsuarioVO usuario) {
		this.usuario = usuario;
	}
	public Integer getNuIntegerentos() {
		return nuIntegerentos;
	}
	public void setNuIntegerentos(Integer nuIntegerentos) {
		this.nuIntegerentos = nuIntegerentos;
	}
	public Boolean getStAplicaEncuesta() {
		return stAplicaEncuesta;
	}
	public void setStAplicaEncuesta(Boolean stAplicaEncuesta) {
		this.stAplicaEncuesta = stAplicaEncuesta;
	}
	public Boolean getStActivo() {
		return stActivo;
	}
	public void setStActivo(Boolean stActivo) {
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
	public EncuestaVO getEncuesta() {
		return encuesta;
	}
	public void setEncuesta(EncuestaVO encuesta) {
		this.encuesta = encuesta;
	}
}
