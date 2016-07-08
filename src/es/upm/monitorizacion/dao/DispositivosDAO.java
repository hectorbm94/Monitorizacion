package es.upm.monitorizacion.dao;

import java.util.List;

import es.upm.monitorizacion.model.Dispositivos;

public interface DispositivosDAO {
	
	public Dispositivos create(Long systime, int canal, String lAP, int potenciaSeñal, int potenciaRuido, int sNR);
	public List<Dispositivos> readDispositivos();
	public List<Dispositivos> readDispositivos_LAP(String LAP);
	public List<Dispositivos> readDispositivos_canal(int canal);
	public List<Dispositivos> readDispositivos_id(Long id);
	public List<Dispositivos> readDispositivos_systime(Long systime);
	public void borrado();
}
