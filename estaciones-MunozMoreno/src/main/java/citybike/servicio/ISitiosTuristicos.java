package citybike.servicio;

import java.util.List;

import citybike.modelo.SitioTuristico;

public interface ISitiosTuristicos {
	public List<SitioTuristicoResumen> getSitiosTuristicosGeo(String lat, String lng);

	public SitioTuristico getSitioTuristicoInfo(String id);

}
