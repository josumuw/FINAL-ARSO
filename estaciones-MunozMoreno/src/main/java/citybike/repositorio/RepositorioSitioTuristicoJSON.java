package citybike.repositorio;

import citybike.modelo.SitioTuristico;
import repositorio.RepositorioJSON;

public class RepositorioSitioTuristicoJSON extends RepositorioJSON<SitioTuristico> {

	@Override
	public Class<SitioTuristico> getClase() {
		return SitioTuristico.class;
	}

}
