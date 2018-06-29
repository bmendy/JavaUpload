package inscription.modele;

import java.sql.Date;

public class Facture {

	private Integer faNumero;
	private String faCommentaire;
	private Date faDate;
	private int cliId;
	
	
	public Facture(String faCommentaire, Date faDate) {
		this.faCommentaire = faCommentaire;
		this.faDate = faDate;
	}
	
	public Integer getFaNumero() {
		return faNumero;
	}
	public void setFaNumero(Integer faNumero) {
		this.faNumero = faNumero;
	}
	public String getFaCommentaire() {
		return faCommentaire;
	}
	public void setFaCommentaire(String faCommentaire) {
		this.faCommentaire = faCommentaire;
	}
	public Date getFaDate() {
		return faDate;
	}
	public void setFaDate(Date faDate) {
		this.faDate = faDate;
	}
	public int getCliId() {
		return cliId;
	}
	public void setCliId(int cliId) {
		this.cliId = cliId;
	}
}
