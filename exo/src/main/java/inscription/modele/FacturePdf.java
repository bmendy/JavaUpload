package inscription.modele;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class FacturePdf {

	public FacturePdf() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {

        Document document = new Document();

        try {
            PdfWriter.getInstance(document,
                new FileOutputStream("C:\\Users\\benedicte.mendy\\Desktop\\facture6.pdf"));

            document.open();
            document.add(new Paragraph("Mendy Bénédicte"));
            document.add(new Phrase("31 rue jacques gabriel"));
            document.add(new Paragraph("33160"));
            
            PdfPTable table = new PdfPTable(4); 

            PdfPCell cell1 = new PdfPCell(new Paragraph("Référence"));
            PdfPCell cell2 = new PdfPCell(new Paragraph("Désignation"));
            PdfPCell cell3 = new PdfPCell(new Paragraph("Quantité"));
            PdfPCell cell4 = new PdfPCell(new Paragraph("Prix Unitaire HT"));

            table.addCell(cell1);
            table.addCell(cell2);
            table.addCell(cell3);
            table.addCell(cell4);

            document.add(table);
            table.setSpacingAfter(30f);
            document.close(); // no need to close PDFwriter?

        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }
	
	public void create(InputStream lInputStream) throws IOException {}
}
