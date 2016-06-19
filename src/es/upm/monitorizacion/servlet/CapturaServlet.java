package es.upm.monitorizacion.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
//import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import javax.servlet.ServletException;
import javax.servlet.http.*;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreInputStream;
//import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;

import es.upm.monitorizacion.dao.DispositivosDAO;
import es.upm.monitorizacion.dao.DispositivosDAOImpl;
import es.upm.monitorizacion.dao.ResumenDispDAO;
import es.upm.monitorizacion.dao.ResumenDispDAOImpl;
import es.upm.monitorizacion.model.*;

@SuppressWarnings("serial")
public class CapturaServlet extends HttpServlet {
	
	//private BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();

	public void doPost(HttpServletRequest req, HttpServletResponse resp)  throws IOException, ServletException {
		
		String alerta = null;
		String auxiliar = null;
		ArrayList<Dispositivos> dispo = new ArrayList<Dispositivos>();
		ArrayList<String> LAPS = new ArrayList<String>();
		DispositivosDAO dao = DispositivosDAOImpl.getInstance();
		ResumenDispDAO dao1 = ResumenDispDAOImpl.getInstance();
		Map<String, List<BlobKey>> blobs = BlobstoreServiceFactory.getBlobstoreService().getUploads(req);
		List<BlobKey> blobKeys = blobs.get("txt");
		System.out.println(blobKeys.toString());
		if (blobKeys == null || blobKeys.isEmpty() || blobKeys.get(0) == null) {
			//resp.getWriter().println("err");
			alerta = "Ha ocurrido un problema con el fichero";
		} else {
			
			alerta = "Fichero subido con éxito";
			BlobKey blobKey = new BlobKey(blobKeys.get(0).getKeyString());
			BlobstoreInputStream is =new BlobstoreInputStream(blobKey);  

			BufferedReader reader = new BufferedReader(new InputStreamReader(is));
			String line = null;
			String auxLap = null;
			String auxSystime = null;
			StringBuilder a = new StringBuilder();
			//String log = null;
			while((line = reader.readLine()) != null) {
				a.append(line);
				String[] disp = line.split(" ");
				//System.out.println(Arrays.toString(disp));
				String[] systime = disp[0].split("=");
				//System.out.println(systime[1]);
				String[] cha = disp[1].split("=");
				String[] LAP = disp[2].split("=");
				String[] senal = disp[6].split("=");
				String[] ruido = disp[7].split("=");
				String[] SNR = disp[8].split("=");
				
				if (LAP[1].equals(auxLap)){
					if (!systime[1].equals(auxSystime) && Integer.parseInt(SNR[1]) >= 10){
						
							Dispositivos dispositivo = dao.create(Long.parseLong(systime[1]), Integer.parseInt(cha[1]), LAP[1], Integer.parseInt(senal[1]), Integer.parseInt(ruido[1]), Integer.parseInt(SNR[1]));
							dispo.add(dispositivo);
							auxiliar = dispositivo.getLAP();
							if (!LAPS.contains(auxiliar)){
								LAPS.add(auxiliar);
							} 
						//System.out.println(dispositivo.toString());
					}
				} else {
					if (Integer.parseInt(SNR[1]) >= 10){
						Dispositivos dispositivo = dao.create(Long.parseLong(systime[1]), Integer.parseInt(cha[1]), LAP[1], Integer.parseInt(senal[1]), Integer.parseInt(ruido[1]), Integer.parseInt(SNR[1]));
						dispo.add(dispositivo);
						auxiliar = dispositivo.getLAP();
						if (!LAPS.contains(auxiliar)){
							LAPS.add(auxiliar);
						} 
						//System.out.println(dispositivo.toString());
					}
				}
				
				//a.append("/");
				auxLap = LAP[1];
				auxSystime = systime[1];
			}
			reader.close();
			//log = a.toString();
			//System.out.println(log);
			
		}
		
		for(int i = 0; i < LAPS.size(); i++){
			
			List<Dispositivos> disp = dao.readDispositivos_LAP(LAPS.get(i));
			ArrayList<Long> systimes = new ArrayList<Long>();
			String lAP = LAPS.get(i);
			long systimeIN = (long) 0;
			long systimeOUT = (long) 0;
			int canal = 0;
			int SNRmedia = 0;
			int contador = 0;
				
			for(int j = 0; j < disp.size(); j++){
					systimes.add(disp.get(j).getSystime());
					if (j == 0){
						canal = disp.get(j).getCanal();
					}
					if (dispo.get(0).getId() <= disp.get(j).getId()){
						SNRmedia += disp.get(j).getSNR();
						contador++;
					}
			}
			if (contador > 0){
				SNRmedia = SNRmedia/contador;
			}
			Collections.sort(systimes);
			for(int j = 0; j < systimes.size(); j++){
				if (j == 0){
					systimeIN = systimes.get(j);
					systimeOUT = systimes.get(j);

				} else if((systimes.get(j) - systimeOUT) < 10){
					systimeOUT = systimes.get(j);
				} else if((systimes.get(j) - systimeOUT) > 10){
					//Guardamos la lectura del dispositivo
					SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
					sdf.setTimeZone(TimeZone.getTimeZone("Europe/Madrid"));
					Date fechaEntrada = new Date();
					Date fechaSalida = new Date();
					fechaEntrada.setTime(systimeIN*1000);
					fechaSalida.setTime(systimeOUT*1000);
					String fechaEn = sdf.format(fechaEntrada);
					String fechaSa = sdf.format(fechaSalida);
					Boolean aux = false;
					for (int k = 0; k < dao1.readResumenDisp_systimeIN(systimeIN).size(); k++){
						if (dao1.readResumenDisp_systimeIN(systimeIN).get(k).getLAP().equals(lAP)){
							
							aux = true;
						}
						if (k < dao1.readResumenDisp_systimeOUT(systimeOUT).size() && dao1.readResumenDisp_systimeOUT(systimeOUT).get(k).getLAP().equals(lAP)){
							aux = true;
						}
					}
					if (!aux){
						dao1.create(systimeIN, canal, lAP, systimeOUT, fechaEn, fechaSa, SNRmedia);
					}
					//Generamos una nueva lectura del mismo dispositivo
					systimeIN = systimes.get(j);
					systimeOUT = systimes.get(j);
				}
			}

			//Guardamos la lectura del dispositivo
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			sdf.setTimeZone(TimeZone.getTimeZone("Europe/Madrid"));
			Date fechaEntrada = new Date();
			Date fechaSalida = new Date();
			fechaEntrada.setTime(systimeIN*1000);
			fechaSalida.setTime(systimeOUT*1000);
			String fechaEn = sdf.format(fechaEntrada);
			String fechaSa = sdf.format(fechaSalida);
			Boolean aux = false;
			for (int k = 0; k < dao1.readResumenDisp_systimeIN(systimeIN).size(); k++){
				if (dao1.readResumenDisp_systimeIN(systimeIN).get(k).getLAP().equals(lAP)){
					
					aux = true;
				}
				if (k < dao1.readResumenDisp_systimeOUT(systimeOUT).size() && dao1.readResumenDisp_systimeOUT(systimeOUT).get(k).getLAP().equals(lAP)){
					aux = true;
				}
			}
			if (!aux){
				dao1.create(systimeIN, canal, lAP, systimeOUT, fechaEn, fechaSa, SNRmedia);
			}

		}
		
		req.getSession().setAttribute("alerta", alerta);
		req.getSession().setAttribute("captura", "true");
		resp.sendRedirect("/monitorizacion");
	}
}
