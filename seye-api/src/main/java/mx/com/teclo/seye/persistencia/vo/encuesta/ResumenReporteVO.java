package mx.com.teclo.seye.persistencia.vo.encuesta;

import java.io.InputStream;
import java.io.Serializable;

public class ResumenReporteVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2053093921114103804L;

	private String pregunta;
	private String opcion;
	private String opcionCorrecta;
	private Integer nuOrden;
	
	private InputStream respuesta;

	public ResumenReporteVO() {}
	
	public ResumenReporteVO(String pregunta, String opcion) {
		this.pregunta = pregunta;
		this.opcion = opcion;
	}
	
	/**
	 * @return the pregunta
	 */
	public String getPregunta() {
		return pregunta;
	}

	/**
	 * @param pregunta the pregunta to set
	 */
	public void setPregunta(String pregunta) {
		this.pregunta = pregunta;
	}

	/**
	 * @return the opcion
	 */
	public String getOpcion() {
		return opcion;
	}

	/**
	 * @param opcion the opcion to set
	 */
	public void setOpcion(String opcion) {
		this.opcion = opcion;
	}

	public InputStream getRespuesta() {
		return respuesta;
	}

	public void setRespuesta(InputStream respuesta) {
		this.respuesta = respuesta;
	}

	public String getOpcionCorrecta() {
		return opcionCorrecta;
	}

	public void setOpcionCorrecta(String opcionCorrecta) {
		this.opcionCorrecta = opcionCorrecta;
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
}
