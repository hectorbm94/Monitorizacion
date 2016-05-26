package es.upm.monitorizacion.servlet;
import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.*;

@SuppressWarnings("serial")
public class MonitorizacionServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		
		RequestDispatcher view = req.getRequestDispatcher("/pages/index.jsp");
		
		try {
			view.forward(req, resp);
		} catch (ServletException e) {
			e.printStackTrace();
		}
		
	}
}
