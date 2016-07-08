package es.upm.monitorizacion.servlet;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import javax.servlet.ServletException;
import javax.servlet.http.*;

import es.upm.monitorizacion.dao.ResumenDispDAO;
import es.upm.monitorizacion.dao.ResumenDispDAOImpl;
import es.upm.monitorizacion.model.ResumenDisp;

@SuppressWarnings("serial")
public class AnalisisServlet extends HttpServlet {
	@SuppressWarnings("deprecation")
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		
		ResumenDispDAO dao = ResumenDispDAOImpl.getInstance();
		req.getSession().setAttribute("dispositivos", new ArrayList<ResumenDisp>(dao.readResumenDisp()));
		
		ArrayList<ResumenDisp> arrivals = new ArrayList<ResumenDisp>();
		ArrayList<ResumenDisp> departures = new ArrayList<ResumenDisp>();
		ArrayList<String> totalLap = new ArrayList<String>();
		ArrayList<String> horasAux = new ArrayList<String>();
		Integer[] total = new Integer[5];
		for(int i = 0; i < total.length; i++){
			total[i]=0;
		}
		Integer[] horas = new Integer[24];
		for(int i = 0; i < horas.length; i++){
			horas[i]=0;
		}
		String[] dias = new String[5];
		
		Long milisDia = (long) 86400000;
		Date ahora = new Date();
		Long milisAhora = ahora.getTime();
		Long milisHoy = (long) ahora.getTime() - (ahora.getHours()*60*60*1000) - (ahora.getMinutes()*60*1000) - (ahora.getSeconds()*1000);
		Date hoy = new Date(ahora.getYear(), ahora.getMonth(), ahora.getDate(), 0, 0);
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		sdf.setTimeZone(TimeZone.getTimeZone("Europe/Madrid"));
		for (int i = 0; i < dias.length; i++){
			if ( i == 0){
				dias[i] = sdf.format(hoy);
			} else{
				hoy.setDate(hoy.getDate()-1);
				dias[i] = sdf.format(hoy);
			}
		}
		
		int hora = -1;
		boolean aux = true;
		List<ResumenDisp> dispo = dao.readResumenDisp();
		for (int i = 0; i < dispo.size(); i++){
			
			if ((dispo.get(i).getSystimeIN()*1000) > milisHoy){

				if (!totalLap.contains(dispo.get(i).getLAP() + "0")){
					totalLap.add(dispo.get(i).getLAP() + "0");
					total[0]++;
				}
					
				SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
				sdf.setTimeZone(TimeZone.getTimeZone("Europe/Madrid"));
				try {
					Date fechaEntrada = sdf1.parse(dispo.get(i).getFechaEntrada());
					hora = fechaEntrada.getHours();
				} catch (ParseException e) {
					e.printStackTrace();
				}
				if (hora != -1){
					if (!horasAux.contains(dispo.get(i).getLAP()+hora)){
						horas[horas.length-hora-1]++;
						horasAux.add(dispo.get(i).getLAP()+hora);
					}

				}
				
				if (dispo.get(i).getSystimeIN()*1000 >= milisAhora-3600000){

					if (arrivals.size() == 0){
						arrivals.add(dispo.get(i));
					} else{
						for (int j = 0; j < arrivals.size(); j++){
							if (dispo.get(i).getSystimeIN() >= arrivals.get(j).getSystimeIN()){
								arrivals.add(j, dispo.get(i));
								aux = false;
								break;
							}						
						}
						if (aux){
							arrivals.add(dispo.get(i));
						}
						aux = true;
					}
				}
				if (dispo.get(i).getSystimeOUT()*1000 >= milisAhora-3600000 && dispo.get(i).getSystimeOUT()*1000 <= milisAhora-600000){

					if (departures.size() == 0){
						departures.add(dispo.get(i));
					} else{	
						for (int j = 0; j < departures.size(); j++){
							if (dispo.get(i).getSystimeOUT() >= departures.get(j).getSystimeOUT()){
								departures.add(j, dispo.get(i));
								aux = false;
								break;
							}						
						}
						if (aux){
							departures.add(dispo.get(i));
						}
						aux = true;
					}
				}
			}
			else if ((dispo.get(i).getSystimeIN()*1000) < milisHoy && (dispo.get(i).getSystimeIN()*1000) > (milisHoy - milisDia)){
				if (!totalLap.contains(dispo.get(i).getLAP() + "1")){
					totalLap.add(dispo.get(i).getLAP() + "1");
					total[1]++;
				} 
			}
			else if ((dispo.get(i).getSystimeIN()*1000) < (milisHoy - milisDia) && (dispo.get(i).getSystimeIN()*1000) > (milisHoy - 2*milisDia)){
				if (!totalLap.contains(dispo.get(i).getLAP() + "2")){
					totalLap.add(dispo.get(i).getLAP() + "2");
					total[2]++;
				} 
			}
			else if ((dispo.get(i).getSystimeIN()*1000) < (milisHoy - 2*milisDia) && (dispo.get(i).getSystimeIN()*1000) > (milisHoy - 3*milisDia)){
				if (!totalLap.contains(dispo.get(i).getLAP() + "3")){
					totalLap.add(dispo.get(i).getLAP() + "3");
					total[3]++;
				} 
			}
			else if ((dispo.get(i).getSystimeIN()*1000) < (milisHoy - 3*milisDia) && (dispo.get(i).getSystimeIN()*1000) > (milisHoy - 4*milisDia)){
				if (!totalLap.contains(dispo.get(i).getLAP() + "4")){
					totalLap.add(dispo.get(i).getLAP() + "4");
					total[4]++;
				} 
			}
		}
		
		req.getSession().setAttribute("milisAhora", (milisAhora/1000));
		req.getSession().setAttribute("arrivals", arrivals);
		req.getSession().setAttribute("departures", departures);
		req.getSession().setAttribute("total", Arrays.asList(total));
		req.getSession().setAttribute("horas", Arrays.asList(horas));
		req.getSession().setAttribute("dias", Arrays.asList(dias));
		resp.sendRedirect("/pages/analisis.jsp");
	}
}