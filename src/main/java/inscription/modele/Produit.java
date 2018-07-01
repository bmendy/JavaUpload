package inscription.modele;

import java.sql.Date;

public class Produit {

	private Integer pdtId;
	private String  pdtDesignation;
	private Float pdtPrix;
	private String pdtReference;
	
	public Produit( String  pdtDesignation, Float pdtPrix, String pdtReference ) {
		this.pdtDesignation = pdtDesignation;
		this.pdtPrix = pdtPrix;
		this.pdtReference = pdtReference;
	}
	
	public Integer getPdtId() {
		return pdtId;
	}
	public void setPdtId(Integer pdtId) {
		this.pdtId = pdtId;
	}
	public String getPdtDesignation() {
		return pdtDesignation;
	}
	public void setPdtDesignation(String pdtDesignation) {
		this.pdtDesignation = pdtDesignation;
	}
	public Float getPdtPrix() {
		return pdtPrix;
	}
	public void setPdtPrix(Float pdtPrix) {
		this.pdtPrix = pdtPrix;
	}
	public String getPdtReference() {
		return pdtReference;
	}
	public void setPdtReference(String pdtReference) {
		this.pdtReference = pdtReference;
	}
	
	
	
	
	
}
