package inscription.modele;

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

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

@Stateless
public class BasicCSVReader {

	// public static void main(String[] args) throws IOException {
	// try (
	// Reader reader = Files.newBufferedReader(Paths.get(SAMPLE_CSV_FILE_PATH));
	// CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT);
	// ) {
	// for (CSVRecord csvRecord : csvParser) {
	// // Accessing Values by Column Index
	// String nom = csvRecord.get(0);
	// String prenom = csvRecord.get(1);
	// String adresse1 = csvRecord.get(2);
	// String adresse2 = csvRecord.get(3);
	// String CP = csvRecord.get(3);
	// String Ville = csvRecord.get(4);
	//
	// System.out.println("Record No - " + csvRecord.getRecordNumber());
	// System.out.println("---------------");
	// System.out.println("Nom : " + nom);
	// System.out.println("Prenom : " + prenom);
	// System.out.println("adresse1 : " + adresse1);
	// System.out.println("adresse2 : " + adresse2);
	// System.out.println("CP : " + CP);
	// System.out.println("Ville : " + Ville);
	// System.out.println("---------------\n\n");
	// }
	// }
	// }
	
	@EJB
	private FactureService factureService;

	




	public void read(InputStream lInputStream) throws IOException, InscriptionInvalideException, SQLException {

		try (

				Reader reader = new InputStreamReader(lInputStream, "UTF-8");
				CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withDelimiter(';'));
			) {
//			ArrayList<Client>tableauClient = new ArrayList<Client>();
//			ArrayList<Produit>tableauProduit = new ArrayList<Produit>();
			Client retourClient = null;
			Facture retourFacture;
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
				    retourClient = factureService.inscrire(client); 
					break;
				case "PDT":
					String refPdt = csvRecord.get(1);
					String designationPdt = csvRecord.get(2);
					Float prixPdt = Float.parseFloat(csvRecord.get(4));
					Produit pdt = new Produit(designationPdt, prixPdt, refPdt);
					Produit retourProduit = factureService.destocker(pdt); 
					break;
				case "COM":
					String comment = csvRecord.get(1); 
					Date dateSQL = new java.sql.Date(new java.util.Date().getTime()); 
					Facture facture = new Facture(comment,dateSQL);
					facture.setCliId(retourClient.getId());
					retourFacture = factureService.commenter(facture);
					break;
				default:
					
				}
				
				
				}
				
			
			}

	}

	
	
	
	// public ArrayList<Produit> readProduit(InputStream lInputStream) throws IOException {

//		try (
//
//				Reader reader = new InputStreamReader(lInputStream, "UTF-8");
//				CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withDelimiter(';'));) {
//			ArrayList<Produit> tableauProduit = new ArrayList<Produit>();
//			for (CSVRecord csvRecord : csvParser) {
//				String nomcol = csvRecord.get(0);
//				switch (nomcol) {
//				case "PDT":
//					String refPdt = csvRecord.get(1);
//					String designationPdt = csvRecord.get(2);
//					Float prixPdt = Float.parseFloat(csvRecord.get(4));
//					Produit pdt = new Produit(designationPdt, prixPdt, refPdt);
//					tableauProduit.add(pdt);
//				default:
//					tableauProduit = null;
//
//				}
//			}
//			return null;
//		}
//
//	}
	
}


