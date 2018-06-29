package inscription.modele;

public class Tva {
	
	private Integer tvaId;
	private Float tvaTaux;
	
	public Tva(Integer tvaId, Float tvaTaux) {
		this.tvaId = tvaId;
		this.tvaTaux = tvaTaux;
	}
	
	
	public Integer getTvaId() {
		return tvaId;
	}
	public void setTvaId(Integer tvaId) {
		this.tvaId = tvaId;
	}
	public Float getTvaTaux() {
		return tvaTaux;
	}
	public void setTvaTaux(Float tvaTaux) {
		this.tvaTaux = tvaTaux;
	}
	
	
	
}
