package alquileres.modelo;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class Alquiler {

	private String idBicicleta;
	private LocalDateTime inicio;
	private LocalDateTime fin;

	public Alquiler(String idBicicleta) {
		this.idBicicleta = idBicicleta;
		this.inicio = LocalDateTime.now();
	}

	public Alquiler() {

	}

	public boolean activo() {
		return this.fin == null;
	}

	public long tiempo() {
		LocalDateTime fin = activo() ? LocalDateTime.now() : this.fin;
		return ChronoUnit.MINUTES.between(inicio, fin);
	}

	public String getIdBicicleta() {
		return idBicicleta;
	}

	public void setIdBicicleta(String idBicicleta) {
		this.idBicicleta = idBicicleta;
	}

	public LocalDateTime getInicio() {
		return inicio;
	}

	public void setInicio(LocalDateTime inicio) {
		this.inicio = inicio;
	}

	public LocalDateTime getFin() {
		return fin;
	}

	public void setFin(LocalDateTime fin) {
		this.fin = fin;
	}
	
	public void concluirAlquiler() {
	    this.fin = LocalDateTime.now();
	}

	@Override
	public String toString() {
		return "Alquiler [idBicicleta=" + idBicicleta + ", inicio=" + inicio + ", fin=" + fin + "]";
	}

}
