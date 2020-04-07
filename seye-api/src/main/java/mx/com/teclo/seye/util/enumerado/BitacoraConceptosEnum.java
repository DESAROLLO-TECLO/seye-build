package mx.com.teclo.seye.util.enumerado;

public enum BitacoraConceptosEnum {

	FINALIZAR_ENCUESTA((long) 1);

	private Long valor;
	
	private BitacoraConceptosEnum(Long valor) {
		this.setValor(valor);
	}

	public Long getValor() {
		return valor;
	}

	public void setValor(Long valor) {
		this.valor = valor;
	}
}
