package estaciones.servicio;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import estaciones.eventos.bus.Evento;
import estaciones.eventos.bus.EventoAlquilar;
import estaciones.eventos.bus.EventoAlquilarFin;
import estaciones.eventos.bus.EventoBicicletaDesactivada;
import estaciones.eventos.servicio.IServicioMensajeria;
import estaciones.modelo.Bicicleta;
import estaciones.modelo.Estacion;
import estaciones.modelo.EstadoBicicleta;
import estaciones.repositorio.EntidadNoEncontrada;
import estaciones.repositorio.RepositorioBicicletas;
import estaciones.repositorio.RepositorioEstaciones;

@Service
@Transactional
public class ServicioEstaciones implements IServicioEstaciones {

    private RepositorioEstaciones repoEstaciones;
    private RepositorioBicicletas repoBicicletas;;
    private IServicioMensajeria servicioMensajeria;

    @Autowired
    public ServicioEstaciones(RepositorioEstaciones repoEstaciones, RepositorioBicicletas repoBicicletas,
	    IServicioMensajeria servicioMensajeria) {
	this.repoEstaciones = repoEstaciones;
	this.repoBicicletas = repoBicicletas;
	this.servicioMensajeria = servicioMensajeria;
    }

    @Override
    public String altaEstacion(String nombre, int numPuestos, String dirPostal, double latitud, double longitud) {

	if (nombre == null || nombre.isEmpty())
	    throw new IllegalArgumentException("nombre: no debe ser nulo ni vacio");
	if (numPuestos < 1)
	    throw new IllegalArgumentException("numPuestos: debe ser mayor que cero");
	if (dirPostal == null || dirPostal.isEmpty())
	    throw new IllegalArgumentException("dirPostal: no debe ser nula ni vacia");

	Estacion estacion = new Estacion(nombre, numPuestos, dirPostal, latitud, longitud);
	String idEstacion = repoEstaciones.save(estacion).getId();
	return idEstacion;

    }

    @Override
    public String altaBicicleta(String modelo, String idEstacion) throws EntidadNoEncontrada {
	if (modelo == null || modelo.isEmpty())
	    throw new IllegalArgumentException("modelo: no debe ser nulo ni vacio");
	if (idEstacion == null || idEstacion.isEmpty())
	    throw new IllegalArgumentException("idEstacion: no debe ser nulo ni vacio");

	Optional<Estacion> consultaEstacion = repoEstaciones.findById(idEstacion);
	if (!consultaEstacion.isPresent()) {
	    throw new EntidadNoEncontrada("estacion no encontrada: " + idEstacion);
	}
	Estacion estacion = consultaEstacion.get();

	if (!estacion.isEstacionable())
	    return null;
	estacion.estacionarBicicleta();
	repoEstaciones.save(estacion);
	Bicicleta bicicleta = new Bicicleta(modelo, idEstacion);
	String idBicicleta = repoBicicletas.save(bicicleta).getId();
	return idBicicleta;
    }

    @Override
    public boolean darBajaBicicleta(String idBicicleta, String motivoBaja) throws EntidadNoEncontrada {
	if (idBicicleta == null || idBicicleta.isEmpty())
	    throw new IllegalArgumentException("idBicicleta: no debe ser nulo ni vacio");
	if (motivoBaja == null || motivoBaja.isEmpty())
	    throw new IllegalArgumentException("motivoBaja: no debe ser nulo ni vacio");

	Optional<Bicicleta> consultaBicicleta = repoBicicletas.findById(idBicicleta);
	if (!consultaBicicleta.isPresent())
	    throw new EntidadNoEncontrada("bicicleta no encontrada: " + idBicicleta);
	Bicicleta bicicleta = consultaBicicleta.get();

	String idEstacion = bicicleta.getIdEstacion();
	Optional<Estacion> consultaEstacion = repoEstaciones.findById(idEstacion);
	if (!consultaEstacion.isPresent())
	    throw new EntidadNoEncontrada("estacion no encontrada: " + idEstacion);
	Estacion estacion = consultaEstacion.get();

	if (bicicleta.darBajaBicicleta(motivoBaja)) {
	    repoBicicletas.save(bicicleta);
	    estacion.darBajaBicicleta();
	    repoEstaciones.save(estacion);

	    // EVENTO citybike.estaciones.bicicleta-desactivada
	    EventoBicicletaDesactivada evento = new EventoBicicletaDesactivada(idBicicleta,
		    bicicleta.getFechaBaja().toString(), bicicleta.getMotivoBaja());
	    servicioMensajeria.publicar(evento);
	}

	return false;
    }

