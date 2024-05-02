package dto;

public class BicicletaDTO {
    private String id;
    private String modelo;
    private String estacion;
    
	public BicicletaDTO(String id, String modelo, String estacion) {
		super();
		this.id = id;
		this.modelo = modelo;
		this.estacion = estacion;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getModelo() {
		return modelo;
	}
	public void setModelo(String modelo) {
		this.modelo = modelo;
	}
	public String getEstacion() {
		return estacion;
	}
	public void setEstacion(String estacion) {
		this.estacion = estacion;
	}
	@Override
	public String toString() {
		return "BicicletaDTO [id=" + id + ", modelo=" + modelo + ", estacion=" + estacion + "]";
	}
	
	
}