package alquileres.modelo;

import java.time.LocalDateTime;

public class Reserva {
	private static int MAX_DURACION_RESERVA = 30;

	private String idBicicleta;
	private LocalDateTime creada;
	private LocalDateTime caducidad;

	public Reserva(String idBicicleta) {
		this.idBicicleta = idBicicleta;
		this.creada = LocalDateTime.now();
		this.caducidad = this.creada.plusMinutes(MAX_DURACION_RESERVA);
	}

	public Reserva() {

	}

	public String getIdBicicleta() {
		return idBicicleta;
	}

	public void setIdBicicleta(String idBicicleta) {
		this.idBicicleta = idBicicleta;
	}

	public LocalDateTime getCreada() {
		return creada;
	}

	public void setCreada(LocalDateTime creada) {
		this.creada = creada;
	}

	public LocalDateTime getCaducidad() {
		return caducidad;
	}

	public void setCaducidad(LocalDateTime caducidad) {
		this.caducidad = caducidad;
	}

	public boolean caducada() {
		LocalDateTime ahora = LocalDateTime.now();
		return ahora.isAfter(this.caducidad);
	}

	public boolean activa() {
		return !caducada();
	}

	@Override
	public String toString() {
		return "Reserva [idBicicleta=" + idBicicleta + ", creada=" + creada + ", caducidad=" + caducidad + "]";
	}

}
