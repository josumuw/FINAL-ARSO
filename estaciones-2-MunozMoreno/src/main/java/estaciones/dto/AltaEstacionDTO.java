package estaciones.dto;

public class AltaEstacionDTO {

	private String nombre;
	private int numPuestos;
	private String dirPostal;
	private double latitud;
	private double longitud;

	public AltaEstacionDTO(String nombre, int numPuestos, String dirPostal, double latitud, double longitud) {
		this.nombre = nombre;
		this.numPuestos = numPuestos;
		this.dirPostal = dirPostal;
		this.latitud = latitud;
		this.longitud = longitud;
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

	public double getLatitud() {
		return latitud;
	}

	public void setLatitud(double latitud) {
		this.latitud = latitud;
	}

	public double getLongitud() {
		return longitud;
	}

	public void setLongitud(double longitud) {
		this.longitud = longitud;
	}
	// Transformadores
}
