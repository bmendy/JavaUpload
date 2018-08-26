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
	    
	    
		final PrintWriter writer = resp.getWriter();
		
		

		// Try-with-resources statement
		try (	OutputStream out = new FileOutputStream(new File( path + File.separator + fileName));
				InputStream filecontent = filePart.getInputStream();) {
			
			
				
				
				int lRead = 0;
				final byte[] lBytes = new byte[1024];
				
				while ((lRead = filecontent.read(lBytes)) != -1) {
					out.write(lBytes, 0, lRead);
					}
				
				 
			} catch (FileNotFoundException e) {
			writer.println("An error occurred while trying to upload file.");
			
		} 
		// si fichier non ecrit on doit sortir de la methode!! 
		
		try (InputStream filecontent = new FileInputStream(new File( path + File.separator + fileName));
				) {
			basicCSVReader.read(filecontent);
			
			
			String succes = "Le fichier CSV a été correctement sauvegardé";
			
			req.setAttribute("message", succes);
			RequestDispatcher rd = getServletContext().getRequestDispatcher("/index.jsp");
			rd.forward(req, resp);
			
			// FacturePdf facturePdf = new FacturePdf();
			//facturePdf.create(lInputStream);
			
			
			// List<Facture> factures = openCSVReader.read(lInputStream);
			
			
		
			// lWriter.println("File has been successfully uploaded.");
		
		
		} catch (FileNotFoundException e) {
			writer.println("An error occurred while trying to upload file.");
		} catch (InscriptionInvalideException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		if (writer != null) {
			writer.close();
		}
	}
	
	

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

