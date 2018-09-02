package inscription.modele;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.sql.DataSource;

@Stateless
public class FactureService {
	
	@Resource(name = "csvintopdf")
	private DataSource dataSource;
	
	
/*public FactureService(DataSource dataSource) {
	this.dataSource = dataSource;
}
	
	public FactureService() {};*/

	public int inscrire(Client client) throws SQLException {

		int ClientId = 0;

		try (Connection c = dataSource.getConnection()) {
			String sqlStatement = "insert into client (cli_Nom, cli_Prenom, cli_adresse, cli_cp, cli_ville) values(?, ?, ?,?,?)";
			try (PreparedStatement pstmt = c.prepareStatement(sqlStatement, Statement.RETURN_GENERATED_KEYS)) {

				pstmt.setString(1, client.getNom());
				pstmt.setString(2, client.getPrenom());
				pstmt.setString(3, client.getAdresse());
				pstmt.setString(4, client.getCp());
				pstmt.setString(5, client.getVille());

				pstmt.executeUpdate();
				ResultSet result = pstmt.getGeneratedKeys();
				result.next();
				ClientId = result.getInt(1);
				client.setId(ClientId);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return ClientId;
	}
	
	public Produit destocker(Produit produit) throws SQLException {
		
			try(Connection c = dataSource.getConnection()) {
				String sqlStatement = "insert into produit (pdt_designation, prix, pdt_reference) values(?, ?, ?)";
				try(PreparedStatement pstmt = c.prepareStatement(sqlStatement, Statement.RETURN_GENERATED_KEYS)) {
					pstmt.setString(1, produit.getPdtDesignation());
					pstmt.setFloat(2, produit.getPdtPrix());
					pstmt.setString(3, produit.getPdtReference());
				
					pstmt.executeUpdate();
					ResultSet result = pstmt.getGeneratedKeys();
					result.next();
					int PdtId = result.getInt(1);
					produit.setPdtId(PdtId);
				}
			}  catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			return produit;
		}
	
	
	public Facture commenter(Facture facture) throws SQLException {
	
			try(Connection c = dataSource.getConnection()) {
				String sqlStatement = "insert into facture (fa_commentaire, fa_date, cli_id) values(?, ?, ?)";
				try(PreparedStatement pstmt = c.prepareStatement(sqlStatement, Statement.RETURN_GENERATED_KEYS)) {
					pstmt.setString(1, facture.getFaCommentaire());
					pstmt.setDate(2, (java.sql.Date) facture.getFaDate());
					pstmt.setInt(3, facture.getCliId());
					pstmt.executeUpdate();
					ResultSet result = pstmt.getGeneratedKeys();
					result.next();
					int FactureNum = result.getInt(1);
					facture.setFaNumero(FactureNum);
				}
			}  catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			return facture;
		}
	
	
	public DetailFacture ligneFactureEnregistrer(DetailFacture detailFacture) throws SQLException {
	
			try(Connection c = dataSource.getConnection()) {
				String sqlStatement = "insert into detailfacture (lfa_quantite, fa_numero, pdt_id, tva_id) values(?, ?, ?, ?)";
				try(PreparedStatement pstmt = c.prepareStatement(sqlStatement)) {
					pstmt.setInt(1, detailFacture.getLfaQuantite());
					pstmt.setInt(2, detailFacture.getFaNumero());
					pstmt.setInt(3, detailFacture.getPdtId());
					pstmt.setInt(4, detailFacture.getTvaId());
					pstmt.executeUpdate();	
				}
			}  catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			return detailFacture;
		}
	
	public Tva tvaEnregistrer(Tva tva) throws SQLException {

			try(Connection c = dataSource.getConnection()) {
				String sqlStatement = "insert into tva (tva_taux) values(?)";
				try(PreparedStatement pstmt = c.prepareStatement(sqlStatement, Statement.RETURN_GENERATED_KEYS)) {
					pstmt.setFloat(1, tva.getTvaTaux());
					pstmt.executeUpdate();
					ResultSet result = pstmt.getGeneratedKeys();
					result.next();
					int tvaId = result.getInt(1);
					tva.setTvaId(tvaId);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			return tva;
		}
	
	public ArrayList<Facture> lister() throws SQLException {
		ArrayList<Facture> listeFact = new ArrayList();
		ResultSet resultat = null;
		try (Connection c = dataSource.getConnection()) {
			String sqlStatement = "select * from facture";
			try (PreparedStatement pstmt = c.prepareStatement(sqlStatement)) {
				resultat = pstmt.executeQuery();
				while (resultat.next()) {
					int id = resultat.getInt("fa_numero");
					String comment = resultat.getString("fa_commentaire");
					Date date = resultat.getDate("fa_date");
					int clientId = resultat.getInt("cli_id");
					Facture fact = new Facture(comment, date);
					fact.setCliId(clientId);
					fact.setFaNumero(id);
					listeFact.add(fact);
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return listeFact;
	}
}
