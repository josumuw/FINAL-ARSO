package estaciones.dto;

public class BajaBicicletaDTO {

	private String motivoBaja;

	public BajaBicicletaDTO(String motivoBaja) {
		super();
		this.motivoBaja = motivoBaja;
	}
	
	public BajaBicicletaDTO() {
		
	}

	public String getMotivoBaja() {
		return motivoBaja;
	}

	public void setMotivoBaja(String motivoBaja) {
		this.motivoBaja = motivoBaja;
	}

}
