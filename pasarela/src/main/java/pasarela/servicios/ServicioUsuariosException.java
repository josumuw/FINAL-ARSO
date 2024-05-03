package pasarela.servicios;

@SuppressWarnings("serial")
public class ServicioUsuariosException extends Exception {
    public ServicioUsuariosException(String msg, Throwable causa) {
	super(msg, causa);
    }

    public ServicioUsuariosException(String msg) {
	super(msg);
    }
}