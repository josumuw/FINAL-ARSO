package estaciones.eventos.bus;

public class EventoBicicletaDesactivada extends Evento {

    private String idBicicleta;
    private String fechaBaja;
    private String motivoBaja;
    
    public EventoBicicletaDesactivada(String idBicicleta, String fechaBaja, String motivoBaja) {
	super();
	this.idBicicleta = idBicicleta;
	this.fechaBaja = fechaBaja;
	this.motivoBaja = motivoBaja;
    }
    
    public EventoBicicletaDesactivada() {

    }

    public String getIdBicicleta() {
        return idBicicleta;
    }

    public void setIdBicicleta(String idBicicleta) {
        this.idBicicleta = idBicicleta;
    }

    public String getFechaBaja() {
        return fechaBaja;
    }

    public void setFechaBaja(String fechaBaja) {
        this.fechaBaja = fechaBaja;
    }

    public String getMotivoBaja() {
        return motivoBaja;
    }

    public void setMotivoBaja(String motivoBaja) {
        this.motivoBaja = motivoBaja;
    }
    
    
    
    
    
}
