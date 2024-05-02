package alquileres.servicio;

@SuppressWarnings("serial")
public class RequisitosException extends Exception {

	public RequisitosException(String msg, Throwable causa) {
		super(msg, causa);
	}

	public RequisitosException(String msg) {
		super(msg);
	}
}
