package inscription.modele;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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
			try(PreparedStatement pstmt = c.prepareStatement(sqlStatement, Statement.RETURN_GENERATED_KEYS)) {
				
				pstmt.setString(1, client.getNom());
				pstmt.setString(2, client.getPrenom());
				pstmt.setString(3, client.getAdresse());
				pstmt.setString(4, client.getCp());
				pstmt.setString(5, client.getVille());
			
				pstmt.executeUpdate();
				ResultSet result = pstmt.getGeneratedKeys();
				result.next();
				int ClientId = result.getInt(1);
				client.setId(ClientId);			
			} 
		} 
		return client;
	}
	
	public Produit destocker(Produit produit) throws InscriptionInvalideException, SQLException {
		InscriptionInvalideException ex = new InscriptionInvalideException();
		

			try(Connection c = dataSource.getConnection()) {
				String sqlStatement = "insert into produit (pdt_designation, prix, pdt_reference) values(?, ?, ?)";
				try(PreparedStatement pstmt = c.prepareStatement(sqlStatement)) {
					pstmt.setString(1, produit.getPdtDesignation());
					pstmt.setFloat(2, produit.getPdtPrix());
					pstmt.setString(3, produit.getPdtReference());
				
					pstmt.executeUpdate();
					return produit;
				}
			}
		}
	
	
	public Facture commenter(Facture facture) throws InscriptionInvalideException, SQLException {
		InscriptionInvalideException ex = new InscriptionInvalideException();
		

			try(Connection c = dataSource.getConnection()) {
				String sqlStatement = "insert into facture (fa_commentaire, fa_date, cli_id) values(?, ?, ?)";
				try(PreparedStatement pstmt = c.prepareStatement(sqlStatement)) {
					pstmt.setString(1, facture.getFaCommentaire());
					pstmt.setDate(2, facture.getFaDate());
					pstmt.setInt(3, facture.getCliId());
					pstmt.executeUpdate();
					return facture;
				}
			}
		}
	
}
