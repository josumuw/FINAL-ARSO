package eventos.rabbitmq;

import java.time.LocalDateTime;

import alquileres.modelo.Alquiler;

public class EventoAlquilar extends Evento {

    private String idBicicleta;
    private String inicio;

    public EventoAlquilar(String idBicicleta, LocalDateTime inicio) {
	super();
	this.idBicicleta = idBicicleta;
	this.inicio = inicio.toString();
    }

    public EventoAlquilar() {

    }
    
    public EventoAlquilar(Alquiler alquiler) {
	this.idBicicleta = alquiler.getIdBicicleta();
	this.inicio = alquiler.getInicio().toString();
    }

    public String getIdBicicleta() {
	return idBicicleta;
    }

    public void setIdBicicleta(String idBicicleta) {
	this.idBicicleta = idBicicleta;
    }

    public String getInicio() {
	return inicio;
    }

    public void String(String inicio) {
	this.inicio = inicio;
    }

}
