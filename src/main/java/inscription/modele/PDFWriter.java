package inscription.modele;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.sql.DataSource;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.api.Indentable;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;



@Stateless
public class PDFWriter {

	@Resource(name = "csvintopdf")
	private DataSource dataSource;
	
	

	// SELECT * FROM client c inner join `facture` f on f.cli_id=c.cli_id inner join detailfacture d ON d.fa_numero = f.fa_numero inner join tva t on t.tva_id = d.tva_id inner join produit p on p.pdt_id = d.pdt_id WHERE f.fa_numero = 35
	public  void generatePdf(String path, String filename, int idFacture ) throws FileNotFoundException, SQLException
	  {
	    Document document = new Document();
	    Float totalHT = (float) 0;
	    Float totalTTC = (float) 0;
	    
	    try (Connection c = dataSource.getConnection()) {
	    ResultSet resultat = null;
	    String sqlStatement = "SELECT * FROM client c inner join facture f on f.cli_id=c.cli_id inner join detailfacture d ON d.fa_numero = f.fa_numero inner join tva t on t.tva_id = d.tva_id inner join produit p on p.pdt_id = d.pdt_id WHERE f.fa_numero = ?;";
	    try (PreparedStatement pstmt = c.prepareStatement(sqlStatement)) {
	    pstmt.setInt(1, idFacture);
	    resultat = pstmt.executeQuery();
	    resultat.next();
	   
	    
	    
	    String NomClient = resultat.getString("cli_Nom");
	    String PrenomClient = resultat.getString("cli_Prenom");
	    String AdresseClient = resultat.getString("cli_adresse");
	    String CodePostClient = resultat.getString("cli_cp");
	    String VilleClient = resultat.getString("cli_ville");
	    
	    Date date = resultat.getDate("fa_date");
	    Float TVA = resultat.getFloat("tva_taux");
	    
	    String Commentaire = resultat.getString("fa_commentaire");
	    
	    
	    PdfWriter.getInstance(document, new FileOutputStream(path+filename));
	    document.open();
	    
	   // Font f= FontFactory.getFont(FontFactory.TIMES_ROMAN,12,BaseColor.BLACK);
	    
	    Paragraph paragraph = new Paragraph("Facture n°: " + idFacture);
	    paragraph.setIndentationLeft(400);
	    //paragraph.setSpacingAfter(20);			
		//paragraph.setSpacingAfter(30);
		document.add(paragraph);
		
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Paragraph paragraph2 = new Paragraph("Le " + dateFormat.format(date));
		paragraph2.setIndentationLeft(400);
	    document.add(paragraph2);
	    
	    document.add(new Paragraph(NomClient + " " + PrenomClient));
	    document.add(new Paragraph(AdresseClient));
	    document.add(new Paragraph(CodePostClient + " " + VilleClient));
	    
	    PdfPTable table = new PdfPTable(6);
	    table.setSpacingBefore(50);
//		PdfPCell defaultcell = table.getDefaultCell();
//		defaultcell.setFixedHeight(35f);
//		defaultcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
//		defaultcell.setHorizontalAlignment(Element.ALIGN_CENTER);
	   
		table.addCell(new Paragraph("Référence"));
		table.addCell(new Paragraph("Désignation"));
		table.addCell(new Paragraph("Quantité"));
		table.addCell(new Paragraph("Prix unitaire HT"));
		table.addCell(new Paragraph("Montant HT"));
		table.addCell(new Paragraph("Montant TTC"));
		
		document.add(table);
		resultat.beforeFirst();
		while (resultat.next()) {
			
			String PdtRef = resultat.getString("pdt_reference");
		    String PdtDesignation = resultat.getString("pdt_designation");
		    Float PdtPrix = resultat.getFloat("prix");
		    Integer Quantite =  resultat.getInt("lfa_quantite");
		    Float MontantHTPdt = calculMontantHTPdt(PdtPrix, Quantite);
		    Float MontantTTCPdt = calculMontantTTCPdt(MontantHTPdt, TVA);
		PdfPTable table2 = new PdfPTable(6);
		table2.addCell(PdtRef);
		table2.addCell(PdtDesignation);
		table2.addCell(String.valueOf(Quantite));
		table2.addCell(String.valueOf(PdtPrix));
		table2.addCell(String.valueOf(MontantHTPdt));
		table2.addCell(String.valueOf(MontantTTCPdt));
		document.add(table2);
		totalHT += MontantHTPdt;
		totalTTC += MontantTTCPdt;
	    } ;
	    
	    Paragraph paragraph3 = new Paragraph("Commentaires: " + Commentaire);
	    paragraph3.setIndentationLeft(225);
	    paragraph.setSpacingBefore(50);			
		paragraph.setSpacingAfter(30);
		document.add(paragraph3);
		
		Paragraph paragraph4 = new Paragraph("Taux TVA: " + TVA);
		paragraph4.setSpacingBefore(20);			
		paragraph4.setSpacingAfter(30);
		paragraph4.setIndentationLeft(400);
		document.add(paragraph4);
		
		PdfPTable table3 = new PdfPTable(1);
	    table3.setSpacingBefore(30);
//		PdfPCell defaultcell = table.getDefaultCell();
//		defaultcell.setFixedHeight(35f);
//		defaultcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
//		defaultcell.setHorizontalAlignment(Element.ALIGN_CENTER);
		table3.addCell(new Paragraph("Montant TOTAL HT"));
		table3.addCell(String.valueOf(totalHT));
		table3.addCell(new Paragraph("Montant TOTAL TTC"));
		table3.addCell(String.valueOf(totalTTC));
		document.add(table3);
	    
		resultat.close();
	      document.close();
	    } 
	    } catch (DocumentException de) {
	      de.fillInStackTrace();
	    } catch (IOException de) {
	     // ioe.printStackTrace();
	    } finally {
	    	}
	    }
	   

	  
	 


     public static Float calculMontantHTPdt(Float PdtPrix, int Quantite) {
    	 Float Total = (float) 0;
    	 Total = PdtPrix * Quantite;
		return Total; 	 
     }

     public static Float calculMontantTTCPdt(Float MontantHTPdt, Float TVA) {
    	 Float Total = (float) 0;
    	 Total = MontantHTPdt *(TVA + 1);
		return Total; 	 
     }

	

	
}	

	



