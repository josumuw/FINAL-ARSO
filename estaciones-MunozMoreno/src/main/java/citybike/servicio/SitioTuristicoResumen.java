package citybike.servicio;

public class SitioTuristicoResumen {

	private String nombre;
	private String resumen;
	private String distancia;
	private String wikiUrl;

	public SitioTuristicoResumen(String nombre, String resumen, String distancia, String wikiUrl) {
		this.nombre = nombre;
		this.resumen = resumen;
		this.distancia = distancia;
		this.wikiUrl = wikiUrl;
	}

	public SitioTuristicoResumen() {
	}

	// Métodos Get/Set
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

	public String getDistancia() {
		return distancia;
	}

	public void setDistancia(String distancia) {
		this.distancia = distancia;
	}

	public String getWikiUrl() {
		return wikiUrl;
	}

	public void setWikiUrl(String wikiUrl) {
		this.wikiUrl = wikiUrl;
	}

	@Override
	public String toString() {
		return "SitioTuristicoResumen [nombre=" + nombre + ", resumen=" + resumen + ", distancia=" + distancia
				+ ", wikiUrl=" + wikiUrl + "]";
	}
}
