package alquileres.servicio;

@SuppressWarnings("serial")
public class ServicioMensajeriaException extends Exception {

    public ServicioMensajeriaException(String msg, Throwable causa) {
	super(msg, causa);
    }

    public ServicioMensajeriaException(String msg) {
	super(msg);
    }
}