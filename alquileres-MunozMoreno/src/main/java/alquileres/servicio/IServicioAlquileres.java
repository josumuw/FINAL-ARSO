package alquileres.servicio;

import alquileres.modelo.Alquiler;
import alquileres.modelo.Reserva;
import repositorio.EntidadNoEncontrada;
import repositorio.RepositorioException;

public interface IServicioAlquileres {

	public Reserva reservar(String idUsuario, String idBicicleta) throws RequisitosException, RepositorioException, IllegalArgumentException, EntidadNoEncontrada;

	public Alquiler confirmarReserva(String idUsuario) throws RequisitosException, RepositorioException, EntidadNoEncontrada, IllegalArgumentException, ServicioMensajeriaException;

	public Alquiler alquilar(String idUsuario, String idBicicleta) throws RequisitosException, IllegalArgumentException, RepositorioException, EntidadNoEncontrada, ServicioMensajeriaException;

	public HistorialUsuario historialUsuario(String idUsuario) throws RepositorioException, EntidadNoEncontrada;

	public void dejarBicicleta(String idUsuario, String idEstacion) throws RepositorioException, EntidadNoEncontrada, RequisitosException, ServicioEstacionesException, ServicioMensajeriaException;

	public void liberarBloqueo(String idUsuario) throws RepositorioException, EntidadNoEncontrada;
	
	public void eliminarReservas(String idBicicleta);
}
