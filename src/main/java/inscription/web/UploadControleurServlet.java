	package inscription.web;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import inscription.modele.BasicCSVReader;
import inscription.modele.FacturePdf;
import inscription.modele.FactureService;
import inscription.modele.Client;
import inscription.modele.InscriptionInvalideException;



@WebServlet(name = "exo", urlPatterns = { "/exo" })
@MultipartConfig
public class UploadControleurServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;

	 @EJB
	 private FactureService factureService;

	
	 
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		RequestDispatcher rd = getServletContext().getRequestDispatcher("/WEB-INF/jsp/upload.jsp");
		rd.forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		final Part part = req.getPart("file");
		final PrintWriter lWriter = resp.getWriter();

		// Try-with-resources statement
		try (OutputStream lOutputStream = new FileOutputStream(new File("uploadfacture" + part));
				InputStream lInputStream = part.getInputStream()) {
			BasicCSVReader basicCSVReader = new BasicCSVReader();
			Client client = basicCSVReader.read(lInputStream);
			factureService.inscrire(client);
			FacturePdf facturePdf = new FacturePdf();
			//facturePdf.create(lInputStream);
			
			
			// List<Facture> factures = openCSVReader.read(lInputStream);
			// int lRead = 0;
			// final byte[] lBytes = new byte[1024];
			// while ((lRead = lInputStream.read(lBytes)) != -1) {
			// lOutputStream.write(lBytes, 0, lRead);
			//
			//
			// }
			// lWriter.println("File has been successfully uploaded.");
		} catch (FileNotFoundException e) {
			lWriter.println("An error occurred while trying to upload file.");
		} catch (InscriptionInvalideException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (lWriter != null) {
			lWriter.close();
		}

	}

}

