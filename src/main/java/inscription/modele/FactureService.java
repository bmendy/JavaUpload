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

	public Client inscrire(Client client) throws InscriptionInvalideException, SQLException {
	InscriptionInvalideException ex = new InscriptionInvalideException();
	

		try(Connection c = dataSource.getConnection()) {
			String sqlStatement = "insert into client (cli_Nom, cli_Prenom, cli_adresse, cli_cp, cli_ville) values(?, ?, ?,?,?)";
			try(PreparedStatement pstmt = c.prepareStatement(sqlStatement)) {
				pstmt.setString(1, client.getNom());
				pstmt.setString(2, client.getPrenom());
				pstmt.setString(3, client.getAdresse());
				pstmt.setString(4, client.getCp());
				pstmt.setString(5, client.getVille());
			
				pstmt.executeUpdate();
				return client;
			}
		}
	}
	
}
