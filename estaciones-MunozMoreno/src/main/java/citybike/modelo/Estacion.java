package citybike.modelo;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.bson.BsonType;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.codecs.pojo.annotations.BsonRepresentation;

import com.mongodb.client.model.geojson.Point;

import citybike.servicio.SitioTuristicoResumen;
import repositorio.Identificable;

public class Estacion implements Identificable, Serializable {

	// Atributos

	@BsonId
	@BsonRepresentation(BsonType.OBJECT_ID)
	private String id;
	@BsonProperty(value = "nombre")
	private String nombre;
	@BsonProperty(value = "numPuestos")
	private int numPuestos;
	@BsonProperty(value = "dirPostal")
	private String dirPostal;
	@BsonProperty(value = "location")
	private Point location;
	@BsonProperty(value = "alta")
	private LocalDate alta;
	@BsonProperty(value = "sitiosTuristicos")
	private Set<SitioTuristicoResumen> sitiosTuristicos;
	@BsonProperty(value = "bicletas")
	private List<String> bicicletas;

	// Constructor

	public Estacion() {

	}

	public Estacion(String nombre, int numPuestos, String dirPostal, Point location) {
		this.nombre = nombre;
		this.numPuestos = numPuestos;
		this.dirPostal = dirPostal;
		this.location = location;
		this.alta = LocalDate.now();
		this.sitiosTuristicos = new HashSet<SitioTuristicoResumen>();
		this.bicicletas = new ArrayList<String>();
	}

	// Métodos Set/Get
	// Implements Identificable
	@Override
	public String getId() {
		return this.id;
	}

	@Override
	public void setId(String id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public int getNumPuestos() {
		return numPuestos;
	}

	public String getDirPostal() {
		return dirPostal;
	}

	public LocalDate getAlta() {
		return alta;
	}

	public boolean añadirBici(String id) {
		if (this.bicicletas.add(id)) {
			numPuestos--;
			return true;
		}
		return false;
	}

	public boolean quitarBici(String id) {
		if (this.bicicletas.remove(id)) {
			numPuestos++;
			return true;
		}
		return false;
	}

	public Set<SitioTuristicoResumen> getSitiosTuristicos() {
		return sitiosTuristicos;
	}

	public List<String> getBicicletas() {
		return bicicletas;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public void setNumPuestos(int numPuestos) {
		this.numPuestos = numPuestos;
	}

	public void setDirPostal(String dirPostal) {
		this.dirPostal = dirPostal;
	}

	public void setAlta(LocalDate alta) {
		this.alta = alta;
	}

	public void setBicicletas(List<String> bicicletas) {
		this.bicicletas = bicicletas;
	}

	public Point getLocation() {
		return location;
	}

	public void setLocation(Point location) {
		this.location = location;
	}

	@Override
	public String toString() {
		return "Estacion [id=" + id + ", nombre=" + nombre + ", numPuestos=" + numPuestos + ", dirPostal=" + dirPostal
				+ ", location=" + location + ", alta=" + alta + ", sitiosTuristicos=" + sitiosTuristicos
				+ ", bicicletas=" + bicicletas + "]";
	}

	public void setSitiosTuristicos(Set<SitioTuristicoResumen> sitiosTuristicos) {
		this.sitiosTuristicos = sitiosTuristicos;
	}

}
