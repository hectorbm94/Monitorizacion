package es.upm.monitorizacion.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class ResumenDisp implements Serializable {
	private static final long serialVersionUID = 01L;
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;
	private Long systimeIN;
	private Long systimeOUT;
	private int canal;
	private String LAP;
	private String fechaEntrada;
	private String fechaSalida;
	private int SNRmedia;
	
	public ResumenDisp(Long systimeIN, int canal, String lAP, Long systimeOUT, String fechaEntrada, String fechaSalida, int sNRmedia) {
		super();
		this.systimeIN = systimeIN;
		this.systimeOUT = systimeOUT;
		this.fechaEntrada = fechaEntrada;
		this.fechaSalida = fechaSalida;
		this.canal = canal;
		LAP = lAP;
		SNRmedia = sNRmedia;
	}
	
	public Long getId() {
		return id;
	}
	
	public Long getSystimeIN() {
		return systimeIN;
	}

	public void setSystimeIN(Long systimeIN) {
		this.systimeIN = systimeIN;
	}

	public Long getSystimeOUT() {
		return systimeOUT;
	}

	public void setSystimeOUT(Long systimeOUT) {
		this.systimeOUT = systimeOUT;
	}

	public int getCanal() {
		return canal;
	}

	public void setCanal(int canal) {
		this.canal = canal;
	}

	public String getLAP() {
		return LAP;
	}

	public void setLAP(String lAP) {
		LAP = lAP;
	}

	public String getFechaEntrada() {
		return fechaEntrada;
	}

	public void setFechaEntrada(String fechaEntrada) {
		this.fechaEntrada = fechaEntrada;
	}

	public String getFechaSalida() {
		return fechaSalida;
	}

	public void setFechaSalida(String fechaSalida) {
		this.fechaSalida = fechaSalida;
	}

	public int getSNRmedia() {
		return SNRmedia;
	}

	public void setSNRmedia(int sNRmedia) {
		SNRmedia = sNRmedia;
	}

	@Override
	public String toString() {
		return "ResumenDisp [id=" + id + ", systimeIN=" + systimeIN
				+ ", systimeOUT=" + systimeOUT + ", canal=" + canal + ", LAP="
				+ LAP + ", fechaEntrada=" + fechaEntrada + ", fechaSalida="
				+ fechaSalida + ", SNRmedia=" + SNRmedia + "]";
	}

	
}
