package mx.com.teclo.seye.persistencia.vo.encuesta;

import java.io.Serializable;
import java.util.List;

public class EncuestaGeneralVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2707286348351235198L;

	private List<UsuarioEncuestaVO> usuarioEncuestaListVO;
	private UsuarioEncuestaVO usuarioEncuestaVO;

	/**
	 * @return the usuarioEncuestaListVO
	 */
	public List<UsuarioEncuestaVO> getUsuarioEncuestaListVO() {
		return usuarioEncuestaListVO;
	}

	/**
	 * @param usuarioEncuestaListVO the usuarioEncuestaListVO to set
	 */
	public void setUsuarioEncuestaListVO(List<UsuarioEncuestaVO> usuarioEncuestaListVO) {
		this.usuarioEncuestaListVO = usuarioEncuestaListVO;
	}

	/**
	 * @return the usuarioEncuestaVO
	 */
	public UsuarioEncuestaVO getUsuarioEncuestaVO() {
		return usuarioEncuestaVO;
	}

	/**
	 * @param usuarioEncuestaVO the usuarioEncuestaVO to set
	 */
	public void setUsuarioEncuestaVO(UsuarioEncuestaVO usuarioEncuestaVO) {
		this.usuarioEncuestaVO = usuarioEncuestaVO;
	}

}
