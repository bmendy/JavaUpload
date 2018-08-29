package inscription.web;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import javax.sql.DataSource;

import inscription.modele.BasicCSVReader;
import inscription.modele.Facture;
import inscription.modele.InscriptionInvalideException;



@WebServlet(name = "UploadControleurServlet", urlPatterns = { "/chargementcsv" })
@MultipartConfig
public class UploadControleurServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;

	 @EJB
	 private BasicCSVReader basicCSVReader;

	 
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		RequestDispatcher rd = getServletContext().getRequestDispatcher("/WEB-INF/jsp/upload.jsp");
		rd.forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		// Create path components to save the file
		final String path = req.getParameter("destination");
		final Part filePart = req.getPart("file");
		String fileName = getFileName(filePart);


		// Try-with-resources statement
		try (OutputStream out = new FileOutputStream(new File(path + File.separator + fileName));
				InputStream filecontent = filePart.getInputStream();) {

			int lRead = 0;
			final byte[] lBytes = new byte[1024];

			while ((lRead = filecontent.read(lBytes)) != -1) {
				out.write(lBytes, 0, lRead);
			}

		} catch (FileNotFoundException e) {
			
			String failed = "Une erreur est survenue pendant l'enregistrement du fichier.\n" + e.getMessage();
			req.setAttribute("message2", failed);
			RequestDispatcher rd = getServletContext().getRequestDispatcher("/WEB-INF/jsp/index.jsp");
			rd.forward(req, resp);
			
		} catch (IOException e) {
			e.getMessage();
		}
		
		try (InputStream filecontent = new FileInputStream(new File(path + File.separator + fileName));) {
			Facture facture = basicCSVReader.read(filecontent);
			Date date = facture.getFaDate();
			int numFac = facture.getFaNumero();
			String succes = "Le fichier CSV a été correctement sauvegardé le "+ date +" , le numero de facture est " + numFac ; 

			req.setAttribute("message", succes);
			RequestDispatcher rd = getServletContext().getRequestDispatcher("/WEB-INF/jsp/index.jsp");
			rd.forward(req, resp);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	/*on récupère l'entête content-disposition pour vérifier qu'il s'agit d'un fichier grâce au paramètre filename
	 * s'il est bien présent on récupère son nom, sinon la méthode retourne null
	*/
	private String getFileName(Part part) {
		for (String lContent : part.getHeader("content-disposition")
				.split(";")) {
			if (lContent.trim().startsWith("filename")) {
				return lContent.substring(lContent.indexOf('=') + 1).trim().replace("\"", "");
			}
		}
		return null;
	}


}

