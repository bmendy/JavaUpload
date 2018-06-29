package inscription.modele;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

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

	public Client read(InputStream lInputStream) throws IOException {

		try (

				Reader reader = new InputStreamReader(lInputStream, "UTF-8");
				CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withDelimiter(';'));) {

			for (CSVRecord csvRecord : csvParser) {

				// Accessing Values by Column Index
				String nomcol = csvRecord.get(0);
				String nom = csvRecord.get(1);
				String prenom = csvRecord.get(2);
				String adresse = csvRecord.get(3);
				String CP = csvRecord.get(4);
				String Ville = csvRecord.get(5);

				return new Client(nom, prenom, adresse, CP, Ville);
			}
			return null;
		}

	}

}
