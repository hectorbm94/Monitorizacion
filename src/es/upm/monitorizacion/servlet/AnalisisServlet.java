package es.upm.monitorizacion.servlet;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

import javax.servlet.ServletException;
import javax.servlet.http.*;

import es.upm.monitorizacion.dao.ResumenDispDAO;
import es.upm.monitorizacion.dao.ResumenDispDAOImpl;
import es.upm.monitorizacion.model.ResumenDisp;

@SuppressWarnings("serial")
public class AnalisisServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		
		ResumenDispDAO dao = ResumenDispDAOImpl.getInstance();
		req.getSession().setAttribute("dispositivos", new ArrayList<ResumenDisp>(dao.readResumenDisp()));
		
		ArrayList<ResumenDisp> arrivals = new ArrayList<ResumenDisp>();
		ArrayList<ResumenDisp> departures = new ArrayList<ResumenDisp>();
		int[] total = new int[5];
		String[] dias = new String[5];
		
		Long milisDia = (long) 86400000;
		Date ahora = new Date();
		Date hoy = new Date(ahora.getYear(), ahora.getMonth(), ahora.getDate(), 0, 0);
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		sdf.setTimeZone(TimeZone.getTimeZone("Europe/Madrid"));
		
		for (int i = 0; i < dias.length; i++){
			hoy.setDate(hoy.getDate()-i);
			dias[i] = sdf.format(hoy);
		}
		
		resp.sendRedirect("/pages/analisis.jsp");
	}
}