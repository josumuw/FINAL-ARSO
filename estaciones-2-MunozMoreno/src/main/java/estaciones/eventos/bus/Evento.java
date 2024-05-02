package estaciones.eventos.bus;

import java.time.LocalDateTime;

public class Evento {

    private String creado;

    public Evento() {
	this.creado = LocalDateTime.now().toString();
    }

    public String getCreado() {
	return creado;
    }

    public void setCreado(String creado) {
	this.creado = creado;
    }

}
