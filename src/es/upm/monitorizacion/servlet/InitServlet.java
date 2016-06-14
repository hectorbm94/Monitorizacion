package es.upm.monitorizacion.servlet;

//import java.util.Date;

import javax.servlet.http.HttpServlet;

/*import es.upm.monitorizacion.dao.ResumenDispDAO;
import es.upm.monitorizacion.dao.ResumenDispDAOImpl;*/

@SuppressWarnings("serial")
public class InitServlet extends HttpServlet{

	public void init(){
		
		/*ResumenDispDAO dao = ResumenDispDAOImpl.getInstance();
		Date ahora = new Date();
		Long milisAhora = ahora.getTime();
		
		dao.create((milisAhora/1000) - 240, 39, "prueba1", (milisAhora/1000) - 120, "fecha3", "fecha1", 40);
		dao.create((milisAhora/1000) - 640, 39, "prueba4", (milisAhora/1000) - 420, "fecha8", "fecha6", 40);
		dao.create((milisAhora/1000) - 440, 39, "prueba3", (milisAhora/1000) - 320, "fecha7", "fecha4", 40);
		dao.create((milisAhora/1000) - 340, 39, "prueba2", (milisAhora/1000) - 220, "fecha5", "fecha2", 40);
		*/
	}

}