    @Override
    public Page<Bicicleta> getAllBicicletasEstacionPaginado(String idEstacion, Pageable paginacion) {
	if (idEstacion == null || idEstacion.isEmpty())
	    throw new IllegalArgumentException("idEstacion: no debe ser nulo ni vacio");

	// TODO: COMPROBAR QUE EXISTE ESTACION
	return repoBicicletas.findByidEstacion(idEstacion, paginacion);
    }

    @Override
    public Page<Estacion> getAllEstacionesPaginado(Pageable paginacion) {
	return repoEstaciones.findAll(paginacion);
    }

    @Override
    public EstacionInfo getEstacionInfo(String idEstacion) throws EntidadNoEncontrada {
	if (idEstacion == null || idEstacion.isEmpty())
	    throw new IllegalArgumentException("idEstacion: no debe ser nulo ni vacio");

	Optional<Estacion> consultaEstacion = repoEstaciones.findById(idEstacion);
	if (!consultaEstacion.isPresent())
	    throw new EntidadNoEncontrada("estacion no encontrada: " + idEstacion);
	Estacion estacion = consultaEstacion.get();

	String nombre = estacion.getNombre();
	int numPuestos = estacion.getNumPuestos();
	String dirPostal = estacion.getDirPostal();
	EstacionInfo estacionInfo = new EstacionInfo(nombre, numPuestos, dirPostal);
	return estacionInfo;
    }

    @Override
    public Page<Bicicleta> getBicicletasEstacionPaginado(String idEstacion, Pageable paginacion) {
	if (idEstacion == null || idEstacion.isEmpty())
	    throw new IllegalArgumentException("idEstacion: no debe ser nulo ni vacio");

	return repoBicicletas.findDisponiblesByidEstacion(idEstacion, paginacion);

    }

    @Override
    public void estacionarBicicleta(String idEstacion, String idBicicleta)
	    throws EntidadNoEncontrada, RequisitosException {
	if (idEstacion == null || idEstacion.isEmpty())
	    throw new IllegalArgumentException("idEstacion: no debe ser nulo ni vacio");
	if (idBicicleta == null || idBicicleta.isEmpty())
	    throw new IllegalArgumentException("idBicicleta: no debe ser nulo ni vacio");

	Optional<Estacion> consultaEstacion = repoEstaciones.findById(idEstacion);
	if (!consultaEstacion.isPresent())
	    throw new EntidadNoEncontrada("estacion no encontrada: " + idEstacion);
	Estacion estacion = consultaEstacion.get();

	Optional<Bicicleta> consultaBicicleta = repoBicicletas.findById(idBicicleta);
	if (!consultaBicicleta.isPresent())
	    throw new EntidadNoEncontrada("bicicleta no encontrada: " + idBicicleta);
	Bicicleta bicicleta = consultaBicicleta.get();

	if (!estacion.isEstacionable())
	    throw new RequisitosException("No hay plazas disponibles");

	estacion.estacionarBicicleta();
	repoEstaciones.save(estacion);
	bicicleta.estacionarBicicleta(idEstacion);
	repoBicicletas.save(bicicleta);
    }

    @Override
    public void gestionarEventoAlquiler(Evento evento) throws EntidadNoEncontrada {
	String idBicicleta;
	if (evento instanceof EventoAlquilarFin) {
	    EventoAlquilarFin eventoAlquilarFin = (EventoAlquilarFin) evento;
	    idBicicleta = eventoAlquilarFin.getIdBicicleta();
	    Optional<Bicicleta> consultaBicicleta = repoBicicletas.findById(eventoAlquilarFin.getIdBicicleta());
	    if (!consultaBicicleta.isPresent())
		throw new EntidadNoEncontrada("bicicleta no encontrada: " + idBicicleta);
	    Bicicleta bicicleta = consultaBicicleta.get();
	    bicicleta.setEstado(EstadoBicicleta.DISPONIBLE);
	    repoBicicletas.save(bicicleta);
	} else if (evento instanceof EventoAlquilar) {
	    EventoAlquilar eventoAlquilar = (EventoAlquilar) evento;
	    idBicicleta = eventoAlquilar.getIdBicicleta();
	    Optional<Bicicleta> consultaBicicleta = repoBicicletas.findById(eventoAlquilar.getIdBicicleta());
	    if (!consultaBicicleta.isPresent())
		throw new EntidadNoEncontrada("bicicleta no encontrada: " + idBicicleta);
	    Bicicleta bicicleta = consultaBicicleta.get();
	    bicicleta.setEstado(EstadoBicicleta.NO_DISPONIBLE);
	    repoBicicletas.save(bicicleta);
	}
    }
}
