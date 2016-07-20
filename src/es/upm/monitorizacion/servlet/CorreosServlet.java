package es.upm.monitorizacion.servlet;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.logging.Logger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.TimeZone;

import javax.mail.BodyPart;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.ContentType;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import es.upm.monitorizacion.dao.DispositivosDAO;
import es.upm.monitorizacion.dao.DispositivosDAOImpl;
import es.upm.monitorizacion.dao.ResumenDispDAO;
import es.upm.monitorizacion.dao.ResumenDispDAOImpl;
import es.upm.monitorizacion.model.Dispositivos;

@SuppressWarnings("serial")
public class CorreosServlet extends HttpServlet {

  private static final Logger log = Logger.getLogger(CorreosServlet.class.getName());

  @Override
  public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    Properties props = new Properties();
    Session session = Session.getDefaultInstance(props, null);
    String mensaje = null;
    Date hora = new Date();
    try {
      MimeMessage message = new MimeMessage(session, req.getInputStream());
      log.info("Received mail message.");
      if ((hora.getTime() - message.getSentDate().getTime()) < 30000){
    	  Object contenido = message.getContent();
    	  if (contenido instanceof String){
    		  mensaje = (String) message.getContent();
    	  } else if (message.isMimeType("multipart/*")){
    		  MimeMultipart content = (MimeMultipart) message.getContent();
    		  for( int i=0; i<content.getCount(); i++ ) {
    			  BodyPart part = content.getBodyPart( i );
    			  ContentType ct = new ContentType( part.getContentType() );
            	  if (ct.getPrimaryType().equals("text") && ct.getSubType().equals("plain")){
            			  mensaje = (String) part.getContent();
            	  } 
    		  }
          } else if (message.isMimeType("text/plain")) {
        	  mensaje = (String) message.getContent();
          }
      }
      
    } catch (MessagingException e) {
      // ...
    }
    //System.out.println(mensaje);
        
    int f = 0;
    ArrayList<Dispositivos> dispo = new ArrayList<Dispositivos>();
	ArrayList<String> LAPS = new ArrayList<String>();
	DispositivosDAO dao = DispositivosDAOImpl.getInstance();
	ResumenDispDAO dao1 = ResumenDispDAOImpl.getInstance();
	String auxiliar = null;
    String auxLap = null;
	String auxSystime = null;
	
	if (mensaje != null){
		mensaje = mensaje.replace("&nbsp","");
		mensaje = mensaje.replace("\n","");
		log.info(mensaje);
    	String [] campos = mensaje.split("systime");
    	
    	while(f<campos.length){
        	//System.out.println(campos[f]);
        	//System.out.println("linea " + f);
        	
        	String[] disp = campos[f].split(" ");
        	if (disp.length > 8){
        		String[] systime = disp[0].split("=");
        		String[] cha = disp[1].split("=");
        		String[] LAP = disp[2].split("=");
        		String[] senal = disp[6].split("=");
        		String[] ruido = disp[7].split("=");
        		String[] SNR = disp[8].split("=");
        		if (LAP[1].length() == 6){
        			if (systime.length == 2 && cha.length == 2 && LAP.length == 2 && senal.length == 2 && ruido.length ==2 && SNR.length == 2){
                		SNR[1] = SNR[1].trim();
                		//log.info(SNR[1]);
                		Integer sNR = Integer.parseInt(SNR[1]);
                		if (LAP[1].equals(auxLap)){
                			if (!systime[1].equals(auxSystime) && sNR >= 10){
                				Date ahora = new Date();
                				Long milisAhora = ahora.getTime();
                				if(milisAhora > Long.parseLong(systime[1])){
                					Dispositivos dispositivo = dao.create(Long.parseLong(systime[1]), Integer.parseInt(cha[1]), LAP[1], Integer.parseInt(senal[1]), Integer.parseInt(ruido[1]), sNR);
                					dispo.add(dispositivo);
                					auxiliar = dispositivo.getLAP();
                					if (!LAPS.contains(auxiliar)){
                						LAPS.add(auxiliar);
                					} 
                				}
                				//System.out.println(dispositivo.toString());
                			}
                		} else {
                			if (sNR >= 10){
                				Date ahora = new Date();
                				Long milisAhora = ahora.getTime();
                				if(milisAhora > Long.parseLong(systime[1])){
                					Dispositivos dispositivo = dao.create(Long.parseLong(systime[1]), Integer.parseInt(cha[1]), LAP[1], Integer.parseInt(senal[1]), Integer.parseInt(ruido[1]), sNR);
                        			dispo.add(dispositivo);
                        			auxiliar = dispositivo.getLAP();
                        			if (!LAPS.contains(auxiliar)){
                        				LAPS.add(auxiliar);
                        			}
                				}
                    			//System.out.println(dispositivo.toString());
                			}
                		}
                		auxLap = LAP[1];
                		auxSystime = systime[1];
                	}
        		}
        	}
        	f++;
        }
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
				Date Ahora = new Date();
				if ((disp.get(j).getSystime()*1000) >= (Ahora.getTime()-3600000)){
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

			} else if((systimes.get(j) - systimeOUT) < 30){
				systimeOUT = systimes.get(j);
			} else if((systimes.get(j) - systimeOUT) > 30){
				//Guardamos la lectura del dispositivo
				Boolean aux = false;
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
				sdf.setTimeZone(TimeZone.getTimeZone("Europe/Madrid"));
				Date fechaEntrada = new Date();
				Date fechaSalida = new Date();
				if ((fechaEntrada.getTime()-systimeOUT*1000)<0){
					aux = true;
				}
				fechaEntrada.setTime(systimeIN*1000);
				fechaSalida.setTime(systimeOUT*1000);
				String fechaEn = sdf.format(fechaEntrada);
				String fechaSa = sdf.format(fechaSalida);
				
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
    dao.borrado();
  }
}