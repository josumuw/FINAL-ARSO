package estaciones.modelo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "estaciones")
public class Estacion {

	@Id
	private String id;

	private String nombre;
	private int numPuestos;
	private String dirPostal;
	private double latitud;
	private double longitud;

	public Estacion(String nombre, int numPuestos, String dirPostal, double latitud, double longitud) {
		this.nombre = nombre;
		this.numPuestos = numPuestos;
		this.dirPostal = dirPostal;
		this.latitud = latitud;
		this.longitud = longitud;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public boolean isEstacionable() {
		return (numPuestos > 0);
	}

	public boolean estacionarBicicleta() {
		if (!this.isEstacionable())
			return false;
		numPuestos--;
		return true;
	}
	
	public boolean darBajaBicicleta() {
		numPuestos++;
		return true;
	}

	@Override
	public String toString() {
		return "Estacion [id=" + id + ", nombre=" + nombre + ", numPuestos=" + numPuestos + ", dirPostal=" + dirPostal
				+ ", latitud=" + latitud + ", longitud=" + longitud + "]";
	}
}
