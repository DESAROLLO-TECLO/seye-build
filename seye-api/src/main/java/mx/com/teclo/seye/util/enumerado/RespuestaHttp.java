package mx.com.teclo.seye.util.enumerado;

public enum RespuestaHttp {

	
	NOT_FOUND("No se encontraron registros","404"),
	CONFLICT("Hubo conflictos al tratar de generar un nuevo intento","409");

	private String message;
	private String code;

	private RespuestaHttp(String message, String code) {
		this.message = message;
		this.code = code;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}

	static RespuestaHttp getRespuestaHttp(String x) {
		for (RespuestaHttp currentType : RespuestaHttp.values()) {
			if (x.equals(currentType.getMessage())) {
				return currentType;
			}
		}
		return null;
	}
	
}
