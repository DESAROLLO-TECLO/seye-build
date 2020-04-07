package mx.com.teclo.seye.util.enumerado;

public enum BitacoraComponentesEnum {

	FINALIZAR_ENCUESTA((long) 2);

	private Long valor;
	
	private BitacoraComponentesEnum(Long valor) {
		this.setValor(valor);
	}

	public Long getValor() {
		return valor;
	}

	public void setValor(Long valor) {
		this.valor = valor;
	}
}
