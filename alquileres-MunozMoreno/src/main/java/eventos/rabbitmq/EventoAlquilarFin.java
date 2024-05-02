package eventos.rabbitmq;

import alquileres.modelo.Alquiler;

public class EventoAlquilarFin extends EventoAlquilar {

    private String idEstacion;
    private String fin;

    public EventoAlquilarFin(Alquiler alquiler, String idEstacion) {
	super(alquiler);
	this.idEstacion = idEstacion;
	this.fin = alquiler.getFin().toString();
    }

    public EventoAlquilarFin() {

    }

    public String getIdEstacion() {
	return idEstacion;
    }

    public void setIdEstacion(String idEstacion) {
	this.idEstacion = idEstacion;
    }

    public String getFin() {
	return fin;
    }

    public void setFin(String fin) {
	this.fin = fin;
    }

}
