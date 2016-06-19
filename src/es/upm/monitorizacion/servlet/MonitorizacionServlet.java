package es.upm.monitorizacion.servlet;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
		ArrayList<ResumenDisp> ordenados = new ArrayList<ResumenDisp>();
		List<ResumenDisp> dispo = dao.readResumenDisp();
		boolean aux = true;
		
		for (int i = 0; i < dispo.size(); i++){
			if (ordenados.size() != 0){
				for (int j = 0; j < ordenados.size(); j++){
					if (dispo.get(i).getSystimeIN() >= ordenados.get(j).getSystimeIN()){
						ordenados.add(j, dispo.get(i));
						aux = false;
						break;
					}
				}
				if (aux){
					ordenados.add(dispo.get(i));
				}
				aux = true;
			} else{
				ordenados.add(dispo.get(i));
			}		
		}
		
		//System.out.println(ordenados.size());
		req.getSession().setAttribute("dispositivos", ordenados);
		
		if (req.getSession().getAttribute("captura") == null){
			req.getSession().setAttribute("alerta", alerta);
		} else {
			req.getSession().setAttribute("captura", null);
		}
		
		resp.sendRedirect("/pages/index.jsp");
	}
}
