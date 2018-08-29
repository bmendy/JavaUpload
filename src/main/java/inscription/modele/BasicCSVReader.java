package inscription.modele;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

@Stateless
public class BasicCSVReader {


	@EJB
	private FactureService factureService;

	
	


	public Facture read(InputStream lInputStream) throws IOException, SQLException {
		Facture retourFacture = null;
		try (
				
				Reader reader = new InputStreamReader(lInputStream, "UTF-8");
				CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withDelimiter(';'));) {
			ArrayList<Facture> tableauFacture = new ArrayList<Facture>();
			ArrayList<Produit> tableauProduits = new ArrayList<Produit>();
			ArrayList<DetailFacture> tableaudetailFacture = new ArrayList<DetailFacture>();
			int clientId = 0;
			
			Produit retourProduit = null;
			DetailFacture retourDetailFacture = null;
			Tva retourTva = null;
			Integer quantite = null;
			for (CSVRecord csvRecord : csvParser) {
				String nomcol = csvRecord.get(0);

				
				switch (nomcol) {
				case "CLI":
					// Accessing Values by Column Index
					String nom = csvRecord.get(1);
					String prenom = csvRecord.get(2);
					String adresse = csvRecord.get(3);
					String CP = csvRecord.get(4);
					String Ville = csvRecord.get(5);
					Client client = new Client(nom, prenom, adresse, CP, Ville);
					clientId =  factureService.inscrire(client);
					break;
				case "PDT":
					String refPdt = csvRecord.get(1);
					String designationPdt = csvRecord.get(2);
					Float prixPdt = Float.parseFloat(csvRecord.get(4));
					Produit pdt = new Produit(designationPdt, prixPdt, refPdt);
					retourProduit = factureService.destocker(pdt);
					quantite = Integer.parseInt(csvRecord.get(3));
					DetailFacture detailFacture = new DetailFacture(quantite,retourProduit.getPdtId());
					tableaudetailFacture.add(detailFacture);
					tableauProduits.add(retourProduit);
					break;
				case "COM":
					String comment = csvRecord.get(1);
					Date dateSQL = new java.sql.Date(new java.util.Date().getTime());
					Facture facture = new Facture(comment, dateSQL);
					facture.setCliId(clientId);
					retourFacture = factureService.commenter(facture);
					tableauFacture.add(retourFacture);
					break;
				case "TVA":
					Float tvaTaux = Float.parseFloat(csvRecord.get(1));
					Tva tva = new Tva(tvaTaux);
					retourTva = factureService.tvaEnregistrer(tva);
					break;
				default:

				}
				
			}
			for (DetailFacture lf : tableaudetailFacture) {
				lf.setFaNumero(retourFacture.getFaNumero());
				lf.setTvaId(retourTva.getTvaId());
				retourDetailFacture = factureService.ligneFactureEnregistrer(lf);
			}
			//Facture factureFinale = new Facture(retourFacture.getFaCommentaire(),retourFacture.getFaDate());
			
		} catch (FileNotFoundException e) {
			e.getMessage();	
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return retourFacture;
	}
	

	
}	

	



