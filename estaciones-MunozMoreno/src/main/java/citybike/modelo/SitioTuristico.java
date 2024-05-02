package citybike.modelo;

import java.util.List;

import javax.json.bind.annotation.JsonbProperty;
import javax.json.bind.annotation.JsonbPropertyOrder;

import repositorio.Identificable;

@JsonbPropertyOrder({ "id", "nombre", "resumen", "categorias", "enlaces", "wikiImg", "wikiUrl" })
public class SitioTuristico implements Identificable {

	// Atributos
	@JsonbProperty
	private String id;
	@JsonbProperty
	private String nombre;
	@JsonbProperty
	private String resumen;
	@JsonbProperty(nillable = true)
	private List<String> categorias;
	@JsonbProperty(nillable = true)
	private List<String> enlaces;
	@JsonbProperty(nillable = true)
	private String wikiImg;
	@JsonbProperty
	private String wikiUrl;

	// Constructor vacío para JSON-B
	public SitioTuristico() {

	}

	public SitioTuristico(String id, String nombre, String resumen, List<String> categorias, List<String> enlaces,
			String wikiImg, String wikiUrl) {
		this.id = id;
		this.nombre = nombre;
		this.resumen = resumen;
		this.categorias = categorias;
		this.enlaces = enlaces;
		this.wikiImg = wikiImg;
		this.wikiUrl = wikiUrl;
	}

	// Métodos Get/Set
	// Métodos Identificable (@Override)
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

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getResumen() {
		return resumen;
	}

	public void setResumen(String resumen) {
		this.resumen = resumen;
	}

	public List<String> getCategorias() {
		return categorias;
	}

	public void setCategorias(List<String> categorias) {
		this.categorias = categorias;
	}

	public List<String> getEnlaces() {
		return enlaces;
	}

	public void setEnlaces(List<String> enlaces) {
		this.enlaces = enlaces;
	}

	public String getWikiImg() {
		return wikiImg;
	}

	public void setWikiImg(String wikimediaImg) {
		this.wikiImg = wikimediaImg;
	}

	public String getWikiUrl() {
		return wikiUrl;
	}

	public void setWikiUrl(String wikiUrl) {
		this.wikiUrl = wikiUrl;
	}

	@Override
	public String toString() {
		return "SitioTuristico [id=" + id + ", nombre=" + nombre + ", resumen=" + resumen + ", categorias=" + categorias
				+ ", enlaces=" + enlaces + ", wikiImg=" + wikiImg + ", wikiUrl=" + wikiUrl + "]";
	}

}