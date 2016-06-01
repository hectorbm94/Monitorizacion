package es.upm.monitorizacion.servlet;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.*;

import es.upm.monitorizacion.dao.DispositivosDAO;
import es.upm.monitorizacion.dao.DispositivosDAOImpl;
import es.upm.monitorizacion.model.Dispositivos;

@SuppressWarnings("serial")
public class MonitorizacionServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		
		String alerta = null;
		DispositivosDAO dao = DispositivosDAOImpl.getInstance();
		
		req.getSession().setAttribute("dispositivos", new ArrayList<Dispositivos>(dao.readDispositivos()));
		
		if (req.getSession().getAttribute("captura") == null){
			req.getSession().setAttribute("alerta", alerta);
		} else {
			req.getSession().setAttribute("captura", null);
		}
		
		resp.sendRedirect("/pages/index.jsp");
	}
}
