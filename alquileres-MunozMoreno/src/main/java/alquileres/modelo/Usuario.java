package alquileres.modelo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import repositorio.Identificable;

public class Usuario implements Identificable {
	private String id;
	private List<Reserva> reservas;
	private List<Alquiler> alquileres;

	public Usuario() {

	}

	public Usuario(String id) {
		this.id = id;
		this.reservas = new LinkedList<>();
		this.alquileres = new LinkedList<>();
	}

	public int reservasCaducadas() {
		int numCaducadas = 0;
		for (Reserva reserva : reservas) {
			if (reserva.caducada()) {
				numCaducadas++;
			}
		}
		return numCaducadas;
	}

	public int tiempoUsoHoy() {
		int tiempoTotalHoy = 0;
		LocalDate hoy = LocalDate.now();
		for (Alquiler alquiler : alquileres) {
			LocalDateTime inicioAlquiler = alquiler.getInicio();
			if (inicioAlquiler.toLocalDate().equals(hoy)) {
				tiempoTotalHoy += alquiler.tiempo();
			}
		}
		return tiempoTotalHoy;
	}

	public int tiempoUsoSemana() {
		int tiempoTotalSemana = 0;
		LocalDate hoy = LocalDate.now();
		LocalDate semana = hoy.minusWeeks(1);
		for (Alquiler alquiler : alquileres) {
			LocalDateTime inicioAlquiler = alquiler.getInicio();
			if (!inicioAlquiler.toLocalDate().isBefore(semana)) {
				tiempoTotalSemana += alquiler.tiempo();
			}
		}
		return tiempoTotalSemana;
	}

	public boolean superaTiempo() {
		return tiempoUsoHoy() >= 60 || tiempoUsoSemana() >= 180;
	}

	public Reserva reservaActiva() {
		if (!reservas.isEmpty()) {
			Reserva ultima = reservas.get(reservas.size() - 1);
			if (ultima.activa()) {
				return ultima;
			}
		}
		return null;
	}

	public boolean eliminarReservaActiva() {
		if (!reservas.isEmpty()) {
			Reserva ultima = reservas.get(reservas.size() - 1);
			if (ultima.activa()) {
				return reservas.remove(ultima);
			}
		}
		return true;
	}

	public Alquiler getAlquilerActivo() {
		if (!alquileres.isEmpty()) {
			Alquiler ultimo = alquileres.get(alquileres.size() - 1);
			if (ultimo.activo()) {
				return ultimo;
			}
		}
		return null;
	}

	public Alquiler terminarAlquilerActivo() {
		Alquiler alquilerActivo = this.getAlquilerActivo();
		if (alquilerActivo != null) {
			alquilerActivo.concluirAlquiler();
			return alquilerActivo;
		}
		return null;
	}
	
	public void quitarBloqueo() {
		this.setReservas(new LinkedList());
	}

	public boolean bloqueado() {
		return this.reservasCaducadas() >= 3;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<Reserva> getReservas() {
		return reservas;
	}

	public void setReservas(List<Reserva> reservas) {
		this.reservas = reservas;
	}

	public void addReserva(Reserva reserva) {
		this.reservas.add(reserva);
	}

	public List<Alquiler> getAlquileres() {
		return alquileres;
	}

	public void setAlquileres(List<Alquiler> alquileres) {
		this.alquileres = alquileres;
	}

	public void addAlquiler(Alquiler alquiler) {
		this.alquileres.add(alquiler);
	}

	@Override
	public String toString() {
		return "Usuario [id=" + id + ", reservas=" + reservas + ", alquileres=" + alquileres + ", reservasCaducadas()="
				+ reservasCaducadas() + ", tiempoUsoHoy()=" + tiempoUsoHoy() + ", tiempoUsoSemana()="
				+ tiempoUsoSemana() + ", superaTiempo()=" + superaTiempo() + ", reservaActiva()=" + reservaActiva()
				+ ", bloqueado()=" + bloqueado() + "]";
	}
}
