package es.upm.monitorizacion.servlet;
import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.*;

import es.upm.monitorizacion.dao.ResumenDispDAO;
import es.upm.monitorizacion.dao.ResumenDispDAOImpl;
import es.upm.monitorizacion.model.*;

@SuppressWarnings("serial")
public class MonitorizacionServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		
		String alerta = null;
		ResumenDispDAO dao = ResumenDispDAOImpl.getInstance();
		
		req.getSession().setAttribute("dispositivos", new ArrayList<ResumenDisp>(dao.readResumenDisp()));
		
		if (req.getSession().getAttribute("captura") == null){
			req.getSession().setAttribute("alerta", alerta);
		} else {
			req.getSession().setAttribute("captura", null);
		}
		
		resp.sendRedirect("/pages/index.jsp");
	}
}
