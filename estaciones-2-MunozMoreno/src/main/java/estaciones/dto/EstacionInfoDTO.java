package estaciones.dto;

public class EstacionInfoDTO {

	private String nombre;
	private int numPuestos;
	private String dirPostal;

	public EstacionInfoDTO(String nombre, int numPuestos, String dirPostal) {
		this.nombre = nombre;
		this.numPuestos = numPuestos;
		this.dirPostal = dirPostal;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getNumPuestos() {
		return numPuestos;
	}

	public void setNumPuestos(int numPuestos) {
		this.numPuestos = numPuestos;
	}

	public String getDirPostal() {
		return dirPostal;
	}

	public void setDirPostal(String dirPostal) {
		this.dirPostal = dirPostal;
	}

	@Override
	public String toString() {
		return "EstacionInfo [nombre=" + nombre + ", numPuestos=" + numPuestos + ", dirPostal=" + dirPostal + "]";
	}

}
