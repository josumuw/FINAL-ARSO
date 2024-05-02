package citybike.servicio;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonValue;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import citybike.modelo.SitioTuristico;
import repositorio.EntidadNoEncontrada;
import repositorio.FactoriaRepositorios;
import repositorio.Repositorio;
import repositorio.RepositorioException;

public class SitiosTuristicosGeoNames implements ISitiosTuristicos {

	public static final String CATEGORIAS = "http://www.w3.org/1999/02/22-rdf-syntax-ns#type";
	public static final String NOMBRE = "http://www.w3.org/2000/01/rdf-schema#label";
	public static final String ENLACES = "http://dbpedia.org/ontology/wikiPageExternalLink";
	public static final String IMAGEN = "http://es.dbpedia.org/property/imagen";
	public static final String RESUMEN = "http://dbpedia.org/ontology/abstract";

	// Categorias de interés
	public static final Set<String> CATEGORIAS_SITIOS_INTERES = new HashSet<>(Arrays.asList("Place", "Location"));

	private Repositorio<SitioTuristico, String> repositorio = FactoriaRepositorios.getRepositorio(SitioTuristico.class);

	@Override
	public List<SitioTuristicoResumen> getSitiosTuristicosGeo(String lat, String lng) {
		// Coordenadas geográficas (por ejemplo, 37.980906, -1.1273963)

		// Colección para almacenar objetos SitioTuristico
		List<SitioTuristicoResumen> sitiosTuristicos = new ArrayList<>();

		// Construir la URL con los parámetros
		String requestURL = "http://api.geonames.org" + "/findNearbyWikipedia" + "?lat=" + lat + "&lng=" + lng
				+ "&username=aadd" + "&lang=es" + "&maxRows=10" + "&radius=1";

		try {
			// Realiza una solicitud HTTP GET
			URL url = new URL(requestURL);

			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");

			// Obtener la respuesta en formato XML
			InputStream responseStream = conn.getInputStream();

			// Crear un analizador de documentos XML
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse(responseStream);

			// Procesar el documento XML para obtener los SitiosTuristicos
			NodeList entryList = document.getElementsByTagName("entry");
			for (int i = 0; i < entryList.getLength(); i++) {

				Element entry = (Element) entryList.item(i);

				String nombre = entry.getElementsByTagName("title").item(0).getTextContent();
				String resumen = entry.getElementsByTagName("summary").item(0).getTextContent();
				String wikiUrl = entry.getElementsByTagName("wikipediaUrl").item(0).getTextContent();
				String distancia = entry.getElementsByTagName("distance").item(0).getTextContent();

				// Crear objeto SitioTuristico y agregarlo al repositorio cache
				String id = nombre.replace(" ", "_");
				SitioTuristico sitio = this.getSitioTuristicoInfo(id);
				sitio.setWikiUrl(wikiUrl);
				// FILTRAR? Aquí podriamos seleccionar mediante las categorias los
				// SitiosTuristicos de verdad (algunos son, por ejemplo, eventos). Dado que el
				// enunciado de la práctica indica que todo elemento con artículo en wikipedia
				// es de interés, no filtramos, ya que todos tienes.
				// Podríamos usar:
				// boolean isInteresante =
				// categorias.stream().anyMatch(CATEGORIAS_SITIOS_INTERES::contains);
				repositorio.add(sitio);
				SitioTuristicoResumen sitioResumen = new SitioTuristicoResumen(nombre, resumen, distancia, wikiUrl);
				sitiosTuristicos.add(sitioResumen);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return sitiosTuristicos;
	}

	@Override
	public SitioTuristico getSitioTuristicoInfo(String id) {
		try {
			return repositorio.getById(id);
		} catch (RepositorioException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (EntidadNoEncontrada e) {
			// Construir la URL con los parámetros
			String requestURL = "https://es.dbpedia.org" + "/data/" + id + ".json";
			String object = "http://es.dbpedia.org" + "/resource/" + id;
			try {
				// Realiza una solicitud HTTP GET

				URL url = new URL(requestURL);

				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				conn.setRequestMethod("GET");

				InputStream responseStream = conn.getInputStream();
				// Crear un lector JSON
				JsonReader jsonReader = Json.createReader(responseStream);

				// Leer el JSON como un objeto JSON
				JsonObject jsonObject = jsonReader.readObject();

				if (jsonObject.containsKey(object)) {
					JsonObject sitioTuristicoObject = jsonObject.getJsonObject(object);

					// Leer la propiedad NOMBRE
					String nombre = null;
					if (sitioTuristicoObject.containsKey(NOMBRE)) {
						JsonArray resumenArray = sitioTuristicoObject.getJsonArray(NOMBRE);
						if (resumenArray.size() > 0) {
							JsonObject resumenObject = resumenArray.getJsonObject(0);
							if (resumenObject.containsKey("value")) {
								nombre = resumenObject.getString("value");
							}
						}
					}

					// Leer la propiedad RESUMEN
					String resumen = null;
					if (sitioTuristicoObject.containsKey(RESUMEN)) {
						JsonArray resumenArray = sitioTuristicoObject.getJsonArray(RESUMEN);
						if (resumenArray.size() > 0) {
							JsonObject resumenObject = resumenArray.getJsonObject(0);
							if (resumenObject.containsKey("value")) {
								resumen = resumenObject.getString("value");
							}
						}
					}

					// Leer la propiedad IMAGEN
					String wikiImg = null;
					if (sitioTuristicoObject.containsKey(IMAGEN)) {
						JsonArray imagenArray = sitioTuristicoObject.getJsonArray(IMAGEN);
						if (imagenArray.size() > 0) {
							JsonObject imagenObject = imagenArray.getJsonObject(0);
							if (imagenObject.containsKey("value")) {
								wikiImg = imagenObject.getString("value");
							}
						}
					}

					// Leer la propiedad ENLACES
					List<String> enlaces = new ArrayList<>();
					if (sitioTuristicoObject.containsKey(ENLACES)) {
						JsonArray enlacesArray = sitioTuristicoObject.getJsonArray(ENLACES);
						for (JsonValue value : enlacesArray) {
							if (value.getValueType() == JsonValue.ValueType.OBJECT) {
								JsonObject enlacesObject = (JsonObject) value;
								if (enlacesObject.containsKey("value")) {
									enlaces.add(enlacesObject.getString("value"));
								}
							}
						}
					}
					// Leer la propiedad CATEGORIAS
					List<String> categorias = new ArrayList<>();
					if (sitioTuristicoObject.containsKey(CATEGORIAS)) {
						JsonArray categoriasArray = sitioTuristicoObject.getJsonArray(CATEGORIAS);
						for (JsonValue value : categoriasArray) {
							if (value.getValueType() == JsonValue.ValueType.OBJECT) {
								JsonObject categoriasObject = (JsonObject) value;
								if (categoriasObject.containsKey("value")) {

									String uri = categoriasObject.getString("value");
									// Obtener el último segmento después de la última "/"
									String[] segments = uri.split("/");
									if (segments.length > 0) {
										String lastSegment = segments[segments.length - 1];
										categorias.add(lastSegment);
									}
								}
							}
						}
					}
					String wikiUrl = null;
					SitioTuristico sitioInfo = new SitioTuristico(id, nombre, resumen, categorias, enlaces, wikiImg, wikiUrl);
					return sitioInfo;
				}
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}

		return null;
	}

}
