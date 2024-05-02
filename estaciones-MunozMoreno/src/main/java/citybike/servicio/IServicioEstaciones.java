package citybike.servicio;

import java.util.Collection;
import java.util.List;

import citybike.modelo.Bicicleta;
import citybike.modelo.Estacion;
import dto.BicicletaDTO;
import repositorio.EntidadNoEncontrada;
import repositorio.RepositorioException;

public interface IServicioEstaciones {
	String crear(String nombre, int numPuestos, String dirPostal, String longitud, String latitud)
			throws RepositorioException;

	List<SitioTuristicoResumen> getSitiosTuristicos(String id) throws RepositorioException;

	void setSitiosTuristicos(String id, Collection<SitioTuristicoResumen> sitiosTuristicos) throws RepositorioException;

	Estacion getEstacion(String id) throws RepositorioException;

	String altaBicicleta(String modelo, String idEstacion) throws RepositorioException;

	void estacionarBicicleta(String idBicicleta) throws RepositorioException;
	void estacionarBicicleta(String idBicicleta, String idEstacion) throws RepositorioException;

	void retirarBicicleta(String idBicicleta) throws RepositorioException;

	void darDeBajaBicicleta(String idBicicleta, String motivo) throws RepositorioException, EntidadNoEncontrada;

	
	List<BicicletaDTO> getBicicletasCercanas(double latitud, double longitud)
			throws RepositorioException, EntidadNoEncontrada;

	List<Estacion> getEstacionesConSitiosTuristicosCercanas()
			throws RepositorioException;
}
