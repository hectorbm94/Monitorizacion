package es.upm.monitorizacion.servlet;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.*;

import es.upm.monitorizacion.dao.DispositivosDAO;
import es.upm.monitorizacion.dao.DispositivosDAOImpl;
import es.upm.monitorizacion.model.Dispositivos;

@SuppressWarnings("serial")
public class MonitorizacionServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		
		DispositivosDAO dao = DispositivosDAOImpl.getInstance();
		
		List<Dispositivos> dispositivos = dao.readDispositivos();
		
		req.setAttribute("dispositivos", dispositivos);
		
		resp.sendRedirect("/pages/index.jsp");
	}
}
