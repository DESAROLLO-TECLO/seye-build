package mx.com.teclo.seye.persistencia.vo.encuesta;

import java.io.Serializable;

public class EncuestaUsuarioIntentoPDFMVO implements Serializable  {

	/**
	 * 
	 */
	private static final long serialVersionUID = -922399712344805896L;
	
	private Long idUsuEncuIntento;
	private Long idUsuario;
	private String opcion;
	
	public Long getIdUsuEncuIntento() {
		return idUsuEncuIntento;
	}
	public void setIdUsuEncuIntento(Long idUsuEncuIntento) {
		this.idUsuEncuIntento = idUsuEncuIntento;
	}
	public Long getIdUsuario() {
		return idUsuario;
	}
	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}
	public String getOpcion() {
		return opcion;
	}
	public void setOpcion(String opcion) {
		this.opcion = opcion;
	}
}
