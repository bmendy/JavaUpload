	package inscription.web;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
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
import java.sql.SQLException;

import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import inscription.modele.BasicCSVReader;
import inscription.modele.InscriptionInvalideException;
import inscription.modele.PDFWriter;



@WebServlet(name = "PdfControleurServlet", urlPatterns = { "/pdf" })
public class PdfControleurServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;

	 @EJB
	 private PDFWriter genererPDF;

	 public static final String PATH = "C:\\fact\\";
	 
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String paramId = req.getParameter("id");
		int idFacture = Integer.parseInt(paramId);
		
		String nomFac = "facture"+ idFacture+".pdf";
		
		try {
			genererPDF.generatePdf(PATH, nomFac, idFacture);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		File pdf = new File(PATH + nomFac);
		
		resp.setContentType("application/pdf");
		resp.setHeader("Content-Disposition", "inline; filename="+nomFac);
		resp.setContentLength((int) pdf.length());
		req.setAttribute("fichierpdf",pdf);
		
		try {
		BufferedInputStream bis = new BufferedInputStream(new FileInputStream(pdf));
		BufferedOutputStream bos = new BufferedOutputStream(resp.getOutputStream());
		
		byte[] buffer = new byte[2048];
		boolean end = false;
		while(!end) {
			int length = bis.read(buffer);
			if(length == -1) {
				end = true;
			} else {
				bos.write(buffer, 0, length);
			}
		}
		try {
			bis.close();
		} catch (IOException ex)
		{
			ex.printStackTrace();
		}
		
		try {
			bos.flush();
		} catch(IOException ex) {
			ex.printStackTrace();
		}
		
		bos.close();
		} catch (FileNotFoundException e) {
			
		}
	
	}
	
	

}

