package alquileres.servicio;

public interface IServicioEstaciones {

	public void isPlazasDisponibles(String idEstacion) throws ServicioEstacionesException, RequisitosException;

	public void estacionarBicicleta(String idBicicleta, String idEstacion) throws ServicioEstacionesException, RequisitosException;
}
