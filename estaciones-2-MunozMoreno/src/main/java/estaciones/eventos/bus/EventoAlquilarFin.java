package estaciones.eventos.bus;

public class EventoAlquilarFin extends EventoAlquilar {

    private String idEstacion;
    private String fin;

    public EventoAlquilarFin(String idEstacion, String fin) {
	super();
	this.idEstacion = idEstacion;
	this.fin = fin;
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

    @Override
    public String toString() {
	return "EventoAlquilarFin [idEstacion=" + idEstacion + ", fin=" + fin + ", toString()=" + super.toString()
		+ "]";
    }

    
    

}
