package estaciones.eventos.bus;

import java.time.LocalDateTime;

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

    @Override
    public String toString() {
	return "EventoAlquilar [idBicicleta=" + idBicicleta + ", inicio=" + inicio + "]";
    }
    
    

}
