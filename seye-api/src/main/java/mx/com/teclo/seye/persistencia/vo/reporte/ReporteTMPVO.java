package mx.com.teclo.seye.persistencia.vo.reporte;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;

public class ReporteTMPVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5372683368382625240L;

	private ByteArrayOutputStream bao;
	private String fileName;
	private byte[] archivoByteArray;
	/**
	 * @return the bao
	 */
	public ByteArrayOutputStream getBao() {
		return bao;
	}

	/**
	 * @param bao the bao to set
	 */
	public void setBao(ByteArrayOutputStream bao) {
		this.bao = bao;
	}

	/**
	 * @return the fileName
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * @param fileName the fileName to set
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public byte[] getArchivoByteArray() {
		return archivoByteArray;
	}

	public void setArchivoByteArray(byte[] archivoByteArray) {
		this.archivoByteArray = archivoByteArray;
	}
	
}
