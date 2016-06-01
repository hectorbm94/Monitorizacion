package es.upm.monitorizacion.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.*;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreInputStream;
//import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;

import es.upm.monitorizacion.dao.DispositivosDAO;
import es.upm.monitorizacion.dao.DispositivosDAOImpl;
import es.upm.monitorizacion.model.Dispositivos;

@SuppressWarnings("serial")
public class CapturaServlet extends HttpServlet {
	
	//private BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();

	public void doPost(HttpServletRequest req, HttpServletResponse resp)  throws IOException, ServletException {
		
		String alerta = null;
		DispositivosDAO dao = DispositivosDAOImpl.getInstance();
		Map<String, List<BlobKey>> blobs = BlobstoreServiceFactory.getBlobstoreService().getUploads(req);
		List<BlobKey> blobKeys = blobs.get("txt");
		if (blobKeys == null || blobKeys.isEmpty() || blobKeys.get(0) == null) {
			//resp.getWriter().println("err");
			alerta = "Ha ocurrido un problema con el fichero";
		} else {
			
			alerta = "Fichero subido con éxito";
			BlobKey blobKey = new BlobKey(blobKeys.get(0).getKeyString());
			BlobstoreInputStream is =new BlobstoreInputStream(blobKey);  

			BufferedReader reader = new BufferedReader(new InputStreamReader(is));
			String line = null;
			PrintWriter s = resp.getWriter();
			StringBuilder a = new StringBuilder();
			String log = null;
			while((line = reader.readLine()) != null) {
				a.append(line);
				String[] disp = line.split(" ");
				//System.out.println(Arrays.toString(disp));
				String[] systime = disp[0].split("=");
				//System.out.println(systime[1]);
				String[] cha = disp[1].split("=");
				String[] LAP = disp[2].split("=");
				String[] senal = disp[6].split("=");
				String[] ruido = disp[7].split("=");
				String[] SNR = disp[8].split("=");
				
				Dispositivos dispositivo = dao.create(Long.parseLong(systime[1]), Integer.parseInt(cha[1]), LAP[1], Integer.parseInt(senal[1]), Integer.parseInt(ruido[1]), Integer.parseInt(SNR[1]));
				System.out.println(dispositivo.toString());
				a.append("/");
			}
			reader.close();
			log = a.toString();
			//s.println(log);
		}
		
		req.getSession().setAttribute("alerta", alerta);
		req.getSession().setAttribute("captura", "true");
		resp.sendRedirect("/monitorizacion");
	}
}
