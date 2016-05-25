package es.upm.monitorizacion.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Dispositivos implements Serializable {
	private static final long serialVersionUID = 01L;
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;
	private Long systime;
	private int canal;
	private String LAP;
	private int potenciaSe�al;
	private int potenciaRuido;
	private int SNR;
	
	public Dispositivos(Long systime, int canal, String lAP, int potenciaSe�al, int potenciaRuido, int sNR) {
		super();
		this.systime = systime;
		this.canal = canal;
		LAP = lAP;
		this.potenciaSe�al = potenciaSe�al;
		this.potenciaRuido = potenciaRuido;
		SNR = sNR;
	}

	public Long getSystime() {
		return systime;
	}

	public void setSystime(Long systime) {
		this.systime = systime;
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

	public int getPotenciaSe�al() {
		return potenciaSe�al;
	}

	public void setPotenciaSe�al(int potenciaSe�al) {
		this.potenciaSe�al = potenciaSe�al;
	}

	public int getPotenciaRuido() {
		return potenciaRuido;
	}

	public void setPotenciaRuido(int potenciaRuido) {
		this.potenciaRuido = potenciaRuido;
	}

	public int getSNR() {
		return SNR;
	}

	public void setSNR(int sNR) {
		SNR = sNR;
	}

	@Override
	public String toString() {
		return "Dispositivos [id=" + id + ", systime=" + systime + ", canal="
				+ canal + ", LAP=" + LAP + ", potenciaSe�al=" + potenciaSe�al
				+ ", potenciaRuido=" + potenciaRuido + ", SNR=" + SNR + "]";
	}
}
