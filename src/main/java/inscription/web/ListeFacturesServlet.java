package inscription.web;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import inscription.modele.FactureService;
import inscription.modele.InscriptionInvalideException;

@WebServlet(name = "ListeFacturesServlet", urlPatterns = { "/listeFacture" })
public class ListeFacturesServlet extends HttpServlet{
	
	@EJB
	private FactureService factureService;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException {

		try {
			ArrayList listeFact = factureService.lister();
			req.setAttribute("factures", listeFact);
			RequestDispatcher rd = getServletContext().getRequestDispatcher("/WEB-INF/jsp/listeFactures.jsp");
			rd.forward(req, resp);
			
		} catch (SQLException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	 
	 
	 @Override
		protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
		 
	 }
}
