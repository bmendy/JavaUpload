package inscription.modele;

public class DetailFacture {

	private Integer lfaId;
	private Integer lfaQuantite;
//	private Float lfaPrixUnitHt;
//	private Float lfaMontantHt;
//	private Float lfaMontantTTC;
	private Integer faNumero;
	private Integer pdtId;
	private Integer tvaId;
	

	public DetailFacture (Integer lfaQuantite, Integer pdtId) {
		this.lfaQuantite = lfaQuantite;
//		this.lfaPrixUnitHt = lfaPrixUnitHt;
//		this.lfaMontantTTC = lfaMontantTTC;
		this.pdtId = pdtId;
	}
	
	public DetailFacture (Integer lfaQuantite, Integer faNumero, Integer pdtId, Integer tvaId ) {
		this.lfaQuantite = lfaQuantite;
//		this.lfaPrixUnitHt = lfaPrixUnitHt;
//		this.lfaMontantTTC = lfaMontantTTC;
		this.faNumero = faNumero;
		this.pdtId = pdtId;
		this.tvaId = tvaId;
	}
	
	public Integer getLfaId() {
		return lfaId;
	}

	public void setLfaId(Integer lfaId) {
		this.lfaId = lfaId;
	}

	public Integer getLfaQuantite() {
		return lfaQuantite;
	}

	public void setLfaQuantite(Integer lfaQuantite) {
		this.lfaQuantite = lfaQuantite;
	}

//	public Float getLfaPrixUnitHt() {
//		return lfaPrixUnitHt;
//	}

//	public void setLfaPrixUnitHt(Float lfaPrixUnitHt) {
//		this.lfaPrixUnitHt = lfaPrixUnitHt;
//	}

//	public Float getLfaMontantHt() {
//		return lfaMontantHt;
//	}
//
//	public void setLfaMontantHt(Float lfaMontantHt) {
//		this.lfaMontantHt = lfaMontantHt;
//	}

//	public Float getLfaMontantTTC() {
//		return lfaMontantTTC;
//	}
//
//	public void setLfaMontantTTC(Float lfaMontantTTC) {
//		this.lfaMontantTTC = lfaMontantTTC;
//	}

	public Integer getFaNumero() {
		return faNumero;
	}

	public void setFaNumero(Integer faNumero) {
		this.faNumero = faNumero;
	}

	public Integer getPdtId() {
		return pdtId;
	}

	public void setPdtId(Integer pdtId) {
		this.pdtId = pdtId;
	}

	public Integer getTvaId() {
		return tvaId;
	}

	public void setTvaId(Integer tvaId) {
		this.tvaId = tvaId;
	}
	
	
	
	
	
	
	
	
}
