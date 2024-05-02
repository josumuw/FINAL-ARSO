package alquileres.servicio;

@SuppressWarnings("serial")
public class ServicioEstacionesException extends Exception {

    public ServicioEstacionesException(String msg, Throwable causa) {
	super(msg, causa);
    }

    public ServicioEstacionesException(String msg) {
	super(msg);
    }
}
