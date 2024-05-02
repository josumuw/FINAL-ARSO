package estaciones.dto;

import estaciones.modelo.EstadoBicicleta;

public class BicicletaDTO {

	private String modelo;
	private EstadoBicicleta estado;

	public BicicletaDTO(String modelo, EstadoBicicleta estado) {
		super();
		this.modelo = modelo;
		this.estado = estado;
	}

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public EstadoBicicleta getEstado() {
		return estado;
	}

	public void setEstado(EstadoBicicleta estado) {
		this.estado = estado;
	}

}
