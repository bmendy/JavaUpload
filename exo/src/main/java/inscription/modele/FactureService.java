package inscription.modele;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.sql.DataSource;

@Stateless
public class FactureService {
	
	@Resource(name = "csvintopdf")
	private DataSource dataSource;
	
	
//	public FactureService(DataSource dataSource) {
//		this.dataSource = dataSource;
//	}
	
//	public FactureService() {};

	public Inscription inscrire(String nom, String prenom, String adresse1,  String adresse2, String ville, String cp) throws InscriptionInvalideException, SQLException {
	InscriptionInvalideException ex = new InscriptionInvalideException();
	
	Inscription client = new Inscription(nom, prenom, adresse1,  adresse2,  ville, cp);
		try(Connection c = dataSource.getConnection()) {
			String sqlStatement = "insert into client (cli_Nom, cli_Prenom, cli_adresse1,  cli_adresse2,  cli_ville, cli_cp) values(?, ?, ?,?,?,?)";
			try(PreparedStatement pstmt = c.prepareStatement(sqlStatement)) {
				pstmt.setString(1, client.getNom());
				pstmt.setString(2, client.getPrenom());
				pstmt.setString(3, client.getAdresse1());
				pstmt.setString(4, client.getAdresse2());
				pstmt.setString(5, client.getVille());
				pstmt.setString(6, client.getCp());
				pstmt.executeUpdate();
				return client;
			}
		}
	}
	
}
