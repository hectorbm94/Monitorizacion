package es.upm.monitorizacion.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.*;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreInputStream;
//import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;

@SuppressWarnings("serial")
public class CapturaServlet extends HttpServlet {
	
	//private BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();

	public void doPost(HttpServletRequest req, HttpServletResponse resp)  throws IOException, ServletException {

		Map<String, List<BlobKey>> blobs = BlobstoreServiceFactory.getBlobstoreService().getUploads(req);
		List<BlobKey> blobKeys = blobs.get("txt");
		if (blobKeys == null || blobKeys.isEmpty() || blobKeys.get(0) == null) {
			resp.getWriter().println("err");
		} else {

			BlobKey blobKey = new BlobKey(blobKeys.get(0).getKeyString());
			BlobstoreInputStream is =new BlobstoreInputStream(blobKey);  

			BufferedReader reader = new BufferedReader(new InputStreamReader(is));
			String line = null;
			PrintWriter s = resp.getWriter();
			StringBuilder a = new StringBuilder();
			String cert = null;
			while((line = reader.readLine()) != null) {
				a.append(line);
			}
			reader.close();
			cert = a.toString();
			s.println(cert);

			resp.sendRedirect("/pages/index.jsp");
		}
	}
}
