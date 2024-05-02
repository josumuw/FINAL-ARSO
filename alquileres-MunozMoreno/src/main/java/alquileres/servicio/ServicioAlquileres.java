package alquileres.servicio;

import alquileres.modelo.Alquiler;
import alquileres.modelo.Reserva;
import alquileres.modelo.Usuario;
import alquileres.repositorio.IRepositorioUsuario;
import eventos.rabbitmq.EventoAlquilar;
import eventos.rabbitmq.EventoAlquilarFin;
import repositorio.EntidadNoEncontrada;
import repositorio.FactoriaRepositorios;
import repositorio.RepositorioException;
import servicio.FactoriaServicios;

public class ServicioAlquileres implements IServicioAlquileres {

    // Repositorio JPA-SQL Usuarios
    public IRepositorioUsuario repoUsuarios = FactoriaRepositorios.getRepositorio(Usuario.class);

    // IServicioEstaciones
    IServicioEstaciones servicioEstacion = FactoriaServicios.getServicio(IServicioEstaciones.class);

    // IServicioMensajeria
    IServicioMensajeria servicioMensajeria = FactoriaServicios.getServicio(IServicioMensajeria.class);

    public ServicioAlquileres() {

    }

    @Override
    public Reserva reservar(String idUsuario, String idBicicleta)
	    throws IllegalArgumentException, RepositorioException, RequisitosException, EntidadNoEncontrada {
	if (idUsuario == null || idUsuario.isEmpty())
	    throw new IllegalArgumentException("El identificador del usuario no debe ser nulo o vació");
	if (idBicicleta == null || idBicicleta.isEmpty())
	    throw new IllegalArgumentException("El identificador de la bicicleta no debe ser nulo o vació");
	Usuario usuario = null;
	boolean existe = false;
	System.out.println("PREVIA CONSULTA");
	try {
	    usuario = repoUsuarios.getById(idUsuario);
	    existe = true;
	    // Requisitos
	    if (usuario.bloqueado())
		throw new RequisitosException("El usuario está bloqueado.");
	    if (usuario.reservaActiva() != null)
		throw new RequisitosException("El usuario tiene una reserva activa.");
	    if (usuario.superaTiempo())
		throw new RequisitosException("El usuario ha excedido el tiempo de uso permitido.");
	} catch (EntidadNoEncontrada e) {
	    System.out.println("NO ENCONTRADA");
	    usuario = new Usuario(idUsuario);
	}
	// Se crea la reserva
	Reserva reserva = new Reserva(idBicicleta);
	usuario.addReserva(reserva);

	if (existe) {
	    repoUsuarios.update(usuario);
	} else {
	    repoUsuarios.add(usuario);
	}

	return reserva;
    }

    @Override
    public Alquiler confirmarReserva(String idUsuario)
	    throws RequisitosException, RepositorioException, EntidadNoEncontrada, IllegalArgumentException, ServicioMensajeriaException {
	// Recuperamos el usuario
	Usuario usuario = repoUsuarios.getById(idUsuario);
	// Recuperamos si existe una reserva Activa
	Reserva reservaActiva = usuario.reservaActiva();
	String idBicicleta = reservaActiva.getIdBicicleta();
	// Intentamos crear un alquiler para dicha reserva
	Alquiler alquiler = alquilar(idUsuario, idBicicleta);
	// Obtenemos el usuario actualizado con su alquiler realizado del repo
	// Si no lo hacemos así el alquiler se borra
	usuario = repoUsuarios.getById(idUsuario);
	usuario.eliminarReservaActiva();
	repoUsuarios.update(usuario);
	return alquiler;
    }

    @Override
    public Alquiler alquilar(String idUsuario, String idBicicleta) throws IllegalArgumentException, RequisitosException,
	    RepositorioException, EntidadNoEncontrada, ServicioMensajeriaException {
	if (idUsuario == null || idUsuario.isEmpty())
	    throw new IllegalArgumentException("El identificador del usuario no debe ser nulo o vacío");
	if (idBicicleta == null || idBicicleta.isEmpty())
	    throw new IllegalArgumentException("El identificador de la bicicleta no debe ser nulo o vacío");

	Usuario usuario;
	boolean existe = false;

	try {
	    usuario = repoUsuarios.getById(idUsuario);
	    existe = true;
	    // Requisitos
	    if (usuario.getAlquilerActivo() != null)
		throw new RequisitosException("El usuario tiene un alquiler activo.");
	    if (usuario.bloqueado())
		throw new RequisitosException("El usuario está bloqueado.");
	    if (usuario.superaTiempo())
		throw new RequisitosException("El usuario ha excedido el tiempo de uso permitido.");
	} catch (EntidadNoEncontrada e) {
	    usuario = new Usuario(idUsuario);
	}
	Alquiler alquiler = new Alquiler(idBicicleta);
	usuario.addAlquiler(alquiler);
	if (existe)
	    repoUsuarios.update(usuario);
	else
	    repoUsuarios.add(usuario);

	// EVENTO citybike.alquileres.bicicleta-alquilada
	EventoAlquilar evento = new EventoAlquilar(alquiler);
	servicioMensajeria.publicar(evento);

	return alquiler;
    }

    @Override
    public HistorialUsuario historialUsuario(String idUsuario) throws RepositorioException, EntidadNoEncontrada {
	Usuario usuario = repoUsuarios.getById(idUsuario);
	return new HistorialUsuario(usuario);
    }

    @Override
    public void dejarBicicleta(String idUsuario, String idEstacion) throws RepositorioException, EntidadNoEncontrada,
	    RequisitosException, ServicioEstacionesException, ServicioMensajeriaException {
	Usuario usuario = repoUsuarios.getById(idUsuario);
	Alquiler alquilerActivo = usuario.getAlquilerActivo();
	// Requisitos
	if (alquilerActivo == null)
	    throw new RequisitosException("El usuario NO tiene un alquiler activo.");

	String idBicicleta = alquilerActivo.getIdBicicleta();
	servicioEstacion.estacionarBicicleta(idBicicleta, idEstacion);

	Alquiler alquiler = usuario.terminarAlquilerActivo();
	
	if (alquiler == null) {
	    throw new RequisitosException("Error al concluir el alquiler activo.");
	}
	repoUsuarios.update(usuario);
	EventoAlquilarFin evento = new EventoAlquilarFin(alquiler, idEstacion);
	// EVENTO BICICLETA-ALQUILER-CONCLUIDO
	servicioMensajeria.publicar(evento);
    }

    @Override
    public void liberarBloqueo(String idUsuario) throws RepositorioException, EntidadNoEncontrada {
	Usuario usuario = repoUsuarios.getById(idUsuario);
	usuario.quitarBloqueo();
	repoUsuarios.update(usuario);
    }

    public void eliminarReservas(String idBicicleta) {
	// TODO: ELIMINAR RESERVAS CON idBicicleta.
    }

}
