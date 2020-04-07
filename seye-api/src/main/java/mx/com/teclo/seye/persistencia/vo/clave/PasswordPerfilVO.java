package mx.com.teclo.seye.persistencia.vo.clave;

import java.io.Serializable;

import mx.com.teclo.seye.persistencia.vo.perfil.PerfilVO;

public class PasswordPerfilVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4714427417574624975L;

	private Long idPasswordPerfil;
	private PasswordVO password;
	private PerfilVO perfil;

	/**
	 * @return the idPasswordPerfil
	 */
	public Long getIdPasswordPerfil() {
		return idPasswordPerfil;
	}

	/**
	 * @param idPasswordPerfil the idPasswordPerfil to set
	 */
	public void setIdPasswordPerfil(Long idPasswordPerfil) {
		this.idPasswordPerfil = idPasswordPerfil;
	}

	/**
	 * @return the password
	 */
	public PasswordVO getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(PasswordVO password) {
		this.password = password;
	}

	/**
	 * @return the perfil
	 */
	public PerfilVO getPerfil() {
		return perfil;
	}

	/**
	 * @param perfil the perfil to set
	 */
	public void setPerfil(PerfilVO perfil) {
		this.perfil = perfil;
	}

}
