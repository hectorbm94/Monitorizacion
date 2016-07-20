package es.upm.monitorizacion.dao;

import java.util.List;

import es.upm.monitorizacion.model.ResumenDisp;

public interface ResumenDispDAO {
	
	public ResumenDisp create(Long systimeIN, int canal, String lAP, Long systimeOUT, String fechaEntrada, String fechaSalida, int sNRmedia);
	public List<ResumenDisp> readResumenDisp();
	public List<ResumenDisp> readResumenDisp_LAP(String LAP);
	public List<ResumenDisp> readResumenDisp_systimeIN(Long systimeIN);
	public List<ResumenDisp> readResumenDisp_systimeOUT(Long systimeOUT);
	public void borrado(Long systimeIN);
	
}
