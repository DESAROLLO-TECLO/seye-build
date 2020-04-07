package mx.com.teclo.seye.util.enumerados;

public enum LogsEnum {

	CREACION_REGISTRO("CREACION_REGISTRO","Se creo correctamente el registro."),
	ERROR_REGISTRO("ERROR_REGISTRO","Error al actualizar valores"),
	ACTUALIZACION_REGISTRO("ACTUALIZACION_REGISTRO","Se modifico correctamente el registro."),
	ELIMINACION_REGISTRO("ELIMINACION_REGISTRO","Se eliminó el perfil correctamente."),
	MODIFICAR_LOG("MODIFICAR_LOG","M"),
	NUEVO_LOG("NUEVO_LOG","A"),
	AGREGAR_PERFIL("AGREGAR_PERFIL","AP"),
	ELIMINAR_PERFIL("ELIMINAR_PERFIL","EP");
	
	private String nombre;
	private String action;
	
	private LogsEnum(String nombre,String action) {
		this.nombre= nombre;
		this.action= action;
	}

	public String getNombre() {
		return nombre;
	}

	public String getAction() {
		return action;
	}

		
}
