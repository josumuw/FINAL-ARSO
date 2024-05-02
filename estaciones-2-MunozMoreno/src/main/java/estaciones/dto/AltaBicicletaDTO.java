package estaciones.dto;

public class AltaBicicletaDTO {

	private String modelo;

	public AltaBicicletaDTO(String modelo) {
		this.modelo = modelo;
	}
	
	public AltaBicicletaDTO() {
		
	}

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}
}
