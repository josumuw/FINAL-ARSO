package estaciones.servicio;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import estaciones.eventos.bus.Evento;
import estaciones.modelo.Bicicleta;
import estaciones.modelo.Estacion;
import estaciones.repositorio.EntidadNoEncontrada;


public interface IServicioEstaciones {

	// ADMIN
	public String altaEstacion(String nombre, int numPuestos, String dirPostal, double latitud, double longitud);
	public String altaBicicleta(String modelo, String idEstacion) throws EntidadNoEncontrada;
	public boolean darBajaBicicleta(String idBicicleta, String motivoBaja) throws EntidadNoEncontrada;
	//public List<Bicicleta> getAllBicicletasEstacion(String idEstacion);
	public Page<Bicicleta> getAllBicicletasEstacionPaginado(String idEstacion, Pageable paginacion);

	
	// CLIENTE
	//public List<Estacion> getAllEstaciones();
	public Page<Estacion> getAllEstacionesPaginado(Pageable paginacion);
	public EstacionInfo getEstacionInfo(String idEstacion) throws EntidadNoEncontrada;
	//public List<Bicicleta> getBicicletasEstacion(String idEstacion);
	public Page<Bicicleta> getBicicletasEstacionPaginado(String idEstacion, Pageable paginacion);
	public void estacionarBicicleta(String idEstacion, String idBicicleta) throws EntidadNoEncontrada, RequisitosException;
	
	// EVENTOS
	public void gestionarEventoAlquiler(Evento evento) throws EntidadNoEncontrada;
}
