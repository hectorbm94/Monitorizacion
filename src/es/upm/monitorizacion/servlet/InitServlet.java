package es.upm.monitorizacion.servlet;

//import java.util.Date;

import javax.servlet.http.HttpServlet;
/*
import es.upm.monitorizacion.dao.ResumenDispDAO;
import es.upm.monitorizacion.dao.ResumenDispDAOImpl;
*/
@SuppressWarnings("serial")
public class InitServlet extends HttpServlet{

	public void init(){
		/*
		ResumenDispDAO dao = ResumenDispDAOImpl.getInstance();
		Date ahora = new Date();
		Long milisAhora = ahora.getTime();
		//Hoy
		dao.create((milisAhora/1000) - 240, 39, "461919", (milisAhora/1000) - 120, "23/06/2016 11:09:21", "23/06/2016 11:09:26", 40);
		dao.create((milisAhora/1000) - 640, 39, "c10414", (milisAhora/1000) - 420, "23/06/2016 12:09:21", "23/06/2016 12:09:37", 40);
		dao.create((milisAhora/1000) - 440, 39, "cdfe73", (milisAhora/1000) - 320, "23/06/2016 12:09:21", "23/06/2016 12:09:29", 40);
		dao.create((milisAhora/1000) - 340, 39, "050eea", (milisAhora/1000) - 220, "23/06/2016 14:09:21", "23/06/2016 14:09:26", 40);
		dao.create((milisAhora/1000) - 240, 39, "176e88", (milisAhora/1000) - 120, "23/06/2016 11:19:21", "23/06/2016 12:00:26", 40);
		dao.create((milisAhora/1000) - 840, 39, "1b79f1", (milisAhora/1000) - 620, "23/06/2016 11:19:21", "23/06/2016 12:00:26", 40);
		//Ayer
		dao.create((milisAhora/1000) - 86240, 39, "prueba1", (milisAhora/1000) - 120, "23/06/2016 11:09:21", "23/06/2016 11:09:26", 40);
		dao.create((milisAhora/1000) - 85640, 39, "prueba4", (milisAhora/1000) - 420, "23/06/2016 12:09:21", "23/06/2016 12:09:37", 40);
		dao.create((milisAhora/1000) - 86040, 39, "prueba3", (milisAhora/1000) - 320, "23/06/2016 12:09:21", "23/06/2016 12:09:29", 40);
		dao.create((milisAhora/1000) - 87440, 39, "prueba2", (milisAhora/1000) - 220, "23/06/2016 14:09:21", "23/06/2016 14:09:26", 40);
		dao.create((milisAhora/1000) - 88240, 39, "prueba1", (milisAhora/1000) - 120, "23/06/2016 11:19:21", "23/06/2016 12:00:26", 40);
		//Antes de ayer
		dao.create((milisAhora/1000) - 173240, 39, "prueba1", (milisAhora/1000) - 120, "23/06/2016 11:09:21", "23/06/2016 11:09:26", 40);
		dao.create((milisAhora/1000) - 174640, 39, "prueba4", (milisAhora/1000) - 420, "23/06/2016 12:09:21", "23/06/2016 12:09:37", 40);
		dao.create((milisAhora/1000) - 173440, 39, "prueba3", (milisAhora/1000) - 320, "23/06/2016 12:09:21", "23/06/2016 12:09:29", 40);
		dao.create((milisAhora/1000) - 174340, 39, "prueba2", (milisAhora/1000) - 220, "23/06/2016 14:09:21", "23/06/2016 14:09:26", 40);
		dao.create((milisAhora/1000) - 173240, 39, "prueba1", (milisAhora/1000) - 120, "23/06/2016 11:19:21", "23/06/2016 12:00:26", 40);
		//Hace 3 días
		dao.create((milisAhora/1000) - 259240, 39, "prueba1", (milisAhora/1000) - 120, "23/06/2016 11:09:21", "23/06/2016 11:09:26", 40);
		dao.create((milisAhora/1000) - 259640, 39, "prueba4", (milisAhora/1000) - 420, "23/06/2016 12:09:21", "23/06/2016 12:09:37", 40);
		dao.create((milisAhora/1000) - 259440, 39, "prueba3", (milisAhora/1000) - 320, "23/06/2016 12:09:21", "23/06/2016 12:09:29", 40);
		dao.create((milisAhora/1000) - 259340, 39, "prueba2", (milisAhora/1000) - 220, "23/06/2016 14:09:21", "23/06/2016 14:09:26", 40);
		dao.create((milisAhora/1000) - 259240, 39, "prueba1", (milisAhora/1000) - 120, "23/06/2016 11:19:21", "23/06/2016 12:00:26", 40);
		//Hace 4 días
		dao.create((milisAhora/1000) - 345240, 39, "prueba1", (milisAhora/1000) - 120, "23/06/2016 11:09:21", "23/06/2016 11:09:26", 40);
		dao.create((milisAhora/1000) - 345640, 39, "prueba4", (milisAhora/1000) - 420, "23/06/2016 12:09:21", "23/06/2016 12:09:37", 40);
		dao.create((milisAhora/1000) - 345440, 39, "prueba3", (milisAhora/1000) - 320, "23/06/2016 12:09:21", "23/06/2016 12:09:29", 40);
		dao.create((milisAhora/1000) - 345340, 39, "prueba2", (milisAhora/1000) - 220, "23/06/2016 14:09:21", "23/06/2016 14:09:26", 40);
		dao.create((milisAhora/1000) - 345240, 39, "prueba1", (milisAhora/1000) - 120, "23/06/2016 11:19:21", "23/06/2016 12:00:26", 40);
		*/
	}

}
