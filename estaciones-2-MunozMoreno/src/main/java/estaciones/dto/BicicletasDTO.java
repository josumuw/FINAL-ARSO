package estaciones.dto;

import java.util.List;

public class BicicletasDTO {

	private List<BicicletaDTO> bicicletas;

	public BicicletasDTO(List<BicicletaDTO> bicicletas) {
		super();
		this.bicicletas = bicicletas;
	}

	public List<BicicletaDTO> getBicicletas() {
		return bicicletas;
	}

	public void setBicicletas(List<BicicletaDTO> bicicletas) {
		this.bicicletas = bicicletas;
	}

}
